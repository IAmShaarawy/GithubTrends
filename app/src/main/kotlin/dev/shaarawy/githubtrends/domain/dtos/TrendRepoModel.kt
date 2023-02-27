package dev.shaarawy.githubtrends.domain.dtos

data class TrendRepoModel(
    val id: Int,
    val name: String,
    val description: String,
    val url: String,
    val language: String,
    val starsCount: PrettyCount,
    val owner: Owner,
    val colorHex: String
) {
    data class Owner(
        val id: Int,
        val name: String,
        val avatarUrl: String,
        val url: String
    )
}
