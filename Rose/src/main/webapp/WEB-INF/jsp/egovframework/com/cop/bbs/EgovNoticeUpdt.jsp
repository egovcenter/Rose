<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<%
 /**
  * @Class Name : EgovNoticeUpdt.jsp
  * @Description : 게시물 수정 화면
  * @Modification Information
  * @
  * @  수정일      수정자            수정내용
  * @ -------        --------    ---------------------------
  * @ 2009.03.19   이삼섭          최초 생성
  *   2011.09.15   서준식          유효기간 시작일이 종료일보다 빠른지 체크하는 로직 추가
  *  @author 공통서비스 개발팀 이삼섭
  *  @since 2009.03.19
  *  @version 1.0
  *  @see
  *
  */
%>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>

<link href="<c:url value='${brdMstrVO.tmplatCours}' />" rel="stylesheet" type="text/css">
<script type="text/javascript">
_editor_area = "nttCn";
_editor_url = "<c:url value='/html/egovframework/com/cmm/utl/htmlarea3.0/'/>";
</script>

<script type="text/javascript" src="<c:url value='/html/egovframework/com/cmm/utl/htmlarea3.0/htmlarea.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/cop/bbs/EgovBBSMng.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/cmm/fms/EgovMultiFile.js'/>" ></script>
<script type="text/javascript" src="<c:url value="/validator.do"/>"></script>
<validator:javascript formName="board" staticJavascript="false" xhtml="true" cdata="false"/>
<c:if test="${anonymous == 'true'}"><c:set var="prefix" value="/anonymous"/></c:if>
<script type="text/javascript">
	function fn_egov_validateForm(obj){
		return true;
	}

	function fn_egov_update_noticeList(){
		document.board.onsubmit();


		var ntceBgnde = eval(document.getElementById("ntceBgnde").value);
		var ntceEndde = eval(document.getElementById("ntceEndde").value);


		if(ntceBgnde > ntceEndde){
			alert("게시기간 종료일이 시작일보다 빠릅니다.");
			return;
		}

		if (!validateBoard(document.board)){
			return;
		}

		if (confirm('<spring:message code="common.update.msg" />')) {
			document.board.action = "<c:url value='/cop/bbs${prefix}/updateBoardArticle.do'/>";
			document.board.submit();
		}
	}

	function fn_egov_select_noticeList() {
		document.board.action = "<c:url value='/cop/bbs${prefix}/selectBoardList.do'/>";
		document.board.submit();
	}

	function fn_egov_check_file(flag) {
		if (flag=="Y") {
			document.getElementById('file_upload_posbl').style.display = "block";
			document.getElementById('file_upload_imposbl').style.display = "none";
		} else {
			document.getElementById('file_upload_posbl').style.display = "none";
			document.getElementById('file_upload_imposbl').style.display = "block";
		}
	}
	function makeFileAttachment(){
	<c:if test="${bdMstr.fileAtchPosblAt == 'Y'}">

		var existFileNum = document.board.fileListCnt.value;
		var maxFileNum = document.board.posblAtchFileNumber.value;
		
		if (existFileNum=="undefined" || existFileNum ==null) {
			existFileNum = 0;
		}
		if (maxFileNum=="undefined" || maxFileNum ==null) {
			maxFileNum = 0;
		}
		var uploadableFileNum = maxFileNum - existFileNum;
		if (uploadableFileNum<0) {
			uploadableFileNum = 0;
		}
		if (uploadableFileNum != 0) {
			fn_egov_check_file('Y');
			var multi_selector = new MultiSelector( document.getElementById( 'egovComFileList' ), uploadableFileNum, '${pageContext.request.contextPath}');
			multi_selector.addElement( document.getElementById( 'egovComFileUploader' ) );
		} else {
			fn_egov_check_file('N');
		}
	</c:if>
	}
	/* TBS 2016.02.11 start */
	function fn_egov_register() {
		document.frm.action = "<c:url value='/cop/bbs/addBoardArticle.do'/>";
		document.frm.submit();
	}
	/* TBS 2016.02.11 end */
