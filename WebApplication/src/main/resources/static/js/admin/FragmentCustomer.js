var FragmentCustomer = function () {
};

FragmentCustomer.prototype = {
    bindEventShowCustomerTobutton: function () {
        var $this = this;
        var $deleteCustomerSubmit = $("#delete-customer-button-submit");
        $("#customer-table").DataTable();

        $deleteCustomerSubmit.click(function () {
            $this.submitDeleteCustomer();
         });
    },

    submitDeleteCustomer: function () {
        $.ajax({
            url: "/admin/ajax/delete-customer",
            data: {
                id: $("#customer-delete-id-holder").val()
            },
            type: "POST",
            async: false,
            success: function (data) {
                $("#deleteCustomerModal").modal("toggle");
                AdminUlti.changePage("/admin/customer", "page-content");
                $("#customer-table").DataTable();
            }
        });
    }
};

var deleteCustomer = function (id) {
    var $customerIdHolder = $("#customer-delete-id-holder");
    $customerIdHolder.val(id);
};
