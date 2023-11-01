package com.afaq.huts.ui;

import static android.content.ContentValues.TAG;
import static com.afaq.huts.model.HideKeyBoard.hideKeyboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afaq.huts.SessionManager;
import com.afaq.huts.databinding.ActivityRegistrationBinding;
import com.afaq.huts.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;


public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;
    private String email, password, number, userId, name, userFcmToken;
    private SessionManager sessionManager;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);

        sessionManager = new SessionManager(this);


        fcmToken();
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
            progressDialog.setMessage("Creating account");
            progressDialog.setCancelable(false);
            progressDialog.show();
            hideKeyboard(this);

            name = binding.editTextTextUserName.getText().toString();
            password = binding.editTextTextPassword.getText().toString();
            email = binding.editTextTextEmail.getText().toString();
            number = binding.editTextTextNumber.getText().toString();


            createAccountWithEmailAndPassword(email, password);


        }
    }


    private void createAccountWithEmailAndPassword(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            progressDialog.dismiss();

                            Toast.makeText(RegistrationActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();

                            hideKeyboard(RegistrationActivity.this);
                            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            SendDataToFireBase();
                            sessionManager.saveCredentials(name, password, email, number, userFcmToken);

                        } else {

                            progressDialog.dismiss();

                            Log.e(TAG, "Account creation failed: " + task.getException());
                            Toast.makeText(RegistrationActivity.this, "Please try again", Toast.LENGTH_SHORT).show();                        }
                    }
                });
    }


    private void SendDataToFireBase() {
        Users users = new Users(name, email, number, password, userId, userFcmToken);
        databaseReference.child("UsersDetail").child(userId).setValue(users)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        finish();
                        progressDialog.dismiss();
                        Intent intent = new Intent(RegistrationActivity.this, DashboardActivity.class);


                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RegistrationActivity.this, "Try Again...\n something went wrong", Toast.LENGTH_SHORT).show();

                        Toast.makeText(RegistrationActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }


    private void fcmToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get the FCM token
                    userFcmToken = task.getResult();
                    sessionManager.setAdminFcmToken(userFcmToken);

                    // Now you have the FCM token for the admin app
                    Log.d(TAG, "token" + userFcmToken);
                    Log.d("toeken", userFcmToken);
//                    Toast.makeText(this, ""+adminFcmToken, Toast.LENGTH_SHORT).show();
                    // Save the FCM token to your server or preferences if needed
                });
    }
}