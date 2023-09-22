package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlanAdapter(private val data: List<Int>, private val layout: Int) : RecyclerView.Adapter<PlanAdapter.ViewHolder>() {


    //實作RecyclerView.ViewHolder來儲存view
    //建立recycleView的item click(2) 加入private val listener: RecycleViewClickListener?
    class ViewHolder(v: View, private val listener: RecyclerViewClickListener?): RecyclerView.ViewHolder(v){
        //連結畫面中的元件
        val tv_day = v.findViewById<TextView>(R.id.tv_day)
        fun setData(day: Int){
            tv_day.text = "第 ${day} 天"

            //建立recycleView的item click(3)
            itemView.setOnClickListener{
                listener?.onItemClick(day)
            }
        }
    }

    //建立recycleView的item click(4)
    private var clickListener: RecyclerViewClickListener? = null
    fun setListener(listener: RecyclerViewClickListener?) {
        this.clickListener = listener
    }


    //取得資料數量
    override fun getItemCount() = data.size

    //建立ViewHolder和Layout，並連結彼此
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.adapter_plan, viewGroup, false)
        //建立recycleView的item click(5) 傳入參數clickListener
        return ViewHolder(v,clickListener)
    }

    //把資料指派給元件呈現
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(data[position])
    }
}