package gri.riverjach.codingcompanionfinder.data

import gri.riverjach.codingcompanionfinder.data.AddressData.atlantaAddress
import gri.riverjach.codingcompanionfinder.data.BreedsData.shihTzu
import gri.riverjach.codingcompanionfinder.data.ContactsData.atlantaCodingShelter
import gri.riverjach.codingcompanionfinder.models.Address
import gri.riverjach.codingcompanionfinder.models.Animal
import gri.riverjach.codingcompanionfinder.models.Breeds
import gri.riverjach.codingcompanionfinder.models.Contact

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
