package edu.uw.ischool.nivlac.quizdroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.canonicalName
    val topics = QuizApp()
    val subjects = topics.topicsList

    lateinit var lstSubjects : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        lstSubjects = findViewById(R.id.subjectList)
        val adapter = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, android.R.id.text1, subjects.map {
                it.title + " - " + it.shortDesc
            })
        lstSubjects.adapter = adapter

        lstSubjects.setOnItemClickListener { parent, view, position, id ->
            val selected = subjects[position]
            val intent = Intent(this, Overview::class.java)
            intent.putExtra("topic", selected)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}