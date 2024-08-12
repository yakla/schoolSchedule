package com.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends  RecyclerView.Adapter {

    private List<String> mData;
    private LayoutInflater mInflater;

    public RecyclerViewAdapter(Context context, List<String> data) {
        mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =   mInflater.inflate(R.layout.recycler_view_text, parent, false);
        return new MyViewHolder(itemView);

    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String data = mData.get(position);
        MyViewHolder myHolder = (MyViewHolder) holder;
        myHolder.textView.setText(data);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
