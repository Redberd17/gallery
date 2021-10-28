package com.chugunova.mygallery.fullscreen

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chugunova.mygallery.R
import com.chugunova.mygallery.adapter.Image
import com.chugunova.mygallery.helper.GlideApp
import kotlinx.android.synthetic.main.fullscreen_image_layout.view.*
import java.util.*

class FullscreenFragment : Fragment() {

    private var images = ArrayList<Image>()
    private var position = 0

    lateinit var viewPager: ViewPager
    lateinit var fullscreenPagerAdapter: FullscreenPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fullscreen_fragment, container, false)
        viewPager = view.findViewById(R.id.viewPager)
        fullscreenPagerAdapter = FullscreenPagerAdapter()
        images = arguments?.getSerializable(getString(R.string.images)) as ArrayList<Image>
        position = arguments?.getInt(getString(R.string.position)) as Int
        viewPager.adapter = fullscreenPagerAdapter
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener)
        setChosenImage(position)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.change_view_type).isVisible = false
    }

    private fun setChosenImage(position: Int) {
        viewPager.setCurrentItem(position, false)
    }

    private var viewPagerPageChangeListener: ViewPager.OnPageChangeListener =
        object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {
            }

            override fun onPageScrollStateChanged(arg0: Int) {
            }
        }

    inner class FullscreenPagerAdapter : PagerAdapter() {
        override fun getCount(): Int {
            return images.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object` as View
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val layoutInflater =
                activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = layoutInflater.inflate(R.layout.fullscreen_image_layout, container, false)
            val image = images[position]
            GlideApp.with(context!!)
                .load(image.imageUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view.fullscreen_image_item)
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }
}