package com.zinzin.tierbuilder.adapter;

import android.app.Activity;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zinzin.tierbuilder.R;
import com.zinzin.tierbuilder.model.Units;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class HeaderRecyclerViewSection extends StatelessSection {
    private static final String TAG = HeaderRecyclerViewSection.class.getSimpleName();
    private String title;
    private List<Units> list;
    private Activity activity;
    private int colorTitle;
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public HeaderRecyclerViewSection(Activity activity, String title, List<Units> list, int colorTitle) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.units_item)
                .headerResourceId(R.layout.header_hero_layout)
                .build());
        this.activity = activity;
        this.title = title;
        this.list = list;
        this.colorTitle = colorTitle;
    }

    @Override
    public int getContentItemsTotal() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new HeroViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final HeroViewHolder viewHolder = (HeroViewHolder) holder;
        final Units units = list.get(position);
        if (units.getBuff() == 0) {
            viewHolder.ivIconUnit.setBackgroundResource(R.drawable.border_background_buff);
            viewHolder.tvStat.setBackgroundResource(R.drawable.background_buff);
            viewHolder.tvStat.setText("BUFFED");
            viewHolder.tvStat.setVisibility(View.VISIBLE);
        } else if (units.getNerf() == 0) {
            viewHolder.ivIconUnit.setBackgroundResource(R.drawable.border_background_neft);
            viewHolder.tvStat.setBackgroundResource(R.drawable.background_nerf);
            viewHolder.tvStat.setText("NERFED");
            viewHolder.tvStat.setVisibility(View.VISIBLE);
        } else if (units.getUpdated() == 0) {
            viewHolder.ivIconUnit.setBackgroundResource(R.drawable.border_background_updated);
            viewHolder.tvStat.setBackgroundResource(R.drawable.background_updated);
            viewHolder.tvStat.setText("UPDATED");
            viewHolder.tvStat.setVisibility(View.VISIBLE);
        } else if (units.getBuff() != 0 && units.getNerf() != 0 && units.getUpdated() != 0) {
            viewHolder.ivIconUnit.setBackgroundResource(R.drawable.border_background);
            viewHolder.tvStat.setVisibility(View.GONE);
        }
        if (units.getNew() == 0) {
            viewHolder.ivNew.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivNew.setVisibility(View.GONE);
        }
        Glide.with(activity).load(units.getUrl_icon_image()).apply(RequestOptions.circleCropTransform()).into(viewHolder.ivIconUnit);
        viewHolder.tvNameUnit.setText(units.getName());
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
        viewHolder.tvNameUnitDota.setText("(" + units.getDotaConvert() + ")");
        if(units.getClass_image()!= null){
            Glide.with(activity).load(units.getClass_image()).into(viewHolder.ivIcon1);
            viewHolder.ivIcon1.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivIcon1.setVisibility(View.GONE);
        }
        if(units.getRace_image()!= null){
            Glide.with(activity).load(units.getRace_image()).into(viewHolder.ivIcon2);
            viewHolder.ivIcon2.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivIcon2.setVisibility(View.GONE);
        }
        if (units.getRace_image2()!= null) {
            viewHolder.ivIcon3.setVisibility(View.VISIBLE);
            Glide.with(activity).load(units.getRace_image2()).into(viewHolder.ivIcon3);
        } else {
            viewHolder.ivIcon3.setVisibility(View.GONE);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.OnItemClick(units, position);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder hHolder = (HeaderViewHolder) holder;
        hHolder.headerTitle.setText(title);
        hHolder.headerTitle.setBackgroundColor(activity.getResources().getColor(colorTitle));
    }

    public interface OnItemClickListener {
        void OnItemClick(Units item, int position);
    }
}
