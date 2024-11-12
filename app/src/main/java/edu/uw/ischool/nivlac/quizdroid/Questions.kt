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
        var answer = ""
        val question = findViewById<TextView>(R.id.question)
        val answerGroup = findViewById<RadioGroup>(R.id.answerGroup)
        val answer1 = findViewById<RadioButton>(R.id.answer1)
        val answer2 = findViewById<RadioButton>(R.id.answer2)
        val answer3 = findViewById<RadioButton>(R.id.answer3)
        val answer4 = findViewById<RadioButton>(R.id.answer4)
        val submitButton = findViewById<Button>(R.id.submitButton)

        question.text = currQuestion.text
        answer1.text = currQuestion.answers[0]
        answer2.text = currQuestion.answers[1]
        answer3.text = currQuestion.answers[2]
        answer4.text = currQuestion.answers[3]

        when (currQuestion.answer) {
            1 -> answer = currQuestion.answers[0]
            2 -> answer = currQuestion.answers[1]
            3 -> answer = currQuestion.answers[2]
            4 -> answer = currQuestion.answers[3]
        }

        answerGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.answer1 -> {
                    currAnswer = 1
                    yourAnswer = currQuestion.answers[0]
                }
                R.id.answer2 -> {
                    currAnswer = 2
                    yourAnswer = currQuestion.answers[1]
                }
                R.id.answer3 -> {
                    currAnswer = 3
                    yourAnswer = currQuestion.answers[2]
                }
                R.id.answer4 -> {
                    currAnswer = 4
                    yourAnswer = currQuestion.answers[3]
                }
            }

            submitButton.visibility = View.VISIBLE
        }

        submitButton.setOnClickListener {
            if (currAnswer == currQuestion.answer) {
                currCorrect = currCorrect!! + 1
            }
            val intent = Intent(this, Answer::class.java)
            intent.putExtra("currQuestionNum", currQuestionNum)
            intent.putExtra("correct", currCorrect)
            intent.putExtra("yourAnswer", yourAnswer)
            intent.putExtra("answer", answer)
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