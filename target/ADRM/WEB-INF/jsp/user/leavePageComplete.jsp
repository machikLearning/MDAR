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
<link rel="stylesheet" type="text/css" href="<c:url value="/css/join.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/font.css"/>">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="<c:url value="/js/jquery.validate.min.js"/>"></script>
<script src="<c:url value="/js/jquery.validate.extend.js"/>"></script>

<script type="text/javascript">
	$(document).ready(function(){
		<c:if test="${param.resultType == 'REJECT'}">
			alert("회원정보 수정에 실패하였습니다.\n 다시 시도해 주세요.");
		</c:if>
		$("#cancelBtn").click(function(){
			location.href="<c:url value="/main/selectFunc"/>"
		});
		$.validator.addMethod("namecheck",  function( value, element ) {
			return this.optional(element) || /^[^0-9]*$/g.test(value);
		}); 
	})
	
	function goMain(){
		$("#goMain").submit()
	}
</script>
<style type="text/css">
	#joinFrm label.error {
		color: #ff0000;
		display: none;
	}
</style>
<title>ADRM</title>
</head>
<body>
	<!-- wrap :s -->
	<div id="wrap">	
		<div id="header"><h1><a href="<c:url value="/main/selectFunc"/>" class="sp h_logo" >ADRM</a></h1></div>
		<div id="container">
			<div id="content" style="text-align:center">
				그동안 사용해 주셔서 감사합니다
				<button class="btn_login" onclick="goMain()">메인으로 가기</button>
			</div>
		</div>
		<form id="goMain" action="<c:url value="/main/selectFunc"/>"></form>
		
	
		<!-- container :e -->

		<!-- footer :s -->
		<div id="footer">
	
			<address>
				<!-- <P class="f_logo"><span class="blind">충북대학교병원</span></P> -->
				<p> Copyright  ⓒ  CHUNGBUK NATIONAL UNIVERSITY HOSPITAL.<span> ALL RIGHTS RESERVED</span></p>
			</address>
		</div>
		<!-- footer :e	 -->
	</div>
<!-- wrap :e -->
</body>
</html>