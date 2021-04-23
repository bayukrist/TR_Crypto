package com.baykris.tr_crypto.data.repository.projectProfile

import com.baykris.tr_crypto.repository.projectProfile.ProjectProfileDataSource
import javax.inject.Inject

class ProjectProfileRepository @Inject constructor(
        private val localDataSource: ProjectProfileDataSource,
        private val remoteDataSource: ProjectProfileRemoteDataSource
) {

    fun projectBySymbol(symbol: String) = localDataSource.projectBySymbol(symbol)

    suspend fun historicalPrice(symbol: String, targetCur: String = "usd") =
        remoteDataSource.historicalPrice(symbol, targetCur)
}