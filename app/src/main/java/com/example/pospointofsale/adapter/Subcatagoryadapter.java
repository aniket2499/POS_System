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

public class Subcatagoryadapter extends FirestoreRecyclerAdapter<CatItem, Subcatagoryadapter.subcatholder> {
    private onItemClickListener listener;

    @Override
    protected void onBindViewHolder(subcatholder holder, int position, CatItem model) {
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

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    class subcatholder extends RecyclerView.ViewHolder{
        public TextView title;
        public ImageView thumbnail;
        public ImageView overflow;
        public ImageView mDeleteImage;

        public subcatholder(View itemView){
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
        void onItemClick(DocumentSnapshot documentSnapshot,int position);
    }
    public void setOnItmeClickListener(onItemClickListener listener){
        this.listener = listener;
    }

    public Subcatagoryadapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @NonNull
    @Override
    public subcatholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_cat_detais,parent,false);
        return new subcatholder(v);
    }
}
