
	
$(function () {



	$(".addStepBtn").on("mouseenter", function(e){
		$(this).css('cursor', 'pointer');
	});

	
	$(document).on("click", ".addStepBtn", function(e){
		e.preventDefault();
		var stepOrderNum = parseInt($("table").find("tr").find("td").find("span").last().text());
		console.log(stepOrderNum);
		stepOrderNum++;
		var stepsInsertHTML = 
			'<tr class="row">' + 
				'<td class="col-lg-1"><span>' + stepOrderNum + '</span><input name="recipeStepIDs" type="hidden" value=""><input name="recipeStepOrders" type="hidden" value="' + stepOrderNum + '"></td>' + 
				'<td class="col-lg-6"><label><textarea name="recipeStepTexts" placeholder="請輸入步驟說明" rows="5" cols="40"></textarea></label></td>' + 
				'<td class="col-lg-4"><input type="file" name="recipeStepPic' + stepOrderNum + '"></td>' + 
				'<td class="col-lg-1"><i class="fas fa-times"></i></td>' + 
			'</tr>'
			;
		$(".recipeStepsTable").find("tbody").append(stepsInsertHTML);
//		$(".recipeStepsTable").find("tbody").find("input[type='file']").last().attr('name', 'recipeStepPics' + stepOrderNum);
	});
	
	

	$("tr.row").on("click", "svg", function(){
		var stepOrderElements = $("table").find("tr").find("td").find("span");
		var stepOrderInput = $("table").find("tr").find("td").find("input[name='recipeStepOrders']");
		var stepOrderUpload = $("table").find("tr").find("td").find("input[type='file']");
		$(stepOrderElements).each(function(index, element) {
			$(element).text(index + 1);
		});
		$(stepOrderInput).each(function(index, element) {
			$(element).val(index + 1);
		});
		$(stepOrderUpload).each(function(index, element) {
			$(element).attr('name', index + 1);
		});
		$(this).closest("tr").remove();
	});
	
});