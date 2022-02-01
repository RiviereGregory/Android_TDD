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

}
