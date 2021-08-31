<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@include file="/WEB-INF/jsp/common/include.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="X-UA-Compatible" content="IE=8">
<title>ADRM</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/normalize.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/join.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/font.css"/>">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="<c:url value="/js/jquery.validate.min.js"/>"></script>
<script src="<c:url value="/js/jquery.validate.extend.js"/>"></script>

<script type="text/javascript">
	$(document).ready(function(){
		$("#cancelBtn").click(function(){
			location.href="<c:url value="/user/login"/>";
		});
		$("#agreeBtn").click(function(){
			location.href='<c:url value="/user/join?agree=1"/>';
		});
	});
	
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
		<!-- header :s -->
		<div id="header">
			<h1><a href="<c:url value="/main/selectFunc"/>" class="sp h_logo" >ADRM</a></h1>
		</div>
		<!-- header :e -->

		<!-- container :s -->
		<div id="container">
			<div id="content1">
				<h4>개인정보처리취급방침</h4>
				<fieldset class="text_area" style="letter-spacing:0.1em;word-spacing: 0.2em;line-height: 1.5">
				<b>‘약물 알레르기 알리미’</b> 앱은 약물알레르기 환자들이 알레르기를 유발할 수 있는 약물 복용을 예방할 수 있도록 하기 위해 기획되었습니다. 
				<b>‘내 손안의 맟춤형 DUR: 스마트폰을 활용한 환자맞춤형 약물 알레르기 예방시스템 구축’</b> 연구(HI17C1081, 책임연구자: 충북대학교병원 알레르기내과 교수 강민규)는 2017년 보건복지부 환자안전연구과제로 선정되어 진행중입니다.<br><br> 
                <b>‘약묵 알레르기 알리미’</b> 앱은 약물 알레르기 예방시스템 구축을 위해, 어플리케이션에 가입한 환자들의 다음과 같은 정보를 수집하고 있습니다.<br><br>
				&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;이름, 병원명, 병원환자번호, *이메일주소<br>
				&nbsp;&nbsp;(*이메일 주소는 향후 ID분실시 인증 및 임시비밀번호 전송을 위해 수집하고 있습니다.)<br><br>  
				&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;약물알레르기 관련 정보(증상, 금기약제, 주의약제, 대체약제)<br><br>  
				‘약물 알레르기 알리미’ 앱은 가입시 제공된 개인정보를 외부(타사)에 저공 및 위탁하지 않을 예정이며, 회원 탈퇴를 요청할 경우 데이터베이스에 저장된 모든 개인정보를 삭제 및 파기하겠습니다.
				
				</fieldset>
				<fieldset class="login_form">
					<input id="cancelBtn" type="button" title="취소" alt="취소" value="취소" class="btn_cancel">
					<input id="agreeBtn" type="button" title="동의합니다." alt="동의합니다." value="동의합니다." class="btn_login">
				</fieldset>
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