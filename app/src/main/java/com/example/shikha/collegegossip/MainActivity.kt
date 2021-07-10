package com.example.shikha.collegegossip

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    @Suppress("DEPRECATION")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val top = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val imageView = findViewById(R.id.imageView) as ImageView
        imageView.startAnimation(top)


        val bottom = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        val textView = findViewById(R.id.textView) as TextView
        val textView2 = findViewById(R.id.textView2) as TextView
        textView.startAnimation(bottom)
        textView2.startAnimation(bottom)


        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            finish()
        }, 3000) //

    }
}