package com.example.madprojectml.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectml.models.DashboardItem;
import com.example.madprojectml.modules.FaceDetection;
import com.example.madprojectml.modules.FlowerClassification;
import com.example.madprojectml.modules.ImageClassificationActivity;
import com.example.madprojectml.modules.ObjectDetection;
import com.example.madprojectml.R;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private List<DashboardItem> items;
    private Context context;

    public DashboardAdapter(Context context, List<DashboardItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DashboardItem item = items.get(position);
        holder.featureIcon.setImageResource(item.getIconResId());
        holder.featureTitle.setText(item.getTitle());

        // Set click listener to handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to respective activity based on item position
                switch (position) {
                    case 0:
                        // Navigate to Image Classification Activity
                        Intent imageIntent = new Intent(context, ImageClassificationActivity.class);
                        context.startActivity(imageIntent);
                        break;
                    case 1:
                        // Navigate to Flower Classification Activity
                        Intent flowerIntent = new Intent(context, FlowerClassification.class);
                        context.startActivity(flowerIntent);
                        break;
                    case 2:
                        // Navigate to Flower Classification Activity
                        Intent objectIntent = new Intent(context, ObjectDetection.class);
                        context.startActivity(objectIntent);
                        break;
                    case 3:
                        // Navigate to Flower Classification Activity
                        Intent faceIntent = new Intent(context, FaceDetection.class);
                        context.startActivity(faceIntent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView featureIcon;
        public TextView featureTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            featureIcon = itemView.findViewById(R.id.feature_icon);
            featureTitle = itemView.findViewById(R.id.feature_title);
        }
    }
}