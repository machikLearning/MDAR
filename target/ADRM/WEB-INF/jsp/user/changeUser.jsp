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
		$("#joinFrm").validate({
			rules: {
				pw: {
					required: true
				},
				confirm_pw: {
					required: true,
					equalTo: "#pw"
				},
				name: {
					required: true,
					namecheck: true
				},
				email: {
					required:true
				},
				code: {
					isPatient: "#role1"
				},
				roles: {
					required: true
				}
			},
			messages: {
				pw: {
					required: "비밀번호를 입력해 주세요."
				},
				confirm_pw: {
					required: "비밀번호를 확인해 주세요.",
					equalTo: "비밀번호와 비밀번호확인이 서로 다릅니다."
				},
				name: {
					required: "이름을 입력해 주세요.",
					namecheck: "숫자는 사용할 수 없습니다."
				},
				email:{
					required: "email을 입력해주세요"
				},
				code: {
					isPatient: "환자일 경우 병원코드를 입력해주세요."
				},
				roles: {
					required: "회원형태를 선택해 주세요."
				}
			}
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
			<div id="content">
				<h4>회원정보수정</h4>
				<form id="joinFrm" name="joinFrm" target="_top" method="post" action="<c:url value="/user/changeFinish"/>">
					<table class="tbl_basic center">
						<tbody>
							<tr>
								<td class="trw1" >ID</td>
								<td class="trw" >
									<c:out value="${user.userId }"></c:out>
								</td>
							</tr>
							<tr>
								<td class="trw1" >비밀번호</td>
								<td class="trw" >
									<div class="join_input_row">
										<span class="input_box">
											<label for="pw" id="label_pw_area" class="lbl">Password</label>
											<input type="password" id="pw" name="pw" class="int" maxlength="30" >
										</span>
									</div>
									<label for="pw" class="error"></label>
								</td>
							</tr>
							<tr>
								<td class="trw1" >비밀번호 확인</td>
								<td class="trw" >
									<div class="join_input_row">
										<span class="input_box">
											<label for="pw" id="label_pw_area" class="lbl">Password</label>
											<input type="password" id="confirm_pw" name="confirm_pw" class="int" maxlength="30" >
										</span>
									</div>
									<label for="confirm_pw" class="error"></label>
								</td>
							</tr>
							<tr>
								<td class="trw1" >E-MAIL</td>
								<td class="trw" >
									<div class="join_input_row">
										<span class="input_box">
											<input type="text" id="email" name="email" class="int" maxlength="30" value="${user.email }">
										</span>
									</div>
									<label for="email" class="error"></label>
								</td>
							</tr>
							<tr>
								<td class="trw1" >이름</td>
								<td class="trw" >
									<div class="join_input_row">
										<span class="input_box">
											<input type="text" id="name" name="name" class="int" maxlength="30" value="${user.name }">
										</span>
									</div>
									<label for="name" class="error"></label>
								</td>
							</tr>
							<tr>
								<td class="trw1" >병원선택</td>
								<td class="trw" >
									<select name="hospital" id="hospital" class="select_box">
									<c:forEach items="${hospitals }" var="hospital">
										<option value="<c:out value="${hospital.id }"/>" <c:if test="${hospital.id == user.hospital.id }">selected</c:if>><c:out value="${hospital.name }"/></option>
									</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td class="trw1" >병원코드</td>
								<td class="trw" >
									<div class="join_input_row">
										<span class="input_box">
											<input type="text" id="code" name="code" class="int" maxlength="30" value="${user.cbnuCode }">
										</span>
									</div>
									<label for="code" class="error"></label>
								</td>
							</tr>
							<tr>
								<td class="trw1" >회원형태</td>
								<td class="trw" >
									<div class="none_input_row" id="select_roll">
										<c:choose>			
											<c:when test="${fn:length(userRoles) eq 1 }">
												<c:forEach items="${userRoles }" var="role">
													<span class="chkbox_area"><input id="role1" type="checkbox" name="roles" value="3" tabindex="9" class="chkbox" ${role.value == 3 ? 'checked' : '' }>환자</span>
													<span class="chkbox_area"><input id="role2" type="checkbox" name="roles" value="2" tabindex="9" class="chkbox" ${role.value == 2 ? 'checked' : ''}>의사</span>
												</c:forEach>
											</c:when>
											<c:when test="${fn:length(userRoles) eq 2 }">
												<span class="chkbox_area"><input id="role1" type="checkbox" name="roles" value="3" tabindex="9" class="chkbox" checked>환자</span>
												<span class="chkbox_area"><input id="role2" type="checkbox" name="roles" value="2" tabindex="9" class="chkbox" checked>의사</span>
											</c:when>
											<c:otherwise>
												<span class="chkbox_area"><input id="role1" type="checkbox" name="roles" value="3" tabindex="9" class="chkbox">환자</span>
												<span class="chkbox_area"><input id="role2" type="checkbox" name="roles" value="2" tabindex="9" class="chkbox">의사</span>
											</c:otherwise>
										</c:choose>
									</div>
									<label for="roles" class="error"></label>
								</td>
							</tr>
						</tbody>
					</table>
					<fieldset class="login_form">
						<input type="submit" title="회원정보수정" alt="회원정보수정" value="회원정보수정" class="btn_login">
						<input id="cancelBtn" type="button" title="취소" alt="취소" value="취소" class="btn_cancel">
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