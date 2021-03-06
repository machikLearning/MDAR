<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Script-Type" content="text/javascript">
	<meta http-equiv="Content-Style-Type" content="text/css">
	<link rel="shortcut icon" href="<c:url value="/img/logo.png"/>">
	<title>ADRM</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/normalize.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/layout.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/font.css"/>">
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script type="text/javascript">
		var regExp = /\s/g;
		
		$(document).ready(function(){	
			$("#searchButton").click(function(){
				if($("#searchValue").val() =="" || regExp.test($("#searchValue").val())){
					alert("검색어를 바르게 입력하세요(공백X).");
					return false;
				}
				$("#searchPatient").submit();
			});
			$(window).resize(function(){
				popResizer();
			});
		});
		function popResizer(){
		    //window.resizeTo(700,700);
		};
		popResizer();
		
		function checkPatient(userID,obj){
			var status;
			$("td.patientID",opener.document).each(function(i,e){
				if($(this).text() == userID){
					status = 1;
				}
			})
			if(status != 1){
				appendPatient(userID,obj);
			}else{
				alert("이미 추가된 환자 입니다");
			}
			
		}
		
		function appendPatient(userID,obj) {
			$.ajax({
				type:"POST",
				url : "<c:url value="/prescription/createRegistration"/>",
				data : {"patientID": userID},
				dataType : "JSON",
				success: function(json){
					if(json.registrationID){
						opener.location.reload();
						window.close();
					}
				},
				error:function(){
					alert("추가할 수 없습니다");
				}	
			});
		}
	</script>
</head>
<body style=" min-width: 1024px;">
	<div id="searchtab">
	<div class="searchtab_ti">환자등록</div>
		<form id="searchPatient" name="searchPatient" class="search_info" style="margin:0 auto;" action="<c:url value="/prescription/searchPatientResult"/>" method="POST">
			<li>
				<select id="searchOption" name="searchOption" class="searchOption">
					<option value="userId" <c:if test="${searchpaging.searchOption == 'userId'}">selected</c:if>>환자 아이디</option>
					<option value="name" <c:if test="${searchpaging.searchOption == 'name'}">selected</c:if>>환자 이름</option>
					<option value="cbnuCode" <c:if test="${searchpaging.searchOption == 'cbnuCode'}">selected</c:if>>병원 코드</option>
				</select>
				<input type="text" id="searchValue" name="searchValue" class="inputText" value="${searchpaging.searchValue}" class="inputText" placeholder="조건 입력"/>
				<input type="submit" id="searchButton" name="searchButton" class="submitbtn" value="검색" class="searchBtn"/>
			</li>
		</form>
		<c:choose>
	             	<c:when test="${fn:length(userList) eq 0}">
	             		<h4 id="resultnull">환자가 없습니다.</h4>
	             	</c:when>
			<c:otherwise>
			<table class="tbl_basic center" style="table-layout:fixed;">
	            <caption>
	                <strong>환자 검색 결과</strong>
	            </caption>
	               <thead>
	                   <tr>
	                       <th class="trw" width="35">No</th>
	                       <th class="trw" width="90">환자 아이디</th>
	                       <th class="trw">환자 이름</th>
	                       <th class="trw">병원</th>
	                       <th class="trw">병원 코드</th>
	                       <th class="trw">선택</th>
	                   </tr>
	               </thead>
	               <tbody>
					<c:set var="cnt" value="${searchpaging.startRow }"/>
	               	<c:forEach var="user" items="${userList}">
						<c:set var="cnt" value="${cnt+1 }"/>
						<tr>
							<td><c:out value="${cnt }"/></td>
							<td class="patientID"><c:out value="${user.userId }"/></td>
							<td class="patientName"><c:out value="${user.name }"/></td>
							<td class="CBNUCode"><c:out value="${user.hospital.name }"/></td> 
							<td class="CBNUCode"><c:out value="${user.cbnuCode }"/></td> 
							<td>
								<input type="button" value="선택" class="btn_save" onclick="checkPatient('<c:out value="${user.userId }"/>',this)">
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	              </c:otherwise>
		</c:choose>
		                	
			            
		<!-- table :e -->
		<!-- searchpaging & search :s -->
		<ul class="paginate">
			<c:if test="${searchpaging.nowPageGroup > 1 }">
				<li class="dir prev">
					<a href="<c:url value="/prescription/searchPatientResult" >
					<c:param name="page" value="${(searchpaging.nowPageGroup-2)*searchpaging.pageGroupSize+1 }"/>
					<c:param name="searchOption" value="${searchpaging.searchOption}" />
					<c:param name="searchValue" value="${searchpaging.searchValue}" />
					</c:url>" onclick="" title="이전페이지로 이동">«</a>
				</li>
			</c:if>
			<c:forEach var="i" begin="${searchpaging.startPage }" end="${searchpaging.endPage }">
				<c:choose>
					<c:when test="${param.page == i }">
						<li class="active">
					</c:when>
					<c:otherwise>
						<li>
					</c:otherwise>
				</c:choose>
				<a href="<c:url value="/prescription/searchPatientResult" >
				<c:param name="page" value="${i }"/>
				<c:param name="searchOption" value="${searchpaging.searchOption}" />
				<c:param name="searchValue" value="${searchpaging.searchValue}" />
				</c:url>" title="<c:out value="${i }"/>페이지" onclick=" ">
				<c:out value="${i }"/>
				</a>
				</li>
			</c:forEach>
			<c:if test="${searchpaging.nowPageGroup < searchpaging.pageGroupCount }" >
     			<li class="dir next"><a href="<c:url value="/prescription/searchPatientResult">
      			<c:param name="page" value="${searchpaging.nowPageGroup*searchpaging.pageGroupSize+1 }"/>
      			<c:param name="searchOption" value="${searchpaging.searchOption}" />
      			<c:param name="searchValue" value="${searchpaging.searchValue}" />
      			</c:url>" onclick=" " title="다음페이지로 이동">»</a></li>
       		</c:if>
   		</ul>
	</div>
</body>
</html>
