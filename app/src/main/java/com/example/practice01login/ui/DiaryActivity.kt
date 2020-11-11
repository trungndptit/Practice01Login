package com.example.practice01login.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice01login.R
import com.example.practice01login.adapter.DiaryAdapter
import com.example.practice01login.api.AppService
import com.example.practice01login.api.DiaryResponse
import com.example.practice01login.db.UserDatabase
import com.example.practice01login.repository.AppRepo
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

        diaryViewModel.getListDiary()
    }

    private fun setupViewModels() {
        val db = UserDatabase.getInstance(this)
        val userDao = db.userDao()
        diaryViewModel.appRepo =
            AppRepo(appService, userDao)

    }

    private fun updateControls() {
        rv_diary.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        rv_diary.layoutManager = layoutManager
        rv_diary.adapter = adapter
    }

    private fun createDiaryObserver() {
        diaryViewModel.getDiary()?.observe(this, Observer<List<DiaryResponse>> {
            adapter.updateData(it)
            println("Debug size ${it.size}")
        })
    }

}