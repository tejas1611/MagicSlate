package com.tejas.magicslate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tejas.magicslate.lessons.Lesson3;

public class LessonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
    }

    public void onClickLesson(View view) {
        String class_to_invoke = "";
        System.out.println(view.getId());
        System.out.println(R.id.l0);
        switch (view.getId()) {
            case R.id.l0:
                class_to_invoke = "com.tejas.magicslate.lessons.Lesson0";
                break;
            case R.id.l1:
                class_to_invoke = "com.tejas.magicslate.lessons.Lesson1";
                break;
            case R.id.l2:
                class_to_invoke = "com.tejas.magicslate.lessons.Lesson2";
                break;
            case R.id.l3:
                class_to_invoke = "com.tejas.magicslate.lessons.Lesson3";
                break;
            case R.id.l4:
                class_to_invoke = "com.tejas.magicslate.lessons.Lesson4";
                break;
            case R.id.l5:
                class_to_invoke = "com.tejas.magicslate.lessons.Lesson5";
                break;
            case R.id.l6:
                class_to_invoke = "com.tejas.magicslate.lessons.Lesson6";
                break;
            case R.id.l7:
                class_to_invoke = "com.tejas.magicslate.lessons.Lesson7";
                break;
        }
        try {
            Intent i = new Intent(LessonActivity.this, Class.forName(class_to_invoke));
            startActivity(i);
        } catch (ClassNotFoundException e) {
            Toast.makeText(this,"Not Available Right Now", Toast.LENGTH_SHORT).show();
        }
    }
}
