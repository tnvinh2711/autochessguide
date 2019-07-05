package com.zinzin.tierbuilder.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zinzin.tierbuilder.R;


public class HeaderViewHolder extends RecyclerView.ViewHolder{
    public TextView headerTitle;
    public HeaderViewHolder(View itemView) {
        super(itemView);
        headerTitle = (TextView)itemView.findViewById(R.id.header_id);
    }
}
