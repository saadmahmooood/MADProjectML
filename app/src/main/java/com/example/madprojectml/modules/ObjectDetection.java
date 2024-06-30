package com.example.madprojectml.modules;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectml.Adapters.ImageAdapter;
import com.example.madprojectml.ImageSelectionActivity;
import com.example.madprojectml.R;
import com.example.madprojectml.models.ImageData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ObjectDetection extends AppCompatActivity {

    private RecyclerView recyclerViewImages;
    private FloatingActionButton fabAddImage;
    private List<ImageData> imageDataList;
    private ImageAdapter imageAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_detection);

        recyclerViewImages = findViewById(R.id.recyclerViewImages);
        fabAddImage = findViewById(R.id.fabAddImage);
        databaseReference = FirebaseDatabase.getInstance().getReference("object_images");

        recyclerViewImages.setLayoutManager(new LinearLayoutManager(this));
        imageDataList = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, imageDataList);
        recyclerViewImages.setAdapter(imageAdapter);

        fabAddImage.setOnClickListener(v -> openImageSelectionForm());

        loadImagesFromFirebase();
    }

    private void loadImagesFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imageDataList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String imageUrl = postSnapshot.getValue(String.class);
                    imageDataList.add(new ImageData(imageUrl, "90%")); // Adjust accuracy as needed
                }
                imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ObjectDetection.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openImageSelectionForm() {
        Intent intent = new Intent(this, ImageSelectionActivity.class);
        startActivity(intent);
    }
}
