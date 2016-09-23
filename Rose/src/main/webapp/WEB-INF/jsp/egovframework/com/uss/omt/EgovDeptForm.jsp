<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script src="<c:url value='/js/egovframework/com/uss/omt/orgCommon.js'/>"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jquery-1.11.3.js'/>"></script>
<script>
var CONTEXT = "${pageContext.servletContext.contextPath}";
function formMapperOnload() {
    $('input:hidden[name=DEPT_ID]').val("<c:out value="${dept.deptId}" />");
    $('input:hidden[name=DEPT_PAR_ID]').val("<c:out value="${DEPT_PAR_ID}" />");
    $('input:hidden[name=DEPT_BOX_USER_ID]').val("<c:out value="${dept.deptBoxUserId}" />");
    $('input:hidden[name=DEPT_RBOX_USER_ID]').val("<c:out value="${dept.deptRboxUserId}" />");
    $('input:hidden[name=DEPT_SBOX_USER_ID]').val("<c:out value="${dept.deptSboxUserId}" />");
    $('input:hidden[name=DEPT_ADMIN_USER_ID]').val("<c:out value="${dept.deptAdminUserId}" />");
    $('input:hidden[name=DEPT_SEQ]').val("<c:out value="${dept.deptSeq}" />");
    
    $('#DEPT_NM').val("<c:out value="${dept.deptNm}" />");
    $('#DEPT_CD').val("<c:out value="${dept.deptCd}" />");
    $('#DEPT_RBOX_F').attr("checked", "<c:out value="${dept.deptRboxF}" />" == "1");
    $('#DEPT_BOX_F').attr("checked", "<c:out value="${dept.deptBoxF}" />" == "1");
    $('#DEPT_TOP_F_Y').attr("checked", "<c:out value="${dept.deptTopF}" />" == "1");
    $('#DEPT_TOP_F_N').attr("checked", "<c:out value="${dept.deptTopF}" />" == "2");

    $('#DEPT_PAR_NM').val("<c:out value="${DEPT_PAR_NM}" />");
    $('#DEPT_RBOX_USER_NM').val("<c:out value="${dept.deptRboxUserNm}" />");
    $('#DEPT_BOX_USER_NM').val("<c:out value="${dept.deptBoxUserNm}" />");
    $('#DEPT_SBOX_USER_NM').val("<c:out value="${dept.deptSboxUserNm}" />");
    $('#DEPT_ADMIN_USER_NM').val("<c:out value="${dept.deptAdminUserNm}" />");
    
    $("#DEPT_TOP_F_N, #DEPT_TOP_F_Y").click(function() {
        return false;
    });
    if (true == env.isSysAdmin) {
    	$("#DEPT_TOP_F_Y").each(function() {
    		this.checked = true;
    	});
    } else {
    	$("#DEPT_TOP_F_N").each(function() {
            this.checked = true;
        });
    }
}
</script>
</head>

<body>       
<c:choose>
<c:when test="${empty dept.deptId}">
	<!-- IF ADD -->
    <c:set var="formType" value="New"></c:set>
    <c:set var="saveName" value="Apply"></c:set>
</c:when>
<c:otherwise>
	<!-- IF MODIFY -->
    <c:set var="formType" value="Modify"></c:set>
    <c:set var="saveName" value="Apply"></c:set>
</c:otherwise>
</c:choose>

