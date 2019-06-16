package com.example.kacperopyrchal.petinder

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun apolloClient(): ApolloClient {
        val okHttp = OkHttpClient
                .Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val builder = original.newBuilder().method(original.method(),
                            original.body())
                    chain.proceed(builder.build())
                }
                .build()
        return ApolloClient.builder()
                .serverUrl("https://api.graph.cool/simple/v1/cjwyxgnjv22k80108zzzwy2gd")
                .okHttpClient(okHttp)
                .build()
    }

}