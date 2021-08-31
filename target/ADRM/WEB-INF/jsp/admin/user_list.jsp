<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.jsp" %>

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
<script src="<c:url value="/js/jquery-ui-1.10.3.custom.min.js"/>"></script>
<script src="<c:url value="/js/submenu.js"/>"></script>
<script type="text/javascript">
	var regExp = /\s/g;
	
	$(document).ready(function(){		
		$("#searchButton").click(function(){
			if($("#search").val() =="" || regExp.test($("#search").val())){
				alert("검색어를 바르게 입력하세요(공백X).");
				return false;
			}
			$("#searchForm").submit();
		});
	});
	
	function accountDisable(cnt, userID) {
		var val = $(":input:radio[name=disable"+cnt+"]:checked").val();
		
		$("#userID").val(userID);
		$("#isDisable").val(val);
		$("#page").val("1");
		<c:if test="${param.page != null}">
		$("#page").val("<c:out value="${param.page}"/>");	
		</c:if>
		$("#managementFrm").submit();
	}
	
</script>
</head>

<body>
<!-- wrap :s -->
	<div id="wrapper">

		<!-- header :s -->
		<div id="headerWrap">
			<c:import url="../common/admin/header.jsp"/>
		</div>
		<!-- header :e -->


		<!-- contentsWrap :s -->
		<div id="contentsWrap">

			<!-- lnbWrap :s -->
			<div id="lnbWrap">
				<div id="lnb_title">
					<h2>사용자 관리</h2>
				</div>			
				<c:import url="../common/admin/submenu1.jsp">
					<c:param name="menuType" value="1"/>
				</c:import>
			</div>
			<!-- lnbWrap :e -->

			<!-- container :s -->
			<div id="container">

				<div class="location">
					<span class="home"><a href="<c:url value="/admin/accountManagement"/>"><img src="<c:url value="/img/layout/ico_home.png"/>" alt="홈"></a></span>
					<span class="arrow">&gt;</span>
					<a href="#"> <span class="first">사용자 관리</span></a>
					<span class="arrow">&gt;</span>
					<a href="<c:url value="/admin/accountManagement"/>"> <span>계정 관리</span></a>
				</div>

				<h3 id="contTi">계정 관리</h3>

				<!-- 컨텐츠영역 txt :s -->
				<div id="txt">
					
					<h4>계정 목록</h4>
					<!-- table :s -->
					<table class="tbl_basic" style="table-layout:fixed;">
			            <caption>
			                <strong>계정목록</strong>
			                <details>
			                    <summary>계정 목록</summary>
			                </details>
			            </caption>
		                <thead>
		                    <tr>
		                        <th class="trw" width=30>No</th>
		                        <th class="trw" width=100>아이디</th>
		                        <th class="trw" width=40>이름</th>
		                        <th class="trw" width=40>계정<br>유형</th>
		                        <th class="trw" width=90>병원</th>
		                        <th class="trw" width=80>병원 코드</th>
		                        <th class="trw" width=120>E-Mail</th>
		                        <th class="trw" >가입 날짜</th>
		                        <th class="trw" >차단</th>
		                        <th class="trw" >저장</th>
		                    </tr>
		                </thead>
		                <tbody>
							<c:set var="cnt" value="${paging.startRow }"/>
		                	<c:forEach var="user" items="${userList}">
								<c:set var="cnt" value="${cnt+1 }"/>
								<tr>
									<td><c:out value="${cnt }"/></td>
									<td><c:out value="${user.userId }"/></td> 
									<td><c:out value="${user.name }"/></td>
									<td>
										<c:forEach items="${user.roles }" var="role">
											<c:choose>
												<c:when test="${role.userRole.getValue() == 1 }">
													<p>관리자</p>
												</c:when>
												<c:when test="${role.userRole.getValue() == 2 }">
													<p>의사</p>
												</c:when>
												<c:otherwise>
													<p>환자</p>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</td>
									<td><c:out value="${user.hospital.name }"/></td>
									<td><c:out value="${user.cbnuCode }"/></td>
									<td><c:out value="${user.email }"/></td>
									<td><fmt:formatDate value="${user.date }"  pattern="yy-MM-dd"/></td>
									<td>
									<c:choose>
										<c:when test="${user.userId == 'admin' }">
											수정불가
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${user.disable == 'N' }">
													N<input type="radio" name='disable<c:out value="${cnt }"/>' value="N" checked>
													Y<input type="radio" name='disable<c:out value="${cnt }"/>' value="Y">
												</c:when>
												<c:otherwise>
													N<input type="radio" name='disable<c:out value="${cnt }"/>' value="N" >
													Y<input type="radio" name='disable<c:out value="${cnt }"/>' value="Y" checked>				
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
									</td>
									<td>
									<c:choose>
										<c:when test="${user.userId == 'admin' }">
											수정불가
										</c:when>
										<c:otherwise>
											<input type="button" value="저장" class="btn_save" onclick="accountDisable(<c:out value="${cnt }"/>, '<c:out value="${user.userId }"/>')">
										</c:otherwise>
									</c:choose>
									</td>
								</tr>
							</c:forEach>
		                </tbody>
		            </table>
		            <!-- table :e -->
		            
					<form id="managementFrm" action="<c:url value="/admin/disableAccount"/>" method="POST">
						<input type="hidden" id="userID" name="userID"/>
						<input type="hidden" id="isDisable" name="isDisable"/>
						<input type="hidden" id="page" name="page"/>
					</form>
					
		            <!-- paging & search :s -->
		            <ul class="paginate">
	            		<c:if test="${paging.nowPageGroup > 1 }">
							<li class="dir prev">
								<a href="<c:url value="/admin/accountManagement?page=${(paging.nowPageGroup-2)*paging.pageGroupSize+1 }"/>" onclick="" title="이전페이지로 이동">«</a>
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
            						<a href="<c:url value="/admin/accountManagement?page=${i }"/>" title="<c:out value="${i }"/>페이지">
            							<c:out value="${i }"/>
           							</a>
           							</li>
	            		</c:forEach>
	            		<c:if test="${paging.nowPageGroup < paging.pageGroupCount }">
	            			<li class="dir next"><a href="<c:url value="/admin/accountManagement?page=${paging.nowPageGroup*paging.pageGroupSize+1 }"/>" onclick="" title="다음페이지로 이동">»</a></li>
	            		</c:if>
					</ul>
		            <!-- paging & search :e -->
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