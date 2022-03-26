package gri.riverjach.codingcompanionfinder

import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import gri.riverjach.codingcompanionfinder.retrofit.PetFinderService
import gri.riverjach.codingcompanionfinder.searchforcompanion.SearchForCompanionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class SearchForCompanionViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate =
        newSingleThreadContext("Mocked UI thread")

    val server = MockWebServer()

    lateinit var petFinderService: PetFinderService
    val dispatcher: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(
            request: RecordedRequest
        ): MockResponse {
            return CommonTestDataUtil.nonInterceptedDispatch(request)
                ?: MockResponse().setResponseCode(404)
        }
    }

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        server.setDispatcher(dispatcher)
        server.start()
        val logger = HttpLoggingInterceptor()
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .build()
        petFinderService = Retrofit.Builder()
            .baseUrl(server.url("").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(PetFinderService::class.java)
    }

    @Test
    fun call_to_searchForCompanions_gets_results() {
        val searchForCompanionViewModel =
            SearchForCompanionViewModel(petFinderService)
        searchForCompanionViewModel.companionLocation.value = "30318"
        val countDownLatch = CountDownLatch(1)
        searchForCompanionViewModel.searchForCompanions()
        searchForCompanionViewModel.animals.observeForever {
            countDownLatch.countDown()
        }
        countDownLatch.await(2, TimeUnit.SECONDS)
        Assert.assertEquals(
            2,
            searchForCompanionViewModel.animals.value!!.size
        )
    }

    fun callSearchForCompanionWithALocationAndWaitForVisibilityResult(location: String): SearchForCompanionViewModel {
        val searchForCompanionViewModel =
            SearchForCompanionViewModel(petFinderService)
        searchForCompanionViewModel.companionLocation.value = location
        val countDownLatch = CountDownLatch(1)
        searchForCompanionViewModel.searchForCompanions()
        searchForCompanionViewModel.noResultsViewVisiblity
            .observeForever {
                countDownLatch.countDown()
            }

        countDownLatch.await(2, TimeUnit.SECONDS)
        return searchForCompanionViewModel
    }

    @Test
    fun call_to_searchForCompanions_with_results_sets_the_visibility_of_no_results_to_INVISIBLE() {
        val searchForCompanionViewModel =
            callSearchForCompanionWithALocationAndWaitForVisibilityResult("30318")
        Assert.assertEquals(
            INVISIBLE,
            searchForCompanionViewModel.noResultsViewVisiblity.value
        )
    }

    @Test
    fun call_to_searchForCompanions_with_no_results_sets_the_visibility_of_no_results_to_VISIBLE() {
        val searchForCompanionViewModel =
            callSearchForCompanionWithALocationAndWaitForVisibilityResult("90210")
        Assert.assertEquals(
            VISIBLE,
            searchForCompanionViewModel.noResultsViewVisiblity.value
        )
    }
}
