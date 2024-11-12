package edu.uw.ischool.nivlac.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.Serializable

class Overview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_overview)

        val currTopic = intent.extras?.get("topic") as Topic
        val name = currTopic.title
        val description = currTopic.desc
        val questions = currTopic.questions.size
        val topic = findViewById<TextView>(R.id.topicName)
        val desc = findViewById<TextView>(R.id.description)
        val total = findViewById<TextView>(R.id.questionTotal)
        val beginButton = findViewById<Button>(R.id.startButton)

        topic.text = name
        desc.text = description
        total.text = "Number of Questions : $questions"

        beginButton.setOnClickListener {
            val intent = Intent(this, Questions::class.java)
            intent.putExtra("correct", 0)
            intent.putExtra("currQuestion", 1)
            intent.putExtra("topic", currTopic as Serializable)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