</script>
</head>

<!-- body onload="javascript:editor_generate('nttCn');"-->
<body onLoad="HTMLArea.init(); HTMLArea.onload = initEditor; document.board.nttSj.focus(); makeFileAttachment();">

<!-- TBS 2016.02.04 start -->
<div class="wrap">
	<!--  Top 메뉴영역 시작 --> 
	<jsp:include page="/WEB-INF/jsp/egovframework/com/cmm/EgovTopMenu.jsp" flush="false">
		<jsp:param value="BBS" name="selectedMenu"/>
	</jsp:include>	
	<!--  Top 메뉴영역 끝 --> 
		<div class="clear"></div>
		<div class="Container"> 
			<!-- Lnb -->
			<div class="Side"> 
				<!-- 메뉴별 lnb 타이틀 컬러 변경 / lnb_00 -->
				<div class="lnb_ap"><spring:message code="appvl.sidemenu.label.processing"/></div>
				<div class="h36"></div>
				<%-- TBS 2016.02.11 start --%> 
				<form name="frm" action ="<c:url value='/cop/bbs${prefix}/selectBoardList.do'/>" method="post">
				<input type="hidden" name="bbsId" value="<c:out value='${result.bbsId}'/>" />
				<input type="hidden" name="bbsNm" value="<c:out value='${result.bbsNm}'/>" />
				<input type="hidden" name="nttId"  value="0" />
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
				<%-- TBS 2016.02.11 end --%> 
				<!-- lnb별 공통 버튼 -->
				<div class="lnb_butbox"><input type="submit" value='<spring:message code="appvl.sidemenu.button.posting"/>' title="<spring:message code="appvl.sidemenu.button.posting"/>" class="but_big" onClick="fn_egov_register();"/></div>
				<!-- 트리 삽입 영역 -->
				<div class="lnb_tree" style="background:#FFF;">
					<jsp:include page="./cmm/EgovBBSTree.jsp" flush="false" />
				</div>
				<%-- TBS 2016.02.11 start --%> 
				</form>
				<%-- TBS 2016.02.11 end --%> 
			</div>
			</div>
</div>
<!-- TBS 2016.02.04 end -->

<!-- Content Start -->
<form:form commandName="board" name="board" method="post" enctype="multipart/form-data" >
<input type="hidden" name="pageIndex" value="<c:out value='${searchVO.pageIndex}'/>"/>
<input type="hidden" name="returnUrl" value="<c:url value='/cop/bbs/forUpdateBoardArticle.do'/>"/>

<input type="hidden" name="bbsId" value="<c:out value='${result.bbsId}'/>" />
<input type="hidden" name="bbsNm" value="<c:out value='${result.bbsNm}'/>" />
<input type="hidden" name="nttId" value="<c:out value='${result.nttId}'/>" />

<input type="hidden" name="bbsAttrbCode" value="<c:out value='${bdMstr.bbsAttrbCode}'/>" />
<input type="hidden" name="bbsTyCode" value="<c:out value='${bdMstr.bbsTyCode}'/>" />
<input type="hidden" name="replyPosblAt" value="<c:out value='${bdMstr.replyPosblAt}'/>" />
<input type="hidden" name="fileAtchPosblAt" value="<c:out value='${bdMstr.fileAtchPosblAt}'/>" />
<input type="hidden" name="posblAtchFileNumber" value="<c:out value='${bdMstr.posblAtchFileNumber}'/>" />
<input type="hidden" name="posblAtchFileSize" value="<c:out value='${bdMstr.posblAtchFileSize}'/>" />
<input type="hidden" name="tmplatId" value="<c:out value='${bdMstr.tmplatId}'/>" />

<input type="hidden" name="cal_url" value="<c:url value='/sym/cal/EgovNormalCalPopup.do'/>" />

<c:if test="${anonymous != 'true'}">
<input type="hidden" name="ntcrNm" value="dummy">	<!-- validator 처리를 위해 지정 -->
<input type="hidden" name="password" value="dummy">	<!-- validator 처리를 위해 지정 -->
</c:if>

