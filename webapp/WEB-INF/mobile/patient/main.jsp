<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("newline", "\n"); %>

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
	<script src="<c:url value="/js/submenu.js"/>"></script>
	<script type="text/javascript">
		function openActionPlan(){
			<c:choose>
				<c:when test="${prescription.contentActionPlanDataType.actionPlan == null || prescription.contentActionPlanDataType.actionPlan == ''}">
					alert("Action Plan이 없습니다.");
				</c:when>
				<c:otherwise>
					alert("<c:out value="${fn:replace(prescription.contentActionPlanDataType.getSimpleActionPlan(), '<br>', '\\\n')}"/>");
				</c:otherwise>
			</c:choose>
			
		}
	</script>
</head>
<body>
<!-- wrap :s -->
    <div id="wrap">
        <!-- content :s -->
        <div id="content">
        <!-- paging & search :s -->
        <c:if test="${paging.pageCount >= 2}">
			<ul class="paginate">
				<c:if test="${paging.nowPageGroup > 1 }">
					<li class="dir prev">
						<a href="<c:url value="/patient/main" >
						<c:param name="page" value="${paging.nowPage-1}"/>
						<c:param name="id" value="${id}" />
						</c:url>" onclick="" title="이전페이지로 이동">«</a>
					</li>
				</c:if>
				<c:forEach var="i" begin="${paging.startPage }" end="${paging.endPage }">
					<c:choose>
						<c:when test="${param.page == null and i == 1 }">
							<li class="active">
						</c:when>
						<c:when test="${param.page == i }">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="<c:url value="/patient/main" >
					<c:param name="page" value="${i }"/>
					<c:param name="id" value="${id}" />
					</c:url>" title="<c:out value="${i }"/>페이지" onclick=" ">
					<c:out value="${i }"/>
					</a>
					</li>
				</c:forEach>
				<c:if test="${paging.nowPage < paging.endPage }" >
					<li class="dir next">
	     			<a href="<c:url value="/patient/main" >
	      			<c:param name="page" value="${paging.nowPage+1 }"/>
	      			<c:param name="id" value="${id}" />
	      			</c:url>" onclick=" " title="다음페이지로 이동">»</a></li>
	       		</c:if>
	   		</ul>
	   		</c:if>
			<section class="mainSection">
                <h2 class="card_ti">의약품<span>안전카드</span></h2>
                <div class="card_wrap">
                    <!-- 의약품내용 :s -->
                    <table class="tbl_basic center" style="table-layout:fixed;">
                        <tbody>
                        <tr>
                                <th width="20%">약물유해반응</th>
                                <td width="70%">${fn:replace(prescription.contentActionPlanDataType.content, newline, '<br>')}</td>
                            </tr>
                            <tr>
								<th width="20%">금기 약제</th>
								<td width="70%">
									<c:set value="0" var="cnt"/>	
									<c:forEach var="prohibition" items="${prescription.prohibitionList }">
										<c:set value="${cnt+1 }" var="cnt"/>
										<c:choose>
                                            <c:when test = "${prohibition.ATCShortName eq null}">
                                        	    <c:out value="${prohibition.ATCName }"/>
                                        	</c:when>
                                        	<c:otherwise>
                                        	    <c:out value="${prohibition.ATCShortName }"/>
                                        	</c:otherwise>
                                        </c:choose>
										<c:if test="${fn:length(prescription.prohibitionList) != cnt}">
											/
										</c:if>
										<br>			                  
									</c:forEach>
                               </td>
                            </tr>
                            <tr>
								<th width="20%">주의 약제</th>
								<td width="70%">
									<c:set value="0" var="cnt"/>
									<c:forEach var="upper" items="${prescription.upperList }">
										<c:set value="${cnt+1 }" var="cnt"/>
										<c:choose>
                                            <c:when test = "${upper.ATCShortName eq null}">
                                                <c:out value="${upper.ATCName }"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${upper.ATCShortName }"/>
                                            </c:otherwise>
                                        </c:choose>
										<c:if test="${fn:length(prescription.upperList) != cnt}">
											/
										</c:if>
										<br>				                  
									</c:forEach>
                                </td>
                            </tr>
                            <tr>
								<th width="20%">복용 가능 약제</th>
								<td width="70%">
									<c:set value="0" var="cnt"/>
									<c:forEach var="tolerable" items="${prescription.tolerableList }">
										<c:set value="${cnt+1 }" var="cnt"/>
										<c:choose>
                                            <c:when test = "${tolerable.ATCShortName eq null}">
                                                <c:out value="${tolerable.ATCName }"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${tolerable.ATCShortName }"/>
                                            </c:otherwise>
                                        </c:choose>
										<c:if test="${fn:length(prescription.tolerableList) != cnt}">
											/
										</c:if>
										<br>				                  
									</c:forEach>
                               </td>
                            </tr>
                            <tr>
                                <th width="20%">Action Plan</th>
                                <td width="70%">
                                	<input type="button" value="확인하기" class="btn_login" onclick="openActionPlan()">
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div class="card_txt">
                        <p>본 카드를 소지한 분은 위의 약제 투여 시 부작용 발생이 우려되어 각별한 주의를 요합니다. </p>
                        <strong>발급일   <fmt:formatDate value='${prescription.date }'  pattern="yy. MM. dd"/></strong>
                    </div>
                    <div class="card_txt">
                    	<c:if test="${hospital.contactName1 != null 
                    			&& hospital.contactName1 != ''
                    			&& hospital.contactTel1 != null
                    			&& hospital.contactTel1 != ''}">
                        	<p><c:out value="${hospital.contactName1 }"/> : <c:out value="${hospital.contactTel1 }"/></p>
                    	</c:if>
                        <c:if test="${hospital.contactName2 != null 
                    			&& hospital.contactName2 != ''
                    			&& hospital.contactTel2 != null
                    			&& hospital.contactTel2 != ''}">
                        	<p><c:out value="${hospital.contactName2 }"/> : <c:out value="${hospital.contactTel2 }"/></p>
                    	</c:if>
                    </div>
                    <!-- 의약품내용 :e -->
				</div>          
             </section>
        </div>
        <!-- content :e -->

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
