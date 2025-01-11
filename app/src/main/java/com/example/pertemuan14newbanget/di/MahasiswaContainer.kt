package com.example.pertemuan14newbanget.di

import com.example.pertemuan14newbanget.repository.MahasiswaRepository
import com.example.pertemuan14newbanget.repository.NetworkMahasiswaRepository
import com.google.firebase.firestore.FirebaseFirestore

interface AppContainer {
    val mahasiswaRepository: MahasiswaRepository
}

class MahasiswaContainer: AppContainer{
    private val firebase: FirebaseFirestore = FirebaseFirestore.getInstance()      // Sama dengan Base URL

    override val mahasiswaRepository: MahasiswaRepository by lazy {
        NetworkMahasiswaRepository(firebase)
    }
}