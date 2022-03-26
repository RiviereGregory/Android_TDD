package gri.riverjach.codingcompanionfinder

import gri.riverjach.codingcompanionfinder.data.AnimalData.atlantaShihTzuNamedSpike
import gri.riverjach.codingcompanionfinder.searchforcompanion.ViewCompanionViewModel
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
}
