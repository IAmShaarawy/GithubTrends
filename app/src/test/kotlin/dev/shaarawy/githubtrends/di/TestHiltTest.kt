package dev.shaarawy.githubtrends.di

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
class TestHiltTest {

    @get:Rule
    val hiltRule by lazy { HiltAndroidRule(this) }

    @Inject
    lateinit var subjectUnderTest: TestHilt

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun `ensure hilt testing is working`() {
        assertEquals("i am a result", subjectUnderTest.data)
    }
}