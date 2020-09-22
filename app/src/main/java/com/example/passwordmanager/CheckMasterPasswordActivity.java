package com.example.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class CheckMasterPasswordActivity extends AppCompatActivity {

    TextInputEditText createAccountMP_edittext, createAccountConfirmMP_edittext, signInMP_edittext;
    Button createAccount_btn, signIn_btn;
    CardView createAccount_cardView, signIn_cardView;

    static SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_master_password);
        getSupportActionBar().hide();

        createAccountMP_edittext = findViewById(R.id.masterPassword_edittext);
        createAccountConfirmMP_edittext = findViewById(R.id.confirmMasterPassword_edittext);
        signInMP_edittext = findViewById(R.id.enterMasterPassword_edittext);
        createAccount_btn = findViewById(R.id.createAccount_button);
        signIn_btn = findViewById(R.id.signIn_button);
        createAccount_cardView = findViewById(R.id.createAccount_cardview);
        signIn_cardView = findViewById(R.id.enterMasterPassword_cardview);

        sessionManager = new SessionManager(this);

        if(sessionManager.checkAccountCreated()) {
            signIn_cardView.setVisibility(View.VISIBLE);
            createAccount_cardView.setVisibility(View.GONE);
        }
        else {
            signIn_cardView.setVisibility(View.GONE);
            createAccount_cardView.setVisibility(View.VISIBLE);
        }
    }



    public void signIn(View view) {
        String masterPassword = signInMP_edittext.getText().toString().trim();
        if(masterPassword.equals(sessionManager.getMasterPassword())) {
            startActivity(new Intent(CheckMasterPasswordActivity.this, MainActivity.class));
            finish();
        }
        else {
            Toast.makeText(this,"Invalid Password", Toast.LENGTH_SHORT).show();
            signInMP_edittext.setText("");
        }
    }



    public void createAccount(View view) {
        String masterPassword = createAccountMP_edittext.getText().toString().trim();
        String confireMasterPassword = createAccountConfirmMP_edittext.getText().toString().trim();

        if(masterPassword.equals(confireMasterPassword)) {
            sessionManager.createLoginSession(masterPassword);
            startActivity(new Intent(CheckMasterPasswordActivity.this, MainActivity.class));
            finish();
            Toast.makeText(CheckMasterPasswordActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(CheckMasterPasswordActivity.this, "Passwords doesn't match", Toast.LENGTH_SHORT).show();
            createAccountMP_edittext.setText("");
            createAccountConfirmMP_edittext.setText("");
        }
    }

}