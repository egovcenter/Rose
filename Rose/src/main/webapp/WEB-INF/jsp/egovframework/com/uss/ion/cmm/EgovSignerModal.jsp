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
var redraft = "${redraft}";
var signerListId4signerline = "signer_list_table4signerline";
var pos = 0;
var pos_flag = 'M';
var total_count = 0;

function user_selection_load(){
	user_selection_load_ui();
	
	var userDeptId = '${user.orgnztId}';
	$("#baseDept").val(userDeptId);
	
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

var DEPT = "$";
function addSignerToSignerLine() {
	appr_debug("addSignerToSignerLine orgPopup.toList.size()["+orgPopup.toList.size()+"]");
	if (orgPopup.toList.size() == 0) {
		alert("not selected.");
		return;
	}
	selectedID = orgPopup.toList.get(0).toString("id");
	selectedDeptId = orgPopup.toList.get(0).toString("deptId");
	appr_debug("addSignerToSignerLine selectedID["+selectedID+"]");

	var signtable = $("#"+signerListId4signerline);
	var signtable_first_tr = signtable.find("tbody tr:first");
	var signtable_last_tr = signtable.find("tbody tr:last");
	// DEPT
	if (selectedID.indexOf(DEPT) > -1) {
	} else {
		var userInfo = getUser(selectedID, selectedDeptId);
		
		// TODO add to signerline
		appr_debug("addSignerToSignerLine selectedID is user.["+selectedID+"]");
		var maxSeq = 0;
		$("[name=signer_seq]", signtable).each(function(){
			if(parseInt($(this).val(), 10) > maxSeq){
				maxSeq = parseInt($(this).val(), 10);
			}
		});
		
		var newSignerTr = signtable_last_tr.clone();
		newSignerTr.removeClass("unsortable");
		newSignerTr.find("ul.token-input-list-facebook").remove();

		var signer_kind  = "SK02";
		var signer_state = "SS03"
		if (signtable_first_tr.find("td:eq(4)").text().trim() == 'Approval') {
			var signer_review = "SK01";
			signtable_first_tr.find(".signer_kindtext").text(signerKind[signer_review]);  
			signtable_first_tr.find("input[name=signer_kind]").val(signer_review);
		}
		newSignerTr.find("input[name=signer_seq]").attr("disabled", false);	
		newSignerTr.find("input[name=signer_seq]").val(++maxSeq);		
		newSignerTr.find(".signer_deptname").text(userInfo.deptName);
		newSignerTr.find(".signer_positionname").text(userInfo.positionName);
		newSignerTr.find(".signer_signername").text(userInfo.name);
		newSignerTr.find(".signer_kindtext").text(signerKind[signer_kind]);  
		newSignerTr.find("input[name=signer_id]").val("");
		newSignerTr.find("input[name=signer_userid]").val(userInfo.ID);
		newSignerTr.find("input[name=signer_deptid]").val(userInfo.deptId);
		newSignerTr.find("input[name=signer_kind]").val(signer_kind);
		newSignerTr.find("input[name=signer_signstate]").val(signer_state);
		newSignerTr.find("input[name=signer_dutyname]").val(userInfo.dutyName);
		newSignerTr.find("input[name=signer_opinion]").val("");
		newSignerTr.find("input[name=signer_signDate]").val("");
		
		newSignerTr.insertBefore($("tbody tr:first", signtable));
	}
	
	arrangeSignerKind();
}

function removeSignerToSignerLine() {
	
	var removeCount = 0;
	var listNum = $("tbody input[name=signer_seq]").length;
	listNum -= $("tbody input[name=signer_seq]:checkbox:checked").length;
	$("tbody tr [name=signer_seq]", "#"+signerListId4signerline).each(function(index){
		if($(this).is(":checked") == true && !$(this).is(":disabled")){
			$(this).parent().parent().remove();
			//var signer_seq =$("tbody tr [name=signer_seq]").length;
			removeCount++;
		}else{
			$(this).parent().parent().find('.signer_seq').text(listNum);
			listNum--;
		}
	});
	if(removeCount < 1){
		alert("not selected.");
	}else{
		$("tbody tr:first select[name=signer_kind]", "#"+signerListId4signerline).each(function(index){
			if(!$(this).is(":disabled") && $(this).val() == 'SK01'){
				$(this).val('SK02');
			}
		});
	}
	
	arrangeSignerKind();
}

function arrangeSignerKind(){
	var i = 0;
	var signer_kind = "";
	total_count = $("#"+signerListId4signerline+" tbody tr").length;

	$($("#"+signerListId4signerline+" tbody tr").get()).each(function(index){
		if (i == (total_count - 1)) {
			return;
		}
		if (i == 0) {
			signer_kind = "SK02";		// Approval
		}
		else {
			signer_kind = "SK01";		// Review
		}
			
		$(this).find("input[name=signer_seq]").val(total_count - i);
		$(this).find("input[name=signer_kind]").val(signer_kind);
		$(this).find(".signer_kindtext").text(signerKind[signer_kind]);	
		i++;
	});
}

$(function() {
	user_selection_load();
});

function closeSignerline(){
	$("#div_popup").hide();
}

function moveSignerToUpper(){
	if (pos_flag == 'T') {
		return;
	}
	
	pos--;
	if (pos == 0) {
		pos_flag = 'T';
	}

	var next = false;
	$("tbody tr [name=signer_seq]", "#"+signerListId4signerline).each(function(index){
		if($(this).is(":checked") == true && !$(this).is(":disabled")){
			if(next && !$(next).is(":disabled")){
				var thisSeq = $(this).val();
				var nextSeq = $(next).val();
				$(next).val(thisSeq);
				$(this).val(nextSeq);
				$(".signer_seq", $(next).parent()).text(thisSeq);
				$(".signer_seq", $(this).parent()).text(nextSeq);
				
				$(this).parent().parent().after($(next).parent().parent());
			}
		}
		next = this;
	});
	
	arrangeSignerKind();
}

function moveSignerToDown(){
	if (pos_flag == 'B') {
		return;
	}
	
	pos++;
	if (pos == (total_count - 2)) {
		pos_flag = 'B';
	}

	var next = false;
	$($("tbody tr [name=signer_seq]", "#"+signerListId4signerline).get().reverse()).each(function(index){
		if($(this).is(":checked") == true){
			if(next && !$(next).is(":disabled")){
				var thisSeq = $(this).val();
				var nextSeq = $(next).val();
				$(next).val(thisSeq);
				$(this).val(nextSeq);
				$(".signer_seq", $(next).parent()).text(thisSeq);
				$(".signer_seq", $(this).parent()).text(nextSeq);
				
				$(this).parent().parent().before($(next).parent().parent());
			}
		}
		next = this;
	});
	
	arrangeSignerKind();
}

function getSignerList4Signerline(signerListId4signerline){
	var signerList = new Array();

	$($("#"+signerListId4signerline+" tbody tr").get().reverse()).each(function(index){
		var seq = $(this).find("input[name=signer_seq]").val();
		var signerDeptName = $(this).find(".signer_deptname").text();
		var signerPositionName = $(this).find(".signer_positionname").text();
		var signerSignerName = $(this).find(".signer_signername").text();
		var signerId = $(this).find("input[name=signer_id]").val();
		var signerUserId = $(this).find("input[name=signer_userid]").val();
		var signerDeptId = $(this).find("input[name=signer_deptid]").val();
		var signerKind = $(this).find("input[name=signer_kind]").val();
		var signerSignState = $(this).find("input[name=signer_signstate]").val();
		var signerDutyName = $(this).find("input[name=signer_dutyname]").val();
		var signerOpinion = $(this).find("input[name=signer_opinion]").val();
		var signerSignDate = $(this).find("input[name=signer_signDate]").val();		
		
		signerList.push({"seq" : seq, "signerId": signerId, "signerKind": signerKind, 
			"signerSignState": signerSignState, "signerSignDate" : signerSignDate,
			"signerUserId" : signerUserId, "signerSignerName": signerSignerName, 
			"signerDeptId": signerDeptId, "signerDeptName": signerDeptName, 
			"signerPositionName": signerPositionName, "signerDutyName" : signerDutyName, 
			"signerOpinion" : signerOpinion});
	});
	
	return signerList;
}

function applySignerLine(){
	var signerList = getSignerList4Signerline(signerListId4signerline);
	applySignerList(signerList, redraft);
	closeSignerline();
}

$(document).on("click", "input[name=signer_seq]:checkbox", function(){ 
	$("input[name=signer_seq]:checkbox").attr("checked", false);
	$(this).attr("checked", true);
	
	pos = 0; 
	total_count = $("input[name=signer_seq]:checkbox").length;
	for (var i = 0; i < total_count; i++) {
		if ($("input[name=signer_seq]:checkbox")[i].checked == true) {
			pos = i;
			break;
		}
	}
	
	if (pos == 0) {
		pos_flag = 'T';
	} 
	else if (pos == (total_count - 2)) {
		pos_flag = 'B';
	}
	else {
		pos_flag = 'M';
	}
});
</script>
</head>
<body>
<div class="layerPopup_rapper" style="width: 800px; position:absolute;">
	<p class="popup_title type_approval"><spring:message code="appvl.assignsigner.title"/></p>
	<div class="ui_btn_rapper float_right mtb10">
		<a href="javascript:applySignerLine()" class="btn_color1"><spring:message code="common.button.apply"/></a>
		<a href="javascript:closeSignerline()" class="btn_color2"><spring:message code="common.button.cancel"/></a>
	</div>
	<div class="tabBox_rapper">
		<!-- 탭메뉴 -->
		<div class="tab_box">
			<ul>
				<!-- 탭 활성화 on 추가 -->
				<li class="on"><spring:message code="appvl.assignsigner.label.org"/></li>
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

	<div class="solt_width">							
		<a href="javascript:addSignerToSignerLine()"><img src="<c:url value='/images/egovframework/com/uss/cmm/ico_down.png'/>" alt="ico_down"></a>
		<a href="javascript:removeSignerToSignerLine()"><img src="<c:url value='/images/egovframework/com/uss/cmm/ico_up.png'/>" alt="ico_up"></a>
	</div>
	
	<p class="tableUp_title"><spring:message code="appvl.assignsigner.label.selectedsigner"/></p>
	<div class="table_rapper display_table">
		<div class="display_tableCell_top" >
			<ul class="solt_height type1">
				<li class="ico_up"  ><a href="javascript:moveSignerToUpper()"><img src="<c:url value='/images/egovframework/com/uss/cmm/ico_up.png'/>" alt="ico_up"></a></li>
				<li class="ico_down"><a href="javascript:moveSignerToDown()"><img src="<c:url value='/images/egovframework/com/uss/cmm/ico_down.png'/>" alt="ico_down"></a></li>
			</ul>
		</div>
		<div class="display_tableCell_top">
			<div class="height_line5" id="signer_ck_box">
				<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height" id="signer_list_table4signerline">
					<caption class="blind"></caption>
					<colgroup>
						<col width="30px"/>
						<col width="50%"/>
						<col width="25%"/>
						<col width="25%"/>
						<col width="100px"/>
					</colgroup>
					<thead>
						<tr>
							<th scope="col" class="align_center"><img src="<c:url value='/images/egovframework/com/uss/cmm/icon_check.png'/>" alt="icon_check"></th>
							<th scope="col" class=""><span><spring:message code="appvl.assignsigner.label.selectedsigner.table.department"/></span></th>
							<th scope="col" class=""><span><spring:message code="appvl.assignsigner.label.selectedsigner.table.position"/></span></th>
							<th scope="col" class=""><span><spring:message code="appvl.assignsigner.label.selectedsigner.table.name"/></span></th>
							<th scope="col" class=""><span><spring:message code="appvl.assignsigner.label.selectedsigner.table.signertype"/></span></th>
						</tr>
					</thead>					
					<tbody>
						<c:forEach var="signer" items="${signerList}" varStatus="status">
						<tr <c:if test="${signer.signState == 'SS09' or signer.signKind == 'SK00'}">class="unsortable"</c:if>>
							<td class="align_center">
								<c:choose>
									<c:when test="${signer.signState == 'SS09'}">
										<input type="checkbox" name="signer_seq" disabled="true" value="<c:if test="${empty signer.signSeq}"><c:out value="${status.count}"/></c:if><c:if test="${not empty signer.signSeq}"><c:out value="${signer.signSeq}"/></c:if>"/></td>
									</c:when>
									<c:when test="${signer.signKind == 'SK01' or signer.signKind == 'SK02'}">
										<input type="checkbox" name="signer_seq" value="<c:if test="${empty signer.signSeq}"><c:out value="${status.count}"/></c:if><c:if test="${not empty signer.signSeq}"><c:out value="${signer.signSeq}"/></c:if>"/></td>
									</c:when>
									<c:otherwise>
										<!-- 기안, 접수, 재기안의 경우 checkbox가 없다 -->
										<input type="checkbox" name="signer_seq" disabled="true" value="<c:if test="${empty signer.signSeq}"><c:out value="${status.count}"/></c:if><c:if test="${not empty signer.signSeq}"><c:out value="${signer.signSeq}"/></c:if>"/></td>
									</c:otherwise>
								</c:choose>
							</td>							
							<td><div class="signer_deptname"><c:out value="${signer.signerDeptName}"/></div></td>
							<td><div class="signer_positionname"><c:out value="${signer.signerPositionName}"/></div></td>
							<td><div class="signer_signername"  name="signer_signername">${signer.signerName}</div></td>
							<td>
								<c:choose>
									<c:when test="${signer.signKind eq 'SK00'}"><div class="signer_kindtext"><spring:message code="appvl.signerKind.SK00"/></div></c:when>
									<c:when test="${signer.signKind eq 'SK01'}"><div class="signer_kindtext"><spring:message code="appvl.signerKind.SK01"/></div></c:when>
									<c:when test="${signer.signKind eq 'SK02'}"><div class="signer_kindtext"><spring:message code="appvl.signerKind.SK02"/></div></c:when>
									<c:when test="${signer.signKind eq 'SK03'}"><div class="signer_kindtext"><spring:message code="appvl.signerKind.SK03"/></div></c:when>
									<c:when test="${signer.signKind eq 'SK04'}"><div class="signer_kindtext"><spring:message code="appvl.signerKind.SK04"/></div></c:when>
								</c:choose>
								<input class="signer_id"  name="signer_id" type="hidden" value="<c:out value="${signer.signerID}"/>">
								<input class="signer_userid"  name="signer_userid"  type="hidden" value="<c:out value="${signer.userID}"/>">
								<input class="signer_deptid"  name="signer_deptid"  type="hidden" value="<c:out value="${signer.signerDeptID}"/>">
								<input class="signer_kind"  name="signer_kind"  type="hidden" value="<c:out value="${signer.signKind}"/>">
								<input class="signer_signstate"  name="signer_signstate"  type="hidden" value="<c:out value="${signer.signState}"/>">
								<input class="signer_dutyname"  name="signer_dutyname"  type="hidden" value="<c:out value="${signer.signerDutyName}"/>">
								<input class="signer_opinion"  name="signer_opinion"  type="hidden" value="<c:out value="${signer.opinion}"/>">
								<input class="signer_signDate"  name="signer_signDate"  type="hidden" value="<c:out value="${signer.signDate}"/>">
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>			
		</div>			
	</div>	
</div>
<div id="shadow_popup"></div>
<!-- ==== DirectoryWeb Start ==== -->
<input type="hidden" id="display" value="" />
<input type="hidden" id="dutiesUsed" value="" />
<input type="hidden" id="checkbox" value="both" />
<input type="hidden" id="selectMode" value="1" />
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
function user_selection_load_ui(){
	$('.board_type_height th').click(function() {
	if ($(this).hasClass('sort_down')){
		$(this).removeClass('sort_down');
		$(this).addClass('sort_up');
	} else {
		$(this).removeClass('sort_up');
		$(this).addClass('sort_down');
	  }
	});
	
	$("#shadow_popup").css(
		{
			width:$(document).width(),
			height:$(document).height(),
			opacity:0.5
		}
	);

	$(window).resize(function(){
		$("#shadow_popup").css(
			{
				width:$(window).width(),
				height:$(document).height(),
				opacity:0.5
			}
		);
	});
	
	$(".layerPopup_rapper").css(
		{
			left:"50%",
			top:"4%",
			marginLeft:"-400px"
		}
	);
}
//]]>
</script>
</html>