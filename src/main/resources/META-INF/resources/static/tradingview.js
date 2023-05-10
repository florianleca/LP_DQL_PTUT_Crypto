// Traite les données récupérées du Json pour générer les "bougies" et les confier à la fonction "displayGraphique"
function genererBougies(json, symbole, devise) {
    let index = 0;
    let datesGraphe = [];
    let donneesBougiesGeneral = [];
    let donneesOpen = [];
    let donneesHigh = [];
    let donneesLow = [];
    let donneesClose = [];
    for (let key of Object.keys(json)) {
        let donneesBougies = [];
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
        donneesBougies.push(open, high, low, close);
        donneesBougiesGeneral.push(donneesBougies);
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
    let options = {
        series: [{
            data: donneesGraph
        }],
        chart: {
            type: 'candlestick',
            height: 350
        },
        title: {
            text: symbole.toUpperCase() + ' en ' + devise.toUpperCase(),
            align: 'left'
        },
        xaxis: {
            type: 'datetime'
        },
        yaxis: {
            tooltip: {
                enabled: true
            }
        }
    };
    let chart = new ApexCharts(document.querySelector("#bloc_tradingview"), options);
    chart.render();
}