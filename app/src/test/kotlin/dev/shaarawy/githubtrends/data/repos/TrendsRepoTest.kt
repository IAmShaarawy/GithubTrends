package dev.shaarawy.githubtrends.data.repos

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.shaarawy.githubtrends.data.remote.dtos.TrendingReposResponse
import dev.shaarawy.githubtrends.fakeDataPath
import dev.shaarawy.githubtrends.readJSONFile
import dev.shaarawy.githubtrends.readTextFile
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.internal.closeQuietly
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
class TrendsRepoTest {
    @get:Rule
    val hiltRule by lazy { HiltAndroidRule(this) }

    @Inject
    lateinit var subjectUnderTest: TrendsRepo

    @Inject
    lateinit var server: MockWebServer

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        server.closeQuietly()
    }

    fun `ensure trending repos count`(): Unit = runTest {
        // given
        val expectedCount = readJSONFile<TrendingReposResponse>(fakeDataPath).items!!.size
        val response = MockResponse().apply { setBody(readTextFile(fakeDataPath)) }
        server.enqueue(response)

        // when
        val flowSubject = subjectUnderTest.trendsRepoItemsFlow()

        // then
        flowSubject.test {
            assertThat(awaitItem()).hasSize(expectedCount)
        }
    }

    fun `ensure trending repos ids`(): Unit = runTest {
        // given
        val expectedIds = readJSONFile<TrendingReposResponse>(fakeDataPath).items!!.map { it.id!! }
        val response = MockResponse().apply { setBody(readTextFile(fakeDataPath)) }
        server.enqueue(response)

        // when
        val flowSubject = subjectUnderTest.trendsRepoItemsFlow()

        // then
        flowSubject.test {
            assertThat(awaitItem().map { it.id }).containsExactlyElementsIn(expectedIds)
        }
    }

    fun `ensure trending repos owners ids`(): Unit = runTest {
        // given
        val expectedIds =
            readJSONFile<TrendingReposResponse>(fakeDataPath).items!!.map { it.owner!!.id!! }
        val response = MockResponse().apply { setBody(readTextFile(fakeDataPath)) }
        server.enqueue(response)

        // when
        val flowSubject = subjectUnderTest.trendsRepoItemsFlow()

        // then
        flowSubject.test {
            assertThat(awaitItem().map { it.owner!!.id }).containsExactlyElementsIn(expectedIds)
        }
    }
}