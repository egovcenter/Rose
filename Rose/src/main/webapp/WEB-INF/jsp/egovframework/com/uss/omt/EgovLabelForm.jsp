<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script src="<c:url value='/js/egovframework/com/uss/omt/orgCommon.js'/>"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jquery-1.11.3.js'/>"></script>
<script src="<c:url value='/js/egovframework/com/uss/ion/jquery-ui-1.11.4.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/jquery.treeview.css'/>" />
<script src="<c:url value='/js/egovframework/com/cmm/jquery.treeview.js'/>" type="text/javascript"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jquery.cookie.js'/>" type="text/javascript"></script>
<script src="<c:url value='/js/egovframework/com/uss/ion/jquery.form.js'/>"></script>
<script src="<c:url value='/js/egovframework/com/uss/ion/admin.js'/>"></script>
<c:if test="${empty param.labelId and not empty labelTree}">
	<c:forEach var="node" items="${labelTree}" varStatus="status">
		<c:if test="${status.index eq 0}"><c:set var="labelId" value="${node.labelId}"/><c:set var="labelNm" value="${node.labelNm}"/></c:if>
	</c:forEach>
</c:if>
<c:if test="${not empty param.labelId}">
	<c:set var="labelId" value="${param.labelId}"/><c:set var="labelNm" value="${param.labelNm}"/>
