let workspace = null;

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
        let DATA_OPTIONS = [
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
let toolbox = {
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
function injectBlockly() {
    workspace = Blockly.inject("blocklyDiv", {toolbox: toolbox});
    let defaultBlocks = {
        "blocks": {
            "languageVersion": 0,
            "blocks": [{
                "type": "controls_if",
                "id": "k38QBFRc~G9guEH,SoA_",
                "x": 202,
                "y": 97,
                "inputs": {
                    "IF0": {
                        "block": {
                            "type": "logic_compare",
                            "id": "PdHKdY)7Ub]C3_XKGju1",
                            "fields": {"OP": "LT"},
                            "inputs": {
                                "A": {
                                    "block": {
                                        "type": "klines_variables",
                                        "id": "Z}C,{rjp$|+P1+aM3*mJ",
                                        "fields": {"DATA_TYPE": "close"}
                                    }
                                },
                                "B": {
                                    "block": {
                                        "type": "math_number",
                                        "id": "}M`QNb3UFRbf3-PnC^$t",
                                        "fields": {"NUM": 27500}
                                    }
                                }
                            }
                        }
                    },
                    "DO0": {
                        "block": {
                            "type": "buy",
                            "id": "2/YZpGq-v06Dc/E(+%u1",
                            "fields": {"UNIT": "$"},
                            "inputs": {
                                "VALUE": {
                                    "block": {
                                        "type": "math_number",
                                        "id": "%#hcJM0DqeC/FY:;{kK",
                                        "fields": {"NUM": 10}
                                    }
                                }
                            }
                        }
                    }
                }
            }, {
                "type": "controls_if",
                "id": "_H0WQPP{#OE,1ZYnR7_M",
                "x": 192,
                "y": 262,
                "inputs": {
                    "IF0": {
                        "block": {
                            "type": "logic_compare",
                            "id": "o`GVeX6:(W^niXLHDR@]",
                            "fields": {"OP": "GTE"},
                            "inputs": {
                                "A": {
                                    "block": {
                                        "type": "klines_variables",
                                        "id": "BDzip?_@AypJZ9Uc(QT`",
                                        "fields": {"DATA_TYPE": "close"}
                                    }
                                },
                                "B": {
                                    "block": {
                                        "type": "math_number",
                                        "id": ";49xVz7YC=),tohqPOY2",
                                        "fields": {"NUM": 28100}
                                    }
                                }
                            }
                        }
                    },
                    "DO0": {
                        "block": {
                            "type": "sell",
                            "id": "gYvOZQvF6+OUWdi=|ce0",
                            "fields": {"UNIT": "%"},
                            "inputs": {
                                "VALUE": {
                                    "block": {
                                        "type": "math_number",
                                        "id": ".DF(AWF-LVqAyplo_cJS",
                                        "fields": {"NUM": 10}
                                    }
                                }
                            }
                        }
                    }
                }
            }]
        }
    };
    Blockly.serialization.workspaces.load(defaultBlocks, workspace);
}


// Lance l'action lorsqu'on clique sur "Run Test"
$(document).ready(function () {
    $("#run_test_button").click(function () {
        const test = Blockly.serialization.workspaces.save(workspace);
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
    // Modif Sam
    ajoutAnnotations(transactions_json);
}

function remplirTransactions(json) {
    let pair = true;
    for (let key of Object.keys(json)) {
        let date = new Date(parseInt(key));
        date = formatageDateTableauKlines(date);
        var bos = "Vente de ";
        if (json[key].type === 'buy') {
            bos = "Achat de ";
        }
        $('#test_klines_body').append(
            '<tr class="' +
            pariteLigne(pair) +
            '"><td>' + date + " : " + bos + document.getElementById("pair1").value.toUpperCase() + " pour " + Number(json[key].currency_amount).toFixed(2) + " " + document.getElementById("pair2").value.toUpperCase() + "." + "</td>"
        )
        pair = !pair
    }
}

function remplirResult(json) {
    let variationCrypto = "Le solde de crypto est passé de " + json['previous_crypto'] + " à " + Math.round(json['new_crypto'] * 10000000) / 10000000 + ".";
    let variationCurrency = "Le solde de $ est passé de " + Number(json['previous_currency']).toFixed(2) + " à " + Number(json['new_currency']).toFixed(2) + ".";
    let valorisation1 = "Au début du test, le porte-feuille était valorisé à " + Number(json['previous_value']).toFixed(2) + ".";
    let valorisation2 = "À la fin du test, le porte-feuille est valorisé à " + Number(json['new_value']).toFixed(2) + "$.";

    if(json['result'] >= 0){
        result = "<span class='badge-green'> Résultat global : " + Number(json['result']).toFixed(2) + " $</span>";
    }else{
        result = "<span class='badge-red'> Résultat global : " + Number(json['result']).toFixed(2) + " $</span>";
    }

    let resultats = document.getElementById("resultats");
    resultats.innerHTML = variationCrypto + "</br>" + variationCurrency + "</br>" + valorisation1 + "</br>" + valorisation2 + "</br>" + result;
}

