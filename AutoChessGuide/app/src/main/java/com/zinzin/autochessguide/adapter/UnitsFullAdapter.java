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
import com.zinzin.autochessguide.model.Units;
import com.zinzin.autochessguide.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class UnitsFullAdapter extends RecyclerView.Adapter<UnitsFullAdapter.ViewHolder> {

    private Activity activity;
    private List<Units> unitsList = new ArrayList<>();
    private OnItemClickListener listener;

    public UnitsFullAdapter(Activity activity, List<Units> unitsList) {
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
        return new UnitsFullAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(unitsList.get(position), position, listener);
        Units units = unitsList.get(position);
        viewHolder.ivIconUnit.setImageDrawable(activity.getResources().getDrawable(units.getIcon_image()));
        viewHolder.tvNameUnit.setText(units.getName());
        viewHolder.tvNameUnitDota.setText("("+units.getDotaConvert()+")");
    }
    public void updateList(List<Units> list){
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
