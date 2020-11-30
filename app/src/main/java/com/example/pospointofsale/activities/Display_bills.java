package com.example.pospointofsale.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pospointofsale.R;
import com.example.pospointofsale.adapter.display_bills_adapter;
import com.example.pospointofsale.objects.bill_display;
import com.example.pospointofsale.objects.bill_items;
import com.example.pospointofsale.objects.customer;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class Display_bills extends AppCompatActivity {

    display_bills_adapter dba;
    public String str_date;
    public EditText date_ed;
    TextView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_bills);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.dynamic);
        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.recycler_view);
        search = findViewById(R.id.btn_search);
        date_ed = (EditText) findViewById(R.id.ed_name);

        //Toast.makeText(this, str_date, Toast.LENGTH_SHORT).show();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_date = date_ed.getText().toString();
                FirebaseFirestore fb = FirebaseFirestore.getInstance();
                String doc = getIntent().getStringExtra("doc").trim();
                CollectionReference cr = fb.collection("companies").document(doc).collection("bill");
                //Query query = cr.orderBy("mobile", Query.Direction.DESCENDING);
                Query query =cr.whereEqualTo("date" , str_date );
                FirestoreRecyclerOptions<bill_display> options = new FirestoreRecyclerOptions.Builder<bill_display>()
                        .setQuery(query, bill_display.class)
                        .build();
                dba = new display_bills_adapter(options);
                recyclerView.setLayoutManager(new LinearLayoutManager(Display_bills.this,LinearLayoutManager.HORIZONTAL,false));
                recyclerView.setAdapter(dba);
                Toast.makeText(Display_bills.this, str_date, Toast.LENGTH_SHORT).show();
                dba.startListening();
            }
        });
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        CollectionReference cr = fb.collection("companies").document("test4").collection("bill");
        //Query query = cr.orderBy("mobile", Query.Direction.DESCENDING);
        Query query =cr.whereEqualTo("date" , str_date );
        FirestoreRecyclerOptions<bill_display> options = new FirestoreRecyclerOptions.Builder<bill_display>()
                .setQuery(query, bill_display.class)
                .build();
        dba = new display_bills_adapter(options);
        recyclerView.setLayoutManager(new LinearLayoutManager(Display_bills.this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(dba);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dba.stopListening();
    }
}