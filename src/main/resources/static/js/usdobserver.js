/**
 * Created by Chudov A.V. on 6/5/2017.
 */

var ratesChart;
var table;

$(document).ready(function () {

    var oneWeekAgo = new Date();
    oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);

    $("#date-from").datepicker({
        maxDate: '-2',
        inline: true,
        dateFormat: "yy-mm-dd"
    }).datepicker("setDate", oneWeekAgo);

    $("#date-to").datepicker({
        maxDate: '-1',
        inline: true,
        dateFormat: "yy-mm-dd"
    }).datepicker("setDate", new Date());

    var dateFrom = getDateFrom();
    var dateTo = getDateTo();

    table = $('#rates-table').dataTable({
        "autoWidth": false,
        "order": [[ 0, "desc" ]],
        "lengthMenu": [[10, 25, 50, 100, 1000], [10, 25, 50, 100, "All"]],
        columns: [
            {"data": "date"},
            {"data": "rate"}
        ],
        "bProcessing": true,
        bServerSide: true,
        sAjaxSource: "/api/rates/getAjax",
        sServerMethod: "POST"
    });

    var ctx = document.getElementById("rates-chart").getContext('2d');
    ratesChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: [],
            datasets: [{
                label: 'USD rates',
                data: [],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderColor: [
                    'rgba(255,99,132,1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            },
            maintainAspectRatio: false
        }
    });

    $('#download-excel').on('click', function () {
        $.ajax({
            type: "POST",
            url: "/api/rates/generateExcel",
            cache: false,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(table.fnGetData()),
            complete: function (Data) {
                window.open("/api/rates/getExcel?fileName=" + Data.responseText, '_blank');
            }
        });
    });

    $('#download-rates').on('click', function () {
        updateDB(getDateFrom(), getDateTo());
    });

    $("#date-from, #date-to").on('change', function(){
        updateChart(getDateFrom(), getDateTo());
    });

    updateDB(dateFrom, dateTo);
    updateChart(dateFrom, dateTo);
});

function getDateFrom(){
    return $("#date-from").val();
}

function getDateTo(){
    return $("#date-to").val();
}

function getRatesByPeriod(dateFrom, dateTo) {
    return $.ajax({
        type: "GET",
        async: false,
        dataType: "json",
        url: "/api/rates/getRatesByPeriod?from=" + dateFrom + "&to=" + dateTo,
        success: function (JsonData) {
            return JsonData;
        }
    }).responseText;
}

function updateChart(dateFrom, dateTo) {
    var JsonData = getRatesByPeriod(dateFrom, dateTo);
    var newChartData = [];
    var newChartLabels = [];

    $.each(JSON.parse(JsonData), function (index, data) {
        newChartLabels.push(data.date);
        newChartData.push(data.rate);
    });

    ratesChart.config.data.labels = newChartLabels;
    ratesChart.config.data.datasets[0].data = newChartData;
    ratesChart.update();
}
function updateTable(dateFrom, dateTo) {
}

function updateDB(dateFrom, dateTo) {
    $.ajax({
        type: "POST",
        url: "/api/rates/updateDB?from=" + dateFrom + "&to=" + dateTo,
        success: function () {
            updateChart(dateFrom, dateTo);
            updateTable(dateFrom, dateTo);
        }
    });
}