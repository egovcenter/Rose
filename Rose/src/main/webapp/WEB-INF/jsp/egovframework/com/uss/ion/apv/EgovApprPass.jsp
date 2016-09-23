<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="en">
<!doctype html>
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/egovframework/com/uss/ion/jquery.datetimepicker.css'/>">
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery-1.7.1.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery-ui-1.11.4.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/json2.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/approval.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery.hs.localization.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery.form.js"'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/popup.js"'/>"></script>
<script language="javascript">
var APPROVAL_CONTEXT = "${pageContext.servletContext.contextPath}";
var userId = "${user.uniqId}";
var docId = "${doc.docID}";
var formId = "${doc.formId}";
var from = "<c:out value="${param.from}"/>";//what???
var common_button_delete = "<spring:message code="common.button.delete"/>";
function removeRecpient(thisObj){
	$(thisObj).parent().parent().parent().remove();
}
</script>
</head>

<body>
<div class="wrap"> 
	<!--  Top Menu Start --> 
	<jsp:include page="/WEB-INF/jsp/egovframework/com/cmm/EgovTopMenu.jsp" flush="false">
		<jsp:param value="APPROVAL" name="selectedMenu"/>
	</jsp:include>
	<!--  Top Menu End --> 
	<div class="clear"></div>
	<!-- Container Start -->
	<div class="Container"> 
	<!-- Lnb -->
	<jsp:include page="/WEB-INF/jsp/egovframework/com/uss/ion/cmm/EgovLeftMenu.jsp" flush="false">
		<jsp:param value="register" name="CommonButton"/>
		<jsp:param value="approval" name="CommonTitle"/>
	</jsp:include>
		<!-- Content Start -->
		<div class="Content"> 
			<!-- Content box Start -->
			<div class="content_box"> 
				<div class="h36"></div>
				<!-- Menu Title-->
				<div class="title_box">
					<div class="title_line">
						<h1><spring:message code="appvl.passingdoc.title"/></h1>						
					</div>					
					<div class="but_line">
						<input type="button" value='<spring:message code="common.button.apply"/>' class="but_navy mr05" onClick="apprPass('formPass')"/>
						<input type="button" value='<spring:message code="common.button.cancel"/>' class="but_grayL" onClick="javascript:history.back()"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="table_rapper">
					<form name="formPass" id="formPass" action="<c:out value="${pageContext.servletContext.contextPath}"/>/pass.do" method="post">
						<input type="hidden" name="orgdocId" value="${orgdoc.docID}"/>
						<input type="hidden" name="passdeptId" value="${user.orgnztId}"/>
					<table summary="" class="board_width_borderNone">
						<caption class="blind"></caption>
						<colgroup>
							<col width="130px"/>
							<col width="*"/>
							<col width="85px"/>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row"><label for="label_1"><spring:message code="appvl.documet.label.title"/></label></th>
								<td colspan="2"><div class="ui_input_text" style="border-bottom:1px solid #e1e1e1;"><c:out value="${orgdoc.docTitle}"/></div></td>
							</tr>
							<tr>
								<th scope="row"><label for="label_6"><spring:message code="appvl.passingdoc.label.sendername"/></label></th>
								<td colspan="2">
									<div class="ui_input_text" style="border-bottom:1px solid #e1e1e1;">
									<c:out value="${user.emplyrNm}"/>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row" style="padding-top: 12px;"><spring:message code="appvl.passingdoc.label.recipient"/></a></th>
								<td colspan="2">
									<div class="float_right ui_btn_rapper">
										<a href="javascript:openOrgPopup('multi')" class="btn_color3"><spring:message code="appvl.document.button.assign"/></a>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row"></th>
								<td colspan="2">
									<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height mb20" id="recipient_list">
										<caption class="blind"></caption>
										<thead>
											<tr>
												<th scope="col"><spring:message code="appvl.passingdoc.label.recipient"/></th>
											</tr>
										</thead>					
										<tbody>
											<tr>
												<td><spring:message code="appvl.passingdoc.label.norecipient"/></td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>			
						</tbody>
					</table>
					</form>
				</div>
				<!-- table_rapper End  -->
			</div>
			<!-- Content box End -->
		</div>
		<!-- Content End --> 
	</div>
	<!-- Container End -->
	<div id="div_popup" style="display: none"></div>
</div>
<!-- wrap End -->
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCopyright.jsp" %>
</body>
</html>