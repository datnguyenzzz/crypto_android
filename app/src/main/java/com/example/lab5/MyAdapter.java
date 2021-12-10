package com.example.lab5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<Info> data;

    public MyAdapter() {
        this.data = new ArrayList<>();
    }

    public void clearData() {
        this.data = new ArrayList<>();
    }

    public void addData(DataRow dataRow, View view) {
        SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        Date date = new Date(Long.parseLong(dataRow.time) * 1000);
        String nDate = df.format(date);

        String time = nDate + "\n ";

        String low = view.getResources().getString(R.string.low)
                + ": "
                + dataRow.low + "\n ";

        String high = view.getResources().getString(R.string.high)
                + ": "
                + dataRow.high + "\n ";

        String open = view.getResources().getString(R.string.open)
                + ": "
                + dataRow.open + "\n ";

        String close = view.getResources().getString(R.string.close)
                + ": "
                + dataRow.close + "\n ";

        String head = time;
        String tail = time + open + high + low + close ;
        this.data.add(new Info(head,tail));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_data, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolder.isHidden()) {
                    viewHolder.showMore();
                }
                else {
                    viewHolder.hideMore();
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int pos) {
        holder.hideMore();
        holder.setText(data.get(pos));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
