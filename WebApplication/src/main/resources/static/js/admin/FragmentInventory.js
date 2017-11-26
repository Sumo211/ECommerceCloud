var FragmentInventory = function () {
};

FragmentInventory.prototype = {
    bindEventShowInventoryTobutton: function () {
        var $this = this;
        /*  var $buttonAddCategory = $("#add-category-button");
         var $editCategorySubmit = $("#edit-category-button-submit");
         var $deleteCategorySubmit = $("#delete-category-button-submit");*/
        $("#inventory-table").DataTable();
        /* $buttonAddCategory.click(function () {
         $this.addNewCategory();
         });
         $editCategorySubmit.click(function () {
         $this.submitEditCategory();
         });
         $deleteCategorySubmit.click(function () {
         $this.submitDeleteCategory();
         })*/
    },

    addNewCategory: function () {
       /* var $this = this;
        var $categoryName = $("#category-name");
        var $categoryDescription = $("#category-description");
        $.ajax({
            url: "/admin/ajax/add-new-category",
            data: {
                name: $categoryName.val(),
                description: $categoryDescription.val()
            },
            type: "POST",
            async: false,
            success: function (data) {
                $("#addCategoryModal").modal("toggle");
                AdminUlti.changePage("/admin/category", "page-content");
            }
        });*/
    },

    submitEditCategory: function () {
        /*$.ajax({
            url: "/admin/ajax/edit-category",
            data: {
                code: $("#category-edit-id-holder").val(),
                name: $("#edit-category-name").val(),
                description: $("#edit-category-description").val()
            },
            type: "POST",
            async: false,
            success: function (data) {
                $("#editCategoryModal").modal("toggle");
                AdminUlti.changePage("/admin/category", "page-content");
            }
        });*/
    },

    submitDeleteCategory: function () {
        /*$.ajax({
            url: "/admin/ajax/delete-category",
            data: {
                id: $("#category-delete-id-holder").val()
            },
            type: "POST",
            async: false,
            success: function (data) {
                $("#deleteCategoryModal").modal("toggle");
                AdminUlti.changePage("/admin/category", "page-content");
            }
        });*/
    }
};

/*var editCategory = function (id) {
    var $categoryName = $("#edit-category-name");
    var $categoryDescription = $("#edit-category-description");
    var $categoryIdHolder = $("#category-edit-id-holder");
    $categoryName.val($("#name" + id).html());
    $categoryDescription.val($("#desc" + id).html());
    $categoryIdHolder.val(id);
}*/


/*var deleteCategory = function (id) {
    var $categoryIdHolder = $("#category-delete-id-holder");
    $categoryIdHolder.val(id);
}*/
