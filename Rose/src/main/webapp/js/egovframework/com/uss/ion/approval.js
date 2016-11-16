function approval_debug(msg){
	if(window.console) window.console.log(msg);
}

var signerListTableId = "signer_list_table";

window.showModalDialog = function (url, arg, feature) {
       var opFeature = feature.split(";");
       var featuresArray = new Array()
       if (document.all) {
	       for (var i = 0; i < opFeature.length - 1; i++) {
		       var f = opFeature[i].split("=");
		       featuresArray[f[0]] = f[1];
	       }
       }
       else{
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
function getSignerList(signerListId){
	var signerList = new Array();

	$($("#"+signerListId+" tbody tr").get().reverse()).each(function(index){
		var seq = $(this).find(".signer_seq").text();
		var signerId = $(this).find(".signer_id").text();
		var signerKind = $(this).find(".signer_kind").text();
		var signerSignState = $(this).find(".signer_signstate").text();
		var signerSignDate = $(this).find(".signer_signdate").text();
		var signerUserId = $(this).find(".signer_userid").text();
		var signerSignerName = $(this).find(".signer_signername").text();
		var signerDeptId = $(this).find(".signer_deptid").text();
		var signerDeptName = $(this).find(".signer_deptname").text();
		var signerPositionName = $(this).find(".signer_positionname").text();
		var signerDutyName = $(this).find(".signer_dutyname").text();
		var signerOpinion = $(this).find(".signer_opinion").text();

		signerList.push({"seq" : seq, "signerId": signerId, "signerKind": signerKind, 
			"signerSignState": signerSignState, "signerSignDate" : signerSignDate,
			"signerUserId" : signerUserId, "signerSignerName": signerSignerName, 
			"signerDeptId": signerDeptId, "signerDeptName": signerDeptName, 
			"signerPositionName": signerPositionName, "signerDutyName" : signerDutyName, 
			"signerOpinion" : signerOpinion});
	});
	return signerList;
}
function changeSignerLine(){
	$("body").css("overflow","");
	var signerList = getSignerList(signerListTableId);
	var signerList4Json = JSON.stringify(signerList);
	$("#div_popup").load(APPROVAL_CONTEXT+'/signerline.do', 
			{userId: userId, formId: formId, signerList: signerList4Json, redraft: "false"},
			function(){
				if (typeof(user_selection_load) != "undefined") {
					user_selection_load();
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
	if($("#register_recp_s_docnum").val() == ""){
		alert(appvl_draft_nodocnum);
		$('#register_recp_s_docnum').focus();
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
function cancel(){
	var editFlag = $("#editFlag").val();
	var formId = "retformPass";	
	var form = "";
	form = $("<form id='"+formId+"' action='"+APPROVAL_CONTEXT+"/approvalDocPageList.do' method='post'></form>");
	form.append("<input type=\"hidden\" name=\"docType\" value=\"waiting\">");
	$('body').append(form);
	
	if(editFlag == "true"){
		$.ajax({                        
			type: "POST",
			url: APPROVAL_CONTEXT +"/deleteTmpDocument.do",
			dataType: 'html',
			cache: false,
			data:{"docId": docId},
			success: function(data) {
				$("#"+formId+"").submit();
			},
			error : function(jqXHR, textStatus, errorThrown){
			}
	    });
	}else{
		$("#"+formId+"").submit();
	}
}