package mobsmile.com.ecommercewithpaypal.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mobsmile.com.ecommercewithpaypal.R;
import mobsmile.com.ecommercewithpaypal.entity.Order;
import mobsmile.com.ecommercewithpaypal.utils.Utils;

/**
 * Created by Baris on 05.9.2015.
 * Custom Adapter for list of Order Entity
 *
 * @author Baris Sarikaya
 * @version 1.0
 */
public class OrderAdapter extends ArrayAdapter<Order> {

    private List<Order> items;
    private Activity activity;

    public OrderAdapter(Activity act, int resource, List<Order> arrayList) {
        super(act, resource, arrayList);
        this.activity = act;
        this.items = arrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.order_row, null);
            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if ((items == null) || ((position + 1) > items.size()))
            return view;

        final Order bean = items.get(position);
        holder.imgOrder = (ImageView) view.findViewById(R.id.imgOrderImage);
        holder.title = (TextView) view.findViewById(R.id.txtOrderTitle);
        holder.details = (TextView) view.findViewById(R.id.txtOrderInfo);
        holder.price = (TextView) view.findViewById(R.id.txtOrderPrice);
        holder.quantity = (EditText) view.findViewById(R.id.edtOrderQuantity);
        holder.addItem = (Button) view.findViewById(R.id.btnItemAdd);

        if (holder.title != null)
            holder.title.setText(bean.getTitle());
        if (holder.details != null) {
            holder.details.setText(bean.getDetail());
        }
        if (holder.price != null) {
            holder.price.setText("Price: " + bean.getPrice() + " GBP");
        }

        if (holder.quantity != null) {
            holder.quantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    //bean.setQuantity(Integer.parseInt(holder.quantity.getText().toString()));
                }
            });
        }
        if (holder.addItem != null) {
            holder.addItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.addCheckoutItem(new Order(bean.getId(), bean.getTitle(), bean.getDetail(), bean.getPrice(), (holder.quantity != null && !holder.quantity.getText().toString().equals("Quantity")) ? Integer.parseInt(holder.quantity.getText().toString()) : 0));
                }
            });
        }
        if ((position % 2) == 0) {
            view.setBackgroundResource(R.color.clrOrderRowEven);
        } else {
            view.setBackgroundResource(R.color.clrOrderRowOdd);
        }
        return view;
    }

    class ViewHolder {
        public ImageView imgOrder;
        public TextView title;
        public TextView details;
        public TextView price;
        public EditText quantity;
        public Button addItem;
    }
}
