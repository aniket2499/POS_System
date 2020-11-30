package com.example.pospointofsale.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "message";
    private static int value;
    private FirebaseAuth mAuth ;
    private  final String USER = "user";
    static int identity=0;
    private  FirebaseFirestore db;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        //mAuth = FirebaseAuth.getInstance();
        Intent i = new Intent(MainActivity.this, Login.class);
        i.putExtra("doc" , "test4");
        startActivity(i);

    }
    /*public  void onbutton(View view) {
        final EditText na = (EditText) findViewById(R.id.email);
        //final EditText pa = (EditText) findViewById(R.id.pass);
        EditText userid = (EditText) findViewById(R.id.userid);
        final String name = na.getText().toString();
        final String pass = userid.getText().toString();
       // final String u_id = userid.getText().toString();
        final String us_name;
        final String status;
        status = "owner";
        int i = 0;
        //company_details cd = new company_details(Integer.toString(i));
        db = FirebaseFirestore.getInstance();
        //final Map<String, Object> user = new HashMap<>();
        //user.put("first", name);
        //user.put("last", pass);
        //user.put("born", 1815);
        //db.collection("users")
        //      .add(user)
        ;
        user ud = new user(name, pass);
        // company cm = new company("subham gems", ud );

        mAuth.createUserWithEmailAndPassword(name, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "sucess", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        }
    public  void login(View view) {
        final EditText na = (EditText) findViewById(R.id.email);
        //final EditText pa = (EditText) findViewById(R.id.pass);
        EditText userid = (EditText) findViewById(R.id.userid);
        final String name = na.getText().toString();
        final String pass = userid.getText().toString();
        // final String u_id = userid.getText().toString();
        final String us_name;
        final String status;
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(name, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, name,
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
    }

    public  void logout(View view){
        mAuth.signOut();
    }
/*        HashMap<String,company> docdata = new HashMap<String, company>();
        docdata.put("company_details",cm);
        db.collection("compnaies").document(name).set(cm).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //makeText(MainActivity.this, "success", LENGTH_SHORT).show();
            }
        });
        HashMap<String,Object> catdetails = new HashMap<>();
        catdetails.put("catagoryname","dairy_product");
        catdetails.put("sub_catagory","sweets");
        db.collection("compnaies").document(name).collection("catagories").document("dairy_product").set(catdetails).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                makeText(MainActivity.this, "success", LENGTH_SHORT).show();
            }
        });
        HashMap<String,Object> sub_Cat = new HashMap<>();
        catdetails.put("sub_catagory","sweets");
        catdetails.put("item_details","dairy_milk");
        db.collection("compnaies").document(name).collection("catagories").document("dairy_product").collection("sub_catagories").document("sweet").set(sub_Cat).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                makeText(MainActivity.this, "success", LENGTH_SHORT).show();
            }
        });
        HashMap<String,Object> item_details = new HashMap<>();
        item_details.put("item_name","dairy_milk");
        item_details.put("item_price","80");
        db.collection("compnaies").document(name).collection("catagories").document("dairy_product").collection("sub_catagories").document("sweet").collection("items").document("dairy_milk").set(item_details).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                makeText(MainActivity.this, "success", LENGTH_SHORT).show();
            }
        });*/
    }


