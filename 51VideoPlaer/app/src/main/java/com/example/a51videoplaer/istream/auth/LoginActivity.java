package com.example.a51videoplaer.istream.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a51videoplaer.R;
import com.example.a51videoplaer.istream.database.AppDatabase;
import com.example.a51videoplaer.istream.database.UserEntity;
import com.example.a51videoplaer.istream.home.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    TextView username, password;
    Button loginBtn, goToSignUpBtn;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);

        loginBtn = findViewById(R.id.btnLogin);
        goToSignUpBtn = findViewById(R.id.btnSignup);

        db = AppDatabase.getInstance(this);

        loginBtn.setOnClickListener(v -> {

            String user = username.getText().toString();
            String pass = password.getText().toString();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Enter credentials", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {

                UserEntity loggedUser = db.userDao().login(user, pass);

                runOnUiThread(() -> {

                    if (loggedUser != null) {

                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(this, HomeActivity.class);
                        intent.putExtra("userId", loggedUser.id);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show();
                    }
                });

            }).start();
        });

        goToSignUpBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });
    }
}