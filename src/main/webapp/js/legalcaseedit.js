/**
 * view-controller for lawyeredit.html
 * @author Nimai Leuenberger
 */
document.addEventListener("DOMContentLoaded", () => {
    readLegalCase();

    document.getElementById("legalcaseeditForm").addEventListener("submit", saveLegalCase);
    document.getElementById("cancel").addEventListener("click", cancelEdit);
});

/**
 * saves the data of a book
 */
function saveLegalCase(event) {
    event.preventDefault();
    showMessage("", "info");

    const legalCaseForm = document.getElementById("legalcaseeditForm");
    const formData = new FormData(legalCaseForm);
    const data = new URLSearchParams(formData);
    let method;
    let url = "./resource/legalCase/";
    const legalCaseID = getQueryParam("id");
    if (legalCaseID == null) {
        method = "POST";
        url += "create";
    } else {
        method = "PUT";
        url += "update";
    }
    console.log(data.toString());
    fetch(url,
        {
            method: method,
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: data
        })
        .then(function (response) {
            if (!response.ok) {
                showMessage("Fehler beim Speichern", "error");
                console.log(response);
            } else {
                showMessage("Anwalt gespeichert", "info");
                window.location.href = "./legalcaselist.html";
                return response;
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * reads a lawyer
 */
function readLegalCase() {
    const legalCaseID = getQueryParam("id");
    fetch("./resource/legalCase/read?id=" + legalCaseID, {
        headers: { "Authorization": "Bearer "}
    })
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showLegalCase(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * show the data of a book
 * @param data  the book-data
 */
function showLegalCase(data) {
    const userRole = getCookie("userRole");
    document.getElementById("legalCaseID").value = data.legalCaseID;
    document.getElementById("accuser").value = data.accuser;
    document.getElementById("defendant").value = data.defendant;

    //selectedClients(data.client);
    const locked =  !(userRole === "user" || userRole === "admin");
    lockForm("legalcaseeditForm", locked);
}

/**
 * redirects to the bookshelf
 * @param event  the click-event
 */
function cancelEdit(event) {
    window.location.href = "./legalcaselist.html";
}