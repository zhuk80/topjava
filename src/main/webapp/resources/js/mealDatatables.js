var ajaxUrl = 'ajax/admin/meals/';
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $('#datatable').DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});

/*$(document).ready(function () {

    $("#filter-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        //event.preventDefault();
        alert('Form submitted!');
        filter_submit();
        return false;

    });

});*/

/*$('#filter-form').on('submit', function () {
    //alert('Form submitted!');
    filter_submit();

    //return false;
});*/


function filter_submit() {

    alert('FFFF');
    var json = {}
    json["startDate"] = $("#startDate").val();
    json["startTime"] = $("#startTime").val();
    json["endDate"] = $("#endDate").val();
    json["endTime"] = $("#endTime").val();
    console.log(json);

    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : "ajax/admin/meals/filter",
        data : {
            startDate : "1",
            startTime : "2",
            endDate : "3",
            endTime : "4"
        },
        dataType : 'json',
        timeout : 100000,
        success : function(data) {
            console.log("SUCCESS: ", data);
            display(data);
        },
        error : function(e) {
            console.log("ERROR: ", e);
            display(e);
        },
        done : function(e) {
            console.log("DONE");
        }
    });
}

function display(data) {
    var json = "<h4>Ajax Response</h4><pre>"
        + JSON.stringify(data, null, 4) + "</pre>";
    $('#feedback').html(json);
}
