package com.example.flashcards

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray

class EditActivity : AppCompatActivity() {

    // define UI elements: input front card, input back card and
    // done button to save the modification
    private lateinit var editFrontEdit: EditText
    private lateinit var editBackEdit: EditText
    private lateinit var doneButtonEdit: Button

    // store flashcards locally
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        // initialize UI elements
        editFrontEdit = findViewById(R.id.editFrontEdit)
        editBackEdit = findViewById(R.id.editBackEdit)
        doneButtonEdit = findViewById(R.id.doneButtonEdit)

        // initialize SharedPreferences
        sharedPreferences = getSharedPreferences("FlashcardPrefs", Context.MODE_PRIVATE)

        // load flashcard to edit
        val isEdit = intent.getBooleanExtra("isEdit", false)
        val editIndex = intent.getIntExtra("editIndex", -1)

        // load the content of the flashcard
        if (isEdit && editIndex != -1) {
            val flashcardsJson = sharedPreferences.getString("flashcards", "[]") ?: "[]"
            val jsonArray = JSONArray(flashcardsJson)
            val card = jsonArray.getString(editIndex)
            val parts = card.split("::")
            // check there are two parts before assigning
            // the front and back contents
            if (parts.size == 2) {
                editFrontEdit.setText(parts[0])
                editBackEdit.setText(parts[1])
            }
        }

        // set done button click listener to finish editing the flashcard
        // and going back to the main screen
        doneButtonEdit.setOnClickListener {
            saveCard()
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    // saveCard is a function that saves the modifications on the
    // flashcard in the local storage
    private fun saveCard() {
        val frontText = editFrontEdit.text.toString()
        val backText = editBackEdit.text.toString()
        // checks that teh front and back inputs aren't empty
        if (frontText.isNotEmpty() && backText.isNotEmpty()) {
            // retrieve the flashcards list or initialize an empty JSON array
            val flashcardsJson = sharedPreferences
                .getString("flashcards", "[]") ?: "[]"
            // convert the JSON string into a JSON array
            val jsonArray = JSONArray(flashcardsJson)

            val isEdit = intent.getBooleanExtra("isEdit", false)
            val editIndex = intent.getIntExtra("editIndex", -1)

            // checks that the content has been modified
            if (isEdit && editIndex != -1) {
                // replace existing flashcard
                jsonArray.put(editIndex, "$frontText::$backText")
            } else {
                // add a new flashcard
                jsonArray.put("$frontText::$backText")
            }

            // save the updated list
            with(sharedPreferences.edit()) {
                putString("flashcards", jsonArray.toString())
                apply()
            }
        }
    }
}