package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity=0;
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamBox=(CheckBox)findViewById(R.id.whipped_Cream);
        CheckBox chocolateBox=(CheckBox)findViewById(R.id.chocolate);
        boolean hasWhippedCream=whippedCreamBox.isChecked();
        boolean hasChocolate=chocolateBox.isChecked();

        EditText name_text=(EditText)findViewById(R.id.name);
        String name=name_text.getText().toString();

        if(name.length()<2){
            Toast.makeText(this,"Please fill valid name",Toast.LENGTH_SHORT).show();
            return;
        }

        int price=calculatePrice(hasWhippedCream,hasChocolate);

        String s=createOrderSummary(price,hasWhippedCream,hasChocolate,name);

        openmail(s);

    }

    public void openmail(String s){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Your Order");
        intent.putExtra(Intent.EXTRA_TEXT, s);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public int calculatePrice(boolean whippedCream,boolean chocolate){
        int basePrice=5;
        if(whippedCream){
            basePrice+=1;
        }

        if(chocolate){
            basePrice+=2;
        }
        return basePrice*quantity;
    }

    public String createOrderSummary(int price,boolean whippedCream,boolean chocolate,String name){
        String text=getString(R.string.order_summary_name,name);
        text+=getString(R.string.order_summary_whipped_cream,whippedCream);
        text+=getString(R.string.order_summary_chocolate,chocolate);
        text+=getString(R.string.order_summary_quantity,quantity);
        text+=getString(R.string.order_summary_total,price);
        text+=getString(R.string.thank_you);
        return text;
    }

    public void increment(View view) {
        if(quantity<100)
            quantity++;
        else{
            Toast.makeText(this,"Coffee can't be more than 100",Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if(quantity==0) {
            Toast.makeText(this,"Value can't be negative",Toast.LENGTH_SHORT).show();
        }
        else{
            quantity--;
        }
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}