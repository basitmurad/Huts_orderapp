package com.example.huts.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huts.R;
import com.example.huts.model.ChildItem;

import java.util.ArrayList;

public class CardAdapter2 extends RecyclerView.Adapter<CardAdapter2.MyHolder> {
    private Context context;
    private ArrayList<ChildItem> childItemArrayList;

    public CardAdapter2(Context context, ArrayList<ChildItem> childItemArrayList) {
        this.context = context;
        this.childItemArrayList = childItemArrayList;
    }

    @NonNull
    @Override
    public CardAdapter2.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter2.MyHolder holder, int position) {

        ChildItem childItem = childItemArrayList.get(position);



    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public MyHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
