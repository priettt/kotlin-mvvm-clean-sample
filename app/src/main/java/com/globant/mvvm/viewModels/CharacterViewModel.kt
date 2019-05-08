package com.globant.mvvm.viewModels

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.globant.data.FindCharacterImpl
import com.globant.domain.entities.MarvelCharacter
import com.globant.mvvm.BaseViewModel
import com.globant.mvvm.Data
import com.globant.mvvm.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharacterViewModel(private val marvelRepo: FindCharacterImpl) : BaseViewModel() {

    private var _mainState: MutableLiveData<Data<MarvelCharacter>> = MutableLiveData()
    val mainState: LiveData<Data<MarvelCharacter>>
        get() {
            return _mainState
        }

    @SuppressLint("CheckResult")
    fun onSearchClicked(id: Int) {
        _mainState.value = Data(responseType = Status.LOADING)
        marvelRepo.getCharacterById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ character ->
                if (character == null) {
                    _mainState.value = Data(responseType = Status.ERROR)
                } else {
                    _mainState.value = Data(responseType = Status.SUCCESSFUL, data = character)
                }
            }, { e ->
                _mainState.value = Data(responseType = Status.ERROR, error = e)
            })
    }
}


