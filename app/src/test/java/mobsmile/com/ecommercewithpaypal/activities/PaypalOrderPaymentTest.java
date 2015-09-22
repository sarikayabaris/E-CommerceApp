package mobsmile.com.ecommercewithpaypal.activities;

import android.content.Intent;
import android.os.Build;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import java.math.BigDecimal;

import mobsmile.com.ecommercewithpaypal.BuildConfig;
import mobsmile.com.ecommercewithpaypal.R;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Baris on 03.9.2015.
 * PaypalOrderPaymentTest test class with Robolectric
 *
 * @author Baris Sarikaya
 * @version 1.0
 */
@Config(constants = BuildConfig.class, manifest = "./src/main/AndroidManifest.xml", sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class PaypalOrderPaymentTest {

    private ActivityController<PaypalOrderPayment> controller;
    private PaypalOrderPayment activity;
    private Button btnPayWithPayPal;
    private TextView txtPaymentCompany;
    private ListView lvCheckoutItems;
    private TextView txtPaymentAmount;
    private TextView txtPaymentTitle;

    /**
     * Useful to do setup for objects that are needed in the test
     */
    @Before
    public void setup() {
        controller = Robolectric.buildActivity(PaypalOrderPayment.class).create().start();
        activity = Robolectric.setupActivity(PaypalOrderPayment.class);
        btnPayWithPayPal = (Button) activity.findViewById(R.id.btnPayWithPayPal);
        lvCheckoutItems = (ListView) activity.findViewById(R.id.lvCheckoutItems);
        txtPaymentCompany = (TextView) activity.findViewById(R.id.txtPaymentCompany);
        txtPaymentAmount = (TextView) activity.findViewById(R.id.txtPaymentAmount);
        txtPaymentTitle = (TextView) activity.findViewById(R.id.txtPaymentTitle);
    }

    /**
     * The test simply checks that our views exist.
     */
    @Test
    public void shouldNotBeNull() {
        assertNotNull("PayPal button could not be found", btnPayWithPayPal);
        assertNotNull("Checkout items listview could not be found", lvCheckoutItems);
        assertNotNull("Company textview could not be found", txtPaymentCompany);
        assertNotNull("Payment title textview could not be found", txtPaymentTitle);
        assertNotNull("Payment amount textview could not be found", txtPaymentAmount);
    }

    /**
     * Checks if activity is null or not
     *
     * @throws Exception is null
     */
    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(activity);
    }

    /**
     * The test simply checks that our Textview has the text "Payment Details"
     */
    @Test
    public void validatePaymentTitleContent() {
        assertTrue("Textview contains incorrect title",
                "Payment Details".equals(txtPaymentTitle.getText().toString()));
    }

    /**
     * The test simply checks that our Button has the text "Add"
     */
    @Test
    public void validatePayWithPayPalButtonContent() {
        assertTrue("Button contains incorrect title",
                "Pay with PayPal".equals(btnPayWithPayPal.getText().toString()));
    }

    /**
     * The test simply checks that PaypalOrderPayment activity called
     */
    @Test
    public void isPaypalOrderPaymentActivityStartedOnClick() {
        activity.findViewById(R.id.btnPayWithPayPal).performClick();

        // The intent we expect to be launched when a user clicks on the button
        Intent expectedIntent = new Intent(activity, PayPalActivity.class);
        PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(0.0), "GBP", txtPaymentCompany.getText().toString(),
                PayPalPayment.PAYMENT_INTENT_SALE);
        expectedIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PaypalOrderPayment.paypalConfig);
        expectedIntent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        /**
         * An Android "Activity" doesn't expose a way to find out about activities it launches
         * Robolectric's "ShadowActivity" keeps track of all launched activities and exposes this information
         * through the "getNextStartedActivity" method.
         */
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        /**
         * Determine if two intents are the same for the purposes of intent resolution (filtering).
         * That is, if their action, data, type, class, and categories are the same. This does
         * not compare any extra data included in the intents
         */
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    // Activity creation that allows intent extras to be passed in
    private void createWithIntent(String extra) {
        Intent intent = new Intent(RuntimeEnvironment.application, PaypalOrderPayment.class);
        intent.putExtra("activity_extra", extra);
        activity = controller
                .withIntent(intent)
                .create()
                .start()
                .resume()
                .visible()
                .get();
    }

    // Test that simulates the full lifecycle of an activity
    @Test
    public void createsAndDestroysActivity() {
        createWithIntent("my extra_value");
    }

    @After
    public void tearDown() {
        // Destroy activity after every test
        controller
                .pause()
                .stop()
                .destroy();
    }
}
