package com.madeit.shooters.presentation.shooters

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madeit.shooters.data.model.GameItem
import com.madeit.shooters.data.repository.GameRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GamesViewModel : ViewModel() {
    private val gameRepository = GameRepository()

    private val _allGamesFlow = MutableStateFlow<List<GameItem>>(emptyList())
    val allGamesFlow: StateFlow<List<GameItem>> = _allGamesFlow

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        getAllPopularGames()
    }

    private fun getAllPopularGames() = viewModelScope.launch {
        _loading.emit(true)
        val games = gameRepository.getPopularGames() ?: emptyList()
        _allGamesFlow.emit(games)
        _loading.emit(false)
    }

    fun updateSearchQuery(query: String) = viewModelScope.launch{
        _searchQuery.value = query
    }

    val filteredGamesFlow: StateFlow<List<GameItem>> =
        combine(_allGamesFlow, _searchQuery) { games, query ->
            if (query.isBlank()) games else games.filter { it.title.contains(query, ignoreCase = true) }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

}