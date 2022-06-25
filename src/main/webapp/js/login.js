/**
 * view-controller for login-form
 */
document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("loginForm").addEventListener("submit", loginUser);
});

function loginUser(event) {
    event.preventDefault();
    showMessage("", "info");
    const loginForm = document.getElementById("loginForm");
    const formData = new FormData(loginForm);
    const data = new URLSearchParams(formData);

    fetch("./resource/user/login",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: data
        })
        .then(function (response) {
            if (!response.ok) {
                showMessage("Benutzername/Passwort unbekannt", "error");
            } else loginSuccess(response)
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * save the JWToken and redirect
 * @param response
 */
function loginSuccess(response) {
    window.location.href = "lawyerlist.html";
}