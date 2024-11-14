package edu.uw.ischool.nivlac.quizdroid

import android.util.Log
import org.json.JSONArray
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader

class ExampleRepository : TopicRepository {
    val TAG = ExampleRepository::class.java.canonicalName
    private var arrTopics = mutableListOf<Topic>()

    private fun readJSON() {
        try {
            val fileInputStream: FileInputStream = QuizApp.appContext.openFileInput("questions.json")
            val inputStreamReader = InputStreamReader(fileInputStream)
            val json = inputStreamReader.readText()
            inputStreamReader.close()

            val data = JSONArray(json)

            for (i in 0..<data.length()) {
                val dataTopic = data.getJSONObject(i)
                val title = dataTopic.getString("title")
                val desc = dataTopic.getString("desc")
                val questions = dataTopic.getJSONArray("questions")
                val questionList = mutableListOf<Question>()

                for (j in 0..<questions.length()) {
                    val dataQuestion = questions.getJSONObject(j)
                    val questionText = dataQuestion.getString("text")
                    val answer = dataQuestion.getString("answer")
                    val answers = dataQuestion.getJSONArray("answers")
                    val answerList = mutableListOf<String>()

                    for (k in 0..<answers.length()) {
                        answerList.add(answers[k].toString())
                    }

                    questionList.add(Question(questionText, answer.toInt(), answerList))
                }

                arrTopics.add(Topic(title, desc, questionList))
                Log.i(TAG, "JSON file found!.")
            }

        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, "Error reading JSON file.")
        }
    }

    override fun getTopics(): List<Topic> {
        readJSON()
        return arrTopics
    }
}