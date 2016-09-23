<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="layerPopup_rapper" style="width: 600px; position:absolute;">
	<p class="popup_title type_approval"><spring:message code="appvl.selectlabel.title"/></p>
	<div class="ui_btn_rapper float_right mtb10">
		<a href="javascript:applyLabel()" class="btn_color1"><spring:message code="common.button.apply"/></a>
		<a href="javascript:closeLabelPopup()" class="btn_color2"><spring:message code="common.button.cancel"/></a>
	</div>
	<div class="table_rapper">
		<table summary="" class="labelboard_width_borderNone">
			<caption class="blind"></caption>
			<colgroup>
				<col width="110px"/>
				<col width="*"/>
				<col width="85px"/>
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" style="padding-top: 12px;"><label for=""><spring:message code="appvl.selectlabel.label.Labels"/></label></th>
					<td colspan="2">
						<div  style="height:182px; overflow:auto; -webkit-overflow-scrolling: touch;">
							<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="labelboard_type_height mb20">
							<caption class="blind"></caption>
							<colgroup>
								<col width="*"/>
							</colgroup>
							<thead>
								<tr>
									<th scope="col" class=""><span><spring:message code="appvl.selectlabel.label.table.labelname"/></span></th>
								</tr>
							</thead>					
							<tbody class="labelTBody">
							<c:if test="${not empty listLabel}">
								<c:forEach var="labels" items="${listLabel}" varStatus="status">
								<tr onclick='javascript:select_content(this)' class='labelSelect <c:out value="${labels.labelId}"/>' id="<c:out value="${labels.labelId}"/>" nm="<c:out value="${labels.labelNm}"/>" child='<c:out value="${labels.childLabel}"/>' style='display: ;'>
									<td>
										<c:choose>
											<c:when test="${labels.childLabel ne '1'}"><div class="label_name td_<c:out value="${labels.labelId}"/>">&nbsp;&nbsp;&nbsp;</c:when>
											<c:otherwise><div class="label_name td_<c:out value="${labels.labelId}"/>" style="font-weight: bold;"></c:otherwise>
										</c:choose>
										<c:out value="${labels.labelNm}"/></div>
									</td>
								</tr>
								</c:forEach>
							</c:if>
							</tbody>
						</table>
						</div>
					</td>
				</tr>
				
				<tr>
					<th scope="row"><label for=""></label></th>
					<td colspan="2">
						<div class="solt_width">							
							<a href="#" class="labelDown"><img src="<c:url value='/images/egovframework/com/uss/cmm/ico_down.png'/>" alt="ico_down"></a>
							<a href="#" class="labelUp"><img src="<c:url value='/images/egovframework/com/uss/cmm/ico_up.png'/>" alt="ico_up"></a>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row" style="padding-top: 12px;"><label for=""><spring:message code="appvl.selectlabel.label.selectedlabel"/></label></th>
					<td colspan="2">
						<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="labelboard_type_height mb20" id="seletedLabel">
							<caption class="blind"></caption>
							<colgroup>
								<col width=""/>
							</colgroup>
							<thead>
								<tr>
									<th scope="col" class=""><span><spring:message code="appvl.selectlabel.label.table.label"/></span></th>
								</tr>
							</thead>					
							<tbody class="attachLabel">
							</tbody>
						</table>
					</td>
				</tr>				
			</tbody>
		</table>
	</div>
<form action="">
<input type="hidden" value="" id="selectedLabelID">
<input type="hidden" value="" id="selectedLabelNm">
<input type="hidden" value="" id="applyLabelID">
<input type="hidden" value="" id="applyLabelNm">
</form>
</div>
<div id="shadow_popup"></div>
</body>
<script language="javascript" type="text/javascript"> 
//<![CDATA[
var appvl_draft_nolabel = "<spring:message code="appvl.draft.nolabel"/>";

function select_label_load(){
	$("#shadow_popup").css(
		{
			width:$(document).width(),
			height:$(document).height(),
			opacity:0.5
		}
	);

	$(window).resize(function(){
		$("#shadow_popup").css(
			{
				width:$(window).width(),
				height:$(document).height(),
				opacity:0.5
			}
		);
	});
	
	$(".layerPopup_rapper").css(
		{
			left:"50%",
			top:"15%",	
			marginLeft:-parseInt($(".layerPopup_rapper").css("width"))/2+"px",
			marginTop:-parseInt($(".layerPopup_rapper").css("height"))/2+"px"
		}
	);
}

function closeLabelPopup(){
	$("#div_popup").hide();
}

function onChangeColor(obj){
	$(obj).css('background-color', '#f3f3f3');
}

