package com.example.naoandroidclient.di

import android.app.Application
import android.content.Context
import com.example.naoandroidclient.MainApplication
import com.example.naoandroidclient.data.repository.InMemoryAppRepository
import com.squareup.moshi.Moshi
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideApplication(@ApplicationContext context: Context): MainApplication {
        return context as MainApplication
    }

    @Provides
    fun provideLifecycle(application: Application): Lifecycle =
        AndroidLifecycle.ofApplicationForeground(application)

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    fun provideHttpClient() = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        .build()

//    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
//    @Provides
//    fun provideYourDatabase(
//        @ApplicationContext app: Context
//    ) = Room.databaseBuilder(
//        app,
//        YourDatabase::class.java,
//        "your_db_name"
//    ).build() // The reason we can construct a database for the repo
//
//    @Singleton
//    @Provides
//    fun provideYourDao(db: YourDatabase) = db.getYourDao() // The reason we can implement a Dao for the database

    @Singleton
    @Provides
    fun providesAppRepository() = InMemoryAppRepository()

}