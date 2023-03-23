$(document).ready(function () {
  $("#btnSubmit").click(function () {
    let tabHtml = document.getElementById("klines_body");
    tabHtml.innerHTML = "";
    validateData();
  });
});

function afficherCrypto() {
  var choix = document.getElementById("pair1").value;
  document.getElementById("starting_balance_crypto_label").innerHTML = choix.toUpperCase() + " Balance";
}

function afficherDevise() {
  var choix = document.getElementById("pair2").value;
  document.getElementById("starting_balance_currency_label").innerHTML = choix.toUpperCase() + " Balance";
}

window.onload = function() {
  afficherCrypto();
  afficherDevise();
  validateData();
}

function validateData() {
  var symbole = $("#pair1").val();
  var devise = $("#pair2").val();
  var start_time = $("#start_time").val();
  var end_time = $("#end_time").val();
  var interval = $("#interval").val();

  getExchangeData(symbole, devise, start_time, end_time, interval);
}

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
        var options = {year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric'};
        var options2 = {hour: 'numeric', minute: 'numeric'};
        // var dateformat = date.toLocaleDateString("fr-FR", options);
        var dateformat = date.toLocaleDateString() + " - " + date.toLocaleTimeString("fr-FR", options2);
        


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



