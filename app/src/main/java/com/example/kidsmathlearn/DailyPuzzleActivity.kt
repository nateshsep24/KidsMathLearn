package com.example.kidsmathlearn

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kidsmathlearn.R
import android.content.Intent
import androidx.appcompat.app.AlertDialog


class DailyPuzzleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dailypuzzle)

        findViewById<TextView>(R.id.textDailyPuzzle).text = "Today's Puzzle: 9 - 4"

        findViewById<Button>(R.id.buttonSubmitDailyPuzzle).setOnClickListener {
            checkPuzzleAnswer()
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
                val intent = Intent(this@DailyPuzzleActivity, MainActivity::class.java)
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

    private fun checkPuzzleAnswer() {
        val answerInput = findViewById<EditText>(R.id.editDailyPuzzleAnswer).text.toString().toIntOrNull()
        val correctAnswer = 5
        val feedbackText = findViewById<TextView>(R.id.textDailyFeedback)

        if (answerInput == correctAnswer) {
            feedbackText.text = "Correct! You earned a star! ⭐"
        } else {
            feedbackText.text = "Oops! Try again tomorrow!"
        }
        feedbackText.visibility = TextView.VISIBLE
    }
}
