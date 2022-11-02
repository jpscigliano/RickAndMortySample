package com.sample.feedpresentation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@ExperimentalCoroutinesApi
class TestCoroutineDispatcherRule : TestRule {

    override fun apply(base: Statement, description: Description) = object : Statement() {
        override fun evaluate() {
            Dispatchers.setMain(UnconfinedTestDispatcher())

            base.evaluate()

            Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        }
    }
}