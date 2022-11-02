package com.sample.feedframework.remote.rest.di

import com.sample.corexdomain.mapper.Mapper
import com.sample.corexframework.errorParser.ErrorParser
import com.sample.corexframework.errorParser.ErrorParserImp
import com.sample.feeddata.datasource.character.RemoteCharactersDataSource
import com.sample.feeddata.datasource.episode.RemoteEpisodeDataSource
import com.sample.feeddomain.model.Character
import com.sample.feeddomain.model.Episode
import com.sample.feedframework.remote.NetworkCharactersDatasource
import com.sample.feedframework.remote.NetworkEpisodesDatasource
import com.sample.feedframework.remote.rest.api.ApiClient
import com.sample.feedframework.remote.rest.api.ApiClientImpl
import com.sample.feedframework.remote.rest.dto.CharacterResponseDto
import com.sample.feedframework.remote.rest.dto.EpisodeResponseDto
import com.sample.feedframework.remote.rest.mapper.*
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

@OptIn(ExperimentalSerializationApi::class)
val networkDatasourceModule = module {
    //Networking

    single<ApiClient> {
        ApiClientImpl(HttpClient(Android) {
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.BODY
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                })
            }

            defaultRequest {
                accept(ContentType.Application.Json)
            }
        })
    }

    single<ErrorParser> { ErrorParserImp() }

    single<RemoteCharactersDataSource> {
        NetworkCharactersDatasource(
            apiClient = get(),
            characterResponseDtoToCharacterModelMapper = get(named("CharacterResponseToModelMapper")),
            errorParser = get()
        )
    }

    single<RemoteEpisodeDataSource> {
        NetworkEpisodesDatasource(
            apiClient = get(),
            episodeResponseDtoToEpisodeModelMapper = get(named("EpisodeResponseToModelMapper")),
            errorParser = get()
        )
    }

    //Mappers
    single<Mapper<CharacterResponseDto?, Character>>(named("CharacterResponseToModelMapper")) {
        CharacterResponseDtoToCharacterMapper(
            statusMapper = StatusResponseDtoToStatusMapper(),
            genderMapper = GenderResponseDtoToGenderMapper(),
            specieMapper = SpecieResponseDtoToSpecieMapper(),
            urlMapper = EpisodeUrlToIdMapper()
        )
    }

    single<Mapper<EpisodeResponseDto, Episode>>(named("EpisodeResponseToModelMapper")) {
        EpisodeResponseDtoToEpisodeMapper()
    }
}