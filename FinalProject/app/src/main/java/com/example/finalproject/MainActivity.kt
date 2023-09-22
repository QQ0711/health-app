package com.example.finalproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val EatFragment = EatFragment()
    private val SportFragment = SportFragment()
    private val RecordFragment = RecordFragment()
    private val SetFragment = SetFragment()
    private lateinit var navigationView: BottomNavigationView
    private lateinit var sp_info : SharedPreferences
    private var f_name = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding()//將變數和xml元件綁定
        check_first() //檢查是否為第一次開啟app (是: 跳到 FirstActivity)
        set_first_fragment() //設定初始時要顯示的fragment
        go_to_fragment() //從其他activity跳轉去指定的fragment


        //

        //測試用
        //val intent = Intent(this, FirstActivity::class.java)
        //startActivity(intent)
        //

    }

    //將變數和xml元件綁定
    private fun binding(){
        navigationView = findViewById(R.id.nvgationview)
    }

    //從其他activity跳轉去指定的fragment
    private fun go_to_fragment(){
        f_name = intent.getStringExtra("f_name").toString()
        if(f_name.equals("sport")){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_activity_main, SportFragment)
                .commit()
            navigationView.selectedItemId = R.id.f_sport
        }
        else if(f_name.equals("record")){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_activity_main, RecordFragment)
                .commit()
            navigationView.selectedItemId = R.id.f_record
        }else{
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_activity_main, EatFragment)
                .commit()
            navigationView.selectedItemId = R.id.f_eat
        }
    }

    //設定初始時要顯示的fragment
    private fun set_first_fragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_activity_main, RecordFragment)
            .commit()
        navigationView.selectedItemId = R.id.f_record
        navigationView.setOnNavigationItemSelectedListener(listener)
    }

    //檢查是否為第一次開啟app (是: 跳到 FirstActivity)
    private fun check_first(){
        //取得SharedPreferences，丟入的參數("檔案名稱",mode參數(存取權限))
        sp_info = getSharedPreferences("info", Context.MODE_PRIVATE)
        //取得儲存的資料
        val sharedAgeValue = sp_info.getString("age","")
        val sharedHeightValue = sp_info.getString("height","")
        val sharedWeightValue = sp_info.getString("weight","")
        val sharedSexValue = sp_info.getString("sex","")
        val sharedGoalValue = sp_info.getString("goal","")
        //如果沒有資料，就要跳到FirstActivity進行基本資料的填寫
        if(sharedAgeValue.equals("") || sharedHeightValue.equals("") || sharedWeightValue.equals("") || sharedSexValue.equals("") || sharedGoalValue.equals("")){
            val intent = Intent(this, FirstActivity::class.java)
            startActivity(intent)
        }
    }

    //點擊BottomNavigationView的item跳轉去對應的fragment
    private var listener = object : BottomNavigationView.OnNavigationItemSelectedListener{
        //設定navigationBar裡的item們的點擊事件 被點擊後採動態載入fragment的方式
        override fun onNavigationItemSelected(item : MenuItem): Boolean {
            when (item.itemId) {
                R.id.f_eat -> {
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.container_activity_main, EatFragment).commit()
                }
                R.id.f_sport -> {
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.container_activity_main, SportFragment).commit()
                }
                R.id.f_record -> {
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.container_activity_main, RecordFragment).commit()
                }
                R.id.f_set -> {
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.container_activity_main, SetFragment).commit()
                }
            }
            return true
        }
    }
}