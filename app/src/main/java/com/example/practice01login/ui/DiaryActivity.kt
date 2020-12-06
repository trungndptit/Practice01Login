package com.example.practice01login.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice01login.R
import com.example.practice01login.adapter.DiaryAdapter
import com.example.practice01login.api.AppService
import com.example.practice01login.db.DiaryDatabase
import com.example.practice01login.db.DiaryEntity
import com.example.practice01login.repository.DiaryRepo
import com.example.practice01login.viewmodel.DiaryViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_diary.*
import javax.inject.Inject

class DiaryActivity : DaggerAppCompatActivity() {
    var adapter = DiaryAdapter()
    private val diaryViewModel by viewModels<DiaryViewModel>()

    @Inject
    lateinit var appService: AppService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        setupViewModels()
        updateControls()
        createDiaryObserver()

        diaryViewModel.getAllDiaryInDb()
//        diaryViewModel.getListDiary()
//        diaryViewModel.getAllDiaryInDb()
    }

    private fun setupViewModels() {
        val db = DiaryDatabase.getInstance(this)
        val diaryDao = db.diaryDao()
        diaryViewModel.diaryRepo =
            DiaryRepo(appService, diaryDao)

    }

    private fun updateControls() {
        rv_diary.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        rv_diary.layoutManager = layoutManager
        rv_diary.adapter = adapter
    }

    private fun createDiaryObserver() {
        diaryViewModel.getDiary()?.observe(this, Observer<List<DiaryEntity>> {
            adapter.updateData(it)
            println("Debug size ${it.size}")
        })
    }

}