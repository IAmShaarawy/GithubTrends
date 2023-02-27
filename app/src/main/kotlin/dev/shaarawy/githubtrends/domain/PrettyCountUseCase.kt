package dev.shaarawy.githubtrends.domain


import dev.shaarawy.githubtrends.domain.dtos.Multiplier
import dev.shaarawy.githubtrends.domain.dtos.PrettyCount
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

interface PrettyCountUseCase : (Int) -> PrettyCount
class PrettyCountUseCaseImpl @Inject constructor() : PrettyCountUseCase {
    override fun invoke(number: Int): PrettyCount {
        val millions = BigDecimal(number / 1_000_000.00)
        val prettyMillion = millions.setScale(1, RoundingMode.HALF_UP)
        if (millions >= BigDecimal(1)) {
            return PrettyCount(prettyMillion.toDouble(), Multiplier.M)
        }

        val thousands = BigDecimal(number / 1_000.00)
        val prettyThousands = thousands.setScale(1, RoundingMode.HALF_UP)
        if (thousands >= BigDecimal(1)) {
            return PrettyCount(prettyThousands.toDouble(), Multiplier.K)
        }

        return PrettyCount(number.toDouble(), Multiplier.Non)
    }
}