<c:if test="${bdMstr.bbsAttrbCode != 'BBSA01'}">
   <input id="ntceBgnde" name="ntceBgnde" type="hidden" value="10000101">
   <input id="ntceEndde" name="ntceEndde" type="hidden" value="99991231">
</c:if>

		<div class="Content"> 
		<!-- Content box Start -->
			<div class="content_box">
				<div class="h36"></div>
				<!-- 공통 타이틀 버튼 라인 -->
				<div class="title_box">
					<div class="title_line"><h1><c:out value='${bdMstr.bbsNm}'/> :: Update </h1></div>
					<div class="but_line">
						<input type="submit" value='<spring:message code="appvl.document.button.register"/>' title="<spring:message code="appvl.document.button.register"/>" class="but_navy mr05" onClick="javascript:fn_egov_update_noticeList();return false;"/>
						<input type="submit" value='List' title="List" class="but_grayL" onClick="javascript:fn_egov_select_noticeList();return false;"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="tabContent_rapper" id="approval_head">
					<div class="display_table">
						<div class="display_tableCell">
							<div class="table_rapper">
								<table summary="" class="board_width_borderNone">
									<caption class="blind"></caption>
									<colgroup>
										<col width="100px"/>
										<col width="*"/>
										<col width="85px"/>
									</colgroup>
									<tbody>
										<tr>
											<th scope="row"><label for="label_1"><spring:message code="appvl.documet.label.title"/></label></th>
											<td colspan="2"><div class="ui_input_text"><input type="text" name="nttSj" value="<c:out value="${result.nttSj}" />" size="60" maxlength="60" title="title"/>
															<br/><form:errors path="nttSj" /></div></td>
										</tr>
										<tr>
											<th scope="row"><label for="label_2">Writer</label></th>
											<td colspan="2"><div class="ui_input_text" style="border-bottom:1px solid #e1e1e1;"><c:out value="${result.frstRegisterNm}"/></div></td>
										</tr>
										<tr>
											<th scope="row"><label for="label_3">Content</label></th>
											<td>
												<div class="ui_btn_rapper" style="border:#e1e1e1 1px solid; width: 1000px">
													<table width="100%" border="0" cellpadding="0" cellspacing="0" class="noStyle">
														<tr><td>
															<textarea id="nttCn" name="nttCn" class="edit_rapper_td" cols="75" rows="28" style="width:550px;" title="inputContent"><c:out value="${result.nttCn}" escapeXml="false" /></textarea>
															<form:errors path="nttCn"/>
														</td></tr>
													</table>
												</div>
											</td>
										</tr>
										<c:if test="${bdMstr.fileAtchPosblAt == 'Y'}">
  									    <tr>
								    	<th scope="row"><spring:message code="appvl.documet.label.attachment"/></th>
										  	<input type="hidden" name="fileListCnt" value="0" />
										    <td colspan="3">
										    <div id="file_upload_posbl"  style="display:none;" >
									            <table width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
												    <tr>
												        <td><input name="file_1" id="egovComFileUploader" type="file"/></td>
												    </tr>
												    <tr>
												        <td>
												        	<div id="egovComFileList"></div>
												        </td>
												    </tr>
									   	        </table>
											</div>
											<div id="file_upload_imposbl"  style="display:none;" >
									            <table width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
												    <tr>
												        <td><spring:message code="common.imposbl.fileupload" /></td>
												    </tr>
									   	        </table>
											</div>
											</td>
										</tr>
								  		<c:if test="${not empty result.atchFileId}">
								  		<tr><td></td>
									    <td>
											<c:import url="/cmm/fms/selectFileInfsForUpdate.do" charEncoding="utf-8">
												<c:param name="param_atchFileId" value="${result.atchFileId}" />
											</c:import>
									    </td></tr>
								  		</c:if>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>								
			</div>
			<!-- //tabContent_rapper head -->
		</div>
		<!-- Content box End --> 
	</div>
	<!-- content End -->
</form:form>
<div id="div_popup" style="display: none"></div>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCopyright.jsp" %>
</body>
</html>
