package com.example.madprojectml.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.madprojectml.models.ImageData;
import com.example.madprojectml.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context context;
    private List<ImageData> imageDataList;
    private DatabaseReference databaseReference;

    public ImageAdapter(Context context, List<ImageData> imageDataList, String moduleType) {
        this.context = context;
        this.imageDataList = imageDataList;
        switch (moduleType) {
            case "ImageClassification":
                this.databaseReference = FirebaseDatabase.getInstance().getReference("image_classification_images");
                break;
            case "FlowerClassification":
                this.databaseReference = FirebaseDatabase.getInstance().getReference("flower_classification_images");
                break;
            case "ObjectDetection":
                this.databaseReference = FirebaseDatabase.getInstance().getReference("object_detection_images");
                break;
            case "FaceDetection":
                this.databaseReference = FirebaseDatabase.getInstance().getReference("face_detection_images");
                break;
            default:
                throw new IllegalArgumentException("Invalid module type");
        }
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_card, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageData imageData = imageDataList.get(position);
        holder.textViewAccuracy.setText(imageData.getAccuracy());
        Glide.with(context).load(imageData.getImageUrl()).into(holder.imageView);

        // Example of deleting an image (long press)
        holder.itemView.setOnLongClickListener(v -> {
            deleteImage(imageData.getImageUrl());
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return imageDataList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewAccuracy;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewAccuracy = itemView.findViewById(R.id.textViewAccuracy);
        }
    }

    private void deleteImage(String imageId) {
        databaseReference.child(imageId).removeValue()
                .addOnSuccessListener(aVoid -> Toast.makeText(context, "Image deleted successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(context, "Failed to delete image: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
