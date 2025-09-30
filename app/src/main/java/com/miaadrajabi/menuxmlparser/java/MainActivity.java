package com.miaadrajabi.menuxmlparser.java;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.ComponentActivity;

import com.miaadrajabi.menuxmlparser.java.presentation.menu.MenuActivity;

public class MainActivity extends ComponentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, MenuActivity.class));
        finish();
    }
}
