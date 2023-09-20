package com.afaq.huts.ui;

import static com.afaq.huts.model.HideKeyBoard.hideKeyboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.afaq.huts.SessionManager;
import com.afaq.huts.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    private SessionManager sessionManager;
    private  String email , password ;
    private ProgressDialog progressDialog;
    private       DatabaseReference usersRef;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging in...");
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
         usersRef = FirebaseDatabase.getInstance().getReference("UsersDetail");
         firebaseAuth = FirebaseAuth.getInstance();
        sessionManager = new SessionManager(this);
        String nameS = sessionManager.getNaame();
        String emailS = sessionManager.getEmail();
        String passwordS = sessionManager.getPassword();




//.

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


                loginWithEmailAndPassword();
            }
        });




        binding.btnNewAccount.setOnClickListener(view -> {


            startActivity(new Intent(SignUpActivity.this , RegistrationActivity.class));
        });
    }

    private void checkEmail(String email, String password) {

        progressDialog.show();
        usersRef.orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressDialog.dismiss();
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String storedPassword = dataSnapshot.child("password").getValue(String.class);
                                if (password.equals(storedPassword)) {


//                                    FirebaseUser user=    FirebaseAuth.getInstance().getCurrentUser();
//                                    Toast.makeText(SignUpActivity.this,   " "  +user.getUid(), Toast.LENGTH_SHORT).show();


                                    startActivity(new Intent(SignUpActivity.this, DashboardActivity.class));
                                    finish();
                                } else {
                                    progressDialog.dismiss();
                                    // Password is incorrect
                                    Toast.makeText(SignUpActivity.this, "Incorrect password.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            progressDialog.dismiss();
                            // Email not found in the database
                            Toast.makeText(SignUpActivity.this, "Email not found.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                        Toast.makeText(SignUpActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loginWithEmailAndPassword() {
        String email = binding.editTextTextEmail.getText().toString();
        String password = binding.editTextTextPassword.getText().toString();

        if (email.isEmpty()) {
            binding.editTextTextEmail.setError("Enter email");
            return;
        }

        if (password.isEmpty()) {
            binding.editTextTextPassword.setError("Enter password");
            return;
        }

        progressDialog.show();
        hideKeyboard(this);




        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        FirebaseUser user=     firebaseAuth.getCurrentUser();


//                        Toast.makeText(this, ""+user.getEmail() +" " +user.getUid(), Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(SignUpActivity.this,DashboardActivity.class));

                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {

                });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }


}