package edu.uw.ischool.nivlac.quizdroid

import java.io.Serializable

data class Question(
    val questionText: String,
    val answer1: String,
    val answer2: String,
    val answer3: String,
    val answer4: String,
    val correctAnswer: Int
) : Serializable
