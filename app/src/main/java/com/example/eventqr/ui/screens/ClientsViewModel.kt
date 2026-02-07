package com.example.eventqr.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventqr.domain.model.Client
import com.example.eventqr.domain.repo.ClientRepository
import com.example.eventqr.domain.usecase.ParseQrToClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientsViewModel @Inject constructor(
    private val repo: ClientRepository,
    private val parser: ParseQrToClientUseCase
) : ViewModel() {

    val clients: StateFlow<List<Client>> =
        repo.observeAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _lastAction = MutableStateFlow("")
    val lastAction: StateFlow<String> = _lastAction

    fun onQrScanned(raw: String) {
        val client = parser.parse(raw)
        if (client == null) {
            _lastAction.value = "Invalid QR"
            return
        }

        viewModelScope.launch {
            val inserted = repo.addClientIgnoreDuplicate(client)
            _lastAction.value = if (inserted) "Saved" else "Duplicate"
        }
    }
}
