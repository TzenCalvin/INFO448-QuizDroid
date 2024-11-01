package edu.uw.ischool.nivlac.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.Serializable

class Answer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_answer)

        val currQuestion = intent.extras?.getInt("currQuestion")
        val currCorrect = intent.extras?.getInt("correct")
        val questions = intent.extras?.getInt("questions")
        val yourAnswer = intent.extras?.getString("yourAnswer")
        val correctAnswer = intent.extras?.getString("correctAnswer")
        val exampleQuestion = intent.extras?.get("exampleQuestion") as Question
        val finished = currQuestion == questions
        val yourAns = findViewById<TextView>(R.id.yourAnswer)
        val corrAns = findViewById<TextView>(R.id.correctAnswer)
        val score = findViewById<TextView>(R.id.numCorrect)
        val nextButton = findViewById<Button>(R.id.nextButton)

        yourAns.text = "You chose: $yourAnswer"
        corrAns.text = "The correct answer was: $correctAnswer"
        score.text = "You have $currCorrect out of $currQuestion correct!"

        if(finished) {
            nextButton.text = "Finish"
        }

        nextButton.setOnClickListener {
            if(finished) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, Questions::class.java)
                intent.putExtra("correct", currCorrect)
                if (currQuestion != null) {
                    intent.putExtra("currQuestion", currQuestion + 1)
                }
                intent.putExtra("questions", questions)
                intent.putExtra("exampleQuestion", exampleQuestion as Serializable)
                startActivity(intent)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}