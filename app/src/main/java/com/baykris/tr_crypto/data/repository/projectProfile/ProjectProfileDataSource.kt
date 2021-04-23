package com.baykris.tr_crypto.repository.projectProfile

import com.baykris.tr_crypto.data.local.database.CoinsDatabase
import javax.inject.Inject

class ProjectProfileDataSource @Inject constructor(private val db: CoinsDatabase){

    fun projectBySymbol(symbol: String) = db.coinsListDao().projectLiveDataFromSymbol(symbol)

}