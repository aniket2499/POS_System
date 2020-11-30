package com.example.pospointofsale.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pospointofsale.R;
import com.example.pospointofsale.objects.CatItem;

import java.util.List;

public class CategoryAdp extends RecyclerView.Adapter<CategoryAdp.MyViewHolder> {
    public static Object RecyclerTouchListener;
    private Context mContext;
    private   List<CatItem> dataholder;
    private RecyclerTouchListener listener;
    public ImageView mDeleteImage;

    public void setCatagorylistmodel(List<CatItem> dataholder){
        this.dataholder = dataholder;
    }

    public interface RecyclerTouchListener {
        public void onClickItem(String titel);

        public void onLongClickItem(View v, int position);

        void onDeleteClick(int position);
    }

    public interface OnItemClickListener{                 //for deleting the catagory
        void onDeleteClick(int position);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title;
        public ImageView thumbnail;
        public ImageView overflow;
        public ImageView mDeleteImage;

        public MyViewHolder(View view ) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_title);
            thumbnail = view.findViewById(R.id.imageView);
            mDeleteImage = view.findViewById(R.id.image_delete);
            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null) {
                        int position = getAdapterPosition();            //for deleting the catagory
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onDeleteClick(position);
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            CatItem catItem = new CatItem();
            String Id = dataholder.get(getAdapterPosition()).getId();
        }
    }
    //Context mContext
    //final RecyclerTouchListener listener
    public CategoryAdp(Context mContext, List<CatItem> dataholder,final RecyclerTouchListener listener) {
        this.mContext = mContext;
        this.dataholder = dataholder;
        this.listener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final CatItem category = dataholder.get(position);
        holder.title.setText(category.getCatname() + "(" + category.getCount() + ")");
        String imagURL = category.getCatimg();

        Glide.with(holder.itemView.getContext())
                .load(imagURL)
                .centerCrop()
                .placeholder(R.drawable.image)
                .into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listener.onClickItem(category.getCatname());
                }catch (NullPointerException ignored){
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }
}