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
	/* 업로드 이미지로 교체될 img 태그의 ID */
	uploadTarget: null,
	/* 업로드 이미지 경로를 전달할 hidden element의 name */
	uploadPath: null,
	message: "${message}",
	isAdd: false
}
var MIN_PASSWORD_LENGTH = 8;
function formMapperOnload() {
    $('input:hidden[name=DEPT_ID]').val("<c:out value="${dept.deptId}" />");
    $('input:hidden[name=USER_ID]').val("<c:out value="${selectedUser.uniqId}" />");
    $('input:hidden[name=USER_ABS_F]').val("<c:out value="${selectedUser.userAbsF}" />");
    
    $('#DEPT_NM').val("<c:out value="${dept.deptNm}" />");
    $('#USER_NM').val("<c:out value="${selectedUser.emplyrNm}" />");
    $('#USER_SEQ').val("<c:out value="${selectedUser.seq}" />");
    $('#USER_EM_CD').val("<c:out value="${selectedUser.emplNo}" />");
    if("${selectedUser.userType}" != "A" && "${user.userType}" != "S"){
	    $('#POSI_ID').val("<c:out value="${selectedUser.positionId}" />");
	    $('#USER_SLVL').val("<c:out value="${selectedUser.securityLvl}" />");
    }
    $('#DUTY_ID').val("<c:out value="${selectedUser.dutyId}" />");
    $('#USER_LOGIN').val("<c:out value="${selectedUser.emplyrId}" />");
    $('#USER_EMAIL').val("<c:out value="${selectedUser.emailAdres}" />");
    $('#USER_TEL').val("<c:out value="${selectedUser.offmTelno}" />");
    $('#USER_FAX').val("<c:out value="${selectedUser.fxnum}" />");
    $('#USER_MOBILE').val("<c:out value="${selectedUser.moblphonNo}" />");
    $('#USER_RMRK').val("<c:out value="${selectedUser.remark}" />");
    
    //신규등록 (userId가 null)
    //파라메터로 전달된 부서정보 출력
    //직책, 직위 초기값
    var userId = "<c:out value="${selectedUser.uniqId}"/>";
    var deptId = "${param.deptId}";
    
    if(userId == "" || userId =="undefined"){
    	$('#DEPT_ID').val("${param.deptId}");
    	env.isAdd = true;
    }
}
function formMapperSubmit() {
    $('input:hidden[name=USER_EM_CD]').val($('#USER_EM_CD').val());
    $('input:hidden[name=USER_NM]').val($('#USER_NM').val());
    if("${selectedUser.userType}" == "A" || "${user.userType}" == "S"){
    	$('input:hidden[name=POSI_ID]').val('POSI_0000000000000');
	    $('input:hidden[name=USER_SLVL]').val("0");
    }else{
    	$('input:hidden[name=POSI_ID]').val($('#POSI_ID option:selected').val());
	    $('input:hidden[name=USER_SLVL]').val($('#USER_SLVL').val());
    }
    var DUTY_ID = $('#DUTY_ID option:selected').val();
    if(DUTY_ID =='< Blank >'){DUTY_ID='';}
    $('input:hidden[name=DUTY_ID]').val(DUTY_ID);
	
    $('input:hidden[name=USER_LOGIN]').val($('#USER_LOGIN').val());
    $('input:hidden[name=USER_LPWD]').val($('#USER_LPWD').val());
    $('input:hidden[name=USER_SPWD]').val($('#USER_SPWD').val());
    $('input:hidden[name=USER_EMAIL]').val($('#USER_EMAIL').val());
    $('input:hidden[name=USER_TEL]').val($('#USER_TEL').val());
    $('input:hidden[name=USER_FAX]').val($('#USER_FAX').val());
    $('input:hidden[name=USER_MOBILE]').val($('#USER_MOBILE').val());
    $('input:hidden[name=USER_RMRK]').val($('#USER_RMRK').val());
}
function checkNullValue(id, msg) {
    if ($.trim($('#' + id).val()) == "") {
        $('#' + id).focus();
        alert(msg);
        return true;
    }
    return false;
}
function checkNumber(id, msg) {
    var val = $('#' + id).val(); 
    if (isNaN(val)) {
        $('#' + id).focus();
        alert(msg);
        return true;
    }
    return false;
}
function checkNumberRange(id, min, max, msg) {
    var val = parseInt($('#' + id).val()); 
    if (val < min || val > max) {
        $('#' + id).focus();
        alert(msg);
        return true;
    }
    return false;
}
function checkNullSelect(id, msg) {
	var val = $("#" + id + " option:selected").val();
	if (val == undefined || val == "") {
		alert(msg);
		return true;
	}
	return false;
}
function checkPasswordLength(id, isAdd, msg) {
	var val = $("#" + id).val();
	
	if (isAdd) {
		if (val.length >= MIN_PASSWORD_LENGTH) {
			return false;
		}
	} else {
		if (val == undefined || val == "" || val.length >= MIN_PASSWORD_LENGTH) {
			return false;
		}
	}
	
	alert(msg);
	return true;
}

