package com.example.unscramble.ui

data class GameUiState(
    val CurrentScrambleWord: String = "",
    val isGuessedWordWrong: Boolean = false,

)
