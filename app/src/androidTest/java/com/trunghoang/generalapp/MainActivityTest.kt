package com.trunghoang.generalapp

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var scenarioRule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    @Test
    fun testEvent() {
        val scenario = scenarioRule.scenario
    }
}