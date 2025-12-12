# Noted

#### Video Demo: `<URL HERE>`

## Description:
Noted is a lightweight, productivity-focused note-taking Android app written entirely in Kotlin. It provides fast local note creation, editing, deletion, and simple organization. The UI is implemented with Jetpack Compose and Material 3, while state is handled using Android’s ViewModel architecture. Persistence is fully local via Room (SQLite), allowing for the app to work offline.

---

### Project structure:

The project files follow modern Android conventions. The **`java/com/qui/noted`** folder contains all of the logic, UI layout, and Room setup. For the vectors icons, image and string resources please refer to **`app/src/main/res`**.

The main entry point for the app is **`MainActivity.kt`**, this file contains the whole UI for the menu and note screens. It also initializes navigation and ViewModels.

The **`Room`** package is where all of the objects and entities that room needs in order to store the data locally exist. In there you can find:

- The Dao (data access object). This is where all of the queries are written so they are standardized when the app is using the API)
- A NoteEntity, a class that defines the columns of the DB,
- The NoteDatabase. This file creates and configures the Room DB.
- NoteRepository. The middleman between the ViewModel and the DB. It follows the Android architecture guidelines for code readability and maintainability.
- The VMFactory. This class creates the ViewModel with parameters.

---

### Design choices:

The whole app follows the MaterialUI aesthetic, with rounded corners, pastel colors, a modern font, and a simple, minimalistic UI.

The basic idea for the UI was to have a similar feel to Samsung Notes, so a grid instead of a LazyList felt like the obvious choice. A FloatingActionButton for adding and saving notes was also a must have.