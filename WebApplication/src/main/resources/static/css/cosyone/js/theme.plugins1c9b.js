/**
 * jQuery CSS Customizable Scrollbar
 *
 * Copyright 2014, Yuriy Khabarov
 * Dual licensed under the MIT or GPL Version 2 licenses.
 *
 * If you found bug, please contact me via email <13real008@gmail.com>
 *
 * @author Yuriy Khabarov aka Gromo
 * @version 0.2.6
 * @url https://github.com/gromo/jquery.scrollbar/
 *
 */
!function(l,e){"function"==typeof define&&define.amd?define(["jquery"],e):e(l.jQuery)}(this,function(l){"use strict";function e(e){if(t.webkit&&!e)return{height:0,width:0};if(!t.data.outer){var o={border:"none","box-sizing":"content-box",height:"200px",margin:"0",padding:"0",width:"200px"};t.data.inner=l("<div>").css(l.extend({},o)),t.data.outer=l("<div>").css(l.extend({left:"-1000px",overflow:"scroll",position:"absolute",top:"-1000px"},o)).append(t.data.inner).appendTo("body")}return t.data.outer.scrollLeft(1e3).scrollTop(1e3),{height:Math.ceil(t.data.outer.offset().top-t.data.inner.offset().top||0),width:Math.ceil(t.data.outer.offset().left-t.data.inner.offset().left||0)}}function o(){var l=e(!0);return!(l.height||l.width)}function s(l){var e=l.originalEvent;return e.axis&&e.axis===e.HORIZONTAL_AXIS?!1:e.wheelDeltaX?!1:!0}var r=!1,t={data:{index:0,name:"scrollbar"},macosx:/mac/i.test(navigator.platform),mobile:/android|webos|iphone|ipad|ipod|blackberry/i.test(navigator.userAgent),overlay:null,scroll:null,scrolls:[],webkit:/webkit/i.test(navigator.userAgent)&&!/edge\/\d+/i.test(navigator.userAgent)};t.scrolls.add=function(l){this.remove(l).push(l)},t.scrolls.remove=function(e){for(;l.inArray(e,this)>=0;)this.splice(l.inArray(e,this),1);return this};var i={autoScrollSize:!0,autoUpdate:!0,debug:!1,disableBodyScroll:!1,duration:200,ignoreMobile:!1,ignoreOverlay:!1,scrollStep:30,showArrows:!1,stepScrolling:!0,scrollx:null,scrolly:null,onDestroy:null,onInit:null,onScroll:null,onUpdate:null},n=function(s){t.scroll||(t.overlay=o(),t.scroll=e(),a(),l(window).resize(function(){var l=!1;if(t.scroll&&(t.scroll.height||t.scroll.width)){var o=e();(o.height!==t.scroll.height||o.width!==t.scroll.width)&&(t.scroll=o,l=!0)}a(l)})),this.container=s,this.namespace=".scrollbar_"+t.data.index++,this.options=l.extend({},i,window.jQueryScrollbarOptions||{}),this.scrollTo=null,this.scrollx={},this.scrolly={},s.data(t.data.name,this),t.scrolls.add(this)};n.prototype={destroy:function(){if(this.wrapper){this.container.removeData(t.data.name),t.scrolls.remove(this);var e=this.container.scrollLeft(),o=this.container.scrollTop();this.container.insertBefore(this.wrapper).css({height:"",margin:"","max-height":""}).removeClass("scroll-content scroll-scrollx_visible scroll-scrolly_visible").off(this.namespace).scrollLeft(e).scrollTop(o),this.scrollx.scroll.removeClass("scroll-scrollx_visible").find("div").andSelf().off(this.namespace),this.scrolly.scroll.removeClass("scroll-scrolly_visible").find("div").andSelf().off(this.namespace),this.wrapper.remove(),l(document).add("body").off(this.namespace),l.isFunction(this.options.onDestroy)&&this.options.onDestroy.apply(this,[this.container])}},init:function(e){var o=this,r=this.container,i=this.containerWrapper||r,n=this.namespace,c=l.extend(this.options,e||{}),a={x:this.scrollx,y:this.scrolly},d=this.wrapper,h={scrollLeft:r.scrollLeft(),scrollTop:r.scrollTop()};if(t.mobile&&c.ignoreMobile||t.overlay&&c.ignoreOverlay||t.macosx&&!t.webkit)return!1;if(d)i.css({height:"auto","margin-bottom":-1*t.scroll.height+"px","margin-right":-1*t.scroll.width+"px","max-height":""});else{if(this.wrapper=d=l("<div>").addClass("scroll-wrapper").addClass(r.attr("class")).css("position","absolute"==r.css("position")?"absolute":"relative").insertBefore(r).append(r),r.is("textarea")&&(this.containerWrapper=i=l("<div>").insertBefore(r).append(r),d.addClass("scroll-textarea")),i.addClass("scroll-content").css({height:"auto","margin-bottom":-1*t.scroll.height+"px","margin-right":-1*t.scroll.width+"px","max-height":""}),r.on("scroll"+n,function(){l.isFunction(c.onScroll)&&c.onScroll.call(o,{maxScroll:a.y.maxScrollOffset,scroll:r.scrollTop(),size:a.y.size,visible:a.y.visible},{maxScroll:a.x.maxScrollOffset,scroll:r.scrollLeft(),size:a.x.size,visible:a.x.visible}),a.x.isVisible&&a.x.scroll.bar.css("left",r.scrollLeft()*a.x.kx+"px"),a.y.isVisible&&a.y.scroll.bar.css("top",r.scrollTop()*a.y.kx+"px")}),d.on("scroll"+n,function(){d.scrollTop(0).scrollLeft(0)}),c.disableBodyScroll){var p=function(l){s(l)?a.y.isVisible&&a.y.mousewheel(l):a.x.isVisible&&a.x.mousewheel(l)};d.on("MozMousePixelScroll"+n,p),d.on("mousewheel"+n,p),t.mobile&&d.on("touchstart"+n,function(e){var o=e.originalEvent.touches&&e.originalEvent.touches[0]||e,s={pageX:o.pageX,pageY:o.pageY},t={left:r.scrollLeft(),top:r.scrollTop()};l(document).on("touchmove"+n,function(l){var e=l.originalEvent.targetTouches&&l.originalEvent.targetTouches[0]||l;r.scrollLeft(t.left+s.pageX-e.pageX),r.scrollTop(t.top+s.pageY-e.pageY),l.preventDefault()}),l(document).on("touchend"+n,function(){l(document).off(n)})})}l.isFunction(c.onInit)&&c.onInit.apply(this,[r])}l.each(a,function(e,t){var i=null,d=1,h="x"===e?"scrollLeft":"scrollTop",p=c.scrollStep,u=function(){var l=r[h]();r[h](l+p),1==d&&l+p>=f&&(l=r[h]()),-1==d&&f>=l+p&&(l=r[h]()),r[h]()==l&&i&&i()},f=0;t.scroll||(t.scroll=o._getScroll(c["scroll"+e]).addClass("scroll-"+e),c.showArrows&&t.scroll.addClass("scroll-element_arrows_visible"),t.mousewheel=function(l){if(!t.isVisible||"x"===e&&s(l))return!0;if("y"===e&&!s(l))return a.x.mousewheel(l),!0;var i=-1*l.originalEvent.wheelDelta||l.originalEvent.detail,n=t.size-t.visible-t.offset;return(i>0&&n>f||0>i&&f>0)&&(f+=i,0>f&&(f=0),f>n&&(f=n),o.scrollTo=o.scrollTo||{},o.scrollTo[h]=f,setTimeout(function(){o.scrollTo&&(r.stop().animate(o.scrollTo,240,"linear",function(){f=r[h]()}),o.scrollTo=null)},1)),l.preventDefault(),!1},t.scroll.on("MozMousePixelScroll"+n,t.mousewheel).on("mousewheel"+n,t.mousewheel).on("mouseenter"+n,function(){f=r[h]()}),t.scroll.find(".scroll-arrow, .scroll-element_track").on("mousedown"+n,function(s){if(1!=s.which)return!0;d=1;var n={eventOffset:s["x"===e?"pageX":"pageY"],maxScrollValue:t.size-t.visible-t.offset,scrollbarOffset:t.scroll.bar.offset()["x"===e?"left":"top"],scrollbarSize:t.scroll.bar["x"===e?"outerWidth":"outerHeight"]()},a=0,v=0;return l(this).hasClass("scroll-arrow")?(d=l(this).hasClass("scroll-arrow_more")?1:-1,p=c.scrollStep*d,f=d>0?n.maxScrollValue:0):(d=n.eventOffset>n.scrollbarOffset+n.scrollbarSize?1:n.eventOffset<n.scrollbarOffset?-1:0,p=Math.round(.75*t.visible)*d,f=n.eventOffset-n.scrollbarOffset-(c.stepScrolling?1==d?n.scrollbarSize:0:Math.round(n.scrollbarSize/2)),f=r[h]()+f/t.kx),o.scrollTo=o.scrollTo||{},o.scrollTo[h]=c.stepScrolling?r[h]()+p:f,c.stepScrolling&&(i=function(){f=r[h](),clearInterval(v),clearTimeout(a),a=0,v=0},a=setTimeout(function(){v=setInterval(u,40)},c.duration+100)),setTimeout(function(){o.scrollTo&&(r.animate(o.scrollTo,c.duration),o.scrollTo=null)},1),o._handleMouseDown(i,s)}),t.scroll.bar.on("mousedown"+n,function(s){if(1!=s.which)return!0;var i=s["x"===e?"pageX":"pageY"],c=r[h]();return t.scroll.addClass("scroll-draggable"),l(document).on("mousemove"+n,function(l){var o=parseInt((l["x"===e?"pageX":"pageY"]-i)/t.kx,10);r[h](c+o)}),o._handleMouseDown(function(){t.scroll.removeClass("scroll-draggable"),f=r[h]()},s)}))}),l.each(a,function(l,e){var o="scroll-scroll"+l+"_visible",s="x"==l?a.y:a.x;e.scroll.removeClass(o),s.scroll.removeClass(o),i.removeClass(o)}),l.each(a,function(e,o){l.extend(o,"x"==e?{offset:parseInt(r.css("left"),10)||0,size:r.prop("scrollWidth"),visible:d.width()}:{offset:parseInt(r.css("top"),10)||0,size:r.prop("scrollHeight"),visible:d.height()})}),this._updateScroll("x",this.scrollx),this._updateScroll("y",this.scrolly),l.isFunction(c.onUpdate)&&c.onUpdate.apply(this,[r]),l.each(a,function(l,e){var o="x"===l?"left":"top",s="x"===l?"outerWidth":"outerHeight",t="x"===l?"width":"height",i=parseInt(r.css(o),10)||0,n=e.size,a=e.visible+i,d=e.scroll.size[s]()+(parseInt(e.scroll.size.css(o),10)||0);c.autoScrollSize&&(e.scrollbarSize=parseInt(d*a/n,10),e.scroll.bar.css(t,e.scrollbarSize+"px")),e.scrollbarSize=e.scroll.bar[s](),e.kx=(d-e.scrollbarSize)/(n-a)||1,e.maxScrollOffset=n-a}),r.scrollLeft(h.scrollLeft).scrollTop(h.scrollTop).trigger("scroll")},_getScroll:function(e){var o={advanced:['<div class="scroll-element">','<div class="scroll-element_corner"></div>','<div class="scroll-arrow scroll-arrow_less"></div>','<div class="scroll-arrow scroll-arrow_more"></div>','<div class="scroll-element_outer">','<div class="scroll-element_size"></div>','<div class="scroll-element_inner-wrapper">','<div class="scroll-element_inner scroll-element_track">','<div class="scroll-element_inner-bottom"></div>',"</div>","</div>",'<div class="scroll-bar">','<div class="scroll-bar_body">','<div class="scroll-bar_body-inner"></div>',"</div>",'<div class="scroll-bar_bottom"></div>','<div class="scroll-bar_center"></div>',"</div>","</div>","</div>"].join(""),simple:['<div class="scroll-element">','<div class="scroll-element_outer">','<div class="scroll-element_size"></div>','<div class="scroll-element_track"></div>','<div class="scroll-bar"></div>',"</div>","</div>"].join("")};return o[e]&&(e=o[e]),e||(e=o.simple),e="string"==typeof e?l(e).appendTo(this.wrapper):l(e),l.extend(e,{bar:e.find(".scroll-bar"),size:e.find(".scroll-element_size"),track:e.find(".scroll-element_track")}),e},_handleMouseDown:function(e,o){var s=this.namespace;return l(document).on("blur"+s,function(){l(document).add("body").off(s),e&&e()}),l(document).on("dragstart"+s,function(l){return l.preventDefault(),!1}),l(document).on("mouseup"+s,function(){l(document).add("body").off(s),e&&e()}),l("body").on("selectstart"+s,function(l){return l.preventDefault(),!1}),o&&o.preventDefault(),!1},_updateScroll:function(e,o){var s=this.container,r=this.containerWrapper||s,i="scroll-scroll"+e+"_visible",n="x"===e?this.scrolly:this.scrollx,c=parseInt(this.container.css("x"===e?"left":"top"),10)||0,a=this.wrapper,d=o.size,h=o.visible+c;o.isVisible=d-h>1,o.isVisible?(o.scroll.addClass(i),n.scroll.addClass(i),r.addClass(i)):(o.scroll.removeClass(i),n.scroll.removeClass(i),r.removeClass(i)),"y"===e&&r.css(s.is("textarea")||h>d?{height:h+t.scroll.height+"px","max-height":"none"}:{"max-height":h+t.scroll.height+"px"}),(o.size!=s.prop("scrollWidth")||n.size!=s.prop("scrollHeight")||o.visible!=a.width()||n.visible!=a.height()||o.offset!=(parseInt(s.css("left"),10)||0)||n.offset!=(parseInt(s.css("top"),10)||0))&&(l.extend(this.scrollx,{offset:parseInt(s.css("left"),10)||0,size:s.prop("scrollWidth"),visible:a.width()}),l.extend(this.scrolly,{offset:parseInt(s.css("top"),10)||0,size:this.container.prop("scrollHeight"),visible:a.height()}),this._updateScroll("x"===e?"y":"x",n))}};var c=n;l.fn.scrollbar=function(e,o){return"string"!=typeof e&&(o=e,e="init"),"undefined"==typeof o&&(o=[]),l.isArray(o)||(o=[o]),this.not("body, .scroll-wrapper").each(function(){var s=l(this),r=s.data(t.data.name);(r||"init"===e)&&(r||(r=new c(s)),r[e]&&r[e].apply(r,o))}),this},l.fn.scrollbar.options=i;var a=function(){var l=0,e=0;return function(o){var s,i,n,c,d,h,p;for(s=0;s<t.scrolls.length;s++)c=t.scrolls[s],i=c.container,n=c.options,d=c.wrapper,h=c.scrollx,p=c.scrolly,(o||n.autoUpdate&&d&&d.is(":visible")&&(i.prop("scrollWidth")!=h.size||i.prop("scrollHeight")!=p.size||d.width()!=h.visible||d.height()!=p.visible))&&(c.init(),n.debug&&(window.console&&console.log({scrollHeight:i.prop("scrollHeight")+":"+c.scrolly.size,scrollWidth:i.prop("scrollWidth")+":"+c.scrollx.size,visibleHeight:d.height()+":"+c.scrolly.visible,visibleWidth:d.width()+":"+c.scrollx.visible},!0),e++));r&&e>10?(window.console&&console.log("Scroll updates exceed 10"),a=function(){}):(clearTimeout(l),l=setTimeout(a,300))}}();window.angular&&!function(l){l.module("jQueryScrollbar",[]).provider("jQueryScrollbar",function(){var e=i;return{setOptions:function(o){l.extend(e,o)},$get:function(){return{options:l.copy(e)}}}}).directive("jqueryScrollbar",function(l,e){return{restrict:"AC",link:function(o,s,r){var t=e(r.jqueryScrollbar),i=t(o);s.scrollbar(i||l.options).on("$destroy",function(){s.scrollbar("destroy")})}}})}(window.angular)});

