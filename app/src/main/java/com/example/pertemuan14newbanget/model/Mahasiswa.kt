package com.example.pertemuan14newbanget.model

data class Mahasiswa (
    val nim: String,
    val nama: String,
    val alamat: String,
    val jenis_kelamin: String,
    val kelas: String,
    val angkatan: String,
    val judulskripsi: String,
    val dosen1: String,
    val dosen2: String,
)
{
    constructor(
    ):this("",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        )
}
