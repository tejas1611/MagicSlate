package com.tejas.magicslate.lessons;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tejas.magicslate.R;
import com.tejas.magicslate.lessons.lesson3.ExercisesFragment;
import com.tejas.magicslate.lessons.lesson3.PrerequisitesFragment;
import com.tejas.magicslate.lessons.lesson3.TutorialsFragment;
import com.tejas.magicslate.lessons.lesson3.WheelChallenge;

public class Lesson3 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson3);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new PrerequisitesFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.prerequisites:
                    fragment = new PrerequisitesFragment();
                    loadFragment(fragment);
                    System.out.println("Prereq");
                    return true;
                case R.id.tutorials:
                    fragment = new TutorialsFragment();
                    loadFragment(fragment);
                    System.out.println("Tut");
                    return true;
                case R.id.exercises:
                    fragment = new ExercisesFragment();
                    loadFragment(fragment);
                    System.out.println("Exercise");
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
