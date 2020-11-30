package com.example.pospointofsale.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pospointofsale.R;
import com.example.pospointofsale.adapter.Subcatagoryadapter;
import com.example.pospointofsale.objects.CatItem;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class sub_cat extends AppCompatActivity {

    private FirebaseFirestore fb ;
    private CollectionReference cr ;
    private Subcatagoryadapter subcatagoryadapter;
    RecyclerView recyclerView;
    TextView add;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sub_cat);
        recyclerView = findViewById(R.id.recycler_view2);
        add = findViewById(R.id.add_new);
        String path = getIntent().getStringExtra("refrence");
        // print.setText(path);
        String[] a = path.split("/",4);
    //   recyclerView.setLayoutManager(new GridLayoutManager(sub_cat.this,3));
       // GridLayoutManager gridLayoutManager = new GridLayoutManager(sub_cat.this,3);
        fb= FirebaseFirestore.getInstance();
        cr = fb.collection(a[0]).document(a[1]).collection(a[2]).document(a[3]).collection("subcatagory");
        Query query = cr.orderBy("catname",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<CatItem> options = new FirestoreRecyclerOptions.Builder<CatItem>()
                .setQuery(query,CatItem.class)
                .build();
        //FirestoreRecyclerOptions<CatItem> options = new FirestoreRecyclerOptions.Builder<CatItem>().setQuery(cr,CatItem.class).build();
        subcatagoryadapter = new Subcatagoryadapter(options);
        recyclerView.setLayoutManager(new GridLayoutManager(sub_cat.this,3));
        //recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(subcatagoryadapter);
        subcatagoryadapter.setOnItmeClickListener(new Subcatagoryadapter.onItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String path = documentSnapshot.getReference().getPath();
                String username = getIntent().getStringExtra("username").trim();
                Intent i = new Intent(sub_cat.this,items_list.class);
                i.putExtra("refrence",path);
                i.putExtra("username",username);
                startActivity(i);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = getIntent().getStringExtra("username").trim();
                Intent i = new Intent(sub_cat.this,add_new_subcat.class);
                i.putExtra("username",username);
                i.putExtra("refrence" , path);
                startActivity(i);
            }
        });
    }

    private void setUpRecyclerView() {
        Query query = cr.orderBy("catname",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<CatItem> options = new FirestoreRecyclerOptions.Builder<CatItem>()
                 .setQuery(query,CatItem.class)
                 .build();
        //FirestoreRecyclerOptions<CatItem> options = new FirestoreRecyclerOptions.Builder<CatItem>().setQuery(cr,CatItem.class).build();
       subcatagoryadapter = new Subcatagoryadapter(options);
        recyclerView.setLayoutManager(new GridLayoutManager(sub_cat.this,3));
        recyclerView.setAdapter(subcatagoryadapter);
    }




    @Override
    protected void onStart() {
        super.onStart();
        subcatagoryadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        subcatagoryadapter.stopListening();
    }
}