package com.alien.ward

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.alien.ward.adapter.BottomAdapter
import com.alien.ward.adapter.TopAdapter
import com.alien.ward.database.entity.TopEntity
import com.alien.ward.databinding.ActivityMainBinding
import com.alien.ward.view.MyViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myViewModel: MyViewModel

    private lateinit var topViewPager: ViewPager2
    private lateinit var bottomViewPager: ViewPager2
    private lateinit var topImageList: ArrayList<Int>
    private lateinit var bottomImageList: ArrayList<Int>
    private lateinit var topAdapter: TopAdapter
    private lateinit var bottomAdapter: BottomAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        initTop()
        initBottom()

        binding.ivTopAdd.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

    }

    override fun onResume() {
        super.onResume()



        binding.ivSyncButton.setOnClickListener {
            autoSync(topImageList.size, bottomImageList.size)
        }

    }


    // top viewPager
    private fun initTop() {
        topViewPager = findViewById(R.id.vp_topViewPager)
        topImageList = ArrayList()
        //project local images
        topImageList.add(R.drawable.shirt1)
        topImageList.add(R.drawable.shirt2)
        topImageList.add(R.drawable.shirt4)
        topImageList.add(R.drawable.shirt3)

        //adapter setup
        topAdapter = TopAdapter(topImageList, topViewPager)
        topViewPager.adapter = topAdapter
        topViewPager.offscreenPageLimit = 3
        topViewPager.clipToPadding = false
        topViewPager.clipChildren = false
        topViewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER


    }

    //bottom viewPager
    private fun initBottom() {
        bottomViewPager = findViewById(R.id.vp_bottomViewPager)
        bottomImageList = ArrayList()
        //project local images
        bottomImageList.add(R.drawable.pants1)
        bottomImageList.add(R.drawable.pant3)
        bottomImageList.add(R.drawable.pant2)
        bottomImageList.add(R.drawable.pant5)

        //adapter setup
        bottomAdapter = BottomAdapter(bottomImageList, bottomViewPager)
        bottomViewPager.adapter = bottomAdapter
        bottomViewPager.offscreenPageLimit = 3
        bottomViewPager.clipToPadding = false
        bottomViewPager.clipChildren = false
        bottomViewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

    }

    //image to bit-converter
    private suspend fun getBitmap(): Bitmap {
        val loading = ImageLoader(this)
        val request = ImageRequest.Builder(this)
            .data("https://place.dog/300/200")
            .build()
        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    //image picker
    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            /**   lifecycleScope.launch {
            val url = uri
            val shirt = url.path?.let { TopEntity("blue", it) }
            if (shirt != null) {
            myViewModel.addTops(shirt)
            }
            }   */
            Log.d("alien", "Selected URI: $uri")
        } else {
            Log.d("alien", "No media selected")
        }
    }


    //auto sync
    fun autoSync(topSize: Int, bottomSize: Int) {
        Toast.makeText(this, "Syncing the best for you \uD83D\uDC7D", Toast.LENGTH_SHORT).show()
        val randomTop = (0..topSize).random()
        val randomBottom = (0..bottomSize).random()

        topViewPager.setCurrentItem(randomTop)
        bottomViewPager.setCurrentItem(randomBottom)

    }


}
