package edu.uw.ischool.nivlac.quizdroid

import java.io.Serializable

data class Question(
    val text: String,
    val answer: Int,
    val answers: List<String>
) : Serializable
