package gri.riverjach.codingcompanionfinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import gri.riverjach.codingcompanionfinder.retrofit.AuthorizationInterceptor
import gri.riverjach.codingcompanionfinder.retrofit.PetFinderService
import gri.riverjach.codingcompanionfinder.testhooks.IdlingEntity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object {
        val PETFINDER_URI = "petfinder_uri"
        val API_KEY = "your client id"
        val API_SECRET = "your client secret"
        val DEFAULT_PETFINDER_URL = "https://api.petfinder.com/v2/"
    }

    var petFinderService: PetFinderService? = null

    var accessToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (petFinderService == null) {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .addInterceptor(AuthorizationInterceptor())
                .build()

            val baseUrl = intent.getStringExtra(PETFINDER_URI) ?: "https://api.petfinder.com/v2/"

            petFinderService = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build().create(PetFinderService::class.java)

        }

    }

    // 1
    @Subscribe
    fun onEvent(idlingEntity: IdlingEntity) {
        // noop
    }

    // 2
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    // 3
    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onResume() {
        super.onResume()

        val navHostController = Navigation.findNavController(this, R.id.mainPetfinderFragment)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        NavigationUI.setupWithNavController(bottomNavigation, navHostController)
    }
}
