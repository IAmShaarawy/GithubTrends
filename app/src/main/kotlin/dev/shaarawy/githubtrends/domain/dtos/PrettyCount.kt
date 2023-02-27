package dev.shaarawy.githubtrends.domain.dtos

data class PrettyCount(val double: Double, val multiplier: Multiplier) {
    override fun toString(): String = "$double ${multiplier.litter}".trimEnd()
}

enum class Multiplier(val litter: String) {
    Non(""), K("K"), M("M")
}