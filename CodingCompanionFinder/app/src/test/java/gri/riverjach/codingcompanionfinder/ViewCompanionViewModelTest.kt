package gri.riverjach.codingcompanionfinder

import gri.riverjach.codingcompanionfinder.data.AnimalData.atlantaShihTzuNamedSpike
import gri.riverjach.codingcompanionfinder.data.fakerAnimal
import gri.riverjach.codingcompanionfinder.searchforcompanion.ViewCompanionViewModel
import junit.framework.Assert.assertEquals
import org.junit.Test


class ViewCompanionViewModelTest {

    @Test
    fun populateFromAnimal_sets_the_animals_name_to_the_view_model() {
        val viewCompanionViewModel = ViewCompanionViewModel()
        viewCompanionViewModel
            .populateFromAnimal(atlantaShihTzuNamedSpike)
        assert(
            viewCompanionViewModel.name
                .equals(atlantaShihTzuNamedSpike.name)
        )
    }

    @Test
    fun populateFromAnimal_sets_the_animals_description_to_the_view_model() {
        val viewCompanionViewModel = ViewCompanionViewModel()
        System.out.println(fakerAnimal.toString())
        viewCompanionViewModel.populateFromAnimal(fakerAnimal)
        assertEquals(
            fakerAnimal.description,
            viewCompanionViewModel.description
        )
    }

}
