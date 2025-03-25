package com.example.flashcards

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONException
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    // define UI elements
    private lateinit var practiceButton: Button
    private lateinit var addButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FlashcardAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private var flashcardList: MutableList<Flashcard> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize UI elements
        practiceButton = findViewById(R.id.practiceButton)
        addButton = findViewById(R.id.addButton)
        recyclerView = findViewById(R.id.viewCards)

        // set add button click listener to move to AddActivity
        addButton.setOnClickListener {
            Intent(this, AddActivity::class.java).also {
                startActivity(it)
            }
        }

        // set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.isNestedScrollingEnabled = true  // enable scrolling
        sharedPreferences = getSharedPreferences("FlashcardPrefs", Context.MODE_PRIVATE)
        flashcardList = loadFlashcards()

        // initialize the adapter with the flashcards list
        adapter = FlashcardAdapter(flashcardList,
            // edit a specific flashcard by passing the position
            // of the card to the EditActivity using Intent
            onEditClick = {
                position ->
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("isEdit", true)
            intent.putExtra("editIndex", position)
            startActivity(intent)
        },
            // delete a specific flashcard by removing the flashcard
            // at its specific position and update the flashcard list
            onDeleteClick = {
                position ->
                flashcardList.removeAt(position)
                adapter.notifyItemRemoved(position)

                // save the updated list ot SharedPreferences
                sharedPreferences.edit {
                    val updatedList = JSONArray()
                    flashcardList.forEach { card ->
                        updatedList.put("${card.front}::${card.back}")
                    }
                    putString("flashcards", updatedList.toString())
                }
            })
        recyclerView.adapter = adapter

        // set practice button to move to the PracticeActivity
        practiceButton.setOnClickListener {
            Intent(this, PracticeActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    // loadFlashcards is a function that retrieves saved flashcards from SharedPreferences
    // and return them as a list
    private fun loadFlashcards(): MutableList<Flashcard> {
        // initialize an empty list to store the flashcards
        val flashcards = mutableListOf<Flashcard>()
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
                    flashcards.add(Flashcard(parts[0], parts[1]))
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        // return a list of flashcards
        return flashcards
    }
}
