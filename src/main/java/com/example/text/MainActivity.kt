package com.example.text

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.text.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _bd : ActivityMainBinding? = null
    private val bd : ActivityMainBinding get() = _bd!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bd = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bd.root)



       // val i : Long = Int() as Long

    }
}