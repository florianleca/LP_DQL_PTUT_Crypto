// Définition du bloc personnalisé "buy"
Blockly.Blocks["buy"] = {
    init: function () {
        let UNIT_OPTIONS = [
            ["%", "%"],
            ["$", "$"],
        ];
        this.appendValueInput("VALUE").setCheck("Number").appendField("buy for");
        this.appendDummyInput().appendField(
            new Blockly.FieldDropdown(UNIT_OPTIONS),
            "UNIT"
        );
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
        this.setColour(330);
        this.setTooltip("");
        this.setHelpUrl("");
    },
};

// Définition du bloc personnalisé "sell"
Blockly.Blocks["sell"] = {
    init: function () {
        let UNIT_OPTIONS = [
            ["%", "%"],
            ["coin", "coin"],
        ];
        this.appendValueInput("VALUE").setCheck("Number").appendField("sell");
        this.appendDummyInput().appendField(
            new Blockly.FieldDropdown(UNIT_OPTIONS),
            "UNIT"
        );
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
        this.setColour(330);
        this.setTooltip("");
        this.setHelpUrl("");
    },
};

// Définition du bloc personnalisé "klines_variables"
Blockly.Blocks["klines_variables"] = {
    init: function () {
        var DATA_OPTIONS = [
            ["open", "open"],
            ["close", "close"],
            ["low", "low"],
            ["high", "high"],
            ["volume", "volume"],
        ];
        this.appendDummyInput().appendField(
            new Blockly.FieldDropdown(DATA_OPTIONS),
            "DATA_TYPE"
        );
        this.setOutput(true, "Number");
        this.setColour(230);
        this.setTooltip("");
        this.setHelpUrl("");
    },
};

// Construction de la toolbox formée des blocs de notre choix
var toolbox = {
    kind: "flyoutToolbox",
    contents: [
        {
            kind: "block",
            type: "controls_if",
        },
        {
            kind: "block",
            type: "logic_compare",
        },
        {
            kind: "block",
            type: "logic_operation",
        },
        {
            kind: "block",
            type: "math_arithmetic",
        },
        {
            kind: "block",
            type: "math_number",
            fields: {
                NUM: 123,
            },
        },
        {
            kind: "block",
            type: "klines_variables",
        },
        {
            kind: "block",
            type: "buy",
        },
        {
            kind: "block",
            type: "sell",
        },
    ],
};

// Injection de blockly et de sa toolbox dans le html
const workspace = Blockly.inject("blocklyDiv", {toolbox: toolbox});

// Lance l'action lorsqu'on clique sur "Run Test"
$(document).ready(function () {
    $("#run_test_button").click(function () {
        const test = Blockly.serialization.workspaces.save(workspace);
        console.log(JSON.stringify(test)); // Garder cette ligne qui peut être pratique pour debug
        // Envoyer le json au back via Ajax (cf précédent bouton)
        clearElements("test_klines_body")
        valideTest();
    });
});

// Vider le tableau et le graphe afin d'éviter des duplications d'affichage
function clearElements(element) {
    document.getElementById(element).innerHTML = null;
}

function valideTest() {
    var cryptoBalance = $("#starting_balance_crypto").val();
    var deviseBalance = $("#starting_balance_currency").val();
    var exchangeFees = $("#exchange_fees").val();
    var captureJson = JSON.stringify(Blockly.serialization.workspaces.save(workspace));
    getTestData(cryptoBalance, deviseBalance, exchangeFees, captureJson,);
}


function getTestData(cryptoBalance, deviseBalance, exchangeFees, blocklyJson) {
    $.ajax({
        url: "http://127.0.0.1:8080/runtest/",
        type: "GET",
        dataType: "json",
        data: {
            blocklyJson: blocklyJson,
            cryptoBalance: cryptoBalance,
            deviseBalance: deviseBalance,
            exchangeFees: exchangeFees,

        },
        success: traiterJson,
    });
}

function traiterJson(json) {
    let transactions_json = json['transactions'];
    let balances_json = json['balances'];
    remplirTransactions(transactions_json);
    remplirResult(balances_json);
}

function remplirTransactions(json) {
    let pair = true;
    for (let key of Object.keys(json)) {
        let date = new Date(parseInt(key));
        date = formatageDateTableauKlines(date);
        var bos = "Vente de ";
        if (json[key].type == 'buy') {
            bos = "Achat de ";
        }
        $('#test_klines_body').append(
            '<tr class="' +
            pariteLigne(pair) +
            '"><td>' + date + " : " + bos + json[key].crypto_amount + " " + document.getElementById("pair1").value + " pour " + json[key].currency_amount + " " + document.getElementById("pair2").value + "." + "</td>"
        )
        pair = !pair
    }
}

function remplirResult(json) {
    let variationCrypto = "Le solde de crypto est passé de " + json['previous_crypto'] + " à " + json['new_crypto'] + ".";
    let variationCurrency = "Le solde de $ est passé de " + json['previous_currency'] + " à " + json['new_currency'] + ".";
    let valorisation1 = "Au début du test, le porte-feuille était valorisé à " + json['previous_value'] + ".";
    let valorisation2 = "À la fin du test, le porte-feuille est valorisé à " + json['new_value'] + ".";
    let result = "Résultat global : " + json['result'];

    let paragraphe1 = document.getElementById("paragraphe1");
    paragraphe1.innerHTML = variationCrypto;

    let paragraphe2 = document.getElementById("paragraphe2");
    paragraphe2.innerHTML = variationCurrency;

    let paragraphe3 = document.getElementById("paragraphe3");
    paragraphe3.innerHTML = valorisation1;

    let paragraphe4 = document.getElementById("paragraphe4");
    paragraphe4.innerHTML = valorisation2;

    let paragraphe5 = document.getElementById("paragraphe5");
    paragraphe5.innerHTML = result;
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
