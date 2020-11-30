package com.example.pospointofsale.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pospointofsale.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    FirebaseFirestore fb;
    FirebaseAuth fauth;

    TextView Login;
    TextView signup;
    FirebaseDatabase fd;
    DatabaseReference dr;
    EditText user_password;
    EditText user_email;
    private static final String TAG = "message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user_email = findViewById(R.id.ed_name);
        user_password = (EditText)findViewById(R.id.ed_password);
        //String name = user_email.getText().toString();
        //String pass = user_password.getText().toString();
        Login = (TextView)findViewById(R.id.btn_login);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = user_email.getText().toString().trim();
                Toast.makeText(Login.this, name, Toast.LENGTH_SHORT).show();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(name);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    String pass = user_password.getText().toString();
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String password = snapshot.child("password").getValue().toString();
                        String email  = snapshot.child("email").getValue().toString().trim();
                        Toast.makeText(Login.this, password, Toast.LENGTH_SHORT).show();
                        if(pass.equals(password)) {
                            String username = user_email.getText().toString().trim();
                            Intent i = new Intent(Login.this , home_new.class);
                            i.putExtra("doc_name",email);
                            i.putExtra("username",username);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(Login.this, "enter corrct details", Toast.LENGTH_SHORT).show();
                        }
                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        //fauth = FirebaseAuth.getInstance();
        signup = (TextView)findViewById(R.id.btn_sign);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Login.this , Sign_up.class );
                startActivity(in);
            }
        });

    }



    private void updateUI(Object o) {
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = fauth.getCurrentUser();
        //updateUI(currentUser);
    }
}