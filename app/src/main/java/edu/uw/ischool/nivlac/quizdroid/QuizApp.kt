package edu.uw.ischool.nivlac.quizdroid

import android.app.Application
import android.util.Log
import java.io.Serializable

class QuizApp : Application() {
    val TAG = MainActivity::class.java.canonicalName
    val topicsList = ExampleRepository().getTopics()
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "The app has been loaded and is now running!")
    }
}