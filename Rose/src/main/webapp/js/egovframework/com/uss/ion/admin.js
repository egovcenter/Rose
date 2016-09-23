function admin_debug(msg){
	if(window.console) window.console.log(msg);
}
function getContextPath(){
    var offset=location.href.indexOf(location.host)+location.host.length;
    var ctxPath=location.href.substring(offset,location.href.indexOf('/',offset+1));
    return ctxPath;
}
function gotoAdmin(){
	if(typeof(APPROVAL_CONTEXT) != 'undefined'){
		window.top.location.href=APPROVAL_CONTEXT+"/mngForm.do";	
	}else{
		window.top.location.href=getContextPath()+"/mngForm.do";
	}
}
function gotoDeptAdmin(){
	if(typeof(APPROVAL_CONTEXT) != 'undefined'){
		window.top.location.href=APPROVAL_CONTEXT+"/mngLabel.do";	
	}else{
		window.top.location.href=getContextPath()+"/mngLabel.do";
	}
}

function mngForm(){
	window.top.location.href = APPROVAL_CONTEXT+"/mngForm.do";
}
function resizeContentIFrame(){
	$("#"+contentID, window.top.document).height($("#"+contentID, window.top.document).parent().outerHeight());
	$("#"+contentID, window.top.document).width($("#"+contentID, window.top.document).parent().outerWidth());
}

function viewLabel(labelID){	
	resizeContentIFrame();
	$("#"+contentID, window.top.document).load(APPROVAL_CONTEXT+"/viewLabel.do?deptId="+deptId+"&labelID="+labelID);
//	$("#"+contentID, window.top.document).attr("src", APPROVAL_CONTEXT+"/viewLabel.do?deptId="+deptId+"&labelID="+labelID);
//	$("#"+contentID).load(APPROVAL_CONTEXT+"/viewLabel.do",
//			{userID: userID, deptId: deptId, labelID: labelID},
//			function(){
//				if(typeof(viewLabel_onLoad) != 'undefined'){
//					viewLabel_onLoad();
//				}
//			}
//	);
}
function selectLabel(labelID, labelNm, labelParentID){
	/* 수정하는 라벨을 위치기준으로 선택할 수 없음 */
	var originalDeptNm = $("#labelNm").val();
	if(labelNm == originalDeptNm){
		alert(appvl_lable_msg_003);
        return false;
	}

	$("#targetLabelID").val(labelID);
	$("#targetLabelNm").val(labelNm);
	
	closeViewLabelTreeLayer();
}
function closeViewLabelTreeLayer(){
	$("#"+popupLayerID).hide();
}
function historyBack(){
	history.back();
}