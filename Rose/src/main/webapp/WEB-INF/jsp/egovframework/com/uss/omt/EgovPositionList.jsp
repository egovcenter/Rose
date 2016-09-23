<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script src="<c:url value='/js/egovframework/com/uss/omt/orgCommon.js'/>"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jquery-1.11.3.js'/>"></script>
<script>
var CONTEXT = "${pageContext.servletContext.contextPath}";
var env = {
    editable: "<c:if test="${not empty list}">true</c:if>" == "true"
};
function doView(id) {
	$("body").css("overflow","");
	$("#div_popup").load(CONTEXT+'/positionView.do', {POSI_ID:id},
			function(){
				if (typeof(select_modal_load) != "undefined") {
					select_modal_load();
				}
				$("#div_popup").show();
			}
	);
}
function getSelectedIDs() {
	var ids = [];
	var undeleteableIDs = [];
	$('input[type=checkbox]:checked').each(function() {
        var rowid = $(this).val();
        var datastr = $(['#ROW', rowid].join("_")).attr("data");
        var inst = toInstance(datastr);
        if (inst.deletable) {
            ids.push(inst.id);
        } else {
        	undeleteableIDs.push(inst.id);
        }
    });
	return {
		ids: ids,
		undeleteableIDs: undeleteableIDs
	};
}
function getCheckboxSize() {
	var size = 0;
	$('input[type=checkbox]').each(function() {
        size++;
    });
	return size;
}
$(function() {
	$('#button_position_new').click(function() {
		$("body").css("overflow","");
		$("#div_popup").load(CONTEXT+'/positionForm.do', {POSI_ID:""},
				function(){
					if (typeof(select_modal_load) != "undefined") {
						select_modal_load();
					}
					$("#div_popup").show();
				}
		);
	});	
	
    if (env.editable) {
        $('#button_position_delete').click(function() {
            var selectedIDs = getSelectedIDs();
            if (selectedIDs.undeleteableIDs.length > 0) {
            	alert("<spring:message code="org.position.msg.004"/>");
            	return;
            }
            var ids = selectedIDs.ids;
            if (ids.length == 0) {
                alert("<spring:message code="common.msg.select.delete"/>");
                return;
            }
            if (confirm("<spring:message code="common.msg.confim.delete"/>")) {
                var postData = $.param({
                	POSI_ID: ids.toString()
	            });
                var formURL = "positionDelete.do";
                $.ajax(
                {
                    url : formURL,
                    type: "POST",
                    data : postData,
                    success: function(data, textStatus, jqXHR) {
                    	location.href="positionList.do"
                    }
                });
            }
        });
        $('#button_check_all').click(function() {
            var checked = ! $(this).hasClass("checked");
            $('input[type=checkbox]').each(function() {
                $(this).prop("checked", checked);
            });
            $(this).toggleClass("checked");
        });
        $('input[type=checkbox]').click(function() {
            if (! this.checked) {
                 $('#button_check_all').removeClass("checked");
            }
        });
    }
});
</script>
</head>

<body>
<div class="wrap"> 
  <!-- Top Menu Start-->
  <jsp:include page="/WEB-INF/jsp/egovframework/com/cmm/EgovTopMenu.jsp" flush="false">
	<jsp:param value="ADMIN" name="selectedMenu"/>
  </jsp:include>
  <!-- Top Menu End -->
  <div class="clear"></div>
  <!-- Container start -->
  <div class="Container"> 
    <!-- Lnb -->
    <div class="Side"> 
      	<!-- Side Menu -->
		<jsp:include page="/WEB-INF/jsp/egovframework/com/uss/omt/cmm/EgovLeftMenu.jsp" flush="false">
			<jsp:param value="POSITION" name="selectedMenu"/>
		</jsp:include> 
    </div>
    <!-- Content start -->
    <div class="Content"> 
      <!-- Content box start -->
      <div class="content_box" id="org_content_box">
        <!-- 공통 타이틀 버튼 라인 -->
        <div class="title_box">
          <div class="title_line">
            <h1><spring:message code="org.position.label.positionmng"/></h1>
          </div>
          <div class="clear"></div>
        </div>
		<div class="overflow_hidden">
			<p class="tab_title float_left"><spring:message code="org.position.label.list"/></p>	

			<div class="ui_btn_rapper float_right">
				<a href="#" class="btn_color3" id="button_position_new"><spring:message code="common.button.new"/></a>
				<a href="#" class="btn_color2" id="button_position_delete"><spring:message code="common.button.delete"/></a>
			</div>			
		</div>		
		<div class="table_rapper display_table">
			<div class="display_tableCell">
				<table summary="" class="board_type_height cursorDefault">
					<caption class="blind"></caption>
					<colgroup>
						<col width="40px"/>
						<col width="40%"/>
						<col width="*"/>
					</colgroup>
					<thead>
						<tr>
							<th scope="col" class="align_center"><a href="#" id="button_check_all" data="{checked: false}"><img src="<c:url value='/images/egovframework/com/uss/cmm/icon_check.png'/>" alt="icon_check"></a></th>
							<th scope="col" class="align_left"><span><spring:message code="org.position.label.position"/></span></th>
							<th scope="col" class="align_left"><span><spring:message code="org.position.label.securitylevel"/></span></th>
						</tr>
					</thead>					
					<tbody>
					<c:choose>
					<c:when test="${empty list}">
                        <tr>
                            <td class="align_center">&nbsp;</td>
                            <td colspan="2"><spring:message code="common.msg.table.nodata"/></td>
                        </tr>
					</c:when>
					<c:otherwise>
					<c:forEach var="row" items="${list}" varStatus="status">
                        <tr id="ROW_<c:out value="${status.count}" />" data="{id: '${row.posiId}', name: '${row.posiNm}', deletable: 'true' == '${row.deletable}'}">
                            <td class="align_center"><input type="checkbox" value="<c:out value="${status.count}"/>"/></td>
                            <td><a href="#" onclick="doView('<c:out value="${row.posiId}"/>');"><c:out value="${row.posiNm}"/></a></td>
                            <td><c:out value="${row.posiLv}"/></td>
                        </tr>
                    </c:forEach>
					</c:otherwise>
					</c:choose>
					</tbody>
				</table>
			</div>			
		</div>
  		</div>
  		<!-- Content box end -->
      </div>
      <!-- Content end -->
    </div>
    <!-- Container end -->
    <div class="clear"></div>
 </div>
 <div id="div_popup" style="display: none"></div>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCopyright.jsp" %>
<!-- form start -->
<form action="/org.do" name="frm" id="frm" method="post">
	<input type="hidden" id="deptId" name="deptId" value="${user.orgnztId}">
	<input type="hidden" id="POSI_ID" name="POSI_ID" value="">
</form>
</body>
</html>