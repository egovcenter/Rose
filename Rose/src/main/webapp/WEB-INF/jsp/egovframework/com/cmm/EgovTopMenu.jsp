<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="<c:out value="${pageContext.servletContext.contextPath}"/>/js/egovframework/com/uss/ion/admin.js"></script>
<div class="Gnb"> 
	<c:choose>
		<c:when test="${param.selectedMenu eq 'HOME'}"><div class="gnb_in"></c:when>
		<c:when test="${param.selectedMenu eq 'ADMIN'}"><div class="gnb_am"></c:when>
		<c:otherwise><div class="gnb_ap"></c:otherwise>
	</c:choose>
		<div class="top_logo"><span class="tx_b">BridgeOffice</span></div>
		<!-- Top Menu Start -->
		<div class="menu">
			<ul>
				<c:if test="${user.userType ne 'S' and user.userType ne 'A'}">
				<li <c:if test="${param.selectedMenu eq 'HOME' or empty param.selectedMenu}">class="on"</c:if>><a href="javascript:gotoHome()" title="HOME"><spring:message code="common.menu.home"/></a></li>
				<li <c:if test="${param.selectedMenu eq 'APPROVAL'}">class="on"</c:if>><a href="javascript:gotoApproval()" title="APPROVAL"><spring:message code="common.menu.approval"/></a></li>
				<li <c:if test="${param.selectedMenu eq 'EMAIL'}">class="on"</c:if>><a href="javascript:gotoEmail()" title="EMAIL"><spring:message code="common.menu.email"/></a></li>
				<li <c:if test="${param.selectedMenu eq 'BBS'}">class="on"</c:if>><a href="javascript:gotoBbs()" title="BBS"><spring:message code="common.menu.bbs"/></a></li>
				<li <c:if test="${param.selectedMenu eq 'SCHEDULE'}">class="on"</c:if>><a href="javascript:gotoSchedule()" title="SCHEDULE"><spring:message code="common.menu.schedule"/></a></li>
				<li <c:if test="${param.selectedMenu eq 'RESERVATION'}">class="on"</c:if>><a href="javascript:gotoReservation()" title="RESERVATION"><spring:message code="common.menu.reservation"/></a></li>
				</c:if>
				<c:if test="${user.userType eq 'D'}">
				<li <c:if test="${param.selectedMenu eq 'ADMIN'}">class="on"</c:if>><a href="javascript:gotoAdmin()" title="ADMIN"><spring:message code="common.menu.admin"/></a></li>
				</c:if>
			</ul>
			<input type="hidden" id="selectedMenu" value="${param.selectedMenu}">
		</div>
		<!-- Top Menu End -->
		<!-- user Information Start -->
		<div class="user">
			<ul style="cursor: pointer;" id="login_view">
				<li><img src="<c:out value="${pageContext.servletContext.contextPath}"/>/downloadImg.jsp?FN=${user.orgnztId}${user.uniqId}&FT=photo" alt="user"></li>
				<li><div class="name profile"><span><c:out value="${user.emplyrNm}" /></span></div></li>
				<li>
					<div class="view">
						<a href="#"  title="view"></a>
					</div>
				</li>
			</ul>
		</div>
		<!-- user Information Start --> 
	</div>
</div>

<script>
function gotoHome(){
	window.top.location.href="<c:out value="${pageContext.servletContext.contextPath}"/>/home.do";
}
function gotoApproval(){
	window.top.location.href="<c:out value="${pageContext.servletContext.contextPath}"/>/mytodo.do";
}
function gotoEmail(){
}
function gotoBbs(){
	window.top.location.href="<c:out value="${pageContext.servletContext.contextPath}"/>/cop/bbs/selectBoardList.do";
}
function gotoSchedule(){
}
function gotoReservation(){
}
function gotoAdmin(){
	window.top.location.href="<c:out value="${pageContext.servletContext.contextPath}"/>/userList.do";
}
var selectedMenu=$('#selectedMenu').val();	
var CONTEXT = "${pageContext.servletContext.contextPath}";
function getCookie(name) {
	var dc = document.cookie;
	var prefix = name + "=";
	var begin = dc.indexOf("; " + prefix);
	if (begin == -1) {
		begin = dc.indexOf(prefix);
		if (begin != 0)
			return null;
	} else
		begin += 2;
	var end = document.cookie.indexOf(";", begin);
	if (end == -1)
		end = dc.length;
	return unescape(dc.substring(begin + prefix.length, end));
}
function profileView() {
	var _width = 240;
	var _height = 300;
    var wo = {
        id: null,
        url: CONTEXT+"/profile.do",
    };
    $("#iframePopup").each(function() {
        $(this).attr("src", wo.url);
        $(this).attr("width", _width);
        $(this).attr("height", _height);
    });
    $(".profilePop").each(function() {
        $(this).css({
			right:"20px",
            top:[50, "px"].join(""), 
			left:"inherit",
			marginTop:"0px",
			marginRight:"0px !important;",
			marginBottom:"0px !important;",
			marginLeft:"-300px",
            width: _width,
            height: _height,
        });
        $(this).show();
    });
	$(window).resize(function(){
		 $("#shadow_profile,.shadow_profile").css({
            width:$(window).width(),
			height:$(document).height()+48,
            opacity: 0.5
        });
	});
    $("#shadow_profile,.shadow_profile").css({
            width:$(window).width(),
			height:$(document).height()+48,
            opacity: 0.5
        });
    $("#shadow_profile,.shadow_profile").show();
}

$(function() {
	$('#login_view, .profile').click(function() {
        profileView();
	});
});
function closePopup() {
	$(".profilePop").hide();
    $("#shadow, #shadow_profile, .shadow_profile, #shadow_org").hide();
}
</script>