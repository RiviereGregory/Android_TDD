package gri.riverjach.codingcompanionfinder.searchforcompanion

import androidx.lifecycle.ViewModel
import gri.riverjach.codingcompanionfinder.models.Animal

data class ViewCompanionViewModel(
    var name: String = "",
    var breed: String = "",
    var city: String = "",
    var email: String = "",
    var telephone: String = "",
    var age: String = "",
    var sex: String = "",
    var size: String = "",
    var title: String = "",
    var description: String = ""
) : ViewModel() {
    fun populateFromAnimal(animal: Animal) {
        name = animal.name
        breed = animal.breeds.primary
        city = animal.contact.address.city + ", " +
                animal.contact.address.state
        email = animal.contact.email
        telephone = animal.contact.phone
        age = animal.age
        sex = animal.gender
        size = animal.size
        title = "Meet " + animal.name
        description = animal.description
    }

}
