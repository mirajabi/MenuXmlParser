package com.miaadrajabi.menuxmlparser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.ComponentActivity;

import com.miaadrajabi.menuxmlparser.java.presentation.menu.MenuActivity;

public class MainJavaActivity extends ComponentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_java);

        Button kotlinBtn = findViewById(R.id.btn_kotlin_ui);
        Button javaBtn = findViewById(R.id.btn_java_ui);

        kotlinBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainJavaActivity.this, com.miaadrajabi.menuxmlparser.presentation.menu.MenuActivity.class));
            }
        });
        javaBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainJavaActivity.this, MenuActivity.class));
            }
        });
    }
}
