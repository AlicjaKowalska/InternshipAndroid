package com.example.mathgame

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    lateinit var textScore : TextView
    lateinit var textLife : TextView
    lateinit var textTime : TextView

    lateinit var textQuestion : TextView
    lateinit var editTextAnswer : EditText

    lateinit var buttonOk : Button
    lateinit var buttonNext : Button

    var correctAnswer = 0
    var userScore = 0
    var userLife = 3

    lateinit var timer : CountDownTimer
    private val startTimerInMillis : Long = 60000
    var timeLeftInMillis : Long  = startTimerInMillis

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val gameType = intent.getStringExtra("gameType")

        if ( gameType.equals("addition")) {
            supportActionBar!!.title = "Addition"
        } else if (gameType.equals("substraction")) {
            supportActionBar!!.title = "Substraction"
        } else {
            supportActionBar!!.title = "Multiplication"
        }

        textScore = findViewById(R.id.textScore)
        textLife = findViewById(R.id.textLife)
        textTime = findViewById(R.id.textTime)
        textQuestion = findViewById(R.id.textQuestion)
        editTextAnswer = findViewById(R.id.editTextAnswer)
        buttonOk = findViewById(R.id.buttonOk)
        buttonNext = findViewById(R.id.buttonNext)

        gameContinue(gameType)

        buttonOk.setOnClickListener {

            val input = editTextAnswer.text.toString()

            if(input == "")
            {
                Toast.makeText(applicationContext, "Please write an answer or click the next button",
                Toast.LENGTH_LONG).show()
            }
            else {
                pauseTimer()

                val userAnswer = input.toInt()
                if (userAnswer == correctAnswer) {
                    userScore = userScore + 10
                    textQuestion.text = "Correct!"
                    textScore.text = userScore.toString()
                }
                else {
                    userLife--
                    textQuestion.text = "Wrong!"
                    textLife.text = userLife.toString()
                }
                buttonOk.setEnabled(false)
            }
        }

        buttonNext.setOnClickListener {
            buttonOk.setEnabled(true)
            pauseTimer()
            resetTimer()
            gameContinue(gameType)
            editTextAnswer.setText("")

            if (userLife == 0) {
                Toast.makeText(applicationContext, "Game Over", Toast.LENGTH_LONG).show()
                val intent = Intent(this@GameActivity, ResultActivity::class.java)
                intent.putExtra("score",userScore)
                startActivity(intent)
                finish()
            } else {
                gameContinue(gameType)
            }
        }

    }

    fun gameContinue(gameType: String?) {
        val number1 = Random.nextInt(0, 100)
        val number2 = Random.nextInt(0, 100)

        if ( gameType.equals("addition")) {
            textQuestion.text = "$number1 + $number2"
            correctAnswer = number1 + number2
        }
        else if ( gameType.equals("substraction")) {
            textQuestion.text = "$number1 - $number2"
            correctAnswer = number1 - number2
        } else {
            textQuestion.text = "$number1 * $number2"
            correctAnswer = number1 * number2
        }

        startTimer()
    }

    fun startTimer() {
        timer = object : CountDownTimer(timeLeftInMillis, 1000){
            override fun onTick(millisUntilFinish: Long) {
                timeLeftInMillis = millisUntilFinish
                updateText()
            }

            override fun onFinish() {
                pauseTimer()
                resetTimer()
                updateText()

                userLife--
                textLife.text = userLife.toString()
                textQuestion.text = "Time is up!"
            }

        }.start()
    }

    fun updateText() {
        val remainingTime : Int = (timeLeftInMillis/1000).toInt()
        textTime.text = String.format(Locale.getDefault(),"%02d", remainingTime)
    }

    fun pauseTimer() {
        timer.cancel()
    }

    fun resetTimer() {
        timeLeftInMillis = startTimerInMillis
        updateText()
    }
}