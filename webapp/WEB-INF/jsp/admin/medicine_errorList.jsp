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
		<c:if test="${param.uploadFile=='true'}">
			alert("파일이 등록되었습니다.");
		</c:if>
		$("#searchButton").click(function(){
			if($("#searchValue").val() =="" || regExp.test($("#searchValue").val())){
				alert("검색어를 바르게 입력하세요(공백X).");
				return false;
			}
		});
	});
	function checkError(code,fullname,name,atccode){
		if(code=="#N/A")
			alert("제품 코드에 오류가 있습니다.");
		else if(fullname=="#N/A")
			alert("상세 제품명에 오류가 있습니다.");
		else if(name=="#N/A")
			alert("제품명에 오류가 있습니다.");
		else if(atccode=="#N/A")
			alert("ATC코드에 오류가 있습니다.");
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
					<h2>엑셀 오류 약물</h2>
				</div>			
				<c:import url="../common/admin/submenu2.jsp">
					<c:param name="menuType" value="3"/>
				</c:import>
			</div>
			<!-- lnbWrap :e -->

			<!-- container :s -->
			<div id="container">

				<div class="location">
					<span class="home"><a href="<c:url value="/admin/accountManagement"/>"><img src="<c:url value="/img/layout/ico_home.png"/>" alt="홈"></a></span>
					<span class="arrow">&gt;</span>
					<a href="#"> <span class="first">약물 DB 관리</span></a>
					<span class="arrow">&gt;</span>
					<a href="<c:url value="/admin/medicineErrorList"/>"> <span>엑셀 오류 약물</span></a>
				</div>

				<h3 id="contTi">엑셀 오류 약물</h3>

				<!-- 컨텐츠영역 txt :s -->
				<div id="txt">
					
					<h4> 엑셀 오류 약물 목록(총  <c:out value="${paging.count }"/>개의 목록이 있습니다.)</h4>
					<!-- table :s -->
					<table class="tbl_basic center" style="table-layout:fixed;">
			            <caption>
			                <strong>엑셀 오류 약물 목록</strong>
			                <details>
			                    <summary>엑셀 오류 약물 목록</summary>
			                </details>
			            </caption>
		                <thead>
		                    <tr>
		                        <th class="trw" width="30">No</th>
		                        <th class="trw" width="80">대표코드</th>
		                        <th class="trw" width="150">상세제품명</th>
		                        <th class="trw" width="150">제품명</th>
		                        <th class="trw" width="90">ATC 코드</th>
		                        <th width="100">오류 확인</th>
		                    </tr>
		                </thead>
		                <tbody>
							<c:set var="cnt" value="${paging.startRow }"/>
		                	<c:forEach var="errorMedicine" items="${errorMedicineList}">
								<c:set var="cnt" value="${cnt+1 }"/>
								<tr>
									<td><c:out value="${cnt }"/></td>
									<td><c:out value="${errorMedicine.code }"/></td>
									<td><c:out value="${errorMedicine.fullName }"/></td>
									<td><c:out value="${errorMedicine.name }"/></td>
									<td><c:out value="${errorMedicine.atc.atcCode }"/></td>
									<td><input type="button" value="선택"  class="btn_save" onclick="checkError('<c:out value="${errorMedicine.code }"/>','<c:out value="${errorMedicine.fullName }"/>','<c:out value="${errorMedicine.name }"/>','<c:out value="${errorMedicine.atc.atcCode }"/>')"></td>
								</tr>
							</c:forEach>
		                </tbody>
		            </table>
		            <!-- table :e -->
					
		            <!-- paging & search :s -->
		            <ul class="paginate">
	            		<c:if test="${paging.nowPageGroup > 1 }">
							<li class="dir prev">
								<a href="<c:url value="/admin/medicineErrorList?page=${(paging.nowPageGroup-2)*paging.pageGroupSize+1 }"/>" onclick="" title="이전페이지로 이동">«</a>
							</li>
						</c:if>
	            		<c:forEach var="i" begin="${paging.startPage }" end="${paging.endPage }">
            				<c:choose>
            					<c:when test="${param.page == i }">
            						<li class="active">
            							<a><c:out value="${i }"/></a>
            					</c:when>
            					<c:otherwise>
            						<li>
	            						<a href="<c:url value="/admin/medicineErrorList?page=${i }"/>" title="<c:out value="${i }"/>페이지">
	            							<c:out value="${i }"/>
	           							</a>
            					</c:otherwise>
            				</c:choose>
           							</li>
	            		</c:forEach>
	            		<c:if test="${paging.nowPageGroup < paging.pageGroupCount }">
	            			<li class="dir next"><a href="<c:url value="/admin/medicineErrorList?page=${paging.nowPageGroup*paging.pageGroupSize+1 }"/>" onclick="" title="다음페이지로 이동">»</a></li>
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