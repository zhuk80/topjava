var ajaxUrl = 'ajax/meals/';
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

function filterSubmit() {

        var form = $('#filterForm');
        //alert(form.serialize());
        $.ajax({
            type: "POST",
            url: "ajax/meals/filter",
            data: form.serialize(),
            success: function (data) {

                //var table = $('#datatable').DataTable();
                datatableApi.clear();

               for (var i = 0; i < data.length; i++) {
                   datatableApi.row.add ( {
                       "dateTime" : data[i].dateTime,
                       "description" : data[i].description,
                       "calories" : data[i].calories,
                       "exceed" : data[i].exceed,
                       "id" : data[i].id
                   })

                }
                datatableApi.draw();

                console.log("SUCCESS: ", data);
                display(data);
            }
        });
}

function display(data) {
    var json = "<h4>Ajax Response</h4><pre>"
        + JSON.stringify(data, null, 4) + "</pre>";
    $('#feedback').html(json);
}

function filterClear() {

    //alert(document.getElementById("filterForm").files.length == 0);
    document.getElementById("filterForm").reset();
    //alert(document.getElementById("filterForm").files.length == 0);
    var form = "startDate=&endDate=&startTime=&endTime=";
    $.ajax({
        type: "POST",
        url: "ajax/meals/filter",
        data: form,
        success: function (data) {

            /*var json = JSON.stringify(data);
             console.log(json);*/

            //var table = $('#datatable').DataTable();
            table.clear();

            for (var i = 0; i < data.length; i++) {
                datatableApi.row.add({
                    "dateTime": data[i].dateTime,
                    "description": data[i].description,
                    "calories": data[i].calories,
                    "exceed": data[i].exceed,
                    "id": data[i].id
                })

            }
            datatableApi.draw();

            console.log("SUCCESS: ", data);
            display(data);
        }
    });
}

function filterCheck () {
    var form = $('#filterForm');
    alert(form.serialize());
}
