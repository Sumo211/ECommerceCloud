var FragmentProduct = function () {
};

FragmentProduct.prototype = {
    bindEventShowProductTobutton: function () {
        var $this = this;
        var $addNewProductButton = $("#addNewProductButton");
        var $buttonAddProductSubmit = $("#add-product-button");
        var $editProductSubmit = $("#edit-category-button-submit");
        var $deleteProductSubmit = $("#delete-category-button-submit");
        $("#clothes-table").DataTable();
        $buttonAddProductSubmit.click(function () {
            $this.addNewProduct();
        });
        $editProductSubmit.click(function () {
            $this.submitEditProduct();
        });
        $deleteProductSubmit.click(function () {
            $this.submitDeleteCategory();
        });
        $addNewProductButton.click(function(){
            $this.bindCategory();
        });
    },
    bindCategory: function(){
        var $this = this;
        $.ajax({
            url: "/admin/ajax/get-all-category",
            type: "GET",
            async: false,
            success: function (data) {
                var dropdown = $("#product-category-dropdown-list");
                dropdown.empty();
                for(var i = 0; i<data.length;i++){
                    dropdown.append("<option>"+data[i].name+"</option>");
                }
            }
        });
    },
    addNewProduct: function () {
        var $this = this;
        var $productName = $("#product-name");
        var $productDescription = $("#product-description");
        var $productCategory = $("#product-category-dropdown-list");
        var $productColor = $("#product-color");
        var $productSize = $("#product-size");
        var $productMaterial = $("#product-material");
        var $productPrice = $("#product-price");
        var $productDiscount = $("#product-discount");
        var $productManufacture = $("#product-manufacture");
        var $productImage = $("#product-image");
        $.ajax({
            url: "/admin/ajax/add-new-product",
            data: {
                name: $productName.val(),
                description: $productDescription.val(),
                image: $productImage.val(),
                price: $productPrice.val() == "" ? 0 : $productPrice.val() ,
                manufacture: $productManufacture.val(),
                size: $productSize.val(),
                color: $productColor.val(),
                material: $productMaterial.val(),
                discount: $productDiscount.val() == ""? 0 :  $productDiscount.val() ,
                categoryId: $productCategory.val(),
            },
            type: "POST",
            async: false,
            success: function (data) {
                $("#addProductModal").modal("toggle");
                AdminUlti.changePage("/admin/product", "page-content");
            }
        });
    },

    submitEditProduct: function () {
        $.ajax({
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
        });
    },

    submitDeleteProduct: function () {
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
            }
        });
    }
};

var editClothes = function (id) {
    var $productName = $("#product-name");
    var $productDescription = $("#product-description");
    var $productCategory = $("#product-category-dropdown-list");
    var $productColor = $("#product-color");
    var $productSize = $("#product-size");
    var $productMaterial = $("#product-material");
    var $productPrice = $("#product-price");
    var $productDiscount = $("#product-discount");
    var $productManufacture = $("#product-manufacture");
    var $productImage = $("#product-image");
};

var deleteProduct = function (id) {
    var $categoryIdHolder = $("#category-delete-id-holder");
    $categoryIdHolder.val(id);
};