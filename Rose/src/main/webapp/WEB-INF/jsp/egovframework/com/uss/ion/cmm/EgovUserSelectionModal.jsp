<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=2, user-scalable=yes" />

<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery-1.7.1.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery-ui-1.11.4.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery.tokeninput.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/org.js'/>"></script>

<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/omt/dynatree-1.2.4/jquery/jquery-ui.custom.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/omt/dynatree-1.2.4/jquery/jquery.cookie.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/js/egovframework/com/uss/omt/dynatree-1.2.4/src/skin/ui.dynatree.css'/>" />
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/omt/dynatree-1.2.4/src/jquery.dynatree.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/egovframework/com/cmm/tabbar.css'/>"/>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/omt/tabbar.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/omt/orgPopup.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/omt/divPopup.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/omt/jquery.tbs.directory.js'/>"></script>
<script>
var APPROVAL_CONTEXT = "${pageContext.servletContext.contextPath}";
var userId = "${user.uniqId}";
var checkType = "${checkType}";
function select_user_load(){
	select_user_load_ui();
	
	var userDeptId = '${user.orgnztId}';
	$("#baseDept").val(userDeptId);    
	
	if(checkType == "uni"){
		$("#selectMode").val('1');
	}else{
		$("#selectMode").val('2');
	}
	
	if ($("#orgTree:empty").length) {
		var activeSelect = false;
		if($("#activeSelect").val() == "1" && $("#selectMode").val() == "1" && $("#checkbox").val() != "list") {
			activeSelect = true;
		}
		$("#orgTree").directorytree({
			idPrefix: "org",
			checkbox: (activeSelect ? false : $("#checkbox").val() != "list"),
			selectMode: $("#selectMode").val(),
			checkMode: $("#checkbox").val(),
			openerType: $("#openerType").val(),
			FRAMEWORK_DIRECTORY_LOCALE : "en",
			onActivate: function(dtnode) {
				if ($("#checkbox").val() != "tree") {
					orgPopup.viewUserList(dtnode.data.key);
				}
				if(activeSelect && !dtnode.isSelected()) {
					dtnode.toggleSelect();
				}
				$('#btnAbs').hide();
			},
			onSelect: function(select, dtnode) {
				var deptType = ($("input[name='subdept']:checked").val() == "true") ? SDEPT : DEPT;
				var o = new Recipient(deptType + dtnode.data.key, deptType + dtnode.data.title);
				orgPopup.onSelect(select, o);
				orgPopup.printRecipientList();
			},
			onDblClick: function(dtnode, event) {
				fncAdd();
			},
			onLazyRead: function(dtnode) {
				orgPopup.expandOrgTree(dtnode);
			},
			init: function() {
				orgPopup.initOrgTree();
				$('#backToList').hide();
			}
		});
	}
	$("#deptSearchValue").keypress(function(event) {
		if (event.keyCode == 13) {
			event.keyCode = 0;
			if (orgPopup.searchDept()) $("#backToList").show();
		}
	});
}
function appr_debug(msg){
	if(window.console) window.console.log(msg);
}
function addUser() {
	appr_debug("addSignerToSignerLine orgPopup.toList.size()["+orgPopup.toList.size()+"]");
	if (orgPopup.toList.size() == 0) {
		alert("not selected.");
		return;
	}
	
	var selectedID =false;
	var userList = new Array();
	
	for (i=0; i<orgPopup.toList.size(); i++) {
		selectedID = orgPopup.toList.get(i).toString("id");
		var userInfo = getUser(selectedID);
		userList.push({"userName" : userInfo.name, "userId": userInfo.ID, "userDeptName":userInfo.deptName, "userDeptId":userInfo.deptId});
	}
	return userList;
}
function closeUser(){
	$("#div_popup").hide();
}
function applyUser(){
	var userList = addUser();
	applyUserList(userList, checkType);
	closeUser();
}
</script>
</head>
<body>
<div class="layerPopup_rapper" style="width: 800px; position:absolute;">
	<p class="popup_title type_approval"><spring:message code="org.user.label.selectuser"/></p>
	<div class="ui_btn_rapper float_right mtb10">
		<a href="javascript:applyUser()" class="btn_color1"><spring:message code="common.button.apply"/></a>
		<a href="javascript:closeUser()" class="btn_color2"><spring:message code="common.button.cancel"/></a>
	</div>
	<div class="tabBox_rapper">
		<div class="tab_box">
			<ul>
				<li class="on tab_org"><spring:message code="appvl.assignsigner.label.org"/></li>
			</ul>
		</div>
	</div>
	<div class="display_table vertical_top_rapper">
		<div class="display_tableCell_top vertical_top">
			<div class="tree_rapper" style='overflow: auto; overflow-x:hidden; height:260px; width: 320px;'>
				<div id="orgTree" style="width: 320px; height: 236px; -webkit-overflow-scrolling: touch; float: left;"></div>
			</div>
		</div>
		<div class="display_tableCell_top" style="padding: 0 10px">
			<div id="userList" class="height_line5" style="height: 268px; border: 1px solid #cccccc; overflow: hidden; padding: 5px; overflow: auto; -webkit-overflow-scrolling: touch;"></div>			
		</div>
	</div>
</div>
<div id="shadow_popup"></div>
<!-- ==== DirectoryWeb Start ==== -->
<input type="hidden" id="display" value="" />
<input type="hidden" id="dutiesUsed" value="" />
<input type="hidden" id="checkbox" value="both" />
<input type="hidden" id="selectMode" value="" />
<input type="hidden" id="openerType" value="" />
<input type="hidden" id="notUseDept" value="000000101" />
<input type="hidden" id="startDept" value="" />
<input type="hidden" id="activeSelect" value="1" />
<input type="hidden" id="useAbsent" value="true" />
<input type="hidden" id="baseDept" value="000020903" />
<input type="hidden" id="useRootdept" value="true" />
<input type="hidden" id="message_noneterm" value="검색어를 입력하십시오." />
<input type="hidden" id="message_minLength" value="검색은 2글자 이상 입력해야 합니다." />
<input type="hidden" id="message_invalidvalue" value="부서명 검색으로 특수문자를 사용할 수 없습니다." />
<!-- ==== DirectoryWeb End ==== -->
</body>
<script language="javascript" type="text/javascript"> 
//<![CDATA[
function select_user_load_ui(){
	$( document ).ready(function() {
		$("#shadow_popup").css(
			{
				width:$(document).width(),
				height:$(document).height()+48,
				opacity:0.7
			}
		);
		$(".layerPopup_rapper").css(
			{
				left:"50%",
				top:"25%",
				marginLeft:-parseInt($(".layerPopup_rapper").css("width"))/2+"px",
				marginTop:-parseInt($(".layerPopup_rapper").css("height"))/2+"px"
			}
		);
	});
}
//]]>
</script>
</html>