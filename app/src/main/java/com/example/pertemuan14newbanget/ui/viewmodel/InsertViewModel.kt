package com.example.pertemuan14newbanget.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan14newbanget.model.Mahasiswa
import com.example.pertemuan14newbanget.repository.MahasiswaRepository
import kotlinx.coroutines.launch

class InsertViewModel (
    private val mhs: MahasiswaRepository
) : ViewModel() {
    var uiEvent: InsertUiState by mutableStateOf(InsertUiState())
        private set
    var uiState: FormState by mutableStateOf(FormState.Idle)
        private set

    // Memperbarui state by input pengguna
    fun updateState(mahasiswaEvent: MahasiswaEvent) {
        uiEvent =uiEvent.copy(
            insertUiEvent = mahasiswaEvent,
        )
    }// Validasi data input pengguna
    fun validateFields() : Boolean {
        val event = uiEvent.insertUiEvent
        val errorState = FormErrorState(
            nim = if (event.nim?.isNotEmpty() == true) null else "NIM tidak boleh kosong",
            nama = if (event.nama?.isNotEmpty() == true) null else "Nama tidak boleh kosong",
            jenis_kelamin = if (event.jenis_kelamin?.isNotEmpty() == true) null else "Jenis Kelamin tidak boleh kosong",
            alamat = if (event.alamat?.isNotEmpty() == true) null else "Alamat tidak boleh kosong",
            kelas = if (event.kelas?.isNotEmpty() == true) null else "Kelas tidak boleh kosong",
            angkatan = if (event.angkatan?.isNotEmpty() == true) null else "angkatan tidak boleh kosong",
            judulskripsi = if (event.judulskripsi?.isNotEmpty() == true) null else "judulskripsi tidak boleh kosong",
            dosen1 = if (event.dosen1?.isNotEmpty() == true) null else "dosen1 tidak boleh kosong",
            dosen2 = if (event.dosen2?.isNotEmpty() == true) null else "dosen2 tidak boleh kosong"
        )
        uiEvent = uiEvent.copy(isEntryValid = errorState)
        return errorState.isValid()
    }// Fungsi insert view model
    fun insertMhs() {
        if (validateFields()) { // logika validasi insert
            viewModelScope.launch {
                uiState = FormState.Loading
                try {
                    mhs.insertMahasiswa(uiEvent.insertUiEvent.toMhsModel())
                    uiState = FormState.Success("Data berhasil disimpan")
                } catch (e: Exception) {
                    uiState = FormState.Error("Data gagal disimpan")
                }
            }
        } else {
            uiState = FormState.Error("Data tidak valid")
        }
    }
    // fungsi reset form
    fun resetForm() {
        uiEvent = InsertUiState()
        uiState = FormState.Idle
    }

    // fungsi reset snack bar message
    fun resetSnackBarMessage() {
        uiState = FormState.Idle
    }
}// membuat sealed class form state
sealed class FormState {
    object Idle : FormState()
    object Loading : FormState()
    data class Success(val message: String) : FormState()
    data class Error(val message: String) : FormState()
}// data class Insert Ui State
data class InsertUiState(
    val insertUiEvent: MahasiswaEvent = MahasiswaEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
)
// data class Form Error State
data class FormErrorState(
    val nim: String? = null,
    val nama: String? = null,
    val jenis_kelamin: String? = null,
    val alamat: String? = null,
    val kelas: String? = null,
    val angkatan: String? = null,
    val judulskripsi: String? = null,
    val dosen1: String? = null,
    val dosen2: String? = null
) {
    fun isValid(): Boolean {
        return nim == null &&
                nama == null &&
                jenis_kelamin == null &&
                alamat == null &&
                kelas == null &&
                angkatan == null &&
                judulskripsi == null &&
                dosen1 == null &&
                dosen2 == null
    }
}
//data class variabel yang menyimpan data input form
data class MahasiswaEvent(
    val nim: String? = "",
    val nama: String? = "",
    val jenis_kelamin: String? = "",
    val alamat: String? = "",
    val kelas: String? = "",
    val angkatan: String? = "",
    val judulskripsi: String? = "",
    val dosen1: String? = "",
    val dosen2: String? = ""
)
// Menyimpan input form kedalam entity
fun MahasiswaEvent.toMhsModel() : Mahasiswa = Mahasiswa(
    nim = nim?: "",
    nama = nama?: "",
    jenis_kelamin = jenis_kelamin?: "",
    alamat = alamat?: "",
    kelas = kelas?: "",
    angkatan = angkatan?: "",
    judulskripsi = judulskripsi?: "",
    dosen1 = dosen1?: "",
    dosen2 = dosen2?: ""
)









