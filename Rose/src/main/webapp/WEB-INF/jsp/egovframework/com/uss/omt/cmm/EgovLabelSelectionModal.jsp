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
</head>

<div class="layerPopup_rapper" style="width:600px;">	
	<p class="popup_title type_admin"><spring:message code="appvl.selectlabel.title"/></p>
	<div class="ui_btn_rapper float_right mtb10">
		<a href="javascript:closeViewLabelTreeLayer()" class="btn_color2"><spring:message code="common.button.cancel"/></a>
	</div>
	<div class="popup_tree scroll_height300">
	      <ul class="tree" style="background:#fff;text-align: left">
   		    <li>
		       <div>&nbsp;<span id="001" nm="ROOT" class='folder' style="text-align: left"><a href="javascript:selectLabel('<c:out value="${rootLabel.labelId}"/>','ROOT')">ROOT</a></span></div>
			<ul>
     	 <!--클래스lnb_tree 안에 트리구조 삽입 (width181px)-->
				<c:forEach var="node" items="${labelList}" varStatus="nodeStatus">
					<%-- 자식이 있는지 체크 --%>
					<c:set var="hasChild" value="false" />
					<c:forEach var="child" items="${labelList}" >
						<c:if test="${node.labelId eq child.labelParentId }">
							<c:set var="hasChild" value="true" />
						</c:if>
					</c:forEach>
					<%-- 자식이 있는지 체크 끝--%>
					
					<c:choose>
						<%--자식이 있으면 --%>
						<c:when test="${hasChild }">
							<li id="${node.labelId}" parentId="${node.labelParentId}" level="${node.labelLevel}"   class="close " style="cursor: pointer;" >
								<!-- 20160315_SUJI.H selectLabel매개변수추가 -->
								<div>&nbsp;<span id="${node.labelId}" nm="${node.labelNm}" class='folder' style="text-align: left"><a href="javascript:selectLabel('<c:out value="${node.labelId}"/>','<c:out value="${node.labelNm}"/>', '<c:out value="${node.labelParentId}"/>')"><c:out value="${node.labelNm}"/></a></span></div>
								<ul>
						</c:when>
						<%--자식이 없으면 --%>
						<c:when test="${not hasChild }">
							<li id="${node.labelId}" parentId="${node.labelParentId}" level="${node.labelLevel}"   class="close " style="cursor: pointer;" >&nbsp;
								<!-- 20160315_SUJI.H selectLabel매개변수추가 -->
								<span id="${node.labelId}" nm="${node.labelNm}" class='file labelPage' style="text-align: left"><a href="javascript:selectLabel('<c:out value="${node.labelId}"/>','<c:out value="${node.labelNm}"/>', '<c:out value="${node.labelParentId}"/>')" class="filetree treeLink" id="label"><c:out value="${node.labelNm}"/></a></span>
							</li>
						</c:when>
					</c:choose>
					
					<%--다음 노드의 labelLevel이 현재보다 작으면 현재의 노드는 끝난 것임. 그로므로 태그를 닫아줘야 함. --%>
					<c:forEach var="nextNode" items="${labelList }" begin="${nodeStatus.index +1 }" end="${nodeStatus.index +1 }">
						<c:forEach var="i" begin="${nextNode.labelLevel}" end="${node.labelLevel -1}" >
							<c:if test="${node.labelLevel > nextNode.labelLevel}">
								</ul>
								<c:if test="${node.labelParentId > nextNode.labelParentId}">
									</li>
								</c:if>
							</c:if>
						</c:forEach>
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
	</div>
</div>
<div id="shadow_labeltreelayer" class="shadow"></div>
<script language="javascript" type="text/javascript">
//<![CDATA[
$('.board_type_height th').click(function() {
if ($(this).hasClass('sort_down')){
	$('.board_type_height th').removeClass('sort_down');
	$(this).addClass('sort_up');
} else {
	$('.board_type_height th').removeClass('sort_up');
	$(this).addClass('sort_down');
  }
});
function viewLabelTreeLayer_onLoad(){
	$("#shadow_labeltreelayer").css(
			{
				width:$(document).width(),
				height:$(document).height()+48,
				opacity:0.5
			}
		);

		$(".layerPopup_rapper").css(
			{
				left:"50%",
				top:"15%",
				marginLeft:-parseInt($(".layerPopup_rapper").css("width"))/2+"px"
			}
		);
		
		$(".popup_tree").treeview({
			animated: "medium",
			control:"#sidetreecontrol",
			persist: "cookie"
		});
}
//]]>
</script>
</html>