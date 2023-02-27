package dev.shaarawy.githubtrends.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shaarawy.githubtrends.domain.TrendingRepoUseCase
import dev.shaarawy.githubtrends.foundation.DispatchersProvider
import dev.shaarawy.githubtrends.presentation.main.item.ItemAction
import dev.shaarawy.githubtrends.presentation.main.item.ItemViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val trendingRepoUseCase: TrendingRepoUseCase,
    private val dispatchersProvider: DispatchersProvider,
) : ViewModel() {
    private val _mainViewState = MutableStateFlow<MainState>(MainState.Loading)
    val mainViewState = _mainViewState.asStateFlow()

    private val _onItemAction = MutableSharedFlow<ItemAction>()
    val onItemAction = _onItemAction.asSharedFlow()

    init {
        loadScreen()
    }

    private fun loadScreen() {
        trendingRepoUseCase.invoke()
            .map { it.map { item -> ItemViewModel(item, this::onItemAction) } }
            .map { if (it.isEmpty()) MainState.Empty else MainState.Content(it) }
            .onStart { emit(MainState.Loading) }
            .catch { emit(MainState.Error(it)) }
            .onEach(_mainViewState::emit)
            .flowOn(dispatchersProvider.default)
            .launchIn(viewModelScope)
    }

    private fun onItemAction(item: ItemAction) {
        viewModelScope.launch {
            _onItemAction.emit(item)
        }
    }

    fun onAction(action: MainAction) {
        when (action) {
            MainAction.Retry -> loadScreen()
        }
    }
}