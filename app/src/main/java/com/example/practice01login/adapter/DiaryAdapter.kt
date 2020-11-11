package com.example.practice01login.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practice01login.R
import com.example.practice01login.api.DiaryResponse
import kotlinx.android.synthetic.main.layout_group.view.*
import kotlinx.android.synthetic.main.layout_item_diary.view.*


class DiaryAdapter : BaseRecyclerViewAdapter<DiaryResponse, RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            Common.VIEWTYPE_HEADER -> {
                var group: ViewGroup =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_group, parent, false) as ViewGroup
                return GroupViewHolder(group)
            }
            Common.VIEWTYPE_DIARY -> {
                var group: ViewGroup =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_item_diary, parent, false) as ViewGroup
                return DiaryViewHolder(group)
            }
            else -> {
                var group: ViewGroup =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_group, parent, false) as ViewGroup
                return GroupViewHolder(group)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mDataList[position].viewType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val element = mDataList[position]
        when(holder){
            is GroupViewHolder -> holder.bind(element)
            is DiaryViewHolder -> holder.bind(element)
            else -> throw IllegalArgumentException()
        }
    }

    class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(diary: DiaryResponse) {
            itemView.tv_group_date.text = diary.date
            itemView.tv_group_title.text = diary.title
            itemView.tv_group_content.text = diary.content
        }

    }

    class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(diary: DiaryResponse) {
            itemView.tv_diary_title.text = diary.title
            itemView.tv_diary_content.text = diary.content
        }
    }

}