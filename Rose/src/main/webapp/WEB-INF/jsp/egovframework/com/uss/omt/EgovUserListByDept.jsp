<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<div class="table_rapper">
	<c:choose>
		<c:when test="${user.userType eq 'S' }">
			<table summary="" class="board_type_height">
				<caption class="blind"></caption>
				<colgroup>
			        <col width="32px"/>
			        <col width="*"/>
			        <col width="22%"/>
			        <col width="22%"/>
				</colgroup>
				<thead>
					<tr>
						<th scope="col" class="align_center"><a href="javascript:checkAll();" id="button_check_all" class=""><img src="<c:url value='/images/egovframework/com/uss/cmm/icon_check.png'/>" alt="icon_check"></a></th>
						<th scope="col" class="align_left"><spring:message code="org.user.label.name"/></th>
						<th scope="col" class="align_left"><spring:message code="org.user.label.id"/></th>
						<th scope="col" class="align_left"><spring:message code="org.user.label.position"/></th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
					<c:when test="${empty list}">
						<tr>
							<td class="align_center">&nbsp;</td>
							<td colspan="3"><spring:message code="common.msg.table.nodata"/></td>
						</tr>
					</c:when>
					<c:otherwise>					
						<c:forEach var="row" items="${list}" varStatus="status">
							<tr id="ROW_<c:out value="${status.count}" />" data="{id: '<c:out value="${row.uniqId}"/>', name: '${row.emplyrNm}', loginId: '${row.emplyrId}', deptId: '${row.orgnztId}', companyId: '${row.companyId}'}">
								<td class="align_center"><input type="checkbox" name="userCheck" onclick="javascript:checkType('${checkType}', this);" value="<c:out value="${status.count}"/>" /></td>
								<td><a href="#" onclick="doView('<c:out value="${row.uniqId}"/>')"><c:out value="${row.emplyrNm}"/></a></td>
								<td><c:out value="${row.emplyrId}"/></td>
								<td><c:out value="${row.positionNm}"/></td>
							</tr>
						</c:forEach>
					</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<table summary="" class="board_type_height">
				<caption class="blind"></caption>
				<colgroup>
			        <col width="32px"/>
			        <col width="*"/>
			        <col width="22%"/>
			        <col width="22%"/>
			        <col width="22%"/>
				</colgroup>
				<thead>
					<tr>
						<th scope="col" class="align_center"><a href="javascript:checkAll();" id="button_check_all" class=""><img src="<c:url value='/images/egovframework/com/uss/cmm/icon_check.png'/>" alt="icon_check"></a></th>
						<th scope="col" class="align_left"><spring:message code="org.user.label.position"/></th>
						<th scope="col" class="align_left"><spring:message code="org.user.label.duty"/></th>
						<th scope="col" class="align_left"><spring:message code="org.user.label.name"/></th>
						<th scope="col" class="align_left"><spring:message code="org.user.label.id"/></th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
					<c:when test="${empty list}">
						<tr>
							<td class="align_center">&nbsp;</td>
							<td colspan="4"><spring:message code="common.msg.table.nodata"/></td>
						</tr>
					</c:when>
					<c:otherwise>					
						<c:forEach var="row" items="${list}" varStatus="status">
							<tr id="ROW_<c:out value="${status.count}" />" data="{id: '<c:out value="${row.uniqId}"/>', name: '${row.emplyrNm}', loginId: '${row.emplyrId}', deptId: '${row.orgnztId}', companyId: '${row.companyId}'}">
								<td class="align_center"><input type="checkbox" name="userCheck" onclick="javascript:checkType('${checkType}', this);" value="<c:out value="${status.count}"/>" /></td>
								<td><c:out value="${row.positionNm}"/></td>
								<td><c:out value="${row.dutyNm}"/></td>
								<td><a href="#" onclick="doView('<c:out value="${row.uniqId}"/>')"><c:out value="${row.emplyrNm}"/></a></td>
								<td><c:out value="${row.emplyrId}"/></td>
							</tr>
						</c:forEach>
					</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>	
</div>	
<script language="javascript" type="text/javascript">
function checkType(checkType, chk){
	if(checkType == "single"){
		var obj = document.getElementsByName("userCheck");
			for(var i=0; i<obj.length; i++){
				if(obj[i] != chk){
					obj[i].checked = false;
				}
			}
	}
}
</script>