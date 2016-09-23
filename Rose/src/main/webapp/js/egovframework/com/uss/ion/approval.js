function approval_debug(msg){
	if(window.console) window.console.log(msg);
}
window.showModalDialog = function (url, arg, feature) {
        var opFeature = feature.split(";");
       var featuresArray = new Array()
        if (document.all) {
           for (var i = 0; i < opFeature.length - 1; i++) {
                var f = opFeature[i].split("=");
               featuresArray[f[0]] = f[1];
            }
       }
        else {

            for (var i = 0; i < opFeature.length - 1; i++) {
                var f = opFeature[i].split(":");
               featuresArray[f[0].toString().trim().toLowerCase()] = f[1].toString().trim();
            }
       }



       var h = "200px", w = "400px", l = "100px", t = "100px", r = "yes", c = "yes", s = "no";
       if (featuresArray["dialogheight"]) h = featuresArray["dialogheight"];
        if (featuresArray["dialogwidth"]) w = featuresArray["dialogwidth"];
       if (featuresArray["dialogleft"]) l = featuresArray["dialogleft"];
        if (featuresArray["dialogtop"]) t = featuresArray["dialogtop"];
        if (featuresArray["resizable"]) r = featuresArray["resizable"];
       if (featuresArray["center"]) c = featuresArray["center"];
      if (featuresArray["status"]) s = featuresArray["status"];
        var modelFeature = "height = " + h + ",width = " + w + ",left=" + l + ",top=" + t + ",model=yes,alwaysRaised=yes" + ",resizable= " + r + ",celter=" + c + ",status=" + s;

        var model = window.open(url, "", modelFeature, null);

       model.dialogArguments = arg;

    }

function downloadFile(url, docId, attachId, userId){
	var formId = "downloadFileForm";
	var iframId = "downloadFileIFrame";
	if($("#"+iframId).length < 1){
		var iframe = $("<iframe id='"+iframId+"' name='"+iframId+"' width='0' height='0' style='display:none'></iframe>");
		$('body').append(iframe);
	}
	if($("#"+formId).length < 1){
		var form = $("<form id='"+formId+"' action='"+url+"' method='post' target='"+iframId+"'></form>");
		form.append("<input type=\"hidden\" name=\"docId\">");
		form.append("<input type=\"hidden\" name=\"attachId\">");
		form.append("<input type=\"hidden\" name=\"userId\">");
		$('body').append(form);
	}
	$("#"+formId+" input[name='docId']").val(docId);
	$("#"+formId+" input[name='attachId']").val(attachId);
	$("#"+formId+" input[name='userId']").val(userId);
	$("#"+formId+"").submit();
	
}

function displaySignerHistory(){
	$("#div_popup").load(APPROVAL_CONTEXT+'/signerhistory.do', 
			{userId: userId, docId: docId},
			function(){
				if (typeof(display_signerhistory_load) != "undefined") {
					display_signerhistory_load();
				}
				$("#div_popup").show();
			}
	);
}
function displayRecipientHistory(){
	$("#div_popup").load(APPROVAL_CONTEXT+'/recipienthistory.do', 
			{userId: userId, docId: docId},
			function(){
				if (typeof(display_recipienthistory_load) != "undefined") {
					display_recipienthistory_load();
				}
				$("#div_popup").show();
			}
	);
}

function composeRegisterIncomingInfos(elementId) {
	// for remove the character, '$' and ';'
	var recipient_deptid = $("#recipient_deptid").val();
	var recipient_deptname = $("#recipient_deptname").val();
	
	recipient_deptid = recipient_deptid.replace("$", "").replace(";", "");
	recipient_deptname = recipient_deptname.replace("$", "").replace(";", "");
	
	$("#recipient_deptid").val(recipient_deptid);
	$("#recipient_deptname").val(recipient_deptname);
	
	var docTitle = encodeURIComponent($("input[name=register_draft_title]").val());
	var docTitleElem = "<input type=\"hidden\" name=\"register_draft_title\" id=\"register_draft_title\" value=\"" + docTitle + "\" />";
	$("#" + elementId).append(docTitleElem);

	var resultDocTypeElem = "<input type=\"hidden\" name=\"docType\" id=\"docType\" value=\"incoming\" />";
	$("#" + elementId).append(resultDocTypeElem);
}

function registeredIncomming(formID){
	if($("#register_draft_title").val() == ""){
		alert(appvl_draft_notitle);
		$('#register_draft_title').focus();
		return false;
	}
	if($("#recipient_deptname").val() == ""){
		alert(appvl_draft_noregdept);
		$('#recipient_deptname').focus();
		return false;
	}
	
	composeRegisterIncomingInfos(formID);
	$("#" + formID).submit();
}

