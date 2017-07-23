package com.example.omjee.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox checkBox = (CheckBox) findViewById(R.id.WHipped_cream_checkBox);
        boolean hasWhippedCream = checkBox.isChecked();
        CheckBox checkBox1 = (CheckBox) findViewById(R.id.ChocolateCheckBox);
        boolean hasChocolate_topping = checkBox1.isChecked();
        EditText editText = (EditText) findViewById(R.id.name_field);
        String name = editText.getText().toString();
        int pricing = calculatePrice(hasChocolate_topping, hasWhippedCream);
        String summary = createOrderSummary(pricing, hasWhippedCream, hasChocolate_topping);
        displayQuantity(quantity);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, summary);
        sendIntent.setData(Uri.parse("mailTo:"));
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.java_order) + name);
        sendIntent.setType("text/plain");
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendIntent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * this method is called when the positive button is clicked
     **/
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You can't order more than 100 cups", Toast.LENGTH_SHORT).show();
        } else {
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method is called when the negative button is clicked
     **/
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You must order atLeast 1 coffee", Toast.LENGTH_SHORT).show();
        } else {
            quantity = quantity - 1;

            displayQuantity(quantity);
        }
    }

    /**
     * Calculate the price from this method
     **/
    public int calculatePrice(boolean hasChoclate, boolean hasWhipped_Cream) {
        int pricePerCup = 50;
        if (hasWhipped_Cream) {
            pricePerCup = pricePerCup + 20;
        }
        if (hasChoclate) {
            pricePerCup = pricePerCup + 30;
        }
        return quantity * pricePerCup;

    }

    public String createOrderSummary(int price, boolean hasCream, boolean hasChoclate) {

        String priceMessage = getString(R.string.java_hasWhipped) + " :" + hasCream;
        priceMessage += "\n " + getString(R.string.java_hasChocolate) + " :" + hasChoclate;
        priceMessage += "\n " + getString(R.string.quantity) + " :" + quantity;
        priceMessage += "\n " + getString(R.string.Total) + " :" + price;
        priceMessage += "\n " + getString(R.string.Thanks);
        return (priceMessage);

    }
}