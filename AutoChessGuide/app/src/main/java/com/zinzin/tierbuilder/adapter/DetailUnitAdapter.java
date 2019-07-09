package com.zinzin.tierbuilder.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zinzin.tierbuilder.R;
import com.zinzin.tierbuilder.model.UnitsInfo;

import java.util.ArrayList;
import java.util.List;

public class DetailUnitAdapter extends RecyclerView.Adapter<DetailUnitAdapter.ViewHolder> {

    private Activity activity;
    private List<UnitsInfo> infos = new ArrayList<>();

    public DetailUnitAdapter(Activity activity, List<UnitsInfo> infos) {
        this.activity = activity;
        this.infos = infos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.detail_units_items, viewGroup, false);
        return new DetailUnitAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(infos.get(position), position);
        UnitsInfo unitsInfo = infos.get(position);
        Glide.with(activity).load(unitsInfo.getImgInfo()).into(viewHolder.ivIcon);
        viewHolder.tvName.setText(unitsInfo.getName());
        viewHolder.tvType.setText(unitsInfo.getType());
        String desc = unitsInfo.getDes();
        desc = desc.replace("(","( ").replace(")"," )");
        viewHolder.tvDes.setText(desc);
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvName;
        TextView tvType;
        TextView tvDes;

        public ViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvName = itemView.findViewById(R.id.tv_name);
            tvType = itemView.findViewById(R.id.tv_type);
            tvDes = itemView.findViewById(R.id.tv_des);
        }

        void bind(final UnitsInfo item, final int position) {
        }
    }
}
