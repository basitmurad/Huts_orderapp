package com.example.huts.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huts.R;
import com.example.huts.model.ChildItem;
import com.example.huts.model.ParentItem;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParentAdpter extends RecyclerView.Adapter<ParentAdpter.MyHolder> {

    private Activity activity;
    private ArrayList<ParentItem> parentItemArrayList;
    private ArrayList<ChildItem> childItemArrayList;
    private  ArrayList<Boolean> nestedVisibilityList;

    public ParentAdpter(Activity activity, ArrayList<ParentItem> parentItemArrayList, ArrayList<ChildItem> childItemArrayList) {
        this.activity = activity;
        this.parentItemArrayList = parentItemArrayList;
        this.childItemArrayList = childItemArrayList;
        nestedVisibilityList = new ArrayList<>();
        for (int i = 0; i < parentItemArrayList.size(); i++) {
            nestedVisibilityList.add(false); // Initialize all as invisible
        }
    }

    @NonNull
    @Override
    public ParentAdpter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist,parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentAdpter.MyHolder holder, @SuppressLint("RecyclerView") int position) {

        ParentItem parentItem =     parentItemArrayList.get(position);
        holder.orderId.setText(parentItem.getOrderId());
        holder.itemPrice.setText(parentItem.getTotalPrice());
//        holder.itemQuant.setText(parentItem.get());
//        holder.ivParent.setImageResource(parentItem.getImageId());



        holder.nestedRecy.setVisibility(View.GONE);
        ChildAdapter childAdapter = new ChildAdapter(childItemArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        holder.nestedRecy.setLayoutManager(linearLayoutManager);
        holder.nestedRecy.setAdapter(childAdapter);



        final boolean isNestedVisible = nestedVisibilityList.get(position);
        holder.nestedRecy.setVisibility(isNestedVisible ? View.VISIBLE : View.GONE);

        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nestedVisibilityList.set(position, !isNestedVisible);
                notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return parentItemArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView orderId, itemQuant, itemPrice,click;
        CircleImageView ivParent;
        RecyclerView nestedRecy;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            orderId = itemView.findViewById(R.id.pId);
            itemQuant = itemView.findViewById(R.id.pQ);
//            itemPrice = itemView.findViewById(R.id.Pp);
            ivParent = itemView.findViewById(R.id.ivparent);
            nestedRecy = itemView.findViewById(R.id.nested_rv);
//            click = itemView.findViewById(R.id.click);
        }
    }
}
