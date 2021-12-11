package com.example.homeworkhelper.history;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkhelper.R;
import com.example.homeworkhelper.history.bean.RecordData;

import java.util.List;

public class MyDataAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<RecordData> dataList;
    private IOnItemClickListener itemClickListener;
    private Activity activity;

    public void refreshDataList() {
        for (int i = 0; i < dataList.size(); i++) {
            System.out.println(dataList.get(i).getSearch_id());
        }
        notifyDataSetChanged();
    }

    MyDataAdapter(List<RecordData> recordDataList, IOnItemClickListener onItemClickListener, Activity activity) {
        super();
        dataList = recordDataList;
        itemClickListener = onItemClickListener;
        this.activity = activity;
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
                itemClickListener.onItemClick(position, dataList.get(position), activity);
            }
        });
        holder.setLongClickListener((view)->{
            if ( itemClickListener != null) {
                itemClickListener.onItemClick(position, dataList.get(position), activity);
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
