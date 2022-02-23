package gri.riverjach.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gri.riverjach.wishlist.persistance.Repository

class DetailViewModel(private val repository: Repository) : ViewModel() {

    fun saveNewItem(wishlist: Wishlist, name: String) {
    }

    fun getWishlist(id: Int): LiveData<Wishlist> {
        return MutableLiveData()
    }
}