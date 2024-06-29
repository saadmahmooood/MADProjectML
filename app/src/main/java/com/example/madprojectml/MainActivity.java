package com.example.madprojectml;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
FragmentManager manager;
Fragment loginFrag, signupFrag;
    View LoginFragView, SignupFragView;
    TextView tvLogin, tvSignup;
    TextInputEditText etEmailS, etPassS, etRePassS, etEmailL, etPassL;
    AppCompatButton btnSignup, btnLogin, btnCancelS, btnCancelL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
init();
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction().hide(loginFrag).show(signupFrag).commit();
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction().hide(signupFrag).show(loginFrag).commit();
            }
        });
    }

    private void init() {

        manager = getSupportFragmentManager();
        loginFrag = manager.findFragmentById(R.id.fragLogin);
        signupFrag = manager.findFragmentById(R.id.fragSignUp);
        LoginFragView = loginFrag.getView();
        SignupFragView = signupFrag.getView();

        tvSignup = LoginFragView.findViewById(R.id.tvSignUp);
        etEmailL = LoginFragView.findViewById(R.id.etEmail);
        etPassL = LoginFragView.findViewById(R.id.etPassword);
        btnCancelL = LoginFragView.findViewById(R.id.btnCancel);
        btnLogin = LoginFragView.findViewById(R.id.btnLogin);

        tvLogin = SignupFragView.findViewById(R.id.tvLogin);
        etEmailS = SignupFragView.findViewById(R.id.etEmail);
        etPassS = SignupFragView.findViewById(R.id.etPassword);
        etRePassS = SignupFragView.findViewById(R.id.etRePassword);
        btnCancelS = SignupFragView.findViewById(R.id.btnCancel);
        btnSignup = SignupFragView.findViewById(R.id.btnSignup);

        manager.beginTransaction().show(loginFrag).hide(signupFrag).commit();
    }
}