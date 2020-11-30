package com.example.pospointofsale.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.pospointofsale.R;
import com.example.pospointofsale.objects.Item_details;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class item_details extends AppCompatActivity {
    public ImageView mimage;
    public EditText name;
    public EditText price;
    public EditText quantity;
    public EditText barcoade;
    public EditText measurement_unit;
    public FirebaseFirestore fb;
    public DocumentReference dr;
    public TextView print;
    TextView save_btn;
    TextView home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        home = findViewById(R.id.btn_home);
        mimage = findViewById(R.id.img_icon);
        name = findViewById(R.id.txt_name);
        price = findViewById(R.id.txt_price);
        quantity = findViewById(R.id.txt_quantity);
        barcoade = findViewById(R.id.txt_barcode);
        measurement_unit = findViewById(R.id.txt_munit);
        save_btn = findViewById(R.id.btn_save);
        Item_details i1 =(Item_details)getIntent().getExtras().getSerializable("item");
        name.setText(i1.getMitemname());
        price.setText(i1.getMitemprice());
        quantity.setText(i1.getMitemquantity());
        barcoade.setText(i1.getMitembarcode());
        measurement_unit.setText(i1.getMitemunit());
        String imageURL = i1.getMitemimage();
        Glide.with(item_details.this)
                .load(imageURL)
                .centerCrop()
                .into(mimage);
        String path = getIntent().getStringExtra("refrence");
        String doc_id = getIntent().getStringExtra("id_of_document");
        String[] a = path.split("/",8);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fb = FirebaseFirestore.getInstance();

                String mname = name.getText().toString();
                String mprice = price.getText().toString();
                String mquantity = quantity.getText().toString();
                String mbarcode = barcoade.getText().toString();
                String munit = measurement_unit.getText().toString();
                Item_details i2 = new Item_details();
                i2.setMitemname(mname);
                i2.setMitemprice(mprice);
                i2.setMitembarcode(mbarcode);
                i2.setMitemunit(munit);
                i2.setMitemquantity(mquantity);
                String path = getIntent().getStringExtra("refrence");
                String[] a = path.split("/",8);
                fb.collection(a[0]).document(a[1]).collection(a[2]).document(a[3]).collection(a[4]).document(a[5]).collection(a[6]).document(a[7])
                .update("mitemquantity",mquantity).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(item_details.this, "saved successfuly", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(item_details.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
                String username = getIntent().getStringExtra("username");
                FirebaseDatabase fd = FirebaseDatabase.getInstance();
                DatabaseReference dr = fd.getReference().child("users").child(username).child("itemdetails").child(mbarcode).child("quantity");
                dr.setValue(mquantity);
                fb.collection(a[0]).document(a[1]).collection(a[2]).document(a[3]).collection(a[4]).document(a[5]).collection(a[6]).document(a[7])
                        .update("mitemname",mname).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(item_details.this, "saved successfuly", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(item_details.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
                dr = fd.getReference().child("users").child(username).child("itemdetails").child(mbarcode).child("name");
                dr.setValue(mname);
                fb.collection(a[0]).document(a[1]).collection(a[2]).document(a[3]).collection(a[4]).document(a[5]).collection(a[6]).document(a[7])
                        .update("mitemunit",munit).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(item_details.this, "saved successfuly", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(item_details.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
                dr = fd.getReference().child("users").child(username).child("itemdetails").child(mbarcode).child("quantity");
                dr.setValue(mquantity);
                fb.collection(a[0]).document(a[1]).collection(a[2]).document(a[3]).collection(a[4]).document(a[5]).collection(a[6]).document(a[7])
                        .update("mitembarcode",mbarcode).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(item_details.this, "saved successfuly", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(item_details.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
                fb.collection(a[0]).document(a[1]).collection(a[2]).document(a[3]).collection(a[4]).document(a[5]).collection(a[6]).document(a[7])
                        .update("mitemprice",mprice).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(item_details.this, "saved successfuly", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(item_details.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
                dr = fd.getReference().child("users").child(username).child("itemdetails").child(mbarcode).child("price");
                dr.setValue(mprice);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = getIntent().getStringExtra("refrence");
                String username = getIntent().getStringExtra("username");
                String[] a = path.split("/",8);
                Intent i = new Intent(item_details.this , home_new.class);
                i.putExtra("doc_name",a[1]);
                i.putExtra("username",username);
                startActivity(i);
            }
        });
    }
}

