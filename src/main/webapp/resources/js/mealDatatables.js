var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize(),
        success: updateTableByData
    });
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

/*$(document).ready(function(){

});*/

$(function () {
    $('#datetimepicker1').datetimepicker({
        timepicker:false,
        format:'Y-m-d'
    });
    $('#datetimepicker2').datetimepicker({
        timepicker:false,
        format:'Y-m-d'
    });
    $('#datetimepicker3').datetimepicker({
        datepicker:false,
        format:'H:i'
    });
    $('#datetimepicker4').datetimepicker({
        datepicker:false,
        format:'H:i'
    });
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (data, type, row) {
                    if (type === 'display') {
                        return data.substring(0, 16).replace("T", " ");
                    }
                    return data;
                }

            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            if (data.exceed) {
                $(row).addClass("exceeded");
            } else {
                $(row).addClass("normal");
            }
        },
        "initComplete": makeEditable
    });
    //makeEditable();
});
