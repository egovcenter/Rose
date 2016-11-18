function draft_debug(msg){
	if(window.console) window.console.log(msg);
}

var adjustment_signer_list_table;
var isReverseOrder = true;
var signerListTableId = "signer_list_table";
var recipientListTableId = "recipient_list_table";
var attachListTableId = "attach_list_table";
var draftBodyId = "draft_body";
var fileCountLimit = 10;
var fileLengthLimit = 1024 * 1024 * 100; 
var submitted = false;
var delay = 1000;
var textareaId = "content";

function getItemFromObj(thisObj, readonly, type){
	var id = $(thisObj).val();
	if(!id){
		return false;
	}
	
	if(type == 'recipient'){
		var name = $("input[name=recipient_deptname]", $(thisObj).parent().parent()).val();
		
		return {"ID": id, "name": name, "type": "dept", "readonly" : readonly};
	}else{
		var name = $("input[name=signer_signername]", $(thisObj).parent().parent()).val();
		var deptId = $("input[name=signer_deptid]", $(thisObj).parent().parent()).val();
		var deptName = $(".signer_deptname", $(thisObj).parent().parent()).text();
		var positionName = $(".signer_positionname", $(thisObj).parent().parent()).text();
		var signerDate = $(".signer_date", $(thisObj).parent().parent()).text();
		var signerOpinion = $(".signer_opinion", $(thisObj).parent().parent()).text();
		
		return {"ID": id, "name": name, "deptName": deptName, "deptId": deptId, 
				"positionName" : positionName, "signerDate" : signerDate, "signerOpinion" : signerOpinion, 
				"type": "user", "readonly" : readonly};
	}
}
function itemFormatter(item){
	var name = item.name;

	if(item.type == 'user'){
		var result =  '<li>';
		result += name + ' (' +  item.deptName + ')';
		result += '</li>';
		return result;
	}else if(item.type == 'dept'){
		return '<li>' + name + '</li>';
	}
	return '<li>' + name + '</li>';
}
function applyItemOnAdd(thisObj, item, type){
	if('signer' == type){
		$(".signer_deptname", $(thisObj).parent().parent()).text(item["deptName"]);
		$(".signer_positionname", $(thisObj).parent().parent()).text(item["positionName"]);
		$("input[name=signer_deptid]", $(thisObj).parent().parent()).val(item["deptId"]);
		$("input[name=signer_signername]", $(thisObj).parent().parent()).val(item["name"]);
		$(".signer_date", $(thisObj).parent().parent()).val(item["signerDate"]);
		$(".signer_opinion", $(thisObj).parent().parent()).text(item["signerOpinion"]);
		$(thisObj).val(item["ID"]);
	}else if('recipient' == type){
		$(".recipient_deptname", $(thisObj).parent().parent()).text(item["name"]);
		$(thisObj).val(item["ID"]);
	}
}
function applyItemOnUpdate(thisObj, item, type){
	if('signer' == type){
		$(".signer_deptname", $(thisObj).parent().parent()).text(item["deptName"]);
		$(".signer_positionname", $(thisObj).parent().parent()).text(item["positionName"]);
		$("input[name=signer_deptid]", $(thisObj).parent().parent()).val(item["deptId"]);
		$("input[name=signer_signername]", $(thisObj).parent().parent()).val(item["name"]);
		$(".signer_date", $(thisObj).parent().parent()).val(item["signerDate"]);
		$(".signer_opinion", $(thisObj).parent().parent()).text(item["signerOpinion"]);
		$(thisObj).val(item["ID"]);
	}else if('recipient' == type){
		$(".recipient_deptname", $(thisObj).parent().parent()).text(item["name"]);
		$(thisObj).val(item["ID"]);
	}
}
function applyItemOnDelete(thisObj, item, type){
	if('signer' == type){
		$(".signer_deptname", $(thisObj).parent().parent()).text("");
		$(".signer_positionname", $(thisObj).parent().parent()).text("");
		$("input[name=signer_deptid]", $(thisObj).parent().parent()).val("");
		$("input[name=signer_signername]", $(thisObj).parent().parent()).val("");
		$(".signer_date", $(thisObj).parent().parent()).val("");
		$(".signer_opinion", $(thisObj).parent().parent()).text("");
		$(thisObj).parent().parent().remove();
	}else if('recipient' == type){
		$(".recipient_deptname", $(thisObj).parent().parent()).text("");
		$(thisObj).parent().parent().remove();
	}
}

