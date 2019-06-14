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
import com.bumptech.glide.request.RequestOptions;
import com.zinzin.tierbuilder.R;
import com.zinzin.tierbuilder.model.Units;

import java.util.ArrayList;
import java.util.List;

public class UnitsTierAdapter extends RecyclerView.Adapter<UnitsTierAdapter.ViewHolder> {

    private Activity activity;
    private List<Units> unitsList = new ArrayList<>();
    private OnItemClickListener listener;

    public UnitsTierAdapter(Activity activity, List<Units> unitsList) {
        this.activity = activity;
        this.unitsList = unitsList;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.units_item, viewGroup, false);
        return new UnitsTierAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(unitsList.get(position), position, listener);
        Units units = unitsList.get(position);
        if(units.getBuff() == 0){
            viewHolder.ivIconUnit.setBackgroundResource(R.drawable.border_background_buff);
            viewHolder.tvStat.setBackgroundResource(R.drawable.background_buff);
            viewHolder.tvStat.setText("BUFFED");
            viewHolder.tvStat.setVisibility(View.VISIBLE);
        } else if(units.getNerf() == 0){
            viewHolder.ivIconUnit.setBackgroundResource(R.drawable.border_background_neft);
            viewHolder.tvStat.setBackgroundResource(R.drawable.background_nerf);
            viewHolder.tvStat.setText("NERFED");
            viewHolder.tvStat.setVisibility(View.VISIBLE);
        }else if(units.getUpdated() == 0){
            viewHolder.ivIconUnit.setBackgroundResource(R.drawable.border_background_updated);
            viewHolder.tvStat.setBackgroundResource(R.drawable.background_updated);
            viewHolder.tvStat.setText("UPDATED");
            viewHolder.tvStat.setVisibility(View.VISIBLE);
        } else if(units.getBuff()!= 0 && units.getNerf() != 0 && units.getUpdated() != 0){
            viewHolder.ivIconUnit.setBackgroundResource(R.drawable.border_background);
            viewHolder.tvStat.setVisibility(View.GONE);
        }
        if(units.getNew() == 0){
            viewHolder.ivNew.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivNew.setVisibility(View.GONE);
        }
        Glide.with(activity).load(activity.getResources().getDrawable(units.getIcon_image())).apply(RequestOptions.circleCropTransform()).into(viewHolder.ivIconUnit);
        viewHolder.tvNameUnit.setText(units.getName());
        viewHolder.tvNameUnit.setTextColor(activity.getResources().getColor(units.getColor_name()));
        viewHolder.tvNameUnitDota.setText("("+units.getDotaConvert()+")");
        viewHolder.tvNameUnitDota.setTextColor(activity.getResources().getColor(units.getColor_name()));
        viewHolder.ivIcon1.setImageDrawable(activity.getResources().getDrawable(units.getClass_image()));
        viewHolder.ivIcon2.setImageDrawable(activity.getResources().getDrawable(units.getRace_image()));
        if(units.getRace().size()>1){
            viewHolder.ivIcon3.setVisibility(View.VISIBLE);
            viewHolder.ivIcon3.setImageDrawable(activity.getResources().getDrawable(units.getRace_image2()));
        } else {
            viewHolder.ivIcon3.setVisibility(View.GONE);
        }
        if(units.getTierUp() == 0){
            viewHolder.ivTier.setVisibility(View.VISIBLE);
            viewHolder.ivTier.setImageDrawable(activity.getResources().getDrawable(R.mipmap.up));
        } else if(units.getTierDown() == 0){
            viewHolder.ivTier.setVisibility(View.VISIBLE);
            viewHolder.ivTier.setImageDrawable(activity.getResources().getDrawable(R.mipmap.down));
        }if(units.getTierUp() != 0 && units.getTierDown() != 0){
            viewHolder.ivTier.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return unitsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIconUnit, ivNew, ivIcon1, ivIcon2, ivIcon3,ivTier;
        TextView tvNameUnit, tvNameUnitDota,tvStat;

        public ViewHolder(View itemView) {
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

        void bind(final Units item, final int position, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemView.setClickable(false);
                    if (listener != null)
                        listener.OnItemClick(item, position);
                    notifyDataSetChanged();
                }
            });
        }
    }


    public interface OnItemClickListener {
        void OnItemClick(Units item, int position);
    }
}
