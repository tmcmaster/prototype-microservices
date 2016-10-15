#!/usr/bin/env node
'use strict';

var jsdom = require('jsdom');
var YAML = require('yaml');
var fs = require('fs');
var asciidoctor = require('asciidoctor.js');
var yaml2json = require('yaml-to-json');

// var str = "salesforce<[quotes]-mulesoft"
// var regexp = /([A-z]*)-\[([A-z]*)\]>([A-z]*)/;
// var matches_array = str.match(regexp);

// var str = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
// var regexp = /[A-E]/gi;
// var matches_array = str.match(regexp);

// for (var i in matches_array) {
//   console.log(i)
// }

// var edge = parseEdge(str);
// console.log(JSON.stringify(edge));
// return;


fs.readFile("processes/process.yaml", 'utf8', function (err, text) {
  if (err) {
    console.error("[ERROR] couldn't read from '" + filename + "':");
    console.error(err.message);
    return;
  }

  var data = yaml2json(text, {});

  //console.info('Object: ' + JSON.stringify(data.process.title));

  console.log('Generating the process diagrams.');
  generateProcessDiagrams(data.process);
});

function generateProcessDiagrams(process) {

  console.log('Generating the process details');
  var processDetails = generateProcessDetails(process);

  console.log('Generating the process usecase diagrams.');
  console.log(process.usecase);
  for (var u=0; u<process.usecase.length; u++) {
    var usecase = process.usecase[u];
    console.log('Generating the usecase definition for: ' + usecase.name);
    var usecaseDefinition = generateUsecaseDefinition(usecase);
    console.log('Generating the sequence diagram definition for: ' + usecase.name);
    var sequenceDiagramDefinition = generateSequenceDiagramDefinition(usecaseDefinition, processDetails);
    console.log('Generating the sequence diagram for: ' + usecase.name);
    generateSequenceDiagram(sequenceDiagramDefinition)
  }
}

function generateUsecaseDefinition(usecase) {
  var usecaseDefinition = {
    name: usecase.name,
    title: usecase.title,
    edges: []
  };

  for (var s=0; s<usecase.sequence.length; s++) {
    var sequence = usecase.sequence[s];
    var edge = parseEdge(sequence);
    usecaseDefinition.edges.push(edge);
  }

  return usecaseDefinition;
}

function generateSequenceDiagramDefinition(usecaseDefinition, processDetails) {
  var sequenceDiagramDefinition = {
    name: usecaseDefinition.name,
    title: usecaseDefinition.title,
    components: [],
    edges: []
  };

  var componentMap = {};

  var componentIndex = 0;
  for (var e=0; e<usecaseDefinition.edges.length; e++) {
    var edge = usecaseDefinition.edges[e];
    console.log('Processing Edge: ' + edge.start + " -" + edge.operation + "> " + edge.end);
    if (!componentMap.hasOwnProperty(edge.start))
    {
      componentMap[edge.start] = componentIndex++;
      sequenceDiagramDefinition.components.push(edge.start);
    }
    if (!componentMap.hasOwnProperty(edge.end))
    {
      componentMap[edge.end] = componentIndex++;
      sequenceDiagramDefinition.components.push(edge.end);
    }
    var edgeDefinition = {
      message: edge.operation,
      start: componentMap[edge.start],
      end: componentMap[edge.end],
      color: (edge.type === "request" ? "blue" : "green")
    };
    sequenceDiagramDefinition.edges.push(edgeDefinition);
  }

  console.log('*** CompoentMap:');
  for (var i in componentMap) {
    console.log(i + ' = ' + componentMap[i])
  }

  for (var i=0; i<sequenceDiagramDefinition.components.length; i++) {
    console.log('Component: ' + sequenceDiagramDefinition.components[i]);
  }

  return sequenceDiagramDefinition;
}

function parseEdge(edgeString) {
  var regexp = /([A-z]*)-\[([A-z]*)\]>([A-z]*)/;
  var matches = edgeString.match(regexp);
  var edge = {};
  if (matches === null) {
    regexp = /([A-z]*)<\[([A-z]*)\]-([A-z]*)/;
    matches = edgeString.match(regexp);
    if (matches === null)
    {
      throw new Error('Invalid edge string: ' + edgeString);
    }
    edge.type = "response";
    edge.start = matches[3];
    edge.operation = matches[2];
    edge.end = matches[1];
  } else {
    edge.type = "request";
    edge.start = matches[1];
    edge.operation = matches[2];
    edge.end = matches[3];
  }

  return edge;
}

function generateProcessDetails(process) {
  var componentDetails = {
  };

  for (var c=0; c<process.component.length; c++) {
    var component = process.component[c];

    var componentDetail = {
      title: component.title,
      operation: {}
    }

    for (var o=0; o<component.operation.length; o++) {
      var operation = component.operation[o];
      componentDetail.operation[operation.name] = operation.title;
    }

    componentDetails[component.name] = componentDetail;
  }

  return componentDetails;
}


/**
 *  Convert a Sequence Diagram into a SVG,
 *      creating a SVG and HTML file in the diagrams directory.
 *
 *  sequenceDiagramDefinition:Object
 *      name:String
 *      title:String
 *      components:Array<String>
 *      edges:Array<Object>
 *          start:Number
 *          end:Number
 *          message:String
 */
