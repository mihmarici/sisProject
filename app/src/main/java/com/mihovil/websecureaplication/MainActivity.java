package com.mihovil.websecureaplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView email;
    private TextView token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        getMyIntent();
    }

    private void initView(){
        email = findViewById(R.id.MainActivityEmail);
        token = findViewById(R.id.MainActivityToken);
    }

    private void getMyIntent(){
        Intent i = getIntent();
        String emailFromIntent = i.getStringExtra("email");
        String tokenFromIntent = i.getStringExtra("token");

        setView(emailFromIntent,tokenFromIntent);
    }

    private void setView(String emailFromIntent,String tokenFromIntent){
        email.setText(emailFromIntent);
        token.setText(tokenFromIntent);
    }

}
