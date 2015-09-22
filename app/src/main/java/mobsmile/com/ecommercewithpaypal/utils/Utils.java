package mobsmile.com.ecommercewithpaypal.utils;

import java.util.ArrayList;
import java.util.List;

import mobsmile.com.ecommercewithpaypal.entity.Order;

/**
 * Created by Baris on 04.9.2015.
 * Utils class for constants and static methods
 * @author Baris Sarikaya
 * @version 1.0
 */
public class Utils {

    public static final String COMPANY_MAIL = "payment@mobsmile.co.uk";
    public static final String SAND_BOX_ID = "APP-PUT_YOUR_ID";
    public static final String PAYPAL_SDK_ID = "PUT_YOUR_PAYPAL_SDK_ID";
    public static List<Order> checkoutItems = new ArrayList<>();


    public static void addCheckoutItem(Order order) {
        if (!checkoutItems.contains(order)) {
            checkoutItems.add(order);
        } else {
            Integer id = findOrderinList(order);
            if (id != null) {
                Order orderExist = checkoutItems.get(id);
                orderExist.setQuantity(orderExist.getQuantity() + order.getQuantity());
            }
        }
    }

    public static List<Order> getCheckoutItems() {
        return checkoutItems;
    }

    private static Integer findOrderinList(Order order) {
        for (Order o : checkoutItems) {
            if (o.getId().equals(order.getId())) {
                return o.getId();
            }
        }
        return null;
    }
}
