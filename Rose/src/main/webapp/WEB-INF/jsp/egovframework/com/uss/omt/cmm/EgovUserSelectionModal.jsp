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
<script>
var selectedId;
function select_modal_load(){
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
function displayLoad(){
	$(".DEPT_ID").css("display","none");
}
$(function() {
	var deptId = "${deptId}";
	if(deptId != ""){
		selectedId = "#node_"+deptId;
		$(selectedId).css("font-weight", "bold");
		$(selectedId).css('color', '#fb6565');
	}
	$('input:checkbox[name=DEPT_ID]').each(function() {
		$(this).hide();
	});
	$(".folder, .file").click(function() {
		$(selectedId).css("font-weight", "");
		$(selectedId).css('color', "");

		selectedId = $(this).attr("id");
		
		selectedId = "#node_"+selectedId;
		$(selectedId).css("font-weight", "bold");
		$(selectedId).css('color', '#fb6565');
	});
});
</script>
</head>

<body onload="displayLoad()">
<div  class="layerPopup_rapper" style="width:760px;">
    <p class="popup_title type_admin"><spring:message code="org.user.label.selectuser"/></p>
    <div class="ui_btn_rapper float_right mtb10">
        <a href="#" class="btn_color3" id="button_select" ><spring:message code="common.button.select"/></a>
        <a href="javascript:close();" class="btn_color2" id="button_close"><spring:message code="common.button.cancel"/></a>
    </div>
    <div class="display_table">
        <div class="display_tableCell_middle">
            <div style="width:312px; height:302px; overflow:auto; border:1px solid #dcdbd5;">
	        	<!-- dept tree start -->
			    <div class="lnb_tree" style="background:#FFF;border: 1px">
			    	<%@ include file="/WEB-INF/jsp/egovframework/com/uss/omt/cmm/EgovLnbTree.jsp" %>
			    </div>
            </div>
        </div>
		<div id="detailInfo">
	        <div class="display_tableCell_middle">
				<%@ include file="/WEB-INF/jsp/egovframework/com/uss/omt/EgovUserListByDept.jsp" %>
	        </div>
        </div>
    </div>  
</div>
<div id="shadow_org"></div>
<body>

</body>
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
$(function(){
    $('#button_select').click(function() {
        var list = getSelectedList();
        if (list.length == 0) {
            alert("<spring:message code="common.msg.select.add"/>");
            return;
        }
        if (opener) {
            opener.setValue(list[0], false);
            close();
        } else if (parent) {
        	parent.setValue(list[0], false);
        	close();
        }
    });
    $(".folder, .file").click(function(){
    	if($(this).attr("nm") == "ROOT"){
    		return false;
    	}
        var id = $(this).attr("id");
		var url = "<c:url value='/userList.do'/>";
		$("#detailInfo").load(url, {action: "getUserListByDept", checkType: "single", deptId: id}, function(data) {
			$(this).html(data).trigger("create");
		});
    });
})
function close(){
	$("#div_popup").hide();
}
function getSelectedList() {
    var objs = [];
    $('input[type=checkbox][name=userCheck]:checked').each(function() {
        var rowid = $(this).val();
        var datastr = $(['#ROW', rowid].join("_")).attr("data");
        var inst = toInstance(datastr);
        objs.push(inst);
    });
    return objs;
}
//]]>
</script>
</html>