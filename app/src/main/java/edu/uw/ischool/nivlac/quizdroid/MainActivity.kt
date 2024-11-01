package edu.uw.ischool.nivlac.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.name

    val subjects = listOf("Math", "Physics", "Marvel Super Heroes", "Computer Science", "History",
        "Video Games")
    val desc = "This is a basic description of the subject that you are looking at: <insert desc>"
    val numQuestions = 10
    val exampleQuestion = Question("This is the question!", "This is answer 1!",
        "This is answer 2!", "This is answer 3!", "This is answer 4!",
        1)

    lateinit var lstSubjects : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        lstSubjects = findViewById(R.id.subjectList)
        val adapter = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, android.R.id.text1, subjects)
        lstSubjects.adapter = adapter

        lstSubjects.setOnItemClickListener { parent, view, position, id ->
            val selected = subjects[position]
            val intent = Intent(this, Overview::class.java)
            intent.putExtra("topic", selected)
            intent.putExtra("description", desc)
            intent.putExtra("questions", numQuestions)
            intent.putExtra("exampleQuestion", exampleQuestion as Serializable)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

data class Question(
    val questionText: String,
    val answer1: String,
    val answer2: String,
    val answer3: String,
    val answer4: String,
    val correctAnswer: Int
) : Serializable