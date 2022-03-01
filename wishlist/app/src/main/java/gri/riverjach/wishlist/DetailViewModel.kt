package gri.riverjach.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import gri.riverjach.wishlist.persistance.Repository

class DetailViewModel(private val repository: Repository) : ViewModel() {

    fun saveNewItem(wishlist: Wishlist, name: String) {
        repository.saveWishlistItem(
            wishlist.copy(wishes = wishlist.wishes + name)
        )
    }

    fun getWishlist(id: Int): LiveData<Wishlist> {
        return repository.getWishlist(id)
    }
}