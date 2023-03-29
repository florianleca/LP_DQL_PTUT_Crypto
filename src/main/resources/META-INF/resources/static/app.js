// Définit les fonctions à exécuter au chargement de la page
window.onload = function () {
  afficherCrypto();
  afficherDevise();
  manageCalendars();
  validateData();
};

// Affiche le nom de la crypto choisie dans le cadre "Test Settings"
function afficherCrypto() {
  let choix = document.getElementById("pair1").value;
  document.getElementById("starting_balance_crypto_label").innerHTML =
    choix.toUpperCase() + " Balance";
}

// Affiche le nom de la devise choisie dans le cadre "Test Settings"
function afficherDevise() {
  let choix = document.getElementById("pair2").value;
  document.getElementById("starting_balance_currency_label").innerHTML =
    choix.toUpperCase() + " Balance";
}

// Fixe les attributs 'value' (7 derniers jours) et 'max' (today) des calendriers
function manageCalendars() {
  let date = new Date();
  let affichage = formatageDateCalendriers(date);
  document.getElementById("end_time").setAttribute("value", affichage);
  document.getElementById("start_time").setAttribute("max", affichage);
  document.getElementById("end_time").setAttribute("max", affichage);
  date.setDate(date.getDate() - 7);
  affichage = formatageDateCalendriers(date);
  document.getElementById("start_time").setAttribute("value", affichage);
}

// Formate une date au format "aaaa-mm-jj"
function formatageDateCalendriers(date) {
  let day = date.getDate();
  let month = date.getMonth() + 1;
  let year = date.getFullYear();
  return (
    year +
    "-" +
    month.toString().padStart(2, "0") +
    "-" +
    day.toString().padStart(2, "0")
  );
}

// Lance l'action lorsqu'on clique sur "Submit"
$(document).ready(function () {
  $("#submit_button").click(function () {
    clearElements("klines_body")
    validateData();
  });
});
// Vider le tableau et le graphe afin d'éviter des duplications d'affichage
function clearElements(element) {
  document.getElementById(element).innerHTML = null;
}

// Récupère les paramètres rentrés par l'utilisateur et les envoie
function validateData() {
  let symbole = $("#pair1").val();
  let devise = $("#pair2").val();
  let start_time = $("#start_time").val();
  let end_time = $("#end_time").val();
  let interval = $("#interval").val();
  getExchangeData(symbole, devise, start_time, end_time, interval);
}

// Envoi des paramètres à l'API + redirige en cas de succès
function getExchangeData(symbole, devise, start_time, end_time, interval) {
  $.ajax({
    url: "http://127.0.0.1:8080/getdata/" + symbole + "/" + devise,
    type: "GET",
    dataType: "json",
    data: {
      startTime: Date.parse(start_time),
      endTime: Date.parse(end_time),
      frequency: interval,
    },
    success: remplirTableauKlines,
  });
}

// Remplit le tableau des klines à partir du json renvoyé par le back
function remplirTableauKlines(json) {
    
  let pair = true;
  for (let key of Object.keys(json)) {
    let date = new Date(parseInt(key));
    let dateformat = formatageDateTableauKlines(date);
    $("#klines_body").append(
      '<tr class="' +
        pariteLigne(pair) +
        '"><td>' +
        dateformat +
        "</td><td>" +
        json[key].open +
        "</td><td>" +
        json[key].close +
        "</td><td>" +
        json[key].low +
        "</td><td>" +
        json[key].high +
        "</td><td>" +
        json[key].volume +
        "</td><td>" +
        json[key].number_of_trades +
        "</td></tr>"
    );
    pair = !pair;
  }
}

// Sert à remplir les lignes avec 2 couleurs en alternance
function pariteLigne(bool) {
  let classe = "lignePaire";
  if (!bool) {
    classe = "ligneImpaire";
  }
  return classe;
}

// Formate une date au format "jj/mm/aa - hh:mm"
function formatageDateTableauKlines(date) {
  let day = date.getDate().toString().padStart(2, "0");
  let month = (date.getMonth() + 1).toString().padStart(2, "0");
  let year = date.getFullYear().toString().slice(-2);
  let hour = date.getHours().toString().padStart(2, "0");
  let minutes = date.getMinutes().toString().padStart(2, "0");
  return day + "/" + month + "/" + year + " - " + hour + ":" + minutes;
}