<div class="wrap"> 
  <!-- Top Menu Start-->
  <jsp:include page="/WEB-INF/jsp/egovframework/com/cmm/EgovTopMenu.jsp" flush="false">
	<jsp:param value="ADMIN" name="selectedMenu"/>
  </jsp:include>
  <!-- Top Menu End -->
  <div class="clear"></div>
  <!-- Container start -->
  <div class="Container"> 
    <!-- Lnb -->
    <div class="Side"> 
      	<!-- Side Menu -->
		<jsp:include page="/WEB-INF/jsp/egovframework/com/uss/omt/cmm/EgovLeftMenu.jsp" flush="false">
			<jsp:param value="ORGANIZATION" name="selectedMenu"/>
		</jsp:include>
    </div>
    <!-- Content start -->
    <div class="Content"> 
      <!-- Content box start -->
      <div class="content_box" id="org_content_box">
        <!-- 공통 타이틀 버튼 라인 -->
        <div class="title_box">
          <div class="title_line">
            <h1><spring:message code="org.organization.label.organzationmng"/></h1>
          </div>
          <div class="clear"></div>
        </div>  
        
		<!-- Input From start -->
		<form name="aForm" id="ajaxform" action="" method="post" onsubmit="return false;">
		    <input type="hidden" name="DEPT_ID"/>
			<input type="hidden" name="DEPT_PAR_ID"/>
			<input type="hidden" name="DEPT_SEQ"/>
			<input type="hidden" name="DEPT_NM"/>
			<input type="hidden" name="DEPT_CD"/>
			<input type="hidden" name="DEPT_RBOX_F"/>
			<input type="hidden" name="DEPT_BOX_F"/>
			<input type="hidden" name="DEPT_TOP_F"/>
			<input type="hidden" name="DEPT_RBOX_USER_ID"/>
			<input type="hidden" name="DEPT_BOX_USER_ID"/>
			<input type="hidden" name="DEPT_SBOX_USER_ID"/>
			<input type="hidden" name="DEPT_ADMIN_USER_ID"/>
			<!-- 부가정보 필드 -->
			<input type="hidden" name="DEPT_ADD_POS"/>
			<input type="hidden" name="DEPT_PAR_NM"/>
			<input type="hidden" name="checkType" value="multi"/>
			<input type="hidden" name="saveUserType"/>
			<!-- user info -->
	       <input type="hidden" name="USER_EM_CD"/>
	       <input type="hidden" name="USER_NM"/>
	       <input type="hidden" name="POSI_ID"/>
	       <input type="hidden" name="DUTY_ID"/>
	       <input type="hidden" name="USER_SLVL"/>
	       <input type="hidden" name="USER_LOGIN"/>
	       <input type="hidden" name="USER_LPWD"/>
	       <input type="hidden" name="USER_SPWD"/>
	       
		<p class="tab_title float_left"><c:out value="${formType}"/> <spring:message code="org.dept.label.department"/></p>
		<div class="ui_btn_rapper float_right">
	    	<a href="javascript:deptSave();" id="button_save" class="btn_color3"><c:out value="${saveName}"/></a>
			<a href="javascript:gotoList();" id="button_cancel" class="btn_color2"><spring:message code="common.button.cancel"/></a>
		</div>
		<div class="table_rapper">
			<table summary="" class="board_width_borderNone">
				<caption class="blind"></caption>
				<colgroup>
					<col width="140px"/>
					<col width="*"/>
					<col width="82px"/>
					<col width="25%"/>
				</colgroup>
				<tbody>
			  	<c:choose>
			  		<c:when test="${user.userType eq 'D'}">
  						<tr>
							<th scope="row"><label for="label_1"><spring:message code="org.dept.label.name"/></label></th>
							<td colspan="3"><div class="ui_input_text"><input type="text" value="" id="DEPT_NM" readonly/></div></td>
						</tr>
						<tr>
							<th scope="row"><label for="label_2"><spring:message code="common.location.label.location"/></label></th>
							<td><div class="ui_input_text"><input type="text" value="" id="DEPT_PAR_NM" readonly/></div></td>
							<td colspan="2">
								<div class="ui_input_text"><input type="text" value="<spring:message code="common.location.label.lower"/>" id="DEPT_ADD_POS" readonly/></div>
							</td>
						</tr>
						<tr>
							<th scope="row"><label for="label_3"><spring:message code="org.dept.label.departmentcode"/></label></th>
							<td colspan="3"><div class="ui_input_text"><input type="text" value="" id="DEPT_CD" readonly/></div></td>							
						</tr>
						<tr>
							<th scope="row"><spring:message code="org.dept.label.auth"/></th>
							<td colspan="2">
								<span class="checkbox_rapper"><input type="checkbox" value="1" id="DEPT_BOX_F" <c:if test="${user.orgnztId eq dept.deptId}">disabled="disabled"</c:if>/><label  for="DEPT_BOX_F"><spring:message code="org.dept.label.hasCabinet"/></label></span>
								<span class="checkbox_rapper"><input type="checkbox" value="1" id="DEPT_RBOX_F" <c:if test="${user.orgnztId eq dept.deptId}">disabled="disabled"</c:if>/><label  for="DEPT_RBOX_F"><spring:message code="org.dept.label.hasRCabinet"/></label></span>
							</td>
						</tr>
						<tr>
							<th scope="row"><label for="label_6"><spring:message code="common.management.label.department"/></label></th>
							<td><div class="ui_input_text"><input type="text" value="" id="DEPT_BOX_USER_NM" readonly/></div></td>
							<c:if test="${user.orgnztId ne dept.deptId}">
							<td id="select_department_manager"><div class="ui_btn_rapper align_right"><a href="#" id="button_select_department_manager" class="btn_color3"><spring:message code="common.button.select"/></a></div></td>
							<td id="deselect_department_manager"><div class="ui_btn_rapper align_left"><a href="#" id="button_Deselect_department_manager" class="btn_color3"><spring:message code="common.button.deselect"/></a></div></td>
							</c:if>
						</tr>
						<tr>
							<th scope="row"><label for="label_6"><spring:message code="common.management.label.register"/></label></th>
							<td><div class="ui_input_text"><input type="text" value="" id="DEPT_RBOX_USER_NM" readonly/></div></td>
							<td id="select_register_manager"><div class="ui_btn_rapper align_right"><a href="#" id="button_select_register_manager" class="btn_color3"><spring:message code="common.button.select"/></a></div></td>
							<td id="deselect_register_manager"><div class="ui_btn_rapper align_left"><a href="#" id="button_Deselect_register_manager" class="btn_color3"><spring:message code="common.button.deselect"/></a></div></td>
						</tr>
						<tr>
							<th scope="row"><label for="label_6"><spring:message code="common.management.label.sender"/></label></th>
							<td><div class="ui_input_text"><input type="text" value="" id="DEPT_SBOX_USER_NM" readonly/></div></td>
							<td id="select_sender_manager"><div class="ui_btn_rapper align_right"><a href="#" id="button_select_sender_manager" class="btn_color3"><spring:message code="common.button.select"/></a></div></td>
							<td id="deselect_sender_manager"><div class="ui_btn_rapper align_left"><a href="#" id="button_Deselect_sender_manager" class="btn_color3"><spring:message code="common.button.deselect"/></a></div></td>
						</tr>
			  		</c:when>
			  		<c:otherwise>
  						<tr>
							<th scope="row"><label for="label_1"><spring:message code="org.dept.label.name"/></label></th>
							<td colspan="3"><div class="ui_input_text"><input type="text" value="" id="DEPT_NM"/></div></td>
						</tr>
						<tr>
							<th scope="row"><label for="label_2"><spring:message code="common.location.label.location"/></label></th>
							<td><div class="ui_input_text"><input type="text" value="" id="DEPT_PAR_NM" disabled="disabled"/></div></td>
							
							<td><div class="ui_btn_rapper align_right"><a href="#" id="button_select_location" class="btn_color3"><spring:message code="common.button.select"/></a></div></td>
							<td>
								<div class="ui_input_text"><input type="text" value="<spring:message code="common.location.label.lower"/>" id="DEPT_ADD_POS" disabled="disabled"/></div>
							</td>
						</tr>
						<tr>
							<th scope="row"><label for="label_3"><spring:message code="org.dept.label.departmentcode"/></label></th>
							<td colspan="3"><div class="ui_input_text"><input type="text" value="" id="DEPT_CD"/></div></td>							
						</tr>
						<tr>
							<th scope="row"><spring:message code="org.dept.label.auth"/></th>
							<td colspan="2">
								<span class="checkbox_rapper"><input type="checkbox" value="1" id="DEPT_BOX_F"/><label  for="DEPT_BOX_F"><spring:message code="org.dept.label.hasCabinet"/></label></span>
								<span class="checkbox_rapper"><input type="checkbox" value="1" id="DEPT_RBOX_F"/><label  for="DEPT_RBOX_F"><spring:message code="org.dept.label.hasRCabinet"/></label></span>
							</td>
						</tr>
						<tr>
							<th scope="row"><label for="label_6"><spring:message code="common.management.label.department"/></label></th>
							<td><div class="ui_input_text"><input type="text" value="" id="DEPT_BOX_USER_NM" readonly/></div></td>
							<td id="select_department_manager"><div class="ui_btn_rapper align_right"><a href="#" id="button_select_department_manager" class="btn_color3"><spring:message code="common.button.select"/></a></div></td>
							<c:choose>
								<c:when test="${empty dept.deptId}">
									<td><div class="ui_btn_rapper align_left"><a href="javascript:addUser('DEPT_BOX_USER_NM')" id="button_Add_company_admin" class="btn_color3"><spring:message code="common.button.assign"/></a></div></td>
								</c:when>
								<c:otherwise>
									<td id="deselect_department_manager"><div class="ui_btn_rapper align_left"><a href="#" id="button_Deselect_department_manager" class="btn_color3"><spring:message code="common.button.deselect"/></a></div></td>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<th scope="row"><label for="label_6"><spring:message code="common.management.label.register"/></label></th>
							<td><div class="ui_input_text"><input type="text" value="" id="DEPT_RBOX_USER_NM" readonly/></div></td>
							<td id="select_register_manager"><div class="ui_btn_rapper align_right"><a href="#" id="button_select_register_manager" class="btn_color3"><spring:message code="common.button.select"/></a></div></td>
							<td id="deselect_register_manager"><div class="ui_btn_rapper align_left"><a href="#" id="button_Deselect_register_manager" class="btn_color3"><spring:message code="common.button.deselect"/></a></div></td>
						</tr>
						<tr>
							<th scope="row"><label for="label_6"><spring:message code="common.management.label.sender"/></label></th>
							<td><div class="ui_input_text"><input type="text" value="" id="DEPT_SBOX_USER_NM" readonly/></div></td>
							<td id="select_sender_manager"><div class="ui_btn_rapper align_right"><a href="#" id="button_select_sender_manager" class="btn_color3"><spring:message code="common.button.select"/></a></div></td>
							<td id="deselect_sender_manager"><div class="ui_btn_rapper align_left"><a href="#" id="button_Deselect_sender_manager" class="btn_color3"><spring:message code="common.button.deselect"/></a></div></td>
						</tr>
		  			</c:otherwise>
			  	</c:choose>
				<tr style="display: none;">
					<th scope="row">Representative</th>
					<td colspan="2">
						<span class="radio_rapper"><input type="radio" value="1" id="DEPT_TOP_F_Y"/><label  for="DEPT_TOP_F_Y">Yes</label></span>
						<span class="radio_rapper"><input type="radio" value="2" id="DEPT_TOP_F_N"/><label  for="DEPT_TOP_F_N">No</label></span>
					</td>
				</tr>
			</tbody>
			</table>
		</div>
		</form>
		<!-- Input From start -->
		</div>
	</div>
	<!-- Content end -->	
