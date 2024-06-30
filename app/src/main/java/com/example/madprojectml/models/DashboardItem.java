package com.example.madprojectml.models;

// DashboardItem.java
public class DashboardItem {
    private int iconResId;
    private String title;

    public DashboardItem(int iconResId, String title) {
        this.iconResId = iconResId;
        this.title = title;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getTitle() {
        return title;
    }
}

