<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("newline", "\n"); %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8" content="text/html">
	<meta http-equiv="Content-Script-Type" content="text/javascript">
	<meta http-equiv="Content-Style-Type" content="text/css">
	<link rel="shortcut icon" href="<c:url value="/img/logo.png"/>">
	<title>충북대학교 병원</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/normalize.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/layout.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/font.css"/>">
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="<c:url value="/js/jquery-ui-1.10.3.custom.min.js"/>"></script>
	<script src="<c:url value="/js/submenu.js"/>"></script>
</head>

<script type="text/javascript">

	$(document).ready(function(){
		$("#content").val();
		<c:choose>
			<c:when test="${param.resultType == 'PROHIBITION_SAME_CAPABLE'}">
				alert("금기약제의 ATC코드와 복용가능 약제의 ATC코드가 같은 목록이 있습니다.");
			</c:when>
		</c:choose>
		<c:choose>
			<c:when test="${param.resultType2 == 'CAPABLE_IN_UPPERATC'}">
				alert("복용 가능 약제가 주의 약제에 포함되어 있습니다.")
			</c:when>
		</c:choose>
		$("#prescriptionClear").click(function(){
			var date =$("#date").val();
			location.href="<c:url value="/prescription/prescriptionClear"/>?date="+date;
		});
		$("#cancelBtn").click(function(){
			history.back();
		});
	});
	
	function removeRow(obj){
		$(obj).parent().parent().remove();
	}
	
	function searchProhibition(){
		var title="Prohibition"
		var date =$("#date").val();
		window.open("<c:url value="/prescription/searchProhibitionResult"/>?date="+date,title,"width=1000px, height=800");
		
	}
	function searchUpper(){
		var title="Upper"
		var date = $("#date").val();
		window.open("<c:url value="/prescription/searchUpperResult"/>?date="+date,title,"width=1000px, height=800");
	}
	function searchTolerable(){
		var title="Tolerable"
		var date = $("#date").val();
		window.open("<c:url value="/prescription/searchTolerableResult"/>?date="+date,title,"width=1000px, height=800");
	}
	
	function searchPrescriptionTemplate(basicOrMe){
		if(basicOrMe == 0){
			window.open("<c:url value="/prescription/searchPrescriptionTemplateMe"/>","width=1000px, height=800");
		}else{
			window.open("<c:url value="/prescription/searchPrescriptionTemplateAdmin"/>","width=1000px, height=800");
		}
	}
	
	
	function submitPrescription(){
		var prohibitionCode = $("td[name=prohibitionCode]");
		var tolerableCode = $("td[name=tolerableCode]");
		var upperCode = $("td[name=upperCode");
		var completePrescription = $("#prescriptionSubmit");
		prohibitionCode.each(function(i,e){
			completePrescription.append("<input type = 'hidden' name='prohibitionCode' value='" + $(e).text() + "'>");
		});
		tolerableCode.each(function(i,e){
			completePrescription.append("<input type = 'hidden' name='tolerableCode' value='" + $(e).text() + "'>");
		});
		upperCode.each(function(i,e){
			completePrescription.append("<input type = 'hidden' name='upperCode' value='" + $(e).text() + "'>");
		});
		completePrescription.append("<input type = 'hidden' name='content' value='" + $("#content").val() + "'>");
		completePrescription.append("<input type = 'hidden' name='actionPlan' value='" + $("#actionPlan").val() + "'>");
		completePrescription.submit();
	}
	
	function deleteChangeProhibitionTolerableUpper(id,obj,ATCCode){
		$.ajax({
			type:"POST",
			url : "<c:url value="/prescription/deleteChangeProhibitionTolerableUpper"/>",
			data : {"id": id,
					"medicineKinds" : $(obj).attr("name")},
			dataType : "JSON",
			success: function(json){
				if(json.deleteChangeProhibitionUpperTolerableResult){
					$(obj).parent().parent().remove();
					deleteRegistration(obj,ATCCode);
				}
			},
			error:function(){
				alert("삭제할 수 없습니다");
			}	
		});
		//$("#prohibition").val(Prohibition);
		//$("#deleteProhibition").submit();
		
	}
	
	function deleteRegistration(obj,ATCCode){
		console.log(ATCCode);
		var kind
		if($(obj).attr("name")=="deleteChangeProhibition"){
			kind="registrationProhibitionCode"
		}else if($(obj).attr("name")=="deleteChangeUpper"){
			kind="registrationUpperCode";
		}else if($(obj).attr("name")=="deleteChageTolerable"){
			kind="registrationTolerableCode";
		}
		var kindTag = "td[name="+kind+"]";
		$(kindTag).each(function(i,e){
			if($(this).text() == ATCCode){
				$(this).remove();
			}
		})
		
	}
	
