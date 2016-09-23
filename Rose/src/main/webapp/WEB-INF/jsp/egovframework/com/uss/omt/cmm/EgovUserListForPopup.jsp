<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="en">
<head>
<script src="<c:url value='/js/egovframework/com/uss/omt/divPopup.js'/>"></script>
<c:if test="${not empty detailUserSrc}">
	<script type="text/javascript" src="<c:out value="${detailUserSrc}" />"></script>
</c:if>
	
<script type="text/javascript">
function directory_userList_viewUserSpec(userID, event) {
<c:choose>
<c:when test="${not empty detailUserCmd}">
	<c:out value="${detailUserCmd}" />;
</c:when>
<c:otherwise>
	orgPopup.viewUserSpec(userID);
</c:otherwise>
</c:choose>
}

$(document).ready(function() {
	$("input[name='checkUser']").each(function() {
		var o = new Recipient($(this).val(), $("#checkUser_" + $(this).val()).val(), $("#checkDept_" + $(this).val()).val(), $("#checkDeptName_" + $(this).val()).val());
		if (orgPopup.toList.contains(o)
				|| orgPopup.ccList.contains(o)
				|| orgPopup.bccList.contains(o)) {
			$(this).attr("checked", true);
		}
		if ($("#notUseUser").val() && $("#notUseUser").val().indexOf($(this).val()) > -1) {
			$(this).attr("disabled", true);
		}
	});
	
	if ($("#userSearchType").val() == "role") {
		$("#name-search").hide();
		$("#role-search").show();
	} else {
		$("#name-search").show();
		$("#role-search").hide();
	}
});

$(function() {
	$("#userSearchType").change(function() {
		if ($(this).val() == "role") {
			$('#userSearchValue').val('');
			$("#name-search").hide();
			$("#role-search").show();
		} else {
			$("#userSearchRole option[value='']").attr("selected", true);
			$("#role_subdept").attr("checked", false);
			$("#name-search").show();
			$("#role-search").hide();
		}
	});
	$("#userSearchValue").keypress(function(event) {
		if (event.keyCode == 13) {
			event.keyCode = 0;
			orgPopup.searchUser();
		}
	});
	$("#userSearchButton").click(function() {
		orgPopup.searchUser();
	});
	$("#checkUserAll").click(function() {
		orgPopup.toggleUserAll();
	});
	$("input[name='checkUser']").click(function() {
		orgPopup.toggleUser($(this));
		if (typeof itemClick != "undefined") {
			itemClick();
		}
	});
	$("#directory-table-userList tr").dblclick(function() {
		var selectObj = $(this).find("input[name='checkUser']");
		if (selectObj.length == 0 || selectObj.is(":disabled")) {
			return;
		}
		selectObj.attr("checked", (selectObj.is(":checked") ? false : true));
		
		orgPopup.toggleUser(selectObj);
		if (typeof itemDblClick != "undefined" && selectObj.is(":checked")) {
			itemDblClick();
		}
	});
});
</script>
</head>
<body>
	<!-- table : start -->
	<table id="directory-table-userList" class="content_lst no_btn" border="0" cellspacing="0" cellpadding="0" style="border-bottom: 0; margin-top: 10px;">
		<col width="33px">
		<col width="">
		<col width="">
		<col width="">
		<!-- style 추가 -->
		<tr style="background-color:#e8eef1; border-top: 2px solid #90949a;">
			<th scope="col" class="input_chk"><c:if test="${param.selectMode == 2 && !display.userSingle}"><input type="checkbox" class="inchk" id="checkUserAll" /></c:if></th>
			<th scope="col" class="lef"><fmt:message key="common.table.name" /></th>
			<th scope="col" class="lef"><fmt:message key="common.table.dept" /></th>
			<th scope="col" class="lef"><fmt:message key="common.table.position" /></th>
		</tr>
		<tbody>
		<c:set var="count" value="0" scope="page" />
		<c:forEach var="user" items="${list}" varStatus="loop">
			<c:if test="${not empty user.emplyrNm}">
				<c:set target="${user}" property="emplyrNm" value="${user.emplyrNm}" />
			</c:if>
			<c:if test="${not empty user.orgnztNm}">
				<c:set target="${user}" property="orgnztNm" value="${user.orgnztNm}" />
			</c:if>
			<c:if test="${not empty user.positionNm}">
				<c:set target="${user}" property="positionNm" value="${user.positionNm}" />
			</c:if>
			
			<tr>
				<td class="input_chk">
					<input type="hidden" id="checkUser_<c:out value="${user.uniqId}" />" value="<c:out value="${user.emplyrNm}" />" />
					<input type="hidden" id="checkDept_<c:out value="${user.uniqId}" />" value="<c:out value="${user.orgnztId}" />" />
					<input type="hidden" id="checkDeptName_<c:out value="${user.uniqId}" />" value="<c:out value="${user.orgnztNm}" />" />
					<input type="<c:choose><c:when test="${param.selectMode == 1 || display.userSingle}">checkbox</c:when><c:when test="${param.selectMode == 2}">checkbox</c:when></c:choose>" class="inchk" name="checkUser" value="<c:out value="${user.uniqId}" />" <c:if test="${param.openerType == 'M' && empty user.emailAdres}">disabled="disabled"</c:if> />
				</td>
				<td title="<c:out value="${user.emplyrNm}" />">
					<c:choose>
					<c:when test="${useDetailUser}">
						<a href="#" onclick="javascript:directory_userList_viewUserSpec('<c:out value="${user.uniqId}" />', event);"><c:out value="${user.emplyrNm}" /></a>
					</c:when>
					<c:otherwise>
						<c:out value="${user.emplyrNm}" />
					</c:otherwise>
					</c:choose>
					<c:if test="${param.useAbsent && user.userAbsF}"><fmt:message key="user.absence" /></c:if>
				</td>
				<td class="lef" title="<c:out value="${user.orgnztNm}" />"><c:out value="${user.orgnztNm}" /></td>
				<td class="lef" title="<c:out value="${user.positionNm}" />"><c:out value="${user.positionNm}" /></td>
			</tr>
			<c:if test="${loop.last}">
				<c:set var="count" value="${loop.count}" />
			</c:if>
		</c:forEach>
		<c:forEach var="i" begin="${count}" end="7">
			<tr>
			<c:choose>
			<c:when test="${count == 0 && i == 1}">
				<td colspan="4" class="cen">
				<c:choose>
				<c:when test="${param.searchType == 'role'}">
					<fmt:message key="duty.nocharge" />
				</c:when>
				<c:otherwise>
					<fmt:message key="user.notuser" />
				</c:otherwise>
				</c:choose>
				</td>
			</c:when>
			<c:otherwise>
				<td colspan="4"></td>
			</c:otherwise>
			</c:choose>
			</tr>
		</c:forEach>
		<c:remove var="count" scope="page" />
		</tbody>
	</table>
	<!-- table : end -->
</body>
</html>