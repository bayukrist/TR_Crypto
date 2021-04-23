package com.baykris.tr_crypto.data.repository.coinsList

import com.baykris.tr_crypto.api.ApiInterface
import com.baykris.tr_crypto.api.BaseRemoteDataSource
import javax.inject.Inject
import com.baykris.tr_crypto.api.Result
import com.baykris.tr_crypto.api.models.Coin

class CoinsListRemoteDataSource @Inject constructor(private val service: ApiInterface) :
    BaseRemoteDataSource() {

    suspend fun coinsList(targetCur: String): Result<List<Coin>> =
        getResult {
            service.coinsList(targetCur)
        }
}