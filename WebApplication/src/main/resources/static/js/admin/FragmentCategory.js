var FragmentCategory = function () {
};

FragmentCategory.prototype = {
    bindEventShowCategoryTobutton: function () {
        var $this = this;
        var $buttonAddCategory = $("#add-category-button");
        var $editCategorySubmit = $("#edit-category-button-submit");
        var $deleteCategorySubmit = $("#delete-category-button-submit");
        $("#category-table").DataTable();
        $buttonAddCategory.click(function () {
            $this.addNewCategory();
        });
        $editCategorySubmit.click(function () {
            $this.submitEditCategory();
        });
        $deleteCategorySubmit.click(function () {
            $this.submitDeleteCategory();
        });
        
    },

    addNewCategory: function () {
        var $this = this;
        var $categoryName = $("#category-name");
        var $categoryDescription = $("#category-description");
        var $categoryImage = $("#category-image");
        $.ajax({
            url: "/admin/ajax/add-new-category",
            data: {
                name: $categoryName.val(),
                description: $categoryDescription.val(),
                image: $categoryImage.val()
            },
            type: "POST",
            async: false,
            success: function (data) {
                $("#addCategoryModal").modal("toggle");
                AdminUlti.changePage("/admin/category", "page-content");
                $("#category-table").DataTable();
            }
        });
    },

    submitEditCategory: function () {
        $.ajax({
            url: "/admin/ajax/edit-category",
            data: {
                code: $("#category-edit-id-holder").val(),
                name: $("#edit-category-name").val(),
                description: $("#edit-category-description").val(),
                image: $("#edit-category-image").val()
            },
            type: "POST",
            async: false,
            success: function (data) {
                $("#editCategoryModal").modal("toggle");
                AdminUlti.changePage("/admin/category", "page-content");
                $("#category-table").DataTable();
            }
        });
    },

    submitDeleteCategory: function () {
        $.ajax({
            url: "/admin/ajax/delete-category",
            data: {
                id: $("#category-delete-id-holder").val()
            },
            type: "POST",
            async: false,
            success: function (data) {
                $("#deleteCategoryModal").modal("toggle");
                AdminUlti.changePage("/admin/category", "page-content");
                $("#category-table").DataTable();
            }
        });
    }
};

var editCategory = function (id) {
    console.log("asdasd");
    var $categoryName = $("#edit-category-name");
    var $categoryDescription = $("#edit-category-description");
    var $categoryImage = $("#edit-category-image");
    var $categoryIdHolder = $("#category-edit-id-holder");
    $categoryName.val($("#name" + id).html());
    $categoryDescription.val($("#desc" + id).html());
    $categoryImage.val($("#image" + id).html())
    $categoryIdHolder.val(id);
};

var deleteCategory = function (id) {
    var $categoryIdHolder = $("#category-delete-id-holder");
    $categoryIdHolder.val(id);
};