<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Script-Type" content="text/javascript">
	<meta http-equiv="Content-Style-Type" content="text/css">
	<meta http-equiv="X-UA-Compatible" content="IE=8">
	<link rel="shortcut icon" href="<c:url value="/img/logo.png"/>">
	<title>충북대학교 병원</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/normalize.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/layout.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/font.css"/>">
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="<c:url value="/js/jquery-ui-1.10.3.custom.min.js"/>"></script>
	<script src="<c:url value="/js/submenu.js"/>"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</head>
<script type="text/javascript">
	
	function selectBody(boardID){
		$("#selectBodyInput").val(boardID);
		$("#selectBody").submit();
	}
	
	function deleteATCTableTr(boardID, ATCListName){
		boardID += "";
		$(ATCListName).each(function(i,e){s
			if($(this).val() == prescriptionID){
				$(this).parent().parent().remove();
			}
		})
	}

	function deleteBody(boardID,obj){
		if(confirm("처방을 삭제하시겠습니까?")==true){
			$.ajax({
				type:"POST",
				url : "<c:url value="board/deleteBody"/>",
				data : {"id": prescriptionID},
				dataType : "JSON",
				success: function(json){
					if(json.deletePrescriptionResult){
						$(obj).parent().parent().remove();
					}
				},
				error:function(){
					alert("삭제할 수 없습니다");
				}	
			});
		}
		else return;
	}
	
	function updateBody(boardID){
		if(confirm("처방을 수정하시겠습니까?")==true){
			$("#updateBodyInput").val(boardID);
			$("#updateBody").submit();
			
		}
		else return;
	}
	
	function writeReplyBoard(parentID,depth){
		$("#writeReplyBoardParentID").val(parentID);
		$("#writeReplyBoardDepth").val(depth);
		$("#writeBoardForm").submit();
	}
	
	$(document).ready(function(){
		<c:choose>
		<c:when test="${param.resultType == 'PRESCRIPTION_SUCCESS'}">
			alert("처방을 완료하였습니다.");
		</c:when>
		<c:when test="${param.resultType == 'DELETE_SUCCESS'}">
			alert("삭제가 완료되었습니다.");
		</c:when>
	</c:choose>
	});
	
</script>
<body>
	
