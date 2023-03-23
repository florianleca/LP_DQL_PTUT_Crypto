//les actions lors des clicks bouton
$(document).ready(function () {
  $("#btnSubmit").click(function () {
    let tabHtml = document.getElementById("klines_body");
    tabHtml.innerHTML = "";
    validateData();
  });

  $("#btnRun").click(function(){
    alert("noob");
  })
});

function afficherCrypto() {
  var choix = document.getElementById("pair1").value;
  document.getElementById("starting_balance_crypto_label").innerHTML = choix.toUpperCase() + " Balance";
}

function afficherDevise() {
  var choix = document.getElementById("pair2").value;
  document.getElementById("starting_balance_currency_label").innerHTML = choix.toUpperCase() + " Balance";
}

//code a effectuer au lancement de l'app
window.onload = function() {
  afficherCrypto();
  afficherDevise();
  validateData();
  let startValue = document.getElementById("start_time");
  let endValue = document.getElementById("end_time")
  endValue.value = maDateMoins(0);
  startValue.value = maDateMoins(10)
  startValue.max = maDateMoins(0);
  endValue.max = maDateMoins(0);
}

//permet d'obtenir le jour actuel moins 
//le nb de jours qu'on veux
function maDateMoins(jours){
  let temp_date=new Date()
  temp_date.setDate(temp_date.getDate()-jours)
  let temp_day=temp_date.getDate();
  let temp_month=temp_date.getMonth()+1;
  let temp_year=temp_date.getFullYear();

  return temp_year+"-"+ temp_month.toString().padStart(2, '0') + "-" +
  temp_day.toString().padStart(2, '0');
}


//recupere les valeurs des inputs
function validateData() {
  var symbole = $("#pair1").val();
  var devise = $("#pair2").val();
  var start_time = $("#start_time").val();
  var end_time = $("#end_time").val();
  var interval = $("#interval").val();

  getExchangeData(symbole, devise, start_time, end_time, interval);
}



//requete au back
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
    success: function (obj) {
      for (var key of Object.keys(obj)) {
        var date = new Date(parseInt(key));
        var options = {hour: 'numeric', minute: 'numeric'};
        var dateformat = date.toLocaleDateString() + " - " + date.toLocaleTimeString("fr-FR", options);
        


        var volume = obj[key].volume;
        var high = obj[key].high;
        var number_of_trades = obj[key].number_of_trades;
        var low = obj[key].low;
        var close = obj[key].close;
        var open = obj[key].open;

        $("#klines_body").append(
          "<tr><td>" +
            dateformat +
            "</td>" +
            "<td>" +
            open +
            "</td>" +
            "<td>" +
            close +
            "</td>" +
            "<td>" +
            low +
            "</td>" +
            "<td>" +
            high +
            "</td>" +
            "<td>" +
            volume +
            "</td>" +
            "<td>" +
            number_of_trades +
            "</td>" +
            "</tr>"
        );
      }
    },
  });
}

//Code pour le deuxieme bouton "RUN Test"

// function validatTest() {
//   var symboleBalance = $("#starting_balance_crypto").val();
//   var deviseBalance = $("#starting_balance_currency").val();
//   var exchangeFees = $("#exchange_fees").val();

//   getTestData(symboleBalance,deviseBalance,exchangeFees);
// }


// function getTestData(symboleBalance,deviseBalance,exchangeFees) {
//   $.ajax({
//     url: "http://127.0.0.1:8080/runtest/" +
//     symboleBalance + "/" + deviseBalance,
//     type: "GET",
//     dataType: "json",
//     data: {
//       startTime: Date.parse(start_time),
//       endTime: Date.parse(end_time),
//       frequency: interval,
//     }
//   });
// }




