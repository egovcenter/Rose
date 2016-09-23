<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script src="<c:url value='/js/egovframework/com/uss/omt/orgCommon.js'/>"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jquery-1.11.3.js'/>"></script>
<script>
function select_deptView_load(){
	$("#shadow_org").css(
		{
			width:$(document).width(),
			height:$(document).height(),
			opacity:0.5
		}
	);
	$(".layerPopup_rapper").css(
		{
			left:"50%",
			top:"15%",	
			marginLeft:-parseInt($(".layerPopup_rapper").css("width"))/2+"px",
			marginTop:-parseInt($(".layerPopup_rapper").css("height"))/2+"px"
		}
	);
}
</script>
</head>

<body>	
<div class="layerPopup_rapper">   
   <form name="aForm" method="post">
	   <input type="hidden" name="deptId" value="${dept.deptId}"/>
   <p class="popup_title type_admin">
   	<c:choose>
   		<c:when test="${dept.deptTopF eq '1'}"><spring:message code="org.company.lable.view"/></c:when>
   		<c:otherwise><spring:message code="org.dept.lable.view"/></c:otherwise>
   	</c:choose>
   </p>
	<div class="ui_btn_rapper float_right mtb10">
	<a href="javascript:deptModify();" id="button_dept_modify" class="btn_color3"><spring:message code="common.button.modify"/></a>
	<c:if test="${user.userType eq 'S' or user.userType eq 'A'}">
		<a href="javascript:deptDelete();" id="button_dept_delete" class="btn_color2"><spring:message code="common.button.delete"/></a>
	</c:if>
        <a href="javascript:close();" id="button_close" class="btn_color2"><spring:message code="common.button.close"/></a>
	</div>
	   <!-- table_rapper start -->
   	   <div class="table_rapper">
	   	<table summary="" class="board_width_borderNone">
			<caption class="blind"></caption>
			<colgroup>
				<col width="170px"/>
				<col width="*"/>
			</colgroup>
			<tbody>
			<tr>
				<th scope="row"><label for="label_1"><spring:message code="org.dept.label.name"/></label></th>
				<td colspan="2"><div class="ui_input_text cursorDefault"><input type="text" value="<c:out value="${dept.deptNm }" />" id="DEPT_NM" readonly /></div></td>
			</tr>
		   	<c:choose>
		   		<c:when test="${dept.deptTopF eq '1'}">
   					<tr>
						<th scope="row"><label for="label_5"><spring:message code="common.admin.label.company"/></label></th>
						<td colspan="2"><div class="ui_input_text cursorDefault"><input type="text" value="${dept.deptAdminUserNm}" id="DEPT_ADMIN_USER_NM" readonly /></div></td>
					</tr>
		   		</c:when>
		   		<c:otherwise>
					<tr>
						<th scope="row"><label for="label_2"><spring:message code="common.location.label.location"/></label></th>
						<td colspan="2"><div class="ui_input_text"><input type="text" value="${DEPT_PAR_NM}" id="DEPT_PAR_NM" readonly/></div></td>
					</tr>
					<tr>
						<th scope="row"><label for="label_2"><spring:message code="org.dept.label.departmentcode"/></label></th>
						<td colspan="2"><div class="ui_input_text cursorDefault"><input type="text" value="<c:out value="${dept.deptCd }" />" id="DEPT_CD" readonly /></div></td>
					</tr>
					<tr>
						<th scope="row"><spring:message code="org.dept.label.auth"/></th>
						<td colspan="2">
							<span class="checkbox_rapper cursorDefault">
								<input type="checkbox" value="" id="DEPT_BOX_F" onclick="return false;" <c:if test="${dept.deptBoxF eq '1'}">checked="checked"</c:if>/><label  for="DEPT_BOX_F"><spring:message code="org.dept.label.hasCabinet"/>
							</label></span>
							<span class="checkbox_rapper cursorDefault">
								<input type="checkbox" value="" id="DEPT_RBOX_F" onclick="return false;" <c:if test="${dept.deptRboxF eq '1'}">checked="checked"</c:if>/><label  for="DEPT_RBOX_F"><spring:message code="org.dept.label.hasRCabinet"/>
							</label></span>
						</td>
					</tr>
					<tr>
						<th scope="row"><label for="label_5"><spring:message code="common.management.label.department"/></label></th>
						<td colspan="2"><div class="ui_input_text cursorDefault"><input type="text" value="<c:out value="${dept.deptBoxUserNm }" />" id="DEPT_BOX_USER_NM" readonly /></div></td>
					</tr>
					<tr>
						<th scope="row"><label for="label_5"><spring:message code="common.management.label.register"/></label></th>
						<td colspan="2"><div class="ui_input_text cursorDefault"><input type="text" value="<c:out value="${dept.deptRboxUserNm }" />" id="DEPT_RBOX_USER_NM" readonly /></div></td>
					</tr>
					<tr>
						<th scope="row"><label for="label_5"><spring:message code="common.management.label.sender"/></label></th>
						<td colspan="2"><div class="ui_input_text cursorDefault"><input type="text" value="<c:out value="${dept.deptSboxUserNm }" />" id="DEPT_SBOX_USER_NM" readonly /></div></td>
					</tr>
	   			</c:otherwise>
		   	</c:choose>
			<tr style="display: none;">
				<th scope="row">Representative</th>
				<td colspan="2">
					<span class="radio_rapper cursorDefault"><input type="radio" value="" id="DEPT_TOP_F_Y" onclick="return false;"/><label for="DEPT_TOP_F_Y">Yes</label></span>
					<span class="radio_rapper cursorDefault"><input type="radio" value="" id="DEPT_TOP_F_N" onclick="return false;"/><label for="DEPT_TOP_F_N">No</label></span>
				</td>
			</tr>
			</tbody>
		</table>
		</div>		
		<!-- table_rapper end -->	
   	</form>
</div>
<div id="shadow_org"></div>
</body>
<script language="javascript" type="text/javascript"> 
function deptModify(){
	document.aForm.action = "<c:url value='/deptModify.do'/>";
	document.aForm.submit();
	close();
}
function deptDelete(){
	if (confirm("<spring:message code="common.msg.confim.delete"/>")) {
		document.aForm.action = "<c:url value='/deptDelete.do'/>";
		document.aForm.submit();
		close();
	}
}
function close(){
	$("#div_popup").hide();
}
</script>
</html>