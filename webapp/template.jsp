<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>ADRM</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/normalize.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/layout.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/font.css"/>">

<script src="<c:url value="/js/jquery-3.1.1.min.js"/>"></script>
<script src="<c:url value="/js/jquery-ui-1.10.3.custom.min.js"/>"></script>
<script src="<c:url value="/js/submenu.js"/>"></script>
</head>

<body>
<!-- wrap :s -->
	<div id="wrapper">

		<!-- header :s -->
		<div id="headerWrap">
			<c:import url="/WEB-INF/jsp/common/admin/header.jsp"/>
		</div>
		<!-- header :e -->


		<!-- contentsWrap :s -->
		<div id="contentsWrap">

			<!-- lnbWrap :s -->
			<div id="lnbWrap">
				<div id="lnb_title">
					<h2>사용자 관리</h2>
				</div>			
				<c:import url="/WEB-INF/jsp/common/admin/submenu1.jsp">
					<c:param name="menuType" value="1"/>
				</c:import>
			</div>
			<!-- lnbWrap :e -->

			<!-- container :s -->
			<div id="container">

				<div class="location">
					<span class="home"><a href="#"><img src="<c:url value="/img/layout/ico_home.png"/>" alt="홈"></a></span>
					<span class="arrow">&gt;</span>
					<a href="#"> <span class="first">사용자 관리</span></a>
					<span class="arrow">&gt;</span>
					<a href="#"> <span>계정 관리</span></a>
				</div>

				<h3 id="contTi">사용자 관리</h3>

				<!-- 컨텐츠영역 txt :s -->
				<div id="txt">
					
					<h4>계정 목록</h4>
					<!-- table :s -->
					<table class="tbl_basic center">
			            <caption>
			                <strong>계정목록</strong>
			                <details>
			                    <summary>계정 목록</summary>
			                </details>
			            </caption>
		                <thead>
		                    <tr>
		                        <th class="trw">No</th>
		                        <th class="trw">아이디</th>
		                        <th class="trw">이름</th>
		                        <th class="trw">계정 유형</th>
		                        <th class="trw">충북대 코드</th>
		                        <th class="trw">E-Mail</th>
		                        <th class="trw">가입 날짜</th>
		                        <th class="trw">차단</th>
		                        <th class="trw">저장</th>
		                    </tr>
		                </thead>
		                <tbody>
							<tr>
								<td>contents 1</td>
								<td>contents 2</td>
								<td>contents 3</td>
								<td>contents 4</td>
								<td>contents 5</td>
								<td>contents 6</td>
								<td>contents 7</td>
								<td>contents 8</td>
								<td>contents 9</td>
							</tr>
		                </tbody>
		            </table>
		            <!-- table :e -->
					
		            <!-- paging & search :s -->
		            
		            <!-- paging & search :e -->
				</div>
				<!-- 컨텐츠영역 txt :e -->

			</div>
			<!-- container :e -->

		</div>
		<!-- contentsWrap :e -->

		
		<!-- footerWrap :s -->
		<div id="footerWrap">
			<c:import url="/WEB-INF/jsp/common/footer.jsp"/>
		</div>
		<!-- footerWrap :e -->
	</div>
</body>
</html>