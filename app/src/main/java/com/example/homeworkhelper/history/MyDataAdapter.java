package com.example.homeworkhelper.history;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkhelper.R;

import java.util.List;

public class MyDataAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<RecordData> dataList;
    private IOnItemClickListener itemClickListener;

    MyDataAdapter(List<RecordData> recordDataList, IOnItemClickListener onItemClickListener) {
        super();
        dataList = recordDataList;
        itemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_record_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.onBind(position, dataList.get(position));
        holder.setOnClickListener((view)->{
            if (itemClickListener != null) {
                itemClickListener.onItemClick(position, dataList.get(position));
            }
        });
        holder.setLongClickListener((view)->{
            if ( itemClickListener != null) {
                itemClickListener.onItemClick(position, dataList.get(position));
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
