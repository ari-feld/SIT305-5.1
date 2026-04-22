package com.example.a51videoplaer.istream.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a51videoplaer.R;
import com.example.a51videoplaer.istream.database.AppDatabase;
import com.example.a51videoplaer.istream.database.UserEntity;

public class SignUpActivity extends AppCompatActivity {

    EditText fullName, username, password, confirmPassword;
    Button signUpBtn, goToLoginBtn;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullName = findViewById(R.id.etFullName);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        confirmPassword = findViewById(R.id.etConfirm);

        signUpBtn = findViewById(R.id.btnSignup);
        goToLoginBtn = findViewById(R.id.btnLogin);

        db = AppDatabase.getInstance(this);

        signUpBtn.setOnClickListener(v -> {

            String name = fullName.getText().toString();
            String user = username.getText().toString();
            String pass = password.getText().toString();
            String confirm = confirmPassword.getText().toString();

            if (name.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pass.equals(confirm)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {

                UserEntity existing = db.userDao().checkUser(user);

                runOnUiThread(() -> {

                    if (existing != null) {
                        Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        new Thread(() -> {
                            UserEntity newUser = new UserEntity();
                            newUser.fullName = name;
                            newUser.username = user;
                            newUser.password = pass;

                            db.userDao().insert(newUser);

                            runOnUiThread(() -> {
                                Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(this, LoginActivity.class));
                                finish();
                            });

                        }).start();
                    }
                });

            }).start();
        });

        goToLoginBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
}