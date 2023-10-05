package com.afaq.huts.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.afaq.huts.R;
import com.afaq.huts.model.BreakfastClass;
import com.afaq.huts.ui.CartsActivity;
import com.afaq.huts.ui.DbHelper;
import com.afaq.huts.ui.OrderActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class SpecialAdapter extends RecyclerView.Adapter<SpecialAdapter.MyHolder> {

    private Context context;
    private ArrayList<BreakfastClass> listBreakFast;

    private DbHelper dbHelper;

    public SpecialAdapter(Context context, ArrayList<BreakfastClass> listBreakFast) {
        this.context = context;
        this.listBreakFast = listBreakFast;


        dbHelper = new DbHelper(context.getApplicationContext());
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custum_breakfast, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        BreakfastClass breakfastClass = listBreakFast.get(position);

        holder.bItemName.setText(breakfastClass.getName());
        holder.bItemPrice.setText(breakfastClass.getPrice());


        holder.bItemPic.setImageResource(breakfastClass.getImageUri());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), OrderActivity.class);
                intent.putExtra("name" , breakfastClass.getName());
                intent.putExtra("price", breakfastClass.getPrice());


                context.startActivity(intent);
            }
        });
//        holder.btnCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = breakfastClass.getName();
//                int price = Integer.parseInt(breakfastClass.getPrice());
//                Bitmap imageBitmap = BitmapFactory.decodeResource(context.getResources(), breakfastClass.getImageUri());
//
//
//                byte[] imageByteArray = convertImageToByteArray(imageBitmap);
////
//
////                Toast.makeText(context, ""+imageByteArray, Toast.LENGTH_SHORT).show();
//                long result = dbHelper.insertDish(name, price, 1, imageByteArray, price, 1);
//
//                if (result != -1) {
//                    Intent intent = new Intent(context.getApplicationContext(), CartsActivity.class);
//                    v.getContext().startActivity(intent);
//
//                    Toast.makeText(context, "Add to cart", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(context, " Exist in cart", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


    }

    private byte[] convertImageToByteArray(Bitmap imageBitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }


    private byte[] convertImageResourceToByteArray(int imageResource) {
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageResource);

            // Convert the bitmap to a byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Exception while converting image to byte array", Toast.LENGTH_SHORT).show();
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return listBreakFast.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView bItemName, bItemPrice;
        ImageView bItemPic;
        Button btnCard;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            bItemName = itemView.findViewById(R.id.bItemName);
            bItemPrice = itemView.findViewById(R.id.bItemPrice);
            bItemPic = itemView.findViewById(R.id.bItemPic);
            btnCard = itemView.findViewById(R.id.btnCard);
        }
    }
}
