
$(function () {
	
//	function textLenChange() {
//		var len = 41; // 超過?個字以"..."取代
//		// $(this).width()) 與 document.body.clientWidth) 相等
//		// window.innerWidth - document.documentElement.clientWidth 為捲軸寬度
////		var sizeNum = $(this).width() + (window.innerWidth - document.documentElement.clientWidth);
////		console.log(sizeNum);
////		if (sizeNum < 575) {
////			len = 41;
////		} else if(sizeNum >= 576) {
////			len = 50;
////		} else if(sizeNum >= 992) {
////			len = 73;
////		} else if(sizeNum >= 1200) {
////			len = 52;
////		}
//
//		$(".intro-text").each(function(i){
//			if($(this).text().length > len){
//				$(this).attr("title", $(this).text());
//				var text = $(this).text().substring(0, len-1)+ "...";
//				$(this).text(text);
////				$(this).attr("style", "vertical-align: middle;");
//			}
//		});
//	}
//	
//	textLenChange();
//	
////	$(window).resize(function() {
////		textLenChange();
////	});
	
	$(".intro-text").each(function(){
		  var styles = window.getComputedStyle(this, null);
		  var line_height = parseInt(styles.lineHeight, 10);
		  var height = parseInt(styles.height, 10);
		  var line_count = Math.round(height / line_height);

		  if (line_count > 3) {
			  $(this).attr("title", $(this).text());
				var text = $(this).text().substring(0, 54)+ "...";
				$(this).text(text);
		  }
	});

	$(".recipe-block").on("mouseenter", function() {
		$(this).addClass("mouseenter");
	});
	
	$(".recipe-block").on("mouseleave", function() {
		$(this).removeClass("mouseenter");
	});
//
//	$(".img-outer").on("mouseenter", function () {
//		$(this).css("cursor", "pointer");
//	});
//	
//	$(".img-outer").on("mouseleave", function () {
//		$(this).css("cursor", "auto");
//	});
	
	$(".img-outer").on("click", function () {
		$(this).closest(".recipe-block").find(".readmore").find("a")[0].click();
	});
});