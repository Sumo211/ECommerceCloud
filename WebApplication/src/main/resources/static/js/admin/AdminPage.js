var adminPage;

$(function () {
    adminPage = new AdminPage();
});

var AdminPage = function () {

    this.category = new FragmentCategory();
    this.product = new FragmentProduct();
    this.customer = new FragmentCustomer();
    this.transaction = new FragmentTransaction();
    this.inventory = new FragmentInventory();
    this.bindEventToMenu();
}

AdminPage.prototype = {

    bindEventToMenu: function () {
        var $this = this;
        $("#dashboard-link").click(function () {
            $this.changeToDashboard();
            demo.initChartist();
        });
        $("#category-link").click(function () {
            $this.changeToCategory();
        });
        $("#product-link").click(function () {
            $this.changeToProduct();
        });
        $("#customer-link").click(function () {
            $this.changeToCustomer();
        });
        $("#transaction-link").click(function () {
            $this.changeToTransaction();
        });
        $("#inventory-link").click(function () {
            $this.changeToInventory();
        });
    },

    changeToDashboard: function () {
        AdminUlti.changePage("/admin/dashboard", "page-content");
    },

    changeToCategory: function () {
        AdminUlti.changePage("/admin/category", "page-content");
        this.category.bindEventShowCategoryTobutton();
    },

    changeToProduct: function () {
        AdminUlti.changePage("/admin/product", "page-content");
        this.product.bindEventShowProductTobutton();
    },
    changeToCustomer: function () {
        AdminUlti.changePage("/admin/customer", "page-content");
        this.customer.bindEventShowCustomerTobutton();
    },
    changeToTransaction: function () {
        AdminUlti.changePage("/admin/transaction", "page-content");
        this.transaction.bindEventShowTransactionTobutton();
    },
    changeToInventory: function () {
        AdminUlti.changePage("/admin/inventory", "page-content");
        this.inventory.bindEventShowInventoryTobutton();
    }
};