package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class SportFragment : Fragment() {
    private lateinit var view: View
    private lateinit var tv_health: TextView
    private lateinit var tv_fat: TextView
    private lateinit var tv_muscle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //在 onCreateView 中定義 SportFragment 的畫面為 fragment_sport
    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.fragment_sport, container, false)
        requireActivity().setTitle("運動")

        binding() //將變數和xml元件綁定
        setListener() //設定監聽器
        return view
    }

    //設定監聽器
    private fun setListener(){
        tv_health.setOnClickListener {
            val intent = Intent(requireActivity(), PlanActivity::class.java)
            intent.putExtra("type", tv_health.text)
            startActivity(intent)
        }

        tv_fat.setOnClickListener {
            val intent = Intent(requireActivity(), PlanActivity::class.java)
            intent.putExtra("type",tv_fat.text)
            startActivity(intent)
        }

        tv_muscle.setOnClickListener {
            val intent = Intent(requireActivity(), PlanActivity::class.java)
            intent.putExtra("type",tv_muscle.text)
            startActivity(intent)
        }
    }

    //將變數和xml元件綁定
    private fun binding(){
        tv_health = view.findViewById(R.id.tv_health)
        tv_fat = view.findViewById(R.id.tv_fat)
        tv_muscle = view.findViewById(R.id.tv_muscle)
    }
}