package edu.uw.ischool.nivlac.quizdroid

import java.io.Serializable

data class Topic(
    val title: String,
    val desc: String,
    val questions: List<Question>
) : Serializable
