/**
 * view-controller for clientlist.html
 * @author Nimai Leuenberger
 */
let delayTimer;

document.addEventListener("DOMContentLoaded", () => {
    showHeadings();
    readClients("","");

    document.getElementById("search").addEventListener("keyup", searchClients);
});

/**
 * reads all publishers
 */
function readClients() {
    let url = "./resource/client/list";
    /*if (field !== "" && filter !== "") {
        url += "?field=" + field;
        url += "&filter=" + filter;
    }*/
    fetch(url, {
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
            showClientList(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * look up the search-fields and create the filter
 * @param event
 */
function searchClients(event) {
    const searchFields = ["clientName", "clientBirthdate", "clientTelNumber", "legalCase"]
    const element = event.target;
    const field = element.id;
    let filter = "";

    searchFields.forEach(searchField => {
        let element = document.getElementById(searchField);
        if (searchField === field) {
            filter = element.value;
        } else {
            element.value = "";
        }

    });
    sessionStorage.setItem("filterField", field);
    sessionStorage.setItem("filterValue", filter);

    clearTimeout(delayTimer);
    delayTimer = setTimeout(() => {
        readClients();
    }, 500);
}
/**
 * shows the publishers as a table
 * @param data  the publishers
 */
function showClientList(data) {
    const userRole = getCookie("userRole");
    showHeadings();
    let tBody = document.getElementById("clients");
    tBody.innerHTML = "";
    data.forEach(client => {
        let row = tBody.insertRow(-1);

        let button = document.createElement("button");
        if (userRole === "user" || userRole === "admin")
            button.innerHTML = "&#9998;";
        else
            button.innerHTML = "&#128065;";

        button.type = "button";
        button.name = "editClient";
        button.setAttribute("data-clientid", client.clientID);
        button.addEventListener("click", editClient);
        row.insertCell(-1).appendChild(button);

        row.insertCell(-1).innerHTML = client.name;
        row.insertCell(-1).innerHTML = client.birthdate;
        row.insertCell(-1).innerHTML = client.telNumber;
        row.insertCell(-1).innerHTML = client.legalCase.accuser + "; " + client.legalCase.defendant;


        if (userRole === "admin") {
            button = document.createElement("button");
            button.innerHTML = "&#128465;";
            button.type = "button";
            button.name = "deleteClient";
            button.setAttribute("data-clientid", client.clientID);
            button.addEventListener("click", deleteClient);
            row.insertCell(-1).appendChild(button);
        }

    });

    if (userRole === "user" || userRole === "admin") {
        document.getElementById("addButton").innerHTML = "<a href='./clientedit.html'>Neuer Klient</a>";
    }
}

/**
 * redirects to the edit-form
 * @param event  the click-event
 */
function editClient(event) {
    const button = event.target;
    const clientID = button.getAttribute("data-clientid");
    window.location.href = "./clientedit.html?id=" + clientID;
}

/**
 * deletes a publisher
 * @param event  the click-event
 */
function deleteClient(event) {
    const button = event.target;
    const clientID = button.getAttribute("data-clientid");

    fetch("./resource/client/delete?id=" + clientID,
        {
            method: "DELETE",
            headers: { "Authorization": "Bearer "}
        })
        .then(function (response) {
            if (response.ok) {
                window.location.href = "./clientlist.html";
            } else {
                console.log(response);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

function showHeadings() {
    const ids = ["name", "birthdate", "telNumber", "legalCase"];
    const labels = ["Name", "Geburtsdatum", "Telefonnummer", "Rechtsfall (Kläger; Angeklagter)"];

    let row = document.getElementById("headings");
    row.innerText = "";
    row.insertCell(-1);
    for (let i=0; i<labels.length; i++) {
        let cell = row.insertCell(-1);
        cell.innerHTML = labels[i] + "&darr;&nbsp;";
        cell.id=ids[i];
    }
}