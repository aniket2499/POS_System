package com.example.pospointofsale.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pospointofsale.R;
import com.example.pospointofsale.adapter.OrdersAdapter;
import com.example.pospointofsale.fragment.dialogbox;
import com.example.pospointofsale.objects.Order_items;
import com.example.pospointofsale.objects.sold_product;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ordersActivity extends AppCompatActivity {

    private FirebaseFirestore fb;
    private CollectionReference cr;
    private OrdersAdapter ordersAdapter;
    private RecyclerView recyclerView;
    TextView textView;
    TextView textView2;
    TextView add;
    TextView scan;
    TextView bill;

    TextView print;
    String[] array_of_string;   //used for getting document id for the refrence

    @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_orders);
            recyclerView = findViewById(R.id.my_recycler_view);
            add = (TextView) findViewById(R.id.add_new);
            bill = (TextView) findViewById(R.id.generate_bill);
            textView = findViewById(R.id.textView2);
            textView2 = findViewById(R.id.textView3);
            scan = (TextView) findViewById(R.id.scan);
            ActivityCompat.requestPermissions(ordersActivity.this,new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED );
            //recyclerView = findViewById(R.id.recycler_view);
           // String path = getIntent().getStringExtra("refrence");
            // print.setText(path);
            //String[] a = path.split("/", 6);
            String doc = getIntent().getStringExtra("doc").trim();
            fb = FirebaseFirestore.getInstance();
            cr = fb.collection("companies").document(doc).collection("orders");
            Query query = cr.orderBy("mitemprice", Query.Direction.ASCENDING);
            FirestoreRecyclerOptions<Order_items> options = new FirestoreRecyclerOptions.Builder<Order_items>()
                    .setQuery(query, Order_items.class)
                    .build();
            ordersAdapter = new OrdersAdapter(options);
            recyclerView.setLayoutManager(new LinearLayoutManager(ordersActivity.this));
            recyclerView.setAdapter(ordersAdapter);
            ordersAdapter.setOnItemClickListener(new OrdersAdapter.onItemClickListener() {
                @Override
                public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                    String path = documentSnapshot.getReference().getPath();
                //    String id = documentSnapshot.getId();
                //    Item_details i1 = documentSnapshot.toObject(Item_details.class);
                 //   Bundle bundle = new Bundle();
                 //   bundle.putSerializable("item", i1);
                 //   Intent i = new Intent(ordersActivity.this, item_details.class);
                //    i.putExtra("refrence", path);
                 //   i.putExtra("id_of_document", id);
                 //   i.putExtras(bundle);
                 //   startActivity(i);
                    String doc = getIntent().getStringExtra("doc");
                    Order_items oi = documentSnapshot.toObject(Order_items.class);
                    String quantity = oi.getMitemquantity();
                    dialogbox db = new dialogbox().newInstance(quantity,path,doc);
                    db.show(getSupportFragmentManager(),"example");
                    //update(doc);
                }
            });
            ordersAdapter.setOnDeleteClickListener(new OrdersAdapter.ondeleteClickListener() {
                @Override
                public void onDeleteClick(DocumentSnapshot documentSnapshot, int position) {
                    documentSnapshot.getReference().delete();
                    update(doc);
                }
            });
            update(doc);
            bill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username = getIntent().getStringExtra("username").trim();
                    String com = getIntent().getStringExtra("company_name").trim();
                    Intent i = new Intent(ordersActivity.this,bill.class);
                    i.putExtra("doc",doc);
                    i.putExtra("company_name",com);
                    i.putExtra("username",username);
                    startActivity(i);

                }
            });

            add.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.R)
                @Override
                public void onClick(View view) {

                    //    Intent i = new Intent(ordersActivity.this, Add_items.class);
                    //    i.putExtra("refrence", path);
                    //    startActivity(i);
                    //    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    String path = getExternalFilesDir(null).toString() + "/" + System.currentTimeMillis() + ".pdf";
                    //File dir = new File(path);
                    File file = new File(path);
                    if(!file.exists()) {
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    String doc = getIntent().getStringExtra("doc").trim();        //email
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("companies").document(doc).collection("orders")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @RequiresApi(api = Build.VERSION_CODES.R)
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    int price = 0 ;
                                 //   TextView textView;
                                 //   textView = findViewById(R.id.textView2);
                                    if (task.isSuccessful()) {
                                        List<Order_items> holder = new ArrayList<>();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Order_items oi = document.toObject(Order_items.class);
                                            holder.add(oi);
                                            String new_quantity = oi.getMitemnewquantity();
                                            String old_quantity = oi.getMitemquantity();
                                            String Str_price = oi.getMitemprice();
                                            String path = oi.getPath();
                                            String firebase_barcode = oi.getFirebase_barcode();
                                            String firebase_user = oi.getFirebase_user();
                                            String item_name = oi.getName();
                                            String remaining_quantity = Integer.toString(Integer.parseInt(old_quantity) - Integer.parseInt(new_quantity));
                                            String[] a = path.split("/", 8);
                                            FirebaseFirestore fb = FirebaseFirestore.getInstance();
                                            fb.collection(a[0]).document(a[1]).collection(a[2]).document(a[3]).collection(a[4]).document(a[5]).collection(a[6]).document(a[7])
                                                    .update("mitemquantity", remaining_quantity).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(ordersActivity.this, "saved successfuly", Toast.LENGTH_SHORT).show();

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(ordersActivity.this, "error", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                            price = price + Integer.parseInt(Str_price) * Integer.parseInt(new_quantity);
                                            //   Toast.makeText(ordersActivity.this, price, Toast.LENGTH_SHORT).show();
                                            FirebaseDatabase.getInstance().getReference().child("users").child(firebase_user).child("itemdetails").child(firebase_barcode).child("quantity").setValue(remaining_quantity);

                                            //FirebaseFirestore db = FirebaseFirestore.getInstance();
                                            String doc = getIntent().getStringExtra("doc").trim();
                                            final String[] testing = new String[3];
                                            testing[0] = new_quantity;
                                            testing[1] = item_name;
                                            testing[2] = firebase_barcode;
                                            int finalPrice = price;
                                            fb.collection("companies").document(doc).collection("sold_products").document(firebase_barcode).get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if(task.isSuccessful()){
                                                        DocumentSnapshot document = task.getResult();
                                                        if(document.exists()){
                                                            sold_product sp = document.toObject(sold_product.class);
                                                            int database_quantity = sp.getQuantity();
                                                            int current_quantity = Integer.parseInt(testing[0]);
                                                            int updated_quantity = database_quantity + current_quantity;
                                                            int database_price = sp.getTotal_price();
                                                            int updated_price = database_price + finalPrice;
                                                            DocumentReference dr = document.getReference();
                                                            dr.update("quantity",updated_quantity).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    Toast.makeText(ordersActivity.this, "updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                            dr.update("total_price",updated_price).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    Toast.makeText(ordersActivity.this, "updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }
                                                        else{
                                                            int in_quantity = Integer.parseInt(testing[0]);
                                                            sold_product sp = new sold_product(testing[1],in_quantity, finalPrice,firebase_barcode);
                                                            FirebaseFirestore.getInstance().collection("companies").document(doc).collection("sold_products").document(firebase_barcode).set(sp).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    Toast.makeText(ordersActivity.this, "successfully added", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                            Toast.makeText(ordersActivity.this, testing[0], Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                        String p = Integer.toString(price);
                                    }
                                }
                            });

                }
            });
            scan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username = getIntent().getStringExtra("username").trim();
                    String com = getIntent().getStringExtra("company_name").trim();
                    Intent i = new Intent(ordersActivity.this , Scanning.class);
                //    i.putExtra("username","test2");
                    i.putExtra("username",username);
                    i.putExtra("doc",doc);
                    i.putExtra("company_name",com);
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
                        textView.setText("total price is :" + "Rs" + p  );
                        textView2.setText("total quantity is :" + q);
                    }
                }
            });
        }
        @Override
        protected void onStart() {
            super.onStart();
            ordersAdapter.startListening();
        }

        @Override
        protected void onStop() {
            super.onStop();
            ordersAdapter.stopListening();
        }
}

