const loginSection = document.getElementById("login-section");
const registerSection = document.getElementById("register-section");
const registerLink = document.getElementById("register");
const loginLink = document.getElementById("login");


registerLink.addEventListener("click", () => {
    loginSection.classList.add("fade-out");
    setTimeout(() => {
        loginSection.style.display = "none";
        registerSection.style.display = "block";
        registerSection.classList.add("fade-in");
    }, 300);
});

loginLink.addEventListener("click", () => {
    registerSection.classList.add("fade-out");
    setTimeout(() => {
        registerSection.style.display = "none";
        loginSection.style.display = "block";
        loginSection.classList.add("fade-in");
    }, 300);
});

//code to hit register api
const registerForm = document.getElementById("register-form");
registerForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const registerEmail = document.getElementById("new-email").value;
    const registerPassword = document.getElementById("new-password").value;
    const registerConfirmPassword = document.getElementById("cnf-password").value;
    const userName = document.getElementById("new-username").value;
    const bio = document.getElementById("biography").value;
    const errMsg = document.getElementById("errMsg");
    errMsg.innerText = "";


    if (registerPassword !== registerConfirmPassword) {
        errMsg.innerText = "Passwords do not match!";
        return;
    }

    try {
        const response = await fetch("/api/authors/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                name: userName,
                email: registerEmail,
                password: registerPassword,
                biography: bio
            })
        });
        if (response.ok) {
            showMessage("Registration successful! Please log in.");
            registerSection.classList.add("fade-out");
            setTimeout(() => {
                registerSection.style.display = "none";
                loginSection.style.display = "block";
                loginSection.classList.add("fade-in");
            }, 300);
        } else {
            const errorData = await response.json();
            showMessage("Registration failed: " + errorData.message);
        }
    }catch (error) {
        console.error("Error:", error);
        showMessage("An error occurred. Please try again.");
    }

});

//code to hit login end point api
const loginBtn = document.getElementById("login-form");
loginBtn.addEventListener("submit", async (e) => {
    console.log("Login button clicked");
    e.preventDefault();
    const loginEmail = document.getElementById("email").value;
    const loginPass = document.getElementById("password").value;

    try {
        console.log("Attempting to log in with email:", loginEmail);
        const res = await fetch("/api/authors/login" , {
            method : "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: loginEmail,
                password: loginPass
            })
        });

        if(res.ok){
            const data = await res.json();
            localStorage.setItem("name" , data.name);
            localStorage.setItem("userId" , data.id);
            window.location.href = "landingPage.html";
        }

    }catch (err){
        console.log("Error: "+err);
        showMessage("Error occurred check the console");
    }
});


function showMessage(msg) {
    const box = document.getElementById('messageBox');
    const text = document.getElementById('messageText');
    text.textContent = msg;
    box.classList.remove('hidden');
}

function hideMessage() {
    const box = document.getElementById('messageBox');
    box.classList.add('hidden');
}
