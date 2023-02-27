package dev.shaarawy.githubtrends.presentation.main

import app.cash.turbine.test
import app.cash.turbine.testIn
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.shaarawy.githubtrends.di.DispatcherProviderModuleMock
import dev.shaarawy.githubtrends.foundation.MainDispatcherRule
import dev.shaarawy.githubtrends.foundation.fakeDataPath
import dev.shaarawy.githubtrends.foundation.readTextFile
import dev.shaarawy.githubtrends.presentation.main.item.ItemAction
import kotlinx.coroutines.*
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
class MainViewModelTest {
    @get:Rule
    val hiltRule by lazy { HiltAndroidRule(this) }

    @Inject
    lateinit var subjectUnderTest: MainViewModel

    @Inject
    lateinit var server: MockWebServer

    @Inject
    @DispatcherProviderModuleMock.TestDispatcherQualifier
    lateinit var testDispatcher: TestDispatcher

    @get:Rule
    val mainDispatcherRule by lazy { MainDispatcherRule() }


    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        server.closeQuietly()
    }

    @Test
    fun `test content and loading states`() = runTest(testDispatcher) {
        // given
        val response = MockResponse().apply { setBody(readTextFile(fakeDataPath)) }
        server.enqueue(response)

        // when
        // init
        advanceUntilIdle()

        // then
        subjectUnderTest.mainViewState.test {
            val loadingState = awaitItem()
            val contentState = awaitItem()
            assertThat(loadingState).isInstanceOf(MainState.Loading::class.java)
            assertThat(contentState).isInstanceOf(MainState.Content::class.java)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test empty state`() = runTest(testDispatcher) {
        // given
        val response = MockResponse().apply { setBody("{}") }
        server.enqueue(response)

        // when
        // init
        advanceUntilIdle()

        // then
        subjectUnderTest.mainViewState.test {
            awaitItem()
            val targetState = awaitItem()
            assertThat(targetState).isInstanceOf(MainState.Empty::class.java)
        }
    }

    @Test
    fun `test error state`() = runTest(testDispatcher) {
        // given
        val response = MockResponse().apply { setResponseCode(500) }
        server.enqueue(response)

        // when
        // init
        advanceUntilIdle()

        // then
        subjectUnderTest.mainViewState.test {
            awaitItem()
            val targetState = awaitItem()
            assertThat(targetState).isInstanceOf(MainState.Error::class.java)
        }
    }

    @Test
    fun `test retry`() = runTest(testDispatcher) {
        // given
        val errorResponse = MockResponse().apply { setResponseCode(500) }
        server.enqueue(errorResponse)
        val stateTurbineReceiver = subjectUnderTest.mainViewState.testIn(this).apply {
            awaitItem() // loading
            assertThat(awaitItem()).isInstanceOf(MainState.Error::class.java) // error
        }


        // when
        val successResponse = MockResponse().apply { setBody(readTextFile(fakeDataPath)) }
        server.enqueue(successResponse)
        subjectUnderTest.onAction(MainAction.Retry)
        advanceUntilIdle()

        // then
        stateTurbineReceiver.apply {
            val loadingState = awaitItem()
            val contentState = awaitItem()
            assertThat(loadingState).isInstanceOf(MainState.Loading::class.java)
            assertThat(contentState).isInstanceOf(MainState.Content::class.java)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test open web action`() = runTest(testDispatcher) {
        // given
        val response = MockResponse().apply { setBody(readTextFile(fakeDataPath)) }
        server.enqueue(response)
        val turbineReceiver = subjectUnderTest.mainViewState.testIn(this)
        turbineReceiver.awaitItem()
        val itemUnderTest = turbineReceiver.awaitItem() as MainState.Content
        val target = itemUnderTest.data.first()
        turbineReceiver.cancelAndConsumeRemainingEvents()
        val urlAction = ItemAction.OpenWebLink("www.test.url.com")

        // when
        val whenJob = launch(start = CoroutineStart.LAZY) {
            target.onAction(urlAction)
        }

        // then
        val thenJob = launch {
            subjectUnderTest.onItemAction.test {
                val actual = awaitItem()
                assertThat(actual).isEqualTo(urlAction)
                cancelAndConsumeRemainingEvents()
            }
        }
        whenJob.start()
        advanceUntilIdle()
        whenJob.join()
        thenJob.join()
    }

}