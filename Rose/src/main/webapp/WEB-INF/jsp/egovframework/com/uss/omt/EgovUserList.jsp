<!doctype html>
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
<script>
function formMapperOnload() {
	//$('input:hidden[name=DEPT_ID]').val("<c:out value="${dept.deptId}" />");
	//$('#DEPT_ID').val("<c:out value="${dept.deptId}" />");
	$('#DEPT_PAR_ID').val("<c:out value="${dept.deptParId}" />");
	$('#DEPT_SEQ').val("<c:out value="${dept.deptSeq}" />");
	$('#DEPT_NM').val("<c:out value="${dept.deptNm}" />");
	$('#DEPT_CD').val("<c:out value="${dept.deptCd}" />");
	$('#DEPT_RBOX_F').attr("checked", "<c:out value="${dept.deptRboxF}" />" == "1");
	$('#DEPT_BOX_F').attr("checked", "<c:out value="${dept.deptBoxF}" />" == "1");
	$('#DEPT_TOP_F_Y').attr("checked", "<c:out value="${dept.deptTopF}" />" == "1");
	$('#DEPT_TOP_F_N').attr("checked", "<c:out value="${dept.deptTopF}" />" == "2");
	//$('#DEPT_RBOX_USER_ID').val("<c:out value="${dept.deptRboxUserId}" />");
	//$('#DEPT_BOX_USER_ID').val("<c:out value="${dept.deptBoxUserId}" />");
	//$('#DEPT_SBOX_USER_ID').val("<c:out value="${dept.deptSboxUserId}" />");
	$('#DEPT_RBOX_USER_NM').val("<c:out value="${DEPT_RBOX_USER_NM}" />");
	$('#DEPT_BOX_USER_NM').val("<c:out value="${DEPT_BOX_USER_NM}" />");
	$('#DEPT_SBOX_USER_NM').val("<c:out value="${DEPT_SBOX_USER_NM}" />");
	$('#DEPT_ADMIN_USER_NM').val("<c:out value="${DEPT_ADMIN_USER_NM}" />");
}
var env = {
		isDeletable: "true" == "${isDeletable}",
		isSysAdmin: "${sessionScope.user.systemAdmin}" == "true",
		undeletableMessage: "${hasUndeletableMessage}" == "true"
			   ? ["<spring:message code="org.department.msg.005"/>"].join("\r\n") : ""
}
function initTree() {
	$('input[type=checkbox]:checked').each(function(){
		$(this).prop('checked', false);
	});
}
function getSelectedList() {
    var objs = [];
    $('input[type=checkbox]:checked').each(function() {
        var rowid = $(this).val();
        var datastr = $(['#ROW', rowid].join("_")).attr("data");
        if (datastr != undefined) {
            var inst = toInstance(datastr);
        	objs.push(inst);
    	}
    });
    return objs;
}
function getIdList(userInfoList) {
	var idList = [];
	for (i = 0; i < userInfoList.length; i++) {
		idList.push(userInfoList[i].id);
	}
	return idList.toString();
}
$(function() {
	formMapperOnload();
	initTree();
    /* New 버튼 클릭 시 */
    $('#button_new').click(function() {
        var paramObj = {
            acton: "deptAdd",
            DEPT_ID: "${param.DEPT_ID}" || "${dept.deptId}" || "${sessionScope.user.deptId}"
        };
        goOrgDo(paramObj);
    });
	$('#button_modify').click(function() {
		var paramObj = {
            acton: "deptModify",
            DEPT_ID: "${param.DEPT_ID}" || "${dept.deptId}" || "${sessionScope.user.deptId}"
        };
        goOrgDo(paramObj);
	});
	$('#button_delete').click(function() {
		if (! env.isDeletable) {
			alert("<spring:message code="org.department.msg.004"/>");
			return;
		}
		if (env.undeletableMessage) {
			alert(env.undeletableMessage);
			return;
		}
	});	
});
/* 부서 트리 노드 클릭시 부서정보페이지 load */
 var CONTEXT = "${pageContext.servletContext.contextPath}";
