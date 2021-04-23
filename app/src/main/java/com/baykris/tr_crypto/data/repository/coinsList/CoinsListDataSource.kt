package com.baykris.tr_crypto.data.repository.coinsList

import androidx.lifecycle.LiveData
import com.baykris.tr_crypto.data.local.database.CoinsDatabase
import com.baykris.tr_crypto.data.local.database.CoinsListDao
import com.baykris.tr_crypto.data.local.database.CoinsListEntity
import javax.inject.Inject

class CoinsListDataSource @Inject constructor(private val db: CoinsDatabase) {
    val allCoinsLD: LiveData<List<CoinsListEntity>> = db.coinsListDao().coinsList()

    suspend fun insertCoinsIntoDB(coinsToInsert: List<CoinsListEntity>) {
        if (coinsToInsert.isNotEmpty()) {
            db.coinsListDao().insert(coinsToInsert)
        }
    }

    suspend fun favouriteSymbols(): List<String> = db.coinsListDao().favouriteSymbols()

    suspend fun updateFavouriteStatus(symbol: String): CoinsListEntity? {
        val project = db.coinsListDao().projectFromSymbol(symbol)
        project?.let {
            val coinsListEntity = CoinsListEntity(
                it.symbol,
                it.id,
                it.name,
                it.price,
                it.changePercent,
                it.image,
                it.isFavourite.not()
            )

            if (db.coinsListDao().updateCoinsListEntity(coinsListEntity) > 0) {
                return coinsListEntity
            }
        }
        return null
    }

}