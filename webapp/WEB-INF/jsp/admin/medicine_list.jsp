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
		$("#fileSubmit").click(function(){
			if($("#fileRoute2").val() ==""){
				alert("파일을 올려주세요");
				return false;
			}
			if($("#fileRoute2").val() !=""){
				var ext= $("#fileRoute2").val().split('.').pop().toLowerCase();
				if ($.inArray(ext, ['xlsx']) == -1) {
					alert("xlsx 확장자만 업로드 할 수 없습니다");
					return false;
	        	}
				
			}
			$("#fileSubmit").submit();
		});
	});
	
	function pagingSubmit(page) {
		$("#page").val(page);
		$("#medicineSearch").submit();
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
					<h2>약물 DB 관리</h2>
				</div>			
				<c:import url="../common/admin/submenu2.jsp">
					<c:param name="menuType" value="2"/>
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
					<a href="<c:url value="/admin/medicineManagement"/>"> <span>약물 데이터베이스 관리</span></a>
				</div>

				<h3 id="contTi">약물 데이터베이스 관리</h3>
				<!-- 컨텐츠영역 txt :s -->
				<div id="txt">
				
					
					
					
					<div class="ui uit type1">
				      <div class="inner">
				          <div class="txtwrap">
				              
							<form action="<c:url value="/admin/medicineExcelUpload"/>" method="POST" id="uploadFrm" enctype="multipart/form-data" name="fileManager" class="fileManager">
								<p class="fileText">
								<strong>약물 데이터베이스 업데이트</strong>
								<input type="text" name="fileRoute2" id="fileRoute2" class="fileRoute2" readonly="">
								<label>파일찾기 
									<input type="file" id="excelFile" name="excelFile" class="fileBtn"  onchange="javascript:document.getElementById('fileRoute2').value=this.value">
								</label>
								<input type="submit" value="저장하기" class="fileSubmit" id="fileSubmit">
								</p>
							</form>
				              
				          </div>            
				      </div>    
				    </div>			    
				    <a href="<c:url value="/admin/medicineExcelDownload"/>" class="btn-file"> 약물 양식 다운로드 하기 <img src="<c:url value="/img/download-icon.png"/>"></a>
					
					
					<h4>약물 데이터베이스 목록(총  <c:out value="${paging.count }"/>개의 목록이 있습니다.)</h4>
					<!-- table :s -->
					<table class="tbl_basic center" style="table-layout:fixed;">
			            <caption>
			                <strong>약물 데이터베이스 목록</strong>
			                <details>
			                    <summary>약물 데이터베이스 목록</summary>
			                </details>
			            </caption>
		                <thead>
		                    <tr>
		                        <th class="trw" width="45">No</th>
		                        <th class="trw" width="120">대표코드</th>
		                        <th class="trw" width="300">제품명</th>
		                        <th >ATC 이름</th>
		                    </tr>
		                </thead>
		                <tbody>
							<c:set var="cnt" value="${paging.startRow }"/>
		                	<c:forEach var="medicine" items="${medicineList}">
								<c:set var="cnt" value="${cnt+1 }"/>
								<tr>
									<td><c:out value="${cnt }"/></td>
									<td><c:out value="${medicine.code }"/></td>
									<td><c:out value="${medicine.fullName }"/></td>
									<td><c:out value="${medicine.atc.atcName }"/></td>
								</tr>
							</c:forEach>
		                </tbody>
		            </table>
		            <!-- table :e -->
					
		            <!-- paging & search :s -->
		            <ul class="paginate">
	            		<c:if test="${paging.nowPageGroup > 1 }">
							<li class="dir prev">
								<a href="javascript:pagingSubmit(${(paging.nowPageGroup-2)*paging.pageGroupSize+1 })" onclick="" title="이전페이지로 이동">«</a>
							</li>
						</c:if>
	            		<c:forEach var="i" begin="${paging.startPage }" end="${paging.endPage }">
            				<c:choose>
            					<c:when test="${paging.nowPage == i }">
            						<li class="active">
            							<a><c:out value="${i }"/></a>
            					</c:when>
            					<c:otherwise>
            						<li>
	            						<a href="javascript:pagingSubmit(${i })" title="<c:out value="${i }"/>페이지">
	            							<c:out value="${i }"/>
	           							</a>
            					</c:otherwise>
            				</c:choose>
           							</li>
	            		</c:forEach>
	            		<c:if test="${paging.nowPageGroup < paging.pageGroupCount }">
	            			<li class="dir next"><a href="javascript:pagingSubmit(${paging.nowPageGroup*paging.pageGroupSize+1 })" onclick="" title="다음페이지로 이동">»</a></li>
	            		</c:if>
						<form class="search_info" id="medicineSearch" name="medicineSearch" action="<c:url value="/admin/medicineManagement"/>" method="POST">
							<li>
								<select name="searchOption">
									<option value="medicineCode" <c:if test="${searchInfo.searchOption == 'medicineCode'}">selected</c:if>>대표코드</option>
									<option value="medicineFullName" <c:if test="${searchInfo.searchOption == 'medicineFullName'}">selected</c:if>>제품명</option>
									<option value="ATCcode" <c:if test="${searchInfo.searchOption == 'ATCcode'}">selected</c:if>>ATC코드</option>
								</select>
								<input type="text" id="searchValue" name="searchValue" class="inputText" placeholder="조건 입력" value="${searchInfo.param }"/>
								<input type="submit" id="searchButton" name="searchButton" value="검색"  class="submitbtn"/>
							</li>
							<input type="hidden" name="page" id="page" />
						</form>
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