</div>
<!-- Container end -->				   
</div>
<div id="div_popup" style="display: none"></div>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCopyright.jsp" %>
<!-- form start -->
<form action="/org.do" name="frm" id="frm" method="post">
	<input type="hidden" id="deptId" name="deptId" value="${user.orgnztId}">
</form>
</body>

<script language="javascript" type="text/javascript"> 
//<![CDATA[
$(function() {
	var location= $('#DEPT_PAR_NM').val();
	if(location == 'ROOT'){
		$('#button_select_location').parent().parent().css('display','none');
		$('#DEPT_ADD_POS').parent().parent().attr('colspan','2');
		$('#DEPT_ADD_POS').attr('disabled','disabled');
	}
});	
function assignSubmit(){
	$('input:hidden[name=DEPT_NM]').val($('#DEPT_NM').val());
	$('input:hidden[name=DEPT_CD]').val($('#DEPT_CD').val());
	$('input:hidden[name=DEPT_RBOX_F]').val(
			$('#DEPT_RBOX_F').is(":checked") ? "1" : "2"
	);
	$('input:hidden[name=DEPT_BOX_F]').val(
			$('#DEPT_BOX_F').is(":checked") ? "1" : "2"
	);
	$('input:hidden[name=DEPT_ADD_POS]').val($('#DEPT_ADD_POS').val());
	$('input:hidden[name=DEPT_PAR_NM]').val($('#DEPT_PAR_NM').val());
	
	document.aForm.action = "<c:url value='/deptSave.do'/>";
	document.aForm.submit();
}
function deptSave(){
	if (checkForm() &&
			confirm(
				env.isAdd
				? "<spring:message code="common.msg.confim.add"/>"
				: "<spring:message code="common.msg.confim.modify"/>")
			) {
				var saveUserType = $('input:hidden[name=saveUserType]').val();
				if(saveUserType == ""){
					formMapperSubmit();
					doSubmit();
				}else{
					assignSubmit();
				}
		}
}
function doSubmit() {	 
	document.aForm.action = "<c:url value='/deptSave.do'/>";
	document.aForm.submit();
}
function gotoList() {	 	
	document.aForm.action = "<c:url value='/userList.do'/>";
	document.aForm.submit();
}
function addUser(inputName) {
	var deptName = $('#DEPT_NM').val();
	$('input:hidden[name=saveUserType]').val(inputName);
	if(deptName == ""){
		alert("<spring:message code="org.user.msg.014"/>");
		return false;
	}
	$("body").css("overflow","");
	$("#div_popup").load(CONTEXT+'/simpleUserForm.do', {deptName : deptName, inputName : inputName},
			function(){
				if (typeof(select_modal_load) != "undefined") {
					select_modal_load();
				}
				$("#div_popup").show();
			}
	);
}
function formMapperSubmit() {
	$('input:hidden[name=DEPT_NM]').val($('#DEPT_NM').val());
	$('input:hidden[name=DEPT_CD]').val($('#DEPT_CD').val());
	$('input:hidden[name=DEPT_RBOX_F]').val(
			$('#DEPT_RBOX_F').is(":checked") ? "1" : "2"
	);
	$('input:hidden[name=DEPT_BOX_F]').val(
			$('#DEPT_BOX_F').is(":checked") ? "1" : "2"
	);
	$('input:hidden[name=DEPT_ADD_POS]').val($('#DEPT_ADD_POS').val());
	$('input:hidden[name=DEPT_PAR_NM]').val($('#DEPT_PAR_NM').val());
}
function checkNullValue(id, msg) {
    if ($.trim($('#' + id).val()) == "") {
        $('#' + id).focus();
        alert(msg);
        return true;
    }
	return false;
}
function checkRadioChecked(ids, msg) {
	for (var i = 0; i < ids.length; i++) {
		if ($('#' + ids[i]).is(":checked")) {
			return false;
		}
	}
	$('#' + ids[0]).focus();
    alert(msg.join("\r\n"));
    return true;
}
function checkForm() {
	if (checkNullValue('DEPT_NM', "<spring:message code="org.department.msg.001"/>")) return false;
	if (checkNullValue('DEPT_PAR_NM', "<spring:message code="org.department.msg.006"/>")) return false;
	if (checkNullValue('DEPT_BOX_USER_NM', "<spring:message code="org.department.msg.008"/>")) return false;
	if (checkRadioChecked(['DEPT_TOP_F_Y', 'DEPT_TOP_F_N'], ["Check Representative."])) return false;
	return true;
}
function setValue(obj, isDepartment) {
    console.log([
           "id          =" + obj.id,
           "name        =" + obj.name,
           "depth       =" + obj.depth,
           "childNode   =" + obj.childNode,
           "isDepartment=" + isDepartment
    ].join("\r\n"));
    
    var OriginalDeptId = "<c:out value="${dept.deptId}" />";
    var deptNm= $('#DEPT_NM').val();
    
    if (OriginalDeptId == "" || OriginalDeptId == null){
    	isDepartment = false;
    }
    
    if (isDepartment) {
		if (obj.childNode == "true") {
			alert("<spring:message code="org.department.msg.003"/>");
	        return false;
		}
		if (obj.id == "${param.DEPT_ID}") {
			alert("<spring:message code="org.department.msg.002"/>");
			return false;
		}
    }
	if(obj.name == "ROOT" || obj.name == "root"){
		obj.depth = 1;
	}
	
	$('input:hidden[name=' + env.targetId + ']').val(obj.id);
	$('#' + env.targetNm).val(obj.name);
	_setLocationCondition(obj.depth);
	resetButtonDeselect();
	return true;
}
function _setLocationCondition(depth) {
	console.log(["[_setLocationCondition] depth=", depth].join(""));
    $("#DEPT_ADD_POS").each(function() {
        $(this).find("option[value='CA']").remove();
        $(this).find("option[value='SA']").remove();
        if (depth == 1) {
            $(this).append("<option value='CA'>Child</option>");
        } else {
            $(this).append("<option value='CA'>Child</option>");
            $(this).append("<option value='SA'>Sibling</option>");
        } 
    });
}
function resetButtonDeselect() {
    $("#button_Deselect_register_manager").each(function() {
    	if ($('#DEPT_RBOX_USER_NM').val().length == 0) {
            $("#select_register_manager").css("display", "block");
            $("#deselect_register_manager").css("display", "none");
        } else {
            $("#select_register_manager").css("display", "none");
            $("#deselect_register_manager").css("display", "block");
        }
    });
    $("#button_Deselect_sender_manager").each(function() {
    	if ($('#DEPT_SBOX_USER_NM').val().length == 0) {
            $("#select_sender_manager").css("display", "block");
            $("#deselect_sender_manager").css("display", "none");
        } else {
            $("#select_sender_manager").css("display", "none");
            $("#deselect_sender_manager").css("display", "block");
        }
    });
    $("#button_Deselect_department_manager").each(function() {
    	if ($('#DEPT_BOX_USER_NM').val().length == 0) {
            $("#select_department_manager").css("display", "block");
            $("#deselect_department_manager").css("display", "none");
        } else {
            $("#select_department_manager").css("display", "none");
            $("#deselect_department_manager").css("display", "block");
        }
    });
}
var env = {
	isAdd: "${dept.deptId}" == "",
	isSysAdmin: "${sessionScope.user.systemAdmin}" == "true",
	isRepresentative: "${dept.deptTopF}" == "1",
	deptParDepth: "${deptParDepth}",
	targetId: null,
	targetNm: null,
};
$(function() {
	formMapperOnload();
    
	/* Select Location start*/
	$('#button_select_location').click(function() {
		if (env.isSysAdmin || env.isRepresentative) {
			return false;
		}
		env.targetId = "DEPT_PAR_ID";
		env.targetNm = "DEPT_PAR_NM";
		
		$("body").css("overflow","");
		$("#div_popup").load(CONTEXT+'/deptSelectionModal.do', {},
				function(){
					if (typeof(select_modal_load) != "undefined") {
						select_modal_load();
					}
					$("#div_popup").show();
				}
		);
	});
	/* Select Location end */
	
	/* Department, Register, Sender Select start */
    $([// select 버튼들
		"#button_select_department_manager",
		"#button_select_register_manager",
		"#button_select_sender_manager"
	    ].join(",")).click(function() {
    	  
        var id = $(this).attr("id");
        if (id == "button_select_department_manager") {
        	env.targetId = "DEPT_BOX_USER_ID";
        	env.targetNm = "DEPT_BOX_USER_NM";
        } else if (id == "button_select_register_manager") {
            env.targetId = "DEPT_RBOX_USER_ID";
            env.targetNm = "DEPT_RBOX_USER_NM";
        } else if (id == "button_select_sender_manager") {
            env.targetId = "DEPT_SBOX_USER_ID";
            env.targetNm = "DEPT_SBOX_USER_NM";
        }
        
		$("body").css("overflow","");
		$("#div_popup").load(CONTEXT+'/userSelectionModal.do',{},
				function(){
					if (typeof(select_modal_load) != "undefined") {
						select_modal_load();
					}
					$("#div_popup").show();
				}
		);
    });
	/* Department, Register, Sender Select end */
	
    $([// Deselect 버튼들
       "#button_Deselect_department_manager",
       "#button_Deselect_register_manager",
       "#button_Deselect_sender_manager"
       ].join(",")).click(function() {    
    
        var id = $(this).attr("id");
        if (id == "button_Deselect_department_manager") {
            $('#DEPT_BOX_USER_NM').val("");        
            $('input:hidden[name=DEPT_BOX_USER_ID]').val("");
        } else if (id == "button_Deselect_register_manager") {
            $('#DEPT_RBOX_USER_NM').val("");        
            $('input:hidden[name=DEPT_RBOX_USER_ID]').val("");
        } else if (id == "button_Deselect_sender_manager") {
            $('#DEPT_SBOX_USER_NM').val("");        
            $('input:hidden[name=DEPT_SBOX_USER_ID]').val("");
        }
        resetButtonDeselect();
    });
    
    /* sysadmin일 경우, root Child 만 적용 */
    /* 기관(representative=Y)인 경우 */
    if (env.isSysAdmin || env.isRepresentative || env.deptParDepth == 1) {
    	_setLocationCondition(1);
    } else {
    	_setLocationCondition(env.deptParDepth);
    }
    resetButtonDeselect();
})
//]]>
</script>
</html>