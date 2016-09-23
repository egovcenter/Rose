<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="utf-8">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="<c:out value="${pageContext.servletContext.contextPath}"/>/js/egovframework/com/uss/ion/approval.js"></script>
</head>
<body>
<div class="wrap">
<!-- listForm form start --> 
<form action="<c:url value='/approvalDocPageList.do'/>" name="listForm" id="listForm" method="post">
	<input type="hidden" value="" id="docType" name="docType" class="docType">
	<input type="hidden" value="" id="docId" name="docId" class="docId">
	<!--  Top Menu Start --> 
	<jsp:include page="/WEB-INF/jsp/egovframework/com/cmm/EgovTopMenu.jsp" flush="false">
		<jsp:param value="HOME" name="selectedMenu"/>
	</jsp:include>
	<!--  Top Menu End--> 
	<div class="clear"></div>
	<!-- Container Start -->
	<div class="Container"> 
		<!-- Lnb start -->
		<div class="Side">
			<!-- Common Title -->
			<div class="lnb_in"><spring:message code="home.sidemenu.label.home"/></div> 
			<div class="h36"></div>
			<!-- lnb_tree start -->
			<div class="lnb_tree" style="background:#FFF;">
				<!-- My Count start -->
				<div class="rapper_table mb40">
					<table summary="" class="boardList_my">
						<caption class="blind"></caption>
						<colgroup>
							<col width="60%"/>
							<col width="*"/>
						</colgroup>
						<thead>
							<tr>
								<th scope="col" colspan="2"><span>My Count</span></th>
							</tr>
						</thead>					
						<tbody>
							<c:if test="${deptAuth.inboxAuth == '1' }">
								 <tr>
									<td class="ttl">inbox</td>
									<td><a href="javascript:approvalDocPage('inbox');" title="Inbox">${myCountList["0"]}</a></td>
								</tr>
							</c:if>
								 <tr>
									<td class="ttl">waiting</td>
									<td><a href="javascript:approvalDocPage('waiting');" title="Waiting">${myCountList["1"]}</a></td>
								</tr>
								 <tr>
									<td class="ttl">Ongoing</td>
									<td><a href="javascript:approvalDocPage('ongoing');" title="Ongoing">${myCountList["2"]}</a></td>
								</tr>
							<c:if test="${userAuth.receiveAuth == '1' }">
								 <tr>
									<td class="ttl">Incoming</td>
									<td><a href="javascript:approvalDocPage('incoming');" title="Incoming">${myCountList["3"]}</a></td>
								</tr>
							</c:if>
							<c:if test="${userAuth.sendAuth == '1' }">
								 <tr>
									<td class="ttl">Outgoing</td>
									<td><a href="javascript:approvalDocPage('outgoing');" title="Outgoing">${myCountList["4"]}</a></td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
				<!-- My Count end -->
	           	<!-- calender start -->
				<div class="rapper_table mb40">
					<table summary="" class="calender">
						<caption class="blind"></caption>
						<colgroup>
							<col width="*"/>
							<col width="14%"/>
							<col width="14%"/>
							<col width="14%"/>
							<col width="14%"/>
							<col width="14%"/>
							<col width="14%"/>
						</colgroup>
						<thead>
							<tr>
								<th scope="col" colspan="7"><span><a href="#"><&nbsp;</a></span> <span>March</span> <span>2016 </span> <span><a href="#">&nbsp;></a></span> </th>
							</tr>
						</thead>					
						<tbody>
								 <tr>
									<th class="ttl">S</td>
									<th class="ttl">M</td>
									<th class="ttl">T</td>
									<th class="ttl">W</td>
									<th class="ttl">T</td>
									<th class="ttl">F</td>
									<th class="ttl">S</td>
								</tr>
								 <tr>
									<td><a href="#">1</a></td>
									<td><a href="#">2</a></td>
									<td><a href="#">3</a></td>
									<td><a href="#">4</a></td>
									<td><a href="#">5</a></td>
									<td><a href="#">6</a></td>
									<td><a href="#">7</a></td>
								</tr>
								 <tr>
									<td ><a href="#">8</a></td>
									<td><a href="#">9</a></td>
									<td><a href="#">10</a></td>
									<td><a href="#">11</a></td>
									<td class="today"><a href="#">12</a></td>
									<td><a href="#">13</a></td>
									<td><a href="#">14</a></td>
								</tr>
								 <tr>
									<td><a href="#">15</a></td>
									<td class="dday"><a href="#">16</a></td>
									<td class="dday"><a href="#">17</a></td>
									<td><a href="#">18</a></td>
									<td><a href="#">19</a></td>
									<td><a href="#">20</a></td>
									<td><a href="#">21</a></td>
								</tr>
								 <tr>
									<td><a href="#">22</a></td>
									<td><a href="#">23</a></td>
									<td><a href="#">24</a></td>
									<td><a href="#">25</a></td>
									<td><a href="#">26</a></td>
									<td class="dday"><a href="#">27</a></td>
									<td><a href="#">28</a></td>
								</tr>
								 <tr>
									<td><a href="#">29</a></td>
									<td><a href="#">30</a></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
						</tbody>
					</table>
				</div>
                <!-- calender end -->
	            <!-- My Favorite start -->
				<div class="rapper_table mb40">
					<table summary="" class="boardList_my">
						<caption class="blind"></caption>
						<colgroup>
							<col width="100%"/>
						</colgroup>
						<thead>
							<tr>
								<th scope="col" colspan="2"><span>My Favorite</span></th>
							</tr>
						</thead>					
						<tbody>
								 <tr>
									<td class="man">amanda</td>
								</tr>
								 <tr>
									<td class="man">Alexsander</td>
								</tr>
								 <tr>
									<td class="man">Madonna</td>
								</tr>
								 <tr>
									<td class="man">Jhon Smith</td>
								</tr>
						</tbody>
					</table>
				</div>
	           	<!-- My Favorite end -->
			</div>
			<!-- lnb_tree end -->
		</div>
		<!-- Lnb end -->
		<!-- Content start-->
		<div class="Content">
			<!-- Content box Start -->
			<div class="content_box"> 
				<!-- Top search -->
				 <div class="top_search">
					<!--  <input name="textfield" type="text" id="textfield" title="search" class="input260" onClick="this.style.backgroundImage='none'">
					<input type="button" value="Search" class="but_navy" onClick="location.href='http://www.naver.com'";/> -->
				</div>
				<div class="clear"></div>
				
				<!-- Menu Title-->
				<div class="title_box">
					<div class="title_line">
						<h1>Weekly Schedule</h1>						
					</div>					
				</div>
				<div class="page_move_rapper float_right">
					<span class="btn_pn_rapper">
						<a href="#" class="move_page_button" act="prev_page"><img src="<c:url value='/images/egovframework/com/uss/cmm/but_prev.png'/>" alt="prev"></a>
						<a href="#" class="move_page_button" act="next_page"><img src="<c:url value='/images/egovframework/com/uss/cmm/but_next.png'/>" alt="next"></a>
                    </span>
				</div>
				<div class="rapper_table mb40">
					<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height">
						<caption class="blind"></caption>
						<colgroup>
							<col width="12.5%"/>
							<col width="12.5%"/>
							<col width="12.5%"/>
							<col width="12.5%"/>
							<col width="12.5%"/>
							<col width="12.5%"/>
							<col width="12.5%"/>
						</colgroup>
						<thead>
							<tr>
								<th scope="col"><span>Time</span></th>
								<th scope="col"><span>Sun</span></th>
								<th scope="col"><span>Monday</span></th>			
								<th scope="col"><span>Tuesday</span></th>
								<th scope="col"><span>Wednesday</span></th>
								<th scope="col"><span>Thursday</span></th>
								<th scope="col"><span>Fridays</span></th>	
								<th scope="col"><span>Saturday</span></th>
							</tr>
						</thead>					
						<tbody>
						 	<tr>
								<td>12am</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							 <tr>
								<td>1pm</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						 
							 <tr>
								<td>2pm</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						 
							 <tr>
								<td>3pm</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						 
							 <tr>
								<td>4pm</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						 
							 <tr>
								<td>5pm</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="clear"></div>
				<div class="rapper_table">
					<!-- approval start -->
					<div class="main_a1" id="approval">
						<!-- Tab Menu start-->
                        <div class="tab_box tab_apv" style="width:100%">
                            <ul>
                                <li class="on inbox" onClick="javascript:toggle_content_apv(this, 'inbox')"><a href="#">Inbox</a></li>
                                <li class="waiting" onClick="javascript:toggle_content_apv(this, 'waiting')"><a href="#">Waiting</a></li>
                                <li class="ongoing" onClick="javascript:toggle_content_apv(this, 'ongoing')"><a href="#">Ongoing</a></li>
                            </ul>
                        </div>
						<!-- Tab Menu end -->
						<div id="approvalTab">
							<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovApprovalTabList.jsp" %>
						</div>
					</div>
					<!-- approval end -->
					<!-- BBS start -->
					<div class="main_a2" id="bbs">
						<!-- Tab Menu start-->
                        <div class="tab_box tab_bbs" style="width:100%">
                            <ul>
                                <li class="on notice" onClick="javascript:toggle_content_bbs(this, 'notice')"><a href="#">Notice</a></li>
                                <li class="news" onClick="javascript:toggle_content_bbs(this, 'news')"><a href="#">News</a></li>
                                <li class="event" onClick="javascript:toggle_content_bbs(this, 'event')"><a href="#">Event</a></li>
                            </ul>
                        </div>
						<!-- Tab Menu end -->
                        <div class="rapper_table mb40">
                            <table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height">
                                <caption class="blind"></caption>
                                <colgroup>
                                    <col width="*"/>
                                    <col width="25%"/>
                                    <col width="25%"/>
                                    <col width="25%"/>
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th scope="col"><span>Date</span></th>
                                        <th scope="col"><span>Title</span></th>			
                                        <th scope="col"><span>Department</span></th>
                                        <th scope="col"><span>Poster</span></th>
                                    </tr>
                                </thead>					
                                <tbody>
                                         <tr>
                                            <td>2015-01-12</td>
                                            <td>First Notice</td>
                                            <td>HR v</td>
                                            <td>Michael Porterv</td>
                                        </tr>
                                         <tr>
                                            <td>2015-01-12</td>
                                            <td>First Notice</td>
                                            <td>HR v</td>
                                            <td>Michael Porterv</td>
                                        </tr>
                                     
                                         <tr>
                                            <td><br/></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                     
                                         <tr>
                                            <td><br/></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                         <tr>
                                            <td><br/></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                     
                                         <tr>
                                            <td><br/></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                         <tr>
                                            <td><br/></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                     
                                         <tr>
                                            <td><br/></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                </tbody>
                            </table>
                        </div>
					</div>
					<!-- BBS end -->
				</div>
			</div>
			<!-- content_box End -->
		</div>
		<!-- Content End --> 
	</div>
	<!-- Container End -->
	<div class="clear"></div>
