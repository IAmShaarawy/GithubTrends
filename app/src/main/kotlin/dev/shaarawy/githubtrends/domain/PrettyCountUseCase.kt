package dev.shaarawy.githubtrends.domain


import dev.shaarawy.githubtrends.domain.dtos.PrettyCount
import javax.inject.Inject

interface PrettyCountUseCase : (Int) -> PrettyCount
class PrettyCountUseCaseImpl @Inject constructor() : PrettyCountUseCase {
    override fun invoke(number: Int): PrettyCount {
        TODO("no implementation")
    }

}