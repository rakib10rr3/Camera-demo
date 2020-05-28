package com.rakib.video_demo

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import androidx.core.net.toUri
import kotlinx.android.synthetic.main.activity_player.*
import java.io.File

class PlayerActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        setFinishOnTouchOutside(false)

        val file : File = intent.extras?.get("url") as File

        var mediaController = MediaController(this)
        video_view.setMediaController(mediaController)
        try{
            mediaController.setAnchorView(video_view)
            mediaController.show()
        } catch (e:Exception){
            e.printStackTrace()
        }

        video_view?.setVideoURI(Uri.fromFile(file))
        video_view?.start()

    }
}
