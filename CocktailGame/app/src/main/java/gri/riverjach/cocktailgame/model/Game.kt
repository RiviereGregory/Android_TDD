package gri.riverjach.cocktailgame.model

class Game(highest: Int = 0) {
    var currentScore = 0
        private set

    var highestScore = highest
        private set

    fun incrementScore() {
        currentScore++
        highestScore++
    }
}