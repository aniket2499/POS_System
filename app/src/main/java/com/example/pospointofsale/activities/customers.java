package com.example.pospointofsale.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pospointofsale.R;
import com.example.pospointofsale.adapter.customer_adp;
import com.example.pospointofsale.objects.customer;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class customers extends AppCompatActivity {
    RecyclerView recyclerView;
    customer_adp ca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);
        recyclerView = findViewById(R.id.my_recycler);
        String doc = getIntent().getStringExtra("doc").trim();
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        CollectionReference cr = fb.collection("companies").document(doc).collection("customer_details");
        Toast.makeText(this, cr.toString(), Toast.LENGTH_SHORT).show();
        Query query = cr.orderBy("purchase", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<customer> options = new FirestoreRecyclerOptions.Builder<customer>()
                .setQuery(query, customer.class)
                .build();
        ca = new customer_adp(options);
        recyclerView.setLayoutManager(new LinearLayoutManager(customers.this));
        recyclerView.setAdapter(ca);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ca.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ca.stopListening();
    }
}