$(document).ready(function () {
  $("#btnSubmit").click(function () {
    validateData();
  });
});

function validateData() {
  var symbole = $("#symbole").val();
  var devise = "usdt";
  var startTime = $("#startTime").val();
  var endTime = $("#endTime").val();
  var frequency = $("#frequency").val();

  getExchangeData(symbole, devise, startTime, endTime, frequency);
}

function getExchangeData(symbole, devise, startTime, endTime, frequency) {
  $.ajax({
    url: "http://127.0.0.1:8080/getdata/" + symbole + "/" + devise,
    type: "GET",
    dataType: "json",
    data: {
      startTime: Date.parse(startTime),
      endTime: Date.parse(endTime),
      frequency: frequency,
    },
    success: function (obj) {
      for (var key of Object.keys(obj)) {
        var date = new Date(parseInt(key));
        var options = {year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric'};
        var dateformat = date.toLocaleDateString("fr-FR", options);

        var volume = obj[key].volume;
        var high = obj[key].high;
        var number_of_trades = obj[key].number_of_trades;
        var low = obj[key].low;
        var close = obj[key].close;
        var open = obj[key].open;

        $("#transactionTable").append(
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
