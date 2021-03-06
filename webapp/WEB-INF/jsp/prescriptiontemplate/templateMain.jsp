<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.ac.cbnu.computerengineering.common.datatype.UserDataType" %>
<%@include file="/WEB-INF/jsp/common/include.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Script-Type" content="text/javascript">
	<meta http-equiv="Content-Style-Type" content="text/css">
	<meta http-equiv="X-UA-Compatible" content="IE=8">
	<link rel="shortcut icon" href="<c:url value="/img/logo.jpg"/>">
	<title>충북대학교 병원</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/normalize.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/layout.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/font.css"/>">
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="<c:url value="/js/jquery-ui-1.10.3.custom.min.js"/>"></script>
	<script src="<c:url value="/js/submenu.js"/>"></script>
</head>

<%
	UserDataType user = (UserDataType)session.getAttribute("user");
	String id = user.getUserId();
	request.setAttribute("id",id);
%>

<script type="text/javascript">
	
	function deleteTemplate(templateID,prescriptionID,obj) {
		$.ajax({
			type:"POST",
			url : "<c:url value="/prescription/deleteTemplate"/>",
			data : {"templateID": templateID,
					"prescriptionID": prescriptionID},
			dataType : "JSON",
			success: function(json){
				if(json.result){
					$(obj).parent().parent().remove();
				}
			},
			error:function(){
				alert("삭제할 수 없습니다");
			}	
		});
	}
	
	function useTemplate(prescriptionID,templateID, obj){
		$("#usePrescriptionInput").val(prescriptionID);
		$("#useTemplateInput").val(templateID);
		$("#useForm").submit();
	}
	
	function updateTemplate(prescriptionID,templateID, changeOrUseBasic,obj){
		$("#updateInput").val(prescriptionID);
		$("#templateIDInput").val(templateID);
		$("#ChangeOrUseBasic").val(changeOrUseBasic);
		$("#updateForm").submit();
	}
	
