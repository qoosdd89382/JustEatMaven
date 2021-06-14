
	
$(function () {

//	$(".addStepBtn").on("mouseenter", function(e){
//		$(this).css('cursor', 'pointer');
//	});

	
	$(document).on("click", "#addStepBtn", function(e){
		e.preventDefault();
		if ($("table").find("tr.recipe").length == 1) {
			$("table").find("tr.recipe").find("td").last().html('<i class="fas fa-times"></i>');
		}
		var stepOrderNum = parseInt($("table").find("tr.recipe").find("td").find("span").last().text());
//		console.log(stepOrderNum);
		stepOrderNum++;
		var stepsInsertHTML = 
			'<tr class="form-group recipe row">' + 
				'<td class="col-6 order-1 col-lg-1 order-lg-1"><span>' + stepOrderNum + '</span><input name="recipeStepIDs" type="hidden" value=""><input name="recipeStepOrders" type="hidden" value="' + stepOrderNum + '"></td>' + 
				'<td class="col-12 order-3 col-lg-6 order-lg-2"><textarea class="form-control" name="recipeStepTexts" placeholder="請輸入步驟說明" rows="5" cols="40"></textarea></td>' + 
				'<td class="col-12 order-4 col-lg-4 order-lg-3"><input class="form-control-file" type="file" name="recipeStepPic"></td>' + 
				'<td class="col-6 order-2 col-lg-1 order-lg-4"><i class="fas fa-times"></i></td>' + 
			'</tr>'
			;
		$(".recipeStepsTable").find("tbody").append(stepsInsertHTML);
//		$(".recipeStepsTable").find("tbody").find("input[type='file']").last().attr('name', 'recipeStepPics' + stepOrderNum);
	});
	
	
	// 待修正
	$("table").on("click", "svg", function(){
//		console.log($("table").find("tr.recipe").first());
//		console.log($(this).closest("tr"));
//		console.log($("table").find("tr.recipe").length);
		if ($("table").find("tr.recipe").length != 1) {
			$(this).closest("tr.recipe").remove();
			var stepOrderSpan = $("table").find("tr.recipe").find("td").find("span");
			var stepOrderInput = $("table").find("tr.recipe").find("td").find("input[name='recipeStepOrders']");
			var stepOrderUpload = $("table").find("tr.recipe").find("td").find("input[type='file']");
			$(stepOrderSpan).each(function(index, element) {
				$(element).html(index + 1);
			});
			$(stepOrderInput).each(function(index, element) {
				$(element).val(index + 1);
			});
			$(stepOrderUpload).each(function(index, element) {
				$(element).attr('name', "recipeStepPic" + (index + 1));
			});
			if ($("table").find("tr.recipe").length == 1) {
				var lastStepOrderDel = $("table").find("tr").find("td").find("svg").first().closest("td");
				lastStepOrderDel.html("<font color='gray'><i class='fas fa-times'></i></font>");
			}
		}
	});
	
});