package dev.shaarawy.githubtrends.domain

import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.shaarawy.githubtrends.domain.dtos.Multiplier
import dev.shaarawy.githubtrends.domain.dtos.PrettyCount
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
class PrettyCountUseCaseTest {
    @get:Rule
    val hiltRule by lazy { HiltAndroidRule(this) }

    @Inject
    lateinit var subjectUnderTest: PrettyCountUseCase

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun `test below thousand`() {
        // given
        val number = 486
        val expected = PrettyCount(number.toDouble(),Multiplier.Non)

        // when
        val actual = subjectUnderTest.invoke(number)

        // then
        assertThat(actual).isEqualTo(expected)
    }


    @Test
    fun `test thousand`() {
        // given
        val number = 1_000
        val expected = PrettyCount(1.0,Multiplier.K)

        // when
        val actual = subjectUnderTest.invoke(number)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `test lower thousands`() {
        // given
        val number = 1230
        val expected = PrettyCount(1.2,Multiplier.K)

        // when
        val actual = subjectUnderTest.invoke(number)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `test upper thousands`() {
        // given
        val number = 1260
        val expected = PrettyCount(1.3,Multiplier.K)

        // when
        val actual = subjectUnderTest.invoke(number)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `test million`() {
        // given
        val number = 1_000_000
        val expected = PrettyCount(1.0,Multiplier.M)

        // when
        val actual = subjectUnderTest.invoke(number)

        // then
        assertThat(actual).isEqualTo(expected)
    }
    @Test
    fun `test lower millions`() {
        // given
        val number = 1_230_000
        val expected = PrettyCount(1.2,Multiplier.M)

        // when
        val actual = subjectUnderTest.invoke(number)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `test upper millions`() {
        // given
        val number = 1_260_000
        val expected = PrettyCount(1.3,Multiplier.M)

        // when
        val actual = subjectUnderTest.invoke(number)

        // then
        assertThat(actual).isEqualTo(expected)
    }
}