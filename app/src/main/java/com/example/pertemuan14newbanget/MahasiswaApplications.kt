package com.example.pertemuan14newbanget

import android.app.Application
import com.example.pertemuan14newbanget.di.AppContainer
import com.example.pertemuan14newbanget.di.MahasiswaContainer

class MahasiswaApplications : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = MahasiswaContainer()
    }
}