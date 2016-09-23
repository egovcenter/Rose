<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="en">
<head>
<meta charset="utf-8">
</head>
<body>
<div class="Side"> 
	<!-- Common Title -->
	<div class="lnb_in">
		Setting
	</div>
	<div class="h30"></div>
	<!-- Menu Tree -->
	<div class="lnb_tree" style="background:#FFF;">
		<div class="rapper_table mb40">
		<table summary="" class="boardList">
			<caption class="blind"></caption>
			<colgroup>
				<col width="100%"/>
			</colgroup>
			<thead>
			</thead>					
			<tbody>
					 <tr>
						<td <c:if test="${param.selectedMenu eq 'profile' or empty param.selectedMenu}">class="select"</c:if>><a href="javascript:profile()">Profile</a></td>
					</tr>
					<tr>
						<td <c:if test="${param.selectedMenu eq 'password'}">class="select"</c:if>><a href="javascript:password()">Password</a></td>
					</tr>
					<tr>
						<td <c:if test="${param.selectedMenu eq 'absence'}">class="select"</c:if>><a href="javascript:absence()">Absence</a></td>
					</tr>
			</tbody>
		</table>
		</div>
	</div>
</div>
</body>
<script type="text/javascript"> 
function profile(){
	top.location.href = CONTEXT+"/profileSetting.do";
}
function password(){
	top.location.href = CONTEXT+"/passwordSetting.do";
}
/*
function absence(){
	top.location.href = CONTEXT+"/absenceSetting.do";
}
*/
</script>
</html>