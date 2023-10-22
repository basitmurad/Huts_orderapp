package com.afaq.huts.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.afaq.huts.R;
import com.afaq.huts.SessionManager;
import com.afaq.huts.details.BioHutsActivity;
import com.afaq.huts.details.DaniyalHutsActivity;
import com.afaq.huts.details.FaizanHutsActivity;
import com.afaq.huts.details.HikmatHutsActivity;
import com.afaq.huts.details.HnineActivity;
import com.afaq.huts.details.MajeedHutsActivity;
import com.afaq.huts.details.MphilCanteenActivity;
import com.afaq.huts.details.ParadiseHutsActivity;
import com.afaq.huts.details.QuaCafeActivity;
import com.afaq.huts.details.QuettaCafeActivity;
import com.afaq.huts.details.ShabbirHutsActivity;
import com.afaq.huts.details.SocialHutActivity;
import com.afaq.huts.model.HutsClass;
import com.afaq.huts.ui.OrderActivity;
import com.afaq.utils.GetDateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HutAdapter extends RecyclerView.Adapter<HutAdapter.MyHolder> {
    private Context context;

    private ArrayList<HutsClass> list ;
    private SessionManager sessionManager;
    private GetDateTime getDateTime;
    private String time ;

    public HutAdapter(Context context, ArrayList<HutsClass> list) {
        this.context = context;
        this.list = list;
        sessionManager = new SessionManager(context.getApplicationContext());
        getDateTime = new GetDateTime((Activity) context);
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
        holder.hutTime.setText(hutsClass.getHutTiming());


        getDateTime.getCurrentDateTime(new GetDateTime.TimeCallBack() {
            @Override
            public void getDateTime(String date, String time) {
                // Check if the current time is within the opening hours of the hut
                if (isHutOpen(hutsClass, time)) {
                    holder.timimg.setText("Open");
                    holder.timimg.setTextColor(ContextCompat.getColor(context, R.color.green));
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String hutName = hutsClass.getHutsName();

                            if (hutName.equals("Daniyal Hut")) {

                                Intent intent = new Intent(context, DaniyalHutsActivity.class);
                                intent.putExtra("hutname" ,hutsClass.getHutsName());
                                context.startActivity(intent);


                            } else if (hutName.equals("Qau Cafe")) {
                                Intent intent = new Intent(context, QuaCafeActivity.class);
                                intent.putExtra("hutname" ,hutsClass.getHutsName());
                                context.startActivity(intent);
                            }
                            else if (hutName.equals("Majeed Hut")) {
                                Intent intent = new Intent(context, MajeedHutsActivity.class);
                                intent.putExtra("hutname" ,hutsClass.getHutsName());

                                context.startActivity(intent);
                            }
                            else if (hutName.equals("Social Hut")) {
                                Intent intent = new Intent(context, SocialHutActivity.class);
                                intent.putExtra("hutname" ,hutsClass.getHutsName());
                                context.startActivity(intent);
                            }
                            else if (hutName.equals("Paradise Hut")) {
                                Intent intent = new Intent(context, ParadiseHutsActivity.class);
                                intent.putExtra("hutname" ,hutsClass.getHutsName());
                                context.startActivity(intent);

                            }


                            else if (hutName.equals("Mphil Canteen")) {
                                Intent intent = new Intent(context, MphilCanteenActivity.class);
                                intent.putExtra("hutname" ,hutsClass.getHutsName());
                                context.startActivity(intent);
                            }

                            else if (hutName.equals("Quetta Cafe")) {
                                Intent intent = new Intent(context, QuettaCafeActivity.class);
                                intent.putExtra("hutname" ,hutsClass.getHutsName());
                                context.startActivity(intent);
                            }


                            else if (hutName.equals("Faizan Hut")) {
                                Intent intent = new Intent(context, FaizanHutsActivity.class);
                                intent.putExtra("hutname" ,hutsClass.getHutsName());
                                context.startActivity(intent);
                            }



                            else if (hutName.equals("Hikmat Hut")) {
                                Intent intent = new Intent(context, HikmatHutsActivity.class);
                                intent.putExtra("hutname" ,hutsClass.getHutsName());
                                context.startActivity(intent);
                            }

                            else if (hutName.equals("Shabbir Hut")) {
                                Intent intent = new Intent(context, ShabbirHutsActivity.class);
                                intent.putExtra("hutname" ,hutsClass.getHutsName());
                                context.startActivity(intent);
                            }

                            else if (hutName.equals("Bio Hut")) {
                                Intent intent = new Intent(context, BioHutsActivity.class);
                                intent.putExtra("hutname" ,hutsClass.getHutsName());
                                context.startActivity(intent);
                            }

                            else if (hutName.equals("H9 Canteen")) {
                                Intent intent = new Intent(context, HnineActivity.class);
                                intent.putExtra("hutname" ,hutsClass.getHutsName());
                                context.startActivity(intent);
                            }
                            else if (hutName.equals("Bio Hut")) {
                                Intent intent = new Intent(context, BioHutsActivity.class);
                                intent.putExtra("hutname" ,hutsClass.getHutsName());
                                context.startActivity(intent);
                            }
                        }
                    });
                } else {
                    holder.timimg.setText("Closed");
                    holder.timimg.setTextColor(ContextCompat.getColor(context, in.aabhasjindal.otptextview.R.color.red));
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "Service time is closed for this hut.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String hutName = hutsClass.getHutsName();
//
//                if (hutName.equals("Daniyal Hut")) {
//
//                    Intent intent = new Intent(context, DaniyalHutsActivity.class);
//                    intent.putExtra("hutname" ,hutsClass.getHutsName());
//                    context.startActivity(intent);
//
//
//                } else if (hutName.equals("Qau Cafe")) {
//                    Intent intent = new Intent(context, QuaCafeActivity.class);
//                    intent.putExtra("hutname" ,hutsClass.getHutsName());
//                    context.startActivity(intent);
//                }
//                else if (hutName.equals("Majeed Hut")) {
//                    Intent intent = new Intent(context, MajeedHutsActivity.class);
//                    intent.putExtra("hutname" ,hutsClass.getHutsName());
//
//                    context.startActivity(intent);
//                }
//                else if (hutName.equals("Social Hut")) {
//                    Intent intent = new Intent(context, SocialHutActivity.class);
//                    intent.putExtra("hutname" ,hutsClass.getHutsName());
//                    context.startActivity(intent);
//                }
//                else if (hutName.equals("Paradise Hut")) {
//                    Intent intent = new Intent(context, ParadiseHutsActivity.class);
//                    intent.putExtra("hutname" ,hutsClass.getHutsName());
//                    context.startActivity(intent);
//
//                }
//
//
//                else if (hutName.equals("Mphil Canteen")) {
//                    Intent intent = new Intent(context, MphilCanteenActivity.class);
//                    intent.putExtra("hutname" ,hutsClass.getHutsName());
//                    context.startActivity(intent);
//                }
//
//                else if (hutName.equals("Quetta Cafe")) {
//                    Intent intent = new Intent(context, QuettaCafeActivity.class);
//                    intent.putExtra("hutname" ,hutsClass.getHutsName());
//                    context.startActivity(intent);
//                }
//
//
//                else if (hutName.equals("Faizan Hut")) {
//                    Intent intent = new Intent(context, FaizanHutsActivity.class);
//                    intent.putExtra("hutname" ,hutsClass.getHutsName());
//                    context.startActivity(intent);
//                }
//
//
//
//                else if (hutName.equals("Hikmat Hut")) {
//                    Intent intent = new Intent(context, HikmatHutsActivity.class);
//                    intent.putExtra("hutname" ,hutsClass.getHutsName());
//                    context.startActivity(intent);
//                }
//
//                else if (hutName.equals("Shabbir Hut")) {
//                    Intent intent = new Intent(context, ShabbirHutsActivity.class);
//                    intent.putExtra("hutname" ,hutsClass.getHutsName());
//                    context.startActivity(intent);
//                }
//
//                else if (hutName.equals("Bio Hut")) {
//                    Intent intent = new Intent(context, BioHutsActivity.class);
//                    intent.putExtra("hutname" ,hutsClass.getHutsName());
//                    context.startActivity(intent);
//                }
//
//                else if (hutName.equals("H9 Canteen")) {
//                    Intent intent = new Intent(context, HnineActivity.class);
//                    intent.putExtra("hutname" ,hutsClass.getHutsName());
//                    context.startActivity(intent);
//                }
//                else if (hutName.equals("Bio Hut")) {
//                    Intent intent = new Intent(context, BioHutsActivity.class);
//                    intent.putExtra("hutname" ,hutsClass.getHutsName());
//                    context.startActivity(intent);
//                }
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView hutName , hutTime , timimg;
        ImageView hutImage;
        Button btnHut;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            hutImage = itemView.findViewById(R.id.hutImage);
            hutName = itemView .findViewById(R.id.hutname);
            hutTime = itemView .findViewById(R.id.hutTiming);
            timimg = itemView.findViewById(R.id.status);
//            btnHut = itemView.findViewById(R.id.btnHuts);
        }
    }
    private boolean isHutOpen(HutsClass hutsClass, String currentTime) {
        String[] timing = hutsClass.getHutTiming().split("to");
        String openingTime = timing[0].trim();
        String closingTime = timing[1].trim();

        // Perform time comparison logic based on your time format
        // Ensure that currentTime is formatted the same way as openingTime and closingTime
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());

        try {
            Date currentTimeDate = sdf.parse(currentTime);
            Date openingTimeDate = sdf.parse(openingTime);
            Date closingTimeDate = sdf.parse(closingTime);

            return currentTimeDate.after(openingTimeDate) && currentTimeDate.before(closingTimeDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

}
