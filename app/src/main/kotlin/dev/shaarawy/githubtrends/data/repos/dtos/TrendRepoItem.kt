package dev.shaarawy.githubtrends.data.repos.dtos

data class TrendRepoItem(
    val id: Int?,
    val name: String?,
    val description: String?,
    val url: String?,
    val language: String?,
    val starsCount: Int?,
    val owner: Owner
) {
    data class Owner(
        val id: Int?,
        val name: String?,
        val avatarUrl: String?,
        val url: String?
    )
}