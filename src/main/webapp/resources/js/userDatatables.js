var ajaxUrl = 'ajax/admin/users/';
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $('#datatable').DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
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

    $(".chcktbl1").click(function () {

        var table = $('#datatable').DataTable();
        var id = $(this).attr("id");
        var isChecked = $(this).is(":checked");

        $.ajax({
            type: "Post",
            url: "ajax/admin/users/setEnabled",
            data: {
                id : id,
                isEnabled : isChecked
            },
            success: function (response) {
                updateTable();
                if (response != 0) {
                    alert("Data Update Successfully!!!!");
                    location.reload();
                }
            },
            error: function (response) {
                if (response != 1) {
                    alert("Error!!!!");
                }
            }
        });
    });

});

