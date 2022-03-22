package gri.riverjach.codingcompanionfinder

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import gri.riverjach.codingcompanionfinder.models.Address
import gri.riverjach.codingcompanionfinder.models.Animal
import gri.riverjach.codingcompanionfinder.models.Breeds
import gri.riverjach.codingcompanionfinder.models.Contact
import gri.riverjach.codingcompanionfinder.searchforcompanion.ViewCompanionFragment
import gri.riverjach.codingcompanionfinder.searchforcompanion.ViewCompanionFragmentArgs
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
class ViewCompanionTest {
    @Before
    fun beforeTestsRun() {
        val animal = Animal(
            22,
            Contact(
                phone = "404-867-5309",
                email = "coding.companion@razware.com",
                address = Address(
                    "",
                    "",
                    "Atlanta",
                    "GA",
                    "30303",
                    "USA"
                )
            ),
            "5",
            "small",
            arrayListOf(),
            Breeds("shih tzu", "", false, false),
            "Spike",
            "male",
            "A sweet little guy with spikey teeth!"
        )

        val bundle = ViewCompanionFragmentArgs(animal).toBundle()

        launchFragmentInContainer<ViewCompanionFragment>(
            bundle,
            R.style.AppTheme
        )
    }

    @Test
    fun check_that_all_values_display_correctly() {
        onView(withText("Spike")).check(matches(isDisplayed()))
        onView(withText("Atlanta, GA")).check(matches(isDisplayed()))
        onView(withText("shih tzu")).check(matches(isDisplayed()))
        onView(withText("5")).check(matches(isDisplayed()))
        onView(withText("male")).check(matches(isDisplayed()))
        onView(withText("small")).check(matches(isDisplayed()))
        onView(withText("A sweet little guy with spikey teeth!"))
            .check(matches(isDisplayed()))
        onView(withText("404-867-5309")).check(matches(isDisplayed()))
        onView(withText("coding.companion@razware.com"))
            .check(matches(isDisplayed()))
    }


}