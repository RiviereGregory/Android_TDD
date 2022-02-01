package gri.riverjach.cocktailgame.model

class Game {
    var currentScore = 0
        private set

    fun incrementScore() {
        currentScore++
    }
}