</form>
<!-- listForm form end -->
</div>
<!-- wrap End -->
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCopyright.jsp" %>
</body>

<script language="javascript" type="text/javascript"> 
//<![CDATA[
function toggle_content_apv(obj, ID){
	$('.tab_apv').each(function(index){
		if(ID == "inbox"){
			$('.inbox').addClass('on');
			$('.waiting').removeClass('on');
			$('.ongoing').removeClass('on');
		}else if(ID == "waiting"){
			$('.waiting').addClass('on');
			$('.inbox').removeClass('on');
			$('.ongoing').removeClass('on');
		}else{
			$('.ongoing').addClass('on');
			$('.inbox').removeClass('on');
			$('.waiting').removeClass('on');
		}
	});
	var url = "<c:url value='/approvalTabList.do'/>";
	$("#approvalTab").load(url, {docType : ID}, function(data) {
		$(this).html(data).trigger("create");
	});
}
function toggle_content_bbs(obj, ID){
	$('.tab_bbs').each(function(index){
		if(ID == "notice"){
			$('.notice').addClass('on');
			$('.news').removeClass('on');
			$('.event').removeClass('on');
		}else if(ID == "news"){
			$('.news').addClass('on');
			$('.notice').removeClass('on');
			$('.event').removeClass('on');
		}else{
			$('.event').addClass('on');
			$('.notice').removeClass('on');
			$('.news').removeClass('on');
		}
	});
}
function approvalDocPage(docType){
	 document.getElementById("docType").value = docType;
	 document.listForm.action = "<c:url value='/approvalDocPageList.do'/>";
	 document.listForm.submit();
}
function approvalRedraft(docID){
	document.getElementById("docId").value = docID;
	document.listForm.action = "<c:url value='/approvalRedraft.do'/>";
	document.listForm.submit();
}
function approvalApprove(docID){
	document.getElementById("docId").value = docID;
	document.listForm.action = "<c:url value='/approvalApprove.do'/>";
	document.listForm.submit();
}
function approvalView(docID){
	document.getElementById("docId").value = docID;
	document.listForm.action = "<c:url value='/approvalView.do'/>";
	document.listForm.submit();
}
//]]>
</script>
</html>