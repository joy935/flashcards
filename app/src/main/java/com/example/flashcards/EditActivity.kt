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

    // define UI elements
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
            if (parts.size == 2) {
                editFrontEdit.setText(parts[0])
                editBackEdit.setText(parts[1])
            }
        }

        // set done button click listener to finish editing the flashcard
        doneButtonEdit.setOnClickListener {
            saveCard()
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun saveCard() {
        val frontText = editFrontEdit.text.toString()
        val backText = editBackEdit.text.toString()

        if (frontText.isNotEmpty() && backText.isNotEmpty()) {
            // retrieve the flashcards list or initialize an empty JSON array
            val flashcardsJson = sharedPreferences
                .getString("flashcards", "[]") ?: "[]"
            // convert the JSON string into a JSON array
            val jsonArray = JSONArray(flashcardsJson)

            val isEdit = intent.getBooleanExtra("isEdit", false)
            val editIndex = intent.getIntExtra("editIndex", -1)

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