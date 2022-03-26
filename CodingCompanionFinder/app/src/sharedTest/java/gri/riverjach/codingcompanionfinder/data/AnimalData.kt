package gri.riverjach.codingcompanionfinder.data

import com.github.javafaker.Faker
import gri.riverjach.codingcompanionfinder.data.AddressData.atlantaAddress
import gri.riverjach.codingcompanionfinder.data.BreedsData.shihTzu
import gri.riverjach.codingcompanionfinder.data.ContactsData.atlantaCodingShelter
import gri.riverjach.codingcompanionfinder.models.Address
import gri.riverjach.codingcompanionfinder.models.Animal
import gri.riverjach.codingcompanionfinder.models.Breeds
import gri.riverjach.codingcompanionfinder.models.Contact

val faker = Faker()

val fakerAddress = Address(
    faker.address().streetAddress(),
    faker.address().secondaryAddress(),
    faker.address().city(),
    faker.address().state(),
    faker.address().zipCode(),
    faker.address().country()
)

val fakerBreed = Breeds(
    faker.cat().breed(),
    faker.dog().breed(), faker.bool().bool(), faker.bool().bool()
)

val fakerShelter = Contact(
    faker.phoneNumber().cellPhone(),
    faker.internet().emailAddress(),
    fakerAddress
)

val fakerAnimal = Animal(
    faker.number().digits(3).toInt(),
    fakerShelter,
    faker.number().digit(),
    faker.commerce().productName(),
    arrayListOf(),
    fakerBreed,
    faker.name().name(),
    faker.dog().gender(),
    faker.chuckNorris().fact()
)

object AnimalData {
    val atlantaShihTzuNamedSpike = Animal(
        22,
        atlantaCodingShelter,
        "5",
        "small",
        arrayListOf(),
        shihTzu,
        "Spike",
        "male",
        "A sweet little guy with spikey teeth!"
    )
}

object AddressData {
    val atlantaAddress = Address(
        "",
        "",
        "Atlanta",
        "GA",
        "30303",
        "USA"
    )
}

object BreedsData {
    val shihTzu = Breeds("shih tzu", "", false, false)
}

object ContactsData {
    val atlantaCodingShelter = Contact(
        phone = "404-867-5309",
        email = "coding.companion@razware.com",
        address = atlantaAddress
    )
}
