package dev.shaarawy.githubtrends.presentation.main

import dev.shaarawy.githubtrends.presentation.main.item.ItemViewModel

sealed class MainState {
    object Loading : MainState()
    object Empty : MainState()
    data class Error(val throwable: Throwable) : MainState()
    data class Content(val data: List<ItemViewModel>) : MainState()
}
