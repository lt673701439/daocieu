<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<%@ include file="/include.jsp"%>
	<script src="<%=basePath%>/static/js/highcharts/highcharts.js"></script>
	<script src="<%=basePath%>/static/js/highcharts/highcharts-more.js"></script>
	<script src="<%=basePath%>/static/js/highcharts/exporting.js"></script>
    <title></title>
</head>
<body>
<div>
	<br>
	<form id="dataForm">
		<div id="dlg-buttons" style="margin-left: 140px;">
       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-color_wheel" id="chart1" onclick="showChart(1)" style="width:90px">环状图</a>
       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-color_wheel" id="chart2" onclick="showChart(2)" style="width:90px">点状图</a>
       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-color_wheel" id="chart3" onclick="showChart(3)" style="width:90px">折线图</a>
       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-color_wheel" id="chart4" onclick="showChart(4)" style="width:90px">柱状图</a>
       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-color_wheel" id="chart5" onclick="showChart(5)" style="width:90px">散点图</a>
       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-color_wheel" id="chart6" onclick="showChart(6)" style="width:90px">仪表盘</a>
       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-color_wheel" id="chart7" onclick="showChart(7)" style="width:90px">热力图</a>
   		</div>
	    <div id="container" style="min-width: 310px; max-width: 400; height: 400px; margin: 0 auto;"></div>
	</form>
	<br>
</div>
    
<script type="text/javascript">

jQuery(function(){ 
// 	var url='${rootPath}/confCustom/getNewOne';
// 	$('#dataForm').form('load', url);
});

Highcharts.chart('container', {

    chart: {
        type: 'gauge',
        plotBackgroundColor: null,
        plotBackgroundImage: null,
        plotBorderWidth: 0,
        plotShadow: false
    },

    title: {
        text: 'Speedometer'
    },

    pane: {
        startAngle: -150,
        endAngle: 150,
        background: [{
            backgroundColor: {
                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                stops: [
                    [0, '#FFF'],
                    [1, '#333']
                ]
            },
            borderWidth: 0,
            outerRadius: '109%'
        }, {
            backgroundColor: {
                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                stops: [
                    [0, '#333'],
                    [1, '#FFF']
                ]
            },
            borderWidth: 1,
            outerRadius: '107%'
        }, {
            // default background
        }, {
            backgroundColor: '#DDD',
            borderWidth: 0,
            outerRadius: '105%',
            innerRadius: '103%'
        }]
    },

    // the value axis
    yAxis: {
        min: 0,
        max: 200,

        minorTickInterval: 'auto',
        minorTickWidth: 1,
        minorTickLength: 10,
        minorTickPosition: 'inside',
        minorTickColor: '#666',

        tickPixelInterval: 30,
        tickWidth: 2,
        tickPosition: 'inside',
        tickLength: 10,
        tickColor: '#666',
        labels: {
            step: 2,
            rotation: 'auto'
        },
        title: {
            text: 'km/h'
        },
        plotBands: [{
            from: 0,
            to: 120,
            color: '#55BF3B' // green
        }, {
            from: 120,
            to: 160,
            color: '#DDDF0D' // yellow
        }, {
            from: 160,
            to: 200,
            color: '#DF5353' // red
        }]
    },

    series: [{
        name: 'Speed',
        data: [80],
        tooltip: {
            valueSuffix: ' km/h'
        }
    }]

},
// Add some life
function (chart) {
    if (!chart.renderer.forExport) {
        setInterval(function () {
            var point = chart.series[0].points[0],
                newVal,
                inc = Math.round((Math.random() - 0.5) * 20);

            newVal = point.y + inc;
            if (newVal < 0 || newVal > 200) {
                newVal = point.y - inc;
            }

            point.update(newVal);
        }, 3000);
    }
});
     

function showChart(flag){
    window.location.href='${rootPath}/testChart?flag='+flag;
}
</script>

</body>
</html>
