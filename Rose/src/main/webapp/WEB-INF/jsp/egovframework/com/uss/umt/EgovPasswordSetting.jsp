<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="utf-8">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script src="<c:url value='/js/egovframework/com/uss/omt/orgCommon.js'/>"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jquery-1.11.3.js'/>"></script>
</head>
<body>
<div class="wrap">
	<form name="passwordForm" id="passwordForm" method="post" action="${pageContext.servletContext.contextPath}/passwordSave.do">
	<!--  Top Menu Start --> 
	<jsp:include page="/WEB-INF/jsp/egovframework/com/cmm/EgovTopMenu.jsp" flush="false">
		<jsp:param value="HOME" name="selectedMenu"/>
	</jsp:include>
	<!--  Top Menu End--> 
	<div class="clear"></div>
	<!-- Container Start -->
	<div class="Container"> 
	<!-- Lnb start-->
	<jsp:include page="/WEB-INF/jsp/egovframework/com/uss/umt/EgovLeftMenu.jsp" flush="false">
		<jsp:param value="password" name="selectedMenu"/>
	</jsp:include>
	<!-- Lnb end -->
		<!-- Content start-->
		<div class="Content">
			<!-- Content box Start -->
			<div class="content_box"> 
				<!-- Menu Title start -->
				<div class="title_box">
					<div class="title_line">
						<h1>Password</h1>						
					</div>	
					<div class="clear"></div>				
				</div>
				<!-- Menu Title end-->
				<!-- password start -->
	  			<div class="ui_btn_rapper float_right">
             		<a href="javascript:apply();" class="btn_color3" id="button_apply"><spring:message code="common.button.apply"/></a>
             		<a href="#" class="btn_color2" id="button_cancel"><spring:message code="common.button.cancel"/></a>
         		</div>
         		<div class="h36"></div>	
				<div class="display_table clear_Both mtb10">
					<div class="display_tableCell vAlign_top">
						<table summary="" class="board_width_borderNone">
							<caption class="blind"></caption>
                            <colgroup>
                                <col width="10%"/>
                                <col width="*"/>
                            </colgroup>                         
                            <tbody>
                                <tr>
                                    <th>Old Password</th>
                                    <td>
                                        <div class="ui_input_text">
                                            <input type="password" style="width: 100%;" value="" name="oldPW" id="oldPW"/>
                                        </div>
                                    </td>           
                                </tr>
                                <tr>
                                    <th>New Password</th>
                                    <td>
                                        <div class="ui_input_text">
                                            <input type="password" style="width: 100%;" value="" name="newPW" id="newPW"/>
                                        </div>
                                    </td>           
                                </tr>
                                <tr>
                                    <th>Confirm</th>
                                    <td>
                                        <div class="ui_input_text">
                                            <input type="password" style="width: 100%;" value="" name="confirmPW" id="confirmPW"/>
                                        </div>
                                    </td>           
                                </tr>
                            </tbody>						
						</table>
					</div>
				</div>
			   <!-- password end -->
			</div>
			<!-- content_box End -->
		</div>
		<!-- Content End --> 
	</div>
	<!-- Container End -->
	<div class="clear"></div>
</form>
</div>
<!-- wrap End -->
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCopyright.jsp" %>
</body>
<script type="text/javascript">
var MIN_PASSWORD_LENGTH = 8;
function checkNullValue(id, msg) {
    if ($.trim($('#' + id).val()) == "") {
        $('#' + id).focus();
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
function checkCompareValue(compare1, compare2, msg) {
	var val1 = $("#" + compare1).val(); 
	var val2 = $("#" + compare2).val(); 
	if(val1 != val2) {
		alert(msg);
		return true;
	}
	return false;
}
function checkDuplicateValue(id, msg, callback) {
	var val = $('#' + id).val();
	$.ajax({
		type: "POST",
		url: "passwordCheck.do",
		data: {oldPW: val},
		success: function(data) {
			var val = data.trim();
			if(val == "NoMatch"){
				$("#" + id).focus();
				alert(msg);
			}else{
				callback();
			}
		},
		error: function(e) {
			alert("password check error!!");
		}
	});
}
function checkForm() {
    if (checkNullValue('oldPW'    , "<spring:message code="org.user.msg.009"/>"))      return false;
    if (checkNullValue('newPW'    , "<spring:message code="org.user.msg.010"/>"))     return false;
    if (checkNullValue('confirmPW'    , "<spring:message code="org.user.msg.011"/>"))     return false;
    if (checkPasswordLength('newPW', "<spring:message code="org.login.msg.005"/>")) return false;
	if (checkCompareValue('newPW', 'confirmPW', "<spring:message code="org.user.msg.012"/>")) return false;
    return true;
}
function doSubmit(){
	document.passwordForm.submit();
}
function apply(){
	if (checkForm()) {
		checkDuplicateValue('oldPW' , "<spring:message code="org.user.msg.013"/>", doSubmit);
	}
}
</script>
</html>