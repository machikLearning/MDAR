<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="X-UA-Compatible" content="IE=8">
<link rel="shortcut icon" href="<c:url value="/img/logo.png"/>">
<title>ADRM</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/normalize.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/signIn.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/font.css"/>">
<script src="<c:url value="/js/jquery-3.1.1.min.js"/>"></script>
<script src="<c:url value="/js/jquery.validate.min.js"/>"></script>
<script type="text/javascript">
	function historyBack(){
    	history.back();
	} 
</script>	
</head>
<body>
<!-- wrap :s -->
	<div id="wrap">
		<!-- header :s -->
		<div id="header">
			<h1><a href="<c:url value="/main/selectFunc"/>" class="sp h_logo" >ADRM</a></h1>
		</div>
		<!-- header :e -->

		<!-- container :s -->
		<div id="container">
			<div id="content">
				<h4>잘못된 접근입니다.</h4>		
				<input type="button" id="mainBtn" title="뒤로 가기" alt="뒤로 가기" value="뒤로 가기" class="btn_login" onclick="historyBack()">

			</div>
		</div>
		<!-- container :e -->

		<!-- footer :s -->
		<div id="footer">
	
			<address>
				<P class="f_logo"><span class="blind">충북대학교병원</span></P>
				<p> Copyright  ⓒ  CHUNGBUK NATIONAL UNIVERSITY HOSPITAL. ALL RIGHT</p>
			</address>
		</div>
		<!-- footer :e	 -->

	

	</div>
<!-- wrap :e -->

</body>
</html>
