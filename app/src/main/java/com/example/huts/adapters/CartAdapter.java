package com.example.huts.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.huts.R;
import com.example.huts.model.CartItem;
import com.example.huts.model.DishDetail;
import com.example.huts.ui.DbHelper;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {


    private Context context;
    private ArrayList<DishDetail> dishDetails;


    DbHelper dbHelper;


    public CartAdapter(Context context, ArrayList<DishDetail> dishDetails) {
        this.context = context;
        this.dishDetails = dishDetails;
        dbHelper = new DbHelper(context.getApplicationContext());
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DishDetail dishDetail = dishDetails.get(position);

        holder.itemName.setText(dishDetail.getName());
        holder.itemPrice.setText(String.valueOf(dishDetail.getPrice()));
        holder.cartItemPriceTOTAL.setText(String.valueOf(dishDetail.getPrice()));



        byte[] imageByteArray = dishDetail.getImageByteArray();
        Bitmap imageBitmap = convertByteArrayToBitmap(imageByteArray);
        holder.itemImage.setImageBitmap(imageBitmap);


        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DbHelper dbHelper = new DbHelper(context);
                dbHelper.deleteDish(dishDetail.getName());

                // Remove the item from the cartItems list
                dishDetails.remove(position);

                // Notify the adapter that an item has been removed
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dishDetails.size());

                // Show a toast or any other feedback indicating successful deletion
                Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
            }


        });



        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int item = Integer.parseInt(holder.itemQuantity.getText().toString());
                item++;

                holder.itemQuantity.setText(String.valueOf(item));

                int actualPrice = Integer.parseInt(holder.itemPrice.getText().toString());
                int totalPrice  = item*actualPrice;


                holder.cartItemPriceTOTAL.setText(String.valueOf(totalPrice));
                Toast.makeText(context, ""+totalPrice, Toast.LENGTH_SHORT).show();
                dbHelper.updateDishQuantityAndPrice(dishDetail.getName(),item, totalPrice);
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int item = Integer.parseInt(holder.itemQuantity.getText().toString());
                item--;

                if (item < 1) {  // Check if the quantity becomes less than 1
                    Toast.makeText(context, "Minimum quantity reached", Toast.LENGTH_SHORT).show();
                } else {
                    holder.itemQuantity.setText(String.valueOf(item));

                    int actualPrice = Integer.parseInt(holder.itemPrice.getText().toString());
                    int totalPrice = item * actualPrice;

                    holder.cartItemPriceTOTAL.setText(String.valueOf(totalPrice));
                    Toast.makeText(context, "" + totalPrice, Toast.LENGTH_SHORT).show();
                    dbHelper.updateDishQuantityAndPrice(dishDetail.getName(),item, totalPrice);
                }
            }
        });




    }





    private Bitmap convertByteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }


    @Override
    public int getItemCount() {
        return dishDetails.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName, itemPrice, itemQuantity, cartItemPriceTOTAL;
        ImageView btndelete, btnPlus, btnMinus;
        LinearLayout layout;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.cartItemPic);
            itemName = itemView.findViewById(R.id.cartItemName);
            itemPrice = itemView.findViewById(R.id.cartItemPrice);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            cartItemPriceTOTAL = itemView.findViewById(R.id.cartItemPriceTOTAL);
            btndelete = itemView.findViewById(R.id.btnDelete);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            layout = itemView.findViewById(R.id.linearLayout);
        }
    }


}