function checkDuplicateValue(id, msg, callback) {
	var val = $('#' + id).val();
	$.ajax({
		type: "POST",
		url: "userCheckLoginId.do",
		data: {USER_LOGIN: val},
		success: function(data) {
			var val = data.trim();
			if (val == "exist") {
				$("#" + id).focus();
				alert(msg);
			} else {
				callback();
			}
		},
		error: function(e) {
			alert("id check error!!");
		}
	});
}
function checkForm() {
    if (checkNullValue('USER_NM'    , "<spring:message code="org.user.msg.001"/>"))      return false;
    if (checkNullValue('DEPT_NM'    , "<spring:message code="org.user.msg.002"/>"))     return false;
    if("${selectedUser.userType}" != "A" && "${user.userType}" != "S"){
   		if (checkNullSelect('POSI_ID' , "<spring:message code="org.user.msg.003"/>"))       return false;
    }
    if (checkNullValue('USER_LOGIN' , "<spring:message code="org.user.msg.004"/>"))       return false;
   
    /* 등록일 때만 필수사항 */
    if (env.isAdd) {
	    if (checkNullValue('USER_LPWD'  , "<spring:message code="org.user.msg.005"/>")) return false;
    }
    
    if (checkPasswordLength('USER_LPWD', env.isAdd, "<spring:message code="org.login.msg.005"/>")) return false;

    return true;
}
function doSubmit() {
	if (! confirm("<spring:message code="common.msg.confim.save"/>")) {
		return;
	}
	
    formMapperSubmit();
	var aForm = document.aForm;
	aForm.action = "<c:url value='/userSave.do'/>";
	aForm.method = "POST";
	aForm.submit();
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
$(function() {
    formMapperOnload();
    
	$('#button_select_location').click(function() {
		$("body").css("overflow","");
		$("#div_popup").load(CONTEXT+'/deptSelectionModal.do', {},
				function(){
					if (typeof(select_modal_load) != "undefined") {
						select_modal_load();
					}
					$("#div_popup").show();
				}
		);
	});
        
    if (env.message != "") {
    	alert(env.message);
    }
    
    /* 일반사용자에 적용할 직위 정보가 없을 경우 경고 메시지 표시 */
    if("${selectedUser.userType}" != "A" && "${user.userType}" != "S"){
	    if ($("#POSI_ID option").size() == 0) {
	    	alert(["<spring:message code="org.position.msg.005"/>"].join("\r\n"));
	    }
    }
    
    var deptId = "${selectedUser.orgnztId}";
    
    //보안등급 (1~10)
    for(var i = 1; i <= 10; i++){
        if(i == '${selectedUser.securityLvl}'){
               	  $('#USER_SLVL').append('<option value="'+ i +'" selected >' + i + '</option>');
        }else {
               	  $('#USER_SLVL').append('<option value="'+ i +'"  >' + i + '</option>');
        }
   	  }
    
    $('#checkBoxId').change(function(){
        
        if($('#checkBoxId').is(':checked')){
            //same as login password
            $('#USER_SPWD').val($('#USER_LPWD').val());
            
        }else{
            $('#USER_SPWD').val('');
        }
    });
    
    $('#button_cancel').click(function() {
        if ("${param.retUrl}") {
            location.href = "${param.retUrl}";        	
        } else {
	        history.back();
	    }
        return false;
	});
    
    //직급변경에 따른 비밀등급 셋팅
    $('#POSI_ID').change(function(){
        
        $('#USER_SLVL').val($(this).find('option:selected').data('id'));
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
    	console.log("loaded!!!");
    	var w = $(this).css("width").replace("px", "");
    	var h = $(this).css("height").replace("px", "");
    	console.log([w, h].join("x"));
    	$(this).css({
    		width: 106,
    		height: 106
    	});
    });
    
    if (env.isAdd) {
    	;
    } else {
    	$("#USER_LOGIN").attr("readonly", true);
    }
    
    /* Security Level 사용자 입력이 불가능하게 readonly 설정. Position 선택에 따라 자동 입력 */
    $("#USER_SLVL").attr("readonly", true);
    
    $("#USER_SPWD").keydown(function() {
    	if ($("#checkBoxId").is(":checked")) {
    		$("#checkBoxId").attr("checked", false);
    	}
    });
    
});
function setValue(obj, isDepartment) {
	$('input:hidden[name=DEPT_ID]').val(obj.id);
	$('#DEPT_NM').val(obj.name);
	$('input[type=checkbox]').each(function() {
		$(this).show();
	});
}
function userSave(){
	if (checkForm()) {
		if (env.isAdd) {
			checkDuplicateValue('USER_LOGIN' , "<spring:message code="org.user.msg.008"/>", doSubmit);
		} else {
			doSubmit();
		}
	}
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
      <!-- Content_box start -->
      <div class="content_box" id="org_content_box">
        <!-- 공통 타이틀 버튼 라인 -->
        <div class="title_box">
          <div class="title_line">
            <h1><spring:message code="org.organization.label.organzationmng"/></h1>
          </div>		  
          <div class="clear"></div>		  
        </div>
		<c:choose>
		<c:when test="${not empty userId}">
			<!-- IF MODIFY -->
		    <c:set var="formType" value="Modify"></c:set>
		    <c:set var="saveName" value="Apply"></c:set>
		</c:when>
		<c:otherwise>
			<!-- IF ADD -->
		    <c:set var="formType" value="New"></c:set>
		    <c:set var="saveName" value="Apply"></c:set>
		</c:otherwise>
		</c:choose>        
		<!-- Input Form start -->
		   <form name="aForm" id="ajaxform" action="" onsubmit="return false;">
		       <input type="hidden" name="USER_ID"/>
		       <input type="hidden" name="DEPT_ID"/>
		       <input type="hidden" name="USER_EM_CD"/>
		       <input type="hidden" name="USER_NM"/>
		       <input type="hidden" name="POSI_ID"/>
		       <input type="hidden" name="DUTY_ID"/>
		       <input type="hidden" name="USER_SLVL"/>
		       <input type="hidden" name="USER_LOGIN"/>
		       <input type="hidden" name="USER_LPWD"/>
		       <input type="hidden" name="USER_SPWD"/>
		       <input type="hidden" name="USER_ABS_F"/>
		       <input type="hidden" name="USER_STATUS"/>
		       <input type="hidden" name="USER_EMAIL"/>
		       <input type="hidden" name="USER_TEL"/>
		       <input type="hidden" name="USER_FAX"/>
		       <input type="hidden" name="USER_MOBILE"/>
		       <input type="hidden" name="USER_RMRK"/>
		       <input type="hidden" name="USER_LOGIN_F"/>
		       <!-- image upload -->
		       <input type="hidden" name="PHOTO_PATH"/>
		       <!-- sign upload -->
		       <input type="hidden" name="SIGN_PATH"/>
  			
  			<div class="ui_btn_rapper float_right">
             <a href="javascript:userSave()" class="btn_color3" id="button_save"><c:out value="${saveName}"/></a>
             <a href="#" class="btn_color2" id="button_cancel"><spring:message code="common.button.cancel"/></a>
         	</div>
		    
		     <p class="tab_title float_left"><c:out value="${formType}"/> <spring:message code="org.user.label.user"/></p>
		       <div class="display_table clear_Both mtb10">
		           <div class="display_tableCell_top vAlign_top" style="width: 120px">
		               <div class="ru_cont1 mr10">
		                   <p class="photo_rapper"><img src="downloadImg.jsp?FN=${selectedUser.orgnztId}${selectedUser.uniqId}&FT=photo" id="IMAGE_PHOTO" alt="photo"/></p>
		                   <div class="ui_btn_rapper align_right mt5">
		                       <a href="#" class="btn_color3" id="button_photoEdit"><spring:message code="common.button.edit"/></a>
		                   </div>
		               </div>
		               <div class="ru_cont2 mr10">
		                   <p class="mb10"><spring:message code="org.user.label.signature"/></p>
		                   <p><img src="downloadImg.jsp?FN=${selectedUser.orgnztId}${selectedUser.uniqId}&FT=sign" id="IMAGE_SIGN" alt="sign"  style="border:1px solid #cdcdcd" /></p>
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
		                           <col width="140px"/>
		                           <col width="*"/>
		                       </colgroup>                         
		                       <tbody>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.name"/></th>
		                               <td colspan="2">
		                                   <div class="ui_input_text">
		                                       <input type="text" value="" id="USER_NM" maxlength="30"/>
		                                   </div>
		                               </td>           
		                           </tr>
		                           <tr>
		                               <th scope="row">
                   	                	<c:choose>
					                		<c:when test="${user.userType eq 'S' or selectedUser.userType eq 'A'}">
					                			 <spring:message code="org.company.label.company"/>		
					                		</c:when>
					                		<c:otherwise>
					                		 	<spring:message code="org.user.label.department"/>
					                		</c:otherwise>
				                		</c:choose>
		                               </th>
		                               <td>
		                                   <div class="ui_input_text">
		                                       <input type="text" value="" id="DEPT_NM" readonly="readonly"/>
		                                   </div>
		                               </td>
		                               <td>
		                               	   <div class="ui_btn_rapper">
		                               		   <a href="#" id="button_select_location" class="btn_color3"><spring:message code="common.button.select"/></a>
	                               		   </div>
		                               </td>
		                           </tr>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.empID"/></th>
		                               <td colspan="2">
		                                   <div class="ui_input_text">
		                                       <input type="text" value="" id="USER_EM_CD" maxlength="30"/>
		                                   </div>
		                               </td>           
		                           </tr>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.position"/></th>
		                               <td colspan="2">
                   	                	<c:choose>
					                		<c:when test="${user.userType eq 'S' or selectedUser.userType eq 'A'}">
			                                   <div class="ui_input_text">
			                                       <input type="text" value="Admin" id="POSI_ID" readonly="readonly"/>
			                                   </div>
					                		</c:when>
					                		<c:otherwise>
 			                                   <div class="ui_select_rapper">
			                                       <select id="POSI_ID">
			                                           <option></option>
				                                       <c:forEach var="prow" items="${posiList}" varStatus="pstatus">
				                                           <option  data-id="${prow.posiLv}" value="${prow.posiId}" ${prow.posiId == user.positionId?"selected":''}>${prow.posiNm }</option>
				                                       </c:forEach>
			                                       </select>
			                                   </div>
					                		</c:otherwise>
				                		</c:choose>
		                               </td>           
		                           </tr>
		                           <c:if test="${user.userType ne 'S' and selectedUser.userType ne 'A'}">
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.duty"/></th>
		                               <td colspan="2">
		                                   <div class="ui_select_rapper">
		                                       <select id="DUTY_ID">
		                                           <option>&lt; Blank &gt;</option>
		                                       <c:forEach var="drow" items="${dutyList}" varStatus="dstatus">
		                                           <option value="${drow.dutyId}" ${dprow.dutyId == user.dutyId?'selected':''}>${drow.dutyNm }</option>
		                                       </c:forEach>
		                                       </select>
		                                   </div>
		                               </td>           
		                           </tr>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.securitylevel"/></th>
		                               <td colspan="2">
		                                   <div class="ui_input_text">
		                                       <input type="text" value="" id="USER_SLVL" maxlength="3"/>
		                                   </div>
		                               </td>           
		                           </tr>
		                           </c:if>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.id"/></th>
		                               <td colspan="2">
		                                   <div class="ui_input_text">
		                                       <input type="text" value="" id="USER_LOGIN" maxlength="30"/>
		                                   </div>
		                               </td>           
		                           </tr>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.loginpasswd"/></th>
		                               <td colspan="2">
		                                   <div class="ui_input_text">
		                                       <input type="password" value="" id="USER_LPWD" maxlength="30"/>
		                                   </div>
		                               </td>           
		                           </tr>
		                           <c:if test="${user.userType ne 'S' and selectedUser.userType ne 'A'}">
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.appvlpasswd"/></th>
		                               <td colspan="2">
		                                   <div class="ui_input_text">
		                                       <input type="password" value="" id="USER_SPWD" />
								 			   <input type="checkbox" value="" id="checkBoxId" />
		                                   <span><label for="checkBoxId"><spring:message code="org.user.label.sameasloginpasswd"/></label></span>
		                                   </div>
		                               </td>
		                           </tr>
		                           </c:if>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.email"/></th>
		                               <td colspan="2">
		                                   <div class="ui_input_text">
		                                       <input type="text" value="" id="USER_EMAIL" maxlength="30"/>
		                                   </div>
		                               </td>           
		                           </tr>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.tel"/></th>
		                               <td colspan="2">
		                                   <div class="ui_input_text">
		                                       <input type="text" value="" id="USER_TEL" maxlength="30"/>
		                                   </div>
		                               </td>           
		                           </tr>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.fax"/></th>
		                               <td colspan="2">
		                                   <div class="ui_input_text">
		                                       <input type="text" value="" id="USER_FAX" maxlength="30"/>
		                                   </div>
		                               </td>           
		                           </tr>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.mobile"/></th>
		                               <td colspan="2">
		                                   <div class="ui_input_text">
		                                       <input type="text" value="" id="USER_MOBILE" maxlength="30"/>
		                                   </div>
		                               </td>           
		                           </tr>
		                       </tbody>
		                   </table>
		               </div>
		           </div>
		           <div class="display_tableCell_top vAlign_top" style="width: 30%">
		               <div class="pl10 description_rapper">
		                   <p class="Description_title"><spring:message code="common.label.description"/></p>
		                   <p class="Description_cont" style="text-align:center; overflow:hidden;">
		                       <textarea cols="40" rows="18" id="USER_RMRK" value="" style="resize:none;  padding:2px; width:98%; "></textarea>
		                   </p>
		               </div>                  
		           </div>
		       </div>				            
		   </form>    
		<!-- Input Form end -->		       
	   </div>
	<!-- Content_box end --> 
	</div>
    <!-- Content end -->
    </div>
    <!-- Container end --> 
    <div class="clear"></div>
</div>
<div id="shadow_org" class="shadow_org" style="display: none;"></div>
<div id="div_popup" style="display: none"></div>
<div class="div_upload" style="display: none; position:absolute !important; right:20px; left:inherit !important; top:200px; margin:0 !important;">
	<iframe id="content_box" name="content_box" src=""></iframe>
</div>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCopyright.jsp" %>
</body>
</html>