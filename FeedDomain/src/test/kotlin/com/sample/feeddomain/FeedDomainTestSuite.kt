package com.sample.feeddomain

import com.sample.feeddomain.useCase.ObserveCharacterDetailUseCaseTest
import com.sample.feeddomain.useCase.ObserveCharactersListUseCaseTest
import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
  ObserveCharactersListUseCaseTest::class,
  ObserveCharacterDetailUseCaseTest::class,
)
internal class FeedDomainTestSuite