package gri.riverjach.cocktailgame

import android.app.Application
import android.content.Context
import gri.riverjach.cocktailgame.factory.CocktailsGameFactory
import gri.riverjach.cocktailgame.factory.CocktailsGameFactoryImpl
import gri.riverjach.cocktailgame.network.CocktailsApi
import gri.riverjach.cocktailgame.repository.CocktailsRepository
import gri.riverjach.cocktailgame.repository.CocktailsRepositoryImpl

class CocktailsApplication : Application() {
    val repository: CocktailsRepository by lazy {
        CocktailsRepositoryImpl(
            CocktailsApi.create(),
            getSharedPreferences("Cocktails", Context.MODE_PRIVATE)
        )
    }

    val gameFactory: CocktailsGameFactory by lazy {
        CocktailsGameFactoryImpl(repository)
    }
}