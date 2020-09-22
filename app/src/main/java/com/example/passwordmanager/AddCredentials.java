package com.example.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddCredentials extends AppCompatActivity {

    TextInputEditText title_edittext,username_edittext, password_edittext;
    MaterialButton button;

    public static final String EXTRA_ID = "com.example.passwordmanager.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.passwordmanager.EXTRA_TTTLE";
    public static final String EXTRA_USERNAME = "com.example.passwordmanager.EXTRA_USERNAME";
    public static final String EXTRA_PASSWORD = "com.example.passwordmanager.EXTRA_PASSWORD";

    private Intent  gotThisIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credentials);

        title_edittext = findViewById(R.id.title_edittext);
        username_edittext = findViewById(R.id.username_edittext);
        password_edittext = findViewById(R.id.password_edittext);
        button = findViewById(R.id.save_btn);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);

        gotThisIntent = getIntent();

        if(gotThisIntent.hasExtra(EXTRA_ID)) {

            button.setText("Update");
            setTitle("Update Credentials");
            title_edittext.setText(gotThisIntent.getStringExtra(EXTRA_TITLE));
            username_edittext.setText(gotThisIntent.getStringExtra(EXTRA_USERNAME));
            password_edittext.setText(gotThisIntent.getStringExtra(EXTRA_PASSWORD));
        }
        else {
            button.setText("Save");
            setTitle("Add Credentials");
        }

    }

    public void saveCredentials(View view) {

        String title = title_edittext.getText().toString().trim();
        String username = username_edittext.getText().toString().trim();
        String password = password_edittext.getText().toString().trim();

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_USERNAME, username);
        data.putExtra(EXTRA_PASSWORD, password);

        long id = gotThisIntent.getLongExtra(EXTRA_ID, -1);

        if(id == -1) {
            setResult(RESULT_OK, data);
            finish();
        }
        else {
            data.putExtra(EXTRA_ID, id);
            setResult(RESULT_OK, data);
            finish();
        }


    }
}