package gri.riverjach.wishlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import gri.riverjach.wishlist.persistance.RepositoryImpl
import gri.riverjach.wishlist.persistance.WishlistDao
import gri.riverjach.wishlist.persistance.WishlistDaoImpl
import org.junit.Rule
import org.mockito.Mockito

class DetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val wishlistDao: WishlistDao =
        Mockito.spy(WishlistDaoImpl())
    private val viewModel =
        DetailViewModel(RepositoryImpl(wishlistDao))

}