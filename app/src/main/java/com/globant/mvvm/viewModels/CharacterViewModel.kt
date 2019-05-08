package com.globant.mvvm.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.usecases.GetCharacterByIdUseCase
import com.globant.domain.utils.Result
import com.globant.mvvm.BaseViewModel
import com.globant.mvvm.Data
import com.globant.mvvm.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class CharacterViewModel : BaseViewModel(SupervisorJob(), Dispatchers.Default) {

    val getCharacterById = GetCharacterByIdUseCase()

    private var _mainState: MutableLiveData<Data<MarvelCharacter>> = MutableLiveData()
    val mainState: LiveData<Data<MarvelCharacter>>
        get() {
            return _mainState
        }

    fun onSearchClicked(id: Int) {
        _mainState.value = Data(responseType = Status.LOADING)
        viewModelCoroutineScope.launch {
            when (val result = getCharacterById(id)) {
                is Result.Failure -> {
                    _mainState.postValue(Data(responseType = Status.ERROR, error = result.exception))
                }
                is Result.Success -> {
                    _mainState.postValue(Data(responseType = Status.SUCCESSFUL, data = result.data))
                }
            }
        }
    }
}


