package com.example.mediamanagment

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.mediamanagment.adopter.ViewPagerAdapter
import com.example.mediamanagment.databinding.ActivityMainBinding
import com.example.mediamanagment.fragments.ImageFragment
import com.example.mediamanagment.fragments.audioFragment
import com.example.mediamanagment.fragments.videoFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter=ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ImageFragment(),"Image")
        adapter.addFragment(audioFragment(),"Audio")
        adapter.addFragment(videoFragment(),"Video")
        binding.viewPager.adapter=adapter
        binding.tbLayout.setupWithViewPager(binding.viewPager)
    }
}

