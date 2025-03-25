package com.example.flashcards

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import android.content.Context
import android.content.Intent
import org.json.JSONArray

class AddActivity : AppCompatActivity() {

    // define UI elements: input front card, input back card,
    // finish adding button and next card button
    private lateinit var editFront: EditText
    private lateinit var editBack: EditText
    private lateinit var doneButton: Button
    private lateinit var nextButton: Button

    // store flashcards locally
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        // initialize UI elements
        editFront = findViewById(R.id.editFront)
        editBack = findViewById(R.id.editBack)
        doneButton = findViewById(R.id.doneButton)
        nextButton = findViewById(R.id.nextButton)

        // initialize SharedPreferences
        sharedPreferences = getSharedPreferences("FlashcardPrefs", Context.MODE_PRIVATE)

        // set done button click listener to finish adding flashcards
        doneButton.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

        // set next button click listener to move to the next flashcard
        nextButton.setOnClickListener {
            saveCards()
        }
    }

    // saveCards is a function to save the flashcards
    // created and move to the next flashcard
    private fun saveCards() {
        val frontText = editFront.text.toString()
        val backText = editBack.text.toString()

        if (frontText.isNotEmpty() && backText.isNotEmpty()) {
            // retrieve the flashcards list or initialize an empty JSON array
            val flashcardsJson = sharedPreferences
                .getString("flashcards", "[]") ?: "[]"
            // convert the JSON string into a JSON array
            val jsonArray = JSONArray(flashcardsJson)
            // add the new flashcard to the array
            jsonArray.put("$frontText::$backText")

            // save to SharedPreferences
            with(sharedPreferences.edit()) {
                putString("flashcards", jsonArray.toString())
                apply()
            }

            // clear the fields for the next card
            editFront.text.clear()
            editBack.text.clear()
        }
    }
}