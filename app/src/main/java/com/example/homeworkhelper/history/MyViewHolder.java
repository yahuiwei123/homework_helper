package com.example.homeworkhelper.history;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkhelper.R;
import com.example.homeworkhelper.history.bean.RecordData;

public class MyViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    private TextView classTextView;
    private TextView numTextView;
    private View contentView;
    private String[] className = {"语文", "数学", "英语"};

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        contentView = itemView;
        imageView = itemView.findViewById(R.id.image);
        classTextView = itemView.findViewById(R.id.result_class);
        numTextView = itemView.findViewById(R.id.result_num);
    }

    public void onBind(int position, RecordData data) {
//        imageView.setImageBitmap(data.getBitmap());
        classTextView.setText(className[data.getQues_class() - 1]);
        numTextView.setText("共" + data.getAns_num() + "条记录");
    }

    public void setOnClickListener(View.OnClickListener listener) {
        if(listener != null) {
            contentView.setOnClickListener(listener);
        }
    }

    public void setLongClickListener(View.OnLongClickListener listener) {
        if(listener != null) {
            contentView.setOnLongClickListener(listener);
        }
    }
}
