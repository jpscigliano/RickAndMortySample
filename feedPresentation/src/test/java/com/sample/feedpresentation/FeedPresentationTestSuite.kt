package com.sample.feedpresentation


import com.sample.feedpresentation.characterDetail.CharacterDetailViewModelTest
import com.sample.feedpresentation.characterList.CharacterListViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite


@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    CharacterDetailViewModelTest::class,
    CharacterListViewModelTest::class
)
internal class FeedPresentationTestSuite