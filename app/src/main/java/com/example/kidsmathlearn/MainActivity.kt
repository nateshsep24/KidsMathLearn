package com.example.kidsmathlearn

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.example.kidsmathlearn.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.buttonCounting).setOnClickListener {
            startMiniGame("Counting")
        }
        findViewById<Button>(R.id.buttonAddition).setOnClickListener {
            startMiniGame("Addition")
        }
        findViewById<Button>(R.id.buttonMultiplication).setOnClickListener {
            startMiniGame("Multiplication")
        }
        findViewById<Button>(R.id.buttonShapes).setOnClickListener {
            startMiniGame("Shapes")
        }
        findViewById<Button>(R.id.buttonFractions).setOnClickListener {
            startMiniGame("Fractions")
        }
        findViewById<Button>(R.id.buttonStoryMode).setOnClickListener {
            startActivity(Intent(this, StoryModeActivity::class.java))
        }
        findViewById<Button>(R.id.buttonDailyPuzzle).setOnClickListener {
            startActivity(Intent(this, DailyPuzzleActivity::class.java))
        }
    }

    private fun startMiniGame(gameType: String) {
        val intent = Intent(this, MiniGameActivity::class.java)
        intent.putExtra("gameType", gameType)
        startActivity(intent)
    }
}
