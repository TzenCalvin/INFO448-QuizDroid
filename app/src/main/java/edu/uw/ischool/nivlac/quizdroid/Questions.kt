package edu.uw.ischool.nivlac.quizdroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import java.io.Serializable

class Questions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_questions)

        var currAnswer = 0
        var currCorrect = intent.extras?.getInt("correct")
        val currQuestionNum = intent.extras?.getInt("currQuestion")
        val currTopic = intent.extras?.get("topic") as Topic
        val currQuestion = currTopic.questions[currQuestionNum!! - 1]
        var yourAnswer = ""
        var correctAnswer = ""
        val question = findViewById<TextView>(R.id.question)
        val answerGroup = findViewById<RadioGroup>(R.id.answerGroup)
        val answer1 = findViewById<RadioButton>(R.id.answer1)
        val answer2 = findViewById<RadioButton>(R.id.answer2)
        val answer3 = findViewById<RadioButton>(R.id.answer3)
        val answer4 = findViewById<RadioButton>(R.id.answer4)
        val submitButton = findViewById<Button>(R.id.submitButton)

        question.text = currQuestion.questionText
        answer1.text = currQuestion.answer1
        answer2.text = currQuestion.answer2
        answer3.text = currQuestion.answer3
        answer4.text = currQuestion.answer4

        when (currQuestion.correctAnswer) {
            1 -> correctAnswer = currQuestion.answer1
            2 -> correctAnswer = currQuestion.answer2
            3 -> correctAnswer = currQuestion.answer3
            4 -> correctAnswer = currQuestion.answer4
        }

        answerGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.answer1 -> {
                    currAnswer = 1
                    yourAnswer = currQuestion.answer1
                }
                R.id.answer2 -> {
                    currAnswer = 2
                    yourAnswer = currQuestion.answer2
                }
                R.id.answer3 -> {
                    currAnswer = 3
                    yourAnswer = currQuestion.answer3
                }
                R.id.answer4 -> {
                    currAnswer = 4
                    yourAnswer = currQuestion.answer4
                }
            }

            submitButton.visibility = View.VISIBLE
        }

        submitButton.setOnClickListener {
            if (currAnswer == currQuestion.correctAnswer) {
                currCorrect = currCorrect!! + 1
            }
            val intent = Intent(this, Answer::class.java)
            intent.putExtra("currQuestionNum", currQuestionNum)
            intent.putExtra("correct", currCorrect)
            intent.putExtra("yourAnswer", yourAnswer)
            intent.putExtra("correctAnswer", correctAnswer)
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