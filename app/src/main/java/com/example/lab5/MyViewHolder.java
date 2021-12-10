package com.example.lab5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class Info {
    private String header, description;

    public Info(String header, String description) {
        this.header = header;
        this.description = description;
    }

    public String getHeader() {
        return header;
    }

    public String getDescription() {
        return description;
    }
}

public class MyViewHolder extends RecyclerView.ViewHolder {

    private TextView listItemDescription = (TextView) itemView.findViewById(R.id.description_item);
    private TextView listItemHeader = (TextView) itemView.findViewById(R.id.tv_item);

    public MyViewHolder(View v) {
        super(v);
    }

    protected void setText(Info data) {
        listItemDescription.setText(data.getDescription());
        listItemHeader.setText(data.getHeader());
    }

    protected boolean isHidden() {
        return listItemDescription.getVisibility() == View.GONE;
    }

    protected void showMore() {
        listItemDescription.setVisibility(View.VISIBLE);
    }

    protected void hideMore() {
        listItemDescription.setVisibility(View.GONE);
    }
}
