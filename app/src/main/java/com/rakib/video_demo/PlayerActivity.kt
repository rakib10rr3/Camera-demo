package com.rakib.video_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        setFinishOnTouchOutside(false)

        video_view?.setVideoPath("https://vdo.bdjobs.com/Videos/Corporate//904205/183073282/2523253_469.webm")
        video_view?.start()
        var mediaController = MediaController(this)
        video_view.setMediaController(mediaController)
        mediaController.setAnchorView(video_view)
        mediaController.show()

    }
}
