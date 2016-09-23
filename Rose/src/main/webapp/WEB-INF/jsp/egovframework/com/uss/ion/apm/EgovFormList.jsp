<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script src="<c:url value='/js/egovframework/com/uss/omt/orgCommon.js'/>"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jquery-1.11.3.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/jquery.treeview.css'/>" />
<script src="<c:url value='/js/egovframework/com/cmm/jquery.treeview.js'/>" type="text/javascript"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jquery.cookie.js'/>" type="text/javascript"></script>
<script language="javascript">
var APPROVAL_CONTEXT = "${pageContext.servletContext.contextPath}";
var userId = "<c:out value="${user.uniqId}"/>";
var deptId = "<c:out value="${user.orgnztId}"/>";
var companyId = "<c:out value="${topDept.deptId}"/>";
function listForm() {
	location.href = "listForm.do";
}
function viewForm(formId) {
	 $('input:hidden[name=selectedFormId]').val(formId);
	 document.form_form.action = "<c:url value='/viewForm.do'/>";
	 document.form_form.submit();
}
function validateDeleteFormList(){
	if(!confirm('<spring:message code="common.msg.confim.delete"/>')){
		return false;
	}
	if($("input[type=checkbox][name=formId]:checked").length < 1){
		alert('<spring:message code="appvl.form.msg.005"/>');
		return false;
	}
	return true;
}
function deleteFormList(){
	if(!validateDeleteFormList()){
		return;
	}
		var postData = $("#form_form").serializeArray();
		var formURL = $("#form_form").attr("action");
		$.ajax({
			url : formURL,
			type: "POST",
			dataType: 'json',
			data : postData,
			success: function(data, textStatus, jqXHR) 
			{
				if(data.result && data.result == 'success'){
					listForm();
				}else if(data.error_msg){
					alert(data.error_msg);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) 
			{
				alert("delete error!");
			}
		});
	$("#label_form").submit(); //Submit  the FORM
}
$(function() {
	$('#button_check_all').click(function() {
	    var checked = ! $(this).hasClass("checked");
	    $('input[type=checkbox]').each(function() {
	        $(this).prop("checked", checked);
	    });
	    $(this).toggleClass("checked");
	});
	$('input[type=checkbox]').click(function() {
	    if (! this.checked) {
	         $('#button_check_all').removeClass("checked");
	    }
	});
});
function inputFormInfo() {
	 document.form_form.action = "<c:url value='/inputFormInfo.do'/>";
	 document.form_form.submit();
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
			<jsp:param value="FORM" name="selectedMenu"/>
		</jsp:include> 
    </div>
    <!-- Content start -->
    <div class="Content"> 
      <!-- Content box start -->
      <div class="content_box" id="org_content_box">
        <!-- 공통 타이틀 버튼 라인 -->
        <div class="title_box">
          <div class="title_line">
            <h1><spring:message code="appvl.formmng.title.formmng"/></h1>
          </div>
          <div class="clear"></div>
        </div>
		<div class="overflow_hidden">
			<p class="tab_title float_left"><spring:message code="appvl.formmng.title.list"/></p>
			<div class="ui_btn_rapper float_right">
				<a href="javascript:inputFormInfo()" class="btn_color3"><spring:message code="common.button.new"/></a>
				<a href="javascript:deleteFormList()" class="btn_color2"><spring:message code="common.button.delete"/></a>
			</div>
		</div>
		<!-- table_rapper start -->
		<div class="table_rapper display_table">
			<!-- display_tableCell start -->
			<div class="display_tableCell_top">
				<div class="page_move_rapper float_right">
					<span class="pageNumber_view"><strong>${page.firstRecordIndexOnCurrentPage}-${page.lastRecordIndexOnCurrentPage }</strong> of <strong>${page.totalRecordCount }</strong></span>
					<span class="btn_pn_rapper">
						<c:if test="${page.currentPageNo ne 1 }">
							<a href="#" class="move_page_button" act="prev_page"><img src="images/egovframework/com/uss/cmm/but_prev.png" alt="prev"></a>
						</c:if>
						<c:if test="${page.currentPageNo ne page.totalPageCount }">
							<a href="#" class="move_page_button" act="next_page"><img src="images/egovframework/com/uss/cmm/but_next.png" alt="next"></a>
						</c:if>
					</span>
				</div>
				<form action="<c:url value='/listApprovalDoc.do'/>" name="paginationForm" id="paginationForm" method="post">
					<input type="hidden" value="${docType}" id="docType" name="docType" class="docType">
					<input type="hidden" value="${labelID}" id="labelID" name="labelID">
					<input type="hidden" value="${labelNm}" id="labelNm" name="labelNm">
					<input type="hidden" value="${page.currentPageNo}" id="currentPageNo" name="currentPageNo">
					<input type="hidden" value=""${page.orderColumn } id="orderColumn" name="orderColumn">
					<input type="hidden" value="${page.orderMethod }" id="orderMethod" name="orderMethod">
				</form>
				<div class="rapper_table mb40">
					<script>
						$(".move_page_button").click(function(){
							var form = document.paginationForm;
							setFormAttr();
							if($(this).attr("act")=="prev_page"){
								form.currentPageNo.value = parseInt(form.currentPageNo.value)-1;	
							}else{
								 form.currentPageNo.value = parseInt(form.currentPageNo.value)+1;	
							}
							form.submit();
						});
						function fnOrderList(columnObj, orderColumn){
							var form = document.paginationForm;
							setFormAttr();
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
						    form.action = "<c:url value='/listForm.do'/>";
						}
					</script>
					<form id="form_form" name="form_form" action="<c:out value="${pageContext.servletContext.contextPath}"/>/deleteFormList.do" method="post">
						<input type="hidden" name="selectedFormId" id="selectedFormId" value="">
						<table summary="" class="board_type_height cursorDefault">
							<caption class="blind"></caption>
							<colgroup>
							<col width="40px"/>
							<col width="*"/>
							</colgroup>
							<thead>
								<tr>
									<th scope="col" class="align_center"><a href="#" id="button_check_all"><img src="<c:url value='/images/egovframework/com/uss/cmm/icon_check.png'/>" alt="icon_check"></a></th>
									<th scope="col" class="align_left"><span><spring:message code="appvl.formmng.label.scope"/></span></th>
									<th scope="col" class="align_left"><span><spring:message code="appvl.formmng.label.name"/></span></th>
									<th scope="col" class="align_left"><span><spring:message code="appvl.formmng.label.version"/></span></th>
									<th scope="col" class="align_left"><span><spring:message code="appvl.formmng.label.remarks"/></span></th>
									<th scope="col" class="align_left"><span><spring:message code="appvl.formmng.label.formtype"/></span></th>
								</tr>
							</thead>					
							<tbody>
							<c:choose>
								<c:when test="${empty formList}">
			                        <tr>
			                            <td class="align_center">&nbsp;</td>
			                            <td colspan="5"><spring:message code="common.msg.table.nodata"/></td>
			                        </tr>
								</c:when>
								<c:otherwise>
								<c:set var="count" value="0"/>
								<c:forEach var="form" items="${formList}" varStatus="status">
									<c:set var="count" value="${status.count}"/>
									<tr>
										<td class="align_center"><input type="checkbox" value="<c:out value="${form.formId}"/>" name="formId" /></td>
										<td>
											<c:if test="${topDept.deptId eq form.orgId}">Common</c:if>
											<c:if test="${topDept.deptId ne form.orgId}"><c:out value="${form.orgNm}"/></c:if>
										</td>
										<td><a href="javascript:viewForm('<c:out value="${form.formId}"/>')" title="<c:out value="${form.formName}"/>"><c:out value="${form.formName}"/></a></td>
										<td><c:out value="${form.formVersion}"/></td>
										<td><c:out value="${form.formRemark}"/></td>
										<td>
											<c:if test="${form.formType eq '1'}"><spring:message code="appvl.formmng.label.formtype.internal"/></c:if>
											<c:if test="${form.formType eq '2'}"><spring:message code="appvl.formmng.label.formtype.outgoing"/></c:if>
											<c:if test="${form.formType eq '3'}"><spring:message code="appvl.formmng.label.formtype.register"/></c:if>
										</td>
									</tr>
								</c:forEach>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
					</form>
				</div>					
			</div>	<!-- display_tableCell_top -->
		</div>	<!-- table_rapper display_table -->	
	</div>	<!-- Content -->		
  </div> <!-- Container -->			
</div> <!-- wrap -->
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCopyright.jsp" %>
</body>	
</html>