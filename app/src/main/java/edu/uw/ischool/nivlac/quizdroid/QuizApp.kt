package edu.uw.ischool.nivlac.quizdroid

import android.app.Application
import android.content.Context
import android.util.Log

class QuizApp : Application() {
    companion object {
        lateinit var appContext: Context
            private set
    }

    val TAG = MainActivity::class.java.canonicalName
    val topicsList by lazy { ExampleRepository().getTopics() }
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Log.i(TAG, "The app has been loaded and is now running!")
    }
}