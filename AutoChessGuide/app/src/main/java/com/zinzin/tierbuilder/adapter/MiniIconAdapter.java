package com.zinzin.tierbuilder.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zinzin.tierbuilder.R;

import java.util.ArrayList;
import java.util.List;

public class MiniIconAdapter extends RecyclerView.Adapter<MiniIconAdapter.ViewHolder> {

    private Activity activity;
    private List<String> imgList = new ArrayList<>();

    public MiniIconAdapter(Activity activity, List<String> imgList) {
        this.activity = activity;
        this.imgList = imgList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mini_icon_item, viewGroup, false);
        return new MiniIconAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(imgList.get(position), position);
        String icon = imgList.get(position);
        if (imgList.size() > 0) {
            Glide.with(activity).load(icon).into(viewHolder.ivIcon);
            viewHolder.ivIcon.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivIcon.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_icon);
        }

        void bind(final String item, final int position) {
        }
    }
}
