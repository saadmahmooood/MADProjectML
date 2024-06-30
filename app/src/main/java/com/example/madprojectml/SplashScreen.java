package com.example.madprojectml;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    ImageView ivLogo;
    TextView tvSlogan;
    Animation logoAnim, sloganAnim;
    private static int SPLASH_TIMEOUT = 3000; // 3 seconds

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        init();

        ivLogo.setAnimation(logoAnim);
        tvSlogan.setAnimation(sloganAnim);

        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                // User is signed in, navigate to Home activity
                Intent intent = new Intent(SplashScreen.this, Home.class);
                startActivity(intent);
            } else {
                // No user is signed in, navigate to MainActivity
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
            }
            finish();
        }, SPLASH_TIMEOUT);
    }

    private void init() {
        ivLogo = findViewById(R.id.ivLogo);
        tvSlogan = findViewById(R.id.tvSlogan);
        logoAnim = AnimationUtils.loadAnimation(this, R.anim.logo_anim);
        sloganAnim = AnimationUtils.loadAnimation(this, R.anim.slogan_anim);
        mAuth = FirebaseAuth.getInstance();
    }
}
