package com.example.huts.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.huts.R;
import com.example.huts.adapters.HutAdapter;
import com.example.huts.databinding.ActivityHutsBinding;
import com.example.huts.model.CartItem;
import com.example.huts.model.HutsClass;


import java.util.ArrayList;

public class HutsActivity extends AppCompatActivity {

    private ActivityHutsBinding binding;
    private ArrayList<HutsClass> list ;
    private HutAdapter hutAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHutsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        list = new ArrayList<>();
        list.add(new HutsClass("Majeed Hut" , R.drawable.chaneychat));
        list.add(new HutsClass("Chemistry Hut" , R.drawable.chaneychat));
        list.add(new HutsClass("Social Hut" , R.drawable.chaneychat));
        list.add(new HutsClass("NIPS Hut" , R.drawable.chaneychat));
        list.add(new HutsClass("Faizan Hut" , R.drawable.chaneychat));
        list.add(new HutsClass("Hikmat Hut" , R.drawable.chaneychat));
        list.add(new HutsClass("Paradise Hut" , R.drawable.chaneychat));
        list.add(new HutsClass("Bio Hut" , R.drawable.chaneychat));
        list.add(new HutsClass("Shabbir Hut" , R.drawable.chaneychat));
        list.add(new HutsClass("Daniyal Hut" , R.drawable.chaneychat));
        list.add(new HutsClass("Mphil Hut" , R.drawable.chaneychat));




        hutAdapter =new HutAdapter(this, list);

        binding.hutRecycler.setAdapter(hutAdapter);
        binding.hutRecycler.setLayoutManager(new LinearLayoutManager(this));



        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }



}