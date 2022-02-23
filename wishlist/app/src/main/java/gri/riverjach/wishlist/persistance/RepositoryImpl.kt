package gri.riverjach.wishlist.persistance

import androidx.lifecycle.LiveData
import gri.riverjach.wishlist.Wishlist

class RepositoryImpl(private val wishlistDao: WishlistDao) : Repository {

    override fun saveWishlist(wishlist: Wishlist) {
        wishlistDao.save(wishlist)
    }

    override fun getWishlists(): LiveData<List<Wishlist>> {
        return wishlistDao.getAll()
    }

    override fun getWishlist(id: Int): LiveData<Wishlist> {
        return wishlistDao.findById(id)
    }

    override fun saveWishlistItem(wishlist: Wishlist) {
        wishlistDao.save(wishlist)
    }
}
