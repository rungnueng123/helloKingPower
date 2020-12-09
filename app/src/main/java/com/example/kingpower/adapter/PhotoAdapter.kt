package com.example.kingpower.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kingpower.R
import com.example.kingpower.callback.ItemClickCallBack
import com.example.kingpower.databinding.ItemPhotoBinding
import com.example.kingpower.models.PhotoModel
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoAdapter(
    private var photoList: List<PhotoModel>?,
    private var listener: ItemClickCallBack
) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemPhotoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_photo,
            parent,
            false
        )
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding.photomodel = photoList!![position]
        Picasso.get()
            .load(photoList!![position].url)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .into(holder.itemView.imgView)
    }

    override fun getItemCount(): Int {
        return photoList!!.size
    }

    class ViewHolder(var itemBinding: ItemPhotoBinding, mListener: ItemClickCallBack) :
        RecyclerView.ViewHolder(itemBinding.root), LayoutContainer, View.OnClickListener {

        var listener: ItemClickCallBack = mListener

        override fun onClick(v: View?) {
            listener.onClick(v, adapterPosition)
        }

        override val containerView: View?
            get() = itemBinding.root

        init {
            itemBinding.root.setOnClickListener(this)
        }
    }

}