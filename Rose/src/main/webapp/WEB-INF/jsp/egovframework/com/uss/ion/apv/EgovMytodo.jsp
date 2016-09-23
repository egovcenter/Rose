<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/approval.js'/>"></script>
</head>

<body>
<script>
function approvalRedraft(docID){
	document.getElementById("docId").value = docID;
	document.pageParam.action = "<c:url value='/approvalRedraft.do'/>";
	document.pageParam.submit();
}
function approvalApprove(docID){
	document.getElementById("docId").value = docID;
	document.pageParam.action = "<c:url value='/approvalApprove.do'/>";
	document.pageParam.submit();
}
function approvalView(docID){
	document.getElementById("docId").value = docID;
	document.pageParam.action = "<c:url value='/approvalView.do'/>";
	document.pageParam.submit();
}
function approvalReceive(docID){
	document.getElementById("orgdocId").value = docID;
	document.pageParam.action = "<c:url value='/approvalReceive.do'/>";
	document.pageParam.submit();
}
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
					<!--  <input name="textfield" type="text" id="textfield" title="search" class="input260" onClick="this.style.backgroundImage='none'">
					<input type="button" value="Search" class="but_navy" onClick="location.href='http://www.naver.com'";/>-->
				</div>
				
				<!-- waiting List start -->		
				<div class="clear"></div>
				<div class="title_box">
					<div class="title_line">
						<h1><spring:message code="appvl.sidemenu.tree.processing.waiting"/></h1>
					</div>					
				</div>
				<div class="ico_more float_right"><a href="javascript:approvalDocPage('waiting');"><img src="<c:url value='/images/egovframework/com/uss/cmm/ico_more.png'/>" alt="btn_more"/></a></div>
				<div class="rapper_table mb40">
					<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height">
						<caption class="blind"></caption>
						<colgroup>
							<col width="10%"/>
							<col width="90px"/>
							<col width="*"/>
							<col width="15%"/>
							<col width="20%"/>
							<col width="10%"/>
							<col width="40px"/>
						</colgroup>
						<thead>
							<tr>
								<th scope="col"><span><spring:message code="appvl.processing.wo.table.type"/></span></th>
								<th scope="col" class=""><span><spring:message code="appvl.processing.wo.table.draftdate"/></span></th>			
								<th scope="col"><span><spring:message code="appvl.processing.wo.table.title"/></span></th>
								<th scope="col"><span><spring:message code="appvl.processing.wo.table.draftdept"/></span></th>
								<th scope="col"><span><spring:message code="appvl.processing.wo.table.drafter"/></span></th>
								<th scope="col"><span><spring:message code="appvl.processing.wo.table.status"/></span></th>		
								<th scope="col" class="align_center"><img src="<c:url value='/images/egovframework/com/uss/cmm/icon_file.png'/>" alt="icon_check"></th>
							</tr>
						</thead>					
						<tbody>
							 <c:if test="${empty listWaiting}">
							 	<tr><td colspan="7" class="table_nodata"><span><spring:message code="common.msg.table.nodata"/></span></td></tr>
							 </c:if>
							 <c:forEach var="waiting" items="${listWaiting}" begin="0" end="2" step="1">
								 <tr>
									<td>
										<c:if test="${waiting.docType == 'DT01'}"><spring:message code="appvl.doctype.internal"/></c:if>
										<c:if test="${waiting.docType == 'DT02'}"><spring:message code="appvl.doctype.outgoing"/></c:if>
										<c:if test="${waiting.docType == 'DT03'}"><spring:message code="appvl.doctype.incoming"/></c:if>
									</td>
									<td class="">
										<fmt:formatDate pattern="yyyy-MM-dd" value="${waiting.draftDateTime }" />
									</td>
									<td>
										<c:choose>
											<c:when test="${waiting.docPState =='DP02'}"><a href="javascript:approvalRedraft('<c:out value="${waiting.docID}"/>', '<c:out value="${loginVO.uniqId}"/>')">${waiting.docTitle}</a></c:when>
											<c:otherwise><a href="javascript:approvalApprove('<c:out value="${waiting.docID}"/>', '<c:out value="${loginVO.uniqId}"/>')">${waiting.docTitle}</a></c:otherwise>
										</c:choose>
									</td>
									<td>${waiting.draftDeptName }</td>
									<td>${waiting.drafterName }</td>
									<td><a href="javascript:status('<c:out value="${waiting.docID}"/>', '<c:out value="${waiting.docTitle}"/>')" class="Status">
										<input type="hidden" value="${waiting.docID}" id="waitDocId" name="waitDocId">
										<c:if test="${waiting.docPState =='DP00'}"><spring:message code="appvl.docpState.DP00"/></c:if>
										<c:if test="${waiting.docPState =='DP01'}"><spring:message code="appvl.docpState.DP01"/></c:if>
										<c:if test="${waiting.docPState =='DP02'}"><spring:message code="appvl.docpState.DP02"/></c:if>
										<c:if test="${waiting.docPState =='DP09'}"><spring:message code="appvl.docpState.DP09"/></c:if>
									</a></td>
									<td class="align_center">
										<c:if test="${waiting.docAttaF =='1'}">
										<a href="javascript:fileDown('<c:out value="${waiting.docID}"/>', '<c:out value="${waiting.docTitle}"/>')" class="fileDown">
											<input type="hidden" value="${waiting.docID}" id="ongoingDocId" name="ongoingDocId">
											<img src="<c:url value='/images/egovframework/com/uss/cmm/icon_file.png'/>" alt="icon_file"/>
										</a>
										</c:if>
									</td>
								</tr>
							 </c:forEach>
						</tbody>
					</table>
				</div>
				
				<c:if test="${userAuth.sendAuth == '1' }">
					<div class="clear"></div>
					<div class="title_box">
						<div class="title_line">
							<h1><spring:message code="appvl.sidemenu.tree.processing.outgoing"/></h1>						
						</div>					
					</div>
					<div class="ico_more float_right"><a href="javascript:approvalDocPage('outgoing');"><img src="<c:url value='/images/egovframework/com/uss/cmm/ico_more.png'/>" alt="icon_more"/></a></div>
					<div class="rapper_table mb40">
						<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height">
							<caption class="blind"></caption>
							<colgroup>
								<col width="110px"/>
								<col width="20%"/>
								<col width="*"/>
								<col width="15%"/>
							</colgroup>
							<thead>
								<tr>
									<th scope="col" class=""><span><spring:message code="appvl.processing.outgoing.table.draftdate"/></span></th>			
									<th scope="col"><span><spring:message code="appvl.processing.outgoing.table.docnum"/></span></th>
									<th scope="col"><span><spring:message code="appvl.processing.outgoing.table.title"/></span></th>
									<th scope="col"><span><spring:message code="appvl.processing.outgoing.table.recipient"/></span></th>		
								</tr>
							</thead>					
							<tbody>
								<c:if test="${empty listOutgoing}">
									<tr><td colspan="4" class="table_nodata"><span><spring:message code="common.msg.table.nodata"/></span></td></tr>
								</c:if>
								<c:forEach var="outgoing" items="${listOutgoing}" begin="0" end="2" step="1">
									<tr>
										<td class=""><fmt:formatDate pattern="yyyy-MM-dd" value="${outgoing.draftDateTime }" /></td>
										<td>${outgoing.docCd } </td>
										<td><a href="javascript:approvalSend('<c:out value="${outgoing.docID}"/>', '<c:out value="${loginVO.uniqId}"/>')">${outgoing.docTitle }</a></td>
										<td>${outgoing.recipientDeptNames }</td>
									</tr>
								 </c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
				
				<c:if test="${userAuth.receiveAuth == '1' }">
					<div class="clear"></div>
					<div class="title_box">
						<div class="title_line">
							<h1><spring:message code="appvl.sidemenu.tree.processing.incoming"/></h1>						
						</div>					
					</div>
					<div class="ico_more float_right"><a href="javascript:approvalDocPage('incoming');"><img src="<c:url value='/images/egovframework/com/uss/cmm/ico_more.png'/>" alt="icon_more"/></a></div>
					<div class="rapper_table mb40">
						<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height">
							<caption class="blind"></caption>
							<colgroup>
								<col width="110px"/>
								<col width="20%"/>
								<col width="*"/>
								<col width="15%"/>
							</colgroup>
							<thead>
								<tr>
									<th scope="col" class=""><span><spring:message code="appvl.processing.incoming.table.registerdate"/></span></th>
									<th scope="col"><span><spring:message code="appvl.processing.incoming.table.docnum"/></span></th>
									<th scope="col"><span><spring:message code="appvl.processing.incoming.table.title"/></span></th>
									<th scope="col"><span><spring:message code="appvl.processing.incoming.table.sender"/></span></th>		
								</tr>
							</thead>					
							<tbody>
								<c:if test="${empty listIncoming}">
									<tr><td colspan="4" class="table_nodata"><span><spring:message code="common.msg.table.nodata"/></span></td></tr>
								</c:if>							
								<c:forEach var="incoming" items="${listIncoming}" begin="0" end="2" step="1">
									<tr>
										<td class=""><fmt:formatDate pattern="yyyy-MM-dd" value="${incoming.arrivalDateTime }" /></td>
										<td>${incoming.docCd } </td>
										<td>
											<c:choose>
											<c:when test="${not empty incoming.recpRecpDocId}"><a href="javascript:approvalView('<c:out value="${incoming.recpRecpDocId}"/>', '<c:out value="${loginVO.uniqId}"/>')">${incoming.docTitle }</a></c:when>
											<c:when test="${not empty incoming.recpRecpDt}"><a href="javascript:approvalView('<c:out value="${incoming.docID}"/>', '<c:out value="${loginVO.uniqId}"/>')">${incoming.docTitle }</a></c:when>
											<c:otherwise>
												<c:if test="${incoming.docPpF eq '1'}"><a href="javascript:approvalReceive4Paper('<c:out value="${incoming.docID}"/>', '<c:out value="${loginVO.uniqId}"/>')">${incoming.docTitle }</a></c:if>
												<c:if test="${incoming.docPpF eq '2'}"><a href="javascript:approvalReceive('<c:out value="${incoming.docID}"/>', '<c:out value="${loginVO.uniqId}"/>')">${incoming.docTitle }</a></c:if>
											</c:otherwise>
											</c:choose>
										</td>
										<td>${incoming.senderUserName }</td>								
									</tr>
								 </c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
				
				<div class="toggleBlock"  style="padding-top:150px; margin-top:20px;"></div>
				<div class="clear"></div>
				<div class="statusSlide_rapper rapper_table" id="statusSlide">
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
				<div class="statusSlide_rapper rapper_table" id="attachSlide">
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
function status(docId, docTitle){
	var status = document.getElementById("statusSlide").style.display;
	var attach = document.getElementById("attachSlide").style.display;
	if(status == "block" || attach == "block"){
		popupClose();
	}
	
	$(".listSignerTr").remove();
	var url = "<c:url value='/listOngoingApprovalSigner.do'/>";
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
function approvalDocPage(docType){
	 document.getElementById("docType").value = docType;
	 document.listForm.action = "<c:url value='/approvalDocPageList.do'/>";
	 document.listForm.submit();
}
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
 function approvalReceive4Paper(docID, userId){
	$("input[name=orgdocId]", "#listForm").val(docID);
	$("input[name=isincoming]", "#listForm").val("1");
	$("#formSelect").show();
	//ajax
	 var url = "<c:url value='/formSelectLabel.do'/>";
	$.ajax({                        
        type: "POST",
        url: url,
        dataType: 'html',
        data:{url:url},
        success: function(data) {
        	$(".fromLabel").empty();
        	$(".fromLabel").append(data); 
       }
    });	 
}
//]]>
</script>
</html>