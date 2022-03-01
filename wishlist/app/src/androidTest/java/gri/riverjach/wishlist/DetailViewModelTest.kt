package gri.riverjach.wishlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import gri.riverjach.wishlist.persistance.RepositoryImpl
import gri.riverjach.wishlist.persistance.WishlistDao
import gri.riverjach.wishlist.persistance.WishlistDaoImpl
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

class DetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val wishlistDao: WishlistDao =
        Mockito.spy(WishlistDaoImpl())
    private val viewModel =
        DetailViewModel(RepositoryImpl(wishlistDao))

    @Test
    fun saveNewItemCallsDatabase() {
        // 1
        viewModel.saveNewItem(
            Wishlist(
                "Victoria",
                listOf("RW Android Apprentice Book", "Android phone"), 1
            ),
            "Smart watch"
        )
        // 2
        verify(wishlistDao).save(any())
    }

}