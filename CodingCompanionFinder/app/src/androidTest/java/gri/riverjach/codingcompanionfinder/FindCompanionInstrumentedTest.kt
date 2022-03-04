package gri.riverjach.codingcompanionfinder

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class FindCompanionInstrumentedTest {
    lateinit var testScenario: ActivityScenario<MainActivity>

    companion object {

        private lateinit var startIntent: Intent

        // 1
        val server = MockWebServer()

        // 2
        val dispatcher: Dispatcher = object : Dispatcher() {

            @Throws(InterruptedException::class)
            override fun dispatch(
                request: RecordedRequest
            ): MockResponse {
                return CommonTestDataUtil.dispatch(request) ?: MockResponse().setResponseCode(404)
            }
        }

        @BeforeClass
        @JvmStatic
        fun setup() {
            // 3
            server.setDispatcher(dispatcher)
            server.start()

            // 4
            startIntent =
                Intent(
                    ApplicationProvider.getApplicationContext(),
                    MainActivity::class.java
                )
            startIntent.putExtra(
                MainActivity.PETFINDER_URI,
                server.url("").toString()
            )
        }
    }

    @Test
    fun pressing_the_find_bottom_menu_item_takes_the_user_to_the_find_page() {
        testScenario = ActivityScenario.launch(startIntent)
        onView(withId(R.id.searchForCompanionFragment))
            .perform(click())
        onView(withId(R.id.searchButton))
            .check(matches(isDisplayed()))
        onView(withId(R.id.searchFieldText))
            .check(matches(isDisplayed()))
        testScenario.close()
    }

}