package com.sample.feeddata


import com.sample.feeddata.repository.CharactersRepositoryTest
import com.sample.feeddata.repository.EpisodesRepositoryTest
import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    CharactersRepositoryTest::class,
    EpisodesRepositoryTest::class
)
internal class FeedDomainTestSuite