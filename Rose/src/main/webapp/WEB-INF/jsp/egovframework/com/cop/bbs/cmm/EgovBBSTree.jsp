<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>jQuery treeview</title>
 
<link rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/jquery.treeview.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/layout.css'/>" type="text/css">
<link rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/form.css'/>" type="text/css">
<link rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/print.css'/>" type="text/css" media="print">

<script src="<c:url value='/js/egovframework/com/cmm/jquery.treeview.js'/>" type="text/javascript"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jquery.cookie.js'/>" type="text/javascript"></script>
<script type="text/javascript"> 

	$(function() {
		$("#tree").treeview({
			animated: "medium",
			control:"#sidetreecontrol",
			persist: "cookie"
		});
		
	})

	function fn_egov_select_bbs(nodeId,nodeNm) {
	document.getElementById("nodeId").value = nodeId;
	document.getElementById("nodeNm").value = nodeNm;
	document.listForm.action = "<c:url value='/cop/bbs/selectBoardList.do'/>";
	document.listForm.submit();
	}
</script>
</head>
<body>
<%-- <jsp:include page="/WEB-INF/jsp/egovframework/com/uss/ion/cmm/layerPopup.jsp" flush="false" /> --%>
<div id="sidetree" style="background:#fff;">
	<ul id="tree">
		<li>
<!-- 			<ul class="filetree"> -->
				<c:forEach var="node" items="${treeList}" varStatus="nodeStatus">
				
					<%-- 자식이 있는지 체크 --%>
					<c:set var="hasChild" value="false" />
					<c:forEach var="child" items="${treeList}" >
						<c:if test="${node.nodeId eq child.parentId }">
							<c:set var="hasChild" value="true" />
						</c:if>
					</c:forEach>
					<%-- 자식이 있는지 체크 끝--%>

					<c:choose>
						<%--자식이 있으면 --%>
						<c:when test="${hasChild }">
							<li id="${node.nodeId}" parentId="${node.parentId}" level="${node.level}"   class="close " style="cursor: pointer;" >
								<span id="${node.nodeId}" nm="${node.nodeNm}" class='folder'>${node.nodeNm}</span>
								<ul>
						</c:when>
						<%--자식이 없으면 --%>
						<c:when test="${not hasChild }">
							<li id="${node.nodeId}" parentId="${node.parentId}" level="${node.level}"   class="close " style="cursor: pointer;" >
								<span id="${node.nodeId}" nm="${node.nodeNm}" class='file'><a href="#" class="filetree" onclick="javascript:fn_egov_select_bbs('${node.nodeId}','${node.nodeNm}');return false;" id="${node.nodeId}">${node.nodeNm}</a></span>
							</li>
						</c:when>
					</c:choose>
					
					<%--다음 노드의 level이 현재보다 작으면 현재의 노드는 끝난 것임. 그로므로 태그를 닫아줘야 함. --%>
					<c:forEach var="nextNode" items="${treeList }" begin="${nodeStatus.index +1 }" end="${nodeStatus.index +1 }">
						<c:if test="${node.level > nextNode.level}">
							<c:forEach var="i" begin="${nextNode.level}" end="${node.level -1}">
								<c:out value="</ul></li>" escapeXml="false"/>
							</c:forEach>
						</c:if>
					</c:forEach>

					<%--노드가 시작하거나 끝나면 마무리..  --%>
					<c:choose>
						<c:when test="${nodeStatus.first }">
						</c:when>
						<c:when test="${nodeStatus.last }">
								</ul>
							</li>
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				
			</ul>
		</li>
		
	</ul>
</div>

<form action="<c:url value='/cop/bbs/selectBoardList.do'/>" name="listForm" id="listForm" method="post">
<input type="hidden" value="" id="nodeId" name="nodeId">
<input type="hidden" value="" id="nodeNm" name="nodeNm">
</body>
</html>

