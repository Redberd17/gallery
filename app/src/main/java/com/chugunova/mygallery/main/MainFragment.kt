package com.chugunova.mygallery.main

import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
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
    private var projection = arrayOf(MediaStore.MediaColumns.DATA)
    private val rowCount = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        imageAdapter = ImageAdapter(imageList)
        imageAdapter.listener = this
        recyclerView.layoutManager = GridLayoutManager(context, rowCount)
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
    }

    fun changeVisibleType() {
        if (recyclerView.layoutManager is GridLayoutManager) {
            recyclerView.layoutManager = LinearLayoutManager(context)
        } else {
            recyclerView.layoutManager = GridLayoutManager(context, rowCount)
        }
    }

    override fun onClick(position: Int) {
        //implement logic
    }
}