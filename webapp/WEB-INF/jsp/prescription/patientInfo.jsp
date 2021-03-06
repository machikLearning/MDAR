<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
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
	
	function checkPrescription(prescriptionID){
		$("#viewPrescriptionInput").val(prescriptionID);
		window.open("" ,"viewPrescription","resizable = no, scrollbars = no");
		$("#viewPrescription").submit();
	}
	
	function deleteATCTableTr(prescriptionID, ATCListName){
		prescriptionID += "";
		$(ATCListName).each(function(i,e){
			if($(this).val() == prescriptionID){
				$(this).parent().parent().remove();
			}
		})
	}
	
	function deletePrescription(prescriptionID,obj){
		if(confirm("처방을 삭제하시겠습니까?")==true){
			$.ajax({
				type:"POST",
				url : "<c:url value="/prescription/deletePrescription"/>",
				data : {"id": prescriptionID},
				dataType : "JSON",
				success: function(json){
					if(json.deletePrescriptionResult){
						location.href="<c:url value='/prescription/selectPatientInfo'/>";
					}
				},
				error:function(){
					alert("삭제할 수 없습니다");
				}	
			});
		}
		else return;
	}
	
	function selectPatientInfo(userID){
		$("#patientID").val(userID);
		$("#selectPatientInfo").submit();
	}
	function selectPrescription(tempDate){
		$("#date").val(tempDate);
		$("#selectPrescription").submit();
	}
	function changePrescription(prescriptionID){
		if(confirm("처방을 수정하시겠습니까?")==true){
			$("#changePrescriptionInput").val(prescriptionID);
			$("#changePrescription").submit();
			
		}
		else return;
	}
	
	$(document).ready(function(){
		<c:choose>
		<c:when test="${param.resultType == 'PRESCRIPTION_SUCCESS'}">
			alert("처방을 완료하였습니다.");
		</c:when>
		<c:when test="${param.resultType == 'DELETE_SUCCESS'}">
			alert("삭제가 완료되었습니다.");
		</c:when>
	</c:choose>
		$("#createPrescription").click(function(){
			location.href="<c:url value='/prescription/createPrescription'/>";

		});
	});
	
