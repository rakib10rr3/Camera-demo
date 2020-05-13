package com.rakib.video_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_player.*
import kotlinx.android.synthetic.main.activity_swipe.*
import kotlinx.android.synthetic.main.activity_swipe.video_view


class SwipeActivity : AppCompatActivity() {

    lateinit var snapHelper: PagerSnapHelper
    lateinit var layoutManager: RecyclerView.LayoutManager

    var links = arrayOf(
        "",
        "https://vdo.bdjobs.com/Videos/Corporate//904205/183073282/2523253_469.webm",
        "http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)


        layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        recycler_view.layoutManager = layoutManager

        recycler_view?.adapter = QuestionAdapter(this)

        snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recycler_view)

        recycler_view.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    Log.d("rakibzzz","not dragging")
                    val snapPosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    Log.d("rakib","$snapPosition")
                    video_view?.stopPlayback()
                    video_view?.setVideoPath(links[snapPosition])
                    progress_bar?.visibility = View.VISIBLE
                    video_view?.requestFocus()

                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    Log.d("rakibzzz","dragging")
                }

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("rakibzzz","onScrolled called")
            }
        })

        video_view?.setOnPreparedListener{
            progress_bar?.visibility = View.GONE
            video_view?.start()
        }

        video_view?.setOnCompletionListener {
            Log.d("rakib","completed")
        }

    }
}
