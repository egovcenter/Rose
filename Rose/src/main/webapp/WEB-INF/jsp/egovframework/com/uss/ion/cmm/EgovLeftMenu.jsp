<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>jQuery treeview</title>
 
<link rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/jquery.treeview.css'/>" />
<script src="<c:url value='/js/egovframework/com/cmm/jquery.treeview.js'/>" type="text/javascript"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jquery.cookie.js'/>" type="text/javascript"></script>
<script type="text/javascript"> 
$(function() {
	$("#tree").treeview({
		animated: "medium",
		control:"#sidetreecontrol",
		persist: "cookie"
	});
	
})
function fn_select_Label(labelId,labelNm) {
	document.getElementById("docType").value = "label";
	document.getElementById("labelId").value = labelId;
	document.getElementById("labelNm").value = labelNm;
	document.listForm.action = "<c:url value='/approvalDocPageList.do'/>";
	document.listForm.submit();
}
function fn_select_Doc(docType) {
	document.getElementById("docType").value = docType;
	document.listForm.action = "<c:url value='/approvalDocPageList.do'/>";
	document.listForm.submit();
}
function fn_select_Mytodo() {
	document.getElementById("docType").value = "mytodo";
	document.listForm.action = "<c:url value='/mytodo.do'/>";
	document.listForm.submit();
}
</script>
</head>
<body>
<div class="Side"> 
	<!-- Common Title -->
	<div class="lnb_ap">
		<c:if test="${param.CommonTitle eq 'processing'}">
			<spring:message code="appvl.sidemenu.label.processing"/>
		</c:if>
		<c:if test="${param.CommonTitle eq 'mycabinet'}">
		<spring:message code="appvl.sidemenu.label.mycabinet"/>
		</c:if>
		<c:if test="${param.CommonTitle eq 'cabinet'}">
			<spring:message code="appvl.sidemenu.label.cabinet"/>
		</c:if>
		<c:if test="${param.CommonTitle eq 'approval'}">
			<spring:message code="appvl.sidemenu.label.approval"/>
		</c:if>
	</div>
	<div class="h36"></div>
	<!-- Common Button -->
	<div class="lnb_butbox">
		<c:if test="${param.CommonButton eq 'draft'}">
			<input type="button" value='<spring:message code="appvl.sidemenu.button.draft"/>' class="but_big"/>
		</c:if>
		<c:if test="${param.CommonButton eq 'register'}">
			<input type="button" value='<spring:message code="appvl.sidemenu.button.register"/>' class="but_big"/>
		</c:if>
	</div>
	<!-- Menu Tree -->
	<div class="lnb_tree" style="background:#FFF;">
		<jsp:include page="/WEB-INF/jsp/egovframework/com/uss/ion/cmm/EgovFormSelectionModal.jsp" flush="false" />
		<div id="sidetree" style="background:#fff;">
			<ul id="tree">
				<li>
					<strong><spring:message code="appvl.sidemenu.tree.processing"/></strong>
					<ul class="filetree">
						<li><span class="file"><a href="#" class="filetree" onclick="javascript:fn_select_Mytodo();return false;" id="mytodo"><spring:message code="appvl.sidemenu.tree.processing.mytodo"/></a></span></li>
						<li><span class="file"><a href="#" class="filetree" onclick="javascript:fn_select_Doc('waiting');return false;" id="waiting"><spring:message code="appvl.sidemenu.tree.processing.waiting"/></a></span></li>
						<c:if test="${userAuth.sendAuth == '1' }">
							<li><span class="file"><a href="#" class="filetree" onclick="javascript:fn_select_Doc('outgoing');return false;" id="outgoing"><spring:message code="appvl.sidemenu.tree.processing.outgoing"/></a></span></li>
						</c:if>
						<c:if test="${userAuth.receiveAuth == '1' }">
							<li><span class="file"><a href="#" class="filetree" onclick="javascript:fn_select_Doc('incoming');return false;" id="incoming"><spring:message code="appvl.sidemenu.tree.processing.incoming"/></a></span></li>
						</c:if>
						<li><span class="file"><a href="#" class="filetree" onclick="javascript:fn_select_Doc('ongoing');return false;" id="ongoing"><spring:message code="appvl.sidemenu.tree.processing.ongoing"/></a></span></li>
					</ul>
				</li>
				<li>
					<strong><spring:message code="appvl.sidemenu.tree.mycabinet"/></strong>
					<ul class="filetree">
						<li><span class="file"><a href="#" class="filetree" onclick="javascript:fn_select_Doc('draft');return false;" id="draft"><spring:message code="appvl.sidemenu.tree.mycabinet.draft"/></a></span></li>
						<li><span class="file"><a href="#" class="filetree" onclick="javascript:fn_select_Doc('approval');return false;" id="approval"><spring:message code="appvl.sidemenu.tree.mycabinet.approval"/></a></span></li>
						
					</ul>
				</li>
				<li>
					<strong><spring:message code="appvl.sidemenu.tree.cabinet"/></strong>
					<ul class="filetree">
						<c:if test="${deptAuth.inboxAuth == '1'}">
							<li><span class="file"><a href="#" class="filetree" onclick="javascript:fn_select_Doc('inbox');return false;" id="inbox"><spring:message code="appvl.sidemenu.tree.cabinet.inbox"/></a></span></li>
						</c:if>
						<c:if test="${deptAuth.outboxAuth == '1'}">
							<li><span class="file"><a href="#" class="filetree" onclick="javascript:fn_select_Doc('outbox');return false;" id="outbox"><spring:message code="appvl.sidemenu.tree.cabinet.outbox"/></a></span></li>
						</c:if>
						<c:if test="${userAuth.receiveAuth == '1' }">
							<li><span class="file"><a href="#" class="filetree" onclick="javascript:fn_select_Doc('passbox');return false;" id="passbox"><spring:message code="appvl.sidemenu.tree.cabinet.passbox"/></a></span></li>
						</c:if>
						<jsp:include page="/WEB-INF/jsp/egovframework/com/uss/omt/cmm/EgovLabelTree.jsp" flush="false">
							<jsp:param value="multi" name="type"/>
							<jsp:param value="Y" name="fusion"/>
						</jsp:include>
					</ul>
				</li>
			</ul>
		</div>

		<form action="<c:url value='/approvalDocPageList.do'/>" name="listForm" id="listForm" method="post">
			<input type="hidden" value="" id="docType" name="docType" class="docType">
			<input type="hidden" value="" id="labelId" name="labelId">
			<input type="hidden" value="" id="labelNm" name="labelNm">
			<input type="hidden" value="" id="selectformID" name="selectformID">
			<input type="hidden" value="" id="selectformType" name="selectformType">
			<input type="hidden" value="" name="orgdocId">
			<input type="hidden" value="" name="isincoming">
		</form>
	</div>
