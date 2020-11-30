package com.example.pospointofsale.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pospointofsale.R;
import com.example.pospointofsale.adapter.Itemsadapter;
import com.example.pospointofsale.objects.Item_details;
import com.example.pospointofsale.objects.Order_items;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class items_list extends AppCompatActivity {
    private FirebaseFirestore fb;
    private CollectionReference cr;
    private Itemsadapter itemsadapter;
    RecyclerView recyclerView;
    TextView add;
    TextView print;
    TextView home;
    String[] array_of_string;   //used for getting document id for the refrence

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        recyclerView = findViewById(R.id.my_recycler_view);
        add = (TextView)findViewById(R.id.add_new);
        home = findViewById(R.id.home);
        //recyclerView = findViewById(R.id.recycler_view);
        String path = getIntent().getStringExtra("refrence");
       // print.setText(path);
        String[] a = path.split("/",6);
        fb= FirebaseFirestore.getInstance();
        cr = fb.collection(a[0]).document(a[1]).collection(a[2]).document(a[3]).collection(a[4]).document(a[5]).collection("items_details");
        Query query = cr.orderBy("mitemname",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Item_details> options = new FirestoreRecyclerOptions.Builder<Item_details>()
                .setQuery(query,Item_details.class)
                .build();
        itemsadapter = new Itemsadapter(options);
        recyclerView.setLayoutManager(new LinearLayoutManager(items_list.this));
        recyclerView.setAdapter(itemsadapter);
        itemsadapter.setOnItemClickListener(new Itemsadapter.onItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String path = documentSnapshot.getReference().getPath();
                String id = documentSnapshot.getId();
                String username = getIntent().getStringExtra("username").trim();
                Item_details i1 = documentSnapshot.toObject(Item_details.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item",i1);
                Intent i = new Intent(items_list.this,item_details.class);
                i.putExtra("refrence",path);
                i.putExtra("id_of_document",id);
                i.putExtra("username",username);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        itemsadapter.setOnDeleteClickListener(new Itemsadapter.ondeleteClickListener() {
            @Override
            public void onDeleteClick(DocumentSnapshot documentSnapshot, int position) {
                Item_details id = documentSnapshot.toObject(Item_details.class);
                String bar_code = id.getMitembarcode();
                String username = getIntent().getStringExtra("username").trim();
                Toast.makeText(items_list.this, "tested", Toast.LENGTH_SHORT).show();
                FirebaseDatabase fd = FirebaseDatabase.getInstance();
                DatabaseReference dr = fd.getReference().child("users").child(username).child("itemdetails").child(bar_code);
                dr.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(items_list.this, "deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                documentSnapshot.getReference().delete();
                //update(doc);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = getIntent().getStringExtra("username").trim();
                Intent i = new Intent(items_list.this , Add_items.class);
                i.putExtra("refrence" , path);
                i.putExtra("username",username);
                startActivity(i);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = getIntent().getStringExtra("username").trim();
                Intent i = new Intent(items_list.this , home_new.class);
                i.putExtra("doc_name",a[1]);
                i.putExtra("username",username);
                startActivity(i);
            }
        });
        //print.setText(a);
        //cr = fb.collection();
       // Query query = cr.orderBy("itmes_name",Query.Direction.ASCENDING);
    }
    public void update(String doc){
        fb = FirebaseFirestore.getInstance();
        cr = fb.collection("companies").document(doc).collection("orders");
        cr.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                int price = 0 ;
                int quantity = 0;
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Order_items oi = document.toObject(Order_items.class);
                        String Str_price = oi.getMitemprice();
                        String new_quantity = oi.getMitemnewquantity();
                        price = price + Integer.parseInt(Str_price)*Integer.parseInt(new_quantity);
                        quantity = quantity + Integer.parseInt(new_quantity);
                    }
                    String p = Integer.toString(price);
                    String q = Integer.toString(quantity);
                 //   textView.setText("total price is :" + "Rs" + p  );
                 //   textView2.setText("total quantity is :" + q);
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        itemsadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        itemsadapter.stopListening();
    }
}