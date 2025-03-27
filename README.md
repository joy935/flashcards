# Flashcards App
# Overview

This is a simple flashcards mobile application as a first project using Android Studio. 
It explores Kotlin's syntax and object-oriented programming principles using Android development. 

## Purpose

This project serves as a learning activity to explore Kotlin and Android Studio and build a functional mobile application. 
This flashcard mobile application allows users to create, manage and review flashcards to help memorize new words or concepts. 
This mobile application provides a foundation for adding more advanced features in the future, such as organizing flashcards by categories and adding more animations to enhance the user interface. 

## Features

* Add, edit and delete flashcards
* Store flashcards locally on the device
* Review flashcards by flipping through them

## Structure

* Flashcard data class: Represents a flashcard with a front and back content
* Activities:
    - MainActivity: Loads and displays flashcards, allows removing a specific flashcard, and links to the Add, Edit and Practice screens
    - AddActivity: Creates a new flashcard with a front and back content. Also includes a link to return to the main screen
    - EditActivity: Edits an existing flashcard and updates the list
    - PracticeActivity: Reviews the flashcards by flipping them card to view the back content. Loop through the list of flashcards. Also includes a link to return to the main screen
* Layouts: Corresponding XML layout files for the main, add, edit and practice screens
* Adapter:  Displays the content of flashcards with the front and back content and the Edit and Delete buttons
* Flip animation: Uses two sequential animations to create a shrinking and growing effect
* Drawable styling: Applies round corners and a background colorto keep a polished a consistent user interface look

[Software Demo Video](https://youtu.be/9a-PMR_9bic)

# Development Environment

## Tools

* Programming Languages: Kotlin and XML
* IDE: Android Studio
* Version Control: Git and GitHub
* Operating System: macOS

## Libraries

* Kotlin Standard Library: Provides essential functions to work with collections, collections and data structures and more
* Android SDK: Tools to build Android apps
* ContextCompat: Access colors and other resources

# Useful Websites

* [Android App Development Tutorial for Beginners](https://www.youtube.com/watch?v=FjrKMcnKahY)
* [Intent and Starting Activities](https://www.youtube.com/watch?v=1xj9G2FvLeE&list=PLQkwcJG4YTCTq1raTb5iMuxnEB06J1VHX)
* [Toasts and Context](https://www.youtube.com/watch?v=ayjINChcG3g&list=PLQkwcJG4YTCTq1raTb5iMuxnEB06J1VHX)
* [Save Simple Data with Shared Preferences](https://developer.android.com/training/data-storage/shared-preferences)
* [Recycler View](https://developer.android.com/develop/ui/views/layout/recyclerview)
* [3D Flipping Animation](https://www.geeksforgeeks.org/simulate-a-3d-flip-by-using-androids-scaleanimation/)
  
# Future Work

* Organize flashcards by categories
* Add random and counter
* Add more animation in the Practice screen
