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

<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/org.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/js/egovframework/com/uss/omt/dynatree-1.2.4/src/skin/ui.dynatree.css'/>" />
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/omt/dynatree-1.2.4/src/jquery.dynatree.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/omt/orgPopup.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/omt/jquery.tbs.directory.js'/>"></script>
<script type="text/javascript">
var APPROVAL_CONTEXT = "${pageContext.servletContext.contextPath}";
var userId = "${user.uniqId}";
var recipientListTableId4Recipient = "recipient_list_table4recipient";
var DEPT = "$";
var appvl_recip_recpInnerFlag_1 = "<spring:message code="appvl.recip.label.recpInnerFlag.1"/>";
var appvl_recip_recpInnerFlag_2 = "<spring:message code="appvl.recip.label.recpInnerFlag.2"/>";
var appvl_recip_ST01 = "<spring:message code="appvl.recip.ST01"/>";
var strRecipientTr4Recipient = "<tr>"
	+"	<td class=\"align_center\"><input type=\"checkbox\" name=\"recipient_recpseq\" value=\"\"/></td>"
	+"	<td>"
	+"		<input class=\"recipient_recpid\"  name=\"recipient_recpid\"  type=\"hidden\" value=\"\">"
	+"		<input class=\"recipient_deptid\"  name=\"recipient_deptid\"  type=\"hidden\" value=\"\">"
	+"		<div class=\"recipient_deptname\" ></div>"
	+"	</td>"
	+"	<td>"
	+"		<input class=\"recipient_recpinnerflag\"  name=\"recipient_recpinnerflag\"  type=\"hidden\" value=\"\">"
	+"		<div class=\"recipient_recpinnerflagtext\">"+appvl_recip_recpInnerFlag_1+"</div>"
	+"	</td>"
	+"	<td>"
	+"		<input class=\"recipient_recpsendtype\"  name=\"recipient_recpsendtype\"  type=\"hidden\" value=\"ST01\">"
	+"		<div class=\"recipient_recpsendtypetext\">"+appvl_recip_ST01+"</div>"
	+"	</td>"
	+"</tr>";
