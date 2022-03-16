package gri.riverjach.codingcompanionfinder

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import gri.riverjach.codingcompanionfinder.testhooks.IdlingEntity
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.robolectric.annotation.LooperMode

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
class FindCompanionInstrumentedTest : AutoCloseKoinTest() {
    lateinit var testScenario: ActivityScenario<MainActivity>
    private val idlingResource = SimpleIdlingResource()

    private fun loadKoinTestModules() {
        stopKoin()
        startKoin { }
        loadKoinModules(listOf(module(override = true) {
            single(named(PETFINDER_URL)) { server.url("").toString() }
        }, appModule))
    }

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
            server.setDispatcher(dispatcher)
            server.start()
        }
    }

    @Subscribe
    fun onEvent(idlingEntity: IdlingEntity) {
        idlingResource.incrementBy(idlingEntity.incrementValue)
    }

    @Before
    fun beforeTestsRun() {
        loadKoinTestModules()
        testScenario = ActivityScenario.launch(MainActivity::class.java)

        EventBus.getDefault().register(this)
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun afterTestsRun() {
        // eventbus and idling resources unregister.
        IdlingRegistry.getInstance().unregister(idlingResource)
        EventBus.getDefault().unregister(this)
        testScenario.close()
    }

    @Test
    fun pressing_the_find_bottom_menu_item_takes_the_user_to_the_find_page() {
        onView(withId(R.id.searchForCompanionFragment))
            .perform(click())
        onView(withId(R.id.searchButton))
            .check(matches(isDisplayed()))
        onView(withId(R.id.searchFieldText))
            .check(matches(isDisplayed()))
    }

    @Test
    fun searching_for_a_companion_and_tapping_on_it_takes_the_user_to_the_companion_details() {
        find_and_select_kevin_in_30318()
        onView(withText("Rome, GA")).check(matches(isDisplayed()))
        onView(withText("Domestic Short Hair")).check(matches(isDisplayed()))
        onView(withText("Young")).check(matches(isDisplayed()))
        onView(withText("Female")).check(matches(isDisplayed()))
        onView(withText("Medium")).check(matches(isDisplayed()))
        onView(withText("Meet KEVIN")).check(matches(isDisplayed()))
    }

    private fun find_and_select_kevin_in_30318() {
        onView(withId(R.id.searchForCompanionFragment))
            .perform(click())
        onView(withId(R.id.searchFieldText))
            .perform(typeText("30318"))
        onView(withId(R.id.searchButton))
            .perform(click())
        onView(withId(R.id.searchButton))
            .check(matches(isDisplayed()))
        onView(withText("KEVIN")).perform(click())
    }

    @Test
    fun verify_that_companion_details_shows_a_valid_phone_number_and_email() {
        find_and_select_kevin_in_30318()
        onView(withText("(706) 236-4537"))
            .check(matches(isDisplayed()))
        onView(withText("adoptions@gahomelesspets.com"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun searching_for_a_companion_in_30318_returns_two_results() {
        onView(withId(R.id.searchForCompanionFragment))
            .perform(click())
        onView(withId(R.id.searchFieldText))
            .perform(typeText("30318"))
        onView(withId(R.id.searchButton)).perform(click())
        onView(withId(R.id.searchButton))
            .check(matches(isDisplayed()))
        onView(withText("Joy")).check(matches(isDisplayed()))
        onView(withText("Male")).check(matches(isDisplayed()))
        onView(withText("Shih Tzu")).check(matches(isDisplayed()))
        onView(withText("KEVIN")).check(matches(isDisplayed()))
        onView(withText("Female")).check(matches(isDisplayed()))
        onView(withText("Domestic Short Hair"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun searching_for_a_companion_in_90210_returns_no_results() {
        onView(withId(R.id.searchForCompanionFragment))
            .perform(click())
        onView(withId(R.id.searchFieldText))
            .perform(typeText("90210"))
        onView(withId(R.id.searchButton)).perform(click())
        onView(withId(R.id.searchButton))
            .check(matches(isDisplayed()))
        onView(withId(R.id.noResults))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun searching_for_a_companion_in_a_call_returns_an_error_displays_no_results() {
        onView(withId(R.id.searchForCompanionFragment))
            .perform(click())
        onView(withId(R.id.searchFieldText)).perform(typeText("dddd"))
        onView(withId(R.id.searchButton)).perform(click())
        onView(withId(R.id.searchButton))
            .check(matches(isDisplayed()))
        onView(withId(R.id.noResults))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

}