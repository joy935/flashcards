package com.example.flashcards

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONException

class PracticeActivity : AppCompatActivity() {

    //define UI elements
    private lateinit var donePracticeButton: Button
    private lateinit var flipButton: Button
    private lateinit var nextPracticeButton: Button
    private lateinit var flashcardsDeck: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private var flashcardList: MutableList<Flashcard> = mutableListOf()
    private var currentIndex = 0
    private var displayFront = true

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice)

        // initialize UI elements
        donePracticeButton = findViewById(R.id.donePracticeButton)
        flipButton = findViewById(R.id.flipButton)
        nextPracticeButton = findViewById(R.id.nextPracticeButton)
        flashcardsDeck = findViewById(R.id.flashcardsDeck)

        // load flashcards
        sharedPreferences = getSharedPreferences("FlashcardPrefs", Context.MODE_PRIVATE)
        flashcardList = loadFlashcards()

        // show the flashcard
        if (flashcardList.isNotEmpty()) {
            updateFlashcard()
        } else {
            // if there are no flashcards, display a message
            // and disabled flip and next buttons
            flashcardsDeck.text = "No flashcards available yet"
            flipButton.isEnabled = false
            nextPracticeButton.isEnabled = false
        }

        // set done practice button listener to go back to the main menu
        donePracticeButton.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

        // set flip button to flip the flashcard
        flipButton.setOnClickListener {
            displayFront = !displayFront
            updateFlashcard()
        }

        // set next practice button to move to the next flashcard
        nextPracticeButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % flashcardList.size
            displayFront = true
            updateFlashcard()
        }
    }

    // update flashcard is a function that displays the current
    // card's content switching from front and back according
    // to the displayFront value
    private fun updateFlashcard() {
        val card = flashcardList[currentIndex]
        flashcardsDeck.text = if (displayFront) card.front else card.back
    }

    // loadFlashcards is a function that retrieves saved flashcards from SharedPreferences
    // and return them as a list
    private fun loadFlashcards(): MutableList<Flashcard> {
        // initialize an empty list to store the flashcards
        val list = mutableListOf<Flashcard>()
        // retrieve the flashcards list or initialize an empty JSON array
        val flashcardsJson = sharedPreferences.getString("flashcards", "[]") ?: "[]"

        try {
            // convert the JSON string to JSON array
            val jsonArray = JSONArray(flashcardsJson)
            // iterate through each item of the array
            for (i in 0 until jsonArray.length()) {
                val parts = jsonArray.getString(i).split("::")
                // make sure tha the split results in 2 parts
                if (parts.size == 2) {
                    // create a flashcard object and add it to the list
                    list.add(Flashcard(parts[0], parts[1]))
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        // return a list of flashcards
        return list
    }
}