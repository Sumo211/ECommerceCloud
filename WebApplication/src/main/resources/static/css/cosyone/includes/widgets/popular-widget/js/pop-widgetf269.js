(function (j) {

	if (typeof popwid == "undefined") return;

	j.post(cosyone_global.ajaxurl, {
		postid: popwid.postid,
		action: "popwid_page_view_count"
	});

})(jQuery);
