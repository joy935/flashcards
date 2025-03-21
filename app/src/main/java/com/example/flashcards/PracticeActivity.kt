package com.example.flashcards

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PracticeActivity : AppCompatActivity() {

    //define UI elements
    private lateinit var donePracticeButton: Button
    private lateinit var flipButton: Button
    private lateinit var nextPracticeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice)

        // initialize UI elements
        donePracticeButton = findViewById(R.id.donePracticeButton)
        flipButton = findViewById(R.id.flipButton)
        nextPracticeButton = findViewById(R.id.nextPracticeButton   )

        // set done practice button listener to go back to the main menu
        donePracticeButton.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

        // set flip button to flip the flashcard
        flipButton.setOnClickListener {
            // flip
        }

        // set next practice button to move to the next flashcard
        nextPracticeButton.setOnClickListener {
            // get the next flashcard
        }
    }
}