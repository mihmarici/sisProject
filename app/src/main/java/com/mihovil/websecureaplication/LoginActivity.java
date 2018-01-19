package com.mihovil.websecureaplication;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mihovil.websecureaplication.Models.UserModel;
import com.mihovil.websecureaplication.WebService.OnServiceFinished;
import com.mihovil.websecureaplication.WebService.ServiceResponse;
import com.mihovil.websecureaplication.WebService.WebServiceCaller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class LoginActivity extends AppCompatActivity implements OnServiceFinished, View.OnClickListener {
    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginButton;

    WebServiceCaller webServiceCaller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initListeners();

    }

    private void initView() {
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
    }

    private void initListeners() {
        loginButton.setOnClickListener(this);
        webServiceCaller = new WebServiceCaller(this);
    }

    private boolean checkIfEmpty() {
        return !(loginEmail.getText().toString().isEmpty() || loginPassword.getText().toString().isEmpty());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.loginButton: {
                if (checkIfEmpty()) {
                        UserModel user = new UserModel(loginEmail.getText().toString(),
                                loginPassword.getText().toString());
                        webServiceCaller.loginOrRegistrate(user);
                } else {
                    String errorMessage = "Fill all fields!";
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onRequestArrived(ServiceResponse message) {

        if (message.isPostoji()) {

            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("email", message.getEmail());
            i.putExtra("token", message.getToken());
            startActivity(i);
            Toast.makeText(this, message.getResponseMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "invalid email or password", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onRequestFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}