$(function() {
	var userType = "${user.userType}";
	var orgnztId = "${user.orgnztId}";
    /* 부서 트리 노드 클릭 시 */
    $(".folder, .file").click(function() {
    	if($(this).attr("nm") == "ROOT"){
    		return false;
    	}
   		var selectedDeptId = $(this).attr("id");
    	if(userType != "U" || selectedDeptId == orgnztId){
		/* DeptView Location start*/
			$("body").css("overflow","");
			$("#div_popup").load(CONTEXT+'/deptView.do', {action:"manageDept", deptId: selectedDeptId},
					function(){
						if (typeof(select_deptView_load) != "undefined") {
							select_deptView_load();
						}
						$("#div_popup").show();
					}
			);
    	}
	});
	/* DeptView Location end */
});
function doView(userId) {
 	 document.getElementById("userId").value = userId;
 	 document.getElementById("deptId").value = "<c:out value='${dept.deptId}'/>";
	 document.frm.action = "<c:url value='/userView.do'/>";
	 document.frm.submit();
}
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
			<jsp:param value="ORGANIZATION" name="selectedMenu"/>
		</jsp:include> 
    </div>
    <!-- Content start -->
    <div class="Content"> 
      <!-- Content box start -->
      <div class="content_box"  id="org_content_box">
        <!-- 공통 타이틀 버튼 라인 -->
        <div class="title_box">
          <div class="title_line">
            <h1><spring:message code="org.organization.label.organzationmng"/></h1>
          </div>		  
          <div class="clear"></div>		  
        </div>
        <div class="tree_area">
        	<!-- Button start -->
        	<div class="title_box" <c:if test="${user.userType eq 'D'}">style="visibility: hidden;"</c:if>>
        		<div class="ui_btn_rapper float_right">
					<a href="#" id="button_dept_new"    class="btn_color3"><spring:message code="common.button.new"/></a>
					<a href="#" id="button_dept_modify" class="btn_color3"><spring:message code="common.button.modify"/></a>
					<a href="#" id="button_dept_delete" class="btn_color2"><spring:message code="common.button.delete"/></a>
        		</div>
        		<div class="clear"></div>
        	</div>
        	
        	<!-- Button end -->
        	<!-- tree area start -->
        	<div class="tree">
				<div class="lnb_tree_s" style="background:#FFF;border: 1px">
					<%@ include file="/WEB-INF/jsp/egovframework/com/uss/omt/cmm/EgovLnbTree.jsp" %>
				</div>
        	</div>
        	<!-- tree area end -->
        </div>
		<div class="con_area">
			<!-- Button start -->
			<div class="title_box">
				<div class="ui_btn_rapper float_right">
					<a href="#" id="button_user_new" class="btn_color3"><spring:message code="common.button.new"/></a>
					<a href="#" id="button_user_modify" class="btn_color3"><spring:message code="common.button.modify"/></a>
					<a href="#" id="button_user_delete" class="btn_color2"><spring:message code="common.button.delete"/></a>
				</div>
				<div class="clear"></div>
			</div>
			<!-- Button end -->
        	<!-- userList start -->
			<div class="rapper_table mb40">
				<div id="detailInfo">
					<%@ include file="/WEB-INF/jsp/egovframework/com/uss/omt/EgovUserListByDept.jsp" %>
				</div>
			</div>
			<!-- userList end -->
		</div>        
      <!-- Content box end --> 
    </div>
  	<!-- Container end --> 
    <div class="clear"></div>
  </div>
  <!-- Container end -->
</div>
<div id="div_popup" style="display: none"></div>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCopyright.jsp" %>
<!-- form start -->
<form action="/org.do" name="frm" id="frm" method="post"/>
	<input type="hidden" id="deptId" name="deptId" value="" class="deptId">
	<input type="hidden" id="userId" name="userId" value="" class="userId">