</script>
<body>
	
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
				
				<div class="location">
					<a href="<c:url value="/prescription/main"/>" style="cursor:pointer;"> <span class="first">환자 처방</span></a>
				</div>

				<!-- 컨텐츠영역 txt :s -->
				<div id="txt">
					<div class="tableDiv"></div>
					<h4 class="subtitle"><img src="../img/ico_Info.png" /><strong class="patient"><c:out value="${sessionScope.registration.patient.name}"/></strong>님의 전체 처방 내역</h4>
					<div class="tableDiv">
					<table class="tbl_basic center" style="table-layout:fixed;">
						<caption>
			                <strong>환자 내역</strong>
			                <details>
			                    <summary>환자 내역</summary>
			                </details>
			                <tbody>
			                	<tr>
			                		<th id="tableColumn" class="trw">환자 이름</th>
			                		<td id="tableColumn"><c:out value="${sessionScope.registration.patient.name}"/></td>
			                		<th id="tableColumn" class="trw">최신 처방 일자</th>
			                		<td id="tableColumn" colspan="2">
			                			<c:if test="${myPrescriptionList == null} ">
			                				<c:out value="처방내역이 없습니다"></c:out>
			                			</c:if>
			    						<c:forEach var="prescription" items="${myPrescriptionList}" varStatus="islast">
			    							<c:if test="${islast.last}">
			    								<fmt:formatDate value='${prescription.date }'  pattern="yy년 MM월dd일"/>
			    							</c:if>
			    						</c:forEach>
			                		</td>
			                	</tr>
			                </tbody>
			                <tbody>
								<tr>
			                		<th colspan="5" id="tableColumn">복용 ATC 목록</th>
			                	</tr>
			                	<tr>
			                    	<th class="trw" >No</th>
			                        <th class="trw" colspan="2" id="tableSubColumn">ATC코드</th>
			                        <th rightBorderRemove" colspan="2" id="tableSubColumn">ATC성분</th>
			                    </tr>
			                	
			                	<c:set var="cnt" value="${0}"/>
			                	<c:forEach var="registration" items="${registrationList}">
			                		<c:forEach var="prescription" items="${registration.prescriptionList}">
										<c:forEach var="tolerable" items="${prescription.tolerableList}">
											<c:set var="cnt" value="${cnt+1}"/>
											<tr>
												<td><c:out value="${cnt}"/></td>
												<td colspan="2"><c:out value="${tolerable.ATCCode}"/></td>
												<td colspan="2"><c:out value="${tolerable.ATCName}"/></td>
												<td style="display:none"><input class="tolerableList" value='<c:out value="${prescription.prescriptionID}"></c:out>'></td>
											</tr>
										</c:forEach>			                		
			                		</c:forEach>
			                	</c:forEach>
			                </tbody>
			                <tbody>
								<tr>
			                		<th colspan="5" id="tableColumn">금지 ATC 목록</th>
			                	</tr>
			                	<tr>
			                    	<th class="trw" >No</th>
			                        <th class="trw" colspan="2" id="tableSubColumn">ATC코드</th>
			                        <th colspan="2" id="tableSubColumn">ATC성분</th>
			                    </tr>
			                	<c:set var="cnt" value="${0}"/>
			                
			                	<c:forEach var="registration" items="${registrationList}">
			                		<c:forEach var="prescription" items="${registration.prescriptionList}">
										<c:forEach var="prohibition" items="${prescription.prohibitionList}">
											<c:set var="cnt" value="${cnt+1}"/>
											<tr>
												<td><c:out value="${cnt}"/></td>
												<td colspan="2"><c:out value="${prohibition.ATCCode}"/></td>
												<td colspan="2"><c:out value="${prohibition.ATCName}"/></td>
												<td style="display:none"><input class="prohibitionList" value='<c:out value="${prescription.prescriptionID}"></c:out>'></td>
			                   				</tr>
			                   			</c:forEach>
			                			                		
			                		</c:forEach>
			                	</c:forEach>
			                </tbody>

						<tbody>
								<tr>
			                		<th colspan="5" id="tableColumn">주의 ATC 목록</th>
			                	</tr>
			                	<tr>
			                    	<th class="trw" >No</th>
			                        <th class="trw" colspan="2" id="tableSubColumn">ATC코드</th>
			                        <th colspan="2" id="tableSubColumn">ATC성분</th>
			                    </tr>
			                	<c:set var="cnt" value="${0}"/>
			                	<c:forEach var="registration" items="${registrationList}">
			                		<c:forEach var="prescription" items="${registration.prescriptionList}">
										<c:forEach var="upper" items="${prescription.upperList}">
											<c:set var="cnt" value="${cnt+1}"/>
											<tr>
												<td><c:out value="${cnt}"/></td>
												<td colspan="2"><c:out value="${upper.ATCCode}"/></td>
												<td colspan="2"><c:out value="${upper.ATCName}"/></td>
												<td style="display:none"><input class="upperList" value='<c:out value="${prescription.prescriptionID}"></c:out>'></td>
											</tr>
										</c:forEach>            		
			                		</c:forEach>
			                	</c:forEach>	
							
			                </tbody>
			            </caption>
					</table>
					</div>
					<div class="tableDiv">
					<h4 class="subtitle"><c:out value="${patientID}"/> 날짜별 처방 리스트</h4>
					<!-- table :s -->
					<table class="tbl_basic center" style="table-layout:fixed;">
			            <caption>
			                <strong>날짜별 처방 리스트</strong>
			                <details>
			                    <summary>날짜별 처방 리스트</summary>
			                </details>
			            </caption>
			            <thead>
			            	<tr>
			            		<th class="trw" id="tableSubColumn" width=80>No</th>
			            		<th class="trw" id="tableSubColumn">날짜</th>
			            		<th class="trw" id="tableSubColumn" width=120>선택</th>
			            		<th class="trw" id="tableSubColumn" width=120>수정</th>
			            		<th id="tableSubColumn" width=120>삭제</th>
			            	</tr>
			            </thead>
	                	<c:set var="cnt" value="${0}"/>
	             
	                	<c:forEach var="prescription" items="${myPrescriptionList}">
							<c:set var="cnt" value="${cnt+1}"/>
							<tbody>
								<tr id="${prescription.prescriptionID}">
									<td><c:out value="${cnt}"/></td>
									<td><fmt:formatDate value='${prescription.date}'  pattern="yy년 MM월dd일 HH시mm분"/></td>
									<td><input type="button" class="btn_default" value="확인" onclick="checkPrescription('<c:out value="${prescription.prescriptionID }"/>')"></td>
									<td><input type="button" class="btn_save" value="수정" onclick="changePrescription('<c:out value="${prescription.prescriptionID }"/>')"></td>
									<td><input type="button" class="btn_save" value="삭제" onclick="deletePrescription('<c:out value="${prescription.prescriptionID }"/>',this)"></td>
								</tr>
							</tbody>
						</c:forEach>
		            </table>
		            <span class="rightButton">
						<Button id="createPrescription" name="prescription" class="mainbtn">처방하기</Button>
						<!-- <Button id="prescription" name="prescription" class="mainbtn">처방하기</Button> -->
					</span>
		            </div>   
		            <form id="selectPrescription" action="<c:url value="/prescription/prescription"/>" method="POST">
						<input type="hidden" id="date" name="date"/>
					</form>
					<form id="selectPatientInfo" action="<c:url value="/prescription/selectPatientInfo"/>" method="POST">
						<input type="hidden" id="patientID" name="patientID"/>
					</form>
					
						<form target = "viewPrescription" id="viewPrescription" action="<c:url value="/prescription/viewPrescription"/>" method="POST">
							<input id="viewPrescriptionInput" name="prescriptionID" type="hidden"/>
						</form>	
						<form id="changePrescription" action="<c:url value="/prescription/changePrescription"/>" method="POST">
							<input id="changePrescriptionInput" name="prescriptionID" type="hidden" />
						</form>	
					</div>
					<!-- 컨텐츠영역 txt :e -->
			</div>
			<!-- container :e -->

		</div>
		</div>
		<!-- contentsWrap :e -->


		<!-- footerWrap :s -->
		<div id="footerWrap">
			<c:import url="../common/footer.jsp"/>
		</div>
		<!-- footerWrap :e -->

	
</body>
</html>