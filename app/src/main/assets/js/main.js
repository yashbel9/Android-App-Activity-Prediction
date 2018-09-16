var plotGraph = function(traces) {
	var graphDiv = 'graph';
	Plotly.purge(graphDiv);
	var colors = ['red', 'blue', 'yellow'];
	var legendLabels = ['Walk', 'Run', 'Jump']
	var plotData = [];
	traces.forEach(function(trace, i) {
		plotData.push({
			name: legendLabels[i],
			type: 'scatter3d',
			mode: 'lines+markers',
			x: trace['x'],
			y: trace['y'],
			z: trace['z'],
			line: {
				width: 5,
				color: colors[i]
			},
			marker: {
				size: 3.5,
				color: trace['c'],
				colorscale: "Greens",
				cmin: -20,
				cmax: 50
			}
		});
	});
	var layout = {
        showlegend: true,
        legend: {"orientation": "h"}
    };

	Plotly.plot(graphDiv, plotData, layout);
};

var parseData = function(traces, data, selectedPlots) {

	var colors = ['red', 'blue', 'yellow'];
	var x, y, z, c;
	data.forEach(function(dataEl) {
		x = [];
		y = [];
		z = [];
		c = [];

		dataEl.forEach(function(el) {
			if (selectedPlots.includes(el['c'])) {
				x.push(el['x']);
				y.push(el['y']);
				z.push(el['z']);
				c.push(el['c']);
			}
		});

		traces.push({
			"x": x,
			"y": y,
			"z": z,
			"c": c
		});
	});

};

$(function() {
	var data = Android.getData();

	data = JSON.parse(data);

	traces = [];

	// 0 = run, 1 = walk, 2 = jump
	var selectedPlots = [0, 1, 2];

	$('.chart-control').on('change', function(event) {
		var $el = $(this);
		var val = parseInt($el.val());

		if ($el.prop('checked') == true) {
			if (!selectedPlots.includes(val)) {
				selectedPlots.push(val);
			}
		} else {
			if (selectedPlots.includes(val)) {
				selectedPlots.splice(selectedPlots.indexOf(val), 1);
			}
		}
		traces = [];
		parseData(traces, data, selectedPlots);
		plotGraph(traces);
	});

	parseData(traces, data, selectedPlots);
	plotGraph(traces);
});
