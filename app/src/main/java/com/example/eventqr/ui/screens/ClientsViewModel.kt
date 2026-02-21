package com.example.eventqr.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventqr.domain.model.Client
import com.example.eventqr.domain.model.ClientType
import com.example.eventqr.domain.repo.ClientRepository
import com.example.eventqr.domain.usecase.ParseQrToClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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

    private val _typeFilter = MutableStateFlow<ClientType?>(null)
    val typeFilter: StateFlow<ClientType?> = _typeFilter

    private val _companyFilter = MutableStateFlow("")
    val companyFilter: StateFlow<String> = _companyFilter

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val filteredClients: StateFlow<List<Client>> =
        combine(clients, _typeFilter, _companyFilter, _searchQuery) { list, type, company, query ->
            val companyNorm = company.trim().lowercase()
            val queryNorm = query.trim().lowercase()

            list.asSequence()
                .filter { c -> type == null || c.clientType == type }
                .filter { c ->
                    companyNorm.isEmpty() || c.company.lowercase().contains(companyNorm)
                }
                .filter { c ->
                    if (queryNorm.isEmpty()) true
                    else {
                        c.firstName.lowercase().contains(queryNorm) ||
                                c.lastName.lowercase().contains(queryNorm) ||
                                c.company.lowercase().contains(queryNorm)
                    }
                }
                .toList()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setTypeFilter(type: ClientType?) {
        _typeFilter.value = type
    }

    fun setCompanyFilter(value: String) {
        _companyFilter.value = value
    }

    fun setSearchQuery(value: String) {
        _searchQuery.value = value
    }

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

    fun clearLastAction() {
        _lastAction.value = ""
    }
}
