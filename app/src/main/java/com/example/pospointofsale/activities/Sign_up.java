package com.example.pospointofsale.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pospointofsale.R;
import com.example.pospointofsale.model.SessionManager;
import com.example.pospointofsale.objects.company;
import com.example.pospointofsale.objects.user;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class Sign_up extends AppCompatActivity {
    FirebaseFirestore fb = FirebaseFirestore.getInstance();
    FirebaseAuth fauth = FirebaseAuth.getInstance();
    EditText name;
    EditText email;
    EditText password;
    EditText company_name;
    TextView signup;
    SessionManager sessionManager;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sessionManager =  new SessionManager(getApplicationContext());
        signup = (TextView)findViewById(R.id.btn_sign);
        signup.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          name = findViewById(R.id.ed_username);
                                          email = findViewById(R.id.ed_email);
                                          password = findViewById(R.id.ed_password);
                                          company_name = findViewById(R.id.ed_company_name);
                                          String s_name = name.getText().toString();
                                          String s_email = email.getText().toString();
                                          String s_password = password.getText().toString();
                                          String s_company_name = company_name.getText().toString();
                                          user User = new user(s_email,s_password,s_name);
                                          final company Company = new company(s_company_name,User);
                                          rootNode = FirebaseDatabase.getInstance();
                                          reference = rootNode.getReference("users");
                                          String key = reference.push().getKey();
                                          reference.child(s_name).setValue(User);
                                          createUser(Company,s_name);
                                      }
                                  });

    }

    private void createUser(company Company,String s_name) {
        user document_name = Company.getUser1();
        String email = document_name.getEmail();
       // HashMap<String,company> company_details = new HashMap<>();
        //company_details.put("comapny",Company);
        fb.collection("companies").document(email).set(Company).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Sign_up.this , "you have been succesfully registered" , Toast.LENGTH_SHORT);
                Intent i1 = new Intent(Sign_up.this , Login.class);
                startActivity(i1);
            }
        });
        sessionManager.setUserDetails(email);
    }
}