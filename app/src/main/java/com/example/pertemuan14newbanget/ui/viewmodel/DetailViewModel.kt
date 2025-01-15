package com.example.pertemuan14newbanget.ui.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan14newbanget.model.Mahasiswa
import com.example.pertemuan14newbanget.repository.MahasiswaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val mahasiswa: Mahasiswa) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}

class DetailViewModel(private val repository: MahasiswaRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState

    fun getMhsDetail(nim: String) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            repository.getMahasiswaByNim(nim)
                .catch { exception ->
                    _uiState.value = DetailUiState.Error("Error: ${exception.message}")
                }
                .collect { mahasiswa ->
                    _uiState.value = DetailUiState.Success(mahasiswa)
                }
        }
    }
}