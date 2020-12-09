package com.example.kingpower.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.kingpower.R
import com.example.kingpower.common.Singleton
import com.example.kingpower.databinding.ActivityShowPhotoBinding
import com.example.kingpower.viewmodels.ShowPhotoListViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_photo.view.*

class ShowPhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowPhotoBinding
    private lateinit var showPhotoViewModel: ShowPhotoListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_photo)

        initInstance()
    }

    private fun initInstance() {
        initBinding()
        initToolbar()
        setData()
    }

    private fun setData() {
        supportActionBar?.title = Singleton.getInstance()?.getPhotoModel()?.title
        Picasso.get().load(Singleton.getInstance()?.getPhotoModel()?.url).into(binding.imgView)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_photo)
        showPhotoViewModel = ViewModelProviders.of(this).get(ShowPhotoListViewModel::class.java)
        binding.viewModel = showPhotoViewModel
    }

    override fun onDestroy() {
        Singleton.getInstance()?.clearPhotoModel()
        super.onDestroy()
    }
}
