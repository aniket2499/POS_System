package com.example.pospointofsale.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pospointofsale.R;
import com.example.pospointofsale.objects.Order_items;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class OrdersAdapter  extends FirestoreRecyclerAdapter<Order_items,OrdersAdapter.itemsholder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param
     */

    private onItemClickListener listener;
    private ondeleteClickListener dlistener;

    @Override
    protected void onBindViewHolder(@NonNull OrdersAdapter.itemsholder holder, int position, @NonNull Order_items model) {
        String ImageUrl = model.getImageURL();
        int price = Integer.parseInt(model.getMitemprice()) * Integer.parseInt(model.getMitemnewquantity());
        String p = Integer.toString(price);
        String p_final = ("Rs " + model.getMitemprice() + "X" + model.getMitemnewquantity() + "=" + " Rs " + p);
        holder.itemname.setText(model.getName());
        holder.quantity.setText("quantity : " + model.getMitemnewquantity());
        holder.price.setText(p_final);
     //   String imagURL = model.getMitemimage();
        Glide.with(holder.itemView.getContext())
                .load(ImageUrl)
                .centerCrop()
                .placeholder(R.drawable.image)
                .into(holder.thumbnail);
    }

    public void deleteItem(int position){
        DocumentReference df = getSnapshots().getSnapshot(position).getReference();
        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

            }
        });
    }

    class itemsholder extends RecyclerView.ViewHolder {
        public TextView itemname;
        public TextView price;
        public TextView quantity;
        public ImageView thumbnail;
        public ImageView item_del;


        public itemsholder(View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.txtTitle);
            price = itemView.findViewById(R.id.txt_price);
            quantity = itemView.findViewById(R.id.txt_quantity);
            thumbnail = itemView.findViewById(R.id.img_icon);
            item_del = itemView.findViewById(R.id.image_delete);

            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
            /*item_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null){
                        deleteItem(position);
                    }
                }
            });*/
            item_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && dlistener != null) {
                        dlistener.onDeleteClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }
    public interface onItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public interface ondeleteClickListener{
        void onDeleteClick(DocumentSnapshot documentSnapshot , int position);
    }

    public  void setOnItemClickListener(onItemClickListener listener){
        this.listener= listener;
    }

    public void setOnDeleteClickListener(ondeleteClickListener dlistener){
        this.dlistener = dlistener;
    }
    public  OrdersAdapter(@NonNull FirestoreRecyclerOptions options){ super(options); }

    @NonNull
    @Override
    public OrdersAdapter.itemsholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itme_list,parent,false);
        return new OrdersAdapter.itemsholder(v);
    }
}