</script>
<body >
<!-- wrap :s -->
	<div id="wrapper">
		<!-- header :s -->
		<div id="headerWrap">
			<c:import url="../common/prescription/header.jsp"/>
		</div>
		<!-- header :e -->
		<!-- contentsWrap :s -->
		<div id="contentsWrap">
			<!-- container :s -->
				<div id="lnbWrap">
				<div id="lnb_title">
					<h2>환자 처방</h2>
				</div>	
				<c:import url="../common/prescription/submenu.jsp"/>
			</div>
			<div id="container">
				<!-- 컨텐츠영역 txt :s -->
				<div id="txt">
					<c:if test="${!empty templateDataType }">
						<h4 style="text-align:center"class="subtitle">제목 : <c:out value="${templateDataType.templateTitle}"></c:out></h4>
					</c:if>
					<div style="float:right">
						<button onclick="searchPrescriptionTemplate(0)" class="btn_save">나의 템플릿</button>
						<button onclick="searchPrescriptionTemplate(1)" class="btn_save">기본 템플릿</button>
					</div>
					<br>
					<div class="tableDiv">
					<h4 class="subtitle">금기 ATC 목록을 입력해 주세요</h4>
					<!-- table :s -->
					<table class="tbl_basic center" id="prohibitionTable" style="table-layout:fixed;">
			            <caption>
			                <strong>금기 약제 리스트</strong>
			                <details>
			                    <summary>금기 ATC 목록</summary>
			                </details>
			            </caption>
		                <thead>
		                    <tr>
								<th class="trw">ATC코드</th>
								<th class="trw">ATC성분</th>
								<th class="trw">삭제</th>
		                    </tr>
		                </thead>
		                <tbody id ="prohibitionTableBody">
		                	<c:forEach var="prohibition" items="${ATCHashMapList.prohibitionList}">
							<tr>
								<td name="prohibitionCode"><c:out value="${prohibition.ATCCode }"/></td>
								<td><c:out value="${prohibition.ATCName }"/></td>
								<td>
									<input name="deleteChangeProhibition" type="button" value="삭제" class="btn_save" onclick="deleteChangeProhibitionTolerableUpper('<c:out value="${prohibition.ATCID}"/>',this,'<c:out value="${prohibition.ATCCode}"/>')">
								</td>
		                   	</tr>
		                   	</c:forEach>
		                </tbody>
		            </table>
		            <span class="rightButton">
		            	<input type="button" value="금지 약제 검색" class="subbtn2" onclick="searchProhibition()">
		            </span>
		            <!-- table :e -->
					<form id="deleteChangeProhibition" action="<c:url value="/prescription/deleteProhibition"/>" method="POST">
							<input type="hidden" id="prohibition" name="prohibition">
							<input type="hidden" id="date" name="date" value="${date}">
					</form>
					</div>
					<div class="tableDiv">
					<h4 class="subtitle">주의 ATC 목록을 입력해 주세요</h4>
					<!-- table :s -->
					<table class="tbl_basic center" id="atcTable" style="table-layout:fixed;">
			            <caption>
			                <strong>주의 ATC 목록</strong>
			                <details>
			                    <summary>주의 ATC 목록</summary>
			                </details>
			            </caption>
		                <thead>
		                    <tr>
								<th class="trw">ATC코드</th>
								<th class="trw">ATC성분</th>
								<th class="trw">삭제</th>
							</tr>
		                </thead>
		                <tbody id = "upperTableBody">
		                	<c:set var="cnt" value="${0 }"/>
							<c:forEach var="upper" items="${ATCHashMapList.upperList}">
							<c:set var="cnt" value="${cnt+1 }"/>
							<tr>
								<td name="upperCode"><c:out value="${upper.ATCCode }"/></td>
								<td><c:out value="${upper.ATCName }"/></td>
								<td><input name="deleteChangeUpper" type="button" value="삭제" class="btn_save deleteChange" onclick="deleteChangeProhibitionTolerableUpper('<c:out value="${upper.ATCID }"/>',this,'<c:out value="${upper.ATCCode }"/>')"></td>
		                   	</tr>
		                   	</c:forEach>
		                 
		                </tbody>
		            </table>
		            <span class="rightButton">
		            	<input type="button" value="주의 ATC 검색" class="subbtn2" onclick="searchUpper()">
		            </span>
		            <!-- table :e -->
					<form id="deleteChangeUpper" action="<c:url value="/prescription/deleteUpper"/>" method="POST">
							<input type="hidden" id="upper" name="upper"/>
							<input type="hidden" id="date" name="id">
					</form>
					</div>
					
					<div class="tableDiv">
					<h4 class="subtitle">복용 ATC 목록을 결정해주세요</h4>
					<!-- table :s -->
					<table class="tbl_basic center" id="atcTable" style="table-layout:fixed;">
			            <caption>
			                <strong>복용 ATC 목록</strong>
			                <details>
			                    <summary>복용 ATC 목록</summary>
			                </details>
			            </caption>
		                <thead>
		                    <tr>
								<th class="trw">ATC코드</th>
								<th class="trw">ATC성분</th>
								<th class="trw">삭제</th>
							</tr>
		                </thead>
		                <tbody id="tolerableTableBody">
							<c:forEach var="tolerable" items="${ATCHashMapList.tolerableList}">
							<tr>
								<td name="tolerableCode"><c:out value="${tolerable.ATCCode }"/></td>
								<td><c:out value="${tolerable.ATCName }"/></td>
								<td><input type="button" name = "deleteChageTolerable" value="삭제" class="btn_save" onclick="deleteChangeProhibitionTolerableUpper('<c:out value="${tolerable.ATCID }"/>',this,'<c:out value="${tolerable.ATCCode }"/>')"></td>
		                   	</tr>
		                   	</c:forEach>
		                
		                </tbody>
		            </table>
		            <span class="rightButton">
		            	<input type="button" value="복용 ATC 검색" class="subbtn2" onclick="searchTolerable()">
		            </span>
		            <!-- table :e -->
					<form id="deleteChangeTolerable" action="<c:url value="/prescription/deleteTolerable"/>" method="POST">
							<input type="hidden" id="tolerable" name="tolerable"/>
							<input type="hidden" id="date" name="id">
					</form>
					</div>
					<div class="tableDiv">		
		        	    <table calss="tbl_basic center" id="registrationDataList">
		            	    <tbody>
		            	    	<c:forEach var="registration" items="${registrationList}">
			                		<c:forEach var="prescription" items="${registration.prescriptionList}">
										<c:forEach var="tolerable" items="${prescription.tolerableList}">
											<tr style="display:none">
		                   						<td name="registrationTolerableCode"><c:out value="${tolerable.ATCCode}"></c:out></td>
		                   					</tr>
										</c:forEach>
										<c:forEach var="prohibition" items="${prescription.prohibitionList}">
											<tr style="display:none">
		                   						<td name="registrationProhibitionCode"><c:out value="${prohibition.ATCCode}"></c:out></td>
		                   					</tr>
										</c:forEach>
										<c:forEach var="upper" items="${prescription.upperList}">
											<tr style="display:none">
		                   						<td name="registrationUpperCode"><c:out value="${upper.ATCCode}"></c:out></td>
		                   					</tr>
										</c:forEach>			                		
			                		</c:forEach>
			                	</c:forEach>
			                </tbody>
		    	        </table>
					</div>
					<div class="prescriptionDiv">
						<h4 class="subtitle">약물유해반응</h4>
						<textarea class="textarea" id="content" name="content">${fn:replace(ATCHashMapList.contentActionPlanDataType.content, '<br>', newline)}</textarea>
					
					</div>
					<br/><br/>
					<div class="prescriptionDiv">
						<h4 class="subtitle">약물알레르기 Action Plan</h4>
						<textarea class="textarea" id="actionPlan" name="actionPlan">${fn:replace(ATCHashMapList.contentActionPlanDataType.actionPlan, '<br>', newline)}</textarea>
					
					</div>
					<br/>
					<input type="button" value="취소" id="cancelBtn" class="subbtn3">
					<input type="button" value="다시하기" id="prescriptionClear" class="subbtn3">
					<span class="rightButton" >
						<button type="button" id="prescriptionFinish" class="mainbtn" onclick="submitPrescription()">처방하기</button>
					</span>
					<form id="prescriptionSubmit" action="<c:url value="/prescription/prescriptionFinish"/>" method="POST">
						<input type="hidden" id="date" name="date" value="${date }"/>
						<input type="hidden" name="prescriptionID" value = <%= request.getAttribute("prescriptionID") %>>
					</form>
					
					<form id="prescriptionTemplate" action="<c:url value="/prescription/useTemplate"/>" method="POST">
							<input type="hidden" id="prescriptionTemplatePrescriptionIDInput" name="templatePrescriptionID"/>
							<input type="hidden" id="prescriptionTemplateTemplateIDInput" name="templateID">
							<input type="hidden" id="originalPrescriptionID" name="prescriptionID" value="${prescriptionID}"/>
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