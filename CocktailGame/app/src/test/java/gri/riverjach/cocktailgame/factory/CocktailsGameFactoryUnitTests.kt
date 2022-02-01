package gri.riverjach.cocktailgame.factory

import gri.riverjach.cocktailgame.repository.CocktailsRepository
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class CocktailsGameFactoryUnitTests {

    private lateinit var repository: CocktailsRepository
    private lateinit var factory: CocktailsGameFactory

    @Before
    fun setup() {
        repository = mock()
        factory = CocktailsGameFactoryImpl(repository)
    }

    @Test
    fun buildGame_shouldGetCocktailsFromRepo() {
        factory.buildGame(mock())

        verify(repository).getAlcoholic(any())
    }
}
