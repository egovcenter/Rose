<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>

<body>
<script>
function approvalSend(docID){
	document.getElementById("docId").value = docID;
	document.pageParam.action = "<c:url value='/approvalSend.do'/>";
	document.pageParam.submit();
}
</script>
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
		<jsp:param value="processing" name="CommonTitle"/>
	</jsp:include>
		<!-- Content Start -->
		<div class="Content"> 
			<!-- Content box Start -->
			<div class="content_box"> 
				 <!-- Top search -->
				 <div class="top_search">
					<!--<input name="textfield" type="text" id="textfield" title="search" class="input260" onClick="this.style.backgroundImage='none'">
					<input type="button" value="Search" class="but_navy" onClick="location.href='http://www.naver.com'";/>-->
				</div>
				<div class="clear"></div>
				<!-- Menu Title-->
				<div class="title_box">
					<div class="title_line">
						<h1><spring:message code="appvl.sidemenu.tree.processing.outgoing"/></h1>						
					</div>					
				</div>
				<!-- paging -->
				<jsp:include page="/WEB-INF/jsp/egovframework/com/cmm/EgovPagination.jsp" flush="false" />
				<!-- List Start -->						
					<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height">
						<caption class="blind"></caption>
						<colgroup>
							<col width="90px"/>
							<col width="20%"/>
							<col width="*"/>
							<col width="15%"/>
						</colgroup>
						<thead>
							<tr>
								<th scope="col" onclick="javascript:fnOrderList(this, 1);" class='<c:if test="${page.orderColumn eq '1' && page.orderMethod eq 'asc' }">sort_up</c:if><c:if test="${page.orderColumn eq '1' && page.orderMethod eq 'desc' }">sort_down</c:if>'><span><spring:message code="appvl.processing.outgoing.table.draftdate"/></span></th>			
								<th scope="col" onclick="javascript:fnOrderList(this, 2);" class='<c:if test="${page.orderColumn eq '2' && page.orderMethod eq 'asc' }">sort_up</c:if><c:if test="${page.orderColumn eq '2' && page.orderMethod eq 'desc' }">sort_down</c:if>'><span><spring:message code="appvl.processing.outgoing.table.docnum"/></span></th>
								<th scope="col" onclick="javascript:fnOrderList(this, 3);" class='<c:if test="${page.orderColumn eq '3' && page.orderMethod eq 'asc' }">sort_up</c:if><c:if test="${page.orderColumn eq '3' && page.orderMethod eq 'desc' }">sort_down</c:if>'><span><spring:message code="appvl.processing.outgoing.table.title"/></span></th>
								<th scope="col"><span><spring:message code="appvl.processing.outgoing.table.recipient"/></span></th>		
							</tr>
						</thead>					
						<tbody>
							<c:if test="${empty outgoingList}">
								<tr><td colspan="4" class="table_nodata"><span><spring:message code="common.msg.table.nodata"/></span></td></tr>
							</c:if>						
							<c:forEach var="outgoing" items="${outgoingList}" >
								<tr>
									<td class=""><fmt:formatDate pattern="yyyy-MM-dd" value="${outgoing.draftDateTime }" /></td>
									<td>${outgoing.docCd } </td>
									<td><a href="javascript:approvalSend('<c:out value="${outgoing.docID}"/>')">${outgoing.docTitle }</a></td>
									<td title="${outgoing.recipientDeptNames}">${outgoing.recipientDeptNames}</td>
								</tr>
							 </c:forEach>
						</tbody>
					</table>
				</div>
				
			</div>
			<!-- content_box End -->
		</div>
		<!-- Content End -->
	</div>
	<!-- Container End --> 
	<div class="clear"></div>
	<form action="" id="pageParam" name="pageParam" method="post">
		<input type="hidden" id="docId" name="docId" value="">
	</form>
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
//]]>
</script>
</html>