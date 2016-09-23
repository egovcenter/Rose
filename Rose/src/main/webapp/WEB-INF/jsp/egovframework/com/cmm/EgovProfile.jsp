<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script>
var CONTEXT = "${pageContext.servletContext.contextPath}";
var env = {
};
$(function() {
    $("#IMAGE_PHOTO, #IMAGE_SIGN").load(function() {
        var w = $(this).css("width").replace("px", "");
        var h = $(this).css("height").replace("px", "");
        console.log([w, h].join("x"));
        $(this).css({
            width: 106,
            height: 106
        });
    });
	$(".layerPopup_rapper.profilePop").css({"right":"20px", "left":"inherit", "top":"200px"});
});
function doSubmit(obj) {
	$('#uploadItem').submit();
}
function profileClose() {
    if (parent) {
        parent.closePopup();
    } else {
        window.close();
    }
}
function profileLogout() {
	top.location.href = CONTEXT+"/uat/uia/actionLogout.do";
}
function setting(){
	top.location.href = CONTEXT+"/profileSetting.do";
}
</script>
</head>
<body>
<div class="layerPopup_rapper" style="width: 200px; margin-top:0;">   
	<div class="ui_btn_rapper" style="text-align: left;">
		<p>
			<img src="downloadImg.jsp?FN=${user.orgnztId}${user.uniqId}&FT=photo" width="106" height="106" id="IMAGE_PHOTO" alt="photo"/>
			<span style="width: 10px;">&nbsp;</span>
			<c:out value="${user.emplyrNm}"/>
		</p>
	</div>
	<div class="ui_btn_rapper" style="text-align: left;">
		<p>
			<a href="javascript:setting()" id="link_setting"><spring:message code="org.profile.label.setting"/></a>
		</p>
	</div>
	<div class="ui_btn_rapper">
		<table>
			<tr>
				<td style="text-align: left; padding-left: 18px;">
					<a href="javascript:profileClose()" class="btn_color2 profileClose" id="button_close"><spring:message code="common.button.close"/></a>
				</td>
				<td style="text-align: right; padding-right: 18px;">
					<a href="javascript:profileLogout()" class="btn_color3" id="button_logout"><spring:message code="common.button.logout"/></a>
				</td>
			</tr>
		</table>
	</div>
</div>
</html>
