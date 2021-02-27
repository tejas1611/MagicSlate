package com.tejas.magicslate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private String username;
    private TextView nameDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        username = getIntent().getStringExtra("username");
        nameDisplay = findViewById(R.id.nameDisplay);
        nameDisplay.setText("Welcome, " + username);
    }

    public void onClickLogout(View view) {
        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(i);
        HomeActivity.this.finish();
    }

    public void onClickLessons(View view) {
        Intent i = new Intent(HomeActivity.this, LessonActivity.class);
        startActivity(i);
    }
}
