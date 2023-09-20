package com.afaq.huts.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.afaq.huts.R;
import com.afaq.huts.adapters.BreakFastAdapter;
import com.afaq.huts.databinding.ActivityLunchAndDinnerBinding;
import com.afaq.huts.model.BreakfastClass;

import java.util.ArrayList;

public class LunchAndDinnerActivity extends AppCompatActivity {

    private ActivityLunchAndDinnerBinding binding;

    private ArrayList<BreakfastClass> list ;
    private ArrayList<BreakfastClass> filterList;
    private BreakFastAdapter breakFastAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLunchAndDinnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        list = new ArrayList<>();
        filterList = new ArrayList<>();

        list.clear();
        filterList.clear();


//        list.add(new BreakfastClass("Chicken Biryani","200", R.drawable.chickenbiryani));
//        list.add(new BreakfastClass("Mutton Pulao","250", R.drawable.muttonpulao));
//        list.add(new BreakfastClass("Boiled Rice","100", R.drawable.boiledrice));
//        list.add(new BreakfastClass("Beef Pulao","250", R.drawable.beefpulao));
//        list.add(new BreakfastClass("Kabuli Pulao","250", R.drawable.kabulipulao));
//        list.add(new BreakfastClass("Vegetable Pulao","100", R.drawable.vegetablepulao));
//        list.add(new BreakfastClass("Chicken Qorma","100", R.drawable.chickenqorma));
//        list.add(new BreakfastClass("Chicken Haleem","100", R.drawable.chickenhaleem));
//        list.add(new BreakfastClass("Lahori Kofta ","100", R.drawable.lahorikoftachaney));
//
//        list.add(new BreakfastClass("Lobia","100", R.drawable.lobia));
//        list.add(new BreakfastClass("Sabzi (Mix)","100", R.drawable.sabzimix));
//        list.add(new BreakfastClass("Dal Chaney","100", R.drawable.dalchaney));
//        list.add(new BreakfastClass("Dal Mash","100", R.drawable.dalmash));
//        list.add(new BreakfastClass("Chicken Jalferzi","100", R.drawable.chickenjalferzi));
//        list.add(new BreakfastClass("Kari Lakora","100", R.drawable.karilakora));
//        list.add(new BreakfastClass("Nehari","100", R.drawable.nehari));
//        list.add(new BreakfastClass("Chicken BBQ Pieces","100", R.drawable.chickenbbqpieces));
//        list.add(new BreakfastClass("Chappal Kabab","100", R.drawable.chappalkabab));
//        list.add(new BreakfastClass("Beef Karahi","100", R.drawable.beefkarahi));
//        list.add(new BreakfastClass("Mutton Karahi","100", R.drawable.muttonkarahi));
//        list.add(new BreakfastClass("Chicken Laziza","100", R.drawable.chickenlaziza));
//        list.add(new BreakfastClass("Crisis","100", R.drawable.crisis));
//        list.add(new BreakfastClass("Alu Anda","100", R.drawable.aluanda));
//        list.add(new BreakfastClass("Lobia Anda","100", R.drawable.lobiaanda));
//        list.add(new BreakfastClass("Seekh Kabab","100", R.drawable.seekhkabab));
//        list.add(new BreakfastClass("Chowmein","380", R.drawable.chowmin));
//        list.add(new BreakfastClass("Egg Fried Rice","100", R.drawable.eggfiedrice));
//        list.add(new BreakfastClass("Chicken Gried Rice","100", R.drawable.chickengriedrice));
//        list.add(new BreakfastClass("Manchurian Rice","100", R.drawable.manchurianrice));
// Assuming you have already initialized your ArrayList<BreakfastClass> list

        list.add(new BreakfastClass("Chowmein", "380", R.drawable.chowmin));
        list.add(new BreakfastClass("Egg fried rice", "350", R.drawable.eggfiedrice));
        list.add(new BreakfastClass("Manchurian Rice", "350", R.drawable.manchurianrice));
        list.add(new BreakfastClass("Seekh kabab", "80", R.drawable.seekhkabab));
        list.add(new BreakfastClass("Aalu anda", "100", R.drawable.aluanda));
        list.add(new BreakfastClass("Lobya anda", "100", R.drawable.lobiaanda));
        list.add(new BreakfastClass("Chicken laziza", "150", R.drawable.chickenlaziza));
        list.add(new BreakfastClass("Chicken BBQ", "180", R.drawable.chickenbbqpieces));
        list.add(new BreakfastClass("Crisis", "150", R.drawable.crisis));
        list.add(new BreakfastClass("Chappal kabab", "100", R.drawable.chappalkabab));
        list.add(new BreakfastClass("Daal mash", "100", R.drawable.dalmash));
        list.add(new BreakfastClass("Chicken jalfrezi", "180", R.drawable.chickenjalferzi));
        list.add(new BreakfastClass("Kari pakora", "130", R.drawable.karilakora));
        list.add(new BreakfastClass("Lahori kofta", "140", R.drawable.lahorikoftachaney));
        list.add(new BreakfastClass("Sabzi", "120", R.drawable.sabzimix));
        list.add(new BreakfastClass("Daal chaney", "100", R.drawable.dalchaney));
        list.add(new BreakfastClass("Kabuli pulao", "250", R.drawable.kabulipulao));
        list.add(new BreakfastClass("Chicken Qourma", "180", R.drawable.chickenqorma));
        list.add(new BreakfastClass("Haleem", "120", R.drawable.chickenhaleem));
        list.add(new BreakfastClass("Chicken biryani", "200", R.drawable.chickenbiryani));
        list.add(new BreakfastClass("Mutton pulao", "250", R.drawable.muttonpulao));
        list.add(new BreakfastClass("Beef pulao", "250", R.drawable.beefpulao));
















        filterList.addAll(list);

        breakFastAdapter = new BreakFastAdapter(this, filterList);

        binding.recyclerLunch.setAdapter(breakFastAdapter);

        binding.recyclerLunch.setLayoutManager(new LinearLayoutManager(this));

        binding.btnSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Filter(newText);
                return false;
            }
        });


        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LunchAndDinnerActivity.this, CartsActivity.class));
            }
        });

    }

    private void Filter(String newText) {

        filterList.clear();

        for (BreakfastClass breakfastClass : list)
        {
            if (breakfastClass.getName().toLowerCase().contains(newText.toLowerCase()))
            {
                filterList.add(breakfastClass);
            }
        }

        if (filterList.isEmpty()) {
            Toast.makeText(this, "No matching items found.", Toast.LENGTH_SHORT).show();
        }
        breakFastAdapter.notifyDataSetChanged();
    }
}