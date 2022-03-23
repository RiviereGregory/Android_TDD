package gri.riverjach.codingcompanionfinder

import gri.riverjach.codingcompanionfinder.models.Address
import gri.riverjach.codingcompanionfinder.models.Animal
import gri.riverjach.codingcompanionfinder.models.Breeds
import gri.riverjach.codingcompanionfinder.models.Contact
import gri.riverjach.codingcompanionfinder.searchforcompanion.ViewCompanionViewModel
import org.junit.Test


class ViewCompanionViewModelTest {

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

    @Test
    fun populateFromAnimal_sets_the_animals_name_to_the_view_model() {
        val viewCompanionViewModel = ViewCompanionViewModel()
        viewCompanionViewModel.populateFromAnimal(animal)

        assert(viewCompanionViewModel.name.equals("Spike"))
    }
}
