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
<link rel="stylesheet" type="text/css" href="<c:url value="/css/layout.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/font.css"/>">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="<c:url value="/js/jquery.validate.min.js"/>"></script>
<script src="<c:url value="/js/jquery.validate.extend.js"/>"></script>
<script src="<c:url value="/js/jquery-ui-1.10.3.custom.min.js"/>"></script>
<script src="<c:url value="/js/submenu.js"/>"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#save_btn").click(function(){
			if($("#hospital_name").val() == "") {
				alert("병원이름은 공백일 수 없습니다.");
				return false;
			}
			
			if($("#contact_name1").val() == "" && $("#contact_name2").val() == "") {
				alert("연락처 이름은 반드시 하나 이상 입력해야 합니다.");
				return false;
			}
			
			if($("#contact_tel1").val() == "" && $("#contact_tel2").val() == "") {
				alert("연락처는 반드시 하나 이상 입력해야 합니다.");
				return false;
			}
		});
		
		$("#cancel_btn").click(function(){
			location.href="<c:url value="/admin/hospitalManagement"/>";	
		});
	});
</script>
</head>

<body>
<!-- wrap :s -->
	<div id="wrapper">

		<!-- header :s -->
		<div id="headerWrap">
			<c:import url="../common/admin/header.jsp"/>
		</div>
		<!-- header :e -->


		<!-- contentsWrap :s -->
		<div id="contentsWrap">

			<!-- lnbWrap :s -->
			<div id="lnbWrap">
				<div id="lnb_title">
					<h2>기타 관리</h2>
				</div>			
				<c:import url="../common/admin/submenu3.jsp">
					<c:param name="menuType" value="1"/>
				</c:import>
			</div>
			<!-- lnbWrap :e -->

			<!-- container :s -->
			<div id="container">

				<div class="location">
					<span class="home"><a href="<c:url value="/admin/accountManagement"/>"><img src="<c:url value="/img/layout/ico_home.png"/>" alt="홈"></a></span>
					<span class="arrow">&gt;</span>
					<a href="#"> <span class="first">기타관리</span></a>
					<span class="arrow">&gt;</span>
					<a href="<c:url value="/admin/hospitalManagement"/>"> <span>참여병원관리</span></a>
				</div>

				<h3 id="contTi">참여병원관리</h3>

				<!-- 컨텐츠영역 txt :s -->
				<div id="txt">
					<h4>참여병원추가</h4>
					<!-- table :s -->
					<form id="hospitalFrm" name="hospitalFrm" action="<c:url value="/admin/addHospital"/>" method="POST">
					<table class="tbl_basic center" style="table-layout:fixed;">
			            <caption>
			                <strong>참여병원추가</strong>
			                <details>
			                    <summary>참여병원추가</summary>
			                </details>
			            </caption>
		                <tbody>
	                		<tr>
	                			<td width="200">병원명</td>
	                			<td style="text-align: left" colspan="2">
									<input type="text" id="hospital_name" name="hospital_name" class="inputText2" />
									<label for="hospital_name" class="error"></label>
	                			</td>
	                		</tr>
	                		<tr>
	                			<td>연락처이름 1</td>
	                			<td style="text-align: left">
	                				<input type="text" id="contact_name1" name="contact_name1" class="inputText2" />
									<label for="contact_name1" class="error"></label>	                			
	                			</td>
	                			<td style="text-align: left; border-left: none;">ex) xx지역의약품안전센터</td>
	                		</tr>
	                		<tr>
	                			<td>연락처 1</td>
	                			<td style="text-align: left">
	                				<input type="text" id="contact_tel1" name="contact_tel1" class="inputText2" />
									<label for="contact_tel1" class="error"></label>	                			
	                			</td>
	                			<td style="text-align: left; border-left: none;">ex) 000-000-0000</td>
	                		</tr>
	                		<tr>
	                			<td>연락처이름 2</td>
	                			<td style="text-align: left">
	                				<input type="text" id="contact_name2" name="contact_name2" class="inputText2" />	                			
	                			</td>
	                			<td style="text-align: left; border-left: none;">ex) xx병원 알레르기내과</td>
	                		</tr>
	                		<tr>
	                			<td>연락처 2</td>
	                			<td style="text-align: left">
	                				<input type="text" id="contact_tel2" name="contact_tel2" class="inputText2" />	                			
	                			</td>
	                			<td style="text-align: left; border-left: none;">ex) 000-000-0000</td>
	                		</tr>
		                </tbody>
		            </table>
					<input type="hidden" id="hospital_id" name="hospital_id">
		            <!-- table :e -->
		            <ul class="search_info">
		            	<li>
							<input type="submit" id="save_btn" value="저장" class="submitbtn2" />
							<input type="button" id="cancel_btn" value="취소" class="submitbtn"/>
						</li>
					</ul>
		            </form>
				</div>
				<!-- 컨텐츠영역 txt:e -->
			</div>
			<!--  container:e    -->
		</div>
		<!-- contentsWrap :e -->
		<!-- footerWrap :s -->
		<div id="footerWrap">
			<c:import url="../common/footer.jsp"/>
		</div>
		<!-- footerWrap :e -->
	</div>
	<script type="text/javascript">
	<c:if test="${hospital != null}">
		$("#hospital_name").val('<c:out value="${hospital.name}"/>');
		$("#contact_name1").val('<c:out value="${hospital.contactName1}"/>');
		$("#contact_tel1").val('<c:out value="${hospital.contactTel1}"/>');
		$("#contact_name2").val('<c:out value="${hospital.contactName2}"/>');
		$("#contact_tel2").val('<c:out value="${hospital.contactTel2}"/>');
		$("#hospital_id").val('<c:out value="${hospital.id}"/>');
		$("#hospitalFrm").attr("action", "<c:url value="/admin/updateHospital"/>")
	</c:if>
	</script>
</body>
</html>