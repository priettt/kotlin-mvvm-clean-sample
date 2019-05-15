package com.globant.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.globant.di.useCasesModule
import com.globant.di.viewModelsModule
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.usecases.GetCharacterByIdUseCase
import com.globant.domain.utils.Result
import com.globant.myapplication.util.captureValues
import com.globant.myapplication.util.getValueForTest
import com.globant.repositoriesModule
import com.globant.utils.Data
import com.globant.utils.Status
import com.globant.viewmodels.CharacterViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.Mockito.`when` as whenever

class CharacterViewModelTest : AutoCloseKoinTest() {

    companion object {
        const val VALID_ID = 1017100
        @ObsoleteCoroutinesApi
        private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    lateinit var subject: CharacterViewModel
    @Mock lateinit var marvelCharacterResult: Result.Success<MarvelCharacter>
    @Mock lateinit var marvelCharacter: MarvelCharacter

    val getCharacterByIdUseCase: GetCharacterByIdUseCase by inject()

    @Before
    fun setup() {
        Dispatchers.Main = mainThreadSurrogate
        startKoin {
            modules(listOf(useCasesModule, viewModelsModule, repositoriesModule))
        }

        declareMock<GetCharacterByIdUseCase>()
        MockitoAnnotations.initMocks(this)
    }

    @After
    fun after() {
        stopKoin()
        // Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun onSeatchRemoteTest() {
        subject = CharacterViewModel(getCharacterByIdUseCase)
        val liveDataUnderTest = subject.mainState.testObserver()

        runBlocking {
            whenever(getCharacterByIdUseCase.invoke(VALID_ID,true)).thenReturn(marvelCharacterResult)
            subject.onSearchRemoteClicked(VALID_ID)
        }

        Truth.assert_()
                .that(liveDataUnderTest.observedValues)
                .isEqualTo(listOf(Data(Status.LOADING), Data(Status.SUCCESSFUL, data = marvelCharacter)))
    }

    @Test
    fun `init method sets liveData value to empty list`() {
        val liveDataUnderTest = subject.mainState.testObserver()

        Truth.assert_()
                .that(liveDataUnderTest.observedValues)
                .isEqualTo(emptyList<String>())
    }

    @Test
    fun `on remote search set correct screen states`() {
        declareMock<GetCharacterByIdUseCase> {
            whenever(getCharacterByIdUseCase.invoke(VALID_ID, true)).thenReturn(marvelCharacterResult)
        }
        val liveDataUnderTest = subject.mainState.testObserver()
        runBlocking {
            subject.onSearchRemoteClicked(VALID_ID)
            Truth.assert_()
                    .that(liveDataUnderTest.observedValues)
                    .isEqualTo(listOf(Data(Status.LOADING), Data(Status.SUCCESSFUL, data = marvelCharacter)))
        }

    }

    @Test
    fun `Display after search remote`() {

        subject.mainState.captureValues {
            runBlocking {
                subject.onSearchRemoteClicked(VALID_ID)
            }
            subject.mainState.getValueForTest()?.responseType?.equals(Status.LOADING)?.let { assert(it) }
        }
    }

    class TestObserver<T> : Observer<T> {
        val observedValues = mutableListOf<T?>()
        override fun onChanged(value: T?) {
            observedValues.add(value)
        }
    }

    fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
        observeForever(it)
    }

}
