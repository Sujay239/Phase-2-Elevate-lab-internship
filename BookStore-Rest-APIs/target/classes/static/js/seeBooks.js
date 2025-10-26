document.addEventListener("DOMContentLoaded", () => {
    if (localStorage.getItem("name") === null) {
        window.location.href = 'index.html';
    }
    const searchBtn = document.getElementById("search-btn");
    // Load all books by default on page load
    fetchBooks("/api/books/getAll");

    // Apply filter on button click
    document.getElementById("filter-btn").addEventListener("click", applyFilter);
    searchBtn.addEventListener("click", searchbynameandauthor);
});

async function fetchBooks(url) {
    const grid = document.getElementById("book-grid");
    grid.innerHTML = "<p>Loading books...</p>";

    try {
        const response = await fetch(url);
        if (!response.ok) throw new Error("Failed to fetch books");
        const books = await response.json();
        grid.innerHTML = "";

        if (books.length > 0) {
            books.forEach(book => {
                const card = document.createElement("div");
                card.classList.add("book-card");
                card.innerHTML = `
                    <h3>${book.title}</h3>
                    <p><strong>Genre:</strong> ${book.genre}</p>
                    <p><strong>Price:</strong> $${book.price}</p>
                    <p><strong>Author:</strong> ${book.authorName || "Unknown"}</p>
                `;
                grid.appendChild(card);
            });
        } else {
            grid.innerHTML = "<p>No books found 📖</p>";
        }
    } catch (error) {
        console.error(error);
        grid.innerHTML = "<p style='color:red;'>⚠️ Failed to load books</p>";
    }
}

function applyFilter() {
    const genre = document.getElementById("genre-filter").value.trim();
    const minPrice = document.getElementById("min-price").value;
    const maxPrice = document.getElementById("max-price").value;

    let url = "/api/books/getAll";

    if (genre && minPrice && maxPrice) {
        url = `/api/books/filter/combined?genre=${genre}&minPrice=${minPrice}&maxPrice=${maxPrice}`;
    } else if (genre) {
        url = `/api/books/filter/genre?genre=${genre}`;
    } else if (minPrice && maxPrice) {
        url = `/api/books/filter/price?minPrice=${minPrice}&maxPrice=${maxPrice}`;
    }

    fetchBooks(url);
}

 function searchbynameandauthor() {
    const titleInput = document.getElementById("search-title");
    const authorInput = document.getElementById("search-author");
    const title = titleInput.value.trim();
    const author = authorInput.value.trim();

    let url = "/api/books/getAll";

    if (title && !author) {
        url = `/api/books/searchByTitle?title=${title}`;
    } else if (author && !title) {
        url = `/api/books/searchByAuthor?author=${author}`;
    } else if (title && author) {
        // Optional: create combination endpoint if you want both filters together
        url = `/api/books/searchByAuthor?author=${author}&title=${title}`;
    }

     fetchBooks(url);
}

