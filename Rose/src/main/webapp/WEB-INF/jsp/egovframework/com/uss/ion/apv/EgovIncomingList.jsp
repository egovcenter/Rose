<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
var userId = "${user.uniqId}";
function approvalReceive(docID){
	document.getElementById("orgdocId").value = docID;
	document.pageParam.action = "<c:url value='/approvalReceive.do'/>";
	document.pageParam.submit();
}
function approvalReceive4Paper(docID, userId){
	$("input[name=orgdocId]", "#listForm").val(docID);
	$("input[name=isincoming]", "#listForm").val("1");
	$("#formSelect").show();
	 var url = "<c:url value='/formSelectLabel.do'/>";
	 var buttonType = "register";
	 $("#shadow,.shadow").css(
			{
	            width:$(window).width(),
				height:$(document).height()+48,
	            opacity: 0.5
	        }
		);
	 $("#shadow,.shadow").show();
	//ajax
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
function approvalView(docID){
	document.getElementById("docId").value = docID;
	document.pageParam.action = "<c:url value='/approvalView.do'/>";
	document.pageParam.submit();
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
		<jsp:param value="processing" name="CommonTitle"/>
	</jsp:include>
		<!-- Content Start-->
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
						<h1><spring:message code="appvl.sidemenu.tree.processing.incoming"/></h1>				
					</div>					
				</div>
				<!-- paging -->
				<jsp:include page="/WEB-INF/jsp/egovframework/com/cmm/EgovPagination.jsp" flush="false" />
				<!-- List Start -->						
					<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height">
						<caption class="blind"></caption>
						<colgroup>
							<col width="110px"/>
							<col width="20%"/>
							<col width="*"/>
							<col width="10%"/>
							<col width="10%"/>
							<col width="10%"/>
						</colgroup>
						<thead>
							<tr>
								<th scope="col" onclick="javascript:fnOrderList(this, 1);" class='<c:if test="${page.orderColumn eq '1' && page.orderMethod eq 'asc' }">sort_up</c:if><c:if test="${page.orderColumn eq '1' && page.orderMethod eq 'desc' }">sort_down</c:if>'><span><spring:message code="appvl.processing.incoming.table.registerdate"/></span></th>
								<th scope="col" onclick="javascript:fnOrderList(this, 2);" class='<c:if test="${page.orderColumn eq '2' && page.orderMethod eq 'asc' }">sort_up</c:if><c:if test="${page.orderColumn eq '2' && page.orderMethod eq 'desc' }">sort_down</c:if>'><span><spring:message code="appvl.processing.incoming.table.docnum"/></span></th>
								<th scope="col" onclick="javascript:fnOrderList(this, 3);" class='<c:if test="${page.orderColumn eq '3' && page.orderMethod eq 'asc' }">sort_up</c:if><c:if test="${page.orderColumn eq '3' && page.orderMethod eq 'desc' }">sort_down</c:if>'><span><spring:message code="appvl.processing.incoming.table.title"/></span></th>
								<th scope="col" onclick="javascript:fnOrderList(this, 4);" class='<c:if test="${page.orderColumn eq '4' && page.orderMethod eq 'asc' }">sort_up</c:if><c:if test="${page.orderColumn eq '4' && page.orderMethod eq 'desc' }">sort_down</c:if>'><span><spring:message code="appvl.processing.incoming.table.sender"/></span></th>		
								<th scope="col" onclick="javascript:fnOrderList(this, 5);" class='<c:if test="${page.orderColumn eq '5' && page.orderMethod eq 'asc' }">sort_up</c:if><c:if test="${page.orderColumn eq '5' && page.orderMethod eq 'desc' }">sort_down</c:if>'><span><spring:message code="appvl.processing.incoming.table.receiver"/></span></th>
								<th scope="col" onclick="javascript:fnOrderList(this, 6);" class='<c:if test="${page.orderColumn eq '6' && page.orderMethod eq 'asc' }">sort_up</c:if><c:if test="${page.orderColumn eq '6' && page.orderMethod eq 'desc' }">sort_down</c:if>'><span><spring:message code="appvl.processing.incoming.table.receivedate"/></span></th>
							</tr>
						</thead>					
						<tbody>
							<c:if test="${empty incomingList}">
								<tr><td colspan="6" class="table_nodata"><span><spring:message code="common.msg.table.nodata"/></span></td></tr>
							</c:if>						
							<c:forEach var="incoming" items="${incomingList}">
								<tr>
									<td class=""><fmt:formatDate pattern="yyyy-MM-dd" value="${incoming.arrivalDateTime }" /></td>
									<td>${incoming.docCd } </td>
									<td>
										<c:choose>
											<c:when test="${empty incoming.recpRecpDt}">		
												<c:if test="${incoming.docPpF eq '1'}"><a href="javascript:approvalReceive4Paper('<c:out value="${incoming.docID}"/>', '<c:out value="${user.uniqId}"/>')">${incoming.docTitle }</a></c:if>
												<c:if test="${incoming.docPpF eq '2'}"><a href="javascript:approvalReceive('<c:out value="${incoming.docID}"/>')">${incoming.docTitle }</a></c:if>
											</c:when>
											<c:otherwise>
												<c:if test="${incoming.docPpF eq '1'}"><a href="javascript:approvalView('<c:out value="${incoming.docID}"/>')">${incoming.docTitle }</a></c:if>
												<c:if test="${incoming.docPpF eq '2'}"><a href="javascript:approvalView('<c:out value="${incoming.docID}"/>')">${incoming.docTitle }</a></c:if>
											</c:otherwise>
										</c:choose>
									</td>
									<td>${incoming.senderUserName }</td>								
									<td>${incoming.recpRecpUserNm }</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${incoming.recpRecpDt }" /></td>
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
		<input type="hidden" id="orgdocId" name="orgdocId" value="">
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