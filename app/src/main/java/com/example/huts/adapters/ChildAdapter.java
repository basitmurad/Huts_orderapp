package com.example.huts.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huts.R;
import com.example.huts.model.ChildItem;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChildAdapter  extends RecyclerView.Adapter<ChildAdapter.MyHolder> {
    private ArrayList<ChildItem> childItemArrayList;

    public ChildAdapter(ArrayList<ChildItem> childItemArrayList) {
        this.childItemArrayList = childItemArrayList;
    }

    @NonNull
    @Override
    public ChildAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_child,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildAdapter.MyHolder holder, int position) {

        ChildItem childItem = childItemArrayList.get(position);


    }

    @Override
    public int getItemCount() {
        return childItemArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2,tv3;
        CircleImageView imageView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.tvItemName);
            tv2 = itemView.findViewById(R.id.tvpricechild);
            tv3 = itemView.findViewById(R.id.tvqtychild);
//            imageView = itemView.findViewById(R.id.ivChild);
        }
    }
}
