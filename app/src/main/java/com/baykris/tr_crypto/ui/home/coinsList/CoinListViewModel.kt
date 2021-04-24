package com.baykris.tr_crypto.ui.home.coinsList

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.baykris.tr_crypto.api.models.Coin
import com.baykris.tr_crypto.api.succeeded
import com.baykris.tr_crypto.core.common.BaseViewModel
import com.baykris.tr_crypto.data.repository.coinsList.CoinsListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.baykris.tr_crypto.api.Result
import com.baykris.tr_crypto.data.local.database.CoinsListEntity
import timber.log.Timber

class CoinListViewModel @ViewModelInject constructor(private val repository: CoinsListRepository) :
        BaseViewModel() {

    val coinsListData = repository.allCoinsLD

    //LiveData to show add/remove status as toast message
    private val _favouriteStock = MutableLiveData<CoinsListEntity?>()
    val favouriteStock: LiveData<CoinsListEntity?> = _favouriteStock


    fun isListEmpty(): Boolean {
        return coinsListData.value?.isEmpty() ?: true
    }

    fun loadCoinsFromApi(targetCur: String = "usd") {
        if (repository.loadData()) {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                repository.coinsList(targetCur)
                _isLoading.postValue(false)
            }
        }
    }

    fun updateFavouriteStatus(symbol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.updateFavouriteStatus(symbol)
            when (result) {
                is Result.Success -> _favouriteStock.postValue(result.data)
                is Result.Error -> _toastError.postValue(result.message)
            }
        }
    }

}