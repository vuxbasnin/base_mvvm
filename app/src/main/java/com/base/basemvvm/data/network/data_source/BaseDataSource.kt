package com.base.basemvvm.data.network.data_source

import android.content.Context
import com.base.basemvvm.core.common.toJson
import com.base.basemvvm.core.utils.InternetUtil
import com.base.basemvvm.data.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber

abstract class BaseDataSource(private val mContext: Context) {
    /**
     * Map data sang resource
     */

    suspend fun<T: Any> safeApiCall(retryCount: Int = 0, apiCall: suspend () -> Response<T>): Resource <T> {
        return withContext(Dispatchers.IO) {
            try {
                if (!InternetUtil.isNetworkAvailable()){
                    return@withContext Resource.error("Không có kết nối mạng")
                }
                val response = apiCall()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) return@withContext Resource.success(body)
                }
                val errorBody = response.errorBody()?.string() ?: "Có lỗi xảy ra"
                if (response.code() == 400) {
                    Timber.d(errorBody.toJson())
                    try {
                        return@withContext Resource.error(errorBody)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                return@withContext Resource.error(response.message())
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext Resource.error(e.message ?: "Có lỗi xảy ra")
            }
        }
    }
}