<!-- wrap :s -->

	<div id="wrapper">
		<div id="headerWrap">
			<c:import url="../common/prescription/header.jsp"/>
		</div>
		<!-- header :e -->


		<!-- contentsWrap :s -->
		<div id="contentsWrap">
			<!-- container :s -->
			<div id="lnbWrap">
				<c:import url="../common/prescription/submenu.jsp"/>
			</div>
		
	
			<div id="container">
				<!-- 컨텐츠영역 txt :s -->
				<div id="txt">
					<div id="searchtab">
		<form id="searchBoard" name="searchBoard" class="search_info" action="<c:url value="/board/searchBoard"/>" method="POST">
			<li>
				<select name="searchOption" class="searchOption">
					<option value="boardTitle"<c:if test="${searchpaging.searchOption == 'boardTitle'}">selected</c:if>>게시판 제목</option>
					<option value="boardBody"<c:if test="${searchpaging.searchOption == 'boardBody'}">selected</c:if>>게시판 내용</option>
					<option value="user"<c:if test="${searchpaging.searchOption == 'userID'}">selected</c:if>>작성자</option>
				</select>
				<input type="text" id="searchValue" name="searchValue" class="inputText" placeholder="조건 입력" value="${searchpaging.searchValue}"/>
				<input type="hidden" id="date" name="date" value="${date}"/>
				<input type="submit" id="searchButton" name="searchButton" value="검색" class="submitbtn"/>
			</li>
		</form>
	
		<!-- table :s -->
		<table class="table table-hover" id="medicineTable" >
	       	<colgroup>
	       		<col width="7%">
	       		<col width="*">
	       		<col width="12%">
	       		<col width="5%">
	       		<col width="5%">
	       		<col width="5%">
	       		<col width="5*">
	       	</colgroup>
	             <thead>
	                 <tr>
	                 	<th class="trw">번호</th>
						<th class="trw">제목</th>
						<th class="trw">날짜</th>
						<th class="trw">글쓴이</th>
						<th class="trw">답글달기</th>
						<th class="trw">수정</th>
						<th class="trw">삭제</th>
	                 </tr>
	             </thead>
	             <tbody>
	             <c:choose>
             		<c:when test="${!empty boardList}">
	             		<c:set var="cnt" value="${searchpaging.startRow }"/>
		             	<c:forEach var="board" items="${boardList}">
		             		<c:if test="${board.depth eq 0}"><c:set var="cnt" value="${cnt+1 }"/></c:if>
							<tr>
								<c:choose>
									<c:when test="${board.depth eq 0 }"><td onclick="selectBody(<c:out value='${board.boardID}'/>)"><c:out value="${cnt} "></c:out></c:when>
									<c:otherwise><td onclick="selectBody(<c:out value='${board.boardID}'/>)"></c:otherwise>
								</c:choose>
								<td onclick="selectBody(<c:out value='${board.boardID}'/>)"><c:out value="${board.boardTitle }"/></td>
								<td onclick="selectBody(<c:out value='${board.boardID}'/>)"><fmt:formatDate value='${board.date}'  pattern="yy년 MM월dd일 HH시mm분"/></td>
								<td onclick="selectBody(<c:out value='${board.boardID}'/>)"><c:out value="${board.userID }"/></td>
								<td><input type="button" value="답글" class="subbtn" onclick="writeReplyBoard('<c:out value="${board.boardID }"/>','<c:out value="${board.depth}"/>',)"/></td>
								<td><input type="button" value="수정" class="subbtn" onclick="changeBoard('<c:out value="${board.boardID }"/>)"/></td>
								<td><input type="button" value="삭제" class="subbtn" onclick="deleteBoard('<c:out value="${board.boardID }"/>)"/></td>
							</tr>
						</c:forEach>
             		</c:when>
             		<c:otherwise>
             		<tr>
             			<td colspan="3">해당 조건의 약품이 검색되지 않습니다.</td>
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
					<a href="<c:url value="/board/boardMain" >
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
				<a href="<c:url value="/board/boardMain" >
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
     			<li class="dir next"><a href="<c:url value="/board/boardMain">
      			<c:param name="page" value="${searchpaging.nowPageGroup*searchpaging.pageGroupSize+1 }"/>
      			<c:param name="searchOption" value="${searchpaging.searchOption}" />
      			<c:param name="searchValue" value="${searchpaging.searchValue}" />
      			<c:param name="date" value="${date}" />
      			</c:url>" onclick=" " title="다음페이지로 이동">»</a></li>
       		</c:if>
   		</ul>
	</div>
		            
					<form id="selectPatientInfo" action="<c:url value="/prescription/selectPatientInfo"/>" method="POST">
						<input type="hidden" id="patientID" name="patientID"/>
					</form>
					
					<form id="writeBoardForm" action="<c:url value="/board/writeBoard"/>" method="POST">
						<input id="writeReplyBoardParentID" name="parentID" type="hidden">
						<input id="writeReplyBoardDepth" name="depth" type="hidden">
						<input type="submit" style="float:right" class="mainbtn" value="글쓰기">
					</form>
					
						<form id="selectBody" action="<c:url value="/board/selectBody"/>" method="POST">
							<input id="selectBodyInput" name="boardID" type="hidden"/>
						</form>	
						<form id="updateBody" action="<c:url value="/board/updateBody"/>" method="POST">
							<input id="updateBodyInput" name="boardID" type="hidden" />
						</form>	
					</div>
					<!-- 컨텐츠영역 txt :e -->
					</div>
					</div>
			</div>
			<!-- container :e -->

	
		<!-- contentsWrap :e -->
	

		<!-- footerWrap :s -->
		<div id="footerWrap">
			<c:import url="../common/footer.jsp"/>
		</div>
		<!-- footerWrap :e -->

	
</body>
</html>