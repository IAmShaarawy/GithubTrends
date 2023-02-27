package dev.shaarawy.githubtrends.presentation.main.item

import dev.shaarawy.githubtrends.domain.dtos.TrendRepoModel

class ItemViewModel(
    private val model: TrendRepoModel? = null,
    private val onAction: ((ItemAction) -> Unit)? = null
) {
    fun onAction(action: ItemAction) = onAction?.invoke(action)
}