document.addEventListener('DOMContentLoaded', function () {
    if(!localStorage.getItem('userId')) {
        window.location.href = 'index.html'; // Redirect to log in if not logged in
    }
    const form = document.getElementById('addBookForm');
    const box = document.getElementById('messageBox');
    const msg = document.getElementById('messageText');
    const ok = document.getElementById('okButton');
    const error = document.getElementById('err');

    form.addEventListener('submit', async function (e) {
        e.preventDefault();
        try {
            console.log('submit fired'); // Debugging line
            const title = form.title.value.trim();
            const genre = form.genre.value;
            const description = form.description.value.trim();
            const isbn = form.isbn.value.trim();
            const price = parseFloat(form.price.value);
            if (!title) {
                error.innerText = 'Please enter the book title.';
                return;
            }
            if (!genre) {
                error.innerText = 'Please select a genre.';
                return;
            }

            if (!description) {
                error.innerText = 'Please enter a description.';
                return;
            }
            if (!isbn || isNaN(isbn) || isbn.length < 10) {
                error.innerText = 'Please enter a valid ISBN (at least 10 digits).';
                return;
            }
            if (isNaN(price) || price <= 0) {
                error.innerText = 'Please enter a valid price greater than 0.';
                return;
            }
            const authorId = localStorage.getItem('userId');
            if (!authorId) {
                error.innerText = 'Author not logged in. Please log in again.';
                return;
            }
            const bookData = {
                title: title,
                genre: genre,
                description: description,
                isbn: isbn,
                price: price,
                authorId: authorId
            };
            console.log('Book Data:', bookData); // Debugging line
            const response = await fetch('/api/books/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(bookData)
            });
            if (!response.ok) {
                const errorData = await response.json();
                error.innerText = errorData.message || 'Failed to add book. Please try again.';
                return;
            }
            // success
            error.innerText = ''; // clear previous errors
            showMessage('✅ Book added successfully!');
        } catch (err) {
            console.error(err);
            // fallback alert if something goes wrong
            error.innerText = 'Something went wrong — check console for details.';
        }
    });

    ok.addEventListener('click', hideMessage);

    function showMessage(text) {
        msg.textContent = text;
        box.classList.remove('hidden');
    }

    function hideMessage() {
        box.classList.add('hidden');
        // optionally reset form &/or navigate
        form.reset();
        window.location.href = 'landingPage.html';
    }
});