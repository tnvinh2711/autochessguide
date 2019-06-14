package com.zinzin.tierbuilder.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zinzin.tierbuilder.R;
import com.zinzin.tierbuilder.model.Creep;

import java.util.ArrayList;
import java.util.List;

public class CreepsAdapter extends RecyclerView.Adapter<CreepsAdapter.ViewHolder> {

    private Activity activity;
    private List<Creep> creepList = new ArrayList<>();

    public CreepsAdapter(Activity activity, List<Creep> creepList) {
        this.activity = activity;
        this.creepList = creepList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.creeps_items, viewGroup, false);
        return new CreepsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(creepList.get(position), position);
        Creep creep = creepList.get(position);
        viewHolder.tvRound.setText("Round: "+ creep.getRound());
        viewHolder.tvName.setText(creep.getName());
        List<String> desCreeps = creep.getCreeps();
        StringBuilder stringDes = new StringBuilder();
        for(int i = 0; i < desCreeps.size(); i++){
            String des = desCreeps.get(i) +"\n";
            stringDes.append(des);
        }
        String desc = stringDes.toString();
        desc = desc.replace("(","( ").replace(")"," )");
        viewHolder.tvDes.setText(desc);
    }

    @Override
    public int getItemCount() {
        return creepList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRound;
        TextView tvName;
        TextView tvDes;

        public ViewHolder(View itemView) {
            super(itemView);
            tvRound = itemView.findViewById(R.id.tv_round);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDes = itemView.findViewById(R.id.tv_des);
        }

        void bind(final Creep item, final int position) {
        }
    }
}
