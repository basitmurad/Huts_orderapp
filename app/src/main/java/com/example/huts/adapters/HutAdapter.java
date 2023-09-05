package com.example.huts.adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huts.R;
import com.example.huts.SessionManager;
import com.example.huts.model.HutsClass;
import com.example.huts.model.OrderDetails;
import com.example.huts.ui.OrderActivity;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HutAdapter extends RecyclerView.Adapter<HutAdapter.MyHolder> {
    private Context context;

    private ArrayList<HutsClass> list ;
    private SessionManager sessionManager;

    public HutAdapter(Context context, ArrayList<HutsClass> list) {
        this.context = context;
        this.list = list;
        sessionManager = new SessionManager(context.getApplicationContext());
    }

    @NonNull
    @Override
    public HutAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.huts_layout,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HutAdapter.MyHolder holder, int position) {
        HutsClass hutsClass = list.get(position);
        holder.hutName.setText(hutsClass.getHutsName());

        holder.hutImage.setImageResource(hutsClass.getImageUri());




        holder.btnHut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.saveHutName(hutsClass.getHutsName());

                Intent intent = new Intent(context.getApplicationContext(), OrderActivity.class);
                intent.putExtra("hutName" , hutsClass.getHutsName());
                intent.putExtra("imageUri",hutsClass.getImageUri());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView hutName ;
        ImageView hutImage;
        Button btnHut;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            hutImage = itemView.findViewById(R.id.hutImage);
            hutName = itemView .findViewById(R.id.hutname);
            btnHut = itemView.findViewById(R.id.btnHuts);
        }
    }
}
