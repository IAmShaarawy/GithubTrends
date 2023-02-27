package dev.shaarawy.githubtrends.data.cache.daos

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.testIn
import com.google.common.truth.Truth.assertThat
import dev.shaarawy.githubtrends.data.cache.AppCacheDatabase
import dev.shaarawy.githubtrends.data.cache.entities.TrendRepoEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class TrendRepoDaoTest {

    private lateinit var subjectUnderTest: TrendRepoDao
    private lateinit var db: AppCacheDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppCacheDatabase::class.java
        ).build()
        subjectUnderTest = db.trendReposDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun validate_insertion_and_delete() = runTest(UnconfinedTestDispatcher()) {
        //given
        val expected = listOf(fakeTrendsRepo(1), fakeTrendsRepo(2), fakeTrendsRepo(3))

        // when
        subjectUnderTest.bulkInsert(expected)
        this.advanceUntilIdle()
        val turbineReceiver =  subjectUnderTest.getAllRepos().testIn(this)

        // then
        val actual = turbineReceiver.awaitItem()
        assertThat(actual).containsExactlyElementsIn(expected)
        turbineReceiver.cancelAndConsumeRemainingEvents()
    }

    private fun fakeTrendsRepo(id: Int) = TrendRepoEntity(
        id = id,
        name = "name $id",
        description = null,
        url = null,
        language = null,
        starsCount = null,
        owner = TrendRepoEntity.Owner(
            id = id + 5,
            name = "owner name $id",
            avatarUrl = null,
            url = null
        )
    )
}