// Will be deleted
function applyTokenInput(thisObj, type){
	draft_debug("applyTokenInput thisObj["+thisObj+"], readonly["+$(thisObj).attr("readonly")+"]");
	if($(thisObj).attr("readonly")){
		var thisToken = getItemFromObj(thisObj, true, type);
		
		draft_debug("applyTokenInput readonly thisToken["+thisToken+"]");
		
		var prePopulate = false;
		if(thisToken){
			prePopulate = new Array();
			prePopulate.push(thisToken);
		}
		draft_debug("applyTokenInput readonly prePopulate["+prePopulate+"]");

		$(thisObj).tokenInput(prePopulate, {
				theme: 'facebook',
				tokenLimit: 1,
				resultsFormatter: itemFormatter,
				prePopulate : prePopulate,
				onAdd: function (item){
					draft_debug("onAdd item["+item+"]");
					applyItemOnAdd(thisObj, item, type);
				}
			}
		);
		$(thisObj).tokenInput("add", thisToken);
	}else{
		var thisToken = getItemFromObj(thisObj, false, type);
		draft_debug("applyTokenInput thisToken["+thisToken+"]");
		var prePopulate = false;
		if(thisToken){
			prePopulate = new Array();
			prePopulate.push(thisToken);
		}
		draft_debug("applyTokenInput prePopulate["+prePopulate+"]");
		
		$(thisObj).tokenInput(
				APPROVAL_CONTEXT+'/suggest.do?userId='+userId+'&type=' + ('signer' == type ? 'user' : 'dept'), {
					method: 'POST',
					queryParam: 'name',
					minChars: 2,
					matchCase: false,
					theme: 'facebook',
					tokenLimit: 1,
					resultsFormatter: itemFormatter,
					prePopulate : prePopulate,
					onAdd: function (item){
						draft_debug("onAdd item["+item+"]");
						applyItemOnAdd(thisObj, item, type);
					},
					onUpdate: function (item){
						draft_debug("onUpdate item["+item+"]");
						applyItemOnUpdate(thisObj, item, type);
					},
					onDelete: function (item, target){
						draft_debug("onUpdate item["+item+"]");
						applyItemOnDelete(thisObj, item, type);
					}
				}
		);
		if(thisToken){
			$(thisObj).tokenInput("add", thisToken);
		}
	}
}
jQuery(function() {
	$("input.signer_userid[type=text]").each(function(index){
		applyTokenInput(this, 'signer');
	});
	$("input.recipient_deptid[type=text]").each(function(index){
		applyTokenInput(this, 'recipient');
	});
	$("input[type=text][name=draft_title]").each(function(index){
		$(this).change( function(){
			var that = this;
			$("input[type=text][name=draft_title]").each(function(index){
				if(this != that){
					$(this).val($(that).val());
				}
			});
		});
	});
	
	$("#signer_list_table tbody").sortable({
		items: "tr:not(.unsortable)",
        start: function(event, ui) {
            var start_pos = ui.item.index();
            ui.item.data('start_pos', start_pos);
            draft_debug("start start_pos["+start_pos+"]");
        },
        change: function(event, ui) {
            var start_pos = ui.item.data('start_pos');
            var index = ui.placeholder.index();
            ui.item.data('change_pos', index);
            draft_debug("change start_pos["+start_pos+"], index["+index+"]");
            if (start_pos < index) {
                $('#sortable li:nth-child(' + index + ')').addClass('highlights');
            } else {
                $('#sortable li:eq(' + (index + 1) + ')').addClass('highlights');
            }
        },
        update: function(event, ui) {
            var start_pos = ui.item.data('start_pos');
            var change_pos = ui.item.data('change_pos');
        	draft_debug("update start_pos["+start_pos+"], change_pos["+change_pos+"]");
        	updateSignerForSorting(start_pos, change_pos);
            $('#sortable li').removeClass('highlights');
        }
    }).disableSelection();
});
function updateSignerForSorting(startPos, changePos){
	draft_debug("updateSingerForSorting startPos["+startPos+"], changePos["+changePos+"]");
	// TODO for parallel agree
	if(startPos < changePos){
		var startSeq = parseInt($("#"+signerListTableId+" tbody tr:eq("+changePos+") .signer_seq").text(), 10);
		draft_debug("updateSingerForSorting (startPos < changePos) startSeq["+startSeq+"]");
		$($("#"+signerListTableId+" tbody tr").slice(startPos, changePos+1).get().reverse()).each(function(index){
			draft_debug("updateSingerForSorting startSeq 1["+startSeq+"]");
			$(this).find(".signer_seq").text(startSeq++);
			draft_debug("updateSingerForSorting changeSeq 1["+$(this).find(".signer_seq").text()+"]");
		});
	}else if(startPos > changePos){
		var startSeq = parseInt($("#"+signerListTableId+" tbody tr:eq("+changePos+") .signer_seq").text(), 10);
		draft_debug("updateSingerForSorting (startPos > changePos) startSeq["+startSeq+"]");
		$($("#"+signerListTableId+" tbody tr").slice(changePos, startPos+1).get().reverse()).each(function(index){
			draft_debug("updateSingerForSorting startSeq 1["+startSeq+"]");
			$(this).find(".signer_seq").text(startSeq++);
			draft_debug("updateSingerForSorting changeSeq 1["+$(this).find(".signer_seq").text()+"]");
		});
	}
}
function arrangeSignerKind(selectObj){
	/*
	var signerCount = $("#signer_list_table tbody tr").length();
	var thisSignKind = $("option:selected", selectObj).val();
	$($("#signer_list_table tbody tr").get().reverse()).each(function(index){
		var outerIndex = index;
		var signKindSelect = $("select[name=signer_kind]", $(this));
		var signKind = $("option:selected", signKindSelect).val();
		draft_debug("arrangeSignerKind index["+index+"], signKind["+signKind+"], disabled["+$(signKindSelect).is(":disabled")+"]");
		if($(signKindSelect).is(":disabled")){
			return;
		}else{
			if(index == 0){
				$("option", this).each(function(index){
					if($(this).val() != 'SK00' || $(this).val() != 'SK03' || $(this).val() != 'SK04'){
						$(this).hide();
					}
				});
			}else if(index == signerCount - 1){
				$("option", this).each(function(index){
					if($(this).val() == 'SK00' || $(this).val() == 'SK03' || $(this).val() == 'SK04'){
						$(this).hide();
					}else if($(this).val() == 'SK01'){
						$(this).show();
						$(this).attr("selected");
					}else if($(this).val() == 'SK02'){
						$(this).show();
					}
				});
			}else{
				$("option", this).each(function(index){
					if($(this).val() == 'SK00' || $(this).val() == 'SK03' || $(this).val() == 'SK04'){
						$(this).hide();
					}else if($(this).val() == 'SK01'){
						$(this).show();
					}else if($(this).val() == 'SK02'){
						$(this).show();
						$(this).attr("selected");
					}
				});
			}
		}
	});
	*/
}
function createSignerTr(addTokenInput, signer){
	var signtable = $("#signer_list_table");
	var signtable_tr = signtable.find("tbody tr:first");
	var newSignerTr = signtable_tr.clone();
	
	newSignerTr.find("ul.token-input-list-facebook").remove();
	
	newSignerTr.find(".signer_seq").text((signer.seq));
	
	if(signer.signState == 'SS09' || signer.signKind == 'SK00'){
		newSignerTr.addClass("unsortable");
		newSignerTr.find("select[name=signer_kind]").addAttr("disabled");
	}else{
		newSignerTr.removeClass("unsortable");
		newSignerTr.find("select[name=signer_kind]").removeAttr("disabled");
	}
	
	newSignerTr.find("select[name=signer_kind]").val(signer.signKind);
	newSignerTr.find("input[name=signer_id]").val(signer.signerId);
	
	newSignerTr.find("select[name=signer_kind] option").each(function(index){
		$(this).removeAttr("selected");
		$(this).css("display", "inline");
		if(signer.seq != 1){
			if($(this).val() == 'SK00' || $(this).val() == 'SK03' || $(this).val() == 'SK04'){
				$(this).css("display", "none");
			}
		}
		if($(this).val() == signer.signKind) $(this).attr("selected", true);
	});
	
	newSignerTr.find("select[name=signer_kind]").on( "change", function(){
		arrangeSignerKind(this);
	});
	
	newSignerTr.find(".signer_deptname").text(signer.signerDeptName);
	newSignerTr.find(".signer_positionname").text(signer.signerPositionName);
	newSignerTr.find("input[name=signer_signername]").val(signer.signerSignerName);
	newSignerTr.find("input[name=signer_signState]").val(signer.signerSignState);
	newSignerTr.find("input[name=signer_dutyname]").val(signer.signerDutyName);
	
	var signerUserIdInput = newSignerTr.find("input[name=signer_userid]");
	signerUserIdInput.removeAttr("readonly");
	
	signerUserIdInput.val("");
	
	if(addTokenInput){
		applyTokenInput(signerUserIdInput, 'signer');
	}
	
	newSignerTr.insertBefore($("tbody tr:first", signtable));
	
	arrangeSignerKind();
}
function addSigner(addTokenInput){
	var signtable = $("#signer_list_table");
	var signtable_tr = signtable.find("tbody tr:first");
	var lastSeq = 0;
	signtable.find("tbody tr .signer_seq").each(function(index){
		var thisSeq = parseInt($(this).text(), 10);
		lastSeq = lastSeq < thisSeq ? thisSeq : lastSeq;
	});
	var thisSeq = lastSeq + 1;
	var newSignerTr = signtable_tr.clone();
	newSignerTr.removeClass("unsortable");
	newSignerTr.find("ul.token-input-list-facebook").remove();
	newSignerTr.find(".signer_seq").text((thisSeq));
	newSignerTr.find("input[name=signer_id]").val("");
	newSignerTr.find("select[name=signer_kind]").removeAttr("disabled");
	newSignerTr.find("select[name=signer_kind] option").each(function(index){
		$(this).removeAttr("selected");
		$(this).css("display", "inline");
		if(thisSeq != 1){
			if($(this).val() == 'SK00' || $(this).val() == 'SK03' || $(this).val() == 'SK04'){
				$(this).css("display", "none");
			}
		}
	});
	if(thisSeq != 1){
		newSignerTr.find("select[name=signer_kind]").val('SK01');
	}
	
	newSignerTr.find("select[name=signer_kind]").on( "change", function(){
		arrangeSignerKind(this);
	});
	
	newSignerTr.find(".signer_deptname").text("");
	newSignerTr.find(".signer_positionname").text("");
	newSignerTr.find("input[name=signer_signername]").val("");
	newSignerTr.find("input[name=signer_signState]").val("SS03");
	newSignerTr.find("input[name=signer_dutyname]").val("");
	
	var signerUserIdInput = newSignerTr.find("input[name=signer_userid]");
	signerUserIdInput.removeAttr("readonly");
	
	signerUserIdInput.val("");
	
	if(addTokenInput){
		applyTokenInput(signerUserIdInput, 'signer');
	}
	
	newSignerTr.insertBefore($("tbody tr:first", signtable));
	
	arrangeSignerKind();
}
function addRecipient(addTokenInput){
	var recipienttable = $("#"+recipientListTableId);
	var recipienttable_tr = recipienttable.find("tbody tr:first");
	
	var newRecipientTr = recipienttable_tr.clone();
	newRecipientTr.find("ul.token-input-list-facebook").remove();
	newRecipientTr.find("input[name=recipient_recpid]").val("");
	newRecipientTr.find("input[name=recipient_deptid]").val("");
	newRecipientTr.find("input[name=recipient_deptname]").val("");
	
	var recipientDeptIdInput = newRecipientTr.find("input[name=recipient_deptid]");
	recipientDeptIdInput.removeAttr("readonly");
	recipientDeptIdInput.val("");
	
	if(addTokenInput){
		applyTokenInput(recipientDeptIdInput, 'recipient');
	}
	
	newRecipientTr.insertBefore($("tbody tr:first", recipienttable));
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
		var signerDocVersion = $(this).find(".signer_docVersion").text();

		signerList.push({"seq" : seq, "signerId": signerId, "signerKind": signerKind, 
			"signerSignState": signerSignState, "signerSignDate" : signerSignDate,
			"signerUserId" : signerUserId, "signerSignerName": signerSignerName, 
			"signerDeptId": signerDeptId, "signerDeptName": signerDeptName, 
			"signerPositionName": signerPositionName, "signerDutyName" : signerDutyName, 
			"signerOpinion" : signerOpinion, "signerDocVersion" : signerDocVersion});
	});
	return signerList;
}

