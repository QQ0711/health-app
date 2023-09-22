package com.example.finalproject

//建立recycleView的item click共有5個步驟(1)~(6)
//建立recycleView的item click(1)
interface RecyclerViewClickListener {
    fun onItemClick(position: Int)
}