$(".labelDown").click(function(){
	var labelID = document.getElementById("selectedLabelID").value;
	var labelNm = document.getElementById("selectedLabelNm").value
	var applyLabelID = document.getElementById("applyLabelID").value;
	var applyLabelNm = document.getElementById("applyLabelNm").value;

	
	if(labelID != ''){
		document.getElementById("applyLabelID").value = labelID;
		document.getElementById("applyLabelNm").value = labelNm;
		
		/*초기화*/
		var relabelSelect  = ".relabelSelect";
		if(relabelSelect != "."){
			var labelUp = $('.attachLabel').find(relabelSelect);
			$('.labelTBody').find('.label_name').css('color', '#323a45');
			$(labelUp).remove();
		}
		
		/* 선택한 라벨 강조 */
		var labelSelect  = ".td_"+labelID;
		$('.labelTBody').find(labelSelect).css('color', '#fb6565');
		
		var labelClass = "."+labelID;
		var reLabelClass = $(labelClass).clone().appendTo($(".attachLabel")).removeClass();
		reLabelClass.css('background-color', '#fff');
		$('.attachLabel').find('.label_name').css('color', '#323a45');
		reLabelClass.toggleClass("relabelSelect");
		reLabelClass.toggleClass(labelID);
		reLabelClass.attr('onclick','selectedLabel(this)').unbind('click'); 
		document.getElementById("selectedLabelID").value = "";
		document.getElementById("selectedLabelNm").value = "";
	}
})

function selectedLabel(obj){
	$(".labelSelect").css('background-color', '#fff');
	$(".relabelSelect").css('background-color', '#fff');
	var backColor = $(obj).css( "background-color" );
	if(backColor == "rgb(255, 255, 255)"){
		$(obj).css('background-color', '#f3f3f3');
		<c:choose>
			<c:when test="${not empty param.selectedLabelID and not empty param.selectedLabelNm}">
			$("#<c:out value="${param.selectedLabelID}"/>").val($(obj).attr("id"));
			$("#<c:out value="${param.selectedLabelNm}"/>").val($(obj).attr("nm"));
			</c:when>
			<c:otherwise>
			document.getElementById("selectedLabelID").value = $(obj).attr("id");
			document.getElementById("selectedLabelNm").value = $(obj).attr("nm");
			</c:otherwise>
		</c:choose>
	}else {
		$(obj).css('background-color', '#f3f3f3');
	}
}

$(".labelUp").click(function(){
	var labelID = document.getElementById("selectedLabelID").value;
	var labelNm = document.getElementById("selectedLabelNm").value
	var applyLabelID = document.getElementById("applyLabelID").value;
	var applyLabelNm = document.getElementById("applyLabelNm").value;

	var labelSelect  = ".td_"+labelID;
	$('.labelTBody').find(labelSelect).css('color', '#323a45');
	
	if(labelID != ''){
		var labelClass = "."+labelID;
		var labelUp =  $('.attachLabel').find(labelClass);
		$(labelUp).remove();
		$(labelClass).attr("style", "display: ");
		document.getElementById("selectedLabelID").value = "";
		document.getElementById("selectedLabelNm").value = "";
		document.getElementById("applyLabelID").value = "";
		document.getElementById("applyLabelNm").value = "";
	}
})

function applyLabel() {
	var applyLabelID = document.getElementById("applyLabelID").value;
	var applyLabelNm = document.getElementById("applyLabelNm").value;
	
	if(applyLabelID == '' && applyLabelNm == ''){
		alert(appvl_draft_nolabel);
		return false;
	}
	if($("#selectlabelNm").length == 0){
		document.getElementById("register_selectlabelNm").value = applyLabelNm;
		document.getElementById("register_selectlabelId").value = applyLabelID;
	}else{
		document.getElementById("selectlabelNm").value = applyLabelNm;
		document.getElementById("selectlabelId").value = applyLabelID;
	}
	closeLabelPopup();
}

function select_content(obj){
	$(".relabelSelect").css('background-color', '#fff');
	var child  =  $(obj).attr("child");
	if(child == '0'){
		$(".labelSelect").css('background-color', '#fff');
		var backColor = $(obj).css( "background-color" );
		if(backColor == "rgb(243, 243, 243)"){
			$(obj).css('background-color', '#fff');
		}else {
			$(obj).css('background-color', '#f3f3f3');
			document.getElementById("selectedLabelID").value = $(obj).attr("id");
			document.getElementById("selectedLabelNm").value = $(obj).attr("nm");
		}
	}
}

function select_labelDepth(){
	 $(".labelSelect[child='1']").css('font-weight', 'bold');
	 $(".labelSelect[child='0']").css('cursor', 'pointer');
}
 
//]]>
</script>