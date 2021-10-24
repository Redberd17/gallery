package com.chugunova.mygallery.main

import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chugunova.mygallery.R
import com.chugunova.mygallery.adapter.*
import java.util.*


class MainFragment : Fragment(), ClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val imageList = ArrayList<Image>()
    lateinit var imageAdapter: ImageAdapter
    private lateinit var recyclerView: RecyclerView
    var projection = arrayOf(MediaStore.MediaColumns.DATA)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        imageAdapter = ImageAdapter(imageList)
        imageAdapter.listener = this
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = imageAdapter
        loadImages()
        return view
    }

    private fun loadImages() {
        val cursor: Cursor? = context?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        while (cursor!!.moveToNext()) {
            val absolutePathOfImage =
                cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))
            val image = Image(absolutePathOfImage)
            imageList.add(image)
        }
        cursor.close()
        imageAdapter.notifyDataSetChanged()
        imageAdapter.notifyDataSetChanged()
    }

    override fun onClick(position: Int) {
        //implement logic
    }
}