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
			alert("회원가입에 실패하였습니다.\n 다시 시도해 주세요.");
		</c:if>
		$("#cancelBtn").click(function(){
			location.href="<c:url value="/user/login"/>"
		});
		$.validator.addMethod("idcheck",  function( value, element ) {
			return this.optional(element) || /^[A-Za-z0-9_-]*$/g.test(value);
		}); 
		$.validator.addMethod("namecheck",  function( value, element ) {
			return this.optional(element) || /^[^0-9]*$/g.test(value);
		}); 
		$("#joinFrm").validate({
			rules: {
				id: {
					required: true,
					idcheck: true,
					remote: {
						url: "<c:url value="/user/idCheck"/>",
						type: "POST",
						data: {
							id: function() {
								return $("#id").val();
							}
						},
						dataFilter: function(data) {
							if(JSON.parse(data).result) {
								return false;
							} else {
								return true;
							}
						}
					}
				},
				pw: {
					required: true
				},
				confirm_pw: {
					required: true,
					equalTo: "#pw"
				},
				email: {
					required: true,
					email: true,
					remote: {
						url: "<c:url value="/user/emailCheck"/>",
						type: "POST",
						data: {
							email: function() {
								return $("#email").val();
							}
						},
						dataFilter: function(data) {
							if(JSON.parse(data).result) {
								return false;
							} else {
								return true;
							}
						}
					}
				},
				name: {
					required: true,
					namecheck: true
				},
				code: {
					isPatient: "#role1"
				},
				roles: {
					required: true
				}
			},
			messages: {
				id: {
					required: "아이디를 입력해 주세요.",
					idcheck: "아이디는 영어와 숫자만 입력 할 수 있습니다.",
					remote: "이미 가입되어 있는 아이디입니다."
				},
				pw: {
					required: "비밀번호를 입력해 주세요."
				},
				confirm_pw: {
					required: "비밀번호를 확인해 주세요.",
					equalTo: "비밀번호와 비밀번호확인이 서로 다릅니다."
				},
				email: {
					required: "이메일을 입력해주세요.",
					email: "유효한 이메일 주소를 입력해 주세요.",
					remote: "이미 가입되어 있는 이메일주소입니다."
				},
				name: {
					required: "이름을 입력해 주세요.",
					namecheck: "숫자는 사용할 수 없습니다."
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
				<h4>회원가입</h4>
				<form id="joinFrm" name="joinFrm" target="_top" method="post" action="<c:url value="/user/createUser"/>">
					<table class="tbl_basic center">
						<tbody>
							<tr>
								<td class="trw1" >ID</td>
								<td class="trw" >
									<div class="join_input_row">
										<span class="input_box">
											<label for="id" id="label_id_area" class="lbl">ID</label>
											<input type="text" id="id" name="id" class="int" maxlength="30" value="">
										</span>
									</div>
									<label for="id" class="error"></label>
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
											<label for="id" id="label_id_area" class="lbl">ID</label>
											<input type="text" id="email" name="email" class="int" maxlength="30" value="">
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
											<label for="id" id="label_id_area" class="lbl">ID</label>
											<input type="text" id="name" name="name" class="int" maxlength="30" value="">
										</span>
									</div>
									<label for="name" class="error"></label>
								</td>
							</tr>
							<tr>
								<td class="trw1" >병원선택</td>
								<td class="trw" >
									<select id="hospital" name="hospital" class="select_box">
									<c:forEach items="${hospitals }" var="hospital">
										<option value="<c:out value="${hospital.id }"/>"><c:out value="${hospital.name }"/></option>
									</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td class="trw1" >병원코드</td>
								<td class="trw" >
									<div class="join_input_row">
										<span class="input_box">
											<label for="id" id="label_id_area" class="lbl">ID</label>
											<input type="text" id="code" name="code" class="int" maxlength="30" value="">
										</span>
									</div>
									<label for="code" class="error"></label>
								</td>
							</tr>
							<tr>
								<td class="trw1" >회원형태</td>
								<td class="trw" >
									<div class="none_input_row" id="select_roll">
										<span class="chkbox_area"><input id="role1" type="checkbox" name="roles" value="3" tabindex="9" class="chkbox">환자</span>
										<span class="chkbox_area"><input id="role2" type="checkbox" name="roles" value="2" tabindex="9" class="chkbox">의사</span>
									</div>
									<label for="roles" class="error"></label>
								</td>
							</tr>
						</tbody>
					</table>
					<fieldset class="login_form">
						<input type="submit" title="회원가입" alt="회원가입" value="회원가입" class="btn_login">
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