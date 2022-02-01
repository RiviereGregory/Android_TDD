package gri.riverjach.cocktailgame.model

class Game(questions: List<Question> = emptyList(), highest: Int = 0) {
    private val score = Score(highest)

    val currentScore: Int
        get() = score.current

    val highestScore: Int
        get() = score.highest

    private var questionIndex = -1

    fun incrementScore() {
        score.increment()
    }

    val questionsIterator = questions.iterator()

    fun nextQuestion(): Question? =
        if (questionsIterator.hasNext()) questionsIterator.next() else null

    fun answer(question: Question, option: String) {
        val result = question.answer(option)
        if (result) {
            score.increment()
        }
    }

}