<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="en">
<!doctype html>
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/egovframework/com/uss/ion/jquery.datetimepicker.css'/>">
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery-1.7.1.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery-ui-1.11.4.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/approval.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/popup.js"'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery.datetimepicker.js"'/>"></script>
<script language="javascript">
var APPROVAL_CONTEXT = "${pageContext.servletContext.contextPath}";
var userId = "${user.uniqId}";
var docId = "${doc.docID}";
var formId = "${doc.formId}";
var from = "<c:out value="${param.from}"/>";
var appvl_outgoing_nosendername = "<spring:message code="appvl.outgoing.nosendername"/>";
var appvl_outgoing_nosenderdept = "<spring:message code="appvl.outgoing.nosenderdept"/>";
var appvl_outgoing_nodate = "<spring:message code="appvl.outgoing.nodate"/>";
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
		<jsp:param value="draft" name="CommonButton"/>
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
						<h1><spring:message code="appvl.sendingoutgoingdoc.title"/></h1>						
					</div>					
					<div class="ui_btn_rapper float_right mtb10">
						<a href="javascript:apprSend('formSend')" class="btn_color1"><spring:message code="common.button.apply"/></a>
						<a href="javascript:history.back()" class="btn_color2"><spring:message code="common.button.cancel"/></a>
					</div>
					<div class="clear"></div>
				</div>		
				<div class="table_rapper">
					<form name="formSend" id="formSend" action="<c:out value="${pageContext.servletContext.contextPath}"/>/send.do" method="post">
					<input type="hidden" name="docId" value="${doc.docID}"/>
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
								<td colspan="2"><div class="ui_input_text"  style="border-bottom:1px solid #e1e1e1;"><c:out value="${doc.docTitle}"/></div></td>
							</tr>
							<tr>
								<th scope="row"><label for="label_2"><spring:message code="appvl.documet.label.label"/></label></th>
								<td colspan="2"><div class="ui_input_text"  style="border-bottom:1px solid #e1e1e1;"><c:out value="${label.labelNm}"/></div></td>
								<!-- <td>
									<div class="ui_btn_rapper">
										<a href="#" class="btn_color3">Select</a>
									</div>
								</td> -->
							</tr>
							<tr>
								<th scope="row"><spring:message code="appvl.documet.label.security"/></th>
								<td colspan="2"><div class="ui_input_text" style="border-bottom:1px solid #e1e1e1;">
									<c:if test="${doc.docSlvl eq '99'}"><label  for="label_3-1"><spring:message code="appvl.documet.label.security.open"/></label></c:if>
									<c:if test="${doc.docSlvl eq '1'}"><label  for="label_3-2"><spring:message code="appvl.documet.label.security.secret"/></label></c:if>
								</div></td>								
							</tr>
							<tr>
								<th scope="row"><label for="label_6"><spring:message code="appvl.sendingoutgoingdoc.label.sendername"/></label></th>
								<td>
									<div class="ui_input_text">
									<input type="hidden" value="" name="selectedUserID" id="selectedUserID"/>
									<input type="text" value="" name="selectedUserNm" id="selectedUserNm" readonly/>
									</div>
								</td>
								<td>
									<div class="ui_btn_rapper">
										<a href="javascript:selectUser('uni')" class="btn_color3"><spring:message code="common.button.select"/></a>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="label_4"><spring:message code="appvl.sendingoutgoingdoc.label.senderdept"/></label></th>
								<td colspan="2"><div class="ui_input_text"><input type="text" value="" name="selectedDeptNm" id="selectedDeptNm" readonly/></div></td>
							</tr>
							<!--
							<tr>
								<th scope="row"><label for="label_5">Sender Position</label></th>
								<td colspan="2"><div class="ui_input_text"><input type="text" value="" name="recpSendPosition" id="recpSendPosition"/></div></td>
							</tr>
							-->
							<tr>
								<th scope="row" style="padding-top: 12px;"><spring:message code="appvl.sendingoutgoingdoc.label.recipient"/></th>
								<td colspan="2">
									<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height mb20" id="recipient_list">
										<caption class="blind"></caption>
										<colgroup>
											<col width="20%"/>
											<col width="20%"/>
											<col width="20%"/>
											<col width=""/>
										</colgroup>
										<thead>
											<tr>
												<th scope="col" class=""><span><spring:message code="appvl.sendingoutgoingdoc.table.recipient"/></span></th>
												<th scope="col" class=""><span><spring:message code="appvl.sendingoutgoingdoc.table.method"/></span></th>
												<th scope="col" class=""><span><spring:message code="appvl.sendingoutgoingdoc.table.sender"/></span></th>
												<th scope="col" class=""><span><spring:message code="appvl.sendingoutgoingdoc.table.date"/></span></th>
											</tr>
										</thead>					
										<tbody>
										<c:set var="recpIDList"/>
										<c:forEach var="recipient" items="${recipientList}" varStatus="loop">
											<c:if test="${empty recipient.recpSendDt}">
											<c:set var="recpIDList" value="${recpIDList},${recipient.recpId}"/>
											</c:if>
											<tr>
												<td>
													<input type="hidden" value="<c:out value="${recipient.recpId}"/>" name="recpID" id="recpID"/>
													<c:out value="${recipient.recpDeptNm}"/>
												</td>
												<td>
												<c:choose>
													<c:when test="${recipient.recpSendType eq 'ST01'}"><div class="recipient_recpsendtypetext"><spring:message code="appvl.recip.ST01"/></div></c:when>
													<c:when test="${recipient.recpSendType eq 'ST02'}"><div class="recipient_recpsendtypetext"><spring:message code="appvl.recip.ST02"/></div></c:when>
													<c:when test="${recipient.recpSendType eq 'ST03'}"><div class="recipient_recpsendtypetext"><spring:message code="appvl.recip.ST03"/></div></c:when>
													<c:when test="${recipient.recpSendType eq 'ST04'}"><div class="recipient_recpsendtypetext"><spring:message code="appvl.recip.ST04"/></div></c:when>
													<c:when test="${recipient.recpSendType eq 'ST05'}"><div class="recipient_recpsendtypetext"><spring:message code="appvl.recip.ST05"/></div></c:when>
													<c:otherwise><div class="recipient_recpsendtypetext"><spring:message code="appvl.recip.ST01"/></div></c:otherwise>
												</c:choose>
												</td>
												<td>
													<c:out value="${recipient.recpSendUserNm}"/>
												</td>
												<td>
													<c:if test="${empty recipient.recpSendDt}">
													<div class="display_table">
														<div class="display_tableCell_top">
															<div class="ui_input_text">
																<input type="text" value="" name="recpSendDt_<c:out value="${recipient.recpId}"/>" id="recpSendDt_<c:out value="${recipient.recpId}"/>" class="datetimepicker"/>
															</div>
														</div>
														<div class="display_tableCell_top" style="padding-left: 10px;">
															<a class="icon_calendar" style="cursor: pointer"><img src="<c:url value='/images/egovframework/com/uss/cmm/icon_calendar.png'/>" alt="icon_calendar"></a>
														</div>
													</div>
													</c:if>
													<c:if test="${not empty recipient.recpSendDt}">
													<div class="display_table">
														<div class="display_tableCell_top">
														<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${recipient.recpSendDt}" />
														</div>
													</div>
													</c:if>
												</td>
											</tr>
										</c:forEach>
										<input type="hidden" name="datetimeformat" value="yyyy/MM/dd HH:mm"/>
										<input type="hidden" name="recpIDList" value="<c:out value="${recpIDList}"/>"/>
										</tbody>
									</table>
								</td>
							</tr>			
						</tbody>
					</table>
					</form>
				</div>			
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

<script language="javascript" type="text/javascript"> 
//<![CDATA[
$('.board_type_height th').click(function() {
	if ($(this).hasClass('sort_down')){
		$(this).removeClass('sort_down');
		$(this).addClass('sort_up');
	} else {
		$(this).removeClass('sort_up');
		$(this).addClass('sort_down');
  	}
});
$( document ).ready(function() {
	$('.datetimepicker').datetimepicker();
	$('.icon_calendar').click(function(){
		$('.datetimepicker', $(this).parent().parent()).datetimepicker('show');
	});
});
//]]>
</script>
</html>