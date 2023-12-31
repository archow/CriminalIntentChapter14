package com.example.criminalintentchapter14.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.criminalintentchapter14.Crime
import com.example.criminalintentchapter14.CrimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class CrimeDetailViewModel(crimeId: UUID) : ViewModel() {
    private val crimeRepository = CrimeRepository.get()
    private val _crime = MutableStateFlow<Crime?>(null)
    val crime : StateFlow<Crime?>
        get() = _crime.asStateFlow()

    init {
        viewModelScope.launch {
            _crime.value = crimeRepository.getCrime(crimeId)
        }
    }

    fun updateCrime(onUpdate: (oldCrime: Crime) -> Crime) {
        _crime.update { oldCrime ->
            oldCrime?.let(onUpdate)
        }
    }

    override fun onCleared() {
        super.onCleared()
        crime.value?.let {
            crimeRepository.updateCrime(it)
        }
    }
}

class CrimeDetailViewModelFactory(private val crimeId: UUID) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CrimeDetailViewModel(crimeId) as T
    }
}