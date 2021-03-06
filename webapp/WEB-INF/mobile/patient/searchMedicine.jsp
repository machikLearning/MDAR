<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("newline", "\n"); %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<!-- initial-scale=1.0 : 기본확대 1배율 , user-scalable=no : 사용자확대,축소불가  -->
<meta name="viewport" content="width=device-width"> <!-- 디바이스 크기에 일치시킴 -->
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/default.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/font.css"/>">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="<c:url value="/js/jquery.validate.min.js"/>"></script>
<script src="<c:url value="/js/submenu.js"/>"></script>
<script type="text/javascript">
	$(document).ready(function(){		
		$("#searchButton").click(function(){
			if($("#searchValue").val() ==""){
				alert("검색어를 바르게 입력하세요(공백X).");
				return false;
			}
			$("#searchMedicine").submit();
		});
	});
</script>
</head>
<body>
<!-- wrap :s -->
    <div id="wrap">
        <!-- content :s -->
		<div id="content">
			<div id="searchDiv">
				<form id="searchMedicine" name="searchMedicine" class="search_info" action="<c:url value="/patient/searchMedicineResult"/>" method="POST">
					<select name="searchOption" class="searchOption">
						<option value="medicineFullName">약품 이름</option>
						<option value="medicineCode">약품 번호</option>
					</select>
					<input type="text" id="searchValue" name="searchValue" class="inputText" placeholder="조건 입력"/>
					<input type="hidden" id="id" name="id" value="${id }"/>
					<input type="submit" id="searchButton" name="searchButton" value="검색" class="submitbtn"/>
				</form>
			</div>
		</div>
        <!-- content :e -->

        <!-- footer :s -->
        <div id="footer">
    
            <address>
               <!--  <P class="f_logo"><span class="blind">충북대학교병원</span></P> -->
                <p> Copyright  ⓒ  CHUNGBUK NATIONAL UNIVERSITY HOSPITAL. ALL RIGHT</p>
            </address>
        </div>
        <!-- footer :e   -->

    

    </div>
<!-- wrap :e -->

</body>
</html>
