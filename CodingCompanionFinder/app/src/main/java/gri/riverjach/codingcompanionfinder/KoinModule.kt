package gri.riverjach.codingcompanionfinder

import gri.riverjach.codingcompanionfinder.retrofit.AuthorizationInterceptor
import gri.riverjach.codingcompanionfinder.retrofit.PetFinderService
import gri.riverjach.codingcompanionfinder.searchforcompanion.SearchForCompanionViewModel
import gri.riverjach.codingcompanionfinder.searchforcompanion.ViewCompanionViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val PETFINDER_URL = "PETFINDER_URL"

val urlsModule = module {
    single(named(PETFINDER_URL)) {
        MainActivity.DEFAULT_PETFINDER_URL
    }
}

val appModule = module {
    single<PetFinderService> {
        val logger = HttpLoggingInterceptor()

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(AuthorizationInterceptor())
            .build()

        Retrofit.Builder()
            .baseUrl(get<String>(named(PETFINDER_URL)))
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(PetFinderService::class.java)
    }

    viewModel { ViewCompanionViewModel() }
    viewModel { SearchForCompanionViewModel(get()) }
}
