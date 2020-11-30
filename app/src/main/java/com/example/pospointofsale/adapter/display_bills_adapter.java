package com.example.pospointofsale.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pospointofsale.R;
import com.example.pospointofsale.activities.Display_bills;
import com.example.pospointofsale.objects.bill_display;
import com.example.pospointofsale.objects.bill_items;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

public class display_bills_adapter extends FirestoreRecyclerAdapter<bill_display, display_bills_adapter.itemsHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public display_bills_adapter(@NonNull FirestoreRecyclerOptions<bill_display> options) {
        super(options);
    }

    @NonNull
    @Override
    public display_bills_adapter.itemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bills,parent,false);
        return new display_bills_adapter.itemsHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull itemsHolder holder, int position, @NonNull bill_display model) {
        String name_c;
        String name;
        name = Strings.repeat(" ",10);
        name_c = model.getName();
        String mobile = model.getMobile();
        String date = model.getDate();
        holder.textView.setText("name :" + name_c);
        holder.cust_mobile.setText("mobile :" + mobile);
        holder.cust_date.setText("date :" + date);
        List<bill_items> li = new ArrayList<>();
        li = model.getLi();
        String b_i = "items                         quantity     price \n \n";
        int total_price = 0 ;
        for (bill_items bi : li){
            String bill_name = bi.getName();
            int b_name_length = bill_name.length();
            int l = 20 - b_name_length;
            String spaces = Strings.repeat("  ",l);
            bill_name = bill_name + spaces;
            String bill_quantity = bi.getQuantity();
            String bill_price = bi.getPrice();
            int price = Integer.parseInt(bill_price);
            int quantity = Integer.parseInt(bill_quantity);
            String bill_total_price = Integer.toString(price * quantity);
            String bill_all = bill_name + bill_quantity + "      "+ bill_total_price + "\n";
            b_i = b_i + bill_all;
            total_price = total_price + (quantity*price);
        }
        holder.textView1.setText(b_i);
        holder.cust_total.setText("total : Rs." + total_price);
    }

    public static class itemsHolder extends RecyclerView.ViewHolder{
        private static LinearLayout li;
        private static TextView textView;
        private static TextView textView1;
        private static TextView cust_mobile;
        private static TextView cust_date;
        private static TextView cust_total;
        public itemsHolder(@NonNull View itemView) {
            super(itemView);
            li = (LinearLayout) itemView.findViewById(R.id.dynamic);
            textView1 = itemView.findViewById(R.id.textView);
            textView = itemView.findViewById(R.id.customer_name);
            cust_mobile = itemView.findViewById(R.id.customer_mobile);
            cust_date = itemView.findViewById(R.id.customer_date);
            cust_total = itemView.findViewById(R.id.total);
        }
    }
}
