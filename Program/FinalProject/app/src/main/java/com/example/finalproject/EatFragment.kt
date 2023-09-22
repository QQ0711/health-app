package com.example.finalproject

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class EatFragment : Fragment() {
    private lateinit var view: View
    private lateinit var btn_add: FloatingActionButton
    private val viewModel : ContactViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var pieChart: PieChart
    private lateinit var date_show: TextView
    private lateinit var choosedate: ImageButton
    private lateinit var calories: TextView
    private var plan_index = 0
    private var total = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    //在 onCreateView 中定義 EatFragment 的畫面為 fragment_eat
    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.fragment_eat, container, false)
        requireActivity().setTitle("飲食")
        binding()
        //
        get_total_calories()

        //
        add_food() //跳到ChooseActivity，添加飲食紀錄
        //observe_data() //收到資料更新的通知時要做的處理
        show_calendar() //顯示日曆
        show_and_update(date_show.text.toString()) //顯示圓餅圖
        return view

    }

    //將變數和xml元件綁定
    private fun binding(){
        recyclerView = view.findViewById(R.id.recyclerView)
        btn_add = view.findViewById(R.id.floatingActionButton)
        pieChart = view.findViewById(R.id.bar_pie)
        date_show = view.findViewById(R.id.textView1)
        choosedate = view.findViewById(R.id.imageButton)
    }

    private fun get_total_calories(){
        //取得SharedPreferences，丟入的參數("檔案名稱",mode參數(存取權限))
        sharedPreferences = requireActivity().getSharedPreferences("info", Context.MODE_PRIVATE)
        val sharedGoalValue = sharedPreferences.getString("goal","None")
        val sharedTotalValue = sharedPreferences.getString("total","None")
        if(sharedGoalValue.equals("維持健康")){
            plan_index = 0
        }else if(sharedGoalValue.equals("減脂")){
            plan_index = 1
        }else{
            plan_index = 2
        }
        total = sharedTotalValue.toString().toDouble()
        calories = view.findViewById(R.id.eat_total)
        calories.text = "${sharedGoalValue}建議攝取總熱量： ${String.format("%.1f",total)} 大卡"
    }

    //跳到ChooseActivity，添加飲食紀錄
    private fun add_food(){
        btn_add.setOnClickListener{
            val intent = Intent(requireActivity() , ChooseActivity::class.java)
            startActivity(intent)
        }
    }
    /*        下方的private fun observe_data()刪掉~~
        private fun observe_data(){
            viewModel.getAllContacts(date_show.text.toString()).observe(requireActivity() , Observer { list->
                //設定recyclerView
                recyclerView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
                recyclerView.adapter = ContactsAdapter(requireActivity(),list)
            })
        }
    */
    private fun show_calendar(){
        val datetime= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")).toString()
        date_show.text=datetime
        val calendar = Calendar.getInstance()
//第二步
        val listener = object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
                calendar.set(year, month, day)
                val myformat = "yyyy/MM/dd"
                val sdf = SimpleDateFormat(myformat, Locale.TAIWAN)
                date_show.text=sdf.format(calendar.time)
                show_and_update(date_show.text.toString())     //呼叫畫面更新在這~~
            }
        }

