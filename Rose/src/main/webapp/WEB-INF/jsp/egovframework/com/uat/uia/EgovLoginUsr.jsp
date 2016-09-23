<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<%
 /**
  * @Class Name : EgovLoginUsr.jsp
  * @Description : Login 인증 화면
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2009.03.03    박지욱          최초 생성
  *   2011.09.25    서준식          사용자 관리 패키지가 미포함 되었을때에 회원가입 오류 메시지 표시
  *   2011.10.27    서준식          사용자 입력 탭 순서 변경
  *  @author 공통서비스 개발팀 박지욱
  *  @since 2009.03.03
  *  @version 1.0
  *  @see
  *
  *  Copyright (C) 2009 by MOPAS  All right reserved.
  */
%>
<html lang="utf-8">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script type="text/javaScript" language="javascript">
function actionLogin() {
	if(checkForm()){
        document.loginForm.action="<c:url value='/uat/uia/actionLogin.do'/>";
        document.loginForm.submit();
	}
}
function checkForm() {
	 if (document.loginForm.id.value =="") {
	 	alert("Input Username.");
	 	document.loginForm.id.focus();
	 	return false;
	} else if (document.loginForm.password.value =="") {
	 	alert("Input Password.");
	 	document.loginForm.password.focus();
	 	return false;
	}
    return true;
}
function setCookie (name, value, expires) {
    document.cookie = name + "=" + escape (value) + "; path=/; expires=" + expires.toGMTString();
}
function getCookie(Name) {
    var search = Name + "=";
    if (document.cookie.length > 0) { // 쿠키가 설정되어 있다면
        offset = document.cookie.indexOf(search);
        if (offset != -1) { // 쿠키가 존재하면
            offset += search.length;
            // set index of beginning of value
            end = document.cookie.indexOf(";", offset);
            // 쿠키 값의 마지막 위치 인덱스 번호 설정
            if (end == -1)
                end = document.cookie.length;
            return unescape(document.cookie.substring(offset, end));
        }
    }
    return "";
}
function saveid(form) {
    var expdate = new Date();
    // 기본적으로 30일동안 기억하게 함. 일수를 조절하려면 * 30에서 숫자를 조절하면 됨
    if (form.checkId.checked)
        expdate.setTime(expdate.getTime() + 1000 * 3600 * 24 * 30); // 30일
    else
        expdate.setTime(expdate.getTime() - 1); // 쿠키 삭제조건
    setCookie("saveid", form.id.value, expdate);
}

function getid(form) {
    form.checkId.checked = ((form.id.value = getCookie("saveid")) != "");
}
function fnInit() {
	/* if (document.getElementById('loginForm').message.value != null) {
	    var message = document.getElementById('loginForm').message.value;
	} */
	var message = "${message}";
    if (message != "") {
        alert(message);
    }

    //getid(document.loginForm);
    document.loginForm.id.focus();
}
</script>
</head>
<body onLoad="fnInit();">
<form name="loginForm" action ="<c:url value='/uat/uia/actionLogin.do'/>" method="post">
    <input name="userSe" type="hidden" value="USR"/>
            
	<div id="login_messages" style="display:none;"></div>
	
	<div id="div_login" class="wrap"> 
		<div class="Login_wrap">
			<div class="title_box"><span class="tx_b">BridgeOffice</span></div>
			<div class="pb10"></div>
			<div class="thum_big"><img src="<c:out value="${CONTEXT}" />/images/egovframework/com/cmm/thum_empty.png" onfocus="blur();"></div>
			<div class="pb10"></div>
			<div class="idpx_box"><input id="id" name="id" type="text" title="Username" placeholder="Username" onFocus="this.placeholder='';" onKeyDown="javascript:if (event.keyCode == 13) { actionLogin(); }" class="input_idpw"></div>
			<div id='directory-homonyms'></div>
			<div id='directory-otheroffices'></div>
			<div class="idpx_box"><input id="password" name="password" type="password"  title="Password" placeholder="Password" onFocus="this.placeholder='';" class="input_idpw" onKeyDown="javascript:if (event.keyCode == 13) { actionLogin(); }"></div>
			<div class="but_box pl05"><input id="login" name="login"  type="button" value="<spring:message code='common.login.button.login'/>" class="but_login"  onclick="actionLogin()" onfocus="blur();" /> </div>
		</div>
	</div>
</form>

<footer>
	<div class="footer_version"><span>v2.0.1</span></div>
</footer>

<script src="<c:out value="${CONTEXT}" />/js/egovframework/com/cmm/jquery-1.11.3.js"></script>
</body>
</html>