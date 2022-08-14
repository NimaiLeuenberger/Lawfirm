/**
 * view-controller for lawyeredit.html
 * @author Nimai Leuenberger
 */
document.addEventListener("DOMContentLoaded", () => {
    const userRole = getCookie("userRole");
    if (userRole === "user"){
        document.getElementById("save").style.visibility="hidden";
        document.getElementById("reset").style.visibility="hidden";
    }

    readLegalCases();
    readClient();

    document.getElementById("clienteditForm").addEventListener("submit", saveClient);
    document.getElementById("cancel").addEventListener("click", cancelEdit);
});

/**
 * saves the data of a client
 */
function saveClient(event) {
    event.preventDefault();
    showMessage("", "info");

    const clientForm = document.getElementById("clienteditForm");
    const formData = new FormData(clientForm);
    const data = new URLSearchParams(formData);
    let method;
    let url = "./resource/client/";
    const clientID = getQueryParam("id");
    if (clientID == null) {
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
                showMessage("Klient gespeichert", "info");
                window.location.href = "./clientlist.html";
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
function readClient() {
    const clientID = getQueryParam("id");
    if (clientID != null){
        fetch("./resource/client/read?id=" + clientID, {
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
                showClient(data);
            })
            .catch(function (error) {
                console.log(error);
            });
    } else {
        document.getElementById("title").innerHTML = "Neuer Klient";
    }
}

/**
 * show the data of a client
 * @param data  the client-data
 */
function showClient(data) {
    const userRole = getCookie("userRole");
    document.getElementById("clientID").value = data.clientID;
    document.getElementById("name").value = data.name;
    document.getElementById("birthdate").value = data.birthdate;
    document.getElementById("telNumber").value = data.telNumber;
    document.getElementById("legalCase").value = data.legalCase;

    //selectedClients(data.client);
    const locked =  !(userRole === "user" || userRole === "admin");
    lockForm("clienteditForm", locked);
}

/**
 * reads all legal cases as an array
 */
function readLegalCases() {
    fetch("./resource/legalCase/list", {
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
            showLegalCases(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows all legal cases as a dropdown
 * @param data
 */
function showLegalCases(data) {
    let dropdown = document.getElementById("legalCase");
    data.forEach(legalCase => {
        let option = document.createElement("option");
        option.text = legalCase.accuser + "; " + legalCase.accuser;
        option.value = legalCase.legalCaseID;
        dropdown.add(option);
    })
}

/**
 * redirects to the clientlist
 * @param event  the click-event
 */
function cancelEdit(event) {
    window.location.href = "./clientlist.html";
}