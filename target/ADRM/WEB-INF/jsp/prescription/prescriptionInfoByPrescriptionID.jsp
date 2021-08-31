<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	<c:set value="${prescription.content }" var="originalContent"/>
	<c:set value="${prescription.actionPlan }" var="actionPlan"/>
	<jsp:scriptlet>
		pageContext.setAttribute("newline", "\n");
	</jsp:scriptlet>
	<script type="text/javascript">
		
	
		$(document).ready(function(){	
			
			$(window).resize(function(){
				popResizer();
			});
		});
		function popResizer(){
		    window.resizeTo(1000,800);
		};
		popResizer();
		function selectPatient(userID) {
			var userID = userID;
			var url = "<c:url value="/prescription/selectPatient"/>?userID="+userID;
			opener.window.location = url;
			close();
		}
	</script>
	</head>
<body>
	<div id="container3">
		<table class="tbl_basic center" style="table-layout:fixed;">
			<caption>
				<strong>날짜별 처방 리스트</strong>
				<details>
					<summary>날짜별 처방 리스트</summary>
				</details>
			</caption>
			<tbody>
				<tr>
					<th class="trw" colspan="4" id="tableColumn"><fmt:formatDate value='${prescription.prescriptiondate }'  pattern="yy년 MM월dd일 HH시mm분"/></th>
				</tr>
			</tbody>
			<tbody>
				<tr>
					<td colspan="4" id="tableColumn"> 복용  ATC
				</tr>
				<tr>
                    <th class="trw" colspan="2" id="tableSubColumn">ATC코드</th>
					<th class="trw" colspan="2" id="tableSubColumn">ATC성분</th>
                </tr>
				<c:forEach var="tolerable" items="${ATCHashMapList.tolerableList }">
					<tr>
						<td colspan="2"><c:out value="${tolerable.ATCCode }"/></td>
						<td colspan="2"><c:out value="${tolerable.ATCName }"/></td>
					</tr>
				</c:forEach>
			</tbody>
			<tbody>
				<tr>
					<td colspan="4" id="tableColumn"> 금지 약제
				</tr>
				<tr>
					<th class="trw" colspan="2" id="tableSubColumn">ATC코드</th>
					<th class="trw" colspan="2" id="tableSubColumn">ATC성분</th>
				</tr>
				<c:forEach var="atc" items="${ATCHashMapList.prohibitionList }">
				<tr>
					<td colspan="2"><c:out value="${atc.ATCCode }"/></td>
					<td colspan="2"><c:out value="${atc.ATCName }"/></td>
				</tr>
				</c:forEach>
			</tbody>
			<tbody>
				<tr>
					<td colspan="4" id="tableColumn"> 회피  ATC
				</tr>
				<tr>
					<th class="trw" colspan="2" id="tableSubColumn">ATC코드</th>
					<th class="trw" colspan="2" id="tableSubColumn">ATC성분</th>
				</tr>
				<c:forEach var="upperATC" items="${ATCHashMapList.upperList }">
					<tr>
						<td colspan="2"><c:out value="${upperATC.ATCCode }"/></td>
						<td colspan="2"><c:out value="${upperATC.ATCName }"/></td>
					</tr>
				</c:forEach>
			</tbody>
			<tbody>
				<tr>
					<td colspan="4" id="tableColumn"> 약물유해반응</td>
				</tr>
				<tr>
					<td colspan="4" id="content"><c:out escapeXml="false" value="${ATCHashMapList.contentActionPlanDataType.content}"></c:out></td>
				</tr>
			</tbody>
			<tbody>
				<tr>
					<td colspan="4" id="tableColumn"> 약물알레르기 Action Plan</td>
				</tr>
				<tr>
					<td colspan="4" id="content"><c:out escapeXml="false" value="${ATCHashMapList.contentActionPlanDataType.actionPlan}"></c:out></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>
