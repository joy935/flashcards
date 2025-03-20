package com.example.flashcards

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import android.content.Context
import android.content.Intent

private const val TAG = "AddActivity"

class AddActivity : AppCompatActivity() {

    // define UI elements: input front card, input back card,
    // finish adding button and next card button
    private lateinit var editFront: EditText
    private lateinit var editBack: EditText
    private lateinit var doneButton: Button
    private lateinit var nextButton: Button

    // store flashcards locally
    private lateinit var sharedPreferences: SharedPreferences
    // track the number of flashcards saved
    private var cardIndex = 0

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

        // load the last flashcard saved
        loadCard()

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

    // loadCard is a function to load the flashcards
    // from the local storage to keep consistency
    private fun loadCard() {
        // retrieve the last flashcard content
        cardIndex = sharedPreferences.getInt("card_count", 0)

        // if there are saved flashcards, load the content
        if (cardIndex > 0) {
            val frontText = sharedPreferences.getString("front_$cardIndex", "")
            val backText = sharedPreferences.getString("back_$cardIndex", "")

            // display the content retried
            editFront.setText(frontText)
            editBack.setText(backText)
        }
    }

    // saveCards is a function to save the flashcards
    // created and move to the next flashcard
    private fun saveCards() {
        val frontText = editFront.text.toString()
        val backText = editBack.text.toString()

        // if the front and back of the card has been populated
        // increment the card index and save the card
        if (frontText.isNotEmpty() && backText.isNotEmpty()) {
            cardIndex++

            // save to SharedPreferences
            with(sharedPreferences.edit()) {
                putString("front_$cardIndex", frontText)
                putString("back_$cardIndex", backText)
                putInt("card_count", cardIndex)
                apply()
            }

            Log.i(TAG, "Saved card: Front: $frontText, Back: $backText")

            // clear the fields for the next card
            editFront.text.clear()
            editBack.text.clear()
        }
    }
}