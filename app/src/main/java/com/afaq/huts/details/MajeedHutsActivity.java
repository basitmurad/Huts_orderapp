package com.afaq.huts.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.afaq.huts.R;
import com.afaq.huts.adapters.BreakFastAdapter;
import com.afaq.huts.databinding.ActivityMajeedHutsBinding;
import com.afaq.huts.model.BreakfastClass;
import com.afaq.huts.ui.CartsActivity;

import java.util.ArrayList;

public class MajeedHutsActivity extends AppCompatActivity {
    private ActivityMajeedHutsBinding binding;
    private ArrayList<BreakfastClass> list;
    private ArrayList<BreakfastClass> filteredList;
    private BreakFastAdapter adapter;
    private String hutName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMajeedHutsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        hutName= getIntent().getStringExtra("hutname");
//        Toast.makeText(this, ""+hutName, Toast.LENGTH_SHORT).show();

        list = new ArrayList<>();
        filteredList = new ArrayList<>();
        list.clear();
        filteredList.clear();



        list.add(new BreakfastClass("Chowmein", "350", R.drawable.chowin));
        list.add(new BreakfastClass("Manchurian w rice", "380", R.drawable.manchurianrice));
        list.add(new BreakfastClass("Chilli dry w rice", "380", R.drawable.chillirice));
        list.add(new BreakfastClass("Shashlik w rice", "380", R.drawable.shashlikrice));
        list.add(new BreakfastClass("Egg fried rice", "220", R.drawable.eggfiedrice));
        list.add(new BreakfastClass("Beef kabab", "110", R.drawable.beefkabab));
        list.add(new BreakfastClass("BBQ", "250", R.drawable.chickenbbqpieces));
        list.add(new BreakfastClass("Qourma", "180", R.drawable.chickenqorma));
        list.add(new BreakfastClass("Qeema", "180", R.drawable.aluqeema));
        list.add(new BreakfastClass("Kaliji fri", "180", R.drawable.kalijifri));
        list.add(new BreakfastClass("Sabzi", "120", R.drawable.sabzimix));
        list.add(new BreakfastClass("Chicken biryani", "220", R.drawable.chickenbiryani));
        list.add(new BreakfastClass("Beef kabab fri", "220", R.drawable.beefkabab));
        list.add(new BreakfastClass("Laziza", "180", R.drawable.lobiasada));
        list.add(new BreakfastClass("Lobiya", "120", R.drawable.lobiasada));
        list.add(new BreakfastClass("Aalu special", "120", R.drawable.aluspecial));
        list.add(new BreakfastClass("Daal", "120", R.drawable.daal));
        list.add(new BreakfastClass("Roti", "20", R.drawable.roti));
        list.add(new BreakfastClass("Chai", "20", R.drawable.chaye));

        filteredList.addAll(list);



        adapter = new BreakFastAdapter(this, filteredList, hutName);

        binding.MajRec.setAdapter(adapter);
        binding.MajRec.setLayoutManager(new LinearLayoutManager(this));

        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MajeedHutsActivity.this, CartsActivity.class));
            }
        });
        SearchView btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set focus on the SearchView
                btnSearch.setIconified(false);

                // Show the keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(btnSearch, InputMethodManager.SHOW_IMPLICIT);
            }
        });

// Set the query listener as you have in your code
        binding.btnSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle query text changes
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