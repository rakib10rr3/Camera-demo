package com.rakib.video_demo

import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import com.otaliastudios.cameraview.VideoResult
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var result : File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        record_button?.setOnClickListener {
            requestPermission()
        }

        view_video_button?.setOnClickListener {
            val intent = Intent(this,PlayerActivity::class.java)
            intent.putExtra("url",result)
            startActivity(intent)
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
                startActivityForResult(Intent(this,CameraActivity::class.java),0)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0){
            if (resultCode == Activity.RESULT_OK){
                result = data?.extras?.get("url")  as File
            }
        }
    }

//    fun setVideoResult(result: VideoResult?){
//        result?.let {
//            this.result = result
//        }
//    }
}
