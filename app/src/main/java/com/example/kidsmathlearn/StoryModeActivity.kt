package com.example.kidsmathlearn

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kidsmathlearn.R
import android.content.Intent
import androidx.appcompat.app.AlertDialog

class StoryModeActivity : AppCompatActivity() {
    private var chapter = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storymode)

        loadChapter()

        findViewById<Button>(R.id.buttonSubmitStoryAnswer).setOnClickListener {
            checkStoryAnswer()
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
                val intent = Intent(this@StoryModeActivity, MainActivity::class.java)
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

    private fun loadChapter() {
        val storyText = when (chapter) {
            1 -> "Chapter 1: The journey begins. Solve 5 + 3 to move forward!"
            2 -> "Chapter 2: Cross the river by solving 4 x 2!"
            else -> "Congratulations! You completed the story."
        }
        findViewById<TextView>(R.id.textStory).text = storyText
    }

    private fun checkStoryAnswer() {
        val answerInput = findViewById<EditText>(R.id.editStoryAnswer).text.toString().toIntOrNull()
        val correctAnswer = when (chapter) {
            1 -> 8
            2 -> 8
            else -> 0
        }
        if (answerInput == correctAnswer) {
            chapter++
            loadChapter()
        } else {
            findViewById<TextView>(R.id.textStoryQuestion).text = "Incorrect! Try again."
        }
    }
}
