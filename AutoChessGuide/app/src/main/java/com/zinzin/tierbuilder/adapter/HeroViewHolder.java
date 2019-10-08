package com.zinzin.tierbuilder.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zinzin.tierbuilder.R;

public class HeroViewHolder extends RecyclerView.ViewHolder {
    ImageView ivIconUnit, ivIcon1, ivIcon2, ivIcon3;
    TextView tvNameUnit, tvNameUnitDota;

    public HeroViewHolder(View itemView) {
        super(itemView);
        ivIconUnit = itemView.findViewById(R.id.iv_icon_unit);
        ivIcon1 = itemView.findViewById(R.id.iv_icon1);
        ivIcon2 = itemView.findViewById(R.id.iv_icon2);
        ivIcon3 = itemView.findViewById(R.id.iv_icon3);
        tvNameUnit = itemView.findViewById(R.id.tv_name);
        tvNameUnitDota = itemView.findViewById(R.id.tv_name_dota);
        tvNameUnitDota = itemView.findViewById(R.id.tv_name_dota);
    }
}
