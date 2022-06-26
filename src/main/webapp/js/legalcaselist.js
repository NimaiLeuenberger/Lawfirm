/**
 * view-controller for legalcaselist.html
 * @author Nimai Leuenberger
 */
let delayTimer;

document.addEventListener("DOMContentLoaded", () => {
    showHeadings();
    readLegalCases("","");

    document.getElementById("search").addEventListener("keyup", searchLegalCases);
});

/**
 * reads all authors
 * @param field   the attribute to be used as a filter or sort
 * @param filter  the value to be filtered by
 * @param sort    the sort order
 */
function readLegalCases(field, filter, sort) {
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
 * look up the search-fields and create the filter
 * @param event
 */
function searchLegalCases(event) {
    const searchFields = ["legalCaseAccuser", "legalCaseDefendant"];
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
        readLegalCases(field, filter);
    }, 500);
}
/**
 * shows the authors as a table
 * @param data  the authors
 */
function showLegalCaseList(data) {
    const userRole = getCookie("userRole");
    showHeadings();
    let tBody = document.getElementById("legalCases");
    tBody.innerHTML = "";
    data.forEach(legalCase => {
        let row = tBody.insertRow(-1);
        let button = document.createElement("button");
        if (userRole === "user" || userRole === "admin")
            button.innerHTML = "&#9998;";
        else
            button.innerHTML = "&#128065;";

        button.type = "button";
        button.name = "editLegalCase";
        button.setAttribute("data-legalCaseID", legalCase.legalCaseID);
        button.addEventListener("click", editLegalCase);
        row.insertCell(-1).appendChild(button);

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

    if (userRole === "user" || userRole === "admin") {
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
 * deletes a author
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
        cell.innerHTML = labels[i] + "&darr;&nbsp;";
        cell.id=ids[i];
    }
}