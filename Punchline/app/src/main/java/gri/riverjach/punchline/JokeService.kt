package gri.riverjach.punchline

import io.reactivex.Single
import retrofit2.http.GET

interface JokeService {

    @GET("https://raywenderlich.com")
    fun getRandomJoke(): Single<Joke>

}