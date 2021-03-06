<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="<c:url value="/img/logo.png"/>">
<script src="<c:url value="/js/jquery-3.1.1.min.js"/>"></script>
<script type="text/javascript">
	$(document).ready(function(){

		var count = 0;
		var first=$("#cbnuCode").val();
		var reg_pw = /^.*(?=.{8,12})(?=.*[0-9])(?=.*[a-zA-Z]).*$/; 
		var regExp = /\s/g;
		$("#disableAccount").click(function(){
			window.location.href="<c:url value="/user/disableAccount"/>";
		});
		
		$("#cbnuCodeDupl").click(function(){
			
			
			var cbnuCode= $("#cbnuCode").val();
			var request = $.ajax({
				type: "POST",
				url: "<c:url value="/user/cbnuCode_check"/>",
				data: {cbnuCode:cbnuCode},
				dataType: "json",
				success: function(jsonObj){
					if(jsonObj.isDupl == false){		
						$("#cbnuCode_inform").text(jsonObj.msg+" 는 사용 가능한 충북대코드 입니다.");
						count = 1;
					}
					else{
						$("#cbnuCode_inform").text(jsonObj.msg+" 는 이미 사용중인 충북대코드입 니다.");
						$("#cbnuCode").val("");
						count = 0;
					}
					
				}
								
			});
						
						event.preventDefault();
		});		
		$("#submitBtn").click(function(){
			
			event.preventDefault();
			
		
			if(!reg_pw.test($("#password").val())){
				alert("비밀번호는 영문,숫자를 혼합하여 \n 8~12자 이내로 작성해 주시기 바랍니다.");
				$("#password").focus();
				return false;
			}
			else if(!$("#name").val()) {
				alert("이름을 입력하세요");
				$("#name").focus();
				return false;
			}
			else if(!$(':radio[id="gender"]:checked').val()) {
				alert("성별을 선택하세요");
				$("#gender").focus();
				return false;
			}
			else if(!$('input:checkbox[name="roles"]').is(":checked")) {
				alert("신분을 선택하세요(중복 선택 가능)");
				$("#roles").focus();
				return false;
			}
			else if($('input:checkbox[id="role1"]').is(":checked")){

					if(first != $("#cbnuCode").val()){
						if(!$("#cbnuCode").val()) {
							alert("환자일 경우에는 충북대코드를 입력해주세요.");
							return false;
						}
						if(count == 0){
							alert("충북대코드 중복확인을 해주세요.");
							return false;
						}
					}	
				
			}
			else if($('input:checkbox[id="role2"]').is(":checked")
					&& !$('input:checkbox[id="role1"]').is(":checked")){
				if($("#cbnuCode").val() != ""){
					$("#cbnuCode").val("");
					alert("의사일 경우에는 충북대코드 입력이 필요하지 않습니다.");
					return false;
				}
			}
			else if(!$("#email").val()){
				alert("이메일을 입력해주세요.");
				return false;
			}
			
			alert("회원 정보가 수정 되었습니다.");
			$("#userform").submit();
		});
	});
	
	
</script>
<title>회원 수정</title>
</head>
<body>
	<H4>수정할 부분의 정보를 입력하세요.</H4>

	<form id="userform"  action="<c:url value="/user/modifyMember_end"/>" method="post">
		아이디: <c:out value="${user.userId}"/>
		
		<br>패스워드: <input type="password" name="password" id="password" size="10"><br>
		이름: <input type="text" id="name" name="name" size="10" value="${ user.name}"><br>
		
		성별: 
		<c:choose>
			<c:when test="${user.gender == 'MAN'}">
			남자 <input id="gender" type="radio" name="gender" value="MAN" checked>
			 여자 <input id="gender" type="radio" name="gender" value="WOMAN">
			</c:when>
			<c:when test="${user.gender == 'WOMAN'}">
				남자 <input id="gender" type="radio" name="gender" value="MAN" >
				 여자 <input id="gender" type="radio" name="gender" value="WOMAN" checked>
			</c:when>
		</c:choose>
		
		<br>
		신분:
		<c:choose>			
			<c:when test="${fn:length(userRoles) eq 1 }">
				<c:forEach items="${userRoles }" var="role">
					<input id="role1" type="checkbox" name="roles" value="3" ${role.value == 3 ? 'checked' : '' }>환자
					<input id="role2" type="checkbox" name="roles" value="2" ${role.value == 2 ? 'checked' : '' }>의사<br>
				</c:forEach>
			</c:when>
			<c:when test="${fn:length(userRoles) eq 2 }">
					<input id="role1" type="checkbox" name="roles" value="3" checked>환자
					<input id="role2" type="checkbox" name="roles" value="2" checked>의사<br>
			</c:when>
			<c:otherwise>
				<input id="role1" type="checkbox" name="roles" value="3" >환자
				<input id="role2" type="checkbox" name="roles" value="2" >의사<br>
			</c:otherwise>
		</c:choose>	 		
		충북대학교코드: <input type="text" id="cbnuCode" name="cbnuCode" size="10" value="${ user.cbnuCode}">
		<div id="cbnuCode_inform"></div>
		<Button name="cbnuCodeDupl" id="cbnuCodeDupl">중복확인</Button>
		<br>
		이메일: <input type="text" id="email" name="email" size=27 value="${user.email }">
		<br>
		<Button name="submitBtn" id="submitBtn">입력완료</Button>
		<c:if test="${isDoctor == true }">
			<c:set value="doctor" var="doctor"/>
			<input type="hidden" name="isDoctor" value="${doctor }"/>
		</c:if>	
	</form>
	<br><br>
	<Button name="disableAccount" id="disableAccount">계정 비활성화</Button> 
</body>
</html>