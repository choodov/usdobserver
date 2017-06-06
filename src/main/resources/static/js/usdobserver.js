/**
 * Created by wonder on 6/5/2017.
 */
$(document).ready(function () {

    $("#date-from, #date-to").datepicker({
        inline: true,
        dateFormat: "yy-mm-dd"
    }).datepicker("setDate", new Date());

    var table = $('#rates-table').dataTable({
        "autoWidth": false,
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
            url: "/api/rates/getExcel",
            cache: false,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(table.fnGetData()),
            complete: function (Data) {
                saveXLS(Data);
            }
        });
    });

    function saveXLS(byte) {
        var file = new Blob([byte], {type: "application/vnd.ms-excel"});
        var link = document.createElement('a');
        var myURL = window.URL || window.webkitURL
        link.href = myURL.createObjectURL(file);
        var fileName = "USD_rates.xls";
        link.download = fileName;
        link.click();
    }

    $('#download-rates').on('click', function () {
        alert("downloading rates...")
    });
});