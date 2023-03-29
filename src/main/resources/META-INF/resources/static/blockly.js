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
  });
});
