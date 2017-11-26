var CartEvent = function() {

};

CartEvent.prototype = function() {

    var addCartEvent = function($addToCartButton, eventType) {
        jQuery.ajax({
            type: "POST",
            url: '/add-cart-event',
            data: JSON.stringify(eventType),
            contentType: "application/json",
            success: function(result) {
                if (result === "created") {
                    $addToCartButton.css("display", "none").next().css("display", "block");
                    loadCartOnNavigation(jQuery);
                }
            },
            error: function(result) {}
        });
    };

    var removeLineItem = function($addToCartButton, eventType) {
        jQuery.ajax({
            type: "POST",
            url: '/add-cart-event',
            data: JSON.stringify(eventType),
            contentType: "application/json",
            success: function(result) {
                if (result === "created") {
                    window.location.href = "/get-cart?status=deleted";
                }
            },
            error: function(result) {}
        });
    };

    var updateLineItem = function($addToCartButton, eventType) {
        jQuery.ajax({
            type: "POST",
            url: '/add-cart-event',
            data: JSON.stringify(eventType),
            contentType: "application/json",
            success: function(result) {
                if (result === "created") {
                    window.location.href = "/get-cart?status=updated";
                }
            },
            error: function(result) {
            }
        });
    };

    return {
        addCartEvent: addCartEvent,
        removeLineItem: removeLineItem,
        updateLineItem: updateLineItem
    }
}();

(function($) {
    loadCartOnNavigation($);
    clickToAddToCart($);
    clickToRemoveToCart($);
    clickToUpdateToCart($);
    clickToChangeQuantity($);
    clickToDisplayLoginSection($);
    clickToDisplayCreateAccount($);
    clickToActiveButton($);
    clickToSearchFormAdvanced($);
    clickToChangeNextTab($);
})(jQuery);

function loadCartOnNavigation($) {
    jQuery.ajax({
        type: "GET",
        url: '/get-cart-ajax',
        success: function(result) {
            $('#cart-navigation').html(result);
        },
        error: function(result) {}
    });
}

function clickToAddToCart($) {
    var cartEvent = new CartEvent();
    $(document).on('click', '.ajax_add_to_cart, .single_add_to_cart_button:first()', function() {
        cartEvent.addCartEvent($(this), getEventType("ADD_ITEM", $(this).data("productId"), 1));
    });
}

function clickToRemoveToCart($) {
    var cartEvent = new CartEvent();
    $(document).on('click', '.remove-clothes-event', function() {
       cartEvent.removeLineItem($(this), getEventType("REMOVE_ITEM", $(this).data("productId"), $(this).parents('tr.cart_item').find('input.qty').val()));
    });
}

function clickToUpdateToCart($) {
    var cartEvent = new CartEvent();
    $(document).on('click', '.edit_product', function() {
        var newQuantity = Number.parseInt($(this).parents('tr.cart_item').find('input.qty').val());
        var lastQuantity = Number.parseInt($(this).parents('tr.cart_item').find('input.qty').data("quantity"));
        var delta = newQuantity - lastQuantity;
        var type = (delta > 0) ? "ADD_ITEM" : "REMOVE_ITEM";
        delta = Math.abs(delta);
        if (delta !== 0) {
            cartEvent.updateLineItem($(this), getEventType(type, $(this).data("productId"), delta));
        } else {
            return false;
        }

    });
}

function getEventType(cartEventType, clothesId, quantity) {
    return {
        cartEventType: cartEventType,
        clothesId: clothesId,
        quantity: quantity
    };
}

function clickToChangeQuantity($) {
    var newQuantities;
    var $quantities;
    $('.table_wrap').on('click', '.minus, .plus', function() {
        $quantities = $(this).siblings('.qty.text');
        newQuantities = Number.parseInt($quantities.val());
        if ($(this).hasClass('minus')) {
            if (newQuantities > 1) {
                $quantities.val(newQuantities - 1);
            }
        } else if ($(this).hasClass('plus')) {
            $quantities.val(newQuantities + 1);
        }
    });

    $('.table_wrap').on('change', '.qty.text', function() {
        if (Number($(this).val()) < 1) {
            $(this).val(1);
        }
    });

}

function clickToDisplayLoginSection($) {
    $('.section_login.checkout-column').on('click', '.d_table.w_full.m_bottom_5.second_font', function () {
        $(this).next().next().slideToggle();
    });
}

function clickToDisplayCreateAccount($) {
    $('.form-row.create-account.woocommerce-validated').on('click', function () {
        $(this).next().slideToggle();
        return false;
    });
}

function clickToActiveButton($) {
    $('#search-advanced-form').on('click', '#color-button>li>a, #material-button>li>a', function() {
        $(this).toggleClass('button-active');
    });
}

function clickToSearchFormAdvanced($) {
    $('#search-advanced-button').on('click', function (e) {
        e.preventDefault();
        var sku = $('#sku-input').val();
        var manufacture = $('#manufacture-input').val();
        var priceFrom = Number.parseInt($('#price-from-option').text().replace(/\D/g, ''));
        var priceTo = Number.parseInt($('#price-to-option').text().replace(/\D/g, ''));
        var size = $('#size-option').text().replace("Please select", "");
        var colors = [];
        var materials = [];

        $('#color-button>li>a').each(function() {
           if ($(this).hasClass('button-active')) {
               colors.push($(this).text());
           }
        });

        $('#material-button>li>a').each(function() {
            if ($(this).hasClass('button-active')) {
                materials.push($(this).text());
            }
        });

        if(isNaN(priceFrom)) {
            priceFrom = 0;
        }

        if(isNaN(priceTo)) {
            priceTo = 0;
        }

        var clothes = {
            sku: sku,
            manufacture: manufacture,
            priceFrom: priceFrom,
            priceTo: priceTo,
            size: size,
            colors: colors,
            materials: materials
        };

        jQuery.ajax({
            type: 'POST',
            url: '/advanced-search',
            data: JSON.stringify(clothes),
            contentType: "application/json; charset=utf-8",
            success: function(result) {
                $('#product-list-result').html(result);
            },
            error: function(result) {}
        });

    });
}

function clickToChangeNextTab($) {
    $('#account-section').on('click', '.vc_tta-tabs-container .vc_tta-tab', function() {
        $('.vc_tta-tabs-container .vc_tta-tab, .vc_tta-panels-container .vc_tta-panel').removeClass('vc_active');

        var index = Number.parseInt($(this).data('vcTab')) - 1;

        $(this).addClass('vc_active');
        $('.vc_tta-panels-container .vc_tta-panel:eq('+index+')').addClass('vc_active');
    });
}

