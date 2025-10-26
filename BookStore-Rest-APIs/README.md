# Author Dashboard Project

## 🚀 Project Overview
**Author Dashboard** is a web application that allows authors to **manage their books efficiently**. Authors can **add, edit, and delete books** with a futuristic neon-themed UI. The project uses a **Spring Boot backend**, **JavaScript frontend**, and **MySQL database**.

The dashboard emphasizes **usability and interactive design**:
- Neon-futuristic design and glowing buttons
- Animated modals for delete confirmation and result alerts
- Glassmorphic cards for each book
- Dynamic rendering of author-specific books

---

## 📚 Features
- Add new books with title, genre, description, and price
- Edit existing books
- Delete books with confirmation message box
- Neon-glow, futuristic, and responsive UI
- Interactive modals for confirmation and alerts instead of default browser dialogs
- Fetch and display books dynamically from backend API

---

## 💻 Tech Stack
- **Frontend:** HTML, CSS (neon/glassmorphic styling), JavaScript  
- **Backend:** Spring Boot (REST API)  
- **Database:** MySQL  

---

## 📝 Sample Book Data

```json
[
  {
    "id": 1,
    "title": "The Quantum Mind",
    "description": "A futuristic exploration of how quantum mechanics might explain human consciousness.",
    "price": 19.99,
    "genre": "SCIENCE_FICTION",
    "author": { "id": 8, "name": "Anirban Chakraborty" }
  },
  {
    "id": 2,
    "title": "Echoes of Eternity",
    "description": "A gripping mystery novel unraveling the secrets of an ancient artifact buried beneath Rome.",
    "price": 15.50,
    "genre": "MYSTERY",
    "author": { "id": 8, "name": "Anirban Chakraborty" }
  }
]
```
# ⚠️ Limitations

- **No authentication or role management** — any user can access dashboard features.
- **No server-side validations for book edits** (assumes valid input on frontend).
- **Static genre list** — cannot add new genres dynamically.
- **No image upload** — books do not have cover images.
- **Delete operation is permanent** — no soft-delete or undo feature.
- **Backend error handling is basic** — errors are displayed via neon modals, but no detailed logs for users.

# ⚠️ Cautions

- Use only test data; deleting a book removes it permanently from the database.
- Ensure JavaScript is enabled; frontend functionalities like modals and dynamic fetching won't work otherwise.
- Neon animations may affect performance on older devices or low-end browsers.
- Make sure MySQL is running and backend APIs are reachable before using the dashboard.

# 🔧 Setup Instructions

## 1. Backend (Spring Boot)

1. Clone the repository.
2. Open in IntelliJ/Eclipse.
3. Configure `application.properties` with your MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/yourdb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```
## 2. Database
  1. Create a database:
  ```
  CREATE DATABASE author_dashboard;
  ```
  2.Run the SQL scripts for books and authors tables if needed.

## 3. Frontend

1. Open index.html or landingPage.html in a browser.
2. Make sure js and css folders are in the same directory.
3. The dashboard fetches books via /api/books/author/{id}.


# 🎨 UI Notes

- Neon/futuristic theme inspired by sci-fi aesthetics.
- Glassmorphic book cards with glowing borders.
- Animated floating modals for confirmation and success/error messages.
- Dark background with subtle gradients for focus and readability.

# 📌 Future Improvements

- Add authentication & roles (admin vs author).
- Enable book cover uploads and preview.
- Add undo for delete with soft-delete. 
- Make genres dynamic in the backend.
- Add analytics dashboard for authors (number of books, total sales, etc.)
- Optimize neon animations for mobile devices.

# 💡 Conclusion
```
The Author Dashboard project is a functional, interactive, and visually appealing platform for managing books.
It demonstrates full-stack web development skills with attention to UI/UX design,
making it a strong portfolio project for web developers and Java enthusiasts.
```
  
