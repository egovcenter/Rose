<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery-1.7.1.js'/>"></script>
<script type="text/javascript" src="<c:url value='/html/egovframework/com/cmm/utl/ckeditor/ckeditor.js'/>"></script>
<script type="text/javascript">
var APPROVAL_CONTEXT = "${pageContext.servletContext.contextPath}";
var userId = "${user.uniqId}";
var docId = "${doc.docID}";
var action = "${action}";

function settingDocumentContent(){
	var contentHtml = $("#editorContent").html();
	if(contentHtml != null){
		if(action == "edit"){
			var content = '<textarea id="content" class="ckeditor">'+contentHtml+'</textarea>';
			$('div#editorContent').replaceWith(content);
		}else if(action == "view"){
			$('div#editorContent').replaceWith('<iframe id="iframe_content" scrolling="yes"></iframe>');
			settingIframe(contentHtml);
		}
	}
}
function settingIframe(newContent) {
	if(newContent != null){
		var styleSheet = "<link rel='stylesheet' type='text/css' href='html/egovframework/com/cmm/utl/ckeditor/contents.css'>"
		var iframeHead = $("#iframe_content").contents().find("head");
		iframeHead.append(styleSheet);
		
		var iframeBody = $("#iframe_content").contents().find("body");
		iframeBody.append(newContent);
		autoResize("iframe_content");
	}
}
function autoResize(id) {
	var iframeBodyHeight = document.getElementById(id).contentWindow.document.body.scrollHeight;
	$("#"+id).css("height", iframeBodyHeight);
}
$(function(){
	settingDocumentContent();
});
</script>
</head>
<body>
<!-- approval_document Start-->
<div class="tabContent_rapper" id="approval_document">
	<div class="title_box">
	<div class="but_line">
		<c:if test="${action eq 'edit'}">
			<input type="button" id="apply_popup_button" value='<spring:message code="common.button.apply"/>' class="but_navy" onClick="javascript:applyPopup();"/>
		</c:if>
		<input type="button" value='<spring:message code="common.button.cancel"/>' class="but_grayL" onClick="javascript:closePopup();"/>
	</div>
	</div>
	<div class="table_rapper">
		<form id="draft_form" name="draft_form" action="<c:out value="${pageContext.servletContext.contextPath}"/>/draft.do" method="post">
		<div class="print_rapper" id="draft_body">
			<c:out value="${docBody}" escapeXml="false"/>
		</div>
		</form>
	</div>			
</div>
<!-- approval_document End -->
</body>
<script language="javascript" type="text/javascript"> 
//<![CDATA[
function closePopup(){
	window.close();
}
function applyPopup(){
	$('div').remove('#cke_content');
	var ckeditorContent = "<div id='editorContent'>"+CKEDITOR.instances.content.getData()+"</div>";
	$('textarea#content').replaceWith(ckeditorContent);
	var docBody = $("#draft_body").html();
	
	$.ajax({                        
		type: "POST",
		url: APPROVAL_CONTEXT +"/saveDocument.do",
		dataType: 'html',
		cache: false,
		data:{"docId": docId, "docBody": docBody},
		success: function(data) {
			window.opener.changeDocument(docBody);
			window.close();
		},
		error : function(jqXHR, textStatus, errorThrown){
			alert("documentEditor error. textStatus["+textStatus+"], errorThrown["+errorThrown+"]");
			draft_debug("documentEditor error. textStatus["+textStatus+"], errorThrown["+errorThrown+"]");
		}
    });
}
//]]>
</script>
</html>