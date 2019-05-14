package com.globant.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.usecases.GetCharacterByIdUseCase
import com.globant.domain.utils.Result
import com.globant.utils.Data
import com.globant.utils.Status
import com.globant.viewmodels.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterViewModel(val getCharacterById: GetCharacterByIdUseCase) : BaseViewModel() {

    private var mutableMainState: MutableLiveData<Data<MarvelCharacter>> = MutableLiveData()
    val mainState: LiveData<Data<MarvelCharacter>>
        get() {
            return mutableMainState
        }

    fun onSearchRemoteClicked(id: Int) = launch {
        mutableMainState.value = Data(responseType = Status.LOADING)
        when (val result = withContext(Dispatchers.IO) { getCharacterById(id, true) }) {
            is Result.Failure -> {
                mutableMainState.value = Data(responseType = Status.ERROR, error = result.exception)
            }
            is Result.Success -> {
                mutableMainState.value = Data(responseType = Status.SUCCESSFUL, data = result.data)
            }
        }
    }

    fun onSearchLocalClicked(id: Int) = launch {
        mutableMainState.value = Data(responseType = Status.LOADING)
        launch {
            when (val result = withContext(Dispatchers.IO) { getCharacterById(id, false) }) {
                is Result.Failure -> {
                    mutableMainState.value = Data(responseType = Status.ERROR, error = result.exception)
                }
                is Result.Success -> {
                    mutableMainState.value = Data(responseType = Status.SUCCESSFUL, data = result.data)
                }
            }
        }
    }
}


