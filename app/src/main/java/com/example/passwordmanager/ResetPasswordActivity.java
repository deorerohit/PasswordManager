package com.example.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class ResetPasswordActivity extends AppCompatActivity {

    TextInputEditText resetMP_oldPassword, resetMP_newPassword, resetMP_confirmPassword;
    Button resetMP_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
        setTitle("Reset Master Password");

        resetMP_oldPassword = findViewById(R.id.resetMP_oldPasssword);
        resetMP_newPassword = findViewById(R.id.resetMP_newPassword);
        resetMP_confirmPassword = findViewById(R.id.resetMP_confirmNewPassword);
        resetMP_btn = findViewById(R.id.resetMP_btn);






    }

    public void resetPasswordBtn(View view) {
        String oldPassword = resetMP_oldPassword.getText().toString().trim();
        String newPassword = resetMP_newPassword.getText().toString().trim();
        String confirmNewPassword = resetMP_confirmPassword.getText().toString().trim();




        if(oldPassword.equals(CheckMasterPasswordActivity.sessionManager.getMasterPassword())) {

            if(newPassword.equals(confirmNewPassword)) {
               CheckMasterPasswordActivity.sessionManager.createLoginSession(newPassword);
               Toast.makeText(this, "Password successfully reset", Toast.LENGTH_SHORT).show();
               resetMP_oldPassword.setText("");
               resetMP_newPassword.setText("");
               resetMP_confirmPassword.setText("");
            }
            else {
                Toast.makeText(this, "Password not confirmed", Toast.LENGTH_SHORT).show();
                resetMP_newPassword.setText("");
                resetMP_confirmPassword.setText("");
            }

        }
        else {
            Toast.makeText(this, "Your old password is incorrect", Toast.LENGTH_SHORT).show();
            resetMP_oldPassword.setText("");
            resetMP_newPassword.setText("");
            resetMP_confirmPassword.setText("");
        }
    }
}