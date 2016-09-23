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
<script language="javascript" type="text/javascript"> 
/* Position click */
function position(){
	 document.sideMenu.action = "<c:url value='/positionList.do'/>";
	 document.sideMenu.submit();
}
/* Duty click */
function duty(){
	 document.sideMenu.action = "<c:url value='/dutyList.do'/>";
	 document.sideMenu.submit();
}
/* Form click */
function form(){
	 document.sideMenu.action = "<c:url value='/listForm.do'/>";
	 document.sideMenu.submit();
}
/* BulletinBoard click */
// function bulletinBoard(){
// 	 document.sideMenu.action = "<c:url value='/org.do'/>";
// 	 document.sideMenu.submit();
// }
/* Organization click */
function organization(){
	 document.sideMenu.action = "<c:url value='/userList.do'/>";
	 document.sideMenu.submit();
}
/* Label click */
function label(){
	 document.sideMenu.action = "<c:url value='/mngLabel.do'/>";
	 document.sideMenu.submit();
}
</script>
</head>
<body>
<div class="Side"> 
	<!-- Common Title -->
	<div class="lnb_am">
		<c:if test="${user.userType eq 'S'}">
			<spring:message code="common.admin.label.sys"/>
		</c:if>
		<c:if test="${user.userType eq 'A'}">
			<spring:message code="common.admin.label.company"/>
		</c:if>
		<c:if test="${user.userType eq 'D'}">
			<spring:message code="common.management.label.department"/>
		</c:if>
	</div>
	<div class="h30"></div>
	<!-- sideMenu -->
	<div class="lnb_tree mb40" style="background:#FFF;">
	<form name="sideMenu" action="" method="post">
		<!-- left menu start -->
        <div class="rapper_table ">
       		<table summary="" class="boardList">
        		<caption class="blind"></caption>
        		<colgroup>
      			 	<col width="100%"/>
       			</colgroup>				
       			<tbody>
       				<c:if test="${user.userType eq 'S'}">    
	        			<tr>
	        				 <td class="select"><a href="javascript:organization()" id="organization"><spring:message code="org.organization.label.organzation"/></a></td>
	        			</tr>
        			</c:if>
        			<c:if test="${user.userType eq 'A'}">
	        			<tr>
	        				 <td <c:if test="${param.selectedMenu eq 'POSITION'}">class="select"</c:if>>
	        				 	<a href="javascript:position()" id="position"><spring:message code="org.position.label.position"/></a>
        				 	</td>
	        			</tr>
	        			<tr>
	        				 <td <c:if test="${param.selectedMenu eq 'DUTY'}">class="select"</c:if>>
	        				 	<a href="javascript:duty()" id="duty"><spring:message code="org.duty.label.duty"/></a>
        				 	</td>
	        			</tr>
	        			<tr>
	        				 <td <c:if test="${param.selectedMenu eq 'FORM'}">class="select"</c:if>>
	        				 	<a href="javascript:form()" id="form"><spring:message code="org.form.label.form"/></a>
        				 	</td>
	        			</tr>
	        			<!-- <tr>
	        				 <td class=""><a href="javascript:label()" id="label">Label</a></td>
	        			</tr> -->
	        			<tr>
	        				 <td <c:if test="${param.selectedMenu eq 'BBOARD'}">class="select"</c:if>>
	        				 	<a href="javascript:bulletinBoard()" id="bulletinBoard"><spring:message code="org.bulletinBoard.label.bulletinBoard"/></a>
        				 	</td>
	        			</tr>
	        			<tr>
	        				 <td <c:if test="${param.selectedMenu eq 'ORGANIZATION' or empty param.selectedMenu}">class="select"</c:if>>
	        				 	<a href="javascript:organization()" id="organization"><spring:message code="org.organization.label.organzation"/></a>
        				 	</td>
	        			</tr>
        			</c:if>
        			<c:if test="${user.userType eq 'D'}">
						<tr>
	        				 <td <c:if test="${param.selectedMenu eq 'LABEL'}">class="select"</c:if>>
	        				 	<a href="javascript:label()" id="label"><spring:message code="org.label.label.label"/></a>
        				 	</td>
	        			</tr>
						<tr>
	        				 <td <c:if test="${param.selectedMenu eq 'ORGANIZATION' or empty param.selectedMenu}">class="select"</c:if>>
	        				 	<a href="javascript:organization()" id="organization"><spring:message code="org.organization.label.organzation"/></a>
        				 	</td>
	        			</tr>
        			</c:if>
        			<!--tr>
        				 <td class=""><a href="#">Equipment</a></td>
        			</tr-->
        		</tbody>
        	</table>
        </div>
        <!--left menu end -->
	</form>
	</div>
</div>
</body>
</html>