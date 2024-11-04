package edu.uw.ischool.nivlac.quizdroid

interface TopicRepository {
    fun getTopics(): List<Topic>
}