function assign_recipient_load(){
	assign_recipient_load_ui();
	
	var userDeptId = '${user.uniqId}';
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
function recp_debug(msg){
	if(window.console) window.console.log(msg);
}
function addRecipientToRecipientList(){
	if($("#div_internal").css('display') != 'none'){
		addInternalRecipientToRecipientList();
	}else{
		addExternalRecipientToRecipientList();
	}
}
function addExternalRecipientToRecipientList(){
	var divExternal = $("#div_external");
	var deptname = $("[name=deptname]", divExternal).val();
	var recpsendtype = $("[name=recpsendtype]:checked", divExternal).val();
	
	recp_debug("addExternalRecipientToRecipientList deptname["+deptname+"], recpsendtype["+recpsendtype+"]");
	if (!deptname || !recpsendtype) {
		alert("not selected.");
		return;
	}
	
	var recipienttable = $("#"+recipientListTableId4Recipient);
	recp_debug("addExternalRecipientToRecipientList recipienttable["+recipienttable+"]");
	//var recipienttable_tr = recipienttable.find("tbody tr:first");
	var recipienttable_tr = $(strRecipientTr4Recipient);
	recp_debug("addExternalRecipientToRecipientList recipienttable_tr["+recipienttable_tr+"]");
	var maxSeq = 0;
	$("[name=recipient_recpseq]", recipienttable).each(function(){
		if(parseInt($(this).val(), 10) > maxSeq){
			maxSeq = parseInt($(this).val(), 10);
		}
	});

	var newRecipientTr = recipienttable_tr.clone();
	newRecipientTr.removeClass("unsortable");
	newRecipientTr.find("ul.token-input-list-facebook").remove();
	
	newRecipientTr.find("input[name=recipient_recpseq]").val(++maxSeq);
	newRecipientTr.find("input[name=recipient_recpid]").val("");
	newRecipientTr.find("input[name=recipient_deptid]").val("000000000");
	newRecipientTr.find(".recipient_deptname").text(deptname);
	newRecipientTr.find("input[name=recipient_recpinnerflag]").val("2");
	newRecipientTr.find(".recipient_recpinnerflagtext").text(appvl_recip_recpInnerFlag_2);
	newRecipientTr.find("input[name=recipient_recpsendtype]").val(recpsendtype);
	newRecipientTr.find(".recipient_recpsendtypetext").text(sendType[recpsendtype]);
	
	$("tbody:first", recipienttable).append(newRecipientTr);
}
function addInternalRecipientToRecipientList(){
	recp_debug("addInternalRecipientToRecipientList orgPopup.toList.size()["+orgPopup.toList.size()+"]");
	if (orgPopup.toList.size() == 0) {
		alert("not selected.");
		return;
	}
	
	var recipienttable = $("#"+recipientListTableId4Recipient);
	recp_debug("addInternalRecipientToRecipientList recipienttable["+recipienttable+"]");
	//var recipienttable_tr = recipienttable.find("tbody tr:first");
	var recipienttable_tr = $(strRecipientTr4Recipient);
	recp_debug("addInternalRecipientToRecipientList recipienttable_tr["+recipienttable_tr+"]");
	var maxSeq = 0;
	$("[name=recipient_recpseq]", recipienttable).each(function(){
		if(parseInt($(this).val(), 10) > maxSeq){
			maxSeq = parseInt($(this).val(), 10);
		}
	});
	
	var selectedID = false;
	var dept = false;
    for (i=0; i<orgPopup.toList.size(); i++) {
        selectedID = orgPopup.toList.get(i).toString("id");
        recp_debug("addRecipientToRecipientList selectedID["+selectedID+"]");
        
        if (selectedID.indexOf(DEPT) > -1) {
        	selectedID = selectedID.substr(DEPT.length);
        	dept = getDept(selectedID);

        	var newRecipientTr = recipienttable_tr.clone();
        	newRecipientTr.find("ul.token-input-list-facebook").remove();
			newRecipientTr.find("input[name=recipient_recpseq]").val(++maxSeq);
        	newRecipientTr.find("input[name=recipient_recpid]").val("");
        	newRecipientTr.find("input[name=recipient_deptid]").val(dept.ID);
        	newRecipientTr.find(".recipient_deptname").text(dept.name);
        	newRecipientTr.find("input[name=recipient_recpinnerflag]").val("1");
        	newRecipientTr.find("input[name=recipient_recpsendtype]").val("ST01");
        	
        	var recipientDeptIdInput = newRecipientTr.find("input[name=recipient_deptid]");
        	recipientDeptIdInput.prop("readonly", false);
        	
        	recp_debug("addRecipientToRecipientList recipientDeptIdInput["+recipientDeptIdInput+"]");
        	
        	$("tbody:first", recipienttable).append(newRecipientTr);
        }
    }
}
function removeRecipientToRecipientList(){
	var removeCount = 0;
	$("tbody tr [name=recipient_recpseq]", "#"+recipientListTableId4Recipient).each(function(index){
		if($(this).is(":checked") == true && !$(this).is(":disabled")){
			$(this).parent().parent().remove();
			removeCount++;
		}
	});
	if(removeCount < 1)
		alert("not selected.");
}
function moveRecipientToUpper(){
	var next = false;
	$("tbody tr [name=recipient_recpseq]", "#"+recipientListTableId4Recipient).each(function(index){
		if($(this).is(":checked") == true && !$(this).is(":disabled")){
			if(next && !$(next).is(":disabled")){
				var thisSeq = $(this).val();
				var nextSeq = $(next).val();
				$(next).val(thisSeq);
				$(this).val(nextSeq);
				$(".recipient_recpseq", $(next).parent()).text(thisSeq);
				$(".recipient_recpseq", $(this).parent()).text(nextSeq);
				
				$(this).parent().parent().after($(next).parent().parent());
			}
		}
		next = this;
	});
}
function moveRecipientToDown(){
	var next = false;
	$($("tbody tr [name=recipient_recpseq]", "#"+recipientListTableId4Recipient).get().reverse()).each(function(index){
		if($(this).is(":checked") == true){
			if(next && !$(next).is(":disabled")){
				var thisSeq = $(this).val();
				var nextSeq = $(next).val();
				$(next).val(thisSeq);
				$(this).val(nextSeq);
				$(".recipient_recpseq", $(next).parent()).text(thisSeq);
				$(".recipient_recpseq", $(this).parent()).text(nextSeq);
				
				$(this).parent().parent().before($(next).parent().parent());
			}
		}
		next = this;
	});
}
function getRecipientList4Recipient(recipientListTableId4Recipient){
	recp_debug("getRecipientList4Recipient recipientListTableId4Recipient["+recipientListTableId4Recipient+"]");
	var recipientList = new Array();
	recp_debug("getRecipientList4Recipient recipientListTableId4Recipient tbody tr["+$("#"+recipientListTableId4Recipient+" tbody tr").length+"]");
	$("#"+recipientListTableId4Recipient+" tbody tr").each(function(index){
		recp_debug("getRecipientList4Recipient this["+this+"]");
		var recipientRecpSeq = index+1;
		var recipientRecpId = $(this).find("input[name=recipient_recpid]").val();
		var recipientDeptId = $(this).find("input[name=recipient_deptid]").val();
		var recipientDeptName = $(this).find(".recipient_deptname").text();
		var recipientSendType = $(this).find("input[name=recipient_recpsendtype]").val();
		if(!recipientSendType) recipientSendType = "ST01";
		var recipientRecpInnerFlag = $(this).find("input[name=recipient_recpinnerflag]").val();
		if(!recipientRecpInnerFlag) recipientRecpInnerFlag = "1";
		
		recp_debug("getRecipientList4Recipient recipientRecpSeq["+recipientRecpSeq+"], recipientRecpId["+recipientRecpId+"], recipientDeptId["+recipientDeptId+"], recipientDeptName["+recipientDeptName+"], recipientSendType["+recipientSendType+"], recipientRecpInnerFlag,["+recipientRecpInnerFlag+"]");
		if(!recipientDeptId) return;
		
		recipientList.push({"recpSeq" : recipientRecpSeq, "recpId": recipientRecpId, "deptId": recipientDeptId, "recpDeptNm" : recipientDeptName, "recpSendType": recipientSendType, "recpInnerFlag": recipientRecpInnerFlag});
		recp_debug("getRecipientList4Recipient recipientList["+recipientList+"]");
	});
	return recipientList;
}
function applyRecipient(){
	recp_debug("applyRecipient recipientListTableId4Recipient["+recipientListTableId4Recipient+"]");
	var recipientList = getRecipientList4Recipient(recipientListTableId4Recipient);
	recp_debug("applyRecipient recipientList["+recipientList+"]");
	applyRecipientList(recipientList);
	closeRecipient();
}
function closeRecipient(){
	$("body").css("overflow","");
	$("#div_popup").hide();
}
function displayInternal(tabObj, ID){
	$(".tab_sendtype a").each(function(index){
		/* if(tabObj == this){
			$(this).addClass("on");
		}else{
			$(this).removeClass("on");
		} */
		$('.tab_sendtype').each(function(index){
			if($(this).attr('id') == ID){
				$(this).addClass("on");
			}else{
				$(this).removeClass("on");
			}
		});
	});
	$("#div_external").hide();
	$("#div_internal").show();
}
function displayExternal(tabObj, ID){
	$(".tab_sendtype a").each(function(index){
		/* if(tabObj == this){
			$(this).removeClass("on");
		}else{
			$(this).addClass("on");
		} */
		$('.tab_sendtype').each(function(index){
			if($(this).attr('id') == ID){
				$(this).addClass("on");
			}else{
				$(this).removeClass("on");
			}
		});
		
	});
	$("#div_internal").hide();
	$("#div_external").show();
}
</script>
</head>
<body>
<div class="layerPopup_rapper recipPopup" style="position:absolute;">
	<p class="popup_title type_approval"><spring:message code="appvl.assignrecpt.title"/></p>
	<div class="ui_btn_rapper float_right mtb10">
		<a href="javascript:applyRecipient()" class="btn_color1"><spring:message code="common.button.apply"/></a>
		<a href="javascript:closeRecipient()" class="btn_color2"><spring:message code="common.button.cancel"/></a>
	</div>
	<div class="tabBox_rapper">
		<!-- 탭메뉴 -->
		<div class="tab_box">
			<ul>
				<!-- 탭 활성화 on 추가 -->
				<li class="on tab_sendtype" id="li_Internal"><a href="javascript:displayInternal(this,'li_Internal')"><spring:message code="appvl.assignrecpt.button.internal"/></a></li>
				<li class="tab_sendtype" id="li_External"><a href="javascript:displayExternal(this,'li_External')"><spring:message code="appvl.assignrecpt.button.external"/></a></li>				
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
	<div class="table_rapper" style="display: none" id="div_external">
		<table summary="" class="board_width_borderNone">
			<caption class="blind"></caption>
			<colgroup>
				<col width="130px"/>
				<col width="*"/>
				<col width="85px"/>
			</colgroup>
			<tbody>
				<tr>
					<th scope="row"><label for="label_1"><spring:message code="appvl.assignrecpt.lable.recipient"/></label></th>
					<td colspan="2"><div class="ui_input_text"><input type="text" value="" name="deptname" id="label_1"/></div></td>
				</tr>
				<tr>
					<th scope="row"><spring:message code="appvl.assignrecpt.lable.method"/></th>
					<td colspan="2">
						<span class="radio_rapper"><input type="radio" value="ST02" name="recpsendtype" id="label_3-1"/><label  for="label_3-1"><spring:message code="appvl.recip.ST02"/> </label></span>
						<span class="radio_rapper"><input type="radio" value="ST03" name="recpsendtype" id="label_3-3"/><label  for="label_3-3"><spring:message code="appvl.recip.ST03"/> </label></span>
						<span class="radio_rapper"><input type="radio" value="ST04" name="recpsendtype" id="label_3-4"/><label  for="label_3-4"><spring:message code="appvl.recip.ST04"/> </label></span>
						<span class="radio_rapper"><input type="radio" value="ST05" name="recpsendtype" id="label_3-5"/><label  for="label_3-5"><spring:message code="appvl.recip.ST05"/> </label></span>
					</td>
				</tr>							
			</tbody>
		</table>
	</div>

	<div class="solt_width">							
		<a href="javascript:addRecipientToRecipientList()"><img src="<c:url value='/images/egovframework/com/uss/cmm/ico_down.png'/>" alt="ico_down"></a>
		<a href="javascript:removeRecipientToRecipientList()"><img src="<c:url value='/images/egovframework/com/uss/cmm/ico_up.png'/>" alt="ico_up"></a>
	</div>
	
	<p class="tableUp_title"><spring:message code="appvl.assignrecpt.lable.selectedrecipient"/></p>
	<div class="table_rapper display_table">
		<div class="display_tableCell_top" >
			<ul class="solt_height type1">
				<li><a href="javascript:moveRecipientToUpper()"><img src="<c:url value='/images/egovframework/com/uss/cmm/ico_up.png'/>" alt="ico_up"></a></li>
				<li class="ico_down"><a href="javascript:moveRecipientToDown()"><img src="<c:url value='/images/egovframework/com/uss/cmm/ico_down.png'/>" alt="ico_down"></a></li>
			</ul>
		</div>
		<div class="display_tableCell_top">
			<div class="height_line5">
				<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height" id="recipient_list_table4recipient">
					<caption class="blind"></caption>
					<colgroup>
						<col width="30px"/>
						<col width="*"/>
						<col width="60px"/>
						<col width="120px"/>
					</colgroup>
					<thead>
						<tr>
							<th scope="col" class="align_center"><img src="<c:url value='/images/egovframework/com/uss/cmm/icon_check.png'/>" alt="icon_check"></th>
							<th scope="col" class=""><span><spring:message code="appvl.assignrecpt.lable.selectedrecipient.table.departmentmda"/></span></th>
							<th scope="col" class=""><span><spring:message code="appvl.assignrecpt.lable.selectedrecipient.table.type"/></span></th>
							<th scope="col" class=""><span><spring:message code="appvl.assignrecpt.lable.selectedrecipient.table.method"/></span></th>
						</tr>
					</thead>					
					<tbody>
						<c:forEach var="recipient" items="${recipientList}" varStatus="status">
						<tr>
							<td class="align_center"><input type="checkbox" name="recipient_recpseq" value="<c:if test="${empty recipient.recpSeq}"><c:out value="${status.count}"/></c:if><c:if test="${not empty recipient.recpSeq}"><c:out value="${recipient.recpSeq}"/></c:if>"/></td>
							<td>
								<input class="recipient_recpid"  name="recipient_recpid"  type="hidden" value="<c:out value="${recipient.recpId}"/>">
								<input class="recipient_deptid"  name="recipient_deptid"  type="hidden" value="<c:out value="${recipient.deptId}"/>">
								<div class="recipient_deptname" ><c:out value="${recipient.recpDeptNm}"/></div>
							</td>
							<td>
								<input class="recipient_recpinnerflag"  name="recipient_recpinnerflag"  type="hidden" value="<c:out value="${recipient.recpInnerFlag}"/>">
							<c:choose>
								<c:when test="${recipient.recpInnerFlag eq '1'}"><div class="recipient_recpinnerflagtext"><spring:message code="appvl.recip.label.recpInnerFlag.1"/></div></c:when>
								<c:when test="${recipient.recpInnerFlag eq '2'}"><div class="recipient_recpinnerflagtext"><spring:message code="appvl.recip.label.recpInnerFlag.2"/></div></c:when>
								<c:otherwise><div class="recipient_recpinnerflagtext"><spring:message code="appvl.recip.label.recpInnerFlag.1"/></div></c:otherwise>
							</c:choose>
							</td>
							<td>
								<input class="recipient_recpsendtype"  name="recipient_recpsendtype"  type="hidden" value="<c:out value="${recipient.recpSendType}"/>">
								<c:choose>
									<c:when test="${recipient.recpSendType eq 'ST01'}"><div class="recipient_recpsendtypetext"><spring:message code="appvl.recip.ST01"/></div></c:when>
									<c:when test="${recipient.recpSendType eq 'ST02'}"><div class="recipient_recpsendtypetext"><spring:message code="appvl.recip.ST02"/></div></c:when>
									<c:when test="${recipient.recpSendType eq 'ST03'}"><div class="recipient_recpsendtypetext"><spring:message code="appvl.recip.ST03"/></div></c:when>
									<c:when test="${recipient.recpSendType eq 'ST04'}"><div class="recipient_recpsendtypetext"><spring:message code="appvl.recip.ST04"/></div></c:when>
									<c:when test="${recipient.recpSendType eq 'ST05'}"><div class="recipient_recpsendtypetext"><spring:message code="appvl.recip.ST05"/></div></c:when>
									<c:otherwise><div class="recipient_recpsendtypetext"><spring:message code="appvl.recip.ST01"/></div></c:otherwise>
								</c:choose>
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
<input type="hidden" id="checkbox" value="tree" />
<input type="hidden" id="selectMode" value="2" />
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
function assign_recipient_load_ui(){
$('.board_type_height th').click(function() {
	if ($(this).hasClass('sort_down')){
		$(this).removeClass('sort_down');
		$(this).addClass('sort_up');
	} else {
		$(this).removeClass('sort_up');
		$(this).addClass('sort_down');
	  }
	});
	
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
				top:"4%",
				marginLeft:-parseInt($(".layerPopup_rapper").css("width"))/2+"px",
				marginTop:-parseInt($(".layerPopup_rapper").css("height"))/2+"px"
			}
		);
	});
}
//]]>
</script>
</html>