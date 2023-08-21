package com.example.huts.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Toast;

import com.example.huts.R;
import com.example.huts.adapters.CartAdapter;
import com.example.huts.databinding.ActivityOrderBinding;
import com.example.huts.model.DishDetail;
import com.example.huts.model.OrderData;
import com.example.huts.model.OrderDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class OrderActivity extends AppCompatActivity {

    private ActivityOrderBinding binding;

    private String quantity, name, price;

    private String hutName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        DbHelper dbHelper = new DbHelper(this);


        hutName = getIntent().getStringExtra("hutName");

        ArrayList<OrderDetails> orderDetailsList = dbHelper.getAll();


        StringBuilder dataBuilder = new StringBuilder();
        StringBuilder dataBuilder1 = new StringBuilder();
        StringBuilder dataBuilder2 = new StringBuilder();
        binding.quantityTextView.setText("");
        binding.nameTextView.setText("");
        binding.priceTextView.setText("");

        for (OrderDetails dish : orderDetailsList) {
            String quantity = String.valueOf(dish.getQuantity()) + "\n";
            dataBuilder.append(quantity);

            String name = dish.getName() + "\n";
            dataBuilder1.append(name);

            String price = String.valueOf(dish.getNewPrice()) + "\n";
            dataBuilder2.append(price);
        }

        binding.quantityTextView.setText(dataBuilder.toString());
        binding.nameTextView.setText(dataBuilder1.toString());
        binding.priceTextView.setText(dataBuilder2.toString());


        int total = (int) dbHelper.calculateTotalNewPrice();
        binding.totalPrcie.setText(String.valueOf(total));


        binding.hutName.setText(hutName);


//        Toast.makeText(this, "total price is " + total, Toast.LENGTH_SHORT).show();

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                dbHelper.deleteAllOrders();


//                Toast.makeText(OrderActivity.this, "All orders deleted", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(OrderActivity.this,PaymentActivity.class);
//                intent.putExtra("hutName," ,hutName);

                startActivity(intent);
            }
        });











//
//        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DbHelper dbHelper = new DbHelper(OrderActivity.this);
//                ArrayList<OrderDetails> orderDetailsList = dbHelper.getAll();
//
//                // Extract the hut name
//                String hutName = getIntent().getStringExtra("hutName");
//
//                // Calculate the total price
//                int total = (int) dbHelper.calculateTotalNewPrice();
//
//                // Send data to Firebase
//                DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");
//                String orderId = ordersRef.push().getKey(); // Generate a unique key
//                OrderData orderData = new OrderData(hutName, total, orderDetailsList); // Create a class to hold the data
//
//                ordersRef.child(orderId).setValue(orderData)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                // Data sent successfully, start PaymentActivity
//                                dbHelper.deleteAllOrders();
//                                Toast.makeText(OrderActivity.this, "Order Place successfully", Toast.LENGTH_SHORT).show();
//
//
//                                startActivity(new Intent(OrderActivity.this,DashboardActivity
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
//                                Toast.makeText(OrderActivity.this, "Failed to send data to Firebase", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            }
//        });






    }


}