</div>
</body>
<script language="javascript" type="text/javascript"> 
//<![CDATA[
var docType = "<c:out value="${docType}"/>";
$(document).ready(function(){
	if(docType == "label"){
		var labelID = "<c:out value="${labelID}"/>";
		var selectedLabel = "#node_"+labelID;
		$(selectedLabel).css('color', '#fb6565');
		$(selectedLabel).css("font-weight", "bold");
	}else{
		var selectedDocType = "#"+docType;
		$(selectedDocType).css('color', '#fb6565');
		$(selectedDocType).css("font-weight", "bold");
	}
});
$(".but_big").click(function(){
	if($(this).val() == 'Register'){
		<c:choose>
			<c:when test="${docType eq 'label'}">
				document.getElementById("labelId").value = labelId;
				document.getElementById("labelNm").value = labelNm;
				document.listForm.action = "<c:url value='/approvalRegisterInternal.do'/>";
				document.listForm.submit();
			</c:when>
			<c:otherwise>
				document.location.href = "<c:url value='/approvalRegisterIncoming.do'/>";
			</c:otherwise>
		</c:choose>
	}else{
		document.getElementById("formSelect").style.display = "block";
		var buttonType = "draft";
		$(".layerPopup_rapper").css(
				{
					left:"50%",
					top:"40%",	
					marginLeft:-parseInt($(".layerPopup_rapper").css("width"))/2+"px",
					marginTop:-parseInt($(".layerPopup_rapper").css("height"))/2+"px"
				}
			);
		$("#shadow,.shadow").css(
			{
	            width:$(window).width(),
				height:$(document).height()+48,
	            opacity: 0.5
	        }
		);
		$("#shadow,.shadow").show();
		//ajax
		 var url = "<c:url value='/formSelectLabel.do'/>";
		$.ajax({                        
	        type: "POST",
	        url: url,
	        buttonType: buttonType,
	        dataType: 'html',
	        data:{url:url, buttonType:buttonType},
	        success: function(data) {
	        	$(".fromLabel").empty();
	        	$(".fromLabel").append(data); 
	       }
	    });	 
	}
})
//]]>
</script>
</html>