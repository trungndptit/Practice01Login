package com.example.practice01login.adapter

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T, V : RecyclerView.ViewHolder> : RecyclerView.Adapter<V>() {
     var mDataList = ArrayList<T>()
    private var mItemClickListener: OnItemClickListener<T>? = null

    override fun getItemCount(): Int {
        return mDataList.size
    }

    fun getDataList(): List<T> {
        return mDataList
    }

    fun updateData(dataList: List<T>) {
        mDataList.clear()
        mDataList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T? {
        if (position < 0 || position > itemCount) {
            return null
        }
        return mDataList.get(position)
    }

    fun addItem(data: T, position: Int) {
        mDataList.add(position, data)
        notifyItemChanged(position)
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener<T>) {
        mItemClickListener = itemClickListener
    }


    interface OnItemClickListener<T> {
        fun onItemClicked(item: T)
    }
}