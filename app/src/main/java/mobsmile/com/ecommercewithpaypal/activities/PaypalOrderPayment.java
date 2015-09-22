package mobsmile.com.ecommercewithpaypal.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mobsmile.com.ecommercewithpaypal.R;
import mobsmile.com.ecommercewithpaypal.entity.Order;
import mobsmile.com.ecommercewithpaypal.utils.Utils;

/**
 * Created by Baris on 05.9.2015.
 * Activity for Payment Summary Page
 * @author Baris Sarikaya
 * @version 1.0
 */
public class PaypalOrderPayment extends Activity {

    /**
     * PayPal Configuration
     */
    public static PayPalConfiguration paypalConfig = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Utils.PAYPAL_SDK_ID)
            .acceptCreditCards(true);
    private Button btnPayWithPayPal;
    private TextView txtPaymentCompany;
    private ListView lvCheckoutItems;
    private TextView txtPaymentAmount;
    private Integer REQUEST_CODE_PAYMENT = 1;
    private Double totalAmount = 0.0;

    /**
     * Creation of Activity
     * @param savedInstanceState for saving state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paypal_payment);

        // Starting PayPal service
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        startService(intent);
        lvCheckoutItems = (ListView) findViewById(R.id.lvCheckoutItems);
        txtPaymentCompany = (TextView) findViewById(R.id.txtPaymentCompany);
        txtPaymentAmount = (TextView) findViewById(R.id.txtPaymentAmount);
        txtPaymentCompany.setText(Utils.COMPANY_MAIL);
        btnPayWithPayPal = (Button) findViewById(R.id.btnPayWithPayPal);
        List<String> checkoutList = new ArrayList<>();
        for (Order order : Utils.getCheckoutItems()) {
            checkoutList.add(order.getTitle() + " Price: £" + order.getPrice() + " Qty: " + order.getQuantity());
            totalAmount += order.getQuantity() * order.getPrice();
        }
        txtPaymentAmount.setText("Total: £" + totalAmount);
        lvCheckoutItems.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, checkoutList));
        btnPayWithPayPal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                makePayment();
            }
        });
    }

    /**
     * Call PaymentActivity of PayPal SDK
     */
    public void makePayment() {
        PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(totalAmount), "GBP", txtPaymentCompany.getText().toString(),
                PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(PaypalOrderPayment.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    /**
     * Result from PayPalActivity class
     * @param requestCode return code
     * @param resultCode status code
     * @param data data coming from called activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Payment done succesfully ", Toast.LENGTH_LONG).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Payment Canceled , Try again ", Toast.LENGTH_LONG).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(getApplicationContext(), "Payment failed , Try again ", Toast.LENGTH_LONG).show();
            }
        }
    }

}
