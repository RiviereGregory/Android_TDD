package gri.riverjach.cocktailgame.model

import org.junit.Assert
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.*

class GameUnitTests {
    @Test
    fun whenNextQuestion_readNextQuestion() {
        val questions = listOf(
            Question("CORRECT", "INCORRECT", "rien"),
            Question("CORRECT", "INCORRECT", "rien"),
            Question("CORRECT", "INCORRECT", "rien")
        )
        val game = Game(questions)

        val question = game.nextQuestion()

        Assert.assertNotNull(question)
    }

    @Test
    fun whenNextQuestion_EndList_returnNull() {
        val questions = listOf(
            Question("CORRECT", "INCORRECT", "rien")
        )
        val game = Game(questions)

        game.nextQuestion()
        val question = game.nextQuestion()

        Assert.assertNull(question)
    }

    @Test
    fun whenAnswering_shouldDelegateToQuestion() {
        // 1
        val question: Question = mock()
        val game = Game(listOf(question))

        // 2
        game.answer(question, "OPTION")

        // 3
        verify(question).answer(eq("OPTION"))

    }

    @Test
    fun whenAnsweringCorrectly_shouldIncrementCurrentScore() {
        val question = mock<Question>()
        whenever(question.answer(anyString())).thenReturn(true)
        val score = mock<Score>()
        val game = Game(listOf(question), score)

        game.answer(question, "OPTION")

        verify(score).increment()
    }

    @Test
    fun whenAnsweringIncorrectly_shouldNotIncrementCurrentScore() {
        val question = mock<Question>()
        whenever(question.answer(anyString())).thenReturn(false)
        val score = mock<Score>()
        val game = Game(listOf(question), score)

        game.answer(question, "OPTION")

        verify(score, never()).increment()
    }

}