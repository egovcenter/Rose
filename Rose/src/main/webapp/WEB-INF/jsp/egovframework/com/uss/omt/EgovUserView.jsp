<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script src="<c:url value='/js/egovframework/com/uss/omt/orgCommon.js'/>"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jquery-1.11.3.js'/>"></script>
<script>
$(function() {
    $('#button_list').click(function() {
    	document.aForm.action = "<c:url value='/userList.do'/>";
    	document.aForm.submit(); 
    });
    $('#button_modify').click(function() {
		 document.aForm.action = "<c:url value='/userForm.do'/>";
		 document.aForm.submit();
    });
    $('#button_delete').click(function() {
    	var userId = "${selectedUser.uniqId}";
		if (confirm("<spring:message code="common.msg.confim.delete"/>")) {
            var formURL = "userDelete.do";
            $.ajax(
            {
               url : formURL,
               type: "POST",
               data : {userId: userId},
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
});
function getIdList(userInfoList) {
	var idList = [];
	
	for (i = 0; i < userInfoList.length; i++) {
		idList.push(userInfoList[i].id);
	}
	return idList.toString();
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
		<!-- Input Form start -->
	    <form name="aForm" id="ajaxform" action="" method="post" onsubmit="return false;">
  			<input type="hidden" id="deptId" name="deptId" value="${dept.deptId}" class="deptId">
  			<input type="hidden" id="userId" name="userId" value="${selectedUser.uniqId}" class="userId">
  			<input type="hidden" id="checkType" name="checkType" value="multi">
  			
  			<div class="ui_btn_rapper float_right">
                <a href="#" class="btn_color3" id="button_list"><spring:message code="common.button.list"/></a>
                <a href="#" class="btn_color3" id="button_modify"><spring:message code="common.button.modify"/></a>
                <a href="#" class="btn_color3" id="button_delete"><spring:message code="common.button.delete"/></a>
         	</div>
		    
		     <p class="tab_title float_left"><spring:message code="org.user.label.view"/></p>
		       <div class="display_table clear_Both mtb10">
		           <div class="display_tableCell_top vAlign_top" style="width: 120px">
		               <div class="ru_cont1 mr10">
		                   <p class="photo_rapper"><img src="downloadImg.jsp?FN=${selectedUser.orgnztId}${selectedUser.uniqId}&FT=photo" id="IMAGE_PHOTO" alt="photo"/></p>
		               </div>
		               <div class="ru_cont2 mr10">
		                   <p class="mb10"><spring:message code="org.user.label.signature"/></p>
		                   <p><img src="downloadImg.jsp?FN=${selectedUser.orgnztId}${selectedUser.uniqId}&FT=sign" id="IMAGE_SIGN" alt="sign"  style="border:1px solid #cdcdcd" /></p>
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
		                                   <div class="ui_input_text cursorDefault">
		                                       <input type="text" value="<c:out value="${selectedUser.emplyrNm}"/>" id="USER_NM" maxlength="30" readonly/>
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
		                               <td colspan="2">
		                                   <div class="ui_input_text cursorDefault">
		                                       <input type="text" value="<c:out value="${dept.deptNm}"/>" id="DEPT_NM" readonly="readonly"/>
		                                   </div>
		                               </td>           
		                           </tr>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.empID"/></th>
		                               <td colspan="2">
		                                   <div class="ui_input_text cursorDefault">
		                                       <input type="text" value="<c:out value="${selectedUser.emplNo}"/>" id="USER_EM_CD" maxlength="30" readonly/>
		                                   </div>
		                               </td>           
		                           </tr>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.position"/></th>
		                               <td colspan="2">
	                                        <div class="ui_input_text cursorDefault">
	                                            <input type="text" value="<c:out value="${selectedUser.positionNm}"/>" id="POSI_NM" readonly />
	                                        </div>
		                               </td>           
		                           </tr>
		                           <c:if test="${user.userType ne 'S' and selectedUser.userType ne 'A'}">
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.duty"/></th>
		                               <td colspan="2">
	                                        <div class="ui_input_text cursorDefault">
	                                            <input type="text" value="<c:out value="${selectedUser.dutyNm}"/>" id="DUTY_NM" readonly />
	                                        </div>
		                               </td>           
		                           </tr>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.securitylevel"/></th>
		                               <td colspan="2">
		                                   <div class="ui_input_text cursorDefault">
		                                       <input type="text" value="<c:out value="${selectedUser.securityLvl}"/>" id="USER_SLVL" maxlength="3" readonly/>
		                                   </div>
		                               </td>           
		                           </tr>
		                           </c:if>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.id"/></th>
		                               <td colspan="2">
	                                        <div class="ui_input_text cursorDefault">
	                                            <input type="text" value="<c:out value="${selectedUser.emplyrId}"/>" id="USER_LOGIN" readonly />
	                                        </div>
		                               </td>           
		                           </tr>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.email"/></th>
		                               <td colspan="2">
	                                       <div class="ui_input_text cursorDefault">
	                                           <input type="text" value="<c:out value="${selectedUser.emailAdres}"/>" id="USER_EMAIL" readonly />
	                                       </div>
		                               </td>           
		                           </tr>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.tel"/></th>
		                               <td colspan="2">
                                            <div class="ui_input_text cursorDefault">
	                                            <input type="text" value="<c:out value="${selectedUser.offmTelno}"/>" id="USER_TEL" readonly />
	                                        </div>
		                               </td>           
		                           </tr>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.fax"/></th>
		                               <td colspan="2">
	                                       <div class="ui_input_text cursorDefault">
	                                           <input type="text" value="<c:out value="${selectedUser.fxnum}"/>" id="USER_FAX"readonly />
	                                       </div>
		                               </td>           
		                           </tr>
		                           <tr>
		                               <th scope="row"><spring:message code="org.user.label.mobile"/></th>
		                               <td colspan="2">
	                                        <div class="ui_input_text cursorDefault">
	                                            <input type="text" value="<c:out value="${selectedUser.moblphonNo}"/>" id="USER_MOBILE" readonly />
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
	                        <p class="Description_cont">
	                            <c:out value="${selectedUser.remark}"/>
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
	<!-- Content box start -->
	<div class="content_box">
		<iframe id="content_box" style="display: none"></iframe>
	</div>
	<!-- Content box end --> 
    </div>
    <!-- Container end --> 
    <div class="clear"></div>
</div>
<div id="shadow" class="shadow" style="display: none;"></div>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCopyright.jsp" %>
</body>
</html>