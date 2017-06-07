/**
 * Created by Chudov A.V. on 6/5/2017.
 */
$(document).ready(function () {

    $("#date-from, #date-to").datepicker({
        maxDate: '-1',
        inline: true,
        dateFormat: "yy-mm-dd"
    }).datepicker("setDate", new Date());

    var table = $('#rates-table').dataTable({
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

//    chart creating
    var ctx = document.getElementById("rates-chart").getContext('2d');
    var ratesChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ["2017-05-02", "2017-05-04", "2017-05-05", "2017-05-08", "2017-05-09", "2017-05-10"],
            datasets: [{
                label: 'USD rates',
                data: [3.8675, 3.863, 3.849, 3.8391, 3.8741, 3.8777],
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
        var dateFrom = $("#date-from").val();
        var dateTo = $("#date-to").val();
        updateDB(dateFrom, dateTo);
    });
});

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
    $.each(JSON.parse(JsonData), function (index, data) {
        console.log("index: " + index + " data: " + data.date + "; " + data.rate);
    });

}
function updateTable(dateFrom, dateTo) {
    alert("table updated!");
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