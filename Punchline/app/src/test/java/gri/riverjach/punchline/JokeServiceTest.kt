package gri.riverjach.punchline

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val id = "6"
private const val joke =
    "How does a train eat? It goes chew, chew"

class JokeServiceTestUsingMockWebServer {

    private val testJson = """{ "id": $id, "joke": "$joke" }"""

    @get:Rule
    val mockWebServer = MockWebServer()

    private val retrofit by lazy {
        Retrofit.Builder()
            // 1
            .baseUrl(mockWebServer.url("/"))
            // 2
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            // 3
            .addConverterFactory(GsonConverterFactory.create())
            // 4
            .build()
    }

    private val jokeService by lazy {
        retrofit.create(JokeService::class.java)
    }

    @Test
    fun getRandomJokeEmitsJoke() {
        // 1
        mockWebServer.enqueue(
            // 2
            MockResponse()
                // 3
                .setBody(testJson)
                // 4
                .setResponseCode(200)
        )

        // 1
        val testObserver = jokeService.getRandomJoke().test()
        // 2
        testObserver.assertValue(Joke(id, joke))
    }


}
