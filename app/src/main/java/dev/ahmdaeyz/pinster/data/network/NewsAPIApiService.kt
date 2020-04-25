package dev.ahmdaeyz.pinster.data.network

import dev.ahmdaeyz.pinster.BuildConfig
import dev.ahmdaeyz.pinster.data.network.response.NewsArticles
import dev.ahmdaeyz.pinster.data.network.response.NewsSources
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/v2/"
const val API_KEY = "870777245ed840d891785e9182078a7f"

interface NewsAPIApiService {
    @GET("top-headlines")
    fun getTopHeadlinesWithSources(
            @Query("sources") sources: String
    ): Single<NewsArticles>

    @GET("top-headlines")
    fun getTopHeadlinesWithCountryAndCategory(
            @Query("country") countryCode: String,
            @Query("category") category: String
    ): Single<NewsArticles>

    @GET("sources")
    fun getSources(
            @Query("category") category: String,
            @Query("language") languageCode: String = "en",
            @Query("country") countryCode: String = "us"
    ): Single<NewsSources>
//    @GET("sources")
//    fun getAllSources(): Single<NewsSources>
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
            val logging = HttpLoggingInterceptor()
                    .apply {
                        level = if (BuildConfig.DEBUG)
                            HttpLoggingInterceptor.Level.BODY
                        else
                            HttpLoggingInterceptor.Level.NONE
                    }
            val okHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(logging)
                    .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()
            return Retrofit
                    .Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsAPIApiService::class.java)
        }
    }
}