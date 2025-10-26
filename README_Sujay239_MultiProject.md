# 💻 Multi-Project Repository by Sujay239

This repository contains three independent backend and full-stack projects developed for learning and demonstration purposes.  
Each project showcases modern development practices, clean architecture, and real-world application logic.

---

## 📚 1. BookStore REST APIs

### 🧩 Overview
The **BookStore REST APIs** project is a backend service that allows managing books, authors, and related data using RESTful APIs.  
It follows the CRUD (Create, Read, Update, Delete) design pattern and can be integrated with any frontend or mobile app.

### ⚙️ Features
- Add, update, delete, and view books  
- Manage authors and book categories  
- RESTful API architecture  
- JSON-based responses  
- Built with **Spring Boot / Express.js** (depending on implementation)  
- Integrated with a relational database (MySQL / MongoDB)

### 📁 Project Structure
```
BookStore Rest APIs/
 ├── src/
 ├── resources/
 ├── pom.xml or package.json
 └── README.md
```

### 🚀 How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/your-repo.git
   cd "BookStore Rest APIs"
   ```
2. Install dependencies:
   ```bash
   npm install        # or mvn clean install for Spring Boot
   ```
3. Run the server:
   ```bash
   npm start          # or mvn spring-boot:run
   ```
4. Open in browser:  
   ```
   http://localhost:8080/api/books
   ```

---

## 💬 2. ChatApplication

### 🧩 Overview
The **ChatApplication** is a real-time messaging app built using **Java Sockets**.  
It allows multiple users to chat simultaneously through a client-server model.

### ⚙️ Features
- Real-time two-way communication  
- Java-based GUI using **Swing**  
- Separate **Server** and **Client** programs  
- Message broadcasting to all connected clients  
- Simple and lightweight interface  

### 📁 Project Structure
```
ChatApplication/
 ├── src/
 │   ├── Server.java
 │   ├── Client.java
 │   └── ChatGUI.java
 └── README.md
```

### 🚀 How to Run
1. Compile and run the server:
   ```bash
   javac Server.java
   java Server
   ```
2. Run multiple clients:
   ```bash
   javac Client.java
   java Client
   ```
3. Start chatting! All connected clients will see each other’s messages.

---

## 🔗 3. URL Shortner

### 🧩 Overview
The **URL Shortner** is a REST API service that converts long URLs into short, shareable links — similar to Bitly or TinyURL.  
It demonstrates backend API design, database storage, and redirection logic.

### ⚙️ Features
- Generate short URLs for any long URL  
- Redirect short links to original destination  
- RESTful API design  
- Database integration for persistence (MongoDB/MySQL)  
- Optionally tracks click counts  

### 📁 Project Structure
```
URL-Shortner/
 ├── src/
 ├── routes/
 ├── controllers/
 ├── models/
 └── README.md
```

### 🚀 How to Run
1. Navigate to the folder:
   ```bash
   cd URL-Shortner
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the server:
   ```bash
   npm start
   ```
4. Test API:
   ```
   POST /api/shorten
   {
     "longUrl": "https://www.example.com/long/link"
   }
   ```

---

## 🧠 Tech Stack Summary
| Project | Primary Language | Framework / Tools | Database |
|----------|------------------|------------------|-----------|
| BookStore REST APIs | Java / Node.js | Spring Boot / Express.js | MySQL / MongoDB |
| ChatApplication | Java | Socket Programming, Swing | — |
| URL Shortner | JavaScript (Node.js) | Express.js | MongoDB |

---

## 💡 Future Enhancements
- Add authentication (JWT / OAuth2) for API security  
- Deploy services to cloud (Render, Vercel, or AWS)  
- Implement Docker for containerization  
- Integrate frontend UI for BookStore and URL Shortner  

---

## 👨‍💻 Author
**Sujay239**  
🚀 Passionate Developer | Java • Spring Boot • React • Node.js  
📧 Contact: *[your-email@example.com]*  
🌐 GitHub: [https://github.com/Sujay239](https://github.com/Sujay239)

---

⭐ **If you like this repository, don’t forget to star it!**
