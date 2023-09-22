package com.example.finalproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class SetFragment : Fragment() {
    private lateinit var view: View
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var ed_height: EditText
    private lateinit var ed_weight: EditText
    private lateinit var ed_age: EditText
    private lateinit var btn_boy: RadioButton
    private lateinit var btn_girl: RadioButton
    private lateinit var btn_health: RadioButton
    private lateinit var btn_fat: RadioButton
    private lateinit var btn_muscle: RadioButton
    private lateinit var btn_check: Button
    private lateinit var btn_sport_1: RadioButton
    private lateinit var btn_sport_2: RadioButton
    private lateinit var btn_sport_3: RadioButton
    private lateinit var btn_sport_4: RadioButton
    private lateinit var btn_sport_5: RadioButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //在 onCreateView 中定義 SetFragment 的畫面為 fragment_set
    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.fragment_set, container, false)
        requireActivity().setTitle("設定")
        binding() //將變數和xml元件綁定
        getSharedPreference() //取得SharedPreferences，丟入的參數("檔案名稱",mode參數(存取權限))
        readData() //顯示上一次填寫的資料
        updateData() //按下「確定」，跳到飲食的主畫面
        return view
    }

    //按下「確定」，回到MainActivity紀錄的主畫面(RecordFragment)
    private fun updateData(){
        btn_check.setOnClickListener {
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
                showToast("～資料更新完成～")
                val intent = Intent(requireActivity(), MainActivity::class.java)
                intent.putExtra("f_name","record")
                startActivity(intent)

            }
        }
    }

    //取得SharedPreferences，丟入的參數("檔案名稱",mode參數(存取權限))
    private fun getSharedPreference(){
        sharedPreferences = requireActivity().getSharedPreferences("info", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    //將變數和xml元件綁定
    private fun binding(){
        ed_height = view.findViewById(R.id.ed_height)
        ed_weight = view.findViewById(R.id.ed_weight)
        ed_age = view.findViewById(R.id.ed_age)
        btn_boy = view.findViewById(R.id.btn_boy)
        btn_girl = view.findViewById(R.id.btn_girl)
        btn_health = view.findViewById(R.id.btn_health)
        btn_fat = view.findViewById(R.id.btn_fat)
        btn_muscle = view.findViewById(R.id.btn_muscle)
        btn_check = view.findViewById(R.id.btn_check)
        btn_sport_1 = view.findViewById(R.id.sport_1)
        btn_sport_2 = view.findViewById(R.id.sport_2)
        btn_sport_3 = view.findViewById(R.id.sport_3)
        btn_sport_4 = view.findViewById(R.id.sport_4)
        btn_sport_5 = view.findViewById(R.id.sport_5)
    }

    //顯示上一次填寫的資料
    private fun readData(){
        val sharedAgeValue = sharedPreferences.getString("age","")
        val sharedHeightValue = sharedPreferences.getString("height","")
        val sharedWeightValue = sharedPreferences.getString("weight","")
        val sharedSexValue = sharedPreferences.getString("sex","")
        val sharedGoalValue = sharedPreferences.getString("goal","")
        val sharedHabitValue = sharedPreferences.getString("habit","None")
        ed_age.setText(sharedAgeValue)
        ed_height.setText(sharedHeightValue)
        ed_weight.setText(sharedWeightValue)

        if(sharedSexValue.equals("男生")) btn_boy.isChecked = true
        else btn_girl.isChecked = true

        if(sharedGoalValue.equals("維持健康")) btn_health.isChecked = true
        else if(sharedGoalValue.equals("減脂")) btn_fat.isChecked = true
        else btn_muscle.isChecked = true

        if(sharedHabitValue.equals("幾乎不運動")) btn_sport_1.isChecked = true
        else if(sharedHabitValue.equals("每週運動1~3天")) btn_sport_2.isChecked = true
        else if(sharedHabitValue.equals("每週運動3~5天")) btn_sport_3.isChecked = true
        else if(sharedHabitValue.equals("每週運動6~7天")) btn_sport_4.isChecked = true
        else btn_sport_5.isChecked = true

    }

    //建立 showToast 方法顯示 Toast 訊息
    private fun showToast(text: String) =
        Toast.makeText(requireActivity(),text, Toast.LENGTH_SHORT).show()
}