function generateSequenceDiagram(sequenceDiagramDefinition) {

  // var classes = ["Class A", "Class B", "Class C", "Class D", "Class E", "Class F", "Class G"];
  // var messages = [{start: 0, end: 2, message: "From A to C"},
  //                 {start: 2, end: 3, message: "From C to D"},
  //                 {start: 3, end: 4, message: "From D to E"},
  //                 {start: 4, end: 3, message: "From E to D"},
  //                 {start: 3, end: 6, message: "From D to G"},
  //                 {start: 6, end: 3, message: "From G to D"},
  //                 {start: 3, end: 2, message: "From D to C"},
  //                 {start: 2, end: 0, message: "From C to A"}];

  // var sequenceDefinition = {
  //   components: sequenceDiagramDefinition,
  //   edges: messages
  // }

  console.log('Generating sequence diagram with definition: ' + JSON.stringify(sequenceDiagramDefinition));

  var svgFilename = 'diagrams/' + sequenceDiagramDefinition.name + '.svg';
  var htmlFilename = 'diagrams/' + sequenceDiagramDefinition.name + '.html';

  var svg = generateSVG(sequenceDiagramDefinition, function(svg) {
    //console.log('SVG: ' + svg);
    fs.writeFile(svgFilename, svg, function(err) {
      if(err) {
          return console.log(err);
      }

      console.log('Sequence Diagram filename: ' + svgFilename);
    });
    var html = '<html><body>' + svg + '</body></html>';
    fs.writeFile(htmlFilename, html, function(err) {
      if(err) {
          return console.log(err);
      }

      console.log('Sequence Diagram filename: ' + htmlFilename);
    });
  });
}

function generateSVG(sequenceDefinition, callback) {

  var classes = sequenceDefinition.components;
  var messages = sequenceDefinition.edges;

  jsdom.env(
    '<html><body><div id="drawArea"></div></body></html>',
    [ 'http://d3js.org/d3.v3.min.js' ],
    function (err, window) {


          var XPAD = 100;
          var YPAD = 20;
          var VERT_SPACE = 200;
          var VERT_PAD = 60;
          var CLASS_WIDTH = 180;
          var CLASS_HEIGHT = 40;
          var CLASS_LABEL_X_OFFSET = -50;
          var CLASS_LABEL_Y_OFFSET = 25;
          var MESSAGE_SPACE = 50;
          var MESSAGE_LABEL_X_OFFSET = -40;
          var MESSAGE_LABEL_Y_OFFSET = 70;
          var MESSAGE_ARROW_Y_OFFSET = 80;
          var CANVAS_WIDTH = 1200;
          var CANVAS_HEIGHT = 600;
          // Create an svg canvas
          var svg = window.d3.select("#drawArea")
            .append("svg")
            .attr("width", CANVAS_WIDTH)
            .attr("height", CANVAS_HEIGHT);

          // Draw vertical lines
          classes.forEach(function(c, i) {
            var line = svg.append("line")
              .style("stroke", "#888")
              .attr("x1", XPAD + i * VERT_SPACE)
              .attr("y1", YPAD)
              .attr("x2", XPAD + i * VERT_SPACE)
              .attr("y2", YPAD + VERT_PAD + messages.length * MESSAGE_SPACE);
          });

          // Draw classes
          classes.forEach(function(c, i) {
            var x = XPAD + i * VERT_SPACE;
            var g1 = svg.append("g")
              .attr("transform", "translate(" + x + "," + YPAD + ")")
              .attr("class", "first")
              .append("rect")
              .attr({x: -CLASS_WIDTH/2, y:0, width: CLASS_WIDTH, height: CLASS_HEIGHT})
              .style("fill", "#CCC");
          });
          // Draw class labels
          classes.forEach(function(c, i) {
            var x = XPAD + i * VERT_SPACE;
            var g1 = svg.append("g")
              .attr("transform", "translate(" + x + "," + YPAD + ")")
              .attr("class", "first")
              .append("text")
              .text(function (d) { return c; })
              .attr("dx", CLASS_LABEL_X_OFFSET)
              .attr("dy", CLASS_LABEL_Y_OFFSET);
          });
          // Draw message arrows
          messages.forEach(function(m, i) {
            var y = YPAD + MESSAGE_ARROW_Y_OFFSET + i * MESSAGE_SPACE;
            var line = svg.append("line")
              .style("stroke", m.color)
              .attr("stroke-width", 1)
              .attr("x1", XPAD + m.start * VERT_SPACE)
              .attr("y1", y)
              .attr("x2", XPAD + m.end * VERT_SPACE)
              .attr("y2", y)
              .attr("marker-end", "url(#end)")
              .append("text")
              .text(function (d) { return m.message; });
          });
          // Draw message labels
          messages.forEach(function(m, i) {
            var xPos = XPAD + MESSAGE_LABEL_X_OFFSET + (((m.end - m.start) * VERT_SPACE) / 2) + (m.start * VERT_SPACE);
            var yPos = YPAD + MESSAGE_LABEL_Y_OFFSET + i * MESSAGE_SPACE;

            var g1 = svg.append("g")
              .attr("transform", "translate(" + xPos + "," + yPos + ")")
              .attr("class", "first")
              .append("text")
              .text(function (d) { return m.message; });
          });
          // Arrow style
          svg.append("svg:defs").selectAll("marker")
              .data(["end"])
            .enter().append("svg:marker")
              .attr("id", String)
              .attr("stroke-width", 1)
              .attr("viewBox", "0 -5 10 10")
              .attr("refX", 10)
              .attr("refY", 0)
              .attr("markerWidth", 10)
              .attr("markerHeight", 10)
              .attr("orient", "auto")
            .append("svg:path")
              .attr("d", "M0,-5L10,0L0,5");

          callback('<svg width="' + CANVAS_WIDTH + '" height="' + CANVAS_HEIGHT + '">' + window.d3.select("body").select('svg').html() + '</svg>');
  // PRINT OUT:
      //console.log('<html><head><body><svg width="800" height=800>' + window.d3.select("body").select('svg').html() + '</svg></body></html>');
  //  fs.writeFileSync('out.svg', window.d3.select("body").html()); // or this
    });
  }
