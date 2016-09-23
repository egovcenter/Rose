<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:if test="${param.type eq 'single'}">
<ul id="tree" style="background:#fff !important;">
</c:if>	
		<c:if test="${param.fusion eq 'N'}">
		    <li>
		       <div>&nbsp;<span id="001" nm="ROOT">ROOT</span></div>
			<ul>
		</c:if>
			<c:forEach var="node" items="${labelTree}" varStatus="nodeStatus" begin="0">
			
			<%-- 자식이 있는지 체크 --%>
		       <c:set var="hasChild" value="false" />
		       <c:forEach var="nextNode" items="${labelTree}" begin="${nodeStatus.index + 1}" end="${nodeStatus.index + 1}">
		           <c:if test="${(node.labelLevel + 1) eq nextNode.labelLevel}">
					<c:set var="hasChild" value="true" />
				</c:if>
		       </c:forEach>
			<%-- 자식이 있는지 체크 끝--%>
		
		       <c:choose>
		            <%--자식이 있으면 --%>
		            <c:when test="${hasChild}">
		                <li id="${node.labelId}" level="${node.labelLevel}" class="close" style="cursor: pointer;">
		                    <div><span id="${node.labelId}" nm="${node.labelNm}" class='folder'><a href="#" class="filetree" id="node_${node.labelId}" onclick="javascript:fn_select_Label('${node.labelId}','${node.labelNm}');return false;">${node.labelNm}</a></span></div>
		                    <ul>
		            </c:when>
		            <%--자식이 없으면 --%>
		            <c:when test="${not hasChild}">
		                <li id="${node.labelId}" level="${node.labelLevel}" class="close" style="cursor: pointer;">
		                    <span id="${node.labelId}" nm="${node.labelNm}" class='file'><a href="#" class="filetree" id="node_${node.labelId}" onclick="javascript:fn_select_Label('${node.labelId}','${node.labelNm}');return false;">${node.labelNm}</a></span>
		                </li>
		            </c:when>
		        </c:choose>
				
		        <%--다음 노드의 depth이 현재보다 작으면 현재의 노드는 끝난 것임. 그로므로 태그를 닫아줘야 함. --%>
		        <c:forEach var="nextNode" items="${labelTree}" begin="${nodeStatus.index + 1}" end="${nodeStatus.index + 1}">
		            <c:if test="${node.labelLevel > nextNode.labelLevel}">
						<c:forEach var="i" begin="${nextNode.labelLevel}" end="${node.labelLevel -1}">
							<c:out value="</ul></li>" escapeXml="false"/>
						</c:forEach>
		            </c:if>
		        </c:forEach>
		
		        <%--노드가 시작하거나 끝나면 마무리..  --%>
		        <c:choose>
		            <c:when test="${nodeStatus.first}">
		            </c:when>
		            <c:when test="${nodeStatus.last}">
		                    <c:out value="</ul></li>" escapeXml="false"/>
		            </c:when>
		            <c:otherwise>
		            </c:otherwise>
		        </c:choose>
			</c:forEach>
<c:if test="${param.type eq 'single'}">
</ul>
</c:if>