</body>
<script language="javascript" type="text/javascript"> 
//<![CDATA[
/* Department New and Modify Click */
$('#button_dept_new').click(function() {
	document.getElementById("deptId").value = $('#DEPT_ID:checked').val();
	document.frm.action = "<c:url value='/deptAdd.do'/>";
	document.frm.submit();
});
$('#button_dept_modify').click(function() {
	var deptIdChecked = $('#DEPT_ID:checked').val();
	if(deptIdChecked == null){
		alert("<spring:message code="common.msg.select.add"/>");
		return;
	}else{
		document.getElementById("deptId").value = $('#DEPT_ID:checked').val();
		document.frm.action = "<c:url value='/deptModify.do'/>";
		document.frm.submit();
	}
});
/* Department delete Click */
$('#button_dept_delete').click(function() {
	var selectedDept = $('#DEPT_ID:checked').val();
	if (selectedDept == null || selectedDept == "undefined") {
		alert("<spring:message code="common.msg.select.delete"/>");
		return;
	}
	if (confirm("<spring:message code="common.msg.confim.delete"/>")) {
		document.getElementById("deptId").value = $('#DEPT_ID:checked').val();
		document.frm.action = "<c:url value='/deptDelete.do'/>";
		document.frm.submit();
	}
});	
/* Department New and Modify Click */
$('#button_user_new').click(function() {
	document.getElementById("deptId").value = $('#DEPT_ID:checked').val();
	document.frm.action = "<c:url value='/userForm.do'/>";
	document.frm.submit();
});
$('#button_user_modify').click(function() {
	var list = getSelectedList();
	if (list.length == 0) {
		alert("<spring:message code="common.msg.select.add"/>");
		return;
	} else if (list.length > 1) {
		alert("<spring:message code="common.msg.select.modify"/>");
		return;
	}
	var userInfo = list[0];
	document.getElementById("deptId").value = userInfo.deptId;
	document.getElementById("userId").value = userInfo.id;
	document.frm.action = "<c:url value='/userForm.do'/>";
	document.frm.submit();
});
/* Department delete Click */
$('#button_user_delete').click(function() {
	var list = getSelectedList();
	if (list.length == 0) {
		alert("<spring:message code="common.msg.select.delete"/>");
		return;
	}
	var idList = getIdList(list);
	if (confirm("<spring:message code="common.msg.confim.delete"/>")) {
		var postData = $.param({
			idList: idList
		});
		var formURL = "userListDelete.do";
		$.ajax(
		{
			url : formURL,
			type: "POST",
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
			    //data: return data from server
			    location.href = "userList.do"
			},
			error: function(jqXHR, textStatus, errorThrown) 
			{
			    //if fails
			}
		});
	}
});	
$(document).ready(function(){
	var chk = document.getElementsByName("DEPT_ID");
	for(i = 0; i < chk.length; i++){
		chk[0].checked = true;
	}
	
	$(".DEPT_ID").change(function(){
		if($(".DEPT_ID").is(":checked")){
			var deptId = $('#DEPT_ID:checked').val();
			var url = "<c:url value='/userList.do'/>";
			$("#detailInfo").load(url, {action: "getUserListByDept", checkType: "multi", deptId: deptId}, function(data) {
				$(this).html(data).trigger("create");
			});
		}
	});
});
function checkAll(){
	var checked = $('#button_check_all').hasClass("checked");
	if(checked==false){
		$('input[type=checkbox][name=userCheck]').each(function() {
		    $(this).prop("checked", true);
		});
		$('#button_check_all').toggleClass("checked");
	}
	if(checked==true){
		$('input[type=checkbox][name=userCheck]').each(function() {
			$(this).prop("checked", false);
		});
		$('#button_check_all').removeClass("checked");
	}
}
//]]>
</script>
</html>