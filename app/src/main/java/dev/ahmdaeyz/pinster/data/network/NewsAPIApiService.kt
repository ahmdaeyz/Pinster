package dev.ahmdaeyz.pinster.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dev.ahmdaeyz.pinster.data.network.response.NewsArticles
import dev.ahmdaeyz.pinster.data.network.response.NewsSources
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/v2/"
const val API_KEY = "870777245ed840d891785e9182078a7f"

interface NewsAPIApiService {
    @GET("top-headlines")
    fun getTopHeadlinesWithSourcesAsync(
        @Query("sources") sources: String
    ): Deferred<NewsArticles>

    @GET("top-headlines")
    fun getTopHeadlinesWithCountryAndCategoryAsync(
        @Query("country") countryCode: String,
        @Query("category") category: String
    ): Deferred<NewsArticles>

    @GET("sources")

    fun getSourcesAsync(
        @Query("category") category: String,
        @Query("language") languageCode: String = "en",
        @Query("country") countryCode: String = "us"
    ): Deferred<NewsSources>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): NewsAPIApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("apiKey", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()
            return Retrofit
                .Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsAPIApiService::class.java)
        }
    }
}