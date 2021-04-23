package com.baykris.tr_crypto.data.repository.projectProfile

import com.baykris.tr_crypto.api.ApiInterface
import com.baykris.tr_crypto.api.BaseRemoteDataSource
import javax.inject.Inject
import com.baykris.tr_crypto.api.Result
import com.baykris.tr_crypto.api.models.HistoricalPriceResponse

class ProjectProfileRemoteDataSource @Inject constructor(private val service: ApiInterface) : BaseRemoteDataSource(){

    //fetch historical price from backend
    suspend fun historicalPrice(symbol: String, targetCurrency: String, days: Int = 30): Result<HistoricalPriceResponse> =
        getResult { service.historicalPrice(symbol, targetCurrency, days) }

}