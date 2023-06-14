package com.example.unscramble.ui

data class GameUiState(
    val CurrentScrambleWord: String = "",
    val isGuessedWordWrong: Boolean = false,
    val currentWordCount: Int = 1,
    val isGameOver: Boolean = false,
    val score: Int = 0
)
