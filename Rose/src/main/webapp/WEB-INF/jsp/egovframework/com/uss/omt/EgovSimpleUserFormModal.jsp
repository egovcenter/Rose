<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script src="<c:url value='/js/egovframework/com/uss/omt/orgCommon.js'/>"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jquery-1.11.3.js'/>"></script>
<script>
var CONTEXT = "${pageContext.servletContext.contextPath}";
var MIN_PASSWORD_LENGTH = 8;
function select_modal_load(){
	$("#shadow_org").css(
		{
			width:$(document).width(),
			height:$(document).height(),
			opacity:0.5
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
}
function close(){
	$("#div_popup").hide();
}
function formMapperSubmit() {
    $('input:hidden[name=USER_EM_CD]').val($('#USER_EM_CD').val());
    $('input:hidden[name=USER_NM]').val($('#USER_NM').val());
    if("${inputName}" == "DEPT_ADMIN_USER_NM"){
    	$('input:hidden[name=POSI_ID]').val('POSI_0000000000000');
	    $('input:hidden[name=USER_SLVL]').val("0");
    }else{
    	$('input:hidden[name=POSI_ID]').val($('#POSI_ID option:selected').val());
	    $('input:hidden[name=USER_SLVL]').val($('#USER_SLVL').val());
    }
    var DUTY_ID = $('#DUTY_ID option:selected').val();
    if(DUTY_ID =='< Blank >'){DUTY_ID='';}
    $('input:hidden[name=DUTY_ID]').val(DUTY_ID);
	
    $('input:hidden[name=USER_LOGIN]').val($('#USER_LOGIN').val());
    $('input:hidden[name=USER_LPWD]').val($('#USER_LPWD').val());
    $('input:hidden[name=USER_SPWD]').val($('#USER_SPWD').val());
}
function checkNullValue(id, msg) {
    if ($.trim($('#' + id).val()) == "") {
        $('#' + id).focus();
        alert(msg);
        return true;
    }
    return false;
}
function checkNumber(id, msg) {
    var val = $('#' + id).val(); 
    if (isNaN(val)) {
        $('#' + id).focus();
        alert(msg);
        return true;
    }
    return false;
}
function checkNumberRange(id, min, max, msg) {
    var val = parseInt($('#' + id).val()); 
    if (val < min || val > max) {
        $('#' + id).focus();
        alert(msg);
        return true;
    }
    return false;
}
function checkNullSelect(id, msg) {
	var val = $("#" + id + " option:selected").val();
	if (val == undefined || val == "") {
		alert(msg);
		return true;
	}
	return false;
}
function checkPasswordLength(id, msg) {
	var val = $("#" + id).val();

	if (val.length >= MIN_PASSWORD_LENGTH) {
		return false;
	}
	alert(msg);
	return true;
}
function checkDuplicateValue(id, msg, callback) {
	var val = $('#' + id).val();
	$.ajax({
		type: "POST",
		url: "userCheckLoginId.do",
		data: {USER_LOGIN: val},
		success: function(data) {
			var val = data.trim();
			if (val == "exist") {
				$("#" + id).focus();
				alert(msg);
			} else {
				callback();
			}
		},
		error: function(e) {
			alert("id check error!!");
		}
	});
}
function checkForm() {
    if (checkNullValue('USER_NM'    , "<spring:message code="org.user.msg.001"/>"))      return false;
    if (checkNullValue('M_DEPT_NM'    , "<spring:message code="org.user.msg.002"/>"))     return false;
    if("${inputName}" != "DEPT_ADMIN_USER_NM"){
   		if (checkNullSelect('POSI_ID' , "<spring:message code="org.user.msg.003"/>"))       return false;
    }
    if (checkNullValue('USER_LOGIN' , "<spring:message code="org.user.msg.004"/>"))       return false;
    if (checkNullValue('USER_LPWD'  , "<spring:message code="org.user.msg.005"/>")) return false;
    if (checkPasswordLength('USER_LPWD', "<spring:message code="org.login.msg.005"/>")) return false;

    return true;
}
function doSubmit() {
	var inputName = "${inputName}";
    formMapperSubmit();
    $("#"+inputName).val($("#USER_NM").val());
    close();
}
$(function() {
    /* 일반사용자에 적용할 직위 정보가 없을 경우 경고 메시지 표시 */
    if("${inputName}" != "DEPT_ADMIN_USER_NM"){
	    if ($("#POSI_ID option").size() == 0) {
	    	alert(["<spring:message code="org.position.msg.005"/>"].join("\r\n"));
	    }
    }
    
    //보안등급 (1~10)
    for(var i = 1; i <= 10; i++){
		$('#USER_SLVL').append('<option value="'+ i +'"  >' + i + '</option>');
  	}
    
    $('#checkBoxId').change(function(){
        if($('#checkBoxId').is(':checked')){
            //same as login password
            $('#USER_SPWD').val($('#USER_LPWD').val());
            
        }else{
            $('#USER_SPWD').val('');
        }
    });
    //직급변경에 따른 비밀등급 셋팅
    $('#POSI_ID').change(function(){
        $('#USER_SLVL').val($(this).find('option:selected').data('id'));
     });
    
    /* Security Level 사용자 입력이 불가능하게 readonly 설정. Position 선택에 따라 자동 입력 */
    $("#USER_SLVL").attr("readonly", true);
    
    $("#USER_SPWD").keydown(function() {
    	if ($("#checkBoxId").is(":checked")) {
    		$("#checkBoxId").attr("checked", false);
    	}
    });
    
});
function userSave(){
	if (checkForm()) {
		checkDuplicateValue('USER_LOGIN' , "<spring:message code="org.user.msg.008"/>", doSubmit);
	}
}
</script>
</head>

<body>
<div class="layerPopup_rapper"> 
	<p class="popup_title type_admin"><c:out value="New"/> <spring:message code="org.user.label.user"/></p>
	<div class="overflow_hidden">
	    <div class="ui_btn_rapper float_right mtb10">
            <a href="javascript:userSave()" class="btn_color3" id="button_save"><spring:message code="common.button.apply"/></a>
	        <a href="javascript:close();" class="btn_color2" id="button_close"><spring:message code="common.button.cancel"/></a>
	    </div>
	</div>	
		    
    <div class="display_tableCell_top vAlign_top">
	    <table summary="" class="board_width_borderNone">
	        <caption class="blind"></caption>
	        <colgroup>
	            <col width="140px"/>
	            <col width="*"/>
	        </colgroup>                         
	        <tbody>
	            <tr>
	                <th scope="row"><spring:message code="org.user.label.name"/></th>
	                <td colspan="2">
	                    <div class="ui_input_text">
	                        <input type="text" value="" id="USER_NM" maxlength="30"/>
	                    </div>
	                </td>           
	            </tr>
	            <tr>
	                <th scope="row">
	                	<c:choose>
	                		<c:when test="${inputName eq 'DEPT_ADMIN_USER_NM'}">
	                			 <spring:message code="org.company.label.company"/>		
	                		</c:when>
	                		<c:otherwise>
	                		 	<spring:message code="org.user.label.department"/>
	                		</c:otherwise>
	                	</c:choose>
                	</th>
	                <td colspan="2">
	                    <div class="ui_input_text">
	                        <input type="text" value="${deptName}" id="M_DEPT_NM" readonly="readonly"/>
	                    </div>
	                </td>
	            </tr>
	            <tr>
	                <th scope="row"><spring:message code="org.user.label.empID"/></th>
	                <td colspan="2">
	                    <div class="ui_input_text">
	                        <input type="text" value="" id="USER_EM_CD" maxlength="30"/>
	                    </div>
	                </td>           
	            </tr>
	            <tr>
	                <th scope="row"><spring:message code="org.user.label.position"/></th>
	                <td colspan="2">
                	<c:choose>
                		<c:when test="${inputName eq 'DEPT_ADMIN_USER_NM'}">
                                 <div class="ui_input_text">
                                     <input type="text" value="Admin" id="POSI_ID" readonly="readonly"/>
                                 </div>
                		</c:when>
                		<c:otherwise>
                                  <div class="ui_select_rapper">
                                     <select id="POSI_ID">
                                         <option></option>
                                      <c:forEach var="prow" items="${posiList}" varStatus="pstatus">
                                          <option  data-id="${prow.posiLv}" value="${prow.posiId}" ${prow.posiId == user.positionId?"selected":''}>${prow.posiNm }</option>
                                      </c:forEach>
                                     </select>
                                 </div>
                		</c:otherwise>
               		</c:choose>
	                </td>           
	            </tr>
	            <c:if test="${inputName ne 'DEPT_ADMIN_USER_NM'}">
	            <tr>
	                <th scope="row"><spring:message code="org.user.label.duty"/></th>
	                <td colspan="2">
	                    <div class="ui_select_rapper">
	                        <select id="DUTY_ID">
	                            <option>&lt; Blank &gt;</option>
	                        <c:forEach var="drow" items="${dutyList}" varStatus="dstatus">
	                            <option value="${drow.dutyId}" ${dprow.dutyId == user.dutyId?'selected':''}>${drow.dutyNm }</option>
	                        </c:forEach>
	                        </select>
	                    </div>
	                </td>           
	            </tr>
	            <tr>
	                <th scope="row"><spring:message code="org.user.label.securitylevel"/></th>
	                <td colspan="2">
	                    <div class="ui_input_text">
	                        <input type="text" value="" id="USER_SLVL" maxlength="3"/>
	                    </div>
	                </td>           
	            </tr>
	            </c:if>
	            <tr>
	                <th scope="row"><spring:message code="org.user.label.id"/></th>
	                <td colspan="2">
	                    <div class="ui_input_text">
	                        <input type="text" value="" id="USER_LOGIN" maxlength="30"/>
	                    </div>
	                </td>           
	            </tr>
	            <tr>
	                <th scope="row"><spring:message code="org.user.label.loginpasswd"/></th>
	                <td colspan="2">
	                    <div class="ui_input_text">
	                        <input type="password" value="" id="USER_LPWD" maxlength="30"/>
	                    </div>
	                </td>           
	            </tr>
	            <c:if test="${inputName ne 'DEPT_ADMIN_USER_NM'}">
		            <tr>
		                <th scope="row"><spring:message code="org.user.label.appvlpasswd"/></th>
		                  <td colspan="2">
		                      <div class="ui_input_text">
		                          <input type="password" value="" id="USER_SPWD" />
								  <input type="checkbox" value="" id="checkBoxId" />
		                      <span><label for="checkBoxId"><spring:message code="org.user.label.sameasloginpasswd"/></label></span>
		                    </div>
		                </td>
		            </tr>
	            </c:if>
	        </tbody>
	    </table>
   	</div>
</div>
<div id="shadow_org" class="shadow_org"></div>
</body>
</html>