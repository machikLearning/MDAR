<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Script-Type" content="text/javascript">
	<meta http-equiv="Content-Style-Type" content="text/css">
	<link rel="shortcut icon" href="<c:url value="/img/logo.png"/>">
	<title>충북대학교 병원</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/normalize.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/layout.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/font.css"/>">
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="<c:url value="/js/jquery-ui-1.10.3.custom.min.js"/>"></script>
	<script src="<c:url value="/js/submenu.js"/>"></script>
</head>

<script type="text/javascript">
	function addPatient(){
		if(confirm("사용자를 추가하시겠습니까?")==true){
			window.open("<c:url value="/prescription/searchPatientResult"/>","adduser","width=1024pxpx, height=800");
		}
		else return;
		
	}
	
	function selectPatientInfo(child){
		var parentTR = $(child).parent();
		$("#patientID").val($(parentTR).children('.patientID').text()); 
		$("#patientName").val($(parentTR).children(".patientName").text());
		$("#CBNUCode").val($(parentTR).children(".CBNUCode").text());
		$("#registrationID").val($(parentTR).children(".registrationID").text());
		$("#selectPatientInfo").submit();
	}
	
	function deletePatient(registrationID,obj) {
		$.ajax({
			type:"POST",
			url : "<c:url value="/prescription/deletePatient"/>",
			data : {"registrationID": registrationID},
			dataType : "JSON",
			success: function(json){
				if(json.result){
					$(obj).parent().parent().remove();
				}
			},
			error:function(){
				alert("삭제할 수 없습니다");
			}	
		});
	}
	
</script>
<body>
<!-- wrap :s -->
	<div id="wrapper">
		<!-- header :s -->
		<div id="headerWrap">
			<c:import url="../common/prescription/header.jsp"/>
		</div>
		<!-- header :e -->
		<!-- contentsWrap :s -->
		<div id="contentsWrap">
			<!-- lnbWrap :s -->
			<div id="lnbWrap">
				<div id="lnb_title">
					<h2>환자 처방</h2>
				</div>	
				<c:import url="../common/prescription/submenu.jsp"/>
			</div>
			<!-- lnbWrap :e -->

			<!-- container :s -->
			<div id="container">
				<div class="location">
					<span class="home"><a href="<c:url value="/main/selectFunc"/>"><img src="<c:url value="/img/layout/ico_home.png"/>" alt="홈"></a></span>
				</div>

				<h3 id="contTi">환자 처방</h3>

				<!-- 컨텐츠영역 txt :s -->
				<div id="txt">
					<h4>환자 리스트</h4>
					<!-- table :s -->
					<table id="maintable" class="tbl_basic" style="table-layout:fixed;">
			            <caption>
			                <strong>환자 리스트</strong>
			                <details>
			                    <summary>환자 리스트</summary>
			                </details>
			            </caption>
							<thead>
			                    <tr>
			                        <th class="trw" >No</th>
			                        <th class="trw" >환자 아이디</th>
			                        <th class="trw" >환자 이름</th>
			                        <th class="trw" >병원</th>
			                        <th class="trw" >병원코드</th>
			                        <th class="trw" >삭제</th>
			                    </tr>
			                </thead>
			                <tbody>
			                <c:choose>
			                	<c:when test="${fn:length(patientList) eq 0}">
			                	<tr>
			                		<td colspan="6"> 환자가 없습니다. </td>
								</tr>
			                	</c:when>
			                	<c:otherwise>
									<c:set var="cnt" value="${paging.startRow}"/>
				                	<c:forEach var="registration" items="${patientList}">
									<c:set var="cnt" value="${cnt+1}"/>
									<tr class="patientList">
										<td onclick="selectPatientInfo(this)" style="cursor:pointer;"><c:out value="${cnt }"/></td>
										<td class="patientID" onclick="selectPatientInfo(this)" style="cursor:pointer;"><c:out value="${registration.patient.userId }"/></td>
										<td class="patientName" onclick="selectPatientInfo(this)" style="cursor:pointer;"><c:out value="${registration.patient.name }"/></td>
										<td class="Hospital" onclick="selectPatientInfo(this)" style="cursor:pointer;"><c:out value="${registration.patient.hospital.name }"/></td>
										<td class="CBNUCode" onclick="selectPatientInfo(this)" style="cursor:pointer;"><c:out value="${registration.patient.cbnuCode }"/></td>
										<td>
											<input type="button" value="삭제" class="btn_save" onclick="deletePatient('<c:out value="${registration.idx }"/>',this)">
										</td>
										<td class="registrationID" style="display:none"><c:out value="${registration.idx}"/></td>
				                   </tr>
				                   </c:forEach>
			                	</c:otherwise>
			                </c:choose>
			                </tbody>
			            </table>
			            <!-- table :e -->
						<form id="deletePatient" action="<c:url value="/prescription/deletePatient"/>" method="POST">
							<input type="hidden" id="userID" name="userID"/>
							<input type="hidden" id="page" name="page"/>
						</form>
						<form id="selectPatientInfo" action="<c:url value="/prescription/selectPatientInfo"/>" method="POST">
							<input type="hidden" id="patientID" name="patientID"/>
							<input type="hidden" id="patientName" name="patientName">
							<input type="hidden" id="CBNUCode" name="CBNUCode">
							<input type="hidden" id="registrationID" name="registrationID">
						</form>
						 <!-- paging & search :s -->
			            <ul class="paginate">
		            		<c:if test="${paging.nowPageGroup > 1 }">
								<li class="dir prev">
									<a href="<c:url value="/prescription/main?page=${(paging.nowPageGroup-2)*paging.pageGroupSize+1 }"/>" onclick="" title="이전페이지로 이동">«</a>
								</li>
							</c:if>
		            		<c:forEach var="i" begin="${paging.startPage }" end="${paging.endPage }">
	            				<c:choose>
	            					<c:when test="${param.page == i }">
	            						<li class="active">
	            					</c:when>
	            					<c:otherwise>
	            						<li>
	            					</c:otherwise>
	            				</c:choose>
	            						<a href="<c:url value="/prescription/main?page=${i }"/>" title="<c:out value="${i }"/>페이지">
	            							<c:out value="${i }"/>
	           							</a>
	           							</li>
		            		</c:forEach>
		            		<c:if test="${paging.nowPageGroup < paging.pageGroupCount }">
		            			<li class="dir next"><a href="<c:url value="/prescription/main?page=${paging.nowPageGroup*paging.pageGroupSize+1 }"/>" onclick="" title="다음페이지로 이동">»</a></li>
		            		</c:if>
						</ul>
						<!-- paging & search :e -->
						<span class="rightButton">
							<input type="button" value="환자등록" onclick="addPatient()" class="mainbtn">
						</span>
				</div>
				<!-- 컨텐츠영역 txt :e -->

			</div>
			<!-- container :e -->

		</div>
		<!-- contentsWrap :e -->


		<!-- footerWrap :s -->
		<div id="footerWrap">
			<c:import url="../common/footer.jsp"/>
		</div>
		<!-- footerWrap :e -->
	</div>
</body>
</html>