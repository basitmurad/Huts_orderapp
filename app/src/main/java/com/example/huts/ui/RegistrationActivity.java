package com.example.huts.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.huts.SessionManager;
import com.example.huts.databinding.ActivityRegistrationBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class RegistrationActivity extends AppCompatActivity {

    ActivityRegistrationBinding binding;
    private String email, password, number, name;
    private SessionManager sessionManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);

        sessionManager = new SessionManager(this);


//        binding.btnSubmit.setOnClickListener(view -> {
//
//            if (binding.editTextTextUserName.getText().toString().isEmpty()) {
//                binding.editTextTextUserName.setError("Name is empty");
//            } else if (binding.editTextTextPassword.getText().toString().isEmpty()) {
//                binding.editTextTextPassword.setError("Password is missing");
//            } else if (binding.editTextTextEmail.getText().toString().isEmpty()) {
//                binding.editTextTextEmail.setError("Email is empty");
//            } else if (binding.editTextTextNumber.getText().toString().isEmpty()) {
//                binding.editTextTextNumber.setError("Number is empty");
//            } else {
//
//
//                name = binding.editTextTextUserName.getText().toString();
//                password = binding.editTextTextPassword.getText().toString();
//                email = binding.editTextTextEmail.getText().toString();
//                number = binding.editTextTextNumber.getText().toString();
//
//                Toast.makeText(this, "Account is created ", Toast.LENGTH_SHORT).show();
//
//
//                sessionManager.saveCredentials(name, password, email);
//
//
//                Intent intent = new Intent(RegistrationActivity.this, DashboardActivity.class);
//                startActivity(intent);
//            }
//
//        });



        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckValidations();
            }
        });

        binding.btnLOgin.setOnClickListener(view -> {


            startActivity(new Intent(RegistrationActivity.this, SignUpActivity.class));
        });


    }

    private void CheckValidations() {
        if (binding.editTextTextUserName.getText().toString().isEmpty()) {
            binding.editTextTextUserName.setError("Name is empty");
        } else if (binding.editTextTextPassword.getText().toString().isEmpty()) {
            binding.editTextTextPassword.setError("Password is missing");
        } else if (binding.editTextTextEmail.getText().toString().isEmpty()) {
            binding.editTextTextEmail.setError("Email is empty");
        } else if (binding.editTextTextNumber.getText().toString().isEmpty()) {
            binding.editTextTextNumber.setError("Number is empty");
        } else {
            progressDialog.setTitle("PLease wait..");
            progressDialog.setMessage("Sending Otp");
            progressDialog.setCancelable(false);
            progressDialog.show();

            name = binding.editTextTextUserName.getText().toString();
            password = binding.editTextTextPassword.getText().toString();
            email = binding.editTextTextEmail.getText().toString();
            number = binding.editTextTextNumber.getText().toString();


            PhoneAuthProvider.getInstance().verifyPhoneNumber("+92" + number,
                    60l,
                    TimeUnit.SECONDS, RegistrationActivity.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {

                            Log.d("Exception", e.getMessage());
                            Toast.makeText(RegistrationActivity.this, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {


                            progressDialog.dismiss();

                            Intent intent = new Intent(RegistrationActivity.this, OtpViewActivity.class);
                            intent.putExtra("verificationID", s);
                            intent.putExtra("number", "+92" + number);
                            intent.putExtra("email", email);
                            intent.putExtra("name", name);
                            intent.putExtra("password", password);

                            sessionManager.saveCredentials(name
                            ,password,email,number
                            );


                            startActivity(intent);


                        }
                    });

        }
    }
}