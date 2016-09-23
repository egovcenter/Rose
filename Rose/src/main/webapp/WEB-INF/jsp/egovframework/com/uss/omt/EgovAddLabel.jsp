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
<script language="javascript">
var APPROVAL_CONTEXT = "${pageContext.servletContext.contextPath}";
var userId = "<c:out value="${user.uniqId}"/>";
var deptId = "<c:out value="${dept.deptId}"/>";
var companyID = "<c:out value="${topDept.deptId}"/>";
var contentID = "content_box";
var popupLayerID = "popup_layer";
var appvl_lable_msg_003 = "<spring:message code="appvl.lable.msg.003"/>";
var appvl_lable_msg_004 = "<spring:message code="appvl.lable.msg.004"/>";
function validateAddLabel(){
	if($('input[name=labelNm]', '#label_form').val() == ''){
		alert('<spring:message code="appvl.lable.msg.001"/>');
		$('input[name=labelNm]', '#label_form').focus();
		return false;
	}
	if($('input[name=targetLabelNm]', '#label_form').val() == '' || $('input[name=targetLabelID]', '#label_form').val() == ''){
		alert('<spring:message code="appvl.lable.msg.002"/>');
		$('input[name=targetLabelNm]', '#label_form').focus();
		return false;
	}
	if(!confirm('<spring:message code="common.msg.confim.add"/>')){
		return false;
	}

	return true;
}
function addLabel(){
	if(!validateAddLabel()){
		return;
	}
	$("#label_form").submit(function(e){
		var postData = $(this).serializeArray();
		var formURL = $(this).attr("action");
		$.ajax({
			url : formURL,
			type: "POST",
			dataType: 'json',
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
				if(data.result && data.result == 'success'){
					if(data.label && data.label.labelID){
						parent.document.location.href = APPROVAL_CONTEXT + "/mngLabel.do?userId="+userId+"&labelID="+data.label.labelID;
					}else{
						parent.document.location.href = APPROVAL_CONTEXT + "/mngLabel.do?userId="+userId;
					}
				}else if(data.error_msg){
					alert(data.error_msg);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) 
			{
			}
		});
		try{
		e.preventDefault(); //STOP default action
		e.unbind(); //unbind. to stop multiple form submit.
		}catch(e){
			if(window.console)window.console.log(e);
		}
	});
	$("#label_form").submit(); //Submit  the FORM
}
function viewLabelTreeLayer(labelID){
	$("#"+popupLayerID).load(APPROVAL_CONTEXT+"/viewLabelTreeLayer.do",
			{userId: userId, deptId: deptId, labelID: labelID},
			function(){
				$("#"+popupLayerID).show();
				if(typeof(viewLabelTreeLayer_onLoad) != 'undefined'){
					viewLabelTreeLayer_onLoad();
				}
			}
	);
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
		<p class="tab_title float_left"><spring:message code="appvl.label.label.new"/><c:if test="${isRootLabel}">(ROOT)</c:if></p>
		<div class="ui_btn_rapper float_right">
			<a href="javascript:addLabel()" class="btn_color3"><spring:message code="common.button.apply"/></a>
			<a href="javascript:historyBack()" class="btn_color2"><spring:message code="common.button.cancel"/></a>
		</div>
		<div class="table_rapper">
			<c:set var="isRoot" value="false"/>
			<c:if test="${not empty rootLabel}"><c:set var="isRoot" value="true"/></c:if>
			<form id="label_form" name="label_form" action="<c:out value="${pageContext.servletContext.contextPath}"/>/addLabel.do" method="post">
			<input type="hidden" name="deptId" value="<c:out value="${dept.deptId}"/>">
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
						<th scope="row"><label for="labelNm"><spring:message code="appvl.label.label.name"/></label></th>
						<td colspan="3"><div class="ui_input_text"><input type="text" value="" name="labelNm" id="labelNm"/></div></td>
					</tr>						
					<tr>
						<th scope="row"><label for="label_2"><spring:message code="common.location.label.location"/></label></th>
						<td <c:if test="${isRoot}">colspan="2"</c:if>>
							<div class="ui_input_text">
							<c:if test="${isRoot}">
								<input type="text" value="<c:out value="${rootLabel.labelNm}"/>" name="targetLabelNm" id="targetLabelNm" readonly/>
								<input type="hidden" value="<c:out value="${rootLabel.labelId}"/>" name="targetLabelID" id="targetLabelID" readonly/>
							</c:if>
							<c:if test="${not isRoot}">
								<input type="text" name="targetLabelNm" id="targetLabelNm"/>
								<input type="hidden" name="targetLabelID" id="targetLabelID"/>
							</c:if>
							</div>
						</td>
						<c:if test="${!isRoot}">
						<td><div class="ui_btn_rapper align_right"><a href="javascript:viewLabelTreeLayer()" class="btn_color3"><spring:message code="common.button.select"/></a></div></td>
						</c:if>
						<td>
							<div class="ui_input_text"><input type="text" value="lower" name="targetPosition" id="lower"/></div>
						</td>
					</tr>
				</tbody>
			</table>
			</form>
		</div>	
  		</div>
      </div>
      <!-- Content end -->
    </div>
    <!-- Container end -->
    <div id="popup_layer" style="display: none;" ></div>
 </div>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCopyright.jsp" %>
</body>
</html>