</script>
<body>
<!-- wrap :s -->
	<div id="wrapper">
		<!-- header :s -->
		<div id="headerWrap">
			<c:choose>
				<c:when test="${id eq 'admin' }"><c:import url="../common/admin/header.jsp"/></c:when>
				<c:otherwise><c:import url="../common/prescription/header.jsp"/></c:otherwise>
			</c:choose>
		</div>
		<!-- header :e -->
		<!-- contentsWrap :s -->
		<div id="contentsWrap">
			<!-- lnbWrap :s -->
			<div id="lnbWrap">
				<div id="lnb_title">
					<h2>처방템플릿</h2>
				</div>	
				<c:choose>
					<c:when test="${id eq 'admin' }">
						<c:import url="../common/admin/submenu3.jsp">
							<c:param name="menuType" value="2"/>
						</c:import>
					</c:when>
					<c:otherwise><c:import url="../common/prescriptiontemplate/submenu.jsp"/></c:otherwise>
				</c:choose>
			</div>
			<!-- lnbWrap :e -->

			<!-- container :s -->
			<div id="container">
				<div class="location">
					<span class="home"><a href="<c:url value="/main/selectFunc"/>"><img src="<c:url value="/img/layout/ico_home.png"/>" alt="홈"></a></span>
				</div>

				<h3 id="contTi">처방템플릿</h3>

				<!-- 컨텐츠영역 txt :s -->
				<div id="txt">
					<h4>template 리스트</h4>
					<!-- table :s -->
					<table id="maintable" class="tbl_basic" style="table-layout:fixed;">
			            <caption>
			                <strong>template 리스트</strong>
			                <details>
			                    <summary>template 리스트</summary>
			                </details>
			            </caption>
							<thead>
			                    <tr>
			                        <th class="trw" >No</th>
			                        <th class="trw" >제목</th>
			                        <th class="trw" >작성자</th>
			                        <th class="trw" >수정하기</th>
			                        <th class="trw" >삭제</th>
			                    </tr>
			                </thead>
			                <tbody>
			                <c:choose>
			                	<c:when test="${fn:length(prescriptionTemplateDataTypeList) eq 0}">
			                	<tr>
			                		<td>  </td>
									<td>  </td>
									<td>  </td>
									<td>  </td>
									<td>  </td>
								</tr>
			                	</c:when>
			                	<c:otherwise>
									<c:set var="cnt" value="${paging.startRow}"/>
				                	<c:forEach var="prescriptionTemplate" items="${prescriptionTemplateDataTypeList}">
									<c:set var="cnt" value="${cnt+1}"/>
									<tr class="patientList">
										<td onclick="useTemplate('<c:out value="${prescriptionTemplate.prescriptionID }"/>','<c:out value="${prescriptionTemplate.templateID }"/>',this)" style="cursor:pointer;"><c:out value="${cnt }"/></td>
										<td onclick="useTemplate('<c:out value="${prescriptionTemplate.prescriptionID }"/>','<c:out value="${prescriptionTemplate.templateID }"/>',this)" class="patientID"  style="cursor:pointer;"><c:out value="${prescriptionTemplate.templateTitle}"/></td>
										<td onclick="useTemplate('<c:out value="${prescriptionTemplate.prescriptionID }"/>','<c:out value="${prescriptionTemplate.templateID }"/>',this)" class="patientName" style="cursor:pointer;"><c:out value="${prescriptionTemplate.writer }"/></td>
										<td> 
											<input type="button" value="수정하기" class="subbtn" onclick="updateTemplate('<c:out value="${prescriptionTemplate.prescriptionID }"/>','<c:out value="${prescriptionTemplate.templateID }"/>',1,this)">
										</td>
										<td>
											<input type="button" value="삭제" class="subbtn" onclick="deleteTemplate('<c:out value="${prescriptionTemplate.templateID }"/>','<c:out value="${prescriptionTemplate.prescriptionID }"/>',this)">
										</td>
										
				                   </tr>
				                   </c:forEach>
			                	</c:otherwise>
			                </c:choose>
			                </tbody>
			            </table>
			            <!-- table :e -->
						<form id="deletePatient" action="<c:url value="/prescription/deletePatient"/>" method="POST">
							<input type="hidden" id="userID" name="userID"/>
							<input type="hidden" id="page" name="page"/>
						</form>
						<form id="useForm" action="<c:url value="/prescription/useTemplate"/>" method="POST">
							<input type="hidden" id="usePrescriptionInput" name="prescriptionID"/>
							<input type="hidden" id="useTemplateInput" name="templateID">
						</form>
						 <!-- paging & search :s -->
			            <ul class="paginate">
		            		<c:if test="${paging.nowPageGroup > 1 }">
								<li class="dir prev">
									<a href="<c:url value="/prescription/main?page=${(paging.nowPageGroup-2)*paging.pageGroupSize+1 }"/>" onclick="" title="이전페이지로 이동">«</a>
								</li>
							</c:if>
		            		<c:forEach var="i" begin="${paging.startPage }" end="${paging.endPage }">
	            				<c:choose>
	            					<c:when test="${param.page == i }">
	            						<li class="active">
	            					</c:when>
	            					<c:otherwise>
	            						<li>
	            					</c:otherwise>
	            				</c:choose>
	            						<a href="<c:url value="/prescription/main?page=${i }"/>" title="<c:out value="${i }"/>페이지">
	            							<c:out value="${i }"/>
	           							</a>
	           							</li>
		            		</c:forEach>
		            		<c:if test="${paging.nowPageGroup < paging.pageGroupCount }">
		            			<li class="dir next"><a href="<c:url value="/prescription/main?page=${paging.nowPageGroup*paging.pageGroupSize+1 }"/>" onclick="" title="다음페이지로 이동">»</a></li>
		            		</c:if>
						</ul>
						<!-- paging & search :e -->
						<form action="<c:url value='/prescription/writeTemplate'/>" method="post">
						<span class="rightButton">
							<input type="submit" value="작성하기" class="mainbtn">
						</span>
						</form>
						<form id="updateForm" action="<c:url value='/prescription/changePrescriptionTemplate'/>" method="post">
							<input id='updateInput' name="prescriptionID" type="hidden">
							<input id="templateIDInput" name="templateID" type="hidden">
							<input id="ChangeOrUseBasic" name = "ChangeOrUseBasic" type="hidden"/>
						</form>
				</div>
				<!-- 컨텐츠영역 txt :e -->

			</div>
			<!-- container :e -->

		</div>
		<!-- contentsWrap :e -->
		

		<!-- footerWrap :s -->
		<div id="footerWrap">
			<c:import url="../common/footer.jsp"/>
		</div>
		<!-- footerWrap :e -->
	</div>
</body>
</html>