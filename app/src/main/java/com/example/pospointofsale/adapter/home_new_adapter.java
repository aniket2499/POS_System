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
import com.example.pospointofsale.objects.CatItem;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class home_new_adapter extends FirestoreRecyclerAdapter<CatItem, home_new_adapter.catholder> {
    private Subcatagoryadapter.onItemClickListener listener;

    public home_new_adapter(@NonNull FirestoreRecyclerOptions<CatItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(catholder holder, int position, CatItem model) {
        holder.title.setText(model.getCatname());
        String imagURL = model.getCatimg();
        Glide.with(holder.itemView.getContext())
                .load(imagURL)
                .centerCrop()
                .placeholder(R.drawable.image)
                .into(holder.thumbnail);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    @NonNull
    @Override
    public catholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new catholder(v);
    }

    public class catholder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;
        public ImageView overflow;
        public ImageView mDeleteImage;
        public catholder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_title);
            thumbnail = itemView.findViewById(R.id.imageView);
            mDeleteImage = itemView.findViewById(R.id.image_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null){
                        deleteItem(position);
                    }
                }
            });
        }
    }

    public interface onItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItmeClickListener(Subcatagoryadapter.onItemClickListener listener){
        this.listener = listener;
    }
}
