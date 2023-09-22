package com.example.finalproject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.finalproject.databinding.ActivityChooseBinding

class ChooseActivity : AppCompatActivity() {
    private var p=0
    private var t=R.array.list_1
    private var photo_t = R.array.photo_list_1
    //val gridView_2=findViewById<GridView>(R.id.gridView_2)
    private lateinit var gridView_2: GridView
    private lateinit var binding : ActivityChooseBinding
    val viewModel : ContactViewModel by viewModels()
    private lateinit var back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("飲食")
        // inflate the layout
        binding = ActivityChooseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*
        // Observe the LiveData returned by the getAllContacts method
        viewModel.getAllContacts().observe(this , Observer { list->
            // set the layout manager and the adapter for the recycler view
            binding.gridView_1.layoutManager = LinearLayoutManager(applicationContext)
            binding.recyclerView.adapter = ContactsAdapter(this,list)
        })
        */

        val gridView_1=findViewById<GridView>(R.id.gridView_1)
        val item = ArrayList<Item>()
        val array=resources.obtainTypedArray(R.array.kind_photo)   //從 R 類別讀取圖檔
        val nameArray=getResources().getStringArray(R.array.kind_list)
        for(i in 0 until array.length()) {
            val photo = array.getResourceId(i,0) //水果圖片 Id
            val name = nameArray[i]
            item.add(Item(photo,name))   //新增資訊
        }
        array.recycle() //釋放圖檔資源
        //設定橫向顯示列數
        gridView_1.numColumns = 3
        //建立 MyAdapter 物件，並傳入 adapter_vertical 作為畫面
        gridView_1.adapter = ChooseAdapter(this, item, R.layout.adapter_choose)
        gridView_2=findViewById<GridView>(R.id.gridView_2)
        show()
        //item點選
        gridView_1.setOnItemClickListener { parent, view, position, id ->
            p=position
            //showToast("選取:${p}")
            when(p){
                0->t=R.array.list_1
                1->t=R.array.list_2
                2->t=R.array.list_3
                3->t=R.array.list_4
                4->t=R.array.list_5
                5->t=R.array.list_6
                6->t=R.array.list_7
                7->t=R.array.list_8
            }
            when(p){
                0->photo_t=R.array.photo_list_1
                1->photo_t=R.array.photo_list_2
                2->photo_t=R.array.photo_list_3
                3->photo_t=R.array.photo_list_4
                4->photo_t=R.array.photo_list_5
                5->photo_t=R.array.photo_list_6
                6->photo_t=R.array.photo_list_7
                7->photo_t=R.array.photo_list_8
            }
            show()
        }
        gridView_2.setOnItemClickListener { parent, view, position, id ->
            //position
            //val b=Bundle()
            //b.putString("kind_key",p.toString())
            //b.putString("food_key",position.toString())
            val intent = Intent(this , CreateContactActivity::class.java)
            //setResult(Activity.RESULT_OK,intent.putExtras(b))
            intent.putExtra("kind_key", p.toString())
            intent.putExtra("food_key", position.toString())
            startActivity(intent)
            //finish()
        }
        backMainActivity()
    }

    private fun show(){
        //val gridView_2=findViewById<GridView>(R.id.gridView_2)
        var item_2 = ArrayList<Item>()
        var array_2=resources.obtainTypedArray(photo_t)   //從 R 類別讀取圖檔
        var nameArray_2=getResources().getStringArray(t)
        for(i in 0 until array_2.length()) {
            val photo = array_2.getResourceId(i,0) //水果圖片 Id
            val name = nameArray_2[i]
            item_2.add(Item(photo,name))   //新增資訊
        }
        array_2.recycle() //釋放圖檔資源
        //設定橫向顯示列數
        gridView_2.numColumns = 3
        //建立 MyAdapter 物件，並傳入 adapter_vertical 作為畫面
        gridView_2.adapter = ChooseAdapter(this, item_2, R.layout.adapter_choose)
    }

    //按下返回鍵，返回MainActivity
    private fun backMainActivity(){
        back = findViewById(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("f_name","eat")
            startActivity(intent)
        }
    }

    private fun showToast(text: String) =
        Toast.makeText(this,text, Toast.LENGTH_LONG).show()
}