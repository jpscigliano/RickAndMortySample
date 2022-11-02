package com.sample.feedframework

import com.sample.feedframework.local.datasource.LocalCharacterDataSourceTest
import com.sample.feedframework.local.datasource.RoomTest
import com.sample.feedframework.local.mapper.CharacterEntityMapperTest
import com.sample.feedframework.remote.datasource.RemoteCharacterDataSourceTest
import com.sample.feedframework.remote.mapper.CharacterResponseMapperTest
import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    LocalCharacterDataSourceTest::class,
    CharacterEntityMapperTest::class,
    RoomTest::class,

    RemoteCharacterDataSourceTest::class,
    CharacterResponseMapperTest::class,
)
internal class FeedFrameworkTestSuite