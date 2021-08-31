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
	$(document).ready(function(){
		
		$("#add_btn").click(function(){
			location.href="<c:url value="/admin/writeHospital"/>";	
		});
	});
	
	function delHospital(id) {
		if(confirm("정말로 삭제하시겠습니까?")) {
			location.href="<c:url value="/admin/deleteHospital"/>?id="+id;	
		}		
	}
	
	function modifyHospital(id) {
		location.href="<c:url value="/admin/modifyHospital"/>?id="+id;	
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
					<h2>기타 관리</h2>
				</div>			
				<c:import url="../common/admin/submenu3.jsp">
					<c:param name="menuType" value="1"/>
				</c:import>
			</div>
			<!-- lnbWrap :e -->

			<!-- container :s -->
			<div id="container">

				<div class="location">
					<span class="home"><a href="<c:url value="/admin/accountManagement"/>"><img src="<c:url value="/img/layout/ico_home.png"/>" alt="홈"></a></span>
					<span class="arrow">&gt;</span>
					<a href="#"> <span class="first">기타관리</span></a>
					<span class="arrow">&gt;</span>
					<a href="<c:url value="/admin/hospitalManagement"/>"> <span>참여병원관리</span></a>
				</div>

				<h3 id="contTi">참여병원관리</h3>

				<!-- 컨텐츠영역 txt :s -->
				<div id="txt">
					<h4>참여병원리스트</h4>
					<!-- table :s -->
					<table class="tbl_basic center" style="table-layout:fixed;">
			            <caption>
			                <strong>참여병원리스트</strong>
			                <details>
			                    <summary>참여병원리스트</summary>
			                </details>
			            </caption>
		                <thead>
		                    <tr>
		                        <th class="trw" width="35">No</th>
		                        <th class="trw">참여병원</th>
		                        <th class="trw" width="80"></th>
		                        <th class="trw" width="80"></th>
		                    </tr>
		                </thead>
		                <tbody>
		                	<c:set var="cnt" value="1"/>
		                	<c:forEach items="${hospitals }" var="hospital">
	                		<tr>
	                			<td><c:out value="${cnt }"/></td>
	                			<td><c:out value="${hospital.name }"/> </td>
		                		<td><input type="button" value="수정" class="btn_default" onclick="modifyHospital(<c:out value="${hospital.id }"/>)"></td>
		                		<td><input type="button" value="삭제" class="btn_save" onclick="delHospital(<c:out value="${hospital.id }"/>)"></td>
	                		</tr>
	                		<c:set var="cnt" value="${cnt + 1 }"/>
		                	</c:forEach>
		                </tbody>
		            </table>
		            <!-- table :e -->
		            <ul class="search_info">
		            	<li>
							<input type="button" id="add_btn" value="병원추가" class="submitbtn2" />
						</li>
					</ul>
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