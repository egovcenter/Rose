<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul id="tree" style="background:#fff !important;">
 <!-- by david.kim
 	top 부서가 빠져서 퀴리 결과가 오니까 강제적으로 넣어 줘야 한다.
  -->
	<c:if test="${user.userType eq 'D' or user.userType eq 'S'}">  
    <li id="001" level="1" class="close" style="cursor: pointer;">
        <div>&nbsp;<span id="001" nm="ROOT" class='folder'>ROOT</span></div>
	<ul>
	</c:if>
	    <c:forEach var="node" items="${tree}" varStatus="nodeStatus">
	        <%-- 자식이 있는지 체크 --%>
	        <c:set var="hasChild" value="false" />
	        <c:forEach var="nextNode" items="${tree}" begin="${nodeStatus.index + 1}" end="${nodeStatus.index + 1}">
	            <c:if test="${node.deptLevel < nextNode.deptLevel}">
	                <c:set var="hasChild" value="true" />
	            </c:if>
	        </c:forEach>
	        <%-- 자식이 있는지 체크 끝--%>
<!-- by david.kim 
strong으로 하니까 폰트가 밀린다. 색깔을 바꾸어 보자.     
	        <c:choose>
	            <c:when test="${node.deptId == param.DEPT_ID}"><c:set var="printDeptNm" value="<strong>${node.deptNm}</strong>"/></c:when>
	            <c:otherwise><c:set var="printDeptNm" value="${node.deptNm}"/></c:otherwise>
	        </c:choose>        
 -->	        
	        <c:choose>
	             <%--자식이 있으면 --%>
	             <c:when test="${hasChild }">
	                 <li id="${node.deptId}" level="${node.deptLevel}" class="close " style="cursor: pointer;">
	                     <div>&nbsp;<span id="${node.deptId}" nm="${node.deptNm}" class='folder'><a href="#" id="node_${node.deptId}">${node.deptNm}</a></span>
	                     	<input type="checkbox" value="${node.deptId}" id="DEPT_ID" name="DEPT_ID" class="DEPT_ID" onclick="onlyOneCheck(this)" <c:if test="${selectedDeptId eq node.deptId}">checked="checked"</c:if>/>
	                     </div>
	                     <ul>
	             </c:when>
	             <%--자식이 없으면 --%>
	             <c:when test="${not hasChild }">
	                 <li id="${node.deptId}" level="${node.deptLevel}" class="close " style="cursor: pointer;">&nbsp;
	                     <span id="${node.deptId}" nm="${node.deptNm}" class='file labelPage'><a href="#" id="node_${node.deptId}">${node.deptNm}</a></span>
	                     <input type="checkbox" value="${node.deptId}" id="DEPT_ID" name="DEPT_ID" class="DEPT_ID" onclick="onlyOneCheck(this)" <c:if test="${selectedDeptId eq node.deptId}">checked="checked"</c:if>/>
	                 </li>
	             </c:when>
	         </c:choose>
	         
	         <%--다음 노드의 depth이 현재보다 작으면 현재의 노드는 끝난 것임. 그로므로 태그를 닫아줘야 함. --%>
	         <c:forEach var="nextNode" items="${tree}" begin="${nodeStatus.index + 1}" end="${nodeStatus.index + 1}">
				 <c:if test="${node.deptLevel > nextNode.deptLevel}">
		             <c:forEach var="i" begin="${nextNode.deptLevel}" end="${node.deptLevel -1}">
		                     <c:out value="</ul></li>" escapeXml="false"/>
		             </c:forEach>
				 </c:if>
	         </c:forEach>
	
	         <%--노드가 시작하거나 끝나면 마무리..  --%>
	         <c:choose>
	             <c:when test="${nodeStatus.first }">
	             </c:when>
	             <c:when test="${nodeStatus.last }">
	                     <c:out value="</ul></li>" escapeXml="false"/>
	             </c:when>
	             <c:otherwise>
	             </c:otherwise>
	         </c:choose>
	    </c:forEach>
    </ul>
    </li>
</ul>
<script>
var env = env == undefined ? {} : env;
env["head4"] = "${param.acton.substring(0, 4)}";

$(function() {
    $("#tree").treeview({
        animated: "medium",
        control:"#sidetreecontrol",
        persist: "cookie"
    });
    
    /* 선택된 노드 강조 처리 */
    $(['#node_', env.head4].join("")).each(function() {
        var text = $(this).html();
        $(this).html(["<strong>", text, "</strong>"].join(""));
    });
//     /* 부서 트리 노드 클릭 시 */
//     $(".folder, .file").click(function() {
//         var id = $(this).attr("id");
//         var paramObj = {
//                 acton: "deptView",
//                 DEPT_ID: id
//         };
//         goOrgDo(paramObj);
//     });
    
});
/* 부서는 다중선택을 할 수 없음 */
function onlyOneCheck(checkBox){
	var obj = document.getElementsByName("DEPT_ID");
	for(var i=0; i<obj.length; i++){
		if(obj[i] != checkBox){
			obj[i].checked = false;
		}
	}
}
/* 부서는 다중선택을 할 수 없음 */
</script>