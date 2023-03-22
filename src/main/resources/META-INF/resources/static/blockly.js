var toolbox = {
    "kind": "flyoutToolbox",
    "contents": [
      {
        "kind": "block",
        "type": "controls_if"
      },
      {
        "kind": "block",
        "type": "logic_compare"
      },
      {
        "kind": "block",
        "type": "logic_operation"
      },
      {
        "kind": "block",
        "type": "math_arithmetic"
      },
      {
        "kind": "block",
        "type": "math_number",
        "fields": {
            "NUM": 123
        }
      },
    ]
  };
  
var workspace = Blockly.inject('blocklyDiv', {toolbox: toolbox});
// workspace.options.dragSurface = false;

// const componentStyle = {
//     'flyoutBackgroundColour': '#6565f6',
//     'insertionMarkerColour': '#6565f6',
//     'workspaceBackgroundColour': '#6565f6'
//  }

//  const theme = Blockly.Theme.defineTheme('monTheme', {
//     'base': Blockly.Themes.Classic,
//     'componentStyles': {
//         'flyoutBackgroundColour': '#6565f6',
//     'insertionMarkerColour': '#6565f6',
//     'workspaceBackgroundColour': '#6565f6'
//     }
//  });

//  workspace.setTheme("monTheme");
