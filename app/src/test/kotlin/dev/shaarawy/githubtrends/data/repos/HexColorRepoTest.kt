package dev.shaarawy.githubtrends.data.repos

import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.shaarawy.githubtrends.di.DispatcherProviderModuleMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
class HexColorRepoTest {
    @get:Rule
    val hiltRule by lazy { HiltAndroidRule(this) }

    @Inject
    lateinit var subjectUnderTest: HexColorRepo

    @Inject
    @DispatcherProviderModuleMock.TestDispatcherQualifier
    lateinit var testDispatcher: TestDispatcher

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun `text hex color repo for C++`() = runTest(testDispatcher) {
        // given
        val language = "C++"

        // when
        val actual =  subjectUnderTest.provideForLanguage(language)

        // then
        Truth.assertThat(actual).isEqualTo("#f34b7d")
    }
}