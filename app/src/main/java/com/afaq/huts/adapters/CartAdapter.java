package com.afaq.huts.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.afaq.huts.R;
import com.afaq.huts.model.DishDetail;
import com.afaq.huts.ui.DbHelper;

import java.io.ByteArrayOutputStream;
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
        holder.hutName.setText(dishDetail.getHutName());
        byte[] imageByteArray = dishDetail.getImageByteArray();


//        Toast.makeText(context, ""+dishDetail.getHutName(), Toast.LENGTH_SHORT).show();

        Bitmap imageBitmap1 = convertByteArrayToBitmap(imageByteArray);

        holder.itemImage.setImageBitmap(imageBitmap1);


        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DbHelper dbHelper = new DbHelper(context);
                dbHelper.deleteDish(dishDetail.getName());


                dishDetails.remove(position);


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
                int totalPrice = item * actualPrice;


                holder.cartItemPriceTOTAL.setText(String.valueOf(totalPrice));
//                Toast.makeText(context, ""+totalPrice, Toast.LENGTH_SHORT).show();
                dbHelper.updateDishQuantityAndPrice(dishDetail.getName(), item, totalPrice);

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
//                    Toast.makeText(context, "" + totalPrice, Toast.LENGTH_SHORT).show();
                    dbHelper.updateDishQuantityAndPrice(dishDetail.getName(), item, totalPrice);
                }
            }
        });


    }


    private Bitmap convertByteArrayToBitmap(byte[] byteArray) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
    }


    @Override
    public int getItemCount() {
        return dishDetails.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName, itemPrice, itemQuantity, cartItemPriceTOTAL, hutName;
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
            btndelete = itemView.findViewById(R.id.btnDelete);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            layout = itemView.findViewById(R.id.linearLayout);

            hutName = itemView.findViewById(R.id.hutname12);
        }
    }

    private Uri getImageUriFromBitmap(Context context, Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        // Insert the image into the MediaStore and get its path
        String imagePath = MediaStore.Images.Media.insertImage(
                context.getContentResolver(),
                image,
                "Title", // Change this to your desired title
                null
        );

        if (imagePath != null) {
            return Uri.parse(imagePath);
        } else {
            // Handle the case where imagePath is null (conversion failed)
            // For example, log an error message
            Log.e("CartAdapter", "Failed to convert Bitmap to Uri");
            return null;
        }
    }


}
