package com.example.flashcards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// FlashcardAdapter is a RecyclerView adapter that displays a list of flashcards
class FlashcardAdapter(private val flashcardList: List<Flashcard>) :
    RecyclerView.Adapter<FlashcardAdapter.ViewHolder>() {

        // onCreateViewHolder is a function that inflates the item_flashcard layout
        // and returns a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flashcard, parent, false)
        return ViewHolder(view)
    }

    // onBindViewHolder is a function that assigns the flashcard front and back
    // content to the corresponding TextView
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flashcard = flashcardList[position]
        holder.frontTextView.text = flashcard.front
        holder.backTextView.text = flashcard.back
    }

    // getItemCount is a function that returns the total number of flashcards in the list
    override fun getItemCount(): Int = flashcardList.size

    // ViewHolder class links each TextView with the flashcard content
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val frontTextView: TextView = itemView.findViewById(R.id.flashcard_front)
        val backTextView: TextView = itemView.findViewById(R.id.flashcard_back)
    }
}
