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
import android.view.animation.Animation
import android.view.animation.AnimationUtils

class PracticeActivity : AppCompatActivity(), Animation.AnimationListener {

    //define UI elements
    private lateinit var donePracticeButton: Button
    private lateinit var flipButton: Button
    private lateinit var nextPracticeButton: Button
    private lateinit var flashcardsDeck: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private var flashcardList: MutableList<Flashcard> = mutableListOf()
    private var currentIndex = 0
    private var displayFront = true
    private lateinit var animation1: Animation
    private lateinit var animation2: Animation
    private var isFrontOfCardShowing = true


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice)

        // initialize UI elements
        donePracticeButton = findViewById(R.id.donePracticeButton)
        flipButton = findViewById(R.id.flipButton)
        nextPracticeButton = findViewById(R.id.nextPracticeButton)
        flashcardsDeck = findViewById(R.id.flashcardsDeck)
        // apply animation from to_middle
        animation1 = AnimationUtils.loadAnimation(this, R.anim.to_middle)
        animation1.setAnimationListener(this)
        // apply animation from to_middle
        animation2 = AnimationUtils.loadAnimation(this, R.anim.from_middle)
        animation2.setAnimationListener(this)

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
            // displayFront = !displayFront
            // updateFlashcard()
            flipButton.isEnabled = false
            flashcardsDeck.startAnimation(animation1)
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

    // handle the animation end and change content of flashcard
    override fun onAnimationEnd(animation: Animation?) {
        if (animation === animation1) {
            // after shrinking, change the content of the flashcard
            if (isFrontOfCardShowing) {
                flashcardsDeck.text = flashcardList[currentIndex].back // show the back of the card
            } else {
                flashcardsDeck.text = flashcardList[currentIndex].front // show the front of the card
            }
            // start the growing animation
            flashcardsDeck.startAnimation(animation2)
        } else if (animation === animation2) {
            // after growing, re-enable the button and toggle the front/back of the card
            isFrontOfCardShowing = !isFrontOfCardShowing
            flipButton.isEnabled = true
        }
    }
    override fun onAnimationRepeat(animation: Animation?) {
        // no need to implement this for this case
    }

    override fun onAnimationStart(animation: Animation?) {
        // no need to implement this for this case
    }
}