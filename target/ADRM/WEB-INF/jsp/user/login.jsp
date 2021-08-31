<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="shortcut icon" href="<c:url value="/img/logo.png"/>">
<title>ADRM</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/normalize.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/signIn.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/font.css"/>">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="<c:url value="/js/jquery.validate.min.js"/>"></script>

<script type="text/javascript">
	$(document).ready(function(){
		var userid = getCookie("userid");
		$("input[name='id']").val(userid);
		
		if($("input[name='id']").val()!=""){
			$("#login_chk").attr("checked",true);
		}
		$("#login_chk").change(function(){
			if($("#login_chk").is(":checked")){
				var userid = $("input[name='id']").val();
				setCookie("userid",userid,7);
			}else{
				deletecCookie("userid");
			}
		});
		$("input[name='id']").keyup(function(){
			if($("#login_chk").is(":checked")){
				var userid = $("input[name='id']").val();
				setCookie("userid",userid,7);
			}
		});
		<c:choose>
			<c:when test="${param.resultType == 'ERROR'}">
				alert("아이디 또는 비밀번호를 확인해 주세요.");
			</c:when>
			<c:when test="${param.resultType == 'NOT_APPROVAL'}">
				alert("비활성화 상태인 계정입니다. 로그인을 할 수 없습니다.");
			</c:when>
			<c:when test="${param.resultType == 'IDSEND'}">
				alert("메일로 아이디가 전송되었습니다.");
			</c:when>
			<c:when test="${param.resultType == 'PWSEND'}">
				alert("메일로 비밀번호가 전송되었습니다.");
			</c:when>
		</c:choose>
		$("#frmNIDLogin").validate({
			rules: {
				id: "required",
				pw: "required"
			},
			messages: {
				id: "아이디를 입력해 주세요.",
				pw: "비밀번호를 입력해 주세요."
			}
		});
	});
function setCookie(cookieName, value, exdays){
		var exdate = new Date();
		exdate.setDate(exdate.getDate() + exdays);
	    var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
	    document.cookie = cookieName + "=" + cookieValue;
	}
function deleteCookie(cookieName){
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}
function getCookie(cookieName) {
    cookieName = cookieName + '=';
    var cookieData = document.cookie;
    var start = cookieData.indexOf(cookieName);
    var cookieValue = '';
    if(start != -1){
        start += cookieName.length;
        var end = cookieData.indexOf(';', start);
        if(end == -1)end = cookieData.length;
        cookieValue = cookieData.substring(start, end);
    }
    return unescape(cookieValue);
}
	
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
			<h1><a href="<c:url value="/main/selectFunc"/>" class="sp h_logo" >약물 알레르기 알리미</a></h1>
		</div>
		<!-- header :e -->

		<!-- container :s -->
		<div id="container">
			<div id="content">
				<form id="frmNIDLogin" name="frmNIDLogin" target="_top" method="post" action="<c:url value="/user/loginCheck"/>">
					<fieldset class="findif_form">
						<legend class="blind">Sign In</legend>

						<label for="id" class="error"></label>
						<div class="input_row" id="id_area">
							<span class="input_box">
								<label for="id" id="label_id_area" class="lbl">ID</label>
								<input type="text" id="id" name="id"  placeholder="ID" class="int" maxlength="30" value="">
							</span>
						</div>
						<label for="pw" class="error"></label>
						<div class="input_row" id="pw_area">
							<span class="input_box">
								<label for="pw" id="label_pw_area" class="lbl">Password</label>
								<input type="password" id="pw" name="pw"  placeholder="Password" class="int" maxlength="30" >
							</span>
						</div>
						
						<div class="login_check">
							<span class="login_check_box">
								<input type="checkbox" id="login_chk" name="nvlong" class="" tabindex="9" value="off" onchange="savedLong(this);nclks_chk('login_chk', 'log.keepon', 'log.keepoff',this,event)" onclick="msieblur(this);">
								<label for="login_chk" id="label_login_chk" class="sp">아이디 저장</label>
							</span>
						</div>

						<input type="submit" id="btn_login" title="로그인" alt="로그인" value="로그인" class="btn_login" onclick=" ">
						
						<div class="find_info">
							<ul>
								<li><a href="<c:url value="/user/idFind"/>">아이디 찾기</a></li>
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
				<!-- <P class="f_logo"><span class="blind">충북대학교병원</span></P> -->
				<p> Copyright  ⓒ  CHUNGBUK NATIONAL UNIVERSITY HOSPITAL.<span> ALL RIGHTS RESERVED</span></p>
			</address>
		</div>
		<!-- footer :e	 -->

	

	</div>
<!-- wrap :e -->

</body>
</html>
