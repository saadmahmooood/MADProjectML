package com.example.madprojectml.models;

public class ImageData {
    private String imageUrl;
    private String accuracy;

    // Default constructor required for calls to DataSnapshot.getValue(ImageData.class)
    public ImageData() {
    }

    public ImageData(String imageUrl, String accuracy) {
        this.imageUrl = imageUrl;
        this.accuracy = accuracy;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }
}
