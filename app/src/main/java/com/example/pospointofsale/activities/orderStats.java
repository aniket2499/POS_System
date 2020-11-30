package com.example.pospointofsale.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pospointofsale.R;
import com.example.pospointofsale.adapter.order_stats;
import com.example.pospointofsale.objects.sold_product;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class orderStats extends AppCompatActivity {

    private RecyclerView recyclerView;
    private order_stats os ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_stats);
        recyclerView = findViewById(R.id.my_recycler);
        String doc = getIntent().getStringExtra("doc").trim();
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        CollectionReference cr = fb.collection("companies").document(doc).collection("sold_products");
        Toast.makeText(this, cr.toString(), Toast.LENGTH_SHORT).show();
        Query query = cr.orderBy("quantity", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<sold_product> options = new FirestoreRecyclerOptions.Builder<sold_product>()
                .setQuery(query, sold_product.class)
                .build();
        os = new order_stats(options);
        recyclerView.setLayoutManager(new LinearLayoutManager(orderStats.this));
        recyclerView.setAdapter(os);
    }
    @Override
    protected void onStart() {
        super.onStart();
        os.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        os.stopListening();
    }
}
