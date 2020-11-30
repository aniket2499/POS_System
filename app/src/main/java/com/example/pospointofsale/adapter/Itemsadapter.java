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
import com.example.pospointofsale.objects.Item_details;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class Itemsadapter extends FirestoreRecyclerAdapter<Item_details,Itemsadapter.itemsholder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    private  onItemClickListener listener;
    private ondeleteClickListener dlistener;

    @Override
    protected void onBindViewHolder(@NonNull itemsholder holder, int position, @NonNull Item_details model) {
        holder.itemname.setText(model.getMitemname());
        holder.quantity.setText(model.getMitemquantity());
        holder.price.setText(model.getMitemprice());
        String imagURL = model.getMitemimage();
        Glide.with(holder.itemView.getContext())
                .load(imagURL)
                .centerCrop()
                .placeholder(R.drawable.image)
                .into(holder.thumbnail);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    public void setOnDeleteClickListener(ondeleteClickListener ondeleteClickListener) {
        dlistener = ondeleteClickListener;
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
            item_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && dlistener != null){
                        dlistener.onDeleteClick(getSnapshots().getSnapshot(position),position);
                        deleteItem(position);
                    }
                }
            });
        }
    }
    public interface onItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot,int position);
    }

    public interface ondeleteClickListener{
        void onDeleteClick(DocumentSnapshot documentSnapshot , int position);
    }

    public  void setOnItemClickListener(onItemClickListener listener){
        this.listener= listener;
    }


    public  Itemsadapter(@NonNull FirestoreRecyclerOptions options){ super(options); }

    @NonNull
    @Override
    public  itemsholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itme_list,parent,false);
        return new itemsholder(v);
    }
}
