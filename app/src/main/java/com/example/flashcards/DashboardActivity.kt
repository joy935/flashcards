package com.example.flashcards

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    // define UI elements: practice button
    // and add button
    private lateinit var practiceButton: Button
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // initialize UI elements
        practiceButton = findViewById(R.id.practiceButton)
        addButton = findViewById(R.id.addButton)

        // set add button click listener to move to
        // the next activity and start add new flashcards
        addButton.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

    }
}