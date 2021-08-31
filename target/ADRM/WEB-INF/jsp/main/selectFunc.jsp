<%@page contentType="text/html; charset=UTF-8"%> 
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
<link rel="stylesheet" type="text/css" href="<c:url value="/css/choose_func.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/font.css"/>">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	<c:choose>
		<c:when test="${resultType == 'SUCCESS'}">
			alert("회원정보 수정에 성공하였습니다.");
		</c:when>
	</c:choose>
});
	function selectUser(){
		if(confirm("현재 환자 서비스는 앱을 통해서만 제공하고 있습니다. 다운 받으시겠습니까?")==true){
			location.href="https://play.google.com/store/apps/details?id=kr.ac.cbnuh.dur"
		}
		else return;
	}
	
	function selectDoctor(approval){
		if(approval == "APPROVAL"){
			location.href="<c:url value="/prescription/main"/>";
		}else if(approval == "REJECTION"){
			alert("의사승인이 거부되었습니다");
		}else if(approval == "WAITING"){
			alert("의사승인이 대기중입니다")
		}
	}
	
</script>
</head>
<body>
<!-- wrap :s -->
	<div id="wrap">
		<!-- header :s -->
		<div id="header">
			<h1><a href="<c:url value="/main/selectFunc"/>" class="sp h_logo">ADRM</a></h1>
		</div>
		<!-- header :e -->

		<!-- container :s -->
		<div id="container">
			<div id="content">
				<h4 class="hide">기능 선택</h4>
				
				<c:forEach var="role" items="${user.roles }">
					<c:choose>
						<c:when test="${role.userRole.value == 1 }">
							<a class="btn_wrap" href="<c:url value="/admin/main"/>">
								<span class="horizontal_align"><p><img src="<c:url value="/img/configuration.png"/>"></p>시스템 관리<b>System Management</b></span>
							</a>
						</c:when>
						<c:when test="${role.userRole.value == 2 }">
							<a class="btn_wrap2" onclick="selectDoctor('${user.approval}')">
								<span class="horizontal_align"><p><img src="<c:url value="/img/doctor.png"/>"></p>의사<b>Doctor</b></span>
							</a>
						</c:when>
						<c:when test="${role.userRole.value == 3 }">
							<a class="btn_wrap" onclick="selectUser()">
								<span class="horizontal_align"><p><img src="<c:url value="/img/medicine.png"/>"></p>환자<b>Patient</b></span>
							</a>
						</c:when>
					</c:choose>
				</c:forEach>
				
				
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