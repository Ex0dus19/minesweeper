package com.minneydev.minesweeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val playIntent = Intent(this, MainActivity::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        easy_btn.setOnClickListener {
            MainActivity.num_col = 10
            MainActivity.num_bomb = 5
            startActivity(playIntent)
        }
        med_btn.setOnClickListener {
            MainActivity.num_col = 10
            MainActivity.num_bomb = 10
            startActivity(playIntent)
        }
        hard_btn.setOnClickListener {
            MainActivity.num_col = 16
            MainActivity.num_bomb = 40
            startActivity(playIntent)
        }

    }
}
