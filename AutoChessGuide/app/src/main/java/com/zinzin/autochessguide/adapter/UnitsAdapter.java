//package com.zinzin.autochessguide.adapter;
//
//import android.app.Activity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//public class UnitsAdapter extends RecyclerView.Adapter<UnitsAdapter.ViewHolder> {
//
//    private Activity activity;
//    private List<Units> unitsList;
//    private OnItemClickListener listener;
//
//    public UnitsAdapter(Activity activity, List<Units> unitsList) {
//        this.activity = activity;
//        this.unitsList = unitsList;
//    }
//
//    public void setListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext())
//                .inflate(R.layout.item_phone_card_price_layout, viewGroup, false);
//        return new UnitsAdapter.ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
//        viewHolder.bind(unitsList.get(position), position, listener);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return unitsList.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        TextView tvPrice;
//        TextView tvShortPrice;
//        View layout;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            layout = itemView.findViewById(R.id.layout_item_card_price);
//            tvPrice = itemView.findViewById(R.id.tv_item_price);
//            tvShortPrice = itemView.findViewById(R.id.tv_item_short_price);
//        }
//
//        void bind(final Units item, final int position, final OnItemClickListener listener) {
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    itemView.setClickable(false);
//                    if (listener != null)
//                        listener.OnItemClick(item, position);
//                }
//            });
//        }
//    }
//
//
//    public interface OnItemClickListener {
//        void OnItemClick(Units item, int position);
//    }
//}
