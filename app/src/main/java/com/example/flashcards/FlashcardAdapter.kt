package com.example.flashcards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button

// FlashcardAdapter is a RecyclerView adapter that displays a list of flashcards
class FlashcardAdapter(
    private val flashcardList: List<Flashcard>,
    private val onEditClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit) :
    RecyclerView.Adapter<FlashcardAdapter.ViewHolder>() {

        // onCreateViewHolder is a function that inflates the item_flashcard layout
        // and returns a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flashcard, parent, false)
        return ViewHolder(view)
    }

    // getItemCount is a function that returns the total number of flashcards in the list
    override fun getItemCount(): Int = flashcardList.size

    // onBindViewHolder is a function that assigns the flashcard front and back
    // content to the corresponding TextView
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flashcard = flashcardList[position]
        holder.frontTextView.text = flashcard.front
        holder.backTextView.text = flashcard.back

        // edit button click
        holder.editButton.setOnClickListener {
            onEditClick(position)
        }

        // delete button click
        holder.deleteButton.setOnClickListener {
            onDeleteClick(position)
        }
    }

    // ViewHolder class links each TextView with the flashcard content
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val frontTextView: TextView = itemView.findViewById(R.id.flashcard_front)
        val backTextView: TextView = itemView.findViewById(R.id.flashcard_back)
        val editButton: Button = itemView.findViewById(R.id.editButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }
}
