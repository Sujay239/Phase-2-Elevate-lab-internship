document.addEventListener("DOMContentLoaded", () => {
    if (localStorage.getItem("name") === null) {
        window.location.href = 'index.html';
    }

    const username = document.getElementById("username");
    username.innerText = localStorage.getItem("name");
    const id = localStorage.getItem("userId");

    // Fetch books by author
    fetch(`/api/books/author/${id}`)
        .then(res => res.json())
        .then(data => {
            const list = document.getElementById("book-list");
            list.innerHTML = "";

            if (data.length === 0) {
                list.innerHTML = "<p>No books added yet!</p>";
                return;
            }

            data.forEach(book => {
                const card = document.createElement("div");
                card.classList.add("book-card");
                card.innerHTML = `
                    <h3>${book.title}</h3>
                    <p><strong>Genre:</strong> ${book.genre}</p>
                    <p>${book.description}</p>
                    <p><strong>Price:</strong> $${book.price}</p>
                    <div class="card-buttons">
                        <button class="edit-btn" data-id="${book.id}">✏️ Edit</button>
                        <button class="delete-btn" data-id="${book.id}">🗑️ Delete</button>
                    </div>
                `;
                list.appendChild(card);
            });

            // Handle edit button
            document.querySelectorAll(".edit-btn").forEach(btn => {
                btn.addEventListener("click", (e) => {
                    const bookId = e.target.getAttribute("data-id");
                    window.location.href = `edit-book.html?id=${bookId}`; // redirect to edit page
                });
            });

            // Handle delete button
            document.querySelectorAll(".delete-btn").forEach(btn => {
                btn.addEventListener("click", async (e) => {
                    const bookId = e.target.getAttribute("data-id");
                    const confirmed = await showConfirmBox("Are you sure you want to delete this book?");
                    if (!confirmed) return;

                    try {
                        const res = await fetch(`/api/books/${bookId}`, { method: "DELETE" });
                        if (res.ok) {
                            await showResultBox("✅ Book deleted successfully!");
                            window.location.href = "landingPage.html"; // Refresh the list
                        } else {
                            await showResultBox("❌ Failed to delete book!");
                        }
                    } catch (error) {
                        console.error("Error deleting book:", error);
                        await showResultBox("⚠️ Error deleting book!");
                    }
                });
            });
        })
        .catch(err => {
            console.log("Error: " + err);
            document.getElementById("book-list").innerHTML = "<p>Error fetching books.</p>";
        });

    const showConfirmBox = (message) => {
        return new Promise((resolve) => {
            const box = document.getElementById("confirmBox");
            const text = document.getElementById("confirmText");
            text.textContent = message;
            box.classList.remove("hidden");

            const yesBtn = document.getElementById("confirmYes");
            const noBtn = document.getElementById("confirmNo");

            const close = (res) => {
                box.classList.add("hidden");
                yesBtn.removeEventListener("click", yesHandler);
                noBtn.removeEventListener("click", noHandler);
                resolve(res);
            };

            const yesHandler = () => close(true);
            const noHandler = () => close(false);

            yesBtn.addEventListener("click", yesHandler);
            noBtn.addEventListener("click", noHandler);
        });
    };

    const showResultBox = (message) => {
        return new Promise((resolve) => {
            const box = document.getElementById("resultBox");
            const text = document.getElementById("resultText");
            const okBtn = document.getElementById("okBtn");
            text.textContent = message;
            box.classList.remove("hidden");

            const close = () => {
                box.classList.add("hidden");
                okBtn.removeEventListener("click", close);
                resolve();
            };
            okBtn.addEventListener("click", close);
        });
    };
});

// Dark mode toggle
const darkModeBtn = document.querySelector('.dark-mode');
const darkModeIcon = "🌙";
const lightModeIcon = "🌞";
darkModeBtn.textContent = lightModeIcon;

const updateIcon = () => {
    darkModeBtn.textContent = document.body.classList.contains('dark-mode') ? lightModeIcon : darkModeIcon;
};

darkModeBtn.addEventListener('click', () => {
    document.body.classList.toggle('dark-mode');
    document.body.classList.toggle('light-mode');
    updateIcon();
});

// Initial mode
document.body.classList.add('dark-mode');

// Logout
document.getElementById('logout-btn').addEventListener('click', () => {
    localStorage.removeItem("userId");
    localStorage.removeItem("name");
    window.location.href = 'index.html';
});
