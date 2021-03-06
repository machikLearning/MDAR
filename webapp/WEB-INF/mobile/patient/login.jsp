<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

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
			<c:when test="${param.resultType == 'NOT_PATIENT'}">
				alert("환자 외의 접근은 준비중입니다.");
			</c:when>
			<c:when test="${param.resultType == 'DONTHAVEROLE'}">
				alert("권한이 없습니다.");
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
            <h1><a href="<c:url value="/patient/main"/>" class="sp h_logo" >충북대학교병원</a></h1>
        </div>
        <!-- header :e -->

        <!-- container :s -->
        <div id="container">
            <div id="content">
                <form id="frmNIDLogin" name="frmNIDLogin" target="_top" class="mt_50" method="post" action="<c:url value="/patient/loginCheck"/>">
                    <fieldset class="login_form">
                        <legend class="blind">Sign In</legend>
                        
						<label for="id" class="error"></label>
                        <div class="input_row" id="id_area">
                            <span class="input_box">
                                <label for="id" id="label_id_area" class="lbl">ID</label>
                                <input type="text" id="id" name="id"  placeholder="ID" class="int" maxlength="41" value="">
                            </span>
                        </div>
                        
						<label for="pw" class="error"></label>
                        <div class="input_row" id="pw_area">
                            <span class="input_box">
                                <label for="pw" id="label_pw_area" class="lbl">Password</label>
                                <input type="password" id="pw" name="pw"  placeholder="Password" class="int" >
                            </span>
                        </div>

                        <div class="login_check">
                            <span class="login_check_box">
                                <input type="checkbox" id="login_chk" name="nvlong" class="" tabindex="9" value="off">
                                <label for="login_chk" id="label_login_chk" class="sp">아이디 저장</label>
                            </span>
                        </div>

                        <input type="submit" title="로그인" alt="로그인" value="로그인" class="btn_login" onclick=" ">
                        
                        <div class="find_info">
                            <ul>
                                <li><a href="#">아이디 찾기</a></li>
                                <li><a href="#">비밀번호 찾기</a></li>
                                <li><a href="#">회원가입</a></li>
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
               <!--  <P class="f_logo"><span class="blind">충북대학교병원</span></P> -->
                <p> Copyright  ⓒ  CHUNGBUK NATIONAL UNIVERSITY HOSPITAL. ALL RIGHT</p>
            </address>
        </div>
        <!-- footer :e   -->

    

    </div>
<!-- wrap :e -->

</body>
</html>
