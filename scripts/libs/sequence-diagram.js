
(function(exports) {
  var jsdom = require('jsdom');

  /**
   * Parameters:
   *    sequenceDefinition:Object
   *      components: Array<String>
   *      edges: Array<Object>
   *        start: Number
   *        end: Number
   *        message: String
   *        type: Enum<request,response>
   *    callback: function
   *      svg: String
   * Return: Void
   * Throws: None
   */
  exports.generateSVG = function(sequenceDefinition, callback) {

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
})(exports)
