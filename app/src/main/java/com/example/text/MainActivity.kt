package com.example.text

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.text.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _bd : ActivityMainBinding? = null
    private val bd : ActivityMainBinding get() = _bd!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.TRANSPARENT
        _bd = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bd.root)

    }
}