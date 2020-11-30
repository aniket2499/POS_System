package com.example.pospointofsale.activities;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pospointofsale.R;
import com.example.pospointofsale.objects.Order_items;
import com.example.pospointofsale.objects.bill_display;
import com.example.pospointofsale.objects.bill_items;
import com.example.pospointofsale.objects.customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class bill extends AppCompatActivity {
    TextView genrate_Bill;
    TextView name;
    TextView mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        genrate_Bill = findViewById(R.id.generate_bill);
        name = findViewById(R.id.cu_name);
        mobile = findViewById(R.id.cu_mobile);
        genrate_Bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String doc = getIntent().getStringExtra("doc").trim();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("companies").document(doc).collection("orders").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            String cu_name = name.getText().toString();
                            String cu_mobile = mobile.getText().toString();
                            long time = System.currentTimeMillis();
                            String file_name = Long.toString(time);
                            String path = getExternalFilesDir(null).toString() + "/" + file_name + ".pdf";

                            List<bill_items> li = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Order_items oi = document.toObject(Order_items.class);
                                String name = oi.getName();
                                String quantity = oi.getMitemnewquantity();
                                String price = oi.getMitemprice();
                                bill_items bi = new bill_items(name,price,quantity);
                                li.add(bi);
                            }

                            long millis=System.currentTimeMillis();
                            java.sql.Date date=new java.sql.Date(millis);
                            String date_eg = date.toString();

                            bill_display bd = new bill_display(cu_name,cu_mobile, date_eg,li);
                            FirebaseFirestore fb = FirebaseFirestore.getInstance();
                            fb.collection("companies").document(doc).collection("bill").document().set(bd).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(bill.this, "successfully added", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            Date time_used = new java.util.Date(System.currentTimeMillis());
                            String t_used = (new SimpleDateFormat("HH:mm:ss").format(time));

                            File file = new File(path);
                            if(!file.exists()) {
                                try {
                                    file.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            android.graphics.pdf.PdfDocument mypdfDocument = new android.graphics.pdf.PdfDocument();
                            Paint myPAint  = new Paint();
                            android.graphics.pdf.PdfDocument.PageInfo myPageInfo1 = new android.graphics.pdf.PdfDocument.PageInfo.Builder(1000,900,1).create();
                            android.graphics.pdf.PdfDocument.Page mypage1 = mypdfDocument.startPage(myPageInfo1);
                            //   Document document = new Document();
                            Canvas canvas = mypage1.getCanvas();
                            myPAint.setTextSize(80);
                            String com = getIntent().getStringExtra("company_name").trim();
                            canvas.drawText(com,30,80 , myPAint);

                            myPAint.setTextSize(30);
                            myPAint.setTextAlign(Paint.Align.RIGHT);
                            canvas.drawText("invoice no ",canvas.getWidth()-40 , 40 , myPAint);
                            canvas.drawText(file_name,canvas.getWidth()-40,80,myPAint);
                            myPAint.setTextAlign(Paint.Align.LEFT);

                            myPAint.setColor(Color.rgb(150,150,150));
                            canvas.drawRect(30,150,canvas.getWidth()-30,160,myPAint);

                            myPAint.setColor(Color.BLACK);
                            canvas.drawText("Date: ",50,200,myPAint);
                            canvas.drawText(date_eg,270,200,myPAint);

                            canvas.drawText("time: ",620,200,myPAint);
                            myPAint.setTextAlign(Paint.Align.RIGHT);
                            canvas.drawText(t_used,canvas.getWidth()-40,200,myPAint);
                            myPAint.setTextAlign(Paint.Align.LEFT);

                            canvas.drawText("customer name: ",50,250,myPAint);
                            canvas.drawText(cu_name,270,250,myPAint);

                            canvas.drawText("mobile: ",620,250,myPAint);
                            myPAint.setTextAlign(Paint.Align.RIGHT);
                            canvas.drawText(cu_mobile,canvas.getWidth()-40,250,myPAint);
                            myPAint.setTextAlign(Paint.Align.LEFT);

                            myPAint.setColor(Color.rgb(150,150,150));
                            canvas.drawRect(30,300,canvas.getWidth()-30,350,myPAint);

                            myPAint.setColor(Color.WHITE);
                            canvas.drawText("item" , 50 , 335,myPAint);
                            canvas.drawText("quantity" , 550 , 335,myPAint);
                            myPAint.setTextAlign(Paint.Align.RIGHT);
                            canvas.drawText("amount" , canvas.getWidth()-40 , 335,myPAint);
                            myPAint.setTextAlign(Paint.Align.LEFT);

                            myPAint.setColor(Color.BLACK);
                            int y = 380;
                            int total_amount = 0;
                            for(bill_items biex:li){

                                String ca_name = biex.getName();
                                canvas.drawText(ca_name,50 , y , myPAint);
                                //   canvas.drawText(biex.getPrice(),40 , y+10 , myPAint);
                                String ca_quantity = biex.getQuantity();
                                canvas.drawText(ca_quantity,580 , y , myPAint);
                                String p = biex.getPrice();
                                int i_price = Integer.parseInt(p);
                                int i_quantity = Integer.parseInt(ca_quantity);
                                int i_amount = i_price * i_quantity;
                                total_amount = total_amount + i_amount;
                                String amount = Integer.toString(i_amount);
                                canvas.drawText(amount,canvas.getWidth()-100 , y , myPAint);
                                y=y+40;
                            }
                            String s_totalamount = Integer.toString(total_amount);
                            myPAint.setColor(Color.rgb(150,150,150));
                            canvas.drawRect(50,y+30,canvas.getWidth()-30,y+20,myPAint);
                            myPAint.setColor(Color.BLACK);
                            canvas.drawText("total: " , 750 , y+60 , myPAint );
                            canvas.drawText(s_totalamount,canvas.getWidth()-100 , y+60 , myPAint);
                            mypdfDocument.finishPage(mypage1);
                            try {
                                mypdfDocument.writeTo(new FileOutputStream(file.getAbsoluteFile()));

                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(bill.this, "error", Toast.LENGTH_SHORT).show();
                                String TAG = null;
                                Log.d(TAG , "error");
                            }


                            Toast.makeText(bill.this, file_name + ".pdf has been generated", Toast.LENGTH_SHORT).show();

                            String doc = getIntent().getStringExtra("doc").trim();
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            DocumentReference dr = db.collection("companies").document(doc).collection("customer_details").document(cu_mobile);
                            customer c1 = new customer(cu_name,cu_mobile,total_amount);
                            int finalTotal_amount = total_amount;
                            dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot document = task.getResult();
                                    if(document.exists()){
                                        customer c1 = document.toObject(customer.class);
                                        int totalpurchase = c1.getPurchase() + finalTotal_amount;
                                        DocumentReference dr = document.getReference();
                                        dr.update("purchase",totalpurchase).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(bill.this, "updated", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    else {
                                        FirebaseFirestore.getInstance().collection("companies").document(doc).collection("customer_details").document(cu_mobile).set(c1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(bill.this, "added", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        Toast.makeText(bill.this, "document does not exist", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    FirebaseFirestore.getInstance().collection("companies").document(doc).collection("customer_details").document(cu_mobile).set(c1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(bill.this, "added", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                }
                            });
                            mypdfDocument.close();
                        }
                    }
                });
                db.collection("companies").document(doc).collection("orders").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        WriteBatch batch = FirebaseFirestore.getInstance().batch();

                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot snapshot: snapshotList){
                            batch.delete(snapshot.getReference());
                        }

                        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                String doc = getIntent().getStringExtra("doc").trim();
                                String username = getIntent().getStringExtra("username").trim();
                                Intent i = new Intent(bill.this , home_new.class);
                                i.putExtra("doc_name",doc);
                                i.putExtra("username",username);
                                startActivity(i);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(bill.this, "error in deletion", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

}