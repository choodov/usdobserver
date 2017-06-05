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

});