package com.example.huts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.huts.databinding.ActivityOrderBinding;
import com.example.huts.model.OrderDetails;

import java.util.ArrayList;


public class OrderActivity extends AppCompatActivity {

    private ActivityOrderBinding binding;

    private String quantity, name, price;
private  ArrayList<OrderDetails> orderDetailsList;

    private String hutName , hutImage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        DbHelper dbHelper = new DbHelper(this);


        hutName = getIntent().getStringExtra("hutName");



        orderDetailsList = new ArrayList<>();
       orderDetailsList = dbHelper.getAll();




        StringBuilder dataBuilder = new StringBuilder();
        StringBuilder dataBuilder1 = new StringBuilder();
        StringBuilder dataBuilder2 = new StringBuilder();
        StringBuilder dataBuilder3 = new StringBuilder();
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


        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });



        binding.btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderActivity.this,CartsActivity.class));
            }
        });


        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                dbHelper.deleteAllOrders();




                Intent intent = new Intent(OrderActivity.this,PaymentActivity.class);
//

                startActivity(intent);
            }
        });








    }


}