//第三步
        choosedate.setOnClickListener {  //日曆按鈕的點擊事件
            //observe_data()
            DatePickerDialog(requireActivity(),
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
            //observe_data()
            //show_pie_chart(date_show.text.toString())     <<----刪掉~~
        }
    }

    //顯示recyclerView和圓餅圖~~
    private fun show_and_update(search_key: String){
        viewModel.getAllContacts(search_key).observe(requireActivity() , Observer { list->

            recyclerView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)       //加入recyclerView的處理~~
            recyclerView.adapter = ContactsAdapter(requireActivity(),list)                        //加入recyclerView的處理~~

            var sum_Calories=0.0        //卡路里
            var sum_Carbs=0.0           //碳水化合物
            var sum_Fat=0.0             //脂肪
            var sum_Protein=0.0         //蛋白質
            var sum_Fiber=0.0           //纖維素

            for (i in list.indices){
                var kind_class=R.array.food_0_0
                when(list[i].food){
                    "0.0"->kind_class=R.array.food_0_0
                    "0.1"->kind_class=R.array.food_0_1
                    "0.2"->kind_class=R.array.food_0_2
                    "0.3"->kind_class=R.array.food_0_3
                    "0.4"->kind_class=R.array.food_0_4
                    "0.5"->kind_class=R.array.food_0_5
                    "0.6"->kind_class=R.array.food_0_6
                    "0.7"->kind_class=R.array.food_0_7
                    "1.0"->kind_class=R.array.food_1_0
                    "1.1"->kind_class=R.array.food_1_1
                    "1.2"->kind_class=R.array.food_1_2
                    "1.3"->kind_class=R.array.food_1_3
                    "1.4"->kind_class=R.array.food_1_4
                    "1.5"->kind_class=R.array.food_1_5
                    "1.6"->kind_class=R.array.food_1_6
                    "1.7"->kind_class=R.array.food_1_7
                    "2.0"->kind_class=R.array.food_2_0
                    "2.1"->kind_class=R.array.food_2_1
                    "2.2"->kind_class=R.array.food_2_2
                    "2.3"->kind_class=R.array.food_2_3
                    "2.4"->kind_class=R.array.food_2_4
                    "2.5"->kind_class=R.array.food_2_5
                    "2.6"->kind_class=R.array.food_2_6
                    "2.7"->kind_class=R.array.food_2_7
                    "3.0"->kind_class=R.array.food_3_0
                    "3.1"->kind_class=R.array.food_3_1
                    "3.2"->kind_class=R.array.food_3_2
                    "3.3"->kind_class=R.array.food_3_3
                    "3.4"->kind_class=R.array.food_3_4
                    "3.5"->kind_class=R.array.food_3_5
                    "3.6"->kind_class=R.array.food_3_6
                    "3.7"->kind_class=R.array.food_3_7
                    "4.0"->kind_class=R.array.food_4_0
                    "4.1"->kind_class=R.array.food_4_1
                    "4.2"->kind_class=R.array.food_4_2
                    "4.3"->kind_class=R.array.food_4_3
                    "4.4"->kind_class=R.array.food_4_4
                    "4.5"->kind_class=R.array.food_4_5
                    "4.6"->kind_class=R.array.food_4_6
                    "4.7"->kind_class=R.array.food_4_7
                    "5.0"->kind_class=R.array.food_5_0
                    "5.1"->kind_class=R.array.food_5_1
                    "5.2"->kind_class=R.array.food_5_2
                    "5.3"->kind_class=R.array.food_5_3
                    "5.4"->kind_class=R.array.food_5_4
                    "5.5"->kind_class=R.array.food_5_5
                    "5.6"->kind_class=R.array.food_5_6
                    "5.7"->kind_class=R.array.food_5_7
                    "6.0"->kind_class=R.array.food_6_0
                    "6.1"->kind_class=R.array.food_6_1
                    "6.2"->kind_class=R.array.food_6_2
                    "6.3"->kind_class=R.array.food_6_3
                    "6.4"->kind_class=R.array.food_6_4
                    "6.5"->kind_class=R.array.food_6_5
                    "6.6"->kind_class=R.array.food_6_6
                    "6.7"->kind_class=R.array.food_6_7
                    "7.0"->kind_class=R.array.food_7_0
                    "7.1"->kind_class=R.array.food_7_1
                    "7.2"->kind_class=R.array.food_7_2
                    "7.3"->kind_class=R.array.food_7_3
                    "7.4"->kind_class=R.array.food_7_4
                    "7.5"->kind_class=R.array.food_7_5
                    "7.6"->kind_class=R.array.food_7_6
                    "7.7"->kind_class=R.array.food_7_7
                }
                var Array=getResources().getStringArray(kind_class)
                //sum_Calories+=(Array[1].toFloat()*0.01)*(list[i].quantity.toFloat())       //卡路里
                sum_Carbs+=(Array[2].toFloat()*0.01)*(list[i].quantity.toFloat())          //碳水化合物
                sum_Fat+=(Array[3].toFloat()*0.01)*(list[i].quantity.toFloat())            //脂肪
                sum_Protein+=(Array[4].toFloat()*0.01)*(list[i].quantity.toFloat())        //蛋白質
                //sum_Fiber+=(Array[5].toDouble()*0.01)*(list[i].quantity.toFloat())          //纖維素

            }
            //showToast(sum_Carbs.toFloat().toString()+"\n\n"+sum_Fat.toFloat().toString()+"\n\n"+sum_Protein.toFloat().toString())

            //setContentView(R.layout.activity_main)
            //val piechart = view.findViewById(R.id.bar_pie) 已在前面宣告
            //建立資料        //PieEntry格式為<所佔範圍100%,標籤>
            var entries = ArrayList<PieEntry>()
            var should_Calories = total    //應攝取_總卡路里量
            //var should_Carbs=1426.0    //應攝取碳水化合物
            //var should_Fat=1426.0    //應攝取脂肪
            //var should_Protein=1426.0    //應攝取蛋白質
            var plan = plan_index    //正常0,減脂1,增肌2
            if(plan==0){      //正常
                //should_Calories-=0.0
                //should_Carbs=should_Calories*0.5
                //should_Fat=should_Calories*0.3
                //should_Protein=should_Calories*0.2
            }else if(plan==1){    //減脂
                //should_Calories-=100.0
                //should_Carbs=should_Calories*0.4
                //should_Fat=should_Calories*0.2
                //should_Protein=should_Calories*0.4
            }else{     //增肌
                //should_Calories+=100.0
                //should_Carbs=should_Calories*0.55
                //should_Fat=should_Calories*0.2
                //should_Protein=should_Calories*0.25
            }
            //showToast(sum_Carbs.toFloat().toString()+"\n\n"+sum_Fat.toFloat().toString()+"\n\n"+sum_Protein.toFloat().toString())
            var temp=should_Calories-(sum_Carbs*4.0+sum_Fat*9.0+sum_Protein*4.0)
            entries.add(PieEntry((sum_Carbs*4.0).toFloat(),"碳水"))
            //entries.add(PieEntry((should_Carbs-sum_Carbs*4).toFloat(),"未攝取碳水"))

            entries.add(PieEntry((sum_Fat*9.0).toFloat(),"脂肪"))
            //entries.add(PieEntry((should_Fat-sum_Fat*9).toFloat(),"未攝取脂肪"))

            entries.add(PieEntry((sum_Protein*4.0).toFloat(),"蛋白質"))
            //entries.add(PieEntry((should_Protein-sum_Protein*4).toFloat(),"未攝取蛋白質"))
            entries.add(PieEntry(temp.toFloat(),"未攝取"))
            //建立顏色分類      //R.color.teal_200來自color.xml
            val colors = ArrayList<Int>()

            colors.add(resources.getColor(R.color.royalblue))
            //colors.add(resources.getColor(R.color.darkgray))

            colors.add(resources.getColor(R.color.cornflowerblue))
            //colors.add(resources.getColor(R.color.silver))

            colors.add(resources.getColor(R.color.skyblue))
            //colors.add(resources.getColor(R.color.lightgrey))

            colors.add(resources.getColor(R.color.silver))

            //
            pieChart.setDrawEntryLabels(false)
            pieChart.setHoleRadius(20f)//設定中心圓的半徑
            pieChart.setTransparentCircleAlpha(0)//設定透明園的透明度
            pieChart.getDescription().setEnabled(false) //關閉圖表的描述
            pieChart.setEntryLabelTextSize(14f) //設定圖表內的文字大小
            pieChart.setUsePercentValues(true)
            //圖例
            val legend = pieChart.getLegend()
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER)//設定圖例的位置
            legend.setFormSize(15f) //設定圖例的大小
            legend.setTextSize(15f) //設定圖例中文字的大小
            legend.setFormToTextSpace(5f) //設定圖例和文字之間的距離
            legend.setXEntrySpace(20f) //設定圖例之間的距離
            //

            //建立dataset
            val dataSet = PieDataSet(entries,"")
            dataSet.setColors(colors)
            //建立data 加入piechart
            val pieData = PieData(dataSet)
            pieData.setDrawValues(true)
            pieData.setValueTextSize(20f) //設定數值的字體大小
            pieData.setValueFormatter(PercentFormatter(DecimalFormat("##0"))) //設定數值的形式
            pieChart.setData(pieData)
            pieChart.invalidate()
        })
        /*
        //setContentView(R.layout.activity_main)
        pieChart.setHoleRadius(0f)//設定中心圓的半徑
        pieChart.setTransparentCircleAlpha(0)//設定透明園的透明度
        pieChart.setEntryLabelTextSize(20f) //設定圖表內的文字大小
        pieChart.setEntryLabelColor(resources.getColor(R.color.black)) ////設定圖表內的文字顏色
        pieChart.setUsePercentValues(true)
        //pieChart.setBackgroundColor(resources.getColor(R.color.pink2)) //設定圖表的背景


        //建立資料        //PieEntry格式為<所佔範圍100%,標籤>
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(63.36F,"未攝取熱量")) //(比例,文字)
        entries.add(PieEntry(87.84F,"碳水"))
        entries.add(PieEntry(120.24F,"蛋白質"))
        entries.add(PieEntry(88.56F,"脂肪"))
        //建立顏色分類      //R.color.teal_200來自color.xml
        val colors = ArrayList<Int>()
        colors.add(resources.getColor(R.color.teal_200))  //.colorPrimaryDark
        colors.add(resources.getColor(R.color.teal_700))   //.colorPrimary
        colors.add(resources.getColor(R.color.yellow9))
        colors.add(resources.getColor(R.color.purple3))
        //建立dataset
        val dataSet = PieDataSet(entries,"")
        dataSet.setColors(colors)
        //圖例
        val legend = pieChart.getLegend()
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER)//設定圖例的位置
        legend.setFormSize(15f) //設定圖例的大小
        legend.setTextSize(15f) //設定圖例中文字的大小
        legend.setFormToTextSpace(5f) //設定圖例和文字之間的距離
        legend.setXEntrySpace(20f) //設定圖例之間的距離
        //legend.setXOffset(25f)
        legend.setTextColor(resources.getColor(R.color.pink2)) //設定圖例中文字的顏色
        //
        //建立data 加入piechart
        val pieData = PieData(dataSet)
        //pieData.setDrawValues(true)
        pieData.setValueTextSize(20f) //設定數值的字體大小
        pieData.setValueFormatter(PercentFormatter(DecimalFormat("##0"))) //設定數值的形式

        pieChart.setData(pieData)
        pieChart.invalidate()
         */
    }
}