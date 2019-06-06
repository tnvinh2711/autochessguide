package com.zinzin.autochessguide.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zinzin.autochessguide.R;
import com.zinzin.autochessguide.model.Item;

import java.util.ArrayList;
import java.util.List;

public class CombineAdapter extends RecyclerView.Adapter<CombineAdapter.ViewHolder> {

    private Activity activity;
    private List<Item> itemList = new ArrayList<>();
    private List<String> combineList = new ArrayList<>();

    public CombineAdapter(Activity activity, List<Item> itemList,List<String> combineList) {
        this.activity = activity;
        this.itemList = itemList;
        this.combineList = combineList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_combine, viewGroup, false);
        return new CombineAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(combineList.get(position), position);
        String combine = combineList.get(position);
        for (Item item: itemList){
            if(item.getName().equals(combine)){
                viewHolder.ivIcon.setImageDrawable(activity.getResources().getDrawable(item.getImgItem()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return combineList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_item_combine);
        }

        void bind(final String combine, final int position) {
        }
    }
}
