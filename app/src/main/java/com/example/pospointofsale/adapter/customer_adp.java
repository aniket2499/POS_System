package com.example.pospointofsale.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pospointofsale.R;
import com.example.pospointofsale.objects.customer;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class customer_adp extends FirestoreRecyclerAdapter<customer,customer_adp.itemsholder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public customer_adp(@NonNull FirestoreRecyclerOptions<customer> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull itemsholder holder, int position, @NonNull customer model) {
        holder.name.setText("name : " + model.getName());
        holder.price.setText("total purchase : " + Integer.toString(model.getPurchase()));
        holder.mobile.setText("mobile : " + model.getNumber());
    }



    public class itemsholder extends RecyclerView.ViewHolder {
        public TextView mobile;
        public TextView price;
        public TextView name;
        public itemsholder(@NonNull View itemView) {
            super(itemView);
            mobile = itemView.findViewById(R.id.mobile);
            price = itemView.findViewById(R.id.points);
            name = itemView.findViewById(R.id.cust_name);
        }
    }
    @NonNull
    @Override
    public customer_adp.itemsholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_details,parent,false);
        return new customer_adp.itemsholder(v);
    }
}
