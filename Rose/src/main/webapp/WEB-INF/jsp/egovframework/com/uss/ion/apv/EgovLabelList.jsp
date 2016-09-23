<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/approval.js'/>"></script>
<script>
var userId = "${loginVO.uniqId}";
var labelNm = "${labelNm}";
var labelId = "${labelID}";
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
		<jsp:param value="cabinet" name="CommonTitle"/>
	</jsp:include>
		<!-- Content Start -->
		<div class="Content"> 
			<!-- Content box Start -->
			<div class="content_box"> 
				 <!-- Top search -->
				 <div class="top_search">
					<!--<input name="textfield" type="text" id="textfield" title="search" class="input260" onClick="this.style.backgroundImage='none'">
					<input type="button" value="Search" class="but_navy" onClick="location.href='http://www.naver.com'";/> -->
				</div>
				<div class="clear"></div>
				<!-- Menu Title-->
				<div class="title_box">
					<div class="title_line">
						<h1>${labelNm}</h1>						
					</div>					
				</div>
				
				<!-- paging -->
				<jsp:include page="/WEB-INF/jsp/egovframework/com/cmm/EgovPagination.jsp" flush="false" />
				<!-- List Start -->		
					<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height">
						<caption class="blind"></caption>
						<colgroup>
							<col width="10%"/>
							<col width="110px"/>
							<col width="110px"/>
							<col width="*"/>
							<col width="15%"/>
							<col width="20%"/>
							<col width="40px"/>
							<col width="40px"/>
						</colgroup>
						<thead>
							<tr>
								<th scope="col" onclick="javascript:fnOrderList(this, 1);" class='<c:if test="${page.orderColumn eq '1' && page.orderMethod eq 'asc' }">sort_up</c:if><c:if test="${page.orderColumn eq '1' && page.orderMethod eq 'desc' }">sort_down</c:if>'><span><spring:message code="appvl.cabinet.label.table.type"/></span></th>
								<th scope="col" onclick="javascript:fnOrderList(this, 2);" class='<c:if test="${page.orderColumn eq '2' && page.orderMethod eq 'asc' }">sort_up</c:if><c:if test="${page.orderColumn eq '2' && page.orderMethod eq 'desc' }">sort_down</c:if>'><span><spring:message code="appvl.cabinet.label.table.draftdate"/></span></th>
								<th scope="col" onclick="javascript:fnOrderList(this, 3);" class='<c:if test="${page.orderColumn eq '3' && page.orderMethod eq 'asc' }">sort_up</c:if><c:if test="${page.orderColumn eq '3' && page.orderMethod eq 'desc' }">sort_down</c:if>'><span><spring:message code="appvl.cabinet.label.table.finisheddate"/></span></th>
								<th scope="col" onclick="javascript:fnOrderList(this, 4);" class='<c:if test="${page.orderColumn eq '4' && page.orderMethod eq 'asc' }">sort_up</c:if><c:if test="${page.orderColumn eq '4' && page.orderMethod eq 'desc' }">sort_down</c:if>'><span><spring:message code="appvl.cabinet.label.table.title"/></span></th>
								<th scope="col" onclick="javascript:fnOrderList(this, 5);" class='<c:if test="${page.orderColumn eq '5' && page.orderMethod eq 'asc' }">sort_up</c:if><c:if test="${page.orderColumn eq '5' && page.orderMethod eq 'desc' }">sort_down</c:if>'><span><spring:message code="appvl.cabinet.label.table.draftdept"/></span></th>
								<th scope="col" onclick="javascript:fnOrderList(this, 6);" class='<c:if test="${page.orderColumn eq '6' && page.orderMethod eq 'asc' }">sort_up</c:if><c:if test="${page.orderColumn eq '6' && page.orderMethod eq 'desc' }">sort_down</c:if>'><span><spring:message code="appvl.cabinet.label.table.drafter"/></span></th>
								<th scope="col" class="align_center"><img src="<c:url value='/images/egovframework/com/uss/cmm/icon_history.png'/>" alt="icon_history"></th>	
								<th scope="col" class="align_center"><img src="<c:url value='/images/egovframework/com/uss/cmm/icon_file.png'/>" alt="icon_check"></th>
							</tr>
						</thead>					
						<tbody>
						<c:if test="${empty labelList}">
							<tr><td colspan="8" class="table_nodata"><span><spring:message code="common.msg.table.nodata"/></span></td></tr>
						</c:if>						
						<c:forEach var="label" items="${labelList }">
							<tr>
								<td><c:if test="${label.docType == 'DT01'}"><spring:message code="appvl.doctype.internal"/></c:if>
									<c:if test="${label.docType == 'DT02'}"><spring:message code="appvl.doctype.outgoing"/></c:if>
									<c:if test="${label.docType == 'DT03'}"><spring:message code="appvl.doctype.incoming"/></c:if></td>
								<td class=""><fmt:formatDate pattern="yyyy-MM-dd" value="${label.draftDateTime }" /></td>
								<td class=""><fmt:formatDate pattern="yyyy-MM-dd" value="${label.approvedDateTime }" /></td>
								<td><a href="javascript:approvalView('<c:out value="${label.docID}"/>')">${label.docTitle}</a></td>
								<td>${label.draftDeptName}</td>
								<td>${label.drafterName}</td>
								<td class="align_center">
									<a href="javascript:status('<c:out value="${label.docID}"/>', '<c:out value="${label.docTitle}"/>')" class="Status">
									<img src="<c:url value='/images/egovframework/com/uss/cmm/icon_history.png'/>" alt="icon_history"></a>
								</td>
								<td class="align_center">
									<c:if test="${label.docAttaF =='1'}">
										<a href="javascript:fileDown('<c:out value="${label.docID}"/>', '<c:out value="${label.docTitle}"/>')" class="fileDown">
											<input type="hidden" value="${label.docID}" id="ongoingDocId" name="ongoingDocId">
											<img src="<c:url value='/images/egovframework/com/uss/cmm/icon_file.png'/>" alt="icon_file">
										</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			
				<div class="toggleBlock" style="padding-top: 150px; margin-top:20px;"></div>
				<div class="clear"></div>
				<div class="statusSlide_rapper rapper_table mb40" id="statusSlide">
					<div class="popup_close_rapper">
						<table>
							<tr>
								<td><div id="statusDocTitle" style="color: #FFFFFF; padding-left: 5px;"></div></td>
								<td><a href="javascript:popupClose()" class="btn_popup_close"><img src="<c:url value='/images/egovframework/com/uss/cmm/btn_popup_close.png'/>" alt="btn_close"/></a></td>
							</tr>
						</table>
					</div>
					<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height">
						<caption class="blind"></caption>
						<colgroup>
							<col width="50px"/>
							<col width="50%"/>
							<col width="25%"/>
							<col width="25%"/>
							<col width="100px"/>
							<col width="120px"/>
						</colgroup>
						<thead>
							<tr>
								<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.order"/></span></th>
								<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.department"/></span></th>
								<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.position"/></span></th>
								<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.name"/></span></th>
								<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.status"/></span></th>
								<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.finished"/></span></th>
							</tr>
						</thead>					
						<tbody class="listSignerBody">
						</tbody>
					</table>
				</div>
				<div class="clear"></div>
				<div class="statusSlide_rapper rapper_table mb40" id="attachSlide">
					<div class="popup_close_rapper">
						<table>
							<tr>
								<td><div id="attachDocTitle" style="color: #FFFFFF; padding-left: 5px;"></div></td>
								<td><a href="javascript:popupClose()" class="btn_popup_close"><img src="<c:url value='/images/egovframework/com/uss/cmm/btn_popup_close.png'/>" alt="btn_close"/></a></td>
							</tr>
						</table>
					</div>
					<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height mb20" id="attach_list_table">
						<caption class="blind"></caption>
						<colgroup>
							<col width="*"/>
							<col width="100px"/>
						</colgroup>
						<thead>
							<tr>
								<th scope="col" class=""><span><spring:message code="appvl.documet.label.attachment.table.filename"/></span></th>
								<th scope="col" class=""><span><spring:message code="appvl.documet.label.attachment.table.filesize"/></span></th>
							</tr>
						</thead>					
						<tbody class="listAttachBody">
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
function status(docId, docTitle){
	var status = document.getElementById("statusSlide").style.display;
	var attach = document.getElementById("attachSlide").style.display;
	if(status == "block" || attach == "block"){
		popupClose();
	}
	$(".listSignerTr").remove();
	var url = "<c:url value='/listApprovalSigner.do'/>";
	$.ajax({                        
        type: "POST",
        url: url,
        dataType: 'html',
        data:{docId:docId},
        success: function(data) {
        $(".listSignerBody").append(data); 
       }
    });
	document.getElementById("statusDocTitle").innerHTML = docTitle;
	$( ".toggleBlock" ).animate({'padding-top':'0'},480);
	$( "#statusSlide" ).slideDown( 480, function() {});
}
function popupClose(){
	$( ".toggleBlock" ).css("padding-top",0);
	$( ".statusSlide_rapper" ).slideUp( 100, function() {});
}
$(".but_big2").click(function(){
	 document.listForm.action = "<c:url value='/register.do'/>";
	 document.listForm.submit();
})
function fileDown(docId, docTitle){
	var status = document.getElementById("statusSlide").style.display;
	var attach = document.getElementById("attachSlide").style.display;
	if(status == "block" || attach == "block"){
		popupClose();
	}
	$(".listAttachTr").remove();
	var url = "<c:url value='/attachList.do'/>";
	$.ajax({                        
        type: "POST",
        url: url,
        dataType: 'html',
        data:{docId:docId},
        success: function(data) {
        $(".listAttachBody").append(data); 
       }
    });
	document.getElementById("attachDocTitle").innerHTML = docTitle;
	$( ".toggleBlock" ).animate({'padding-top':'0'},480);
	$( "#attachSlide" ).slideDown( 480, function() {});
}
//]]>
</script>
</html>