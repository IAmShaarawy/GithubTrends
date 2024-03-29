package dev.shaarawy.githubtrends.data.remote.services

import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.shaarawy.githubtrends.data.remote.dtos.TrendingReposResponse
import dev.shaarawy.githubtrends.foundation.fakeDataPath
import dev.shaarawy.githubtrends.foundation.readJSONFile
import dev.shaarawy.githubtrends.foundation.readTextFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import okhttp3.internal.closeQuietly
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.HttpException
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
class SearchServiceTest {
    @get:Rule
    val hiltRule by lazy { HiltAndroidRule(this) }

    @Inject
    lateinit var subjectUnderTest: SearchService

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

    @Test
    fun `ensure correctness of request path and query parameter`() = runTest {
        // given
        val response = MockResponse().apply { setBody(readTextFile(fakeDataPath)) }
        server.enqueue(response)

        // when
        subjectUnderTest.getTendingRepos()
        val actual = withContext(Dispatchers.IO) {
            URLDecoder.decode(server.takeRequest().path, StandardCharsets.UTF_8.toString())
        }

        //then
        assertThat(actual).apply {
            isNotNull()
            isEqualTo("/search/repositories?q=language=+sort:stars")
        }
    }

    @Test
    fun `ensure that the service will return the fake data in resources directory`() = runTest {
        // given
        val response = MockResponse().apply { setBody(readTextFile(fakeDataPath)) }
        server.enqueue(response)

        // when
        val actual = subjectUnderTest.getTendingRepos()

        //then
        assertThat(actual).isEqualTo(readJSONFile<TrendingReposResponse>(fakeDataPath))
    }

    @Test
    fun `ensure that the service will throw a 404 exception properly`() = runTest {
        // given
        val response = MockResponse().apply { setResponseCode(404) }
        server.enqueue(response)

        // when
        val actual = kotlin.runCatching { subjectUnderTest.getTendingRepos() }


        //then
        assertThat(actual.exceptionOrNull()).apply {
            isNotNull()
            isInstanceOf(HttpException::class.java)
        }
    }
}