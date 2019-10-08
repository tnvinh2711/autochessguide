package com.zinzin.tierbuilder.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zinzin.tierbuilder.R;
import com.zinzin.tierbuilder.model.Builder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuilderAdapter extends RecyclerView.Adapter<BuilderAdapter.ViewHolder> {

    private Activity activity;
    private List<Builder> builderList = new ArrayList<>();
    private OnItemClickListener listener;

    public BuilderAdapter(Activity activity, List<Builder> builderList) {
        this.activity = activity;
        this.builderList = builderList;
    }
    public void updateList(List<Builder> list) {
        builderList = list;
        notifyDataSetChanged();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_save_team, viewGroup, false);
        return new BuilderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        viewHolder.bind(builderList.get(position), position);
        final Builder builder = builderList.get(position);
        viewHolder.tvName.setText(builder.getName_team());
        viewHolder.tvType.setText(builder.getType());
        UnitsBuilderAdapter unitsBuilderAdapter = new UnitsBuilderAdapter(activity, builder.getUnitList());
        GridLayoutManager adapterManager = new GridLayoutManager(activity, 5);
        viewHolder.rcvUnit.setLayoutManager(adapterManager);
        viewHolder.rcvUnit.setAdapter(unitsBuilderAdapter);
        viewHolder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnEditClick(builder.getName_team(), position);
            }
        });
        viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnDeteleClick(position);
            }
        });
        viewHolder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = createBitmapFromView(viewHolder.llCapture);
                try {
                    File cachePath = new File(activity.getCacheDir(), "images");
                    cachePath.mkdirs(); // don't forget to make the directory
                    FileOutputStream stream = new FileOutputStream(cachePath + "/image"+ builder.getName_team() + ".png"); // overwrites this image every time
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    stream.close();
                    File imagePath = new File(activity.getCacheDir(), "images");
                    File newFile = new File(imagePath, "/image"+ builder.getName_team() + ".png");
                    Uri contentUri = FileProvider.getUriForFile(activity, "com.zinzin.tierbuilder.fileprovider", newFile);

                    if (contentUri != null) {
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
                        shareIntent.setDataAndType(contentUri, activity.getContentResolver().getType(contentUri));
                        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                        activity.startActivity(Intent.createChooser(shareIntent, "Choose an app"));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return builderList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvType;
        ImageView ivEdit;
        ImageView ivDelete;
        ImageView ivShare;
        RecyclerView rcvUnit;
        LinearLayout llCapture;

        public ViewHolder(View itemView) {
            super(itemView);
            ivEdit = itemView.findViewById(R.id.iv_edit);
            ivDelete = itemView.findViewById(R.id.iv_close);
            tvName = itemView.findViewById(R.id.tv_name);
            ivShare = itemView.findViewById(R.id.iv_share);
            llCapture = itemView.findViewById(R.id.ll_capture);
            tvType = itemView.findViewById(R.id.tv_type);
            rcvUnit = itemView.findViewById(R.id.rcv_unit);
        }

        void bind(final Builder builder, final int position) {
        }
    }

    public interface OnItemClickListener {
        void OnEditClick(String name, int position);
        void OnDeteleClick(int position);
    }
    private Bitmap createBitmapFromView(View v) {
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(v.getMeasuredWidth(),
                v.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(bitmap);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return bitmap;
    }

}
