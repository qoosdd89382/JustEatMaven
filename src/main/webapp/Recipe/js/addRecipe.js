$(function () {

    var preview_html = `<span id="preview_text" class="text">預覽圖</span>`;
    var top_img_html = `<img id="top_img" class="preview_img" src="#"></img>`;

    if (sessionStorage.getItem("form_data") != null) {
    	session_history = JSON.parse(sessionStorage.getItem("form_data"));
		
    	if (session_history.top_img != null) {
            picTopUploadPreview.innerHTML = null;
            picTopUploadPreview.insertAdjacentHTML('afterbegin', top_img_html);

            document.querySelector("#top_img").src = session_history.top_img;
            img_base64 = session_history.top_img;
    	}
    	
    	if (session_history.step_pic != null) {
    		session_history.step_pic.forEach(function(element, index) {
    			if (element != null) {
    				$($('.picStepPreview')[index]).empty().append('<img src="'+ element +'" class="step_img preview_img">');
    			}
        	});
    	}
    }
//    var formData = new FormData();
    function previewerTopPic(file) {
		let file_reader = new FileReader();
        file_reader.readAsDataURL(file);
        
        file_reader.addEventListener("load", function (e) {
            // load事件用target
            var top_preview_text = document.querySelector('#picTopUploadText');
            var top_img = document.querySelector('#top_img');
            
            var img_ele = document.createElement('img');
            img_ele.id = 'top_img';
            img_ele.src = e.target.result;
            img_ele.setAttribute('class', 'preview_img');
            
            if ((img_ele == null && top_preview_text != null) || top_preview_text != null) {
            	picTopUploadPreview.replaceChild(img_ele, top_preview_text);
            } else {
            	picTopUploadPreview.replaceChild(img_ele, top_img);
            }
        });
        

//		let file_reader2 = new FileReader();
//        
//        file_reader2.readAsArrayBuffer(file);
//        file_reader2.addEventListener("load", function (e) {
//        	console.log(e.target.result);
//        	var blob = new Blob([file_reader2.result]);		// , { type: 'image/jpeg' }
////        	var array = new Int8Array(data);
////        	console.log(URL.createObjectURL(blob));
////        	formData.append("", blob);
//  
//        });
        
    };


    document.querySelector("input[name='recipePicTop']").addEventListener("change", function (e) {
        // change事件用target
        previewerTopPic(e.target.files[0]);
    });
    
    $('#picTopUploadBtn').on("click", function() {
    	$('input[name="recipePicTop"]').trigger("click");
    });
    
    function previewerStepPic(event, file) {
    	let file_reader = new FileReader();
        file_reader.readAsDataURL(file);
        
        file_reader.addEventListener("load", function (e) {
            // load事件用target
            
            $(event).closest('td').find('.preview').empty().append('<img src="'+ e.target.result +'" class="step_img preview_img">');
            
        });
    };
    
    $(document).on("change", "input[name='recipeStepPic']", function (e) {
        // change事件用target
        previewerStepPic(this, e.target.files[0]);
    });
    
    $(document).on("click", '.picStepUploadBtn', function() {
    	$(this).closest('td').find('input[name="recipeStepPic"]').trigger("click");
    });
    

    btnSubmit.addEventListener("click", function() {
    	var session_history = new Object();
    	var step_pic = new Array();
    	
        if (document.querySelector('#top_img') != null) {
            session_history['top_img'] = document.querySelector('#top_img').getAttribute("src");
        }
        if (document.querySelectorAll('.step_img') != null) {
        	document.querySelectorAll('.picStepPreview').forEach(function(element, index) {
        		step_pic.push($(element).find('img.step_img').attr("src"));
        	});
        	session_history.step_pic = step_pic;
        }
        sessionStorage.setItem("form_data", JSON.stringify(session_history));
        
        
    });
    
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
	
	
	
});