/*!
 * jQuery Cookie
 *
 */

jQuery.cookie=function(d,c,a){if("undefined"!=typeof c){a=a||{};null===c&&(c="",a.expires=-1);var b="";if(a.expires&&("number"==typeof a.expires||a.expires.toUTCString))"number"==typeof a.expires?(b=new Date,b.setTime(b.getTime()+864E5*a.expires)):b=a.expires,b="; expires="+b.toUTCString();var e=a.path?"; path="+a.path:"",f=a.domain?"; domain="+a.domain:"";a=a.secure?"; secure":"";document.cookie=[d,"=",encodeURIComponent(c),b,e,f,a].join("")}else{c=null;if(document.cookie&&""!=document.cookie){a=
	document.cookie.split(";");for(b=0;b<a.length;b++)if(e=jQuery.trim(a[b]),e.substring(0,d.length+1)==d+"="){c=decodeURIComponent(e.substring(d.length+1));break}}return c}};


	
/*	FitVids
 /* --------------------------------------------- */

(function ($) {

	$.fn.fitVids = function(options) {

		var settings = {
			customSelector: null
		};

		if (!document.getElementById('fit-vids-style')) {

			var div = document.createElement('div'),
				ref = document.getElementsByTagName('base')[0] || document.getElementsByTagName('script')[0],
				cssStyles = '&shy;<style>.fluid-video-wrapper{width:100%;position:relative;padding:0;}.fluid-video-wrapper iframe,.fluid-video-wrapper object,.fluid-video-wrapper embed {position:absolute;top:0;left:0;width:100%;height:100%;}</style>';

			div.className = 'fit-vids-style';
			div.id = 'fit-vids-style';
			div.style.display = 'none';
			div.innerHTML = cssStyles;

			ref.parentNode.insertBefore(div,ref);

		}

		if (options) {
			$.extend(settings, options);
		}

		return this.each(function () {
			var selectors = [
				"iframe[src*='player.vimeo.com']",
				"iframe[src*='youtube.com']",
				"iframe[src*='youtube-nocookie.com']",
				"iframe[src*='kickstarter.com'][src*='video.html']",
				"iframe[src*='w.soundcloud.com']",
				"object",
				"embed"
			];

			if (settings.customSelector) {
				selectors.push(settings.customSelector);
			}

			var $allVideos = $(this).find(selectors.join(',')).not("iframe[src^='http:/\/\']");
			$allVideos = $allVideos.not("object object"); // SwfObj conflict patch

			$allVideos.each(function(){
				var $this = $(this);
				if (this.tagName.toLowerCase() === 'embed' && $this.parent('object').length || $this.parent('.fluid-video-wrapper').length) {
					return;
				}
				var height = ( this.tagName.toLowerCase() === 'object' || ($this.attr('height') && !isNaN(parseInt($this.attr('height'), 10))) ) ? parseInt($this.attr('height'), 10) : $this.height(),
					width = !isNaN(parseInt($this.attr('width'), 10)) ? parseInt($this.attr('width'), 10) : $this.width(),
					aspectRatio = height / width;

				if(!$this.attr('id')) {
					var videoID = 'fitvid' + Math.floor(Math.random()*999999);
					$this.attr('id', videoID);
				}
				$this.wrap('<div class="fluid-video-wrapper"></div>').parent('.fluid-video-wrapper').css('padding-top', (aspectRatio * 100)+"%");
				$this.removeAttr('height').removeAttr('width');
			});
		});

	};

})(jQuery);	
	
