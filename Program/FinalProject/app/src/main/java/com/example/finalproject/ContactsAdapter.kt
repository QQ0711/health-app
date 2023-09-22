package com.example.finalproject

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ContactsLayoutBinding

class ContactsAdapter(val context : Context, val list : List<Contacts>) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    // Inner ViewHolder class
    class ViewHolder(val binding : ContactsLayoutBinding) : RecyclerView.ViewHolder(binding.root){}

    // DAO instance to interact with the database
    private val dao = ContactDatabase.getDatabaseInstance(context).contactsDao()

    // function to inflate the layout for each contact and create a new ViewHolder instance
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ContactsLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    // function to bind the data to the view elements of the ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var temp="米飯"
        when(list[position].food){
            "0.0"->temp="米飯"
            "0.1"->temp="水煮麵條"
            "0.2"->temp="麥片"
            "0.3"->temp="地瓜"
            "0.4"->temp="鷹嘴豆"
            "0.5"->temp="義大利麵"
            "0.6"->temp="饅頭"
            "0.7"->temp="玉米"
            "1.0"->temp="番茄"
            "1.1"->temp="花椰菜"
            "1.2"->temp="大白菜"
            "1.3"->temp="高麗菜"
            "1.4"->temp="洋蔥"
            "1.5"->temp="韭菜"
            "1.6"->temp="胡蘿蔔"
            "1.7"->temp="秋葵"
            "2.0"->temp="蘋果"
            "2.1"->temp="香蕉"
            "2.2"->temp="橘子"
            "2.3"->temp="奇異果"
            "2.4"->temp="水梨"
            "2.5"->temp="芒果"
            "2.6"->temp="芭樂"
            "2.7"->temp="鳳梨"
            "3.0"->temp="豬肉"
            "3.1"->temp="雞肉"
            "3.2"->temp="鮭魚"
            "3.3"->temp="鱸魚"
            "3.4"->temp="蛤蠣"
            "3.5"->temp="草蝦"
            "3.6"->temp="羊肉"
            "3.7"->temp="牛肉"
            "4.0"->temp="水煮雞蛋"
            "4.1"->temp="鹹鴨蛋"
            "4.2"->temp="起司"
            "4.3"->temp="優格"
            "4.4"->temp="豆腐"
            "4.5"->temp="豆皮"
            "4.6"->temp="毛豆"
            "4.7"->temp="黑豆"
            "5.0"->temp="橄欖油"
            "5.1"->temp="菜籽油"
            "5.2"->temp="芝麻油"
            "5.3"->temp="奶油"
            "5.4"->temp="核桃"
            "5.5"->temp="開心果"
            "5.6"->temp="花生"
            "5.7"->temp="葵花子"
            "6.0"->temp="醬油"
            "6.1"->temp="糖"
            "6.2"->temp="醋"
            "6.3"->temp="鹽"
            "6.4"->temp="蜂蜜"
            "6.5"->temp="辣椒醬"
            "6.6"->temp="番茄醬"
            "6.7"->temp="沙拉醬"
            "7.0"->temp="水"
            "7.1"->temp="可樂"
            "7.2"->temp="啤酒"
            "7.3"->temp="黑咖啡粉"
            "7.4"->temp="無糖茶"
            "7.5"->temp="養樂多"
            "7.6"->temp="可可粉"
            "7.7"->temp="寶礦力水得"
        }
        holder.binding.contactName.text = temp
        holder.binding.contactNumber.text = list[position].quantity+"g"
        // delete button onClickListener to delete the
        // contact from the database and notify the
        // adapter of the change
        holder.binding.deleteButton.setOnClickListener{
            dao.delete(list[position])
            notifyItemRemoved(position)
        }
    }

    // function returns the number of items in the list
    override fun getItemCount(): Int {
        return list.size
    }
}
