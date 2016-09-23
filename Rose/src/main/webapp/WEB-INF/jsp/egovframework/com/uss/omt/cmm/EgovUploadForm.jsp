<!doctype html>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%> 
<script src="<c:url value='/js/egovframework/com/uss/omt/orgCommon.js'/>"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jquery-1.11.3.js'/>"></script>
<script>
function doSubmit(obj) {
	$('#uploadItemVO').submit();
}

function windowClose() {
    if (parent) {
        parent.closeModal();
    } else {
        window.close();
    }
}
</script>
<script>
var env = {
	savedFilename: "${uploadItemVO.savedFilename}"
}

$(function() {
    if (env.savedFilename != "" && parent) {
    	parent.setImage({
    		savedFilename: env.savedFilename
    	});
    	windowClose();
    }
    $("#button_upload").click(function() {
    	var path = $("#fileData").val();
    	if (path == "") {
    		alert("Select Image file.");
    	} else if (path != "") {
    		doSubmit(null);
    	}
    });
    $("#button_close").click(function() {
    	windowClose();
    });
});
</script>
</head>

<body>
<div class="layerPopup_rapper popup_type400 div_upload">   
    <p class="popup_title type_admin"><spring:message code="common.label.upload"/></p>
    <div class="ui_btn_rapper float_right mtb10">
        <a href="#" id="button_upload" class="btn_color3"><spring:message code="common.button.upload"/></a>
        <a href="#" id="button_close" class="btn_color2"><spring:message code="common.button.close"/></a>
    </div>
	<!-- tree -->
	<div style="clear: both;">
	    <form:form modelAttribute="uploadItemVO" method="post" enctype="multipart/form-data">
	        <form:hidden path="name"/>
	        <fieldset>
	            <p>
	                <input type="file" name="fileData" id="fileData"/>
	            </p>
	        </fieldset>
	    </form:form>
	</div>
</div>
</html>