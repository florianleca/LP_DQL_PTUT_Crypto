// Déclaration du graphique
var chart;

// Traite les données récupérées du Json pour générer les "bougies" et les confier à la fonction "displayGraphique"
function genererBougies(json, symbole, devise) {
    let index = 0;
    let datesGraphe = [];
    let donneesOpen = [];
    let donneesHigh = [];
    let donneesLow = [];
    let donneesClose = [];
    for (let key of Object.keys(json)) {
        let date = new Date(parseInt(key));
        let high = json[key].high;
        let low = json[key].low;
        let close = json[key].close;
        let open = json[key].open;
        datesGraphe.push(date);
        donneesOpen.push(open);
        donneesHigh.push(high);
        donneesLow.push(low);
        donneesClose.push(close);
        index++;
    }
    displayGraphique(datesGraphe, donneesOpen, donneesHigh, donneesLow, donneesClose, symbole, devise);
}

// Permet d'afficher un graphe de trading de type "candlestick"
function displayGraphique(datesGraphe, donneesOpen, donneesHigh, donneesLow, donneesClose, symbole, devise) {
    let index = 0;
    let donneesGraph = [];
    for (let date in datesGraphe) {
        let itemToPush = {
            x: new Date(datesGraphe[date]),
            y: [donneesOpen[index], donneesHigh[index], donneesLow[index], donneesClose[index]]
        }
        donneesGraph.push(itemToPush);
        index++;
    }

    // Options du graphique
    let options = {
        series: [
          {
            name: 'Bougies',
            type: 'candlestick',
            data: donneesGraph
          }
        ],
        chart: {
            type: 'candlestick',
            height: 350
        },
        title: {
            text: symbole.toUpperCase() + ' en ' + devise.toUpperCase(),
            align: 'left'
        },
        xaxis: {
            type: 'datetime',
            labels: {
              formatter: function(value, timestamp) {
                return dayjs(timestamp).format('DD/MM/YYYY HH:mm');
              }
            },
          },
        yaxis: {
            tooltip: {
                enabled: true
            }
        }
    };
    chart = new ApexCharts(document.querySelector("#bloc_tradingview"), options);
    chart.render();
}

function ajoutAnnotations(jsonTransactions) {
  let annotations = {
    xaxis: []
  };
  var currentOptions = chart.opts;
  currentOptions.annotations.xaxis = []; // Supprimer les précédentes annotations et ne pas surcharger le graphique
  for(let key of Object.keys(jsonTransactions)) {
    let backColor = jsonTransactions[key].type === 'buy' ? '#FF0000' : '#00FF00';
    let texte = jsonTransactions[key].currency_amount;
    annotations.xaxis.push({
          x: key,
          borderColor: backColor,
          borderWidth: 2,
          label: {
          borderColor: backColor,
          style: {
              color: '#fff',
              background: backColor
          },
          text: texte
        }
    })     
  }
  let newOptions = Object.assign({},currentOptions);
    newOptions.annotations.xaxis = newOptions.annotations.xaxis.concat(annotations.xaxis)
    chart.updateOptions(newOptions);
}