package com.rakib.video_demo

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        record_button?.setOnClickListener {
            requestPermission()
        }

        view_video_button?.setOnClickListener {
            startActivity(Intent(this,PlayerActivity::class.java))
        }

        snap_button?.setOnClickListener {
            startActivity(Intent(this,SwipeActivity::class.java))
        }
    }

    private fun requestPermission() {
        permissionsBuilder(Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE).build().send {result ->
            if (result.allGranted()){
                val storageDir = File(this.getExternalFilesDir(null)!!.absolutePath,"demo_camera")
                if (!storageDir.exists()){
                    storageDir.mkdir()
                }
                startActivity(Intent(this,CameraActivity::class.java))
            }
        }
    }
}
