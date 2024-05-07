package com.dimas.vkinternshipproject.presentationLayer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dimas.vkinternshipproject.R
import com.dimas.vkinternshipproject.databinding.ActivityMainBinding
import com.dimas.vkinternshipproject.presentationLayer.fragments.ProductsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ProductsFragment.newInstance())
                .commit()
        }
    }
}