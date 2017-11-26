
(function ($) {


$('body').on('added_to_wishlist removed_from_wishlist', function () {
			var wishlist_button = $('.wishlist_button'),
				wishlist_link = $('.wishlist_button sup'),
				data = {
					action: 'cosyone_add_count_products'
				}

			$.ajax({
				type: 'POST',
				url: yith_wcwl_l10n.ajax_url,
				data: data,
				beforeSend: function(){ },
				complete: function(){ },
				success: function( response ) {
					response = parseInt(response.slice(0, response.length - 1));
					
					if( isNaN(response) ) {
						response = 0;
					}
					
					
					if (wishlist_button) {
						wishlist_button.attr('data-amount', response);
					}

					if (wishlist_link) {
						wishlist_link.html(response);
					}

				}

			});

});

})(jQuery);