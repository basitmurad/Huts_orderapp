package com.example.huts.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.huts.R;
import com.example.huts.SessionManager;
import com.example.huts.databinding.ActivityPaymentBinding;
import com.example.huts.model.OrderData;
import com.example.huts.model.OrderDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class PaymentActivity extends AppCompatActivity {


    private ActivityPaymentBinding binding ;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        sessionManager = new SessionManager(this);

        String userId = FirebaseAuth.getInstance().getUid();
        String pushID = UUID.randomUUID().toString();
        String hut =  sessionManager.getHutName();
      String userEmail = sessionManager.getEmail();
      String userName = sessionManager.getNaame();

//
//        Toast.makeText(this, ""+hut, Toast.LENGTH_SHORT).show();
// Inside your onClick method in PaymentActivity



        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper dbHelper = new DbHelper(PaymentActivity.this);
                ArrayList<OrderDetails> orderDetailsList = dbHelper.getAll();

                int total = (int) dbHelper.calculateTotalNewPrice();

                // Send data to Firebase
                DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("ActiveOrders");
           // Generate a unique key as the order ID

                // Create an OrderData instance with the data you want to save
//                OrderData orderData = new OrderData(hut,total,orderDetailsList,orderId);
//                OrderData orderData1  = new OrderData(hut, total,)

                OrderData orderData = new OrderData(hut,userId,pushID,total,orderDetailsList, true);
                ordersRef.child(userId).child(pushID).setValue(orderData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                // Data sent successfully, start PaymentActivity
                                dbHelper.deleteAllOrders();
                                Toast.makeText(PaymentActivity.this, "Order Placed successfully", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(PaymentActivity.this, DashboardActivity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle failure
                                Toast.makeText(PaymentActivity.this, "Failed to send data to Firebase", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


//        binding.btnOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DbHelper dbHelper = new DbHelper(PaymentActivity.this);
//                ArrayList<OrderDetails> orderDetailsList = dbHelper.getAll();
//
//                int total = (int) dbHelper.calculateTotalNewPrice();
//
//                // Send data to Firebase
//                DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");
//                String orderId = ordersRef.push().getKey(); // Generate a unique key
//                OrderData orderData = new OrderData(hut, total, orderDetailsList); // Create a class to hold the data
//
//                ordersRef.child(orderId).setValue(orderData)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                // Data sent successfully, start PaymentActivity
//                                dbHelper.deleteAllOrders();
//                                Toast.makeText(PaymentActivity.this, "Order Place successfully", Toast.LENGTH_SHORT).show();
//
//
//                                startActivity(new Intent(PaymentActivity.this,DashboardActivity
//                                        .class));
////                                Intent intent = new Intent(OrderActivity.this, PaymentActivity.class);
////
////                                intent.putExtra("orderId", orderId); // Pass the order ID if needed
////                                startActivity(intent);
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                // Handle failure
//                                Toast.makeText(PaymentActivity.this, "Failed to send data to Firebase", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            }
//        });

    }
}