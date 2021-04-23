package com.baykris.tr_crypto.data.repository.favorites

import androidx.lifecycle.LiveData
import com.baykris.tr_crypto.data.local.database.CoinsListEntity
import javax.inject.Inject
import com.baykris.tr_crypto.api.Result
import com.baykris.tr_crypto.util.Constants

class FavoritesRepository @Inject constructor(private val favoritesDataSource: FavoritesDataSource) {

    val favoriteCoins: LiveData<List<CoinsListEntity>> = favoritesDataSource.favoriteCoins

    suspend fun favoriteSymbols(): List<String> = favoritesDataSource.favouriteSymbols()

    // update favourites in local database
    suspend fun updateFavouriteStatus(symbol: String): Result<CoinsListEntity> {
        val result = favoritesDataSource.updateFavouriteStatus(symbol)
        return result?.let {
            Result.Success(it)
        } ?: Result.Error(Constants.GENERIC_ERROR)
    }
}