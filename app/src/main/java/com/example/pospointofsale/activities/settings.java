package com.example.pospointofsale.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pospointofsale.R;

public class settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TextView username = findViewById(R.id.ed_username);
        TextView email = findViewById(R.id.ed_email);
        TextView company = findViewById(R.id.ed_company_name);
        TextView sign_out = findViewById(R.id.btn_sign_out);
        String comp_name = getIntent().getStringExtra("company_name").trim();
        String email_txt = getIntent().getStringExtra("doc").trim();
        String user_name = getIntent().getStringExtra("username").trim();
        username.setText(user_name);
        email.setText(email_txt);
        company.setText(comp_name);
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(settings.this , Login.class);
                startActivity(i);
            }
        });
    }
}