package com.example.huts.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.huts.R;
import com.example.huts.adapters.BreakFastAdapter;
import com.example.huts.databinding.ActivityBreakFastBinding;
import com.example.huts.model.BreakfastClass;

import java.util.ArrayList;

public class BreakFastActivity extends AppCompatActivity {

    private ArrayList<BreakfastClass> list;
    private ArrayList<BreakfastClass> filteredList;
    private BreakFastAdapter adapter;
    private ActivityBreakFastBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBreakFastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list = new ArrayList<>();
        filteredList = new ArrayList<>();
list.clear();
filteredList.clear();


        list.add(new BreakfastClass("Lahori Chaney " , "120", R.drawable.lahorichaneyingle));
//        list.add(new BreakfastClass("Lahori Chaney Single" , "120", R.drawable.lahorichaneyingle));
        list.add(new BreakfastClass("Paratha" , "150", R.drawable.paratha));
        list.add(new BreakfastClass("Aloo Paratha" , "140", R.drawable.alooparatha));
        list.add(new BreakfastClass("Anda Fri" , "130", R.drawable.andafri));
        list.add(new BreakfastClass("Omlete" , "80", R.drawable.omlete));
        list.add(new BreakfastClass("Chaye" , "50", R.drawable.chaye));
        list.add(new BreakfastClass("Naan" , "30", R.drawable.naan));
//        list.add(new BreakfastClass("Lahori Kofta Chaney" , "110", R.drawable.lahorikoftachaney));
        list.add(new BreakfastClass("Lahori Kofta " , "110", R.drawable.lahorikoftachaney));
        list.add(new BreakfastClass("Sandwitch" , "180", R.drawable.sandwitch));
        list.add(new BreakfastClass("Anda Burji" , "90", R.drawable.andaburji));
        list.add(new BreakfastClass("Jam Malai" , "70", R.drawable.jammalai));
        list.add(new BreakfastClass("Roghni Naan" , "60", R.drawable.roghnnaan));

        filteredList.addAll(list);

        adapter = new BreakFastAdapter(this, filteredList);

        binding.recyclerBreakfast.setAdapter(adapter);
        binding.recyclerBreakfast.setLayoutManager(new LinearLayoutManager(this));

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BreakFastActivity.this, CartsActivity.class));
            }
        });

        binding.btnSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String query) {
        filteredList.clear();
        for (BreakfastClass item : list) {
            if (item.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }


        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No matching items found.", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }
}

















