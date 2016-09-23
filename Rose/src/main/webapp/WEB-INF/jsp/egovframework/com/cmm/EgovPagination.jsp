<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="page_move_rapper float_right">
	<span class="pageNumber_view"><strong>${page.firstRecordIndexOnCurrentPage}-${page.lastRecordIndexOnCurrentPage }</strong> of <strong>${page.totalRecordCount }</strong></span>
	<span class="btn_pn_rapper">
			<c:if test="${page.currentPageNo ne 1 }">
				<a href="#" class="move_page_button" act="prev_page"><img src="<c:url value='/images/egovframework/com/uss/cmm/but_prev.png'/>" alt="prev"></a>
			</c:if>
			<c:if test="${page.currentPageNo ne page.totalPageCount }">
				<a href="#" class="move_page_button" act="next_page"><img src="<c:url value='/images/egovframework/com/uss/cmm/but_next.png'/>" alt="next"></a>
			</c:if>
	</span>
</div>
<form action="<c:url value='/listApprovalDoc.do'/>" name="paginationForm" id="paginationForm" method="post">
	<input type="hidden" value="${docType}" id="docType" name="docType" class="docType">
	<input type="hidden" value="${labelID}" id="labelId" name="labelId">
	<input type="hidden" value="${labelNm}" id="labelNm" name="labelNm">
	<input type="hidden" value="${page.currentPageNo}" id="currentPageNo" name="currentPageNo">
	<input type="hidden" value="${page.orderColumn}" id="orderColumn" name="orderColumn">
	<input type="hidden" value="${page.orderMethod}" id="orderMethod" name="orderMethod">
</form>
<div class="rapper_table mb40">
<script>
$(".move_page_button").click(function(){
  	var form = document.paginationForm;
  	/*setting form action*/
  	setFormAttr();
  	/*setting prev or next page*/
  	if($(this).attr("act")=="prev_page"){
  		form.currentPageNo.value = parseInt(form.currentPageNo.value)-1;	
  	}else{
  		form.currentPageNo.value = parseInt(form.currentPageNo.value)+1;	
  	}
	form.submit();
});
function fnOrderList(columnObj, orderColumn){
	var form = document.paginationForm;
	/*setting form action*/
	setFormAttr();
	/* setting order info */
	form.orderColumn.value = orderColumn;
	if( columnObj.classList.contains('sort_down') ){
		form.orderMethod.value = "asc";
	}else{
		form.orderMethod.value = "desc";
	}
	form.submit();
}
function setFormAttr(){
	var form = document.paginationForm;
	/*setting form action*/
	switch('${docType}'){
		case 'mytodo'		:	form.action = "<c:url value='/mytodo.do'/>";
		break;
		case 'waiting'		:	
		case 'outgoing'		:	
		case 'incoming'		:	
		case 'ongoing'		:	
		case 'draft'		:	
		case 'approval'		:	
		case 'outbox'		:	
		case 'inbox'		:	
		case 'passbox'		:
		case 'label'		:	form.action = "<c:url value='/approvalDocPageList.do'/>";
		break;
	}
}
</script>