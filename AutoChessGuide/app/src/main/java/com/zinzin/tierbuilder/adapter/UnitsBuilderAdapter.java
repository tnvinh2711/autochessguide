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
import com.zinzin.tierbuilder.model.Units;

import java.util.ArrayList;
import java.util.List;

public class UnitsBuilderAdapter extends RecyclerView.Adapter<UnitsBuilderAdapter.ViewHolder> {

    private Activity activity;
    private List<Units> unitsList = new ArrayList<>();
    private OnItemClickListener listener;

    public UnitsBuilderAdapter(Activity activity, List<Units> unitsList) {
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
                .inflate(R.layout.units_item_bottom_sheet, viewGroup, false);
        return new UnitsBuilderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(unitsList.get(position), position, listener);
        Units units = unitsList.get(position);
        Glide.with(activity).load(units.getUrl_icon_image()).into(viewHolder.ivIconUnit);

        viewHolder.tvNameUnit.setText(units.getName());

        viewHolder.tvNameUnitDota.setText("(" + units.getDotaConvert() + ")");
        switch (units.getCost()){
            case "1":
                viewHolder.tvNameUnit.setTextColor(activity.getResources().getColor(R.color.color_cost_1));
                viewHolder.tvNameUnitDota.setTextColor(activity.getResources().getColor(R.color.color_cost_1));
                break;
            case "2":
                viewHolder.tvNameUnit.setTextColor(activity.getResources().getColor(R.color.color_cost_2));
                viewHolder.tvNameUnitDota.setTextColor(activity.getResources().getColor(R.color.color_cost_2));
                break;
            case "3":
                viewHolder.tvNameUnit.setTextColor(activity.getResources().getColor(R.color.color_cost_3));
                viewHolder.tvNameUnitDota.setTextColor(activity.getResources().getColor(R.color.color_cost_3));
                break;
            case "4":
                viewHolder.tvNameUnit.setTextColor(activity.getResources().getColor(R.color.color_cost_4));
                viewHolder.tvNameUnitDota.setTextColor(activity.getResources().getColor(R.color.color_cost_4));
                break;
            case "5":
                viewHolder.tvNameUnit.setTextColor(activity.getResources().getColor(R.color.color_cost_5));
                viewHolder.tvNameUnitDota.setTextColor(activity.getResources().getColor(R.color.color_cost_5));
                break;

        }
    }

    public void updateList(List<Units> list) {
        unitsList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return unitsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIconUnit;
        TextView tvNameUnit, tvNameUnitDota;

        public ViewHolder(View itemView) {
            super(itemView);
            ivIconUnit = itemView.findViewById(R.id.iv_icon_unit);
            tvNameUnit = itemView.findViewById(R.id.tv_name);
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
