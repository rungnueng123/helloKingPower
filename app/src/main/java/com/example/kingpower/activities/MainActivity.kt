package com.example.kingpower.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kingpower.R
import com.example.kingpower.adapter.PhotoAdapter
import com.example.kingpower.callback.ItemClickCallBack
import com.example.kingpower.common.Singleton
import com.example.kingpower.databinding.ActivityMainBinding
import com.example.kingpower.models.PhotoModel
import com.example.kingpower.service.repositories.PhotosRepository
import com.example.kingpower.viewmodels.PhotoListViewModel
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var photoViewModel: PhotoListViewModel
    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var listener: ItemClickCallBack
    private lateinit var photoList: List<PhotoModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initInstance()
    }

    private fun initInstance() {
        initBinding()
        initToolbar()
        downloadPhoto()
    }

    private fun initRecyclerView(kt: List<PhotoModel>) {

        listener = object : ItemClickCallBack {
            override fun onClick(view: View?, position: Int) {
//                Toast.makeText(baseContext, "" + photoList[position].url, Toast.LENGTH_SHORT).show()
                Singleton.getInstance()?.setPhotoModel(photoList[position])
                var gson = Gson()
                Log.e("photoModel", gson.toJson(Singleton.getInstance()?.getPhotoModel()))
                val intent = Intent(this@MainActivity, ShowPhotoActivity::class.java)
                startActivity(intent)

            }
        }

        photoAdapter = PhotoAdapter(kt, listener)
        binding.imgRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = photoAdapter
        }
    }

    private fun downloadPhoto() {
        photoViewModel.downloadPhotoList().observe(this, Observer { pl ->
            photoList = pl
            initRecyclerView(photoList)
        })

        photoViewModel.showApiError.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(it).show()
        })

        photoViewModel.showProgressBar.observe(this, Observer { pb ->
            if (pb) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

        photoViewModel.showNetworkError.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(R.string.no_internet_msg).show()
        })

    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        photoViewModel = ViewModelProviders.of(this).get(PhotoListViewModel::class.java)
        binding.viewModel = photoViewModel
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Photos"
    }
}
