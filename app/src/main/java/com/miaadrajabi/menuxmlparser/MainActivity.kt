package com.miaadrajabi.menuxmlparser

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.miaadrajabi.menuxmlparser.presentation.menu.MenuActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }
}