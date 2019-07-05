package com.zinzin.tierbuilder.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zinzin.tierbuilder.R;

public class HeroViewHolder extends RecyclerView.ViewHolder {
    ImageView ivIconUnit, ivNew, ivIcon1, ivIcon2, ivIcon3,ivTier;
    TextView tvNameUnit, tvNameUnitDota,tvStat;

    public HeroViewHolder(View itemView) {
        super(itemView);
        ivIconUnit = itemView.findViewById(R.id.iv_icon_unit);
        ivNew = itemView.findViewById(R.id.img_new);
        ivIcon1 = itemView.findViewById(R.id.iv_icon1);
        ivIcon2 = itemView.findViewById(R.id.iv_icon2);
        ivIcon3 = itemView.findViewById(R.id.iv_icon3);
        ivTier = itemView.findViewById(R.id.img_tier);
        tvNameUnit = itemView.findViewById(R.id.tv_name);
        tvNameUnitDota = itemView.findViewById(R.id.tv_name_dota);
        tvStat = itemView.findViewById(R.id.tv_stat);
        tvNameUnitDota = itemView.findViewById(R.id.tv_name_dota);
    }
}
