package com.example.pospointofsale.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pospointofsale.R;
import com.example.pospointofsale.adapter.Subcatagoryadapter;
import com.example.pospointofsale.objects.CatItem;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class home_new extends AppCompatActivity {

    private FirebaseFirestore fb ;
    private CollectionReference cr ;
    private Subcatagoryadapter subcatagoryadapter;
    RecyclerView recyclerView;
    TextView add;
    String email;
    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new);
        recyclerView = findViewById(R.id.recycler_view2);
        add = findViewById(R.id.add_new);
        bnv= (BottomNavigationView)findViewById(R.id.Bnv);

        // String email = getIntent().getStringExtra("doc_name");
        //   recyclerView.setLayoutManager(new GridLayoutManager(sub_cat.this,3));
        // GridLayoutManager gridLayoutManager = new GridLayoutManager(sub_cat.this,3);
        email = getIntent().getStringExtra("doc_name").trim();
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
        fb= FirebaseFirestore.getInstance();
        cr = fb.collection("companies").document(email).collection("catagories");
        Query query = cr.orderBy("catname",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<CatItem> options = new FirestoreRecyclerOptions.Builder<CatItem>()
                .setQuery(query,CatItem.class)
                .build();
        //FirestoreRecyclerOptions<CatItem> options = new FirestoreRecyclerOptions.Builder<CatItem>().setQuery(cr,CatItem.class).build();
        subcatagoryadapter = new Subcatagoryadapter(options);
        recyclerView.setLayoutManager(new GridLayoutManager(home_new.this,3));
        //recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(subcatagoryadapter);
        subcatagoryadapter.setOnItmeClickListener(new Subcatagoryadapter.onItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String path = documentSnapshot.getReference().getPath();
                String username = getIntent().getStringExtra("username").trim();
                Intent i = new Intent(home_new.this,sub_cat.class);
                i.putExtra("refrence",path);
                i.putExtra("username",username);
                startActivity(i);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = getIntent().getStringExtra("doc_name");
                String username = getIntent().getStringExtra("username").trim();
                Intent i = new Intent(home_new.this,add_cat_subcat.class);
                i.putExtra("document",email);
                i.putExtra("username",username);
                startActivity(i);
            }
        });
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.order: {
                    //    email = getIntent().getStringExtra("doc_name").trim();
                    //    fb = FirebaseFirestore.getInstance();
                        call_orders();
                        break;
                    }

                    case R.id.setting: {
                        fb.collection("companies").document(email)
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String Company_name = documentSnapshot.getString("company_name");
                                //    Toast.makeText(ordersActivity.this, Company_name, Toast.LENGTH_SHORT).show();

                                String username = getIntent().getStringExtra("username");
                                Intent i = new Intent(home_new.this, settings.class);
                                i.putExtra("company_name", Company_name);
                                i.putExtra("doc", email);
                                i.putExtra("username", username);
                                startActivity(i);
                            }
                        });
                        break;
                    }
                    case R.id.states:{
                        Intent intent1 = new Intent(home_new.this,orderStats.class);
                        intent1.putExtra("doc",email);
                        startActivity(intent1);
                        break;
                    }

                    case R.id.customers:{
                        Intent intent1 = new Intent(home_new.this,customers.class);
                        intent1.putExtra("doc",email);
                        startActivity(intent1);
                        break;
                    }

                    case R.id.bills:{
                        Intent intent = new Intent(home_new.this , Display_bills.class);
                        intent.putExtra("doc",email);
                        startActivity(intent);
                        break;
                    }
                }
                return true;
            }
        });
    }

    void call_orders(){

        String email = getIntent().getStringExtra("doc_name").trim();
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
        fb.collection("companies").document(email)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        String Company_name = documentSnapshot.get("company_name").toString();
                           Toast.makeText(home_new.this, Company_name, Toast.LENGTH_SHORT).show();

                        String username = getIntent().getStringExtra("username");
                        Intent i = new Intent(home_new.this, ordersActivity.class);
                        i.putExtra("company_name", Company_name);
                        i.putExtra("doc", email);
                        i.putExtra("username", username);
                        startActivity(i);
                    } else {
                        Toast.makeText(home_new.this, "document does not exist", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(home_new.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
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