package com.example.madprojectml;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class ImageSelectionActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;

    private ImageView imageViewSelected;
    private Uri imageUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private String moduleType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_image_selection);

        imageViewSelected = findViewById(R.id.imageViewSelected);

        Intent intent = getIntent();
        moduleType = intent.getStringExtra("MODULE_TYPE");

        switch (moduleType) {
            case "ImageClassification":
                storageReference = FirebaseStorage.getInstance().getReference("image_classification_images");
                databaseReference = FirebaseDatabase.getInstance().getReference("image_classification_images");
                break;
            case "FlowerClassification":
                storageReference = FirebaseStorage.getInstance().getReference("flower_classification_images");
                databaseReference = FirebaseDatabase.getInstance().getReference("flower_classification_images");
                break;
            case "ObjectDetection":
                storageReference = FirebaseStorage.getInstance().getReference("object_detection_images");
                databaseReference = FirebaseDatabase.getInstance().getReference("object_detection_images");
                break;
            case "FaceDetection":
                storageReference = FirebaseStorage.getInstance().getReference("face_detection_images");
                databaseReference = FirebaseDatabase.getInstance().getReference("face_detection_images");
                break;
            default:
                throw new IllegalArgumentException("Invalid module type");
        }

        findViewById(R.id.buttonSelectImage).setOnClickListener(v -> openFileChooser());
        findViewById(R.id.buttonOpenCamera).setOnClickListener(v -> openCamera());
        findViewById(R.id.buttonSave).setOnClickListener(v -> uploadImage());
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageViewSelected.setImageURI(imageUri);
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageUri = getImageUri(imageBitmap);
            imageViewSelected.setImageURI(imageUri);
        }
    }

    private Uri getImageUri(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, UUID.randomUUID().toString(), null);
        return Uri.parse(path);
    }

    private String getFileExtension(Uri uri) {
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(getContentResolver().getType(uri));
    }

    private void uploadImage() {
        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            fileReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                String uploadId = databaseReference.push().getKey();
                databaseReference.child(uploadId).setValue(uri.toString());
                Toast.makeText(ImageSelectionActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
            })).addOnFailureListener(e -> Toast.makeText(ImageSelectionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}
