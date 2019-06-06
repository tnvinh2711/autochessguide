package com.zinzin.autochessguide.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zinzin.autochessguide.R;
import com.zinzin.autochessguide.model.Creep;
import com.zinzin.autochessguide.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Activity activity;
    private List<Item> itemList = new ArrayList<>();

    public ItemAdapter(Activity activity, List<Item> itemList) {
        this.activity = activity;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_items, viewGroup, false);
        return new ItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(itemList.get(position), position);
        Item item = itemList.get(position);
        viewHolder.tvName.setText(item.getName() + " ( " + item.getDotaConvert() + " ) ");
        viewHolder.ivIcon.setImageDrawable(activity.getResources().getDrawable(item.getImgItem()));
        List<String> desItem = item.getBonus();
        StringBuilder stringDes = new StringBuilder();
        for (int i = 0; i < desItem.size(); i++) {
            String des = desItem.get(i) + "\n";
            stringDes.append(des);
        }
        String desc = stringDes.toString();
        desc = desc.replace("(", "( ").replace(")", " )");
        viewHolder.tvDes.setText(desc.substring(0, desc.length() - 2));
        CombineAdapter combineAdapter = new CombineAdapter(activity, itemList, item.getCombine());
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        viewHolder.rcvCombine.setLayoutManager(layoutManager);
        viewHolder.rcvCombine.setAdapter(combineAdapter);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivIcon;
        TextView tvDes;
        RecyclerView rcvCombine;

        public ViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_items);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDes = itemView.findViewById(R.id.tv_des);
            rcvCombine = itemView.findViewById(R.id.rcv_combine);
        }

        void bind(final Item item, final int position) {
        }
    }
}
