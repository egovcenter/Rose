<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="Gnb"> 
  <div class="gnb_am">
    <div class="top_logo"><span class="tx_b">BridgeOffice</span></div>
    <!-- Top Menu Start -->
    <div class="menu">
      <ul>
        <!--li><a href="#" title="HOME"><spring:message code="common.menu.home"/></a></li-->
        <c:if test="${!sessionScope.user.systemAdmin and !(sessionScope.user.companyAdmin)}">
        <li ><a href="#" onclick="gotoApproval()" title="APPROVAL"><spring:message code="common.menu.approval"/></a></li>
        </c:if>
        <!--li><a href="#" title="BBS"><spring:message code="common.menu.bbs"/></a></li-->
        <li class="on"><a href="#" title="ADMIN"><spring:message code="common.menu.admin"/></a></li>
      </ul>
    </div>
    <!-- Top Menu End -->
    <!-- user Information Start -->
    <div class="user">
      <ul style="cursor: pointer;" id="login_view">
        <li><img src="download.jsp?FN=${sessionScope.user.deptId}${sessionScope.user.userId}&FT=photo" alt="user"></li>
        <li>
<%--             <div class="name profile">${sessionScope.user.userNm}</div> --%>
          <div class="name profile"><span><c:out value="${loginVO.name}"/></span></div>
        </li>
        <li>
          <div class="view"><a href="#" title="view"></a></div>
        </li>
      </ul>
    </div>
    <!-- user Information Start -->
  </div>
</div>

<script>
	var APPROVAL_CONTEXT = "${pageContext.servletContext.contextPath}";
	
	function gotoApproval(){
	    window.top.location.href=APPROVAL_CONTEXT + "/home.do";
	}
		
	function closePopup() {
	    $(".layerPopup_rapper").hide();
	    $("#shadow").hide();
	}
	
	function profileView() {
		var _width = 240;
		var _height = 300;
		
	    var wo = {
	        id: null,
	        url: "org.do?" +
	                $.param({
	                    acton: "profile",
	                    COMMUNITY_ID: "${sessionScope.user.deptId}",
	                    DEPT_ID: "${param.DEPT_ID}",
	                    T: "${param.T}" || "${sessionScope.T}"
	                }),
	//         name: "_popup",
	//         feature: "width=440, height=530"
	    };
	    
	    $("#iframePopup").each(function() {
	        $(this).attr("src", wo.url);
	        $(this).attr("width", _width);
	        $(this).attr("height", _height);
	    });
	    $(".layerPopup_rapper").each(function() {
	        $(this).css({
	           right:"20px",
				left:"inherit",
	            top: [200, "px"].join(""),
	            width: _width,
	            height: _height,
	        });
	        $(this).css({
				marginLeft:-parseInt($(".layerPopup_rapper").css("width"))/2+"px",
				marginTop:-parseInt($(".layerPopup_rapper").css("height"))/2+"px"
	        });
	        $(this).show();
	    });
	
		$(window).resize(function(){
			var winH = $(window).height();
			var winW = $(window).width();
			$("#shadow").css({"width":winW, "height":winH});
		})
	
	
	    $("#shadow").each(function() {
	        $(this).css({
	            width: $(document).width() + "px",
	            height: $(document).height() + "px",
	            opacity: 0.5
	        });
	        $(this).show();
	    });
	
	}
	
	$(function() {
		$('#login_view, .profile').click(function() {
	        profileView();
		});
		
	// 	$("body").each(function() {
	// 		$(this).append([
	// 			'<div class="layerProfileWrapper" style="display: none;">',
	// 			'<iframe name="iframeProfile" id="iframeProfile" src=""></iframe>',
	// 			'</div>'
	//         ].join(''));
	// 		$(this).append('<div id="shadow" style="display: none;"></div>');
	// 	});
	
	});
</script>