package com.chugunova.mygallery.main

import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.chugunova.mygallery.R
import com.chugunova.mygallery.adapter.*
import com.chugunova.mygallery.fullscreen.FullscreenFragment
import java.util.*


class MainFragment : Fragment(), ClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val imageList = ArrayList<Image>()
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var recyclerView: RecyclerView
    private var projection = arrayOf(MediaStore.MediaColumns.DATA)
    private val rowCount = 3
    private var isGridView = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        imageAdapter = ImageAdapter(imageList)
        imageAdapter.listener = this
        recyclerView.layoutManager = setLayoutManager()
        recyclerView.adapter = imageAdapter
        if (imageList.isEmpty()) {
            loadImages()
        }
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

    private fun setLayoutManager(): RecyclerView.LayoutManager {
        return if (isGridView)
            GridLayoutManager(context, rowCount)
        else
            LinearLayoutManager(context)
    }

    fun changeVisibleType() {
        isGridView = !isGridView
        recyclerView.layoutManager = setLayoutManager()
    }

    override fun onClick(position: Int) {
        val bundle = Bundle()
        bundle.putSerializable(getString(R.string.images), imageList)
        bundle.putInt(getString(R.string.position), position)
        val fullscreenFragment = FullscreenFragment()
        fullscreenFragment.arguments = bundle
        val mainFragment =
            requireActivity().supportFragmentManager.findFragmentByTag(getString(R.string.main_fragment)) as MainFragment
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(mainFragment.id, fullscreenFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}