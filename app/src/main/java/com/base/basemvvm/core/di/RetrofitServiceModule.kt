package com.base.basemvvm.core.di

import android.content.Context
import com.base.basemvvm.BuildConfig
import com.base.basemvvm.core.common.Constants
import com.base.basemvvm.core.common.HeaderRetrofitEnum
import com.base.basemvvm.core.utils.Utility
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import java.util.Collections
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitServiceModule {
    private fun getHttpClient(context: Context, headerRetrofitEnum: HeaderRetrofitEnum = HeaderRetrofitEnum.NONE): OkHttpClient {
        val deviceId = Utility.getDeviceId(context)
        return OkHttpClient.Builder().also { client ->
            client.retryOnConnectionFailure(true)
            client.addInterceptor{
                val newRequest = it.request().newBuilder().apply {
                    //check enum header in here to set header
                }.build()
                it.proceed(newRequest)
            }
            if (BuildConfig.DEBUG) {
                val loggingContent = HttpLoggingInterceptor()
                loggingContent.setLevel(HttpLoggingInterceptor.Level.BODY)
                val collector = ChuckerCollector(context)
                val logging = ChuckerInterceptor.Builder(context).alwaysReadResponseBody(true).collector(collector).build()
                client.interceptors().add(logging)
                client.interceptors().add(loggingContent)
            }
            client.connectTimeout(30, TimeUnit.SECONDS)
            client.readTimeout(30, TimeUnit.SECONDS)
            client.protocols(Collections.singletonList(Protocol.HTTP_1_1))
        }.build()
    }

    @Provides
    @Singleton
    @Named(Constants.Inject.API)
    fun provideDemoRetrofit(gson: Gson, context: Context): Retrofit {

    }
}