package com.chugunova.mygallery.adapter

import android.content.Context
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chugunova.mygallery.R
import com.chugunova.mygallery.helper.GlideApp
import kotlinx.android.synthetic.main.gallery_item.view.*

class ImageAdapter(private val images: List<Image>): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private lateinit var context: Context
    lateinit var listener: ClickListener


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind() {
            val image = images[bindingAdapterPosition]
            GlideApp.with(context)
                .load(image.imageUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.ivGalleryImage)

            //add click listener here

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return images.size
    }
}