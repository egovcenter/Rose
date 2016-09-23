<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- formSelectPopup Start -->
<div id="formSelect" style="display: none;" >
	<div class="layerPopup_rapper">
		<p class="popup_title type_approval"><spring:message code="appvl.selectform.title"/></p>
		<div class="ui_btn_rapper float_right mtb10">
			<a href="#" class="btn_color2 form_close"><spring:message code="common.button.cancel"/></a>
		</div>
		<div class="rapper_table" style="height:330px; overflow:auto;  -webkit-overflow-scrolling: touch;">
			<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="labelboard_type_height">
				<caption class="blind"></caption>
				<colgroup>
					<col width=""/>
					<col width=""/>
					<col width=""/>
				</colgroup>
				<thead>
					<tr>					
						<th scope="col" class="align_left"><span><spring:message code="appvl.selectform.label.table.type"/></span></th>
						<th scope="col" class="align_left"><span><spring:message code="appvl.selectform.label.table.formname"/></span></th>
						<th scope="col" class="align_left"><span><spring:message code="appvl.selectform.label.table.description"/></span></th>
					</tr>
				</thead>
				<tbody class="fromLabel" />
			</table>
		</div>
	</div>
</div>
<div id="shadow" class="shadow" style="display: none;"></div>
<!-- formSelectPopup End -->
<script language="javascript" type="text/javascript"> 
//<![CDATA[
$( document ).ready(function() {
	$("#shadow").css(
		{
			width:$(document).width()+30,
			height:$(document).height()+48,
			opacity:0.5
		}
	);
	$(window).resize(function(){
		$("#shadow").css(
			{
				width:$(document).width()+30,
				height:$(document).height()+48,
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
	$("#layerSelect").css(
		{
			left:"50%",
			top:"50%",		
			marginLeft:-parseInt($(".layerPopup_rapper").css("width"))/2+"px",
			marginTop:-parseInt($(".layerPopup_rapper").css("height"))/2+"px"
		}
	)
});
 
$(".form_close").click(function(){
	document.getElementById("formSelect").style.display = "none";
	$("#shadow, .shadow").hide();
})

function selectForm(objTR){
	var selectformID = $("input[name=formId]", objTR).val();
	var selectformType = $("input[name=formType]", objTR).val();
	document.getElementById("selectformID").value = selectformID;
	document.getElementById("selectformType").value = selectformType;
	
	var isincoming = $("input[name=isincoming]", "#listForm").val();
	var orgdocid = $("input[name=orgdocId]", "#listForm").val();
	if(isincoming != '' && orgdocid != ''){
		document.listForm.action = "<c:url value='/approvalReceive.do'/>";
	}else{
		document.listForm.action = "<c:url value='/approvalDraft.do'/>";
	}
	document.listForm.submit();
	
}

function onChangeColor(obj){
	$(obj).css('background-color', '#f3f3f3');
}
//]]>
</script>