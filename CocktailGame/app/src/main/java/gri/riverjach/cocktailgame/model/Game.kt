package gri.riverjach.cocktailgame.model

class Game(highest: Int = 0, questions: List<Question> = emptyList()) {
    var currentScore = 0
        private set

    var highestScore = highest
        private set

    val questionsIterator = questions.iterator()

    fun incrementScore() {
        currentScore++
        if (currentScore > highestScore) {
            highestScore = currentScore
        }
    }

    fun nextQuestion(): Question? =
        if (questionsIterator.hasNext()) questionsIterator.next() else null

    fun answer(question: Question, option: String) {
        val result = question.answer(option)
        if (result) {
            incrementScore()
        }
    }

}