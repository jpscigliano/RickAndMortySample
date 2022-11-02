package com.sample.rickandmorty

import android.app.Application
import com.sample.feeddata.di.feedDataModule
import com.sample.feeddomain.di.feedDomainModule
import com.sample.feedframework.di.feedFrameworkModule
import com.sample.feedpresentation.di.feedPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class RickAndMortyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@RickAndMortyApp)
            modules(
                *feedFrameworkModule.toTypedArray(),
                feedPresentationModule,
                feedDataModule,
                feedDomainModule
            )
        }
    }
}