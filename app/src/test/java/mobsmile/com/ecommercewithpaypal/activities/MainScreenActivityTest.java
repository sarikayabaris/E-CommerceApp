package mobsmile.com.ecommercewithpaypal.activities;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import junit.framework.Assert;

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

import java.util.ArrayList;
import java.util.List;

import mobsmile.com.ecommercewithpaypal.BuildConfig;
import mobsmile.com.ecommercewithpaypal.R;
import mobsmile.com.ecommercewithpaypal.adapter.OrderAdapter;
import mobsmile.com.ecommercewithpaypal.entity.Order;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Baris on 03.9.2015.
 * MainScreenActivity test class with Robolectric
 *
 * @author Baris Sarikaya
 * @version 1.0
 */
@Config(constants = BuildConfig.class, manifest = "./src/main/AndroidManifest.xml", sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class MainScreenActivityTest {

    private ActivityController<MainScreenActivity> controller;
    private MainScreenActivity activity;
    private Button checkoutButton;
    private ListView listView;
    private ImageView imgOrderImage;
    private TextView txtOrderTitle;
    private TextView txtOrderInfo;
    private TextView txtOrderPrice;
    private EditText edtOrderQuantity;
    private Button btnItemAdd;

    /**
     * Useful to do setup for objects that are needed in the test
     */
    @Before
    public void setup() {
        controller = Robolectric.buildActivity(MainScreenActivity.class).create().start();
        activity = Robolectric.setupActivity(MainScreenActivity.class);
        checkoutButton = (Button) activity.findViewById(R.id.btnCheckout);
        listView = (ListView) activity.findViewById(R.id.lvOrders);
        List<Order> lv = new ArrayList<>();
        //lv.add(new Order(1, "New Test Item 1", "New test item details added.", 2, 0));
        //lv.add(new Order(2, "New Test Item 2", "New test item details added.", 5, 0));
        OrderAdapter adapter = new OrderAdapter(activity, R.layout.order_row, lv);
        listView.setAdapter(adapter);
        View customListView = View.inflate(activity, R.layout.order_row, null);
        imgOrderImage = (ImageView) customListView.findViewById(R.id.imgOrderImage);
        txtOrderTitle = (TextView) customListView.findViewById(R.id.txtOrderTitle);
        txtOrderInfo = (TextView) customListView.findViewById(R.id.txtOrderInfo);
        txtOrderPrice = (TextView) customListView.findViewById(R.id.txtOrderPrice);
        edtOrderQuantity = (EditText) customListView.findViewById(R.id.edtOrderQuantity);
        btnItemAdd = (Button) customListView.findViewById(R.id.btnItemAdd);
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
     * The test simply checks that our views exist.
     */
    @Test
    public void shouldNotBeNull() {
        assertNotNull("Checkout button could not be found", checkoutButton);
        assertNotNull("Order listview could not be found", listView);
        assertNotNull("Item imageview could not be found", imgOrderImage);
        assertNotNull("Item title textview could not be found", txtOrderTitle);
        assertNotNull("Item info textview could not be found", txtOrderInfo);
        assertNotNull("Item price textview could not be found", txtOrderPrice);
        assertNotNull("Item quantity edittext could not be found", edtOrderQuantity);
        assertNotNull("Item add button could not be found", btnItemAdd);
    }

    /**
     * The test simply checks that our Button has the text "Checkout"
     */
    @Test
    public void validateCheckoutButtonAndContent() {
        assertTrue("Button contains incorrect title",
                "Checkout".equals(checkoutButton.getText().toString()));
    }


    /**
     * The test simply checks that our Button has the text "Add"
     */
    @Test
    public void validateAddItemButtonAndContent() {
        assertTrue("Button contains incorrect title",
                "Add".equals(btnItemAdd.getText().toString()));
    }

    @Test
    public void validateOrderItemInListview() {
        //View listItemView = adapter.getView(0, null, null);
        //assertSame(customListView, listItemView);
    }

    /**
     * The test simply checks that PaypalOrderPayment activity called
     */
    @Test
    public void isPaypalOrderPaymentActivityStartedOnClick() {
        activity.findViewById(R.id.btnCheckout).performClick();

        // The intent we expect to be launched when a user clicks on the button
        Intent expectedIntent = new Intent(activity, PaypalOrderPayment.class);

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
        Intent intent = new Intent(RuntimeEnvironment.application, MainScreenActivity.class);
        intent.putExtra("activity_extra", extra);
        activity = controller
                .withIntent(intent)
                .create()
                .start()
                .resume()
                .visible()
                .get();
    }

    @Test
    public void newInstanceNoDataSetTest() {
        //Initialize a new adapter and test if all the needed fields are created
        OrderAdapter adapterNoDataSet = new OrderAdapter(activity, R.layout.order_row, new ArrayList<Order>());

        Assert.assertNotNull(adapterNoDataSet);

        //Check to see if the count of the adapter is 0 (no items have been set)
        Assert.assertEquals(adapterNoDataSet.getCount(), 0);
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
