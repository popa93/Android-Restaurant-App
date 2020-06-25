package com.example.restaurantapp.Helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantapp.UI.OrderActivity;
import com.example.restaurantapp.R;

import java.util.ArrayList;

public
class RecyclerOrderAdapter extends RecyclerView.Adapter<RecyclerOrderAdapter.ViewHolder> {

    ArrayList<Object> list=new ArrayList<>();
    OnItemClickListener onItemClickListener;

    public RecyclerOrderAdapter(ArrayList<Object> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View textView= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_textview_item,parent,false);

        return new ViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        View view=holder.itemView;
        StringBuilder stringBuilder;
        StringBuilder stringBuilder2;

        TextView textView =view.findViewById(R.id.textView);
        // textView=view.findViewById(R.id.recyclerTextView);
        //TextView price=view.findViewById(R.id.priceView);

        if(OrderActivity.orderList.get(position) instanceof Pizza){

            stringBuilder=new StringBuilder();
            stringBuilder.append((((Pizza) OrderActivity.orderList.get(position)).getName()));
            stringBuilder.append(" ");
            stringBuilder.append((((Pizza) OrderActivity.orderList.get(position)).getPrice()));

            textView.setText(stringBuilder);
        //price.setText(((Pizza) OrderActivity.orderList.get(position)).getPrice());
        }
        else {
            stringBuilder2=new StringBuilder();
            stringBuilder2.append((((Drink) OrderActivity.orderList.get(position)).getName()));
            stringBuilder2.append(" ");
            stringBuilder2.append((((Drink) OrderActivity.orderList.get(position)).getPrice()));

            textView.setText(stringBuilder2);
            //price.setText(((Drink) OrderActivity.orderList.get(position)).getPrice());
        }
    }

    @Override
    public int getItemCount() {
        return OrderActivity.orderList.size();
    }

    public interface OnItemClickListener{
        void onDeleteClick(int position);

    }

    public void setClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView deleteView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            deleteView=view.findViewById(R.id.imageView);

            deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    onItemClickListener.onDeleteClick(position);

                }
            });
        }
    }
}
