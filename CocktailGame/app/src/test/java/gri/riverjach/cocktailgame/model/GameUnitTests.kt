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

    @Test
    fun whenIncrementingScore_aboveHighScore_shouldAlsoIncrementHighScore() {
        val game = Game()

        game.incrementScore()

        Assert.assertEquals(1, game.highestScore)
    }

    @Test
    fun whenIncrementingScore_belowHighScore_shouldNotIncrementHighScore() {
        val game = Game(10)

        game.incrementScore()

        Assert.assertEquals(10, game.highestScore)
    }


    @Test
    fun whenNextQuestion_readNextQuestion() {
        val questions = listOf(
            Question("CORRECT", "INCORRECT"),
            Question("CORRECT", "INCORRECT"),
            Question("CORRECT", "INCORRECT")
        )
        val game = Game(questions = questions)

        val question = game.nextQuestion()

        Assert.assertNotNull(question)
    }

    @Test
    fun whenNextQuestion_EndList_returnNull() {
        val questions = listOf(
            Question("CORRECT", "INCORRECT")
        )
        val game = Game(questions = questions)

        game.nextQuestion()
        val question = game.nextQuestion()

        Assert.assertNull(question)
    }
}