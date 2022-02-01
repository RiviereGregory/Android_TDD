package gri.riverjach.cocktailgame.model

import org.junit.Assert
import org.junit.Test

class GameUnitTests {
    // 1
    @Test
    fun whenIncrementingScore_shouldIncrementCurrentScore() {
        // 2
        val game = Game()

        // 3
        game.incrementScore()

        // 4
        Assert.assertEquals(
            "Current score should have been 1",
            1, game.currentScore
        )

    }
}