function convertSignState(state) {
	var convertedState;

	switch (state) {
		case "SS00":
			convertedState = "Processing";
			break;
		case "SS01":
			convertedState = "Holding";
			break;
		case "SS02":
			convertedState = "Reject";
			break;
		case "SS03":
			convertedState = "Waiting";
			break;
		case "SS09":
			convertedState = "Finishing";
			break;
	}
	return convertedState;
}

function applySignerList(signerList, redraft){		
	var signtable = $("#signer_list_table");
	
	if (redraft == 'true') {
		// to do....
	}
	
	var signtable_tr = signtable.find("tbody tr:last");
	$("tbody tr", "#signer_list_table").remove();
	
	$(signerList).each(function(index){
		var newSignerTr = signtable_tr.clone();
		newSignerTr.find("ul.token-input-list-facebook").remove();

		var signer = this;
		newSignerTr.find(".signer_seq").text(signer.seq);
		newSignerTr.find(".signer_id").text(signer.signerId);
		newSignerTr.find(".signer_kind").text(signer.signerKind);		
		switch (signer.signerKind) {	// by  http://egloos.zum.com/aretias/v/970593
			case "SK00":
				newSignerTr.find(".signer_kind_text").text("Draft");
				break;
			case "SK01":
				newSignerTr.find(".signer_kind_text").text("Review");
				break;
			case "SK02":
				newSignerTr.find(".signer_kind_text").text("Approval");
				break;
			case "SK03":
				newSignerTr.find(".signer_kind_text").text("Accept");
				break;
			case "SK04":
				newSignerTr.find(".signer_kind_text").text("Redraft");
				break;
		}	

		newSignerTr.find(".signer_signstate").text(signer.signerSignState);
		newSignerTr.find(".signer_userid").text(signer.signerUserId);
		newSignerTr.find(".signer_signername").text(signer.signerSignerName);
		newSignerTr.find(".signer_deptid").text(signer.signerDeptId);
		newSignerTr.find(".signer_deptname").text(signer.signerDeptName);
		newSignerTr.find(".signer_positionname").text(signer.signerPositionName);
		newSignerTr.find(".signer_dutyname").text(signer.signerDutyName);
		newSignerTr.find(".signer_opinion").text(signer.signerOpinion);
		newSignerTr.find(".signer_state_").text(convertSignState(signer.signerSignState));
		
		if(signer.signerSignState == "SS00" || signer.signerSignState == "SS03"){
			newSignerTr.find(".signer_docVersion").text("");
		}
		
		/* 160307_SUJI.H */
		var year = signer.signerSignDate.substring(24, 28);
		var month = signer.signerSignDate.substring(4, 7);
		switch (month) { 
			case 'Jan'  : month = '01'; break;
			case 'Feb'  : month = '02'; break;
			case 'Mar'  : month = '03'; break;
			case 'Apr'  : month = '04'; break;
			case 'May'  : month = '05'; break;
			case 'Jun'  : month = '06'; break;
			case 'Jul'  : month = '07'; break;
			case 'Aug'  : month = '08'; break;
			case 'Sep'  : month = '09'; break;
			case 'Oct'  : month = '10'; break;
			case 'Nov'  : month = '11'; break;
			case 'Dec'  : month = '12'; break;
		}
		var day = signer.signerSignDate.substring(8,10);
		var time = signer.signerSignDate.substring(11, 16);
		var sd = year + "-" + month + "-" + day + " " + time;
		if (sd.trim() == '--') {
			sd = "";
		}
		newSignerTr.find(".signer_signDate").text(sd);
		
		$(signtable).prepend(newSignerTr);
	});
}

