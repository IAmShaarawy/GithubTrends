package dev.shaarawy.githubtrends.presentation.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shaarawy.githubtrends.domain.TrendingRepoUseCase
import dev.shaarawy.githubtrends.presentation.main.item.ItemAction
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val trendingRepoUseCase: TrendingRepoUseCase
) : ViewModel() {
    private val _mainViewState = MutableStateFlow<MainState>(MainState.Loading)
    val mainViewState = _mainViewState.asStateFlow()

    private val _onItemAction = MutableSharedFlow<ItemAction>()
    val onItemAction = _onItemAction.asSharedFlow()

    fun onAction(action: MainAction) {
        TODO("not implemented")
    }
}