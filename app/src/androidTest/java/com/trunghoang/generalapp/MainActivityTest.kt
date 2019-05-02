package com.trunghoang.generalapp

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.trunghoang.generalapp.data.TodoDatabase
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    lateinit var context : Context

    @Before
    fun initContext() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun givenContext_whenInitTodoDatabase_thenReturnTrue() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity {
            assertNotNull(it.initTodoDatabase(context))
        }
    }
}