function getRecipientList(recipientListTableId){
	var recipientList = new Array();

	$("#"+recipientListTableId+" tbody tr").each(function(index){
		var recipientRecpSeq = $(this).find(".recipient_recpseq").text();
		var recipientRecpId = $(this).find("input[name=recipient_recpid]").val();
		var recipientDeptId = $(this).find("input[name=recipient_deptid]").val();
		var recipientDeptName = $(this).find(".recipient_deptname").text();
		var recipientSendType = $(this).find("input[name=recipient_recpsendtype]").val();
		if(!recipientSendType) recipientSendType = "ST01";
		var recipientRecpInnerFlag = $(this).find("input[name=recipient_recpinnerflag]").val();
		if(!recipientRecpInnerFlag) recipientRecpInnerFlag = "1";
		draft_debug("getRecipientList recipientRecpSeq["+recipientRecpSeq+"], recipientRecpId["+recipientRecpId+"], recipientDeptId["+recipientDeptId+"], recipientDeptName["+recipientDeptName+"], recipientSendType["+recipientSendType+"], recipientRecpInnerFlag,["+recipientRecpInnerFlag+"]");
		if(!recipientDeptId) return;
		
		recipientList.push({"recpSeq" : recipientRecpSeq, "recpId": recipientRecpId, "deptId": recipientDeptId, "recpDeptNm" : recipientDeptName, "recpSendType": recipientSendType, "recpInnerFlag": recipientRecpInnerFlag});
	});
	return recipientList;
}
var strRecipientTr = "<tr>"
+"	<td>"
+"		<div class=\"recipient_recpseq\" value=\"\"></div>"
+"	</td>"
+"	<td>"
+"		<div class=\"recipient_deptname\" value=\"\"></div>"
+"	</td>"
+"	<td>"
+"		<div class=\"recpinnerflagtext\" value=\"\"></div>"
+"	</td>"
+"	<td>"
+"		<div class=\"recipient_recpsendtypetext\" value=\"\"></div>"
+"		<input class=\"recipient_recpsendtype\"  name=\"recipient_recpsendtype\"  type=\"hidden\" value=\"\">"
+"		<input class=\"recipient_recpinnerflag\"  name=\"recipient_recpinnerflag\" type=\"hidden\" value=\"\">"
+"		<input class=\"recipient_recpid\"  name=\"recipient_recpid\"  type=\"hidden\" value=\"\">"
+"		<input class=\"recipient_deptid\"  name=\"recipient_deptid\"  type=\"hidden\" value=\"\">"
+"	</td>"
+"</tr>";
function applyRecipientList(recipientList){
	draft_debug("applyRecipientList recipientList["+recipientList+"]");
	var recipienttable = $("#"+recipientListTableId);
	draft_debug("applyRecipientList recipientListTableId["+recipientListTableId+"], recipienttable["+recipienttable+"]");
	var recipient_tr = $(strRecipientTr);
	draft_debug("applyRecipientList recipient_tr["+recipient_tr+"]");
	$("tbody tr", "#"+recipientListTableId).remove();
	
	$(recipientList).each(function(index){
		draft_debug("applyRecipientList this["+this+"]");
		var newRecipientTr = recipient_tr.clone();
		var recipient = this;
		
		newRecipientTr.find("ul.token-input-list-facebook").remove();
		newRecipientTr.find(".recipient_recpseq").text(recipient.recpSeq);
		newRecipientTr.find(".recipient_deptname").text(recipient.recpDeptNm);
		newRecipientTr.find(".recpinnerflagtext").text(recpInnerFlag[recipient.recpInnerFlag]);
		newRecipientTr.find(".recipient_recpsendtypetext").text(sendType[recipient.recpSendType]);
		newRecipientTr.find("input[name=recipient_recpsendtype]").val(recipient.recpSendType);
		newRecipientTr.find("input[name=recipient_recpinnerflag]").val(recipient.recpInnerFlag);
		newRecipientTr.find("input[name=recipient_recpid]").val(recipient.recpId);
		newRecipientTr.find("input[name=recipient_deptid]").val(recipient.deptId);
		
		var recipientDeptIdInput = newRecipientTr.find("input[name=recipient_deptid]");
		if(recipient.recpInnerFlag == '2'){
			recipientDeptIdInput.prop("readonly", true);
		}else{
			recipientDeptIdInput.prop("readonly", false);
		}
		recipientDeptIdInput.val(recipient.deptId);
		
		$("tbody:first", recipienttable).append(newRecipientTr);
	});
}
function mergetHtml(targetSignTableId, html){
	$("#"+targetSignTableId).html(html);
}

function applySignTable(){
	var targetSignTableId = "draft_sign";
	var signerList = getSignerList(signerListTableId);
	var signerList4Json = JSON.stringify(signerList);
	$.ajax({                        
		type: "POST",
		url: APPROVAL_CONTEXT +"/merge.do",
		dataType: 'html',
		cache: false,
		data:{"docId": docId, "formId": formId, "type": "sign", "targetId": targetSignTableId, "signerList" : signerList4Json},
		success: function(data) {
			mergetHtml(targetSignTableId, data); 
		},
		error : function(jqXHR, textStatus, errorThrown){
			alert("applySignTable error. textStatus["+textStatus+"], errorThrown["+errorThrown+"]");
			draft_debug("applySignTable error. textStatus["+textStatus+"], errorThrown["+errorThrown+"]");
		}
    });	
}
function applyIncomingSignTable(){
	var targetSignTableId = "recv_sign";
	var signerList = getSignerList(signerListTableId);
	var signerList4Json = JSON.stringify(signerList);
	if(docId == "no_doc_id"){
		docId = orgdocId;
	}
	$.ajax({                        
		type: "POST",
		url: APPROVAL_CONTEXT +"/merge.do",
		dataType: 'html',
		cache: false,
		data:{"docId": docId, "formId": formId, "type": "sign", "targetId": targetSignTableId, "signerList" : signerList4Json},
		success: function(data) {
			mergetHtml(targetSignTableId, data); 
		},
		error : function(jqXHR, textStatus, errorThrown){
			alert("applyIncomingSignTable error. textStatus["+textStatus+"], errorThrown["+errorThrown+"]");
			draft_debug("applySignTable error. textStatus["+textStatus+"], errorThrown["+errorThrown+"]");
		}
    });
	
}
function getAttachList(){
	var attachList = new Array();
	$("#"+attachListTableId+" tbody tr input[type=checkbox][name=attachId]").each(function(index){
		//var recipientRecSeq = $(this).find(".recipient_recpseq").text();
		var attachId = $(this).val();
		attachList.push({/*"seq" : recipientRecSeq,*/ "attachId": attachId});
	});
	return attachList;
}
function validSignerList(signerList){
	if($('input[name=draft_title]').val() == ''){
		alert(appvl_draft_notitle);
		$('input[name=draft_title]').focus();
		return false;
	}
	if($('#selectlabelNm').val() == ''){
		alert(appvl_draft_nolabel);
		$('#selectlabelNm').focus();
		return false;
	}
	if(signerList.length >= 1){
		var noApproval = true;
		for(i=0; i<signerList.length; i++){
			if(signerList[i]['signerKind'] == 'SK02'){
				noApproval = false;
			}
		}
		if(noApproval == true){
			alert(appvl_invalid_signerList_noapprover);
			return false;
		}
	}
	return true;
}
function assignSignerLine(){
	$("body").css("overflow","");
	var signerList = getSignerList(signerListTableId);
	var signerList4Json = JSON.stringify(signerList);
	
	$("#div_popup").load(APPROVAL_CONTEXT+'/signerline.do', 
			{userId: userId, formId: formId, signerList: signerList4Json, redraft: "false", docId: docId},
			function(){
				if (typeof(user_selection_load) != "undefined") {
					user_selection_load();
				}
				$("#div_popup").show();
			}
	);
}
function assignSignerLine4Redraft(){
	$("body").css("overflow","");
	var signerList = getSignerList(signerListTableId);
	var signerList4Json = JSON.stringify(signerList);
	
	$("#div_popup").load(APPROVAL_CONTEXT+'/signerline.do', 
			{userId: userId, formId: formId, signerList: signerList4Json, redraft: "true", docId: docId},
			function(){
				if (typeof(user_selection_load) != "undefined") {
					user_selection_load();
				}
				$("#div_popup").show();
			}
	);
}
function assignRecipient(){
	$("body").css("overflow","");
	var recipientList = getRecipientList(recipientListTableId);
	var recipientList4Json = JSON.stringify(recipientList);
	
	$("#div_popup").load(APPROVAL_CONTEXT+'/recipient.do', 
			{userId: userId, docId: docId, formId: formId, recipientList: recipientList4Json},
			function(){
				if (typeof(assign_recipient_load) != "undefined") {
					assign_recipient_load();
				}
				$("#div_popup").show();
			}
	);
}

function opinionDisabled(type){
	var url = type + "_fn()";
	if (submitted == true) { return; }
	document.getElementById("apply_opinion_button").value = ">>>>>";
	document.getElementById("apply_opinion_button").disabled = true;
	setTimeout(url, 1);
	submitted = true;
}

function composeApprovalInfos(elementId, action) {
	var attachList = getAttachList();
	if(attachList != null){
		var attachList4Json = JSON.stringify(attachList);
		var encodedAttachList = encodeURIComponent(attachList4Json);
		var attachListElem = "<input type=\"hidden\" name=\"attachList\" id=\"attachList\" value=\"" + encodedAttachList + "\" />";
		$("#" + elementId).append(attachListElem);
	}

	var signerList = getSignerList(signerListTableId);
	var signerList4Json = JSON.stringify(signerList);
	var encodedSignerList = encodeURIComponent(signerList4Json);
	var signerListElem = "<input type=\"hidden\" name=\"signerList\" id=\"signerList\" value=\"" + encodedSignerList + "\" />";
	$("#" + elementId).append(signerListElem);
	
	if(action == "draft"){
		var docBody = getBodyContent(action);
		var encodedDocBody = encodeURIComponent(docBody);
		var docBodyElem = "<input type=\"hidden\" name=\"docBody\" id=\"docBody\" value=\"" + encodedDocBody + "\" />";
		$("#" + elementId).append(docBodyElem);
	}

	var opinion = document.getElementById("opinion").value;
	var encodedOpinion = encodeURIComponent(opinion);
	var opinionElem = "<input type=\"hidden\" name=\"opinion\" id=\"opinion\" value=\"" + encodedOpinion + "\" />";
	$("#" + elementId).append(opinionElem);
	
	if(action != "approve"){
		var recipientList = getRecipientList(recipientListTableId);
		var recipientList4Json = JSON.stringify(recipientList);
		var encodedRecipientList = encodeURIComponent(recipientList4Json);
		var recipientListElem = "<input type=\"hidden\" name=\"recipientList\" id=\"recipientList\" value=\"" + encodedRecipientList + "\" />";
		$("#" + elementId).append(recipientListElem);
		
		var docTitle = encodeURIComponent($("input[name=draft_title]").val());
		var docTitleElem = "<input type=\"hidden\" name=\"draft_title\" id=\"draft_title\" value=\"" + docTitle + "\" />";
		$("#" + elementId).append(docTitleElem);
		
		var userIdElem = "<input type=\"hidden\" name=\"userId\" id=\"userId\" value=\"" + userId + "\" />";
		$("#" + elementId).append(userIdElem);
	
		var docIdElem = "<input type=\"hidden\" name=\"docId\" id=\"docId\" value=\"" + docId + "\" />";
		$("#" + elementId).append(docIdElem);
	
		var formIdElem = "<input type=\"hidden\" name=\"formId\" id=\"formId\" value=\"" + formId + "\" />";
		$("#" + elementId).append(formIdElem);
		
		var labelId = $("#selectlabelId").val();
		var labelIdElem = "<input type=\"hidden\" name=\"labelId\" id=\"labelId\" value=\"" + labelId + "\" />";
		$("#" + elementId).append(labelIdElem);
		
		var docnum = $("#draft_docnum").val();
		var docNumElem = "<input type=\"hidden\" name=\"draft_docnum\" id=\"draft_docnum\" value=\"" + docnum + "\" />";
		$("#" + elementId).append(docNumElem);
	
		var doc_slvl = $("input[name=doc_slvl]:checked").val();
		var docSecurityLvlElem = "<input type=\"hidden\" name=\"doc_slvl\" id=\"doc_slvl\" value=\"" + doc_slvl + "\" />";
		$("#" + elementId).append(docSecurityLvlElem);
	}
}

