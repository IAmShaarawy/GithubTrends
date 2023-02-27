package dev.shaarawy.githubtrends.domain.dtos

data class PrettyCount(val double: Double, val multiplier: Multiplier)
enum class Multiplier {
    Non, K, M
}