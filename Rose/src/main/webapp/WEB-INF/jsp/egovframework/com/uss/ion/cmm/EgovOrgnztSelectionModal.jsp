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
<script type="text/javascript">
var APPROVAL_CONTEXT = "${pageContext.servletContext.contextPath}";
var userId = "${user.uniqId}";
var DEPT = "$";
var checkType = "${checkType}";
function orgPopup_load(){
	orgPopup_load_ui();
	
	var userDeptId = '${user.orgnztId}'; // General Affairs Dept
	$("#baseDept").val(userDeptId);//기준부서    
	
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
function recp_debug(msg){
	if(window.console) window.console.log(msg);
}
function applyRecipient(){
    var recipientList = addRecipientList();
    applyOneRecipient(recipientList, checkType);
	closeRecipient();
}
function closeRecipient(){
	$("body").css("overflow","");
	$("#div_popup").hide();
}
function addRecipientList(){
	recp_debug("addInternalRecipientToRecipientList orgPopup.toList.size()["+orgPopup.toList.size()+"]");
	if (orgPopup.toList.size() == 0) {
		alert("not selected.");
		return;
	}
	var selectedID = false;
	var dept = false;
    var recipientList = new Array();
    
    for (i=0; i<orgPopup.toList.size(); i++) {
        selectedID = orgPopup.toList.get(i).toString("id");
        if (selectedID.indexOf(DEPT) > -1) {
        	selectedID = selectedID.substr(DEPT.length);
        	dept = getDept(selectedID);
        	var deptName = dept.name;
        	recipientList.push({"recpDeptId" : selectedID, "recpDeptNm": deptName});
        }
    }
    return recipientList;
}
</script>
</head>
<body>
<div class="layerPopup_rapper recipPopup" style="position:absolute;">
	<p class="popup_title type_approval"><spring:message code="org.dept.label.selectDept"/></p>
	<div class="ui_btn_rapper float_right mtb10">
		<a href="javascript:applyRecipient()" class="btn_color1"><spring:message code="common.button.apply"/></a>
		<a href="javascript:closeRecipient()" class="btn_color2"><spring:message code="common.button.close"/></a>
	</div>
	<div class="tabBox_rapper">
		<!-- 탭메뉴 -->
		<div class="tab_box">
			<ul>
				<!-- 탭 활성화 on 추가 -->
				<li class="on tab_org"><spring:message code="appvl.assignsigner.label.org"/></li>
			</ul>
		</div>
	</div>
	
	<div class="display_table vertical_top_rapper" id="div_internal">
		<div class="tree_rapper100p" style='overflow-y:no; height:260px; overflow: auto;'>
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
				</table>
				<div id="orgTree" style="width: 320px; height: 240px; -webkit-overflow-scrolling: touch; float: left;"></div>
		</div>		
	</div>
</div>
<div id="shadow_popup"></div>
<!-- ==== DirectoryWeb Start ==== -->
<input type="hidden" id="display" value="" />
<input type="hidden" id="dutiesUsed" value="" />
<input type="hidden" id="checkbox" value="tree" />
<input type="hidden" id="selectMode" value="" />
<input type="hidden" id="openerType" value="" />
<input type="hidden" id="notUseDept" value="000000101" />
<input type="hidden" id="startDept" value="" />
<input type="hidden" id="activeSelect" value="1" />
<input type="hidden" id="useAbsent" value="true" />
<input type="hidden" id="baseDept" value="000020903" />
<input type="hidden" id="useRootdept" value="true" />
<!-- ==== DirectoryWeb End ==== -->
</body>
<script language="javascript" type="text/javascript"> 
//<![CDATA[
function orgPopup_load_ui(){
	$( document ).ready(function() {
		$("#shadow_popup").css(
			{
				width:$(document).width(),
				height:$(document).height()+48,
				opacity:0.5
			}
		);
		$(window).resize(function(){
			$("#shadow_popup").css(
				{
					width:$(document).width(),
					height:$(window).height()+48,
					opacity:0.5
				}
			);
		});
		$(".layerPopup_rapper.recipPopup").css(
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