package com.example.huts.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.huts.SessionManager;
import com.example.huts.databinding.ActivitySignUpBinding;
import com.example.huts.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.mindrot.jbcrypt.BCrypt;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;

    private SessionManager sessionManager;
    private  String email , password ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("usersDetail");
        Query userRef = usersRef.orderByChild("email").equalTo(email);

//        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        sessionManager = new SessionManager(this);
        String nameS = sessionManager.getNaame();
        String emailS = sessionManager.getEmail();
        String passwordS = sessionManager.getPassword();
//    binding.btnLogin.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.exists()) {
//                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
//                            Users user = userSnapshot.getValue(Users.class);
//                            String enteredPasswordHash = hashFunction(password);
//
//                            if (enteredPasswordHash.equals(user.getPassword())) {
//                                // Successful login
//                                Toast.makeText(SignUpActivity.this, "User login successful", Toast.LENGTH_SHORT).show();
//
//                                // Redirect to Dashboard or wherever you need
//                                startActivity(new Intent(SignUpActivity.this, DashboardActivity.class));
//                                finish();
//                            } else {
//                                // Invalid password
//                                Toast.makeText(SignUpActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    } else {
//                        // User not found
//                        Toast.makeText(SignUpActivity.this, "User not found", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(SignUpActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    });



        Toast.makeText(this, " email is"+sessionManager.getEmail() + "password is  " +sessionManager.getPassword()
                 + "name is  " + sessionManager.getNaame(), Toast.LENGTH_SHORT).show();
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

    private String hashFunction(String password) {
        // Generate a salt for BCrypt
        String salt = BCrypt.gensalt();

        // Hash the password with the generated salt
        String hashedPassword = BCrypt.hashpw(password, salt);

        return hashedPassword;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}