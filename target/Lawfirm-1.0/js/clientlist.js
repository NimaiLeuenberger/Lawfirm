/**
 * view-controller for clientlist.html
 * @author Nimai Leuenberger
 */

document.addEventListener("DOMContentLoaded", () => {
    showHeadings();
    readClients("","");
});

/**
 * reads all clients
 */
function readClients() {
    let url = "./resource/client/list";

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
 * shows the clients as a table
 * @param data  the clients
 */
function showClientList(data) {
    const userRole = getCookie("userRole");
    showHeadings();
    let tBody = document.getElementById("clients");
    tBody.innerHTML = "";
    data.forEach(client => {
        let row = tBody.insertRow(-1);

        let button = document.createElement("button");
        if (userRole === "admin"){
            button.innerHTML = "&#9998;";
            button.type = "button";
            button.name = "editClient";
            button.setAttribute("data-clientid", client.clientID);
            button.addEventListener("click", editClient);
            row.insertCell(-1).appendChild(button);
        } else if (userRole === "user"){
            button.innerHTML = "&#128065";
            button.type = "button";
            button.name = "readClient";
            button.setAttribute("data-clientid", client.clientID);
            button.addEventListener("click", editClient);
            row.insertCell(-1).appendChild(button);
        } else if (userRole === "guest"){
            return;
        }

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

    if (userRole === "admin") {
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
 * deletes a client
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
        cell.innerHTML = labels[i];
        cell.id=ids[i];
    }

    let t = document.getElementById("clientlist");
    t.cellPadding = '5';
}