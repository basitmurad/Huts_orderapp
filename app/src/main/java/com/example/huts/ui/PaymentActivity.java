package com.example.huts.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.example.huts.R;
import com.example.huts.SessionManager;
import com.example.huts.databinding.ActivityPaymentBinding;
import com.example.huts.model.ChildItem;
import com.example.huts.model.OrderData;
import com.example.huts.model.OrderDetails;
import com.example.huts.model.ParentItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class PaymentActivity extends AppCompatActivity {


    private ActivityPaymentBinding binding ;
    private SessionManager sessionManager;
    private ProgressDialog progressDialog;
    private ArrayList<ChildItem> childItemArrayList;
    private int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        sessionManager = new SessionManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Placing Order...");

        progressDialog.setCancelable(false);

        String userId = FirebaseAuth.getInstance().getUid();
        String pushID = UUID.randomUUID().toString();
        String hut =  sessionManager.getHutName();
        String imageUri = sessionManager.getHutImage();
        Toast.makeText(this, ""+imageUri, Toast.LENGTH_SHORT).show();
        String orderId = generateRandomNumber(16);
      String userEmail = sessionManager.getEmail();
      String userName = sessionManager.getNaame();
        DbHelper dbHelper = new DbHelper(PaymentActivity.this);
         total = (int) dbHelper.calculateTotalNewPrice();

     childItemArrayList = dbHelper.getAll1();




        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (! binding.editextDelivery.getText().toString().isEmpty())
                {
                    Toast.makeText(PaymentActivity.this, "Please enter you detail", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.show();
                    Toast.makeText(PaymentActivity.this, "orders placed successfully", Toast.LENGTH_SHORT).show();
                    DbHelper dbHelper = new DbHelper(PaymentActivity.this);
                ArrayList<OrderDetails> orderDetailsList = dbHelper.getAll();
                total = (int) dbHelper.calculateTotalNewPrice();

                // Send data to Firebase
                DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("ActiveOrders");

                    ParentItem parentItem = new ParentItem(hut,userId,pushID,orderId,total,childItemArrayList,true);
//                OrderData orderData = new OrderData(hut,userId,pushID,orderId,total,orderDetailsList, true);
                ordersRef.child(userId).child(pushID).setValue(parentItem)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.dismiss();
                                // Data sent successfully, start PaymentActivity
                                dbHelper.deleteAllOrders();
                                Toast.makeText(PaymentActivity.this, "Order Placed successfully", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(PaymentActivity.this, DashboardActivity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                // Handle failure
                                Toast.makeText(PaymentActivity.this, "Failed to send data to Firebase", Toast.LENGTH_SHORT).show();
                            }
                        });
                }
            }
        });





    }

    public static String generateRandomNumber(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // Generate a random digit (0 to 9)
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();
    }
}