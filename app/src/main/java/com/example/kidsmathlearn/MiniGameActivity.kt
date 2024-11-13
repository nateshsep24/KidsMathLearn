package com.example.kidsmathlearn

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import android.widget.Toast
import com.bumptech.glide.Glide
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.widget.ImageView

class MiniGameActivity : AppCompatActivity() {
    private var score = 0
    private var level = 1
    private var correctStreak = 0
    private lateinit var gameType: String
    private var correctAnswer: Any = 0 // Use Any to allow String for shapes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minigame)

        gameType = intent.getStringExtra("gameType") ?: "Counting"
        loadGame(gameType)

        findViewById<Button>(R.id.buttonSubmit).setOnClickListener {
            handleSubmit()
        }

        findViewById<Button>(R.id.buttonBackToHome).setOnClickListener {
            showBackToHomeConfirmationDialog()
        }
    }

    private fun showBackToHomeConfirmationDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Confirmation")
            setMessage("Are you sure you want to go back to the home screen?")
            setPositiveButton("Yes") { _, _ ->
                val intent = Intent(this@MiniGameActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }

    private fun handleSubmit() {
        val answerEditText = findViewById<EditText>(R.id.editAnswer)
        val answerInput = answerEditText.text.toString().trim()

        // Input validation
        if (answerInput.isBlank()) {
            answerEditText.error = "Please enter an answer."
            return
        }

        if (gameType == "Shapes") {
            // Accept only alphabets for Shapes game type
            if (!answerInput.matches("^[a-zA-Z]+$".toRegex())) {
                answerEditText.error = "Invalid input. Please enter alphabets only for shapes."
                return
            }
        } else {
            // Accept only numeric input for other game types
            if (!answerInput.matches("^[0-9]+$".toRegex())) {
                answerEditText.error = "Invalid input. Please enter a number."
                return
            }
        }

        // If input is valid, proceed to check the answer
        checkAnswer()
    }

    private fun checkAnswer() {
        val answerEditText = findViewById<EditText>(R.id.editAnswer)
        val feedbackTextView = findViewById<TextView>(R.id.textFeedback)
        val checkMarkView = findViewById<ImageView>(R.id.checkMarkView)
        val answerInput = answerEditText.text.toString().trim()

        // Determine if the answer is correct based on game type
        val isCorrect = if (gameType == "Shapes") {
            // Compare answer for shapes (case-insensitive string comparison)
            answerInput.equals(correctAnswer.toString(), ignoreCase = true)
        } else {
            // Compare answer as integer for other types
            val answer = answerInput.toIntOrNull()
            answer != null && answer == correctAnswer
        }

        if (isCorrect) {
            // Correct answer logic
            correctStreak++
            score += 10
            answerEditText.error = null // Clear any previous error
            feedbackTextView.text = "" // Clear any previous feedback

            // Show check mark with animation
            checkMarkView.visibility = View.VISIBLE
            checkMarkView.alpha = 0f
            checkMarkView.animate().alpha(1f).setDuration(500).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    checkMarkView.animate().alpha(0f).setDuration(500).setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            checkMarkView.visibility = View.GONE
                        }
                    })
                }
            })

            if (correctStreak % 5 == 0) increaseLevel()

            // Clear input and load the next question
            answerEditText.text.clear()
            loadGame(gameType)
        } else {
            // Incorrect answer logic
            correctStreak = 0
            answerEditText.error = "Incorrect! Try again."
            //feedbackTextView.text = "Incorrect! Try again." // Display feedback
            //feedbackTextView.visibility = View.VISIBLE // Ensure feedback text is visible
        }

        updateUI()
    }

    private fun loadGame(gameType: String) {
        when (gameType) {
            "Counting" -> generateCountingQuestion()
            "Addition" -> generateAdditionQuestion()
            "Multiplication" -> generateMultiplicationQuestion()
            "Shapes" -> generateShapeQuestion()
            "Fractions" -> generateFractionQuestion()
        }
    }

    private fun generateCountingQuestion() {
        val number = Random.nextInt(1, 10 * level)
        findViewById<TextView>(R.id.textQuestion).text = "Count the items: $number"
        correctAnswer = number
    }

    private fun generateAdditionQuestion() {
        val num1 = Random.nextInt(1, 10 * level)
        val num2 = Random.nextInt(1, 10 * level)
        findViewById<TextView>(R.id.textQuestion).text = "$num1 + $num2"
        correctAnswer = num1 + num2
    }

    private fun generateMultiplicationQuestion() {
        val num1 = Random.nextInt(1, 5 * level)
        val num2 = Random.nextInt(1, 5 * level)
        findViewById<TextView>(R.id.textQuestion).text = "$num1 × $num2"
        correctAnswer = num1 * num2
    }

    private fun generateShapeQuestion() {
        val shapes = listOf("circle", "square", "triangle")
        val selectedShape = shapes.random()
        correctAnswer = selectedShape // Set correctAnswer as a string

        val shapeImageView = findViewById<ImageView>(R.id.imageShape)
        val shapeQuestionText = findViewById<TextView>(R.id.textQuestion)

        shapeQuestionText.text = "Identify this shape:"

        val shapeResId = when (selectedShape) {
            "circle" -> R.drawable.circle
            "square" -> R.drawable.square
            "triangle" -> R.drawable.triangle
            else -> R.drawable.circle
        }

        shapeImageView.setImageResource(shapeResId)
        shapeImageView.visibility = View.VISIBLE
    }

    private fun generateFractionQuestion() {
        findViewById<TextView>(R.id.textQuestion).text = "Divide into parts"
        // Add fraction question logic here
    }

    private fun increaseLevel() {
        level++
        findViewById<TextView>(R.id.textLevel).text = "Level: $level"
    }

    private fun updateUI() {
        findViewById<TextView>(R.id.textScore).text = "Score: $score"
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
