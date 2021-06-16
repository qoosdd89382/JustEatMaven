$(function () {
	
	$(document).on("click", "#addStepBtn", function(e){
		e.preventDefault();
		
		if ($("table").find("tr.recipe").length == 1) {
			$("table").find("tr.recipe").find("td").last().html('<i class="fas fa-times"></i>');
		}
		
		var stepOrderNum = parseInt($("table").find("tr.recipe").find("td").find("span.order").last().text());
		stepOrderNum++;
		var stepsInsertHTML = 
			'<tr class="form-group recipe row">' + 
				'<td class="col-6 order-1 col-lg-1 order-lg-1">' + 
					'<span class="order">' + stepOrderNum + '</span>' + 
					'<input name="recipeStepIDs" type="hidden" value="">' + 
					'<input name="recipeStepOrders" type="hidden" value="' + stepOrderNum + '">' + 
					'<input name="oldFileIdentify" type="hidden" value="false"></td>' +
				'<td class="col-12 order-3 col-lg-6 order-lg-2">' + 
					'<textarea class="form-control" name="recipeStepTexts" placeholder="請輸入步驟說明" rows="5" cols="40"></textarea></td>' + 
				'<td class="col-12 order-4 col-lg-4 order-lg-3">' + 
					'<div class="picStepUploadBtn uploadBtn btn btn-primary col-12">上傳圖片</div>' + 
					'<input type="file" class="form-control-file col-12" name="recipeStepPic" style="display:none" multiple="multiple">' + 
					'<div class="picStepPreview preview col-12"><span class="text">預覽圖</span></div>' + 
				'<td class="col-6 order-2 col-lg-1 order-lg-4">' + 
					'<i class="fas fa-times"></i></td>' + 
			'</tr>';
		
		$(".recipeStepsTable").find("tbody").append(stepsInsertHTML);
	});
	
	
	$("table").on("click", "svg", function(){
		if ($("table").find("tr.recipe").length != 1) {
			$(this).closest("tr.recipe").remove();
			var stepOrderSpan = $("table").find("tr.recipe").find("td").find("span.order");
			var stepOrderInput = $("table").find("tr.recipe").find("td").find("input[name='recipeStepOrders']");
//			var stepOrderUpload = $("table").find("tr.recipe").find("td").find("input[type='file']");
			$(stepOrderSpan).each(function(index, element) {
				$(element).html(index + 1);
			});
			$(stepOrderInput).each(function(index, element) {
				$(element).val(index + 1);
			});
//			$(stepOrderUpload).each(function(index, element) {
//				$(element).attr('name', "recipeStepPic");
//			});
			if ($("table").find("tr.recipe").length == 1) {
				var lastStepOrderDel = $("table").find("tr").find("td").find("svg").first().closest("td");
				lastStepOrderDel.html("<font color='gray'><i class='fas fa-times'></i></font>");
			}
		}
	});
	
});