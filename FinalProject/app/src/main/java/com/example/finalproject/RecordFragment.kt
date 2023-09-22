package com.example.finalproject

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.math.pow


class RecordFragment : Fragment() {
    private lateinit var view: View
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var eat_title: TextView
    private lateinit var tv_eat_total: TextView //總熱量
    private lateinit var tv_eat_1: TextView //碳水
    private lateinit var tv_eat_2: TextView //脂肪
    private lateinit var tv_eat_3: TextView //蛋白質
    private lateinit var tv_height: TextView
    private lateinit var tv_weight: TextView
    private lateinit var tv_age: TextView
    private lateinit var tv_sex: TextView
    private lateinit var tv_goal: TextView
    private lateinit var tv_bmi: TextView
    private lateinit var tv_bmr: TextView
    private lateinit var tv_fatr: TextView
    private lateinit var bmi_result: TextView
    private lateinit var fatr_result: TextView
    private lateinit var tdee1: TextView
    private lateinit var tdee2: TextView
    private lateinit var tdee3: TextView
    private lateinit var tdee4: TextView
    private lateinit var tdee5: TextView
    private var height = 0.0
    private var weight = 0.0
    private var age = 0
    private var BMI = 0.0 //身體質量指數(BMI)
    private var BMR = 0.0 //基礎代謝率(BMR)
    private var FATR = 0.0 //體脂肪率(FATR)
    //日總消耗量(TDEE)
    private var TDEE1 = 0.0
    private var TDEE2 = 0.0
    private var TDEE3 = 0.0
    private var TDEE4 = 0.0
    private var TDEE5 = 0.0
    private var fatr = ""
    //運動習慣
    private var habit = ""
    private var habit_TDEE = 0.0
    private var total = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //在 onCreateView 中定義 RecordFragment 的畫面為 fragment_record
    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.fragment_record, container, false)
        requireActivity().setTitle("紀錄")
        binding() //將變數和xml元件綁定
        //取得SharedPreferences，丟入的參數("檔案名稱",mode參數(存取權限))
        sharedPreferences = requireActivity().getSharedPreferences("info", Context.MODE_PRIVATE)
        readData()//顯示紀錄
        calaulate()//計算指標
        showResult()//顯示計算結果
        //
        editor = sharedPreferences.edit()
        editor.putString("total",total.toString())
        editor.apply()
        editor.commit()
        //
        return view
    }

    //顯示計算結果
    private fun showResult(){
        tv_bmi.text = "${String.format("%.2f", BMI)}"
        tv_bmr.text = "${String.format("%.1f", BMR)}"
        tv_fatr.text = "${String.format("%.2f", FATR)}"
        tdee1.text = "${String.format("%.1f", TDEE1)}"
        tdee2.text = "${String.format("%.1f", TDEE2)}"
        tdee3.text = "${String.format("%.1f", TDEE3)}"
        tdee4.text = "${String.format("%.1f", TDEE4)}"
        tdee5.text = "${String.format("%.1f", TDEE5)}"
        //體脂率標準
        if(fatr.equals("肥胖")) fatr_result.text = Html.fromHtml("<font color = \"#FE7773\">${fatr}</font>")
        else fatr_result.text = fatr
        //BMI標準
        if(BMI < 18.5) bmi_result.text = "體重過輕"
        else if(BMI < 24) bmi_result.text = "體重正常"
        else if(BMI < 27) bmi_result.text = Html.fromHtml("<font color = \"#FE7773\">體重過重</font>")
        else if(BMI < 30) bmi_result.text = Html.fromHtml("<font color = \"#FE7773\">輕度肥胖</font>")
        else if(BMI < 35) bmi_result.text = Html.fromHtml("<font color = \"#FE7773\">中度肥胖</font>")
        else bmi_result.text = Html.fromHtml("<font color = \"#FE7773\">重度肥胖</font>")
        //運動習慣
        if (habit.equals("幾乎不運動")){
            habit_TDEE = TDEE1
        }else if(habit.equals("每週運動1~3天")){
            habit_TDEE = TDEE2
        }else if(habit.equals("每週運動3~5天")){
            habit_TDEE = TDEE3
        }else if(habit.equals("每週運動6~7天")){
            habit_TDEE = TDEE4
        }else{
            habit_TDEE = TDEE5
        }
        //飲食建議
        if(tv_goal.text.equals("維持健康")){
            total = habit_TDEE
            tv_eat_1.text = "${String.format("%.1f",total*0.5/4)}" //碳水
            tv_eat_2.text = "${String.format("%.1f",total*0.3/9)}" //脂肪
            tv_eat_3.text = "${String.format("%.1f",total*0.2/4)}" //蛋白質
        }
        else if(tv_goal.text.equals("減脂")){
            total = habit_TDEE - 100
            tv_eat_1.text = "${String.format("%.1f",total*0.4/4)}"
            tv_eat_2.text = "${String.format("%.1f",total*0.2/4)}"
            tv_eat_3.text = "${String.format("%.1f",total*0.4/4)}"
        }else{
            total = habit_TDEE + 100
            tv_eat_1.text = "${String.format("%.1f",total*0.55/4)}"
            tv_eat_2.text = "${String.format("%.1f",total*0.2/4)}"
            tv_eat_3.text = "${String.format("%.1f",total*0.25/4)}"
        }
        tv_eat_total.text = "${String.format("%.1f", total)}"
    }

    //計算指標
    private fun calaulate(){
        height = tv_height.text.toString().toDouble() //單位：(cm)
        weight = tv_weight.text.toString().toDouble() //單位：(kg)
        age = tv_age.text.toString().toInt() //單位：(歲)
        BMI = weight/((height/100).pow(2))
        if(tv_sex.text.equals("男生")){
            BMR = 10*weight + 6.25*height - 5*age + 5
            FATR = 1.2*BMI + 0.23*age - 5.4 - 10.8
            TDEE1 = BMR * 1.2
            TDEE2 = BMR * 1.4
            TDEE3 = BMR * 1.6
            TDEE4 = BMR * 1.8
            TDEE5 = BMR * 2
            if(age < 31){
                if(FATR < 14) fatr = "消瘦"
                else if(FATR < 21) fatr = "標準"
                else fatr = "肥胖"
            }else{
                if(FATR < 17) fatr = "消瘦"
                else if(FATR < 24) fatr = "標準"
                else fatr = "肥胖"
            }
        }else{
            BMR = 10*weight + 6.25*height - 5*age - 161
            FATR = 1.2*BMI + 0.23*age - 5.4
            TDEE1 = BMR * 1.2
            TDEE2 = BMR * 1.4
            TDEE3 = BMR * 1.6
            TDEE4 = BMR * 1.8
            TDEE5 = BMR * 2
            if(age < 31){
                if(FATR < 17) fatr = "消瘦"
                else if(FATR < 25) fatr = "標準"
                else fatr = "肥胖"
            }else{
                if(FATR < 20) fatr = "消瘦"
                else if(FATR < 28) fatr = "標準"
                else fatr = "肥胖"
            }
        }
    }

    //顯示紀錄
    private fun readData(){
        val sharedAgeValue = sharedPreferences.getString("age","None")
        val sharedHeightValue = sharedPreferences.getString("height","None")
        val sharedWeightValue = sharedPreferences.getString("weight","None")
        val sharedSexValue = sharedPreferences.getString("sex","None")
        val sharedGoalValue = sharedPreferences.getString("goal","None")
        val sharedHabitValue = sharedPreferences.getString("habit","None")
        tv_age.text = sharedAgeValue
        tv_height.text = sharedHeightValue
        tv_weight.text = sharedWeightValue
        tv_sex.text = sharedSexValue
        tv_goal.text = sharedGoalValue
        habit = sharedHabitValue.toString()
        eat_title.text = "${tv_goal.text}的飲食建議："
    }

    //將變數和xml元件綁定
    private fun binding(){
        tv_height = view.findViewById(R.id.tv_height)
        tv_weight = view.findViewById(R.id.tv_weight)
        tv_age = view.findViewById(R.id.tv_age)
        tv_sex = view.findViewById(R.id.tv_sex)
        tv_goal = view.findViewById(R.id.tv_goal)
        tv_bmi = view.findViewById(R.id.tv_bmi)
        tv_bmr = view.findViewById(R.id.tv_bmr)
        tv_fatr = view.findViewById(R.id.tv_fatr)
        bmi_result = view.findViewById(R.id.bmi_result)
        fatr_result = view.findViewById(R.id.fatr_result)
        tdee1 = view.findViewById(R.id.tdee1)
        tdee2 = view.findViewById(R.id.tdee2)
        tdee3 = view.findViewById(R.id.tdee3)
        tdee4 = view.findViewById(R.id.tdee4)
        tdee5 = view.findViewById(R.id.tdee5)
        eat_title = view.findViewById(R.id.eat_title)
        tv_eat_total = view.findViewById(R.id.tv_eat_total)
        tv_eat_1 = view.findViewById(R.id.tv_eat_1)
        tv_eat_2 = view.findViewById(R.id.tv_eat_2)
        tv_eat_3 = view.findViewById(R.id.tv_eat_3)
    }
}