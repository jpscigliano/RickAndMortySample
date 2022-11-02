package com.sample.feedframework.di

import com.sample.feedframework.local.room.di.roomDatasourceModule
import com.sample.feedframework.remote.rest.di.networkDatasourceModule
import org.koin.core.module.Module


val feedFrameworkModule: List<Module> = listOf(
    roomDatasourceModule,
    networkDatasourceModule
)





