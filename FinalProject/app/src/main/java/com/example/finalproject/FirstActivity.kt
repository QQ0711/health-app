package com.example.finalproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView

class FirstActivity : AppCompatActivity() {
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var ed_height: EditText
    private lateinit var ed_weight: EditText
    private lateinit var ed_age: EditText
    private lateinit var btn_boy: RadioButton
    private lateinit var btn_health: RadioButton
    private lateinit var btn_fat: RadioButton
    private lateinit var btn_sport_1: RadioButton
    private lateinit var btn_sport_2: RadioButton
    private lateinit var btn_sport_3: RadioButton
    private lateinit var btn_sport_4: RadioButton
    private lateinit var btn_sport_5: RadioButton
    private val RecordFragment = RecordFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        hideActionBar() //隱藏action bar
        binding()//將變數和xml元件綁定
        newSharedPreferences()//建立SharedPreferences，丟入的參數("檔案名稱",mode參數(存取權限))
        addData()//按下「確定」，跳到紀錄的主畫面

        //測試用
        //editor.clear()
        //editor.apply()
        //
    }

    //建立SharedPreferences，丟入的參數("檔案名稱",mode參數(存取權限))
    private fun newSharedPreferences(){
        sharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    //新增資料
    private fun addData(){
        findViewById<TextView>(R.id.btn_check).setOnClickListener {
            //showToast(ed_height.text.toString())
            if(ed_age.length() < 1 || ed_height.length() < 1 || ed_weight.length() < 1){
                showToast("欄位不可空白")
            }else{
                editor.putString("age",ed_age.text.toString())
                editor.putString("height",ed_height.text.toString())
                editor.putString("weight",ed_weight.text.toString())

                if(btn_boy.isChecked) editor.putString("sex","男生")
                else editor.putString("sex","女生")

                if(btn_health.isChecked) editor.putString("goal","維持健康")
                else if(btn_fat.isChecked) editor.putString("goal","減脂")
                else editor.putString("goal","增肌")

                if(btn_sport_1.isChecked) editor.putString("habit","幾乎不運動")
                else if(btn_sport_2.isChecked) editor.putString("habit","每週運動1~3天")
                else if(btn_sport_3.isChecked) editor.putString("habit","每週運動3~5天")
                else if(btn_sport_4.isChecked) editor.putString("habit","每週運動6~7天")
                else editor.putString("habit","長時間運動")

                editor.apply()
                editor.commit()
                showToast("～資料填寫完成～")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    //將變數和xml元件綁定
    private fun binding(){
        ed_height = findViewById(R.id.ed_height)
        ed_weight = findViewById(R.id.ed_weight)
        ed_age = findViewById(R.id.ed_age)
        btn_boy = findViewById(R.id.btn_boy)
        btn_health = findViewById(R.id.btn_health)
        btn_fat = findViewById(R.id.btn_fat)
        btn_sport_1 = findViewById(R.id.sport_1)
        btn_sport_2 = findViewById(R.id.sport_2)
        btn_sport_3 = findViewById(R.id.sport_3)
        btn_sport_4 = findViewById(R.id.sport_4)
        btn_sport_5 = findViewById(R.id.sport_5)

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