</c:if>
<script language="javascript">
$(document).ready(function(){
	$(window).resize(function(){
		var winH = $(document).height();
		var winW = $(window).width();
		$("#shadow_labeltreelayer").css({"height":winH,"width":winW});
	});
	
}); 
var APPROVAL_CONTEXT = "${pageContext.servletContext.contextPath}";
var userId = "<c:out value="${user.uniqId}"/>";
var deptId = "<c:out value="${dept.deptId}"/>";
var companyID = "<c:out value="${topDept.deptId}"/>";
var contentID = "content_box";
var popupLayerID = "popup_layer";
var selectedLabelId;
var selectedLabelNm;
jQuery(function() {
<c:if test="${not empty labelId}">viewLabel("<c:out value="${labelId}"/>");</c:if>
	$(".lnb_tree").treeview({
		animated: "medium",
		control:"#sidetreecontrol",
		persist: "cookie"
	});
});
function viewLabelTreeLayer(labelId){
	var winH = $(document).height();
	var winW = $(window).width();
	
	$("#shadow_labeltreelayer").css({"height":winH,"width":winW});

	$("#"+popupLayerID).load(APPROVAL_CONTEXT+"/viewLabelTreeLayer.do",
			{userID: userID, deptId: deptId, labelId: labelId},
			function(){
				$("#"+popupLayerID).show();
				if(typeof(viewLabelTreeLayer_onLoad) != 'undefined'){
					viewLabelTreeLayer_onLoad();
				}
			}
	);

}
$(function() {
	$(".folder, .file").click(function() {
		$(selectedLabelId).css("font-weight", "");
		$(selectedLabelId).css('color', "");

		selectedLabelId = $(this).attr("id");
		selectedLabelNm = $(this).attr("nm");
		document.getElementById("labelId").value = selectedLabelId;
		document.getElementById("labelNm").value = selectedLabelNm;
		
		selectedLabelId = "#node_"+selectedLabelId;
		$(selectedLabelId).css("font-weight", "bold");
		$(selectedLabelId).css('color', '#fb6565');
	});
});
function viewAddLabel(){
	 document.frm.action = "<c:url value='/viewAddLabel.do'/>";
	 document.frm.submit();
}
function viewModifyLabel(){
	var selectedlabelId = document.getElementById("labelId").value;
	if(selectedlabelId == ''){
		alert('<spring:message code="appvl.lable.msg.005"/>');
		return false;
	}
	document.frm.action = "<c:url value='/viewModifyLabel.do'/>?labelId="+selectedlabelId;
	document.frm.submit();
}
function validateDeleteLabel(selectedlabelId){
	if(selectedlabelId == ''){
		alert('<spring:message code="appvl.lable.msg.006"/>');
		return false;
	}
	if(!confirm('<spring:message code="common.msg.confim.delete"/>')){
		return false;
	}
	return true;
}
function deleteLabel(){
	var selectedlabelId = document.getElementById("labelId").value;
	if(!validateDeleteLabel(selectedlabelId)){
		return;
	}
	$.ajax({                        
		type: "POST",
		url: APPROVAL_CONTEXT +"/deleteLabel.do",
		dataType: 'json',
		cache: false,
		data:{"userId": userId, "labelId": selectedlabelId},
		success: function(data) {
			parent.document.location.href = APPROVAL_CONTEXT + "/mngLabel.do?userId="+userId;
		},
		error : function(jqXHR, textStatus, errorThrown){
			//alert("applySignTable error. textStatus["+textStatus+"], errorThrown["+errorThrown+"]");
		}
    });
}
</script>
</head>
<body>
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
			<jsp:param value="LABEL" name="selectedMenu"/>
		</jsp:include> 
    </div>
    <!-- Content start -->
    <div class="Content"> 
      <!-- Content box start -->
      <div class="content_box" id="org_content_box">
        <!-- 공통 타이틀 버튼 라인 -->
        <div class="title_box">
          <div class="title_line">
            <h1><spring:message code="appvl.label.label.management"/></h1>
          </div>
          <div class="clear"></div>
        </div>
   		<!-- Label tree start -->
       	<div class="tree_area">
			<div class="title_box" style="visibility: hidden;">
				<div class="ui_btn_rapper float_right">
					<a href="javascript:viewAddLabel()" class="btn_color3"><spring:message code="common.button.new"/></a>
					<a href="javascript:viewModifyLabel()" class="btn_color3"><spring:message code="common.button.modify"/></a>
					<a href="javascript:deleteLabel()" class="btn_color2"><spring:message code="common.button.delete"/></a>
				</div>
				<div class="clear"></div>
			</div>
    		<div class="tree">
		      	<div class="lnb_tree" style="background:#FFF;border: 1px">
		      		<!-- 클래스lnb_tree 안에 트리구조 삽입 (width181px) -->
 					<jsp:include page="/WEB-INF/jsp/egovframework/com/uss/omt/cmm/EgovLabelTree.jsp" flush="false">
						<jsp:param value="single" name="type"/>
						<jsp:param value="N" name="fusion"/>
					</jsp:include>
		     	 </div>
	        </div>
        </div>
   		<!-- Label tree end -->
  		<!-- Label Form start -->
  		<div class="con_area">
			<div class="title_box">
				<div class="ui_btn_rapper float_right">
					<a href="javascript:viewAddLabel()" class="btn_color3"><spring:message code="common.button.new"/></a>
					<a href="javascript:viewModifyLabel()" class="btn_color3"><spring:message code="common.button.modify"/></a>
					<a href="javascript:deleteLabel()" class="btn_color2"><spring:message code="common.button.delete"/></a>
				</div>
				<div class="clear"></div>
			</div>
       		<div class="rapper_table mb40">
				<p class="tab_title float_left"><spring:message code="appvl.label.label.view"/></p>
				<div class="table_rapper">
					<table summary="" class="board_width_borderNone">
						<caption class="blind"></caption>
						<colgroup>
							<col width="60px"/>
							<col width="*"/>
							<col width="82px"/>
							<col width="25%"/>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row"><label for="label_1"><spring:message code="appvl.label.label.name"/></label></th>
								<td colspan="3">
									<div class="ui_input_text cursorDefault">
										<input type="text" value="${labelNm}" id="labelNm" readonly />
										<input type="hidden" value="${labelId}" id="labelId"/>
									</div>
								</td>
							</tr>						
						</tbody>
					</table>
				</div>	
			</div>
		</div>
   		<!-- Label Form end -->
  		</div>
  		<!-- Content box end -->
      </div>
      <!-- Content end -->
    </div>
    <!-- Container end -->
    <div class="clear"></div>
 </div>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCopyright.jsp" %>
<!-- form start -->
<form action="/org.do" name="frm" id="frm" method="post">
	<input type="hidden" id="deptId" name="deptId" value="${user.orgnztId}">
</form>

</body>
</html>