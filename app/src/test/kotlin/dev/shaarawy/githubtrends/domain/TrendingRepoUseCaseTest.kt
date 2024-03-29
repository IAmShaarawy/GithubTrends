package dev.shaarawy.githubtrends.domain


import app.cash.turbine.test
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.shaarawy.githubtrends.data.remote.dtos.TrendingReposResponse
import dev.shaarawy.githubtrends.di.DispatcherProviderModuleMock.*
import dev.shaarawy.githubtrends.domain.dtos.Multiplier
import dev.shaarawy.githubtrends.foundation.fakeDataPath
import dev.shaarawy.githubtrends.foundation.readJSONFile
import dev.shaarawy.githubtrends.foundation.readTextFile
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.internal.closeQuietly
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
class TrendingRepoUseCaseTest {

    @get:Rule
    val hiltRule by lazy { HiltAndroidRule(this) }

    @Inject
    lateinit var subjectUnderTest: TrendingRepoUseCase

    @Inject
    lateinit var server: MockWebServer

    @Inject
    @TestDispatcherQualifier
    lateinit var testDispatcher: TestDispatcher

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        server.closeQuietly()
    }

    @Test
    fun `ensure that repos without url or empty will not show up`(): Unit =
        runTest(testDispatcher) {
            // given
            val expectedCount = 3
            val response = MockResponse().apply { setBody(readTextFile(fakeDataPath)) }
            server.enqueue(response)

            // when
            val flowSubject = subjectUnderTest.invoke()
            advanceUntilIdle()

            // then
            flowSubject.test {
                Truth.assertThat(awaitItem()).hasSize(expectedCount)
                cancelAndConsumeRemainingEvents()
            }

        }

    @Test
    fun `ensure that repos owners without url or empty will not show up`(): Unit =
        runTest(testDispatcher) {
            // given
            val expectedCount = 3
            val response = MockResponse().apply { setBody(readTextFile(fakeDataPath)) }
            server.enqueue(response)

            // when
            val flowSubject = subjectUnderTest.invoke()
            advanceUntilIdle()

            // then
            flowSubject.test {
                Truth.assertThat(awaitItem()).hasSize(expectedCount)
                cancelAndConsumeRemainingEvents()
            }

        }

    @Test
    fun `ensure that repos stars count is valid`(): Unit = runTest(testDispatcher) {
        // given
        val expected = readJSONFile<TrendingReposResponse>(fakeDataPath).items?.first {
            (it.stargazersCount ?: 0) / 1000 > 1 && it.url!=null && it.owner?.url!=null
        }
        val response = MockResponse().apply { setBody(readTextFile(fakeDataPath)) }
        server.enqueue(response)

        // when
        val flowSubject = subjectUnderTest.invoke()
        advanceUntilIdle()

        // then
        flowSubject.test {
            Truth.assertThat(awaitItem().firstOrNull { it.starsCount.multiplier == Multiplier.K }?.id)
                .isEqualTo(expected!!.id)
            cancelAndConsumeRemainingEvents()
        }

    }
}