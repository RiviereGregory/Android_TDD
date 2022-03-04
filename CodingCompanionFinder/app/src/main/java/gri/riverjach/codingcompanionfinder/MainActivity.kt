package gri.riverjach.codingcompanionfinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import gri.riverjach.codingcompanionfinder.models.Token
import gri.riverjach.codingcompanionfinder.retrofit.AuthorizationInterceptor
import gri.riverjach.codingcompanionfinder.retrofit.PetFinderService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object {
        val PETFINDER_URI = "petfinder_uri"
        val PETFINDER_KEY = "petfinder_key"
    }


    var petFinderService: PetFinderService? = null

    var token: Token = Token()

  var apiKey = "replace with your API key"

  val apiSecret = "replace with your API secret"

    var accessToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intent.getStringExtra(PETFINDER_KEY)?.let {
            apiKey = it
        }

        if (petFinderService == null) {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .addInterceptor(AuthorizationInterceptor(this))
                .build()

            val baseUrl = intent.getStringExtra(PETFINDER_URI) ?: "http://api.petfinder.com/v2/"

            petFinderService = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build().create(PetFinderService::class.java)

        }

    }

    override fun onResume() {
        super.onResume()

        val navHostController = Navigation.findNavController(this, R.id.mainPetfinderFragment)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        NavigationUI.setupWithNavController(bottomNavigation, navHostController)
    }
}
