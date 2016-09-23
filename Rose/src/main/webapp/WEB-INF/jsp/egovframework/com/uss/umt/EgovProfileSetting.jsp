<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="utf-8">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script src="<c:url value='/js/egovframework/com/uss/omt/orgCommon.js'/>"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jquery-1.11.3.js'/>"></script>
<script>
var CONTEXT = "${pageContext.servletContext.contextPath}";
var env = {
	/* 업로드 이미지로 교체될 img 태그의 ID */
	uploadTarget: null,
	/* 업로드 이미지 경로를 전달할 hidden element의 name */
	uploadPath: null
}
$(function() {
    $('#button_cancel').click(function() {
    	history.back();
	});   
    
    $('#button_photoEdit').click(function() {
        env.uploadTarget = "IMAGE_PHOTO";
        env.uploadPath = "PHOTO_PATH";
        showPopupUpload();

    });
    
    $('#button_signEdit').click(function() {
        env.uploadTarget = "IMAGE_SIGN";
        env.uploadPath = "SIGN_PATH";
        showPopupUpload();
    });

    $("#IMAGE_PHOTO, #IMAGE_SIGN").load(function() {
    	var w = $(this).css("width").replace("px", "");
    	var h = $(this).css("height").replace("px", "");
    	console.log([w, h].join("x"));
    	$(this).css({
    		width: 106,
    		height: 106
    	});
    });
});
function formMapperSubmit() {
    $('input:hidden[name=USER_EMAIL]').val($('#USER_EMAIL').val());
    $('input:hidden[name=USER_TEL]').val($('#USER_TEL').val());
    $('input:hidden[name=USER_FAX]').val($('#USER_FAX').val());
    $('input:hidden[name=USER_MOBILE]').val($('#USER_MOBILE').val());
    $('input:hidden[name=USER_RMRK]').val($('#USER_RMRK').val());
}
function apply() {
	if (! confirm("<spring:message code="common.msg.confim.save"/>")) {
		return;
	}
    formMapperSubmit();
	document.profileForm.submit();
}
function setImage(obj) {
	if (obj) {
		var dirpath = ["temp", obj.savedFilename].join("/");
		var path = obj.savedFilename;
		$('#' + env.uploadTarget).attr("src", dirpath);
		$('input:hidden[name=' + env.uploadPath + ']').val(path);
	}
}
function showPopupUpload() {
    $("#content_box").each(function() {
        $(this).attr("src", "upload.do");
        $(this).attr("width", 440);
        $(this).attr("height", 200);
    });
    $(".div_upload").each(function() {
        $(this).css({
            left: "50%",
            top: "50%",
            width: 440,
            height: 200,
            marginLeft:-parseInt($(".div_upload").css("width"))/2+"px",
            marginTop:-parseInt($(".div_upload").css("height"))/2+"px"
        });
        $(this).show();
    });
    $("#shadow_org").each(function() {
        $(this).css({
            width: $(document).width() + "px",
            height: $(document).height() + "px",
            opacity: 0.5
        });
        $(this).show();
    });
}
function closeModal() {
    $(".div_upload").hide();
    $("#shadow_org").hide();
}
</script>
</head>
<body>
<div class="wrap">
	<form name="profileForm" id="profileForm" method="post" action="${pageContext.servletContext.contextPath}/profileSave.do">
	   <input type="hidden" name="USER_EMAIL"/>
	   <input type="hidden" name="USER_TEL"/>
	   <input type="hidden" name="USER_FAX"/>
	   <input type="hidden" name="USER_MOBILE"/>
	   <input type="hidden" name="USER_RMRK"/>
	   <!-- image upload -->
	   <input type="hidden" name="PHOTO_PATH"/>
	   <!-- sign upload -->
	   <input type="hidden" name="SIGN_PATH"/>

	<!--  Top Menu Start --> 
	<jsp:include page="/WEB-INF/jsp/egovframework/com/cmm/EgovTopMenu.jsp" flush="false">
		<jsp:param value="HOME" name="selectedMenu"/>
	</jsp:include>
	<!--  Top Menu End--> 
	<div class="clear"></div>
	<!-- Container Start -->
	<div class="Container"> 
	<!-- Lnb start-->
	<jsp:include page="/WEB-INF/jsp/egovframework/com/uss/umt/EgovLeftMenu.jsp" flush="false">
		<jsp:param value="profile" name="selectedMenu"/>
	</jsp:include>
	<!-- Lnb end -->
		<!-- Content start-->
		<div class="Content">
			<!-- Content box Start -->
			<div class="content_box"> 
				<!-- Menu Title start -->
				<div class="title_box">
					<div class="title_line">
						<h1>Profile</h1>						
					</div>	
					<div class="clear"></div>				
				</div>
				<!-- Menu Title end-->
				<!-- profile start -->
	  			<div class="ui_btn_rapper float_right">
             		<a href="javascript:apply();" class="btn_color3" id="button_apply"><spring:message code="common.button.apply"/></a>
             		<a href="#" class="btn_color2" id="button_cancel"><spring:message code="common.button.cancel"/></a>
         		</div>
         		<div class="h36"></div>	
  			    <div class="display_table clear_Both mtb10">
		           <div class="display_tableCell vAlign_top" style="width: 100px">
		               <div class="ru_cont1 mr10">
		               	   <p class="mb10"><strong><spring:message code="org.user.label.photo"/></strong></p>
		                   <p class="photo_rapper"><img src="downloadImg.jsp?FN=${user.orgnztId}${user.uniqId}&FT=photo" id="IMAGE_PHOTO" alt="photo"/></p>
		                   <div class="ui_btn_rapper align_right mt5">
		                       <a href="#" class="btn_color3" id="button_photoEdit"><spring:message code="common.button.edit"/></a>
		                   </div>
		               </div>
		               <div class="ru_cont2 mr10">
		                   <p class="mb10"><strong><spring:message code="org.user.label.signature"/></strong></p>
		                   <p><img src="downloadImg.jsp?FN=${user.orgnztId}${user.uniqId}&FT=sign" id="IMAGE_SIGN" alt="sign"  style="border:1px solid #cdcdcd" /></p>
		                   <div class="ui_btn_rapper align_right mt5">
		                       <a href="#" class="btn_color3" id="button_signEdit"><spring:message code="common.button.edit"/></a>
		                   </div>
		               </div>              
		           </div>
		           <div class="display_tableCell_top vAlign_top">
		               <div>
		                   <table summary="" class="board_width_borderNone">
		                       <caption class="blind"></caption>
		                       <colgroup>
	                                <col width="20px"/>
	                                <col width="100px"/>
	                                <col width="*"/>
	                                <col width="5px"/>
		                       </colgroup>                         
		                       <tbody>
		                           <tr>
		                           	   <td rowspan="5"></td>
		                               <th scope="row"><spring:message code="org.user.label.email"/></th>
		                               <td colspan="2">
		                                   <div class="ui_input_text">
		                                       <input type="text" value="${user.emailAdres}" id="USER_EMAIL" maxlength="30"/>
		                                   </div>
		                               </td>           
		                           </tr>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.tel"/></th>
		                               <td colspan="2">
		                                   <div class="ui_input_text">
		                                       <input type="text" value="${user.offmTelno}" id="USER_TEL" maxlength="30"/>
		                                   </div>
		                               </td>           
		                           </tr>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.fax"/></th>
		                               <td colspan="2">
		                                   <div class="ui_input_text">
		                                       <input type="text" value="${user.fxnum}" id="USER_FAX" maxlength="30"/>
		                                   </div>
		                               </td>           
		                           </tr>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.mobile"/></th>
		                               <td colspan="2">
		                                   <div class="ui_input_text">
		                                       <input type="text" value="${user.moblphonNo}" id="USER_MOBILE" maxlength="30"/>
		                                   </div>
		                               </td>           
		                           </tr>
		                           <tr>
		                               <th scope="row"><spring:message code="common.label.description"/></th>
		                               <td colspan="2">
						                   <p class="Description_cont" style="text-align:center; overflow:hidden;">
						                       <textarea cols="40" rows="18" id="USER_RMRK" style="resize:none;  padding:2px; width:98%;">${user.remark}</textarea>
						                   </p>
		                               </td>           
		                           </tr>
		                       </tbody>
		                   </table>
		               </div>
		           </div>
		       </div>
			   <!-- profile end -->
			</div>
			<!-- content_box End -->
		</div>
		<!-- Content End --> 
	</div>
	<!-- Container End -->
	<div class="clear"></div>
</form>
</div>
<!-- wrap End -->
<div id="shadow_org" class="shadow_org" style="display: none;"></div>
<div id="div_popup" style="display: none"></div>
<div class="div_upload" style="display: none; position:absolute !important; right:20px; left:inherit !important; top:200px; margin:0 !important;">
	<iframe id="content_box" name="content_box" src=""></iframe>
</div>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCopyright.jsp" %>
</body>
</html>