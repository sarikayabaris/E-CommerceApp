package mobsmile.com.ecommercewithpaypal.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import mobsmile.com.ecommercewithpaypal.R;
import mobsmile.com.ecommercewithpaypal.adapter.OrderAdapter;
import mobsmile.com.ecommercewithpaypal.entity.Order;

/**
 * Created by Baris on 05.9.2015.
 * Activity for Main Order Page
 * @author Baris Sarikaya
 * @version 1.0
 */
public class MainScreenActivity extends Activity {

    /**
     * Creation of activity
     * @param savedInstanceState keeps saved info
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        ListView lv = (ListView) findViewById(R.id.lvOrders);
        Button btnCheckout = (Button) findViewById(R.id.btnCheckout);

        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, "New Item 1", "New item details added.", 3, 0));
        orders.add(new Order(2, "New Item 2", "New item details added.", 2, 0));
        orders.add(new Order(3, "New Item 3", "New item details added.", 5, 0));
        orders.add(new Order(4, "New Item 4", "New item details added.", 7, 0));
        OrderAdapter adapter = new OrderAdapter(MainScreenActivity.this, R.layout.order_row, orders);
        lv.setAdapter(adapter);

        btnCheckout.setOnClickListener(new View.OnClickListener() {

            /**
             * Checkout for PayPal Order
             * @param view of activity
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainScreenActivity.this, PaypalOrderPayment.class));
            }
        });
    }

}
