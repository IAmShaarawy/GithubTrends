package dev.shaarawy.githubtrends.presentation.main.item

sealed class ItemAction {
    data class OpenWebLink(val link: String) : ItemAction()
}
