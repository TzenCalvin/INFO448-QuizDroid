package edu.uw.ischool.nivlac.quizdroid

import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

interface TopicRepository {
    fun getTopics(): List<Topic>
}