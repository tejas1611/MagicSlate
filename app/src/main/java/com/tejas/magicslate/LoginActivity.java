package com.tejas.magicslate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private static final String USERNAME = "TEJAS01";
    private static final String PASSWORD = "Tejas1611";

    private EditText usernameEntry;
    private EditText passwordEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEntry = findViewById(R.id.email);
        passwordEntry = findViewById(R.id.password);
    }

    public void onClickSignUp(View view) {
        Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(i);
        LoginActivity.this.finish();
    }

    public void onClickLogin(View view) {
        String username = usernameEntry.getText().toString();
        String password = passwordEntry.getText().toString();
        System.out.println(username + ' ' + password);
        if(username.equals(USERNAME) && password.equals(PASSWORD)) {
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            i.putExtra("username", USERNAME);
            startActivity(i);
            LoginActivity.this.finish();
        }
        else if(username!=USERNAME) {
            Toast.makeText(this,"Invalid Username", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Invalid Password", Toast.LENGTH_SHORT).show();
        }
    }
}
