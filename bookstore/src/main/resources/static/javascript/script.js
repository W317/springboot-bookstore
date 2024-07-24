document.addEventListener('DOMContentLoaded', () => {
    fetchBooks();
    fetchAuthors();

    document.getElementById('addBookButton').addEventListener('click', () => {
        toggleForm('bookForm');
    });

    document.getElementById('addAuthorButton').addEventListener('click', () => {
        toggleForm('authorForm');
    });
});

const bookForm = document.getElementById('bookForm');
const authorForm = document.getElementById('authorForm');
const booksTable = document.getElementById('booksTable').getElementsByTagName('tbody')[0];
const authorsTable = document.getElementById('authorsTable').getElementsByTagName('tbody')[0];
const bookMessage = document.getElementById('bookMessage');
const authorMessage = document.getElementById('authorMessage');

function toggleForm(formId) {
    const form = document.getElementById(formId);
    if (form.style.display === 'none' || form.style.display === '') {
        form.style.display = 'block';
    } else {
        form.style.display = 'none';
    }
}

// Fetch and display books
function fetchBooks() {
    fetch('/api/books')
        .then(response => response.json())
        .then(books => {
            const booksTable = document.getElementById('booksTable').getElementsByTagName('tbody')[0];
            booksTable.innerHTML = '';
            books.forEach(book => {
                const row = booksTable.insertRow();
                row.insertCell(0).textContent = book.id;
                row.insertCell(1).textContent = book.title;
                row.insertCell(2).textContent = book.author ? book.author.name : 'Unknown';
                const actionCell = row.insertCell(3);
                const addButton = document.createElement('button');
                addButton.textContent = 'Add New';
                addButton.addEventListener('click', () => toggleForm('bookForm'));
                actionCell.appendChild(addButton);
            });
        });
}

// Fetch and display authors
function fetchAuthors() {
    fetch('/api/authors')
        .then(response => response.json())
        .then(authors => {
            authorsTable.innerHTML = '';
            authors.forEach(author => {
                const row = authorsTable.insertRow();
                row.insertCell(0).textContent = author.id;
                row.insertCell(1).textContent = author.name;
                const actionCell = row.insertCell(2);
                const addButton = document.createElement('button');
                addButton.textContent = 'Add New';
                addButton.addEventListener('click', () => toggleForm('authorForm'));
                actionCell.appendChild(addButton);
            });
        });
}

// Add a new book
document.getElementById('bookForm').addEventListener('submit', event => {
    event.preventDefault();
    const formData = new FormData(event.target);
    const bookData = {
        title: formData.get('title'),
        author: {
            name: formData.get('author')
        }
    };

    fetch('/api/books', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(bookData)
    })
        .then(response => response.text().then(message => ({status: response.status, message})))
        .then(({status, message}) => {
            const bookMessage = document.getElementById('bookMessage');
            if (status === 201) {
                bookMessage.textContent = message;
                bookMessage.style.color = "green";
                document.getElementById('bookForm').reset();
                fetchBooks();
                fetchAuthors();
            } else {
                bookMessage.textContent = message;
                bookMessage.style.color = "red";
            }
        });
});


// Add a new author
authorForm.addEventListener('submit', event => {
    event.preventDefault();
    const formData = new FormData(authorForm);
    const authorData = Object.fromEntries(formData.entries());

    fetch('/api/authors', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(authorData)
    })
        .then(response => response.text().then(message => ({status: response.status, message})))
        .then(({status, message}) => {
            if (status === 201) {
                authorMessage.textContent = message;
                authorMessage.style.color = "green";
                authorForm.reset();
                authorForm.style.display = "none";
                fetchAuthors();
            } else {
                authorMessage.textContent = message;
                authorMessage.style.color = "red";
            }
        })
});
