package com.rakib.video_demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraOptions
import com.otaliastudios.cameraview.VideoResult
import com.otaliastudios.cameraview.controls.Facing
import com.otaliastudios.transcoder.Transcoder
import com.otaliastudios.transcoder.TranscoderListener
import com.otaliastudios.transcoder.sink.DataSink
import com.otaliastudios.transcoder.sink.DefaultDataSink
import com.otaliastudios.transcoder.strategy.DefaultVideoStrategy
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraActivity : AppCompatActivity() {

    val TAG = CameraActivity::class.simpleName
    lateinit var strategy : DefaultVideoStrategy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        camera.setLifecycleOwner(this)

//        val width : SizeSelector = SizeSelectors.maxWidth(720)
//        val height : SizeSelector = SizeSelectors.maxHeight(1280)
//        val dimensions : SizeSelector = SizeSelectors.and(width,height)
//        val ratio  = SizeSelectors.aspectRatio(AspectRatio.of(3,4),0f)
//
//        val result = SizeSelectors.or(
//            SizeSelectors.and(ratio,dimensions),
//            ratio,
//            SizeSelectors.biggest()
//        )
//
//        camera.setVideoSize(result)

        camera.addCameraListener(object : CameraListener() {

            override fun onCameraOpened(options: CameraOptions) {
                super.onCameraOpened(options)
                Log.d(TAG, "camera opened")
            }

            override fun onVideoTaken(result: VideoResult) {
                super.onVideoTaken(result)
                val video = result.file
                strategy = DefaultVideoStrategy.exact(720,1080).build().also {
                    DefaultVideoStrategy.fraction(.5f).build()
                }

                Log.d(TAG, "${result.size} ${result.location} ${video.absolutePath}")
                val dir = File(video.absolutePath,"demo_cameras")
                val outputDir = File(getExternalFilesDir(null), "outputs")
                //noinspection ResultOfMethodCallIgnored
                outputDir.mkdir()
                var mTranscodeOutputFile = File.createTempFile("transcode_test", ".mp4", outputDir)
                Transcoder
                    .into(DefaultDataSink(mTranscodeOutputFile.absolutePath))
                    .setVideoTrackStrategy(strategy)
                    .addDataSource(video.absolutePath)
                    .setListener(object : TranscoderListener {
                    override fun onTranscodeCompleted(successCode: Int) {
                        Log.d(TAG, "transcode completed")
                    }

                    override fun onTranscodeProgress(progress: Double) {
                        Log.d(TAG, "transcode onProgress")
                    }

                    override fun onTranscodeCanceled() {
                        Log.d(TAG, "transcode cancelled")
                    }

                    override fun onTranscodeFailed(exception: Throwable) {
                        Log.d(TAG, "transcode failed")
                    }
                }).transcode()
            }

            override fun onVideoRecordingStart() {
                super.onVideoRecordingStart()
                Log.d(TAG, "video started")
                captureVideo?.background =
                    ContextCompat.getDrawable(this@CameraActivity, R.drawable.ic_videocam_off)

            }

            override fun onVideoRecordingEnd() {
                super.onVideoRecordingEnd()
                Log.d(TAG, "video end")
                captureVideo?.background =
                    ContextCompat.getDrawable(this@CameraActivity, R.drawable.ic_videocam)

            }
        })

        setupButtons()



    }

    private fun setupButtons() {
        captureVideo?.setOnClickListener {
            captureVideo()
        }
        toggleCamera?.setOnClickListener {
            toggleCamera()
        }
    }

    private fun toggleCamera() {
        if (camera.isTakingPicture || camera.isTakingVideo) return
        when (camera.toggleFacing()) {
            Facing.BACK -> return
            Facing.FRONT -> return
        }
    }

    private fun captureVideo(){
        Log.d(TAG,"came here")
        if (camera.isTakingPicture || camera.isTakingVideo) return
        val dir = File(this.getExternalFilesDir(null)!!.absolutePath,"demo_camera")
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val newFile = File(dir.path + File.separator + "Demo_" + timeStamp + ".mp4")
        camera.takeVideo(newFile,30000)
        //camera.takeVideo()
    }
}
