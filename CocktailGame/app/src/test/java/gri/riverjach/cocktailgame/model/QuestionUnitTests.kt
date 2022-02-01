package gri.riverjach.cocktailgame.model

import org.junit.Assert
import org.junit.Test

class QuestionUnitTests {

    @Test
    fun whenCreatingQuestion_shouldNotHaveAnsweredOption() {
        val question = Question("CORRECT", "INCORRECT")

        Assert.assertNull(question.answeredOption)
    }
}
