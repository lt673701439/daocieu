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
	<script src="<%=basePath%>/static/js/highcharts/heatmap.js"></script>
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
	    <div id="container" style="min-width: 310px; max-width: 800; height: 400px; margin: 0 auto;"></div>
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
        type: 'heatmap',
        marginTop: 40,
        marginBottom: 80,
        plotBorderWidth: 1
    },


    title: {
        text: 'Sales per employee per weekday'
    },

    xAxis: {
        categories: ['Alexander', 'Marie', 'Maximilian', 'Sophia', 'Lukas', 'Maria', 'Leon', 'Anna', 'Tim', 'Laura']
    },

    yAxis: {
        categories: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'],
        title: null
    },

    colorAxis: {
        min: 0,
        minColor: '#FFFFFF',
        maxColor: Highcharts.getOptions().colors[0]
    },

    legend: {
        align: 'right',
        layout: 'vertical',
        margin: 0,
        verticalAlign: 'top',
        y: 25,
        symbolHeight: 280
    },

    tooltip: {
        formatter: function () {
            return '<b>' + this.series.xAxis.categories[this.point.x] + '</b> sold <br><b>' +
                this.point.value + '</b> items on <br><b>' + this.series.yAxis.categories[this.point.y] + '</b>';
        }
    },

    series: [{
        name: 'Sales per employee',
        borderWidth: 1,
        data: [[0, 0, 10], [0, 1, 19], [0, 2, 8], [0, 3, 24], [0, 4, 67], [1, 0, 92], [1, 1, 58], [1, 2, 78], [1, 3, 117], [1, 4, 48], [2, 0, 35], [2, 1, 15], [2, 2, 123], [2, 3, 64], [2, 4, 52], [3, 0, 72], [3, 1, 132], [3, 2, 114], [3, 3, 19], [3, 4, 16], [4, 0, 38], [4, 1, 5], [4, 2, 8], [4, 3, 117], [4, 4, 115], [5, 0, 88], [5, 1, 32], [5, 2, 12], [5, 3, 6], [5, 4, 120], [6, 0, 13], [6, 1, 44], [6, 2, 88], [6, 3, 98], [6, 4, 96], [7, 0, 31], [7, 1, 1], [7, 2, 82], [7, 3, 32], [7, 4, 30], [8, 0, 85], [8, 1, 97], [8, 2, 123], [8, 3, 64], [8, 4, 84], [9, 0, 47], [9, 1, 114], [9, 2, 31], [9, 3, 48], [9, 4, 91]],
        dataLabels: {
            enabled: true,
            color: '#000000'
        }
    }]
});

function showChart(flag){
    window.location.href='${rootPath}/testChart?flag='+flag;
}
</script>

</body>
</html>
