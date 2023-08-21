package com.example.huts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.huts.SessionManager;
import com.example.huts.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;

    private SessionManager sessionManager;
    private  String email , password ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);
        String nameS = sessionManager.getNaame();
        String emailS = sessionManager.getEmail();
        String passwordS = sessionManager.getPassword();

        binding.btnLogin.setOnClickListener(view -> {


            if (binding.editTextTextEmail.getText().toString().isEmpty())
            {
                binding.editTextTextEmail.setError("Email is missing");
            }
            if (binding.editTextTextPassword.getText().toString().isEmpty())
            {
                binding.editTextTextPassword.setError("Password is missing");
            }

            else
            {
                email = binding.editTextTextEmail.getText().toString();
                password = binding.editTextTextPassword.getText().toString();


                startActivity(new Intent(SignUpActivity.this,DashboardActivity.class));
            }
        });




        binding.btnNewAccount.setOnClickListener(view -> {


            startActivity(new Intent(SignUpActivity.this , RegistrationActivity.class));
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}