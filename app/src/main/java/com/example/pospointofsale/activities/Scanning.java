package com.example.pospointofsale.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.pospointofsale.R;
import com.example.pospointofsale.objects.Order_items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Scanning extends AppCompatActivity {

    private TextView textView;
    private ProgressBar mProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);
        textView = findViewById(R.id.textView);
        ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.CAMERA} , PackageManager.PERMISSION_GRANTED);
        mProgressbar = findViewById(R.id.progress_circular);
        mProgressbar.setVisibility(View.GONE);
    }

    public void startscan(View view){
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        mProgressbar.setVisibility(View.VISIBLE);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        if(intentResult != null){
            if(intentResult.getContents() == null){
                textView.setText("Cancled");
            }
            else {
                textView.setText(intentResult.getContents());
                FirebaseDatabase fb = FirebaseDatabase.getInstance();
                String username = getIntent().getStringExtra("username").trim();
                DatabaseReference reference = firebaseDatabase.getReference().child("users").child(username).child("itemdetails").child(intentResult.getContents());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                   // String pass = user_password.getText().toString();
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       // mProgressbar.setVisibility(View.VISIBLE);
                        //String password = snapshot.child("password").getValue().toString();
                        //String email  = snapshot.child("email").getValue().toString().trim();
                        //Toast.makeText(Login.this, password, Toast.LENGTH_SHORT).show();
                        String path = snapshot.child("path").getValue().toString();
                        String name = snapshot.child("name").getValue().toString();
                        String price = snapshot.child("price").getValue().toString();
                        String quantity = snapshot.child("quantity").getValue().toString();
                        String soldquantity = "1";
                        String barcode = snapshot.child("barcode").getValue().toString();
                        String user =snapshot.child("user").getValue().toString();
                        String image_Url = snapshot.child("image").getValue().toString();
                        Order_items order_items = new Order_items(image_Url,soldquantity,quantity, price, path, name,barcode,user);
                    //    String doc = getIntent().getStringExtra("doc").trim();
                    //    Toast.makeText(Scanning.this, doc, Toast.LENGTH_SHORT).show();
                        FirebaseFirestore fb = FirebaseFirestore.getInstance();
                        String doc = getIntent().getStringExtra("doc").trim();
                        DocumentReference dr = fb.collection("companies").document(doc).collection("orders").document();
                        dr.set(order_items).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //Toast.makeText(Scanning.this, "sucess", Toast.LENGTH_SHORT).show();

                                String doc = getIntent().getStringExtra("doc").trim();
                                String com = getIntent().getStringExtra("company_name").trim();
                                String username = getIntent().getStringExtra("username").trim();
                                Intent i = new Intent(Scanning.this , ordersActivity.class);
                                i.putExtra("doc",doc);
                                i.putExtra("username",username);
                                i.putExtra("company_name",com);
                                startActivity(i);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Scanning.this, "failure", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}