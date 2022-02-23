package gri.riverjach.wishlist.app

import gri.riverjach.wishlist.DetailViewModel
import gri.riverjach.wishlist.MainViewModel
import gri.riverjach.wishlist.persistance.Repository
import gri.riverjach.wishlist.persistance.RepositoryImpl
import gri.riverjach.wishlist.persistance.WishlistDao
import gri.riverjach.wishlist.persistance.WishlistDaoImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<Repository> { RepositoryImpl(get()) }

    single<WishlistDao> { WishlistDaoImpl() }

    viewModel { MainViewModel(get()) }

    viewModel { DetailViewModel(get()) }
}
