package gri.riverjach.cocktailgame.model

import org.junit.Assert
import org.junit.Test

class QuestionUnitTests {

    @Test
    fun whenCreatingQuestion_shouldNotHaveAnsweredOption() {
        val question = Question("CORRECT", "INCORRECT")

        Assert.assertNull(question.answeredOption)
    }

    @Test
    fun whenAnswering_shouldHaveAnsweredOption() {
        val question = Question("CORRECT", "INCORRECT")

        question.answer("INCORRECT")

        Assert.assertEquals("INCORRECT", question.answeredOption)
    }

    @Test
    fun whenAnswering_withCorrectOption_shouldReturnTrue() {
        val question = Question("CORRECT", "INCORRECT")

        val result = question.answer("CORRECT")

        Assert.assertTrue(result)
    }

    @Test
    fun whenAnswering_withIncorrectOption_shouldReturnFalse() {
        val question = Question("CORRECT", "INCORRECT")

        val result = question.answer("INCORRECT")

        Assert.assertFalse(result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenAnswering_withInvalidOption_shouldThrowException() {
        val question = Question("CORRECT", "INCORRECT")

        question.answer("INVALID")
    }

}
