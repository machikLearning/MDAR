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
	$(document).ready(function(){
		<c:if test="${param.error == 1}">
			alert("아이디 찾기에 실패하였습니다.\n 메일을 다시 확인해 주세요");
		</c:if>
		$("#idSelect").click(function(){
			var mail= $("#email").val();
			if(mail == ""){
				alert("이메일을 입력하세요.");
				return false;
			}
		});
	});
</script>	
<style type="text/css">
	#frmNIDLogin label.error {
		color: #ff0000;
		display: none;
	}
</style>
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
				<form id="frmNIDLogin" name="frmNIDLogin" target="_top" action="<c:url value="/user/idSend"/>">
					<fieldset class="login_form">
						<div class="input_row" id="pw_area">
							<span class="input_box">
								<label for="id" id="label_id_area" class="lbl">이메일</label>
								<input type="text" id="email" name="email"  placeholder="E-MAIL" class="int" maxlength="30" >
							</span>
						</div>
						<input type="submit" id="idSelect" title="아이디 찾기" alt="아이디 찾기" value="아이디 찾기" class="btn_login" >
						
						<div class="find_info">
							<ul>
								<li><a href="<c:url value="/user/login"/>">로그인</a></li>
								<li><a href="<c:url value="/user/pwFind"/>">비밀번호 찾기</a></li>
								<li><a href="<c:url value="/user/join"/>">회원가입</a></li>
							</ul>
						</div>
					</fieldset>
				</form>
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
