/**
 * view-controller for lawyeredit.html
 * @author Nimai Leuenberger
 */
document.addEventListener("DOMContentLoaded", () => {
    readClients();
    readLawyer();

    document.getElementById("lawyereditForm").addEventListener("submit", saveLawyer);
    document.getElementById("cancel").addEventListener("click", cancelEdit);
});

/**
 * saves the data of a book
 */
function saveLawyer(event) {
    event.preventDefault();
    showMessage("", "info");

    const lawyerForm = document.getElementById("lawyereditForm");
    const formData = new FormData(lawyerForm);
    const data = new URLSearchParams(formData);
    let method;
    let url = "./resource/lawyer/";
    const lawyerID = getQueryParam("id");
    if (lawyerID == null) {
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
                window.location.href = "./lawyerlist.html";
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
function readLawyer() {
    const lawyerID = getQueryParam("id");
    fetch("./resource/lawyer/read?id=" + lawyerID, {
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
            showLawyer(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * show the data of a book
 * @param data  the book-data
 */
function showLawyer(data) {
    const userRole = getCookie("userRole");
    document.getElementById("lawyerID").value = data.lawyerID;
    document.getElementById("name").value = data.name;
    document.getElementById("experience").value = data.experience;
    document.getElementById("winrate").value = data.winrate;
    document.getElementById("client").value = data.client;

    //selectedClients(data.client);
    const locked =  !(userRole === "user" || userRole === "admin");
    lockForm("lawyereditForm", locked);
}

/**
 * reads all publishers as an array
 */
function readClients() {

    fetch("./resource/client/list", {
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
            showClients(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows all publishers as a dropdown
 * @param data
 */
function showClients(data) {
    let dropdown = document.getElementById("client");
    data.forEach(client => {
        let option = document.createElement("option");
        option.text = client.name;
        option.value = client.clientID;
        dropdown.add(option);
    })
}

function selectedClients(clientList) {
    clientList.forEach(client => {
        document.getElementById(client.clientID).selected = true;
    })
}

/**
 * redirects to the bookshelf
 * @param event  the click-event
 */
function cancelEdit(event) {
    window.location.href = "./lawyerlist.html";
}