package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class StartSportActivity : AppCompatActivity() {
    private var type = ""
    private var day_id = ""
    private var videoEmbededAdress = ""
    private var url = ""
    private val mimeType = "text/html"
    private val encoding = "UTF-8"
    private val USERAGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36"
    private lateinit var title_plan: TextView
    private lateinit var title_day: TextView
    private lateinit var back: ImageView
    private lateinit var webView: WebView
    private lateinit var source: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_sport)

        type = intent.getStringExtra("type").toString() //取得使用者點擊的運動計畫的名稱(維持健康/減脂/增肌)
        day_id = intent.getStringExtra("id").toString() //取得使用點擊的天數(1~30)
        binding() //將變數和xml元件綁定
        hideActionBar() //隱藏 action bar
        showTitle() //根據使用者點擊的運動計畫和天數，顯示對應的計畫名稱
        backPlanActivity() //返回PlanActivity
        playVideo()//播放運動影片
    }

    //播放運動影片
    private fun playVideo(){
        //取得要嵌入影片的網址
        val health_array = resources.getStringArray(R.array.health_url_list)
        val fat_array = resources.getStringArray(R.array.fat_url_list)
        val muscle_array = resources.getStringArray(R.array.muscle_url_list)
        if(type.equals("維持健康")) url = health_array[day_id.toInt()-1]
        else if(type.equals("減脂")) url = fat_array[day_id.toInt()-1]
        else url = muscle_array[day_id.toInt()-1]
        videoEmbededAdress = "<iframe width=\"400\" height=\"300\" src=\"${url}?&autoplay=1\" title=\"YouTube video player\" frameborder=\"0\" allow=\"autoplay;\" allowfullscreen></iframe>"
        //設定webView
        webView.getSettings().setJavaScriptEnabled(true) //設定為可以使用JavaScript
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false) //設定為不需要手動觸發媒體
        webView.getSettings().setUserAgentString(USERAGENT) //設定為載入影片後自動播放
        //設定網址並播放
        webView.loadDataWithBaseURL("", videoEmbededAdress, mimeType, encoding, "")
    }

    //將變數和xml元件綁定
    private fun binding(){
        title_plan = findViewById(R.id.title_plan)
        title_day = findViewById(R.id.title_day)
        back = findViewById(R.id.back)
        webView = findViewById(R.id.youtubeView)
        source = findViewById(R.id.source)
    }

    //按下返回鍵，返回PlanActivity
    private fun backPlanActivity(){
        back.setOnClickListener {
            val intent = Intent(this, PlanActivity::class.java)
            intent.putExtra("type",type)
            intent.putExtra("id",day_id)
            startActivity(intent)
        }
    }

    //根據使用者點擊的運動計畫，顯示對應的計畫名稱
    private fun showTitle(){
        if(type.equals("維持健康")){
            title_plan.text = "維持健康的運動計畫"
            source.text = "影片來源： 臺北市真愛健康協會healthcare"
        }
        else if(type.equals("減脂")){
            title_plan.text = "減脂的運動計畫"
            source.text = "影片來源： Bellysu減肥中"
        }
        else{
            title_plan.text = "增肌的運動計畫"
            source.text = "影片來源： 游書庭-瘦身有氧"
        }
        title_day.text = "第 ${day_id} 天"
    }

    //隱藏 action bar
    private fun hideActionBar() {
        val supportActionBar = supportActionBar
        supportActionBar?.hide()
    }
}