package com.example.oskartestapp.repo

import androidx.test.filters.SmallTest
import com.example.oskartestapp.data.source.MyDB
import com.example.oskartestapp.domain.model.AuthItem
import com.example.oskartestapp.domain.repo.AuthRepo
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
@SmallTest
class AuthRepoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: MyDB

    @Inject
    @Named("test_repo")
    lateinit var noteRepo: AuthRepo

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun getAndAddAuthItem() = runBlocking {
        val item = AuthItem( id = "1", flow = "test")
        noteRepo.addAuth(item)
        assertThat(noteRepo.getAuth("1")).isEqualTo(item)
    }



    @Test
    fun updateAuthItem() = runBlocking {
        val authItem = AuthItem( id = "1", flow = "test")
        noteRepo.addAuth(authItem)
        val authItem2 = AuthItem( id = "1", flow = "test2")
        noteRepo.addAuth(authItem2)

        assertThat(noteRepo.getAuth("1")).isEqualTo(authItem2)
    }

    //TODO: complete this tests
}