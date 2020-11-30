package com.example.pospointofsale.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pospointofsale.R;
import com.example.pospointofsale.objects.sold_product;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class order_stats extends FirestoreRecyclerAdapter<sold_product,order_stats.itemsholder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public order_stats(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }




    @Override
    protected void onBindViewHolder(@NonNull itemsholder holder, int position, @NonNull sold_product model) {
        holder.itemname.setText("name : " + model.getName());
        holder.quantity.setText("quantity : " + model.getQuantity());
        holder.price.setText("total price : " + model.getTotal_price());
        holder.barcode.setText("barcode : " + model.getBarcode());
    }

    public class itemsholder extends RecyclerView.ViewHolder {
        public TextView itemname;
        public TextView price;
        public TextView quantity;
        public TextView barcode;


        public itemsholder(View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity_sold);
            barcode = itemView.findViewById(R.id.barcode);
        }

    }

    @NonNull
    @Override
    public order_stats.itemsholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stats_details,parent,false);
        return new order_stats.itemsholder(v);
    }
}
