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
const workspace = Blockly.inject("blocklyDiv", { toolbox: toolbox });

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
  getTestData(cryptoBalance,deviseBalance,exchangeFees,captureJson,);
}


function getTestData(cryptoBalance,deviseBalance,exchangeFees,blocklyJson) {
  $.ajax({
    url: "http://127.0.0.1:8080/runtest/",
    type: "GET",
    dataType: "json",
    data: {
      blocklyJson : blocklyJson,
      cryptoBalance: cryptoBalance,
      deviseBalance: deviseBalance,
      exchangeFees: exchangeFees,
      
    },
    success:remplirTestResult,
  });
}

function remplirTestResult(json){
  //   var json= `{"1679443200000":{
  //   "type":"buy",
  //   "crypto_amount":"0.155",
  //   "currency_amount":"1501",
  //   "rates":"15675"},
  // "1679475600000":{
  //   "type":"sell",
  //   "crypto_amount":"0.156",
  //   "currency_amount":"1502",
  //   "rate":"15676"},
  // "1679464800000":{
  //   "type":"buy",
  //   "crypto_amount":"0.157",
  //   "currency_amount":"1503",
  //   "rate":"15677"},
  // "1679461200000":{
  //   "type":"sell",
  //   "crypto_amount":"0.158",
  //   "currency_amount":"1504",
  //   "rate":"15678"}
    
  // }`
  // json=JSON.parse(json);
  // console.log(json);
  let pair = true;
  
  for(let key of Object.keys(json)){
    var bos="Vente de ";
    if(json[key].type == 'buy'){
      bos="Achat de ";
    }

    $('#test_klines_body').append(
      '<tr class="' +
      pariteLigne(pair) +
      '"><td>' + bos +json[key].crypto_amount +" "+ document.getElementById("pair1").value +" pour la modique somme de "+ json[key].currency_amount + " "+document.getElementById("pair2").value +"</td>"
    )
      pair=!pair
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