function draft_fn() {
	composeApprovalInfos("attach", "draft");
	var resultDocTypeElem = "<input type=\"hidden\" name=\"docType\" id=\"docType\" value=\"ongoing\" />";
	$("#attach").append(resultDocTypeElem);
	$("#attach").submit();
}
function redraft_fn(){
	composeApprovalInfos("attach", "redraft");
	var resultDocTypeElem = "<input type=\"hidden\" name=\"docType\" id=\"docType\" value=\"waiting\" />";
	$("#attach").append(resultDocTypeElem);
	$("#attach").submit();
}
function approve_fn(){
	composeApprovalInfos("attach", "approve");
	var resultDocTypeElem = "<input type=\"hidden\" name=\"docType\" id=\"docType\" value=\"waiting\" />";
	$("#attach").append(resultDocTypeElem);
	$("#attach").submit();
}
function receive_fn(){
	var opinion = document.getElementById("opinion").value;
	if($("#selectlabelNm").val() ==""){
		alert(appvl_draft_nolabel);
		$('#selectlabelNm').focus();
		return false;
	}
	var docBody = getBodyContent("receive");
	var signerList = getSignerList(signerListTableId);

	var signerList4Json = JSON.stringify(signerList);
	
	var attachList = getAttachList();
	var attachList4Json = JSON.stringify(attachList);
	
	var labelId = $("#selectlabelId").val();
	var docnum = $("#draft_docnum").val();
	
	// TODO for other info, title, label, security, and so on. 
	$("#draft_form").submit(function(e){
		var postData = $(this).serializeArray();
		postData.push({"name" : "signerList", "value" : signerList4Json});
		postData.push({"name" : "attachList", "value" : attachList4Json});
		postData.push({"name" : "docBody", "value" : docBody});
		postData.push({"name" : "userId", "value" : userId});
		postData.push({"name" : "docId", "value" : docId});
		postData.push({"name" : "orgdocId", "value" : orgdocId});
		postData.push({"name" : "formId", "value" : formId});
		postData.push({"name" : "labelId", "value" : labelId});
		postData.push({"name" : "draft_docnum", "value" : docnum});
		postData.push({"name" : "opinion", "value" : opinion});
		
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
			},
			error: function(jqXHR, textStatus, errorThrown) 
			{
				draft_debug("draft error textStatus["+textStatus+"], errorThrown["+errorThrown+"]");
				alert("error!-4");
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

	$("#draft_form").submit(); //Submit  the FORM
}

function redraftForIncoming_fn(){
	var opinion = document.getElementById("opinion").value;
	if($("#selectlabelNm").val() ==""){
		alert(appvl_draft_nolabel);
		$('#selectlabelNm').focus();
		return false;
	}
	var docBody = getBodyContent("redraftForIncoming");
	var signerList = getSignerList(signerListTableId);
	var signerList4Json = JSON.stringify(signerList);
	
	var labelId = $("#selectlabelId").val();
	var docnum = $("#draft_docnum").val();
	var docType = "waiting";
	// TODO for other info, title, label, security, and so on. 
	$("#draft_form").submit(function(e){
		var postData = $(this).serializeArray();
		postData.push({"name" : "signerList", "value" : signerList4Json});
		postData.push({"name" : "docBody", "value" : docBody});
		postData.push({"name" : "docId", "value" : docId});
		postData.push({"name" : "orgdocId", "value" : orgdocId});
		postData.push({"name" : "labelId", "value" : labelId});
		postData.push({"name" : "draft_docnum", "value" : docnum});
		postData.push({"name" : "opinion", "value" : opinion});
		postData.push({"name" : "docType", "value" : docType});
		
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
			},
			error: function(jqXHR, textStatus, errorThrown) 
			{
				draft_debug("draft error textStatus["+textStatus+"], errorThrown["+errorThrown+"]");
				alert("error!-4");
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
	$("#draft_form").submit(); //Submit  the FORM
}

function reject_fn(){
	var opinion = document.getElementById("opinion").value;
	var editFlag = document.getElementById("editFlag").value;
	$.ajax({                        
		type: "POST",
		url: APPROVAL_CONTEXT +"/rejected.do",
		dataType: 'html',
		cache: false,
		data:{"userId": userId, "docId": docId, "opinion": opinion, "editFlag": editFlag},
		success: function(data) {
				var formId = "retformPass";	
				var form = "";
				form = $("<form id='"+formId+"' action='"+APPROVAL_CONTEXT+"/approvalDocPageList.do' method='post'></form>");
				form.append("<input type=\"hidden\" name=\"docType\" value=\"waiting\">");
				$('body').append(form);
				$("#"+formId+"").submit();
		},
		error : function(jqXHR, textStatus, errorThrown){
			alert("reject error. textStatus["+textStatus+"], errorThrown["+errorThrown+"]");
			draft_debug("applySignTable error. textStatus["+textStatus+"], errorThrown["+errorThrown+"]");
		}
    });
}

function hold_fn(){
	var opinion = document.getElementById("opinion").value;
	var editFlag = document.getElementById("editFlag").value;
	$.ajax({                        
		type: "POST",
		url: APPROVAL_CONTEXT +"/hold.do",
		dataType: 'html',
		cache: false,
		data:{"userId": userId, "docId": docId, "opinion": opinion, "editFlag": editFlag},
		success: function(data) {
				var formId = "holdform";	
				var form = "";
				form = $("<form id='"+formId+"' action='"+APPROVAL_CONTEXT+"/approvalDocPageList.do' method='post'></form>");
				form.append("<input type=\"hidden\" name=\"docType\" value=\"waiting\">");
				$('body').append(form);
				$("#"+formId+"").submit();
		},
		error : function(jqXHR, textStatus, errorThrown){
			alert("reject error. textStatus["+textStatus+"], errorThrown["+errorThrown+"]");
			draft_debug("applySignTable error. textStatus["+textStatus+"], errorThrown["+errorThrown+"]");
		}
    });
}

function getBodyContent(action){
	$("#"+draftBodyId +" input[type=text]").each(function(){
		  $(this).attr("value", $(this).val());
	});
	
	//delete ckeditor toolbar 
	$('div').remove('#cke_content');
	//replace textarea to div
	if(action == "draft"){
		var ckeditorContent = "<div id='editorContent'>"+CKEDITOR.instances.content.getData()+"</div>";
		$('textarea#content').replaceWith(ckeditorContent);
	}else if(action == "receive" || action == "redraftForIncoming"){
		var ckeditorContent = "<div id='editorContent'>"+document.getElementById("hiddenContent").value+"</div>";
		$('iframe#iframe_content').replaceWith(ckeditorContent);
	}
	return $("#"+draftBodyId).html();
}