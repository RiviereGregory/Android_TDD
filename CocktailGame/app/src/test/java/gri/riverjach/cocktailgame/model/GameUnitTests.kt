package gri.riverjach.cocktailgame.model

import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.anyString
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GameUnitTests {
    @Test
    fun whenNextQuestion_readNextQuestion() {
        val questions = listOf(
            Question("CORRECT", "INCORRECT"),
            Question("CORRECT", "INCORRECT"),
            Question("CORRECT", "INCORRECT")
        )
        val game = Game(questions, 0)

        val question = game.nextQuestion()

        Assert.assertNotNull(question)
    }

    @Test
    fun whenNextQuestion_EndList_returnNull() {
        val questions = listOf(
            Question("CORRECT", "INCORRECT")
        )
        val game = Game(questions, 0)

        game.nextQuestion()
        val question = game.nextQuestion()

        Assert.assertNull(question)
    }

    @Test
    fun whenAnswering_shouldDelegateToQuestion() {
        // 1
        val question: Question = mock()
        val game = Game(listOf(question), 0)

        // 2
        game.answer(question, "OPTION")

        // 3
        verify(question).answer(option = "OPTION")

    }

    @Test
    fun whenAnsweringCorrectly_shouldIncrementCurrentScore() {
        // 1
        val question = mock<Question>()
        whenever(question.answer(anyString())).thenReturn(true)

        val game = Game(listOf(question), 0)

        // 2
        game.answer(question, "OPTION")

        // 3
        Assert.assertEquals(1, game.currentScore)
    }

    @Test
    fun whenAnsweringIncorrectly_shouldNotIncrementCurrentScore() {
        val question = mock<Question>()
        whenever(question.answer(anyString())).thenReturn(false)
        val game = Game(listOf(question), 0)

        game.answer(question, "OPTION")

        Assert.assertEquals(0, game.currentScore)
    }


}