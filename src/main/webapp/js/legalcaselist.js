/**
 * view-controller for legalcaselist.html
 * @author Nimai Leuenberger
 */

document.addEventListener("DOMContentLoaded", () => {
    showHeadings();
    readLegalCases("","");
});

/**
 * reads all legal cases
 */
function readLegalCases() {
    let url = "./resource/legalCase/list";

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
            showLegalCaseList(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows the legal cases as a table
 * @param data  the legal cases
 */
function showLegalCaseList(data) {
    const userRole = getCookie("userRole");
    showHeadings();
    let tBody = document.getElementById("legalCases");
    tBody.innerHTML = "";
    data.forEach(legalCase => {
        let row = tBody.insertRow(-1);
        let button = document.createElement("button");
        if (userRole === "admin"){
            button.innerHTML = "&#9998;";
            button.type = "button";
            button.name = "editLegalCase";
            button.setAttribute("data-legalcaseid", legalCase.legalCaseID);
            button.addEventListener("click", editLegalCase);
            row.insertCell(-1).appendChild(button);
        } else if (userRole === "user"){
            button.innerHTML = "&#128065";
            button.type = "button";
            button.name = "readLegalCase";
            button.setAttribute("data-legalcaseid", legalCase.legalCaseID);
            button.addEventListener("click", editLegalCase);
            row.insertCell(-1).appendChild(button);
        }

        row.insertCell(-1).innerHTML = legalCase.accuser;
        row.insertCell(-1).innerHTML = legalCase.defendant;

        if (userRole === "admin") {
            button = document.createElement("button");
            button.innerHTML = "&#128465;";
            button.type = "button";
            button.name = "deleteLegalCase";
            button.setAttribute("data-legalCaseID", legalCase.legalCaseID);
            button.addEventListener("click", deleteLegalCase);
            row.insertCell(-1).appendChild(button);
        }

    });

    if (userRole === "admin") {
        document.getElementById("addButton").innerHTML = "<a href='./legalcaseedit.html'>Neuer Rechtsfall</a>";
    }
}

/**
 * redirects to the edit-form
 * @param event  the click-event
 */
function editLegalCase(event) {
    const button = event.target;
    const legalCaseID = button.getAttribute("data-legalCaseID");
    window.location.href = "./legalcaseedit.html?id=" + legalCaseID;
}

/**
 * deletes a legal case
 * @param event  the click-event
 */
function deleteLegalCase(event) {
    const button = event.target;
    const legalCaseID = button.getAttribute("data-legalCaseID");

    fetch("./resource/legalCase/delete?id=" + legalCaseID,
        {
            method: "DELETE",
            headers: { "Authorization": "Bearer "}
        })
        .then(function (response) {
            if (response.ok) {
                window.location.href = "./legalcaselist.html";
            } else {
                console.log(response);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

function showHeadings() {
    const ids = ["accuser", "defendant"];
    const labels = ["Kläger", "Angeklagter"];

    let row = document.getElementById("headings");
    row.innerText = "";
    row.insertCell(-1);
    for (let i=0; i<labels.length; i++) {
        let cell = row.insertCell(-1);
        cell.innerHTML = labels[i] + "&darr;";
        cell.id=ids[i];
    }
}