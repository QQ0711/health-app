package com.example.finalproject

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.databinding.ActivityCreateContactBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class CreateContactActivity : AppCompatActivity() {
    // private variable to inflate the layout for the activity
    private lateinit var binding : ActivityCreateContactBinding
    private lateinit var back: ImageView

    // variable to access the ViewModel class
    val viewModel : ContactViewModel by viewModels()
    var temp=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inflate the layout
        setTitle("飲食")
        binding = ActivityCreateContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set onClickListener for save button
        binding.save.setOnClickListener{
            if(binding.quantity.length()<1){
                showToast("欄位請勿留空")
            }else{
                createContact(it)
            }
        }

        var kind = intent.getStringExtra("kind_key")
        var food = intent.getStringExtra("food_key")

        var kind_class=R.array.food_0_0
        temp=kind.toString()+"."+food.toString()
        when(temp.toDouble()){
            0.0->kind_class=R.array.food_0_0
            0.1->kind_class=R.array.food_0_1
            0.2->kind_class=R.array.food_0_2
            0.3->kind_class=R.array.food_0_3
            0.4->kind_class=R.array.food_0_4
            0.5->kind_class=R.array.food_0_5
            0.6->kind_class=R.array.food_0_6
            0.7->kind_class=R.array.food_0_7
            1.0->kind_class=R.array.food_1_0
            1.1->kind_class=R.array.food_1_1
            1.2->kind_class=R.array.food_1_2
            1.3->kind_class=R.array.food_1_3
            1.4->kind_class=R.array.food_1_4
            1.5->kind_class=R.array.food_1_5
            1.6->kind_class=R.array.food_1_6
            1.7->kind_class=R.array.food_1_7
            2.0->kind_class=R.array.food_2_0
            2.1->kind_class=R.array.food_2_1
            2.2->kind_class=R.array.food_2_2
            2.3->kind_class=R.array.food_2_3
            2.4->kind_class=R.array.food_2_4
            2.5->kind_class=R.array.food_2_5
            2.6->kind_class=R.array.food_2_6
            2.7->kind_class=R.array.food_2_7
            3.0->kind_class=R.array.food_3_0
            3.1->kind_class=R.array.food_3_1
            3.2->kind_class=R.array.food_3_2
            3.3->kind_class=R.array.food_3_3
            3.4->kind_class=R.array.food_3_4
            3.5->kind_class=R.array.food_3_5
            3.6->kind_class=R.array.food_3_6
            3.7->kind_class=R.array.food_3_7
            4.0->kind_class=R.array.food_4_0
            4.1->kind_class=R.array.food_4_1
            4.2->kind_class=R.array.food_4_2
            4.3->kind_class=R.array.food_4_3
            4.4->kind_class=R.array.food_4_4
            4.5->kind_class=R.array.food_4_5
            4.6->kind_class=R.array.food_4_6
            4.7->kind_class=R.array.food_4_7
            5.0->kind_class=R.array.food_5_0
            5.1->kind_class=R.array.food_5_1
            5.2->kind_class=R.array.food_5_2
            5.3->kind_class=R.array.food_5_3
            5.4->kind_class=R.array.food_5_4
            5.5->kind_class=R.array.food_5_5
            5.6->kind_class=R.array.food_5_6
            5.7->kind_class=R.array.food_5_7
            6.0->kind_class=R.array.food_6_0
            6.1->kind_class=R.array.food_6_1
            6.2->kind_class=R.array.food_6_2
            6.3->kind_class=R.array.food_6_3
            6.4->kind_class=R.array.food_6_4
            6.5->kind_class=R.array.food_6_5
            6.6->kind_class=R.array.food_6_6
            6.7->kind_class=R.array.food_6_7
            7.0->kind_class=R.array.food_7_0
            7.1->kind_class=R.array.food_7_1
            7.2->kind_class=R.array.food_7_2
            7.3->kind_class=R.array.food_7_3
            7.4->kind_class=R.array.food_7_4
            7.5->kind_class=R.array.food_7_5
            7.6->kind_class=R.array.food_7_6
            7.7->kind_class=R.array.food_7_7
        }
        var Array=getResources().getStringArray(kind_class)
        findViewById<TextView>(R.id.textView4).text= "${Array[0]}\n"
        findViewById<TextView>(R.id.textView7).text=
            "卡路里:  \n" +
                    "碳水化合物:  \n" +
                    "脂肪:  \n" +
                    "蛋白質:  \n" +
                    "纖維素:  "

        findViewById<TextView>(R.id.textView9).text=
            "${Array[1]} 大卡\n" +
                    "${Array[2]} 克\n" +
                    "${Array[3]} 克\n" +
                    "${Array[4]} 克\n" +
                    "${Array[5]} 克"

        //第一步
        val date_show=findViewById<TextView>(R.id.textView12)
        val choosedate=findViewById<ImageButton>(R.id.imageButton_1)
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
            }
        }

//第三步
        choosedate.setOnClickListener {  //日曆按鈕的點擊事件
            DatePickerDialog(this,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        backChooseActivity()
    }


    // function to create new contact and call
    // the addContacts function from the ViewModel class
    private fun createContact(it: View?) {

        //showToast(datetime)
        val setdate = binding.textView12.text.toString()
        val food = temp.toString()
        val quantity = binding.quantity.text.toString()
        // create new contact object
        //val data = Contacts(null,datemark = datetime , food = food , quantity=quantity)
        val data = Contacts(null,datemark=setdate,food = food , quantity=quantity)
        // call addContacts function from the ViewModel class
        viewModel.addContacts(data)
        // display a Toast message to confirm the save
        Toast.makeText(this@CreateContactActivity, "Saved", Toast.LENGTH_SHORT).show()

        // start MainActivity
        //startActivity(Intent(this@CreateContactActivity,MainActivity::class.java))
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("f_name","eat")
        startActivity(intent)
    }

    //按下返回鍵，返回MainActivity
    private fun backChooseActivity(){
        back = findViewById(R.id.back2)
        back.setOnClickListener {
            val intent = Intent(this, ChooseActivity::class.java)
            startActivity(intent)
        }
    }
    private fun showToast(text: String) =
        Toast.makeText(this,text, Toast.LENGTH_LONG).show()
}