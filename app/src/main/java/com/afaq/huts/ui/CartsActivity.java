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

    private String hutName;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot()); // Set your layout here
//
//        hutName = getIntent().getStringExtra("hutname");
//        Toast.makeText(this, " "+hutName, Toast.LENGTH_SHORT).show();

         dbHelper = new DbHelper(this);


        dishList = dbHelper.getAllDishes();


        cartAdapter = new CartAdapter(this, dishList);
        binding.cardRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.cardRecycler.setAdapter(cartAdapter);



        binding.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dishList.isEmpty()) {
                    Toast.makeText(CartsActivity.this, "Order is empty", Toast.LENGTH_SHORT).show();
                } else if (isCartItemsFromSameHut()) {
                    // All items are from the same hut, proceed to order
                    Intent intent = new Intent(CartsActivity.this, OrderActivity.class);
                    intent.putExtra("hutname", hutName);
                    startActivity(intent);
                } else {
                    Toast.makeText(CartsActivity.this, "You can only order from the same hut", Toast.LENGTH_SHORT).show();
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


    private boolean isCartItemsFromSameHut() {
        // Check if all items in the cart have the same "hutname"
        if (dishList.isEmpty()) {
            return false;
        }

        hutName = dishList.get(0).getHutName(); // Get the hut name from the first item
        for (DishDetail dish : dishList) {
            if (!dish.getHutName().equals(hutName)) {
                return false; // Items from different huts found
            }
        }
        return true; // All items are from the same hut
    }
}
