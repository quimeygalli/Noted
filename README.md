# Noted

### [Video Demo](https://youtu.be/pU8_G0qVQbo)

## Description:
Noted is a lightweight, productivity-focused note-taking Android app written entirely in Kotlin. It provides fast local note creation, editing, deletion, and simple organization. The UI is implemented with Jetpack Compose and Material 3, while state is handled using Android’s ViewModel architecture. Persistence is fully local via Room (SQLite), allowing for the app to work offline.

---

### Project structure:

The project files follow modern Android conventions. The **`java/com/qui/noted`** folder contains all of the logic, UI layout, and Room setup. For the vectors icons, image and string resources please refer to **`app/src/main/res`**.

The main entry point for the app is **`MainActivity.kt`**, this file contains the hole UI for the menu and note screens. It also initializes navigation and ViewModels.



The **`Room`** package is where all of the objects and entities that room needs in order to store the data locally exist. In there you can find:

- The Dao (data access object). This is where all of the queries are written so they are standardized when the app is using the API)
- A NoteEntity, a class that defines the columns of the DB,
- The NoteDatabase. This file creates and configures the Room DB.
- NoteRepository. The middleman between the ViewModel and the DB. It follows the Android architecture guidelines for code readability and maintainability.
- The VMFactory. This class creates the ViewModel with parameters.

---

### Design choices:

The whole app follows the MaterialUI aesthetic, with rounded corners, pastel colors, a modern font, and a simple, minimalistic UI.

The basic idea for the menu UI was to have a similar feel to Samsung Notes, an app that i use daily, so a grid instead of a LazyList felt like the obvious choice. A FloatingActionButton for adding and saving notes was also a must have.

As for the creation of notes, because of my technical skills and knowledge about the technologies, i aimed for a simplistic and familiar set of actions (similar to SQL's CRUD). Create note, read note, edit note, delete note. Nothing else. 

---

### Challenges:

The Android developing world is a complex and everchanging one. Three year old StackOverflow questions don't work anymore because, as phones change every year, technologies have to follow them.

The documentation from the official AndroidDeveloper webpage is also unclear and/or outdated in some cases. All of this led to long afternoons of diving into a loophole of asking questions both in online forums and AI chats. This was frustrating but also a great learning experience, full of difficult challenges to overcome and an accomplishment sensation whenever i got over them.