function composeRegisterInternalInfos(elementId) {
	var docTitle = encodeURIComponent($("input[name=register_draft_title]").val());
	var docTitleElem = "<input type=\"hidden\" name=\"register_draft_title\" id=\"register_draft_title\" value=\"" + docTitle + "\" />";
	$("#" + elementId).append(docTitleElem);

	var resultDocTypeElem = "<input type=\"hidden\" name=\"docType\" id=\"docType\" value=\"label\" />";
	$("#" + elementId).append(resultDocTypeElem);
}

function registeredInternal(formID){
	//title check
	if($("#register_draft_title").val() ==""){
		alert(appvl_draft_notitle);
		$("#register_draft_title").focus();
		return false;
	}
	composeRegisterInternalInfos(formID);
	$("#" + formID).submit();
}

function apprSend(formID){
	if($("#selectedUserNm").val() == ""){
		alert(appvl_outgoing_nosendername);
		$('#selectedUserNm').focus();
		return false;
	}
	if($("#selectedDeptNm").val() == ""){
		alert(appvl_outgoing_nosenderdept);
		$('#selectedDeptNm').focus();
		return false;
	}
	if($(".datetimepicker").val() == ""){
		alert(appvl_outgoing_nodate);
		$('.datetimepicker').focus();
		return false;
	}
	
	$("#"+formID).submit(function(e){
		var postData = $(this).serializeArray();
		
		var formURL = $(this).attr("action");
		$.ajax({
			url : formURL,
			type: "POST",
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
				var formId = "listForm";
				$("#"+formId).remove();
				if($("#"+formId).length < 1){
					var form = "";
					if(from){
						form = $("<form id='"+formId+"' action='"+APPROVAL_CONTEXT+"/"+from+".do' method='post'></form>");
						form.append("<input type=\"hidden\" name=\"docType\" value=\""+from+"\">");
					}else{
						form = $("<form id='"+formId+"' action='"+APPROVAL_CONTEXT+"/approvalDocPageList.do' method='post'></form>");
						form.append("<input type=\"hidden\" name=\"docType\" value=\"outgoing\">");
					}
					
					
					$('body').append(form);
				}
				$("#"+formId+"").submit();
				//history.back();
				//document.location.href = APPROVAL_CONTEXT + "/approvalDocPageList.do?docType=outgoing";
			},
			error: function(jqXHR, textStatus, errorThrown) 
			{
				approval_debug("send error textStatus["+textStatus+"], errorThrown["+errorThrown+"]");
				alert("error!-c");
			    //if fails      
			}
		});
		try{
		e.preventDefault(); //STOP default action
		e.unbind(); //unbind. to stop multiple form submit.
		}catch(e){
			if(window.console)window.console.log(e);
		}
	});

	$("#"+formID).submit(); //Submit  the FORM
}

function apprPass(formID){

	// for remove the character, '$' and ';'
	var recpDeptIDs = "";
	var recpDeptNms = "";
	$("input[name=recpDeptID]").each(function(index){
		$(this).val($(this).val().replace("$", "").replace(";", ""));
		recpDeptIDs += $(this).val() + ",";
	});
	$("input[name=recpDeptNm]").each(function(index){
		$(this).val($(this).val().replace("$", "").replace(";", ""));
		recpDeptNms += $(this).val() + ",";
	});
	
	$("#"+formID).submit(function(e){
		var postData = $(this).serializeArray();
		postData.push({"name" : "recpDeptIDs", "value" : recpDeptIDs});
		postData.push({"name" : "recpDeptNms", "value" : recpDeptNms});
		
		var formURL = $(this).attr("action");
		$.ajax({
			url : formURL,
			type: "POST",
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
				var formId = "retformPass";	
				var form = "";
				form = $("<form id='"+formId+"' action='"+APPROVAL_CONTEXT+"/approvalDocPageList.do' method='post'></form>");
				form.append("<input type=\"hidden\" name=\"docType\" value=\"incoming\">");
				$('body').append(form);
				$("#"+formId+"").submit();
				//history.back();
				//document.location.href = APPROVAL_CONTEXT + "/approvalDocPageList.do?docType=outgoing";
			},
			error: function(jqXHR, textStatus, errorThrown) 
			{
				approval_debug("send error textStatus["+textStatus+"], errorThrown["+errorThrown+"]");
				alert("error!-d");
			    //if fails      
			}
		});
		try{
		e.preventDefault(); //STOP default action
		e.unbind(); //unbind. to stop multiple form submit.
		}catch(e){
			if(window.console)window.console.log(e);
		}
	});

	$("#"+formID).submit(); //Submit  the FORM
}