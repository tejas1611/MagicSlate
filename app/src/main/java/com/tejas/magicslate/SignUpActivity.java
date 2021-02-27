package com.tejas.magicslate;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final EditText eText= findViewById(R.id.DOBEnter);
        eText.setInputType(InputType.TYPE_NULL);

        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(SignUpActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String strToDisplay = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                eText.setText(strToDisplay);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    public void onClickLoginAlt(View view) {
        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(i);
        SignUpActivity.this.finish();
    }
}