/*
 * jQuery.appear
 * https://github.com/bas2k/jquery.appear/
 * http://code.google.com/p/jquery-appear/
 * http://bas2k.ru/
 *
 * Copyright (c) 2009 Michael Hixson
 * Copyright (c) 2012-2014 Alexander Brovikov
 * Licensed under the MIT license (http://www.opensource.org/licenses/mit-license.php)
 */
!function(e){e.fn.appear=function(a,r){var n=e.extend({data:void 0,one:!0,accX:0,accY:0},r);return this.each(function(){var r=e(this);if(r.appeared=!1,!a)return void r.trigger("appear",n.data);var p=e(window),t=function(){if(!r.is(":visible"))return void(r.appeared=!1);var e=p.scrollLeft(),a=p.scrollTop(),t=r.offset(),c=t.left,i=t.top,o=n.accX,f=n.accY,s=r.height(),u=p.height(),d=r.width(),l=p.width();i+s+f>=a&&a+u+f>=i&&c+d+o>=e&&e+l+o>=c?r.appeared||r.trigger("appear",n.data):r.appeared=!1},c=function(){if(r.appeared=!0,n.one){p.unbind("scroll",t);var c=e.inArray(t,e.fn.appear.checks);c>=0&&e.fn.appear.checks.splice(c,1)}a.apply(this,arguments)};n.one?r.one("appear",n.data,c):r.bind("appear",n.data,c),p.scroll(t),e.fn.appear.checks.push(t),t()})},e.extend(e.fn.appear,{checks:[],timeout:null,checkAll:function(){var a=e.fn.appear.checks.length;if(a>0)for(;a--;)e.fn.appear.checks[a]()},run:function(){e.fn.appear.timeout&&clearTimeout(e.fn.appear.timeout),e.fn.appear.timeout=setTimeout(e.fn.appear.checkAll,20)}}),e.each(["append","prepend","after","before","attr","removeAttr","addClass","removeClass","toggleClass","remove","css","show","hide"],function(a,r){var n=e.fn[r];n&&(e.fn[r]=function(){var a=n.apply(this,arguments);return e.fn.appear.run(),a})})}(jQuery);

	/*	HELPERS
	/* --------------------------------------------- */

	(function ($) {

		$.cosyone_core_helpers = $.cosyone_core_helpers || {};

		$.cosyone_core_helpers = {
			sameheight : function (obj) {
				var $this = $(this), max = 0,
					$item = $this.find('.owl-item').children();

				$item.css('height','auto').each(function () {
					max = Math.max( max, $(this).outerHeight() );
				}).promise().done(function () {
					$(this).css('height', max);
				});
			},
			owlGetVisibleElements : function () {
				var $this = $(this);

				$this.find('.owl-item').removeClass('first last');
				$this.find('.owl-item.active').first().addClass('first');
				$this.find('.owl-item.active').last().addClass('last');
			}
		}

	
	$('.widget_latest_tweets_widget ul').addClass('tweet_list'); 	
	$('.widget_latest_tweets_widget li').addClass('relative'); 	
	$('.widget_latest_tweets_widget .tweet-text').addClass('second_font'); 	
	$('.widget_latest_tweets_widget .tweet-details').addClass('fw_light lh_small'); 	
	
	$('.single-folio-cat a').addClass('color_light sc_hover fw_light'); 
	$('#menu-top a').addClass('sc_hover tr_delay'); 

	$('#contactform-submit').addClass('button_type_2 black state_2 tr_all second_font fs_medium tt_uppercase d_inline_b'); 
	$('.comment-reply-link').addClass('t_align_c brands_carousel_next button_type_4 grey state_2 tr_all vc_child d_inline_b reply_button black_hover'); 
	
	
	$('.pagination ul').addClass('hr_list'); 
	$('.pagination ul li').addClass('m_right_3'); 
	$('.pagination ul li>a').addClass('button_type_4 tr_delay grey state_2 d_block vc_child t_align_c fs_small'); 
	$('.pagination ul li>span').addClass('button_type_4 tr_delay grey state_2 d_block vc_child t_align_c fs_small'); 
	
	$('.woocommerce.yith-similar-products h2').addClass('products-title second_font color_dark tt_uppercase fw_light d_inline_m m_bottom_4');
	$('.woocommerce.yith-similar-products h2').after('<hr class="divider_bg m_bottom_15">'); 
	
	$('.woocommerce.yith-similar-products .products').wrap('<div data-sidebar="no_sidebar" data-columns="4" class="related_products view-grid owl_carousel type_2 shop-columns-4"></div>'); 
	$('.woocommerce.yith-similar-products .products').wrap('<div class="row"></div>'); 

	
	
	
	
	
	
	
	
		$('.button_question').on('click',function(event){
			event.preventDefault();
			
					
				
					var tab = $('.single-clothes .tabs.styled_tabs');
					
					//e.preventDefault();
					tab.find('.tab_content.active').hide();
					tab.find('.tab_content.active').removeClass('active');
					
					tab.find('nav a.color_dark').removeClass('color_dark');

					$('nav a[href="#tab-questions"]').addClass('color_dark');
					$('.tab_content#tab-questions').addClass('active');
					$('.tab_content#tab-questions').show();
				
				
					$('html, body').animate({scrollTop:$('#tab-questions').offset().top}, 'slow');
				
				});
		
		$('.mgwoocommercebrands.brands-listing ul').wrap('<div class="row"></div>');
		$('.mgwoocommercebrands.brands-listing li').wrapInner('<div class="col-lg-4 col-md-4 col-sm-4 m_bottom_35 m_xs_bottom_30"><div class="frame_container r_image_container relative"><figure class="relative"></figure></div></div>'); 
		$('.mgwoocommercebrands.brands-listing figure a:nth-child(1)').addClass('d_block wrapper scale_image_container relative m_bottom_15'); 
		$('.mgwoocommercebrands.brands-listing figure img').addClass('tr_all scale_image'); 
		
		
		
		
		
		
		$('.wpcf7-select').wrap('<div class="styled_select relative"></div>'); 
		$('.wpcf7-select').before('<div class="select_title fs_medium fw_light color_light relative d_none tr_all">Please select</div>'); 
		$('.wpcf7-select').after('<ul class="options_list d_none tr_all hidden bg_grey_light"></ul>'); 
		
		
		
		
		$('.view-grid.type_3 .actions_wrap a.compare').append('<span class="feedback">Add to Compare</span>'); 
		$('.view-grid.type_2 .buttons_row a.compare').append('<span class="feedback">Add to Compare</span>'); 
		
		
		
		
		$('.widget_recent_reviews>ul.product_list_widget li').addClass('relative m_bottom_15 t_sm_align_c t_xs_align_l'); 
		$('.widget_recent_reviews>ul li').addClass('relative m_bottom_15'); 
		$('.widget_recent_reviews>ul li:not(:last-child)').append('<hr class="m_top_15 m_bottom_0">'); 
		
		
		$('.widget_recent_entries>ul').addClass('fw_light'); 
		$('.widget_recent_entries>ul li').addClass('relative m_bottom_15'); 
		$('.widget_recent_entries>ul li:not(:last-child)').append('<hr class="m_top_15 m_bottom_0">'); 
		
		
		$('.widget_rss>ul').addClass('fw_light'); 
		$('.widget_rss>ul li').addClass('relative m_bottom_15'); 
		$('.widget_rss>ul li:not(:last-child)').append('<hr class="m_top_15 m_bottom_0">'); 
		
		
		$('.current-menu-item').addClass('current'); 
		$('.current-menu-parent').addClass('current'); 
		$('.current_page_parent').addClass('current'); 
		
		$('.main_menu_nav .children').addClass('sub_menu bg_grey_light tr_all'); 
		$('.main_menu_nav .sub-menu').addClass('sub_menu bg_grey_light tr_all'); 
		$('.main_menu > li > a').addClass('tt_uppercase tr_delay'); 
		
		
		$('.wp-polls-form .Buttons').addClass('second_font w_full tt_uppercase fs_medium button_type_2 black state_2 d_block tr_all'); 
		
		
		
		$('.widget_text p').addClass('m_bottom_14 fw_light'); 
		$('.widget_text a').not('.mail').addClass('sc_hover second_font'); 
		
		
		$('.widget_archive>ul').addClass('categories_list second_font w_break'); 
		$('.widget_archive ul li').addClass('relative m_bottom_0'); 
		$('.widget_archive>ul>li>a').addClass('fs_large_0 d_block tr_delay'); 
		
		
		$('.widget_meta>ul').addClass('categories_list second_font w_break'); 
		$('.widget_meta ul li').addClass('relative m_bottom_0'); 
		$('.widget_meta>ul>li>a').addClass('fs_large_0 d_block tr_delay'); 
		
		
		
		$('.widget_categories>ul').addClass('categories_list second_font w_break'); 
		$('.widget_categories ul li').addClass('relative m_bottom_0'); 
		$('.widget_categories>ul>li>a').addClass('fs_large_0 d_block tr_delay'); 
		$('.widget_categories ul.children').before('<button class="open_sub_categories fs_medium"></button>'); 
		$('.widget_categories ul.children').addClass('d_none'); 
		$('.widget_categories ul.children>li>a').addClass('tr_delay sc_hover bg_grey_light_2_hover'); 
		
		
		$('.widget_product_categories>ul').addClass('categories_list second_font w_break'); 
		$('.widget_product_categories ul li').addClass('relative m_bottom_0'); 
		$('.widget_product_categories>ul>li>a').addClass('fs_large_0 d_block tr_delay'); 
		$('.widget_product_categories ul.children').before('<button class="open_sub_categories fs_medium"></button>'); 
		$('.widget_product_categories ul.children').addClass('d_none'); 
		$('.widget_product_categories ul.children>li>a').addClass('tr_delay sc_hover bg_grey_light_2_hover'); 
		
		
		$('.widget_tag_cloud ul').addClass('hr_list tags_list second_font fs_medium'); 
		$('.widget_tag_cloud ul li').addClass('m_right_3 m_bottom_3'); 
		$('.widget_tag_cloud ul li a').addClass('tr_delay button_type_1 d_block grey state_2'); 
		
		$('.widget_product_tag_cloud ul').addClass('hr_list tags_list second_font fs_medium'); 
		$('.widget_product_tag_cloud ul li').addClass('m_right_3 m_bottom_3'); 
		$('.widget_product_tag_cloud ul li a').addClass('tr_delay button_type_1 d_block grey state_2'); 
		
		
		$('.widget_nav_menu ul').addClass('second_font vr_list_type_1 with_links'); 
		$('.widget_nav_menu ul li').addClass('m_bottom_14'); 
		$('.widget_nav_menu ul a').addClass('sc_hover d_block'); 
		$('.widget_nav_menu ul a').prepend('<i class="fa fa-caret-right"></i>'); 
		
		
		$('.widget_pages ul').addClass('second_font vr_list_type_1 with_links'); 
		$('.widget_pages ul li').addClass('m_bottom_14'); 
		$('.widget_pages ul a').addClass('sc_hover d_block'); 
		$('.widget_pages ul a').prepend('<i class="fa fa-caret-right"></i>'); 
		
		$('header.type_3 input[type="search"]').addClass('hidden');	
		
	})(jQuery);

	
	
	
	
	
		/*	MAIN ANIMATION
	/* --------------------------------------------- */

	(function ($) {

		$.cosyone_dropdown_list = (function () {

			var $dropdown = $('.dropdown-list'),
				transEndEventNames = {
					'WebkitTransition': 'webkitTransitionEnd',
					'MozTransition': 'transitionend',
					'OTransition': 'oTransitionEnd',
					'msTransition': 'MSTransitionEnd',
					'transition': 'transitionend'
				},
				transEndEventName = transEndEventNames[ Modernizr.prefixed('transition') ],
				istouch = Modernizr.touch,
				support = Modernizr.csstransitions && !istouch,
				event = istouch ? 'touchstart' : 'click',
				settings = {
					speed : 10
				};

			function init(config) {

				settings = $.extend( {}, settings, config );

				prepareEachDropdown(settings);
				bindEvents();
			}

			function update(dropdown, settings) {

				$(dropdown).each(function (idx, dropdown) {
					initItems(dropdown, settings);
				});

			}

			function prepareEachDropdown(settings) {

				$dropdown.each(function (idx, dropdown) {
					initItems(dropdown, settings);
				});

			}

			function initItems( dropdown, settings ) {

				if (!support) return;

				var $dropdown = $(dropdown),
					$drop = $dropdown.find('.dropdown');

				if ($drop.hasClass("secondary_navigation")) {
					$items = $drop.find('ul').first().children('.menu-item, .page_item');
				} else {
					$items = $dropdown.find('.animated_item');
				}

				$dropdown.data({
					items : $items,
					len : $items.length,
					settings: settings
				});

				defaultState($dropdown, settings.speed);

				$items.eq(0).on(transEndEventName, function (e) {

					if ($dropdown.hasClass("active") || e.originalEvent.propertyName !== "transform") return false;
					defineNewState($dropdown);
					$dropdown.removeClass("visible");

				});

				if ($dropdown.hasClass('active')) {
					defineNewState($dropdown, true);
				}

				$items.eq($dropdown.data("len") - 1).on(transEndEventName, function () {

					if (!$dropdown.hasClass("active")) return false;
					defineNewState($dropdown, true);

				});

			}

			function defaultState(dropdown, speed) {

				dropdown.data('items').each(function (i) {
					$(this).css('transition-delay', (i + 1) / speed + 's');
				});

			}

			function defineNewState(dropdown, reverse) {

				var speed = dropdown.data('settings').speed;

				if (reverse) {

					var len = dropdown.data('len'),
						$items = dropdown.data('items');

					for (var i = len,j = 0; i >= 0, j < len; i--, j++) {
						$items.eq(j).css('transition-delay', i / speed + 's');
					}

				} else {
					defaultState(dropdown, speed);
				}

			}

			function bindEvents() {

				$dropdown.on(event, '[class*="open_"]', function (e) {

					var $target = $(this),
						$delegateTarget = $(e.delegateTarget);

					if (!$delegateTarget.is('.active')) {
						$delegateTarget.trigger('defineNewState', [false]);
						$delegateTarget.add($target).addClass('active').end().addClass('visible');
					} else {
						$delegateTarget.trigger('defineNewState', [true]);
						$delegateTarget.add($target).removeClass('active');
					}

				}).mouseleave(function () {

					var $target = $(this),
						$element = $target.find('[class*="open_"]');

					if ($target.is('.active')) {
						$target.add($element).removeClass('active');
					}

				}).on('defineNewState', function (e, reverse) {

				});

			}

			return {
				init: init,
				update: update
			}

		})();

	})(jQuery);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	(function($){
	"use strict";

	
	
	var Core = {

		initialized : false,

		initialize : function(pageLoad){
			if(this.initialized) return;

			if(pageLoad == "DOM"){
				this.buildAfterDomReady();
			}
			else if(pageLoad == "images"){
				this.buildAfterWindowLoad();
				this.initialized = true;
			}
		},

		buildAfterDomReady : function(){
			this.plugins();
		
			$('.page_wrapper').fitVids();
			
			$.responsiveMenu();
			$.responsiveMenu1();
			$.backToTop(100,'bounceInRight','bounceOutRight');
			$.animatedContent();
			$.styledSelect();
			$.oldBrowsersPlaceholder();
			$.scrollSidebar();
			this.events.categories();
			this.sliders();
			this.events.openDropdown();
			this.events.ratingList();
			this.events.openSearchForm();
			this.owlCarousel();
			this.simpleSlideshow();
			this.events.reset();
			this.events.isotopeChangeLayout();
			
		},

		buildAfterWindowLoad : function(){
			$.fullWidthMasonry();

			$.stickyMenu();
			$.megaMenu();
			this.isotope();
			this.events.progressBar();
			$.correctResponsiveImagesPosition();
			this.events.popupButtons();
			this.pluginsWLOAD();
			this.events.offer();
			$.counters();

		},

		events : {

			openDropdown : function(){

				$.openDropdown();

				// close button

				$('[class*="_layout"]').on('click','[class*="close"],.alert_box i[class^="fa "]',function(){
					$(this).parent().animate({
						'opacity': 0
					},function(){
						$(this).slideUp();
					});
				});

			},

			ratingList : function(){
				var fp = $('.rating_list');
				fp.on('click','li',function(){
					var self = $(this);
					self.siblings().removeClass('color_lbrown');
					self.addClass('color_lbrown').prevAll().addClass('color_lbrown');
				});
			},

			
			
			openSearchForm : function(){

				var form = $('[role="search"]'),
					field = form.children('input[type="search"]');
	
				if(!field.hasClass('hidden')) return false;

				form.on("mouseenter mouseleave",function(event){
					if($(window).width() < 767) return false;
					$(this).stop().animate({
						"width" : event.type === "mouseenter" ? 242 : 40
					}).children('input[type="search"]').toggleClass('hidden');

					if(event.type === "mouseleave"){
						$(this).children('input[type="search"]').trigger("blur");
					}
				});
			},

			progressBar : function(){

				var skill = $('[data-progress]');

				skill.each(function(){
					var $this = $(this),
						percent = $this.data('progress'),
						offset = $this.offset().top - 850;

					$(window).on("scroll",function(){

						if($this.children().width() > 0) return false;

						if($(window).scrollTop() >= offset){
							$this.children().stop().animate({
								width : percent + "%"
							});
							return false;
						}
					});

				});

			},

			popupButtons : function(){

				var ulWithButtons = $('.open_buttons_container:not(.in_masonry)');

				ulWithButtons.each(function(){
					var $this = $(this);
					$this.css({
						'margin-left' : $this.outerWidth() / -2,
						'margin-top' : $this.outerHeight() / -2
					});
				});

			},

			categories : function(){
				var list = $('.categories_list');

				list.on('click','.open_sub_categories',function(){
					var $this = $(this);

					if(!$this.next('ul').length) return false;

					$this.toggleClass('active').prev("a").toggleClass("fw_bold").siblings("ul").stop().slideToggle();
					$this.prev('a').toggleClass('scheme_color bg_grey_light_2');
				});
			},

			sortIsotope : function(container){
				$('.sort').on('click','[data-filter]',function(e){
					var self = $(this),
					selector = self.attr('data-filter');
				  	container.isotope({ filter: selector });
				  	e.preventDefault();
				});
			},

			contactForm : function(){

				var cf = $('#contactform');
				cf.append('<div class="message_container d_none m_top_20"></div>');
				var message = cf.children('.message_container');

				cf.on("submit",function(event){
					event.preventDefault();
					if(message.hasClass('opened')) return;
					var self = $(this),text;

					var request = $.ajax({
						url:"php/mail.php",
						type : "post",
						data : self.serialize()
					});

					request.then(function(data){
						if(data == "1"){
							message.addClass('opened');
							text = "Your message has been sent successfully!";

							cf.find('input:not([type="submit"]),textarea').val('');

							$('.message_container').html('<div class="alert_box r_corners color_green success"><p>'+text+'</p></div>')
								.delay(150)
								.slideDown(300)
								.delay(4000)
								.slideUp(300,function(){
									$(this).html("");
									message.removeClass('opened');
								});

						}
						else{
							message.addClass('opened');
							if(cf.find('textarea').val().length < 20){
								text = "Message must contain at least 20 characters!"
							}
							if(cf.find('input').val() == ""){
								text = "All required fields must be filled!";
							}
							$('.message_container').html('<div class="alert_box error relative m_bottom_10 fw_light"><p>'+text+'</p></div>')
								.delay(150)
								.slideDown(300)
								.delay(4000)
								.slideUp(300,function(){
									$(this).html("");
									message.removeClass('opened');
								});
						}
					},function(){
						message.addClass('opened');
						$('.message_container').html('<div class="alert_box error relative m_bottom_10 fw_light"><p>Connection to server failed!</p></div>')
								.delay(150)
								.slideDown(300)
								.delay(4000)
								.slideUp(300,function(){
									$(this).html("");
									message.removeClass('opened');
								});
					});
				});

			},

			newsletter : function(){
				var subscribe = $('.newsletter');

				subscribe.each(function(){
					var $this = $(this);
					$this.append('<div class="message_container_subscribe d_none m_top_20"></div>');
					var message = $this.find('.message_container_subscribe'),text;

					$this.on('submit',function(e){
						e.preventDefault();
						if(message.hasClass('opened')) return;
						
						if($this.find('input[type="email"]').val() == ''){
							message.addClass('opened');
							text = "Please enter your e-mail!";
							message.html('<div class="alert_box error relative m_bottom_10 fw_light"><p>'+text+'</p></div>')
								.slideDown()
								.delay(4000)
								.slideUp(function(){
									$(this).html("");
									message.removeClass('opened');
								});

						}else{
							$this.find('span.error').hide();
							$.ajax({
								type: "POST",
								url: "php/newsletter.php",
								data: $this.serialize(),
								success: function(data){
									if(data == '1'){
										message.addClass('opened');
										text = "Your email has been sent successfully!";
										message.html('<div class="alert_box r_corners color_green success"><p>'+text+'</p></div>')
											.slideDown()
											.delay(4000)
											.slideUp(function(){
												$(this).html("");
												message.removeClass('opened');
											})
											.prevAll('input[type="email"]').val("");
									}else{
										message.addClass('opened');
										text = "Invalid email address!";
										message.html('<div class="alert_box error relative m_bottom_10 fw_light"><p>'+text+'</p></div>')
											.slideDown()
											.delay(4000)
											.slideUp(function(){
												$(this).html("");
												message.removeClass('opened');
											});
									}
								}
							});
						}
					});

				});
			},

			

			// offer

			offer : function(){

				$('.offer_container').each(function(){

					var $this = $(this),
						offer = $this.find('.offer');

					$this.on('mouseenter mouseleave',function(){
						offer.toggleClass('hidden visible');
					});

					$this.on('mousemove',function(event){

						var left = $this.offset().left,
							top = $this.offset().top;

						offer.css({
							top : Math.abs(top - event.pageY - 20),
							left : Math.abs(left - event.pageX - 20)
						});

					});

				});

			},

			reset : function(){

				$('.filter_reset').on('click',function(){
					var range = $(this).closest('form').find('.range_slider'),
						data = range.data();

					range.slider('option','values', [data.firstValue, data.secondValue]);

					setTimeout(function(){
						range.next().children('.range_min').val("$" + data.firstValue)
							.next().val("$" + data.secondValue);
					},0);

				});

			},

			isotopeChangeLayout : function(){

				var button = $('[data-isotope-container]');

				button.each(function(){

					var $this = $(this),
						container = $($this.data('isotope-container')),
						layout = $this.data('isotope-layout');

					$this.on('click',function(){

						$(this).addClass('black_button_active').siblings().removeClass('black_button_active').addClass('black_hover');

						if(layout == "list"){
							container.children("[class*='isotope_item']").addClass('list_view_type');
							container.removeClass('m_bottom_20').addClass('m_bottom_10');
						}
						else{
							container.children("[class*='isotope_item']").removeClass('list_view_type');
							container.addClass('m_bottom_20').removeClass('m_bottom_10');
							$.correctResponsiveImagesPosition();
						}

						container.isotope('layout');

						container.find('.tooltip_container').tooltip('.tooltip').tooltip('.tooltip');

					});



				});

			}

		},

		sliders : function(){

			var slidersArray = ['.layerslider','.layerslider_video','.royalslider','.r_slider','.flexslider'];

			// layerslider 

			if($(slidersArray[0]).length){
				$(slidersArray[0]).layerSlider({
					responsiveUnder : 1140,
					layersContainer : 1140,
					navStartStop : false,
					showBarTimer : false,
					showCircleTimer : false,
					skinsPath : './plugins/layerslider/skins/',
					skin : 'defaultskin',
					cbInit : function(){
						$(slidersArray[0]).find('.ls-nav-prev').append('<i class="fa fa-angle-left"></i>').end().
							find('.ls-nav-next').append('<i class="fa fa-angle-right"></i>');
					}
				});
			}

			// video slider (layer)

			if($(slidersArray[1]).length){
				$(slidersArray[1]).layerSlider({
					pauseOnHover:false,
					responsive:true,
					responsiveUnder:1170,
					layersContainer : 1170,
					animateFirstSlide:false,
					twoWaySlideshow:true,
					skinsPath:'plugins/layerslider/skins/',
					skin:'borderlessdark',
					globalBGColor:'transparent',
					navPrevNext : true,
					hoverPrevNext : false,
					navStartStop:false,
					navButtons:false,
					showCircleTimer:false,
					thumbnailNavigation:'disabled',
					lazyLoad:false,
					cbInit : function(){
 						$(slidersArray[1]).find('.ls-nav-next').addClass('button_type_11 black_hover grey state_2 t_align_c vc_child d_block tr_all')
 							.append('<i class="fa fa-angle-right d_inline_m"></i>').end()
 						.find('.ls-nav-prev').addClass('button_type_11 black_hover grey state_2 t_align_c vc_child d_block tr_all')
 											.append('<i class="fa fa-angle-left d_inline_m"></i>');

 					}
				});
			}

			// royal slider

			if($(slidersArray[2]).length){
				$(slidersArray[2]).royalSlider({
		            keyboardNavEnabled: true,
		            autoScaleSlider : true,
		            imageScaleMode : 'fill',
		            slidesSpacing : 0,
		            transitionSpeed : 500,
		            fadeinLoadedSlide : false,
		            loop : true
		        });
		        var slider = $(slidersArray[2]).data('royalSlider');

				slider.slides[0].holder.on('rsAfterContentSet', function(e, slideObject) {
				    $(slidersArray[2]).find('.rsArrowLeft').append('<i class="fa fa-angle-left"></i>').end()
				    	.find('.rsArrowRight').append('<i class="fa fa-angle-right"></i>');
				});
			}

			// revolution slider

			if($(slidersArray[3]).length){
				var api = $(slidersArray[3]).revolution({
					delay:5000,
					startwidth:1170,
					startheight:570,
					hideThumbs:0,
					fullWidth:"on",
		     		hideTimerBar:"on",
		     		soloArrowRightHOffset:20,
		     		soloArrowLeftHOffset:20,
		     		navigationVOffset : 15,
		     		shadow:0
				});
				api.bind('revolution.slide.onloaded',function(){
	      		$(slidersArray[3]).parent().find('.tp-leftarrow').append('<i class="fa fa-angle-left"></i>').end()
				    	.find('.tp-rightarrow').append('<i class="fa fa-angle-right"></i>');
	      		});
			}

			// flexslider

			if($(slidersArray[4]).length){
				$(slidersArray[4]).flexslider({
					animation : "fade",
					animationSpeed : 500,
					prevText: '<i class="fa fa-angle-left"></i>',
					nextText: '<i class="fa fa-angle-right"></i>'
				});
			}
		},

		pluginsWLOAD : function(){
			var pluginsArray = ['.tooltip_container','.sh_container'];

			// tooltip container

			if($(pluginsArray[0]).length){
				$(pluginsArray[0]).tooltip('.tooltip');
			 }

			// same height

			if($(pluginsArray[1]).length){
				$(pluginsArray[1]).sameHeight();
			}
			
			
			
			
			// footer widget masonry
			
			var container_widget_footer = document.querySelector('#container-main-footer');
	
			if(container_widget_footer) {
				var msnry = new Masonry( container_widget_footer, {
				  itemSelector: '.item'
				});
			}
	
			
			
			
			
			
		},

		plugins : function(){

			// tabs

			if($('.tabs').length){
				$('.tabs').easytabs({
					tabActiveClass : 'color_dark',
					tabs : '> nav > ul > li',
					updateHash : false
				}).bind('easytabs:after', function() {
				    $('.tabs').find('.tooltip_container').tooltip('.tooltip').tooltip('.tooltip');
				});
			}

			// twitter

			 if($('.latest-tweets').length){
			
				 $('.latest-tweets').find('.tweet_odd').remove();
				 $('.latest-tweets').find('.tweet_list').owlCarousel({
					 items : 1,
					 autoplay : true,
					 loop:false,
					 animateIn : "flipInX",
					 animateOut : "slideOutDown",
					 autoplayTimeout : 4000
				 });
				
			 }

			
			// accordion

			if($('.accordion:not(.toggle)').length){
				$('.accordion:not(.toggle)').accordion();
			}

			// toggle

			if($('.toggle').length){
				$('.toggle').accordion(450,true);
			}

			// jackbox
			(function () {
	
			if ($(".jackbox[data-group]").length) {

				$(".jackbox[data-group]").jackBox("init", {
					dynamic: true,
					showInfoByDefault: false,
					preloadGraphics: true,
					fullscreenScalesContent: true,
					autoPlayVideo: true,
					flashVideoFirst: false,
					defaultVideoWidth: 960,
					defaultVideoHeight: 540,
					baseName: cosyone_global.template_directory + 'js/jackbox',
					className: ".jackbox",
					useThumbs: true,
					thumbsStartHidden: false,
					thumbnailWidth: 75,
					thumbnailHeight: 50,
					useThumbTooltips: true,
					showPageScrollbar: false,
					useKeyboardControls: true
				});
			}

			})();
			
		

			


		},

		owlCarousel : function(){

			$('.owl-carousel').each(function(){

				var _this = $(this),
					options = _this.data('owl-carousel-options') ? _this.data('owl-carousel-options') : {},
					buttons = _this.data('nav'),
					config = $.extend(options,{
						dragEndSpeed : 500,
						smartSpeed : 500
					});
					
				var owl = _this.owlCarousel(config);

				$('.' + buttons + 'prev').on('click',function(){
					owl.trigger('prev.owl.carousel');
				});
				$('.' + buttons + 'next').on('click',function(){
					owl.trigger('next.owl.carousel');
				});

			});
		},

		

		isotope : function(){

			var cthis = this;
			$('[data-isotope-options]').each(function(){

				var self = $(this),
					options = self.data('isotope-options');

				var isotope = self.isotope(options);

				isotope.isotope('layout');

				cthis.events.sortIsotope(self);	

			});

		},

		simpleSlideshow : function(){

			var slideshow = $('.simple_slideshow');
			if(!slideshow.length) return false;

			slideshow.each(function(){

				var $this = $(this),
					options = $this.data('flexslider-options'),
					all = {
						animationSpeed : 500,
						slideshow : $this.data('slideshow'),
						controlNav : false,
						prevText : "",
						nextText : "",
						start : function(){
							var p = $this.find('.flex-prev'),
								n = $this.find('.flex-next');
							p.append('<i class="fa fa-angle-left d_inline_m"></i>');
							n.append('<i class="fa fa-angle-right d_inline_m"></i>');
							p.add(n).addClass('b_none button_type_11 grey state_2 t_align_c vc_child d_block tr_all');
						}
					},
					config = $.extend({}, $.flexslider.defaults , all,  options);

				slideshow.flexslider(config);

			});

		}

	}

	window.globalCore = Core;

	//DOM ready

	$(function(){
		Core.initialize("DOM");

	});


	// after all images been loaded

	$(window).load(function(){
		Core.initialize('images');
	});

	$(window).on('load',function(){
		$('#preloader').fadeOut(1000,function(){
			// page loaded
			//$('[data-popup="#subscribe_popup"]').trigger('click');
		});
	});

})(jQuery);








