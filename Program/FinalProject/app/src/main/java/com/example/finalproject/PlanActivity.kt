package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlanActivity : AppCompatActivity() {
    private var type = ""
    private lateinit var tv_title: TextView
    private lateinit var back: ImageView
    private lateinit var adapter: PlanAdapter
    private lateinit var recyclerView: RecyclerView
    private val day = (1..30).toList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan)

        hideActionBar() //隱藏 action bar
        type = intent.getStringExtra("type").toString() //取得使用者點擊的運動計畫的名稱(維持健康/減脂/增肌)
        binding() //將變數和xml元件綁定
        showTitle() //根據使用者點擊的運動計畫，顯示對應的計畫名稱
        backSportActivity() //返回SportActivity
        setRecyclerView() //設定recyclerView
    }

    //按下返回鍵，返回MainActivity
    private fun backSportActivity(){
        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("f_name","sport")
            startActivity(intent)
        }
    }

    //根據使用者點擊的運動計畫，顯示對應的計畫名稱
    private fun showTitle(){
        if(type.equals("維持健康")) tv_title.text = "維持健康的運動計畫"
        else if(type.equals("減脂")) tv_title.text = "減脂的運動計畫"
        else tv_title.text = "增肌的運動計畫"
    }

    //將變數和xml元件綁定
    private fun binding(){
        tv_title = findViewById(R.id.title)
        back = findViewById(R.id.back)
        recyclerView = findViewById(R.id.recycleView)
    }

    //設定recyclerView
    private fun setRecyclerView() {
        // 建立LinearLayoutoutManager物件，設定垂直方向
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager

        //建立MyAdapter並連結recycleView
        adapter = PlanAdapter(day,R.layout.adapter_plan)

        //建立recycleView的item click(6)
        adapter.setListener(object : RecyclerViewClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@PlanActivity, StartSportActivity::class.java)
                intent.putExtra("type",type)
                intent.putExtra("id",position.toString())
                startActivity(intent)
            }
        })

        recyclerView.adapter = adapter
    }

    //隱藏 action bar
    private fun hideActionBar() {
        val supportActionBar = supportActionBar
        supportActionBar?.hide()
    }

    //建立 showToast 方法顯示 Toast 訊息
    private fun showToast(text: String) =
        Toast.makeText(this,text, Toast.LENGTH_SHORT).show()
}