document.addEventListener("DOMContentLoaded", () => {
    const box = document.getElementById('messageBox');
    const msg = document.getElementById('messageText');
    const ok = document.getElementById('okButton');

    if (localStorage.getItem("name") === null) {
        window.location.href = 'index.html';
    }
    const params = new URLSearchParams(window.location.search);
    const bookId = params.get("id");

    if (!bookId) {
        showMessage("Invalid book ID");
        window.location.href = "seeBooks.html";
        return;
    }

    // Load existing book details
    fetch(`/api/books/${bookId}`)
        .then(res => res.json())
        .then(book => {
            document.getElementById("title").value = book.title;
            document.getElementById("isbn").value = book.isbn; // ISBN is read-only
            document.getElementById("genre").value = book.genre;
            document.getElementById("description").value = book.description;
            document.getElementById("price").value = book.price;
        })
        .catch(err => {
            console.error("Error loading book:", err);
            showMessage("Failed to load book details!");
        });

    // Handle form submission (PUT update)
    document.getElementById("edit-book-form").addEventListener("submit", async (e) => {
        e.preventDefault();

        const updatedBook = {
            title: document.getElementById("title").value.trim(),
            isbn: document.getElementById("isbn").value.trim(), // Keep ISBN unchanged
            genre: document.getElementById("genre").value.trim(),
            description: document.getElementById("description").value.trim(),
            price: parseFloat(document.getElementById("price").value),
            authorId: localStorage.getItem("userId") // Ensure authorId is included
        };

        try {
            console.log(bookId);
            console.log(updatedBook);
            const res = await fetch(`/api/books/${bookId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(updatedBook)
            });

            if (res.ok) {
                showMessage("✅ Book updated successfully!");
                // window.location.href = "landingPage.html"; // Redirect back to book list
            } else {
                showMessage("❌ Failed to update book!");
            }
        } catch (error) {
            console.error("Error updating book:", error);
            showMessage("Error updating book! check console.");
        }
    });

    // Cancel button
    document.getElementById("cancel-btn").addEventListener("click", () => {
        window.location.href = "landingPage.html";
    });

    ok.addEventListener("click", hideMessage);

    function showMessage(text) {
        msg.textContent = text;
        box.classList.remove('hidden');
    }

    function hideMessage() {
        box.classList.add('hidden');
        // optionally reset form &/or navigate
        document.getElementById("edit-book-form").reset();
        window.location.href = 'landingPage.html';
    }
});
