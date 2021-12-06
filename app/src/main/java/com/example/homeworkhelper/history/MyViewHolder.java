package com.example.homeworkhelper.history;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.homeworkhelper.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    private TextView classTextView;
    private TextView numTextView;
    private View contentView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        contentView = itemView;
        imageView = itemView.findViewById(R.id.image);
        classTextView = itemView.findViewById(R.id.result_class);
        numTextView = itemView.findViewById(R.id.result_num);
    }

    public void onBind(int position, RecordData data) {
//        imageView.setImageBitmap(data.getBitmap());
        Glide.with(contentView).load(data.getUrl()).into(imageView);
        classTextView.setText(data.getClassName());
        numTextView.setText("共" + data.getResultNum() + "条记录");
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
