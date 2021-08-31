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
		$(document).ready(function(){
			$("#searchButton").click(function(){
				if($("#searchValue").val() == ""){
					alert("검색어를 바르게 입력하세요(공백X).");
					return false;
				}
				$("#searchForm").submit();
			});
			$(window).resize(function(){
				popResizer();
			});
		});
		function popResizer(){
		    window.resizeTo(1024,800);
		};
		
		function saveRegistrationInSession(registrationID,userID,userName,CBNUCode){
			$.ajax({
				type:"POST",
				url : "<c:url value="/prescription/saveRegistrationInSession"/>",
				data : {"registrationID": registrationID,
					    "userID" : userID,
					    "userName" : userName,
					    "CBNUCode" : CBNUCode
					    },
				dataType : "JSON",
				success: function(json){
					if(json.result){
						addParentHTML(userName,CBNUCode,userID);
					}
				},
				error:function(){
					alert("추가할 수 없는 사용자입니다");
				}	
			});
		}
		popResizer();
		
		function addParentHTML(userName,CBNUCode,userID){
			$(opener.document).find("#registrationTableTr").remove();
			var html = "<tr id='registrationTableTr'>";
			html += "<td name='userID'>" + userID + "</td>";
			html += "<td name = 'userName'>" + userName + "</td>";
			html += "<td name = 'userName'>" + CBNUCode + "</td>";
			html += "</tr>";
			$(opener.document).find("#registrationTableBody").append(html);
			close();
		}
		
	</script>
</head>
<body style=" min-width: 1024px;">
	<div id="searchtab">
	<div class="searchtab_ti">환자 검색</div>
		<form id="searchUpper" name="searchUpper" class="search_info"  style="margin:0 auto;"  action="<c:url value="/prescription/searchUpperResult"/>" method="POST">
			<li>
				<select name="searchOption" class="searchOption">
					<option value="userID"<c:if test="${searchpaging.searchOption == 'userID'}">selected</c:if>>환자ID</option>
					<option value="userName"<c:if test="${searchpaging.searchOption == 'userName'}">selected</c:if>>환자이름</option>
				</select>
				<input type="text" id="searchValue" name="searchValue" class="inputText" placeholder="조건 입력" value="${searchpaging.searchValue}"/>
				<input type="submit" id="searchButton" name="searchButton" value="검색" class="submitbtn"/>
			</li>
		</form>
	
		<!-- table :s -->
		<table class="tbl_basic center" id="medicineTable" style="table-layout:fixed;">
	          <caption>
	              <strong>환자 검색 결과</strong>
	              <details>
	                  <summary>환자 검색 결과</summary>
	              </details>
	          </caption>
	             <thead>
	                 <tr>
						<th class="trw">번호</th>
						<th class="trw">환자ID</th>
						<th class="trw">환자이름</th>
						<th class="trw">CBNU코드</th>
	                 </tr>
	             </thead>
	             <tbody>
	             <c:choose>
             		<c:when test="${fn:length(registrationDataTypeList) > 0 }">
	             		<c:set var="cnt" value="${searchpaging.startRow }"/>
		             	<c:forEach var="registration" items="${registrationDataTypeList}">
						<c:set var="cnt" value="${cnt+1}"/>
						<tr onclick="saveRegistrationInSession('<c:out value="${registration.idx}"/>','<c:out value="${registration.patient.userId}"/>','<c:out value="${registration.patient.name}"></c:out>','<c:out value="${registration.patient.cbnuCode}"></c:out>')">
							<td><c:out value="${cnt}"/></td>
							<td><c:out value="${registration.patient.userId}"/></td>
							<td><c:out value="${registration.patient.name}"></c:out></td>
							<td><c:out value="${registration.patient.cbnuCode}"></c:out></td>
						</tr>
						</c:forEach>
             		</c:when>
             		<c:otherwise>
             		<tr>
             			<td colspan="3">해당 조건의 환자가 검색되지 않습니다.</td>
             		</tr>
             		</c:otherwise>
             	</c:choose>
			</tbody>
		</table>
		<!-- table :e -->           
		<!-- searchpaging & search :s -->
		<ul class="paginate">
			<c:if test="${searchpaging.nowPageGroup > 1 }">
				<li class="dir prev">
					<a href="<c:url value="/prescription/searchUpperResult" >
					<c:param name="page" value="${(searchpaging.nowPageGroup-2)*searchpaging.pageGroupSize+1 }"/>
					<c:param name="searchOption" value="${searchpaging.searchOption}" />
					<c:param name="searchValue" value="${searchpaging.searchValue}" />
					<c:param name="date" value="${date}" />
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
				<a href="<c:url value="/prescription/searchUpperResult" >
				<c:param name="page" value="${i }"/>
				<c:param name="searchOption" value="${searchpaging.searchOption}" />
				<c:param name="searchValue" value="${searchpaging.searchValue}" />
				<c:param name="date" value="${date}" />
				</c:url>" title="<c:out value="${i }"/>페이지" onclick=" ">
				<c:out value="${i }"/>
				</a>
				</li>
			</c:forEach>
			<c:if test="${searchpaging.nowPageGroup < searchpaging.pageGroupCount }" >
     			<li class="dir next"><a href="<c:url value="/prescription/searchUpperResult">
      			<c:param name="page" value="${searchpaging.nowPageGroup*searchpaging.pageGroupSize+1 }"/>
      			<c:param name="searchOption" value="${searchpaging.searchOption}" />
      			<c:param name="searchValue" value="${searchpaging.searchValue}" />
      			<c:param name="date" value="${date}" />
      			</c:url>" onclick=" " title="다음페이지로 이동">»</a></li>
       		</c:if>
   		</ul>
	</div>
</body>
</html>
