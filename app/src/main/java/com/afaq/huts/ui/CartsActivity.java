package com.afaq.huts.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.afaq.huts.adapters.CartAdapter;
import com.afaq.huts.databinding.ActivityCartsBinding;
import com.afaq.huts.model.DishDetail;

import java.util.ArrayList;

public class CartsActivity extends AppCompatActivity {


    private CartAdapter cartAdapter;


    private ArrayList<DishDetail> dishList;
    private ActivityCartsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot()); // Set your layout here


        DbHelper dbHelper = new DbHelper(this);


        dishList = dbHelper.getAllDishes();


        cartAdapter = new CartAdapter(this, dishList);
        binding.cardRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.cardRecycler.setAdapter(cartAdapter);


        binding.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dishList.isEmpty()) {
                    Toast.makeText(CartsActivity.this, "order is empty", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(CartsActivity.this, HutsActivity.class));
                }
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


}
