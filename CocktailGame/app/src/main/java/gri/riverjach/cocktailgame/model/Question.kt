package gri.riverjach.cocktailgame.model

class Question(
    val correctOption: String,
    val incorrectOption: String
) {
    var answeredOption: String? = "MY ANSWER"
        private set
}
