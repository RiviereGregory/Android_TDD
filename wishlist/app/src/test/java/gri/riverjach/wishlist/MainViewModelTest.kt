package gri.riverjach.wishlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import gri.riverjach.wishlist.persistance.Repository
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MainViewModelTest {

    @get:Rule
    @Suppress("unused")
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockRepository: Repository = mock()

    private val viewModel = MainViewModel(mockRepository)

    @Test
    fun saveNewListCallsRepository() {
        viewModel.saveNewList("New list")

        verify(mockRepository).saveWishlist(any())
    }

    @Test
    fun saveNewListCallsRepositoryCorrectWithData() {
        val name = "New list"
        viewModel.saveNewList(name)

        verify(mockRepository).saveWishlist(Wishlist(name, listOf()))
    }

    @Test
    fun getWishlistsCallsRepository() {
        viewModel.getWishlists()

        verify(mockRepository).getWishlists()
    }

    @Test
    fun getWishListReturnsReturnsData() {
        val wishes = listOf(Wishlist("Victoria", listOf("RW Book")))
        whenever(mockRepository.getWishlists())
            .thenReturn(MutableLiveData<List<Wishlist>>().apply { postValue(wishes) })

        val mockObserver = mock<Observer<List<Wishlist>>>()
        viewModel.getWishlists().observeForever(mockObserver)

        verify(mockObserver).onChanged(wishes)
    }
}