/*	Popup
 /* --------------------------------------------- */

(function ($) {

	$.cosyone_popup_prepare = function (el, options) {
		
		this.el = el;
		this.options = $.extend({}, $.cosyone_popup_prepare.DEFAULTS, options);
		this.init();
		
	}

	$.cosyone_popup_prepare.DEFAULTS = {
		actionpopup : '',
		noncepopup: '',
		on_load : function () { }
	}

	$.cosyone_popup_prepare.openInstance = [];

	$.cosyone_popup_prepare.prototype = {
		init: function () {
			$.cosyone_popup_prepare.openInstance.unshift(this);
			var base = this;
			base.scope = false;
			base.doc = $(document);
			base.body = $('body');
			base.instance	= $.cosyone_popup_prepare.openInstance.length;
			base.namespace	= '.popup_modal_' + base.instance;

			var animEndEventNames = {
				'WebkitAnimation' : 'webkitAnimationEnd',
				'OAnimation' : 'oAnimationEnd',
				'msAnimation' : 'MSAnimationEnd',
				'animation' : 'animationend'
			};
			base.animEndEventName = animEndEventNames[ Modernizr.prefixed('animation') ];

			base.support = {
				animations: Modernizr.cssanimations,
				touch : Modernizr.touch,
				csstransitions : Modernizr.csstransitions
			};
			base.eventtype = base.support.touch ? 'touchstart' : 'click';
			base.ajaxLoad();
		},
		ajaxLoad: function () {
			this.body.on('click', this.el, $.proxy(function (e) {
				
				if (!this.scope) {
					this.loadPopup(e);
				}
				this.scope = true;
			}, this));
		},
		loadPopup: function (e) {
			e.preventDefault();

			var base = this,
				el = $(e.target),
				data = el.data();
			data.action = base.options.actionpopup;
			data._wpnonce = base.options.noncepopup;

			if (data.action == undefined) return;

			$.ajax({
				type: "POST",
				url: woocommerce_mod.ajaxurl,
				data: data,
				beforeSend: function() {
					el.block({
						message: null,
						overlayCSS: {
							background: '#fff url(' + cosyone_global.ajax_loader_url + ') no-repeat center',
							backgroundSize: '16px 16px',
							opacity: 0.6
						}
					});
				},
				success: function (response) {
					el.unblock();

					if (response.match('exit')) {
						response = response.replace('exit', '');
						base.modal	= $('<div class="popup-modal modal-show"></div>');
						base.overlay = $('<div class="popup-modal-overlay"></div>');
						base.body.append(base.modal).append(base.overlay);
						base.modal.append(response);
						base.container = $(response).eq(0);
						base.onLoadCallback();
						base.behavior();
					}
				}
			});

		},
		closeModal: function () {
			var base = this;
			base.modal.removeClass('modal-show');

			setTimeout( function() {
				base.modal.addClass('modal-hide');
			}, 25);

			var onEndAnimationFn = function () {
				base.modal.add(base.overlay).remove();
				base.doc.off('keydown' + base.namespace);
			};

			if (base.support.animations) {
				base.modal.on( base.animEndEventName, onEndAnimationFn );
			} else {
				onEndAnimationFn();
			}
			base.scope = false;
			$.cosyone_popup_prepare.openInstance.shift();

		},
		behavior: function () {
			var base = this;

			$('.close', base.modal).add(base.overlay).on(base.eventtype, function (e) {
				e.preventDefault();
				base.closeModal();
			});

			base.doc.on('keydown ' + base.namespace, function (e) {
				var keycode = e.keyCode;
				switch (keycode) {
					case 27:
						setTimeout(function () {
							base.closeModal();
						}, 25);
						e.stopImmediatePropagation();
						break;
				}
			});
		},
		onLoadCallback: function () {
			var callback = this.options.on_load;
			if (typeof callback == 'function') {
				callback.call(this);
			}
		}
	}

})(jQuery);
