package com.amber.simple.zload

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amber.imageloader.ZImageLoader
import com.amber.imageloader.listener.LoadListener
import com.amber.simple.R
import kotlinx.android.synthetic.main.activity_z_load.*

/**
 * @author lsy
 * @date 2021/05/18
 * @description
 */
class ZLoadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_z_load)

        ZImageLoader.with(this)
            .load(R.drawable.gif_test)
            .asGif()
            .listener(object : LoadListener<Drawable?> {
                override fun onLoadSuccess(resource: Drawable?) {
                    Log.e("111", "resource=$resource")
                }

                override fun onLoadFailure(e: Exception?, errorMsg: String) {
                    Log.e("111", "errorMsg=$errorMsg")
                }
            })
            .grayscale()
            .fitCenter(60f)
            .into(iv_test)


        ZImageLoader.with(this)
            .load(R.drawable.gif_test)
            .asGif()
            .listener(object : LoadListener<Drawable?> {
                override fun onLoadSuccess(resource: Drawable?) {
                    Log.e("111", "resource=$resource")
                }

                override fun onLoadFailure(e: Exception?, errorMsg: String) {
                    Log.e("111", "errorMsg=$errorMsg")
                }
            })
            .grayscale()
            .fitCenter(60f)
            .into(v_test)

    }
}