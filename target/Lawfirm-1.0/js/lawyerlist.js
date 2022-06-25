/**
 * view-controller for bookshelf.html
 * @author Marcel Suter
 */
let delayTimer;

document.addEventListener("DOMContentLoaded", () => {
    showHeadings();
    readLawyers();

    document.getElementById("search").addEventListener("keyup", searchLawyers);
});

/**
 * reads all books
 */
function readLawyers() {
    let url = "./resource/lawyer/list";

    fetch(url, {
        headers: {"Authorization": "Bearer "}
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
            showLawyerlist(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * look up the search-fields and create the filter
 * @param event
 */
function searchLawyers(event) {
    const searchFields = ["nameFilter", "experienceFilter", "winrateFilter", "clientFilter"]
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

        readLawyers();
    }, 500);
}

/**
 * shows the booklist as a table
 * @param data  the books
 */
function showLawyerlist(data) {
    const userRole = getCookie("userRole");
    showHeadings();
    let tBody = document.getElementById("lawyers");
    tBody.innerHTML = "";
    data.forEach(lawyer => {
        let row = tBody.insertRow(-1);

        let button = document.createElement("button");
        if (userRole === "user" || userRole === "admin")
            button.innerHTML = "&#9998;";
        else
            button.innerHTML = "&#128065;";
        button.type = "button";
        button.name = "editLawyer";
        button.setAttribute("data-lawyerid", lawyer.lawyerID);
        button.addEventListener("click", editLawyer);
        row.insertCell(-1).appendChild(button);

        row.insertCell(-1).innerHTML = lawyer.name;
        row.insertCell(-1).innerHTML = lawyer.experience;
        row.insertCell(-1).innerHTML = lawyer.winrate;
        row.insertCell(-1).innerHTML = lawyer.client.name;

        if (userRole === "admin") {
            button = document.createElement("button");
            button.innerHTML = "&#128465;";
            button.type = "button";
            button.name = "deleteLawyer";
            button.setAttribute("data-lawyerid", lawyer.lawyerID);
            button.addEventListener("click", deleteLawyer);
            row.insertCell(-1).appendChild(button);
        }

    });

    if (userRole === "user" || userRole === "admin") {
        document.getElementById("addButton").innerHTML = "<a href='../lawyeredit.html'>Neues Buch</a>";
    }
}

/**
 * redirects to the edit-form
 * @param event  the click-event
 */
function editLawyer(event) {
    const button = event.target;
    const lawyerID = button.getAttribute("data-lawyerid");
    window.location.href = "./lawyeredit.html?id=" + lawyerID;
}

/**
 * deletes a book
 * @param event  the click-event
 */
function deleteLawyer(event) {
    const button = event.target;
    const lawyerID = button.getAttribute("data-lawyerid");

    fetch("./resource/lawyer/delete?id=" + lawyerID,
        {
            method: "DELETE",
            headers: {"Authorization": "Bearer "}
        })
        .then(function (response) {
            if (response.ok) {
                window.location.href = "./lawyerlist.html";
            } else {
                console.log(response);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

function showHeadings() {
    const ids = ["name", "experience", "winrate", "client"];
    const labels = ["Name", "Erfahrung (in Jahren)", "Gewinnrate", "Klient"];

    let row = document.getElementById("headings");
    row.innerText = "";
    row.insertCell(-1);
    for (let i=0; i<labels.length; i++) {
        let cell = row.insertCell(-1);
        cell.innerHTML = labels[i] + "&darr;&nbsp;";
        cell.id=ids[i];
    }
}