<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
</head>
<body>
<div class="rapper_table mb40">
	<c:choose>
		<c:when test="${tabName eq 'waiting' or tabName eq 'ongoing'}">
		    <table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height">
		        <caption class="blind"></caption>
		        <colgroup>
		            <col width="*"/>
		            <col width="25%"/>
		            <col width="25%"/>
		            <col width="25%"/>
		        </colgroup>
		        <thead>
		            <tr>
		                <th scope="col"><span><spring:message code="appvl.processing.wo.table.draftdate"/></span></th>
		                <th scope="col"><span><spring:message code="appvl.processing.wo.table.title"/></span></th>			
		                <th scope="col"><span><spring:message code="appvl.processing.wo.table.draftdept"/></span></th>
		                <th scope="col"><span><spring:message code="appvl.processing.wo.table.drafter"/></span></th>
		            </tr>
		        </thead>					
		        <tbody>
				<c:if test="${empty list}">
				 	<tr><td colspan="4" class="table_nodata"><span><spring:message code="common.msg.table.nodata"/></span></td></tr>
				</c:if>
				<c:forEach var="list" items="${list}" begin="0" end="7" step="1">
					 <tr>
						<td class="">
							<fmt:formatDate pattern="yyyy-MM-dd" value="${list.draftDateTime}"/>
						</td>
						<td>
							<c:choose>
								<c:when test="${docType eq 'waiting' and list.docPState ne 'DP02'}"><a href="javascript:approvalApprove('<c:out value="${list.docID}"/>')">${list.docTitle}</a></c:when>
								<c:when test="${docType eq 'waiting' and list.docPState eq 'DP02'}"><a href="javascript:approvalRedraft('<c:out value="${list.docID}"/>')">${list.docTitle}</a></c:when>
								<c:otherwise><a href="javascript:approvalView('<c:out value="${list.docID}"/>')">${list.docTitle}</a></c:otherwise>
							</c:choose>
						</td>
						<td>${list.draftDeptName}</td>
						<td>${list.drafterName}</td>
					</tr>
				</c:forEach>
		        </tbody>
		    </table>
		</c:when>
		<c:otherwise>
	    	<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height">
		        <caption class="blind"></caption>
		        <colgroup>
		            <col width="*"/>
		            <col width="25%"/>
		            <col width="25%"/>
		            <col width="25%"/>
		        </colgroup>
		        <thead>
		            <tr>
		                <th scope="col"><span><spring:message code="common.date"/></span></th>
		                <th scope="col"><span><spring:message code="common.title"/></span></th>			
		                <th scope="col"><span><spring:message code="common.department"/></span></th>
		                <th scope="col"><span><spring:message code="common.sender"/></span></th>
		            </tr>
		        </thead>					
		        <tbody>
				<c:if test="${empty list}">
				 	<tr><td colspan="4" class="table_nodata"><span><spring:message code="common.msg.table.nodata"/></span></td></tr>
				</c:if>
				<c:forEach var="list" items="${list}" begin="0" end="7" step="1">
					 <tr>
						<td class="">
							<fmt:formatDate pattern="yyyy-MM-dd" value="${list.draftDateTime}"/>
						</td>
						<td>
							<c:choose>
								<c:when test="${docType eq 'waiting' and list.docPState ne 'DP02'}"><a href="javascript:approvalApprove('<c:out value="${list.docID}"/>')">${list.docTitle}</a></c:when>
								<c:when test="${docType eq 'waiting' and list.docPState eq 'DP02'}"><a href="javascript:approvalRedraft('<c:out value="${list.docID}"/>')">${list.docTitle}</a></c:when>
								<c:otherwise><a href="javascript:approvalView('<c:out value="${list.docID}"/>')">${list.docTitle}</a></c:otherwise>
							</c:choose>
						</td>
						<td>${list.draftDeptName}</td>
						<td>${list.drafterName}</td>
					</tr>
				</c:forEach>
		        </tbody>
		    </table>
		</c:otherwise>
	</c:choose>
</div>
</body>
</html>