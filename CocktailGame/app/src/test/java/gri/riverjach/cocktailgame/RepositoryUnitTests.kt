package gri.riverjach.cocktailgame

import android.content.SharedPreferences
import gri.riverjach.cocktailgame.network.CocktailsApi
import gri.riverjach.cocktailgame.repository.CocktailsRepositoryImpl
import org.junit.Test
import org.mockito.kotlin.*

class RepositoryUnitTests {

    @Test
    fun saveScore_shouldSaveToSharedPreferences() {
        val api: CocktailsApi = mock()
        // 1
        val sharedPreferencesEditor: SharedPreferences.Editor =
            mock()
        val sharedPreferences: SharedPreferences = mock()
        whenever(sharedPreferences.edit())
            .thenReturn(sharedPreferencesEditor)
        val repository = CocktailsRepositoryImpl(
            api,
            sharedPreferences
        )

        // 2
        val score = 100
        repository.saveHighScore(score)

        // 3
        inOrder(sharedPreferencesEditor) {
            // 4
            verify(sharedPreferencesEditor).putInt(any(), eq(score))
            verify(sharedPreferencesEditor).apply()
        }
    }

    @Test
    fun getScore_shouldGetFromSharedPreferences() {
        val api: CocktailsApi = mock()
        val sharedPreferences: SharedPreferences = mock()

        val repository = CocktailsRepositoryImpl(
            api,
            sharedPreferences
        )

        repository.getHighScore()

        verify(sharedPreferences).getInt(any(), any())
    }

}
