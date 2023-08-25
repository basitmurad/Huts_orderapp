package com.example.huts.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.example.huts.R;
import com.example.huts.adapters.CartAdapter;
import com.example.huts.databinding.ActivityOrderBinding;
import com.example.huts.model.ChildItem;
import com.example.huts.model.DishDetail;
import com.example.huts.model.OrderData;
import com.example.huts.model.OrderDetails;
import com.example.huts.model.ParentItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class OrderActivity extends AppCompatActivity {

    private ActivityOrderBinding binding;

    private String quantity, name, price;
private  ArrayList<OrderDetails> orderDetailsList;
private  ArrayList<ChildItem> childItemArrayList;
    private String hutName , hutImage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        DbHelper dbHelper = new DbHelper(this);


        hutName = getIntent().getStringExtra("hutName");



//        orderDetailsList = new ArrayList<>();
//       orderDetailsList = dbHelper.getAll();
       childItemArrayList = new ArrayList<>();
       childItemArrayList = dbHelper.getAll1();



        StringBuilder dataBuilder = new StringBuilder();
        StringBuilder dataBuilder1 = new StringBuilder();
        StringBuilder dataBuilder2 = new StringBuilder();
        StringBuilder dataBuilder3 = new StringBuilder();
        binding.quantityTextView.setText("");
        binding.nameTextView.setText("");
        binding.priceTextView.setText("");

        for (ChildItem dish : childItemArrayList) {
            String quantity = String.valueOf(dish.getItemQuantity()) + "\n";
            dataBuilder.append(quantity);



            String name = dish.getItemName() + "\n";
            dataBuilder1.append(name);

            String price = String.valueOf(dish.getItemPrice()) + "\n";
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




