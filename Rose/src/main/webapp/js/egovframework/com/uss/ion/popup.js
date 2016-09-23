/* 20160329_SUJI.H */
function applyUserList(userList){		
	if(checkType == "uni"){
		$(userList).each(function(index){
			var user = this;
			$("#selectedUserID").val(user.userId);
			$("#selectedUserNm").val(user.userName);
			$("#selectedDeptNm").val(user.userDeptName);
			$("#selectedDeptId").val(user.userDeptId);
		});
	}else{
		/* 기존 내용 clean */
		$("tbody tr", "#user_list").remove();
		$(userList).each(function(index){
			var user = this;
			/* 새로 지정한 user 추가 */
			$('#user_list > tbody:last').append("<tr><td><input type='hidden' name='recpSendUserID' value='"+user.userId+"'><input type='hidden' name='recpSendUserNm' value='"+user.userName+"'>"+user.userName+"</td></tr>");
		});
	}
}

function selectUser(checkType){
	$("body").css("overflow","");
	
	$("#div_popup").load(APPROVAL_CONTEXT+'/selectUser.do', 
			{userId: userId, checkType:checkType},
			function(){
				if (typeof(select_user_load) != "undefined") {
					select_user_load();
				}
				$("#div_popup").show();
			}
	);
}

function openOrgPopup(checkType){
	$("body").css("overflow","");
	$("#div_popup").load(APPROVAL_CONTEXT+'/orgPopup.do',{checkType: checkType}, 
			function(){
				if (typeof(orgPopup_load) != "undefined") {
					orgPopup_load();
				}
				$("#div_popup").show();
			}
	);
}

function applyOneRecipient(recipientList, checkType){
	if(checkType == "uni"){
		$(recipientList).each(function(index){
			var recipient = this;
			$("#recipient_deptid").val(recipient.recpDeptId);
			$("#recipient_deptname").val(recipient.recpDeptNm);
			$("#deptNm").val(recipient.recpDeptNm);
			$($('input:radio[name=deptId]')[1]).val(recipient.recpDeptId);
		});
	}else{
		/* 기존 내용 clean */
		$("tbody tr", "#recipient_list").remove();
		$(recipientList).each(function(index){
			var recipient = this;
			/* 새로 지정한 recipient 추가 */
			$('#recipient_list > tbody:last').append("<tr><td><input type='hidden' name='recpDeptID' value='"+recipient.recpDeptId+"'><input type='hidden' name='recpDeptNm' value='"+recipient.recpDeptNm+"'>"+recipient.recpDeptNm+" <div class='float_right ui_btn_rapper'><a href='#' onclick='removeRecpient(this)' class='btn_color2'>"+common_button_delete+"</a></div></td></tr>");
		});
	}
}

function addOpinion(type){
	var signerList = getSignerList(signerListTableId);
	if(!validSignerList(signerList)){
		return;
	}
	
	$("body").css("overflow","");
	$("#div_popup").load(APPROVAL_CONTEXT+'/opinionAdd.do', {type: type},
			function(){
		if (typeof(opinion_load) != "undefined") {
			opinion_load();
		}
		$("#div_popup").show();
	}
	);
}

function opinionView(opinion){
	$("body").css("overflow","");
	$("#div_popup").load(APPROVAL_CONTEXT+'/opinionView.do', {opinion: opinion},
			function(){
				if (typeof(opinion_load) != "undefined") {
					opinion_load();
				}
				$("#div_popup").show();
			}
	);
}

function openLabelPopup(labelId,labelNm){
	var labelNm = $("#selectlabelNm").val();
	var labelId = $("#selectlabelId").val();
	$("body").css("overflow","");
	$("#div_popup").load(APPROVAL_CONTEXT+'/selectLabel.do', {labelId: labelId, labelNm:labelNm},
	function(){
		if (typeof(select_label_load) != "undefined") {
			select_label_load();
		}
		$("#div_popup").show();
	}
	);
}
/* 20160329_SUJI.H */