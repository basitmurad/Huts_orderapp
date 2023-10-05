package com.afaq.huts.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.afaq.huts.R;
import com.afaq.huts.adapters.HutAdapter;

import com.afaq.huts.databinding.ActivityHutsBinding;
import com.afaq.huts.model.HutsClass;


import java.util.ArrayList;

public class HutsActivity extends AppCompatActivity {

    private ActivityHutsBinding binding;
    private ArrayList<HutsClass> list ;
    private ArrayList<HutsClass> filterList;
    private HutAdapter hutAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHutsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        list = new ArrayList<>();
        filterList = new ArrayList<>();

        list.clear();
        filterList.clear();

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
        list.add(new HutsClass("Quetta cafe" , R.drawable.chaneychat));
        list.add(new HutsClass("Qau cafe" , R.drawable.chaneychat));
        list.add(new HutsClass("Ahmed Food point\n(hut special)" , R.drawable.chaneychat));






        filterList.addAll(list);
        hutAdapter =new HutAdapter(this, filterList);

        binding.hutRecycler.setAdapter(hutAdapter);
        binding.hutRecycler.setLayoutManager(new LinearLayoutManager(this));



        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        filterList.clear();
        for (HutsClass item : list) {
            if (item.getHutsName().toLowerCase().contains(query.toLowerCase())) {
                filterList.add(item);
            }
        }


        if (filterList.isEmpty()) {
            Toast.makeText(this, "No matching items found.", Toast.LENGTH_SHORT).show();
        }
        hutAdapter.notifyDataSetChanged();
    }

}