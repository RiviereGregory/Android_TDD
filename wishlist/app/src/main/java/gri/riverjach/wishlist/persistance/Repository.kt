package gri.riverjach.wishlist.persistance

import androidx.lifecycle.LiveData
import gri.riverjach.wishlist.Wishlist

interface Repository {
    fun saveWishlist(wishlist: Wishlist)
    fun getWishlists(): LiveData<List<Wishlist>>
    fun getWishlist(id: Int): LiveData<Wishlist>
    fun saveWishlistItem(wishlist: Wishlist)
}