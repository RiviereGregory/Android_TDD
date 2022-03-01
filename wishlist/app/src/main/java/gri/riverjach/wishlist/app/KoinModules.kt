package gri.riverjach.wishlist.app

import androidx.room.Room
import gri.riverjach.wishlist.DetailViewModel
import gri.riverjach.wishlist.MainViewModel
import gri.riverjach.wishlist.persistance.Repository
import gri.riverjach.wishlist.persistance.RepositoryImpl
import gri.riverjach.wishlist.persistance.WishlistDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<Repository> { RepositoryImpl(get()) }

    single {
        Room.databaseBuilder(
            get(),
            WishlistDatabase::class.java, "wishlist-database"
        ).allowMainThreadQueries()
            .build().wishlistDao()
    }

    viewModel { MainViewModel(get()) }

    viewModel { DetailViewModel(get()) }
}
