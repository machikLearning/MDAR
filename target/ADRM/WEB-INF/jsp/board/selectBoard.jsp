<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Script-Type" content="text/javascript">
	<meta http-equiv="Content-Style-Type" content="text/css">
	<meta http-equiv="X-UA-Compatible" content="IE=8">
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
		$("#body").html($("#hiddenbody").text());
		$(".replyDisplayNone").each(function(){
			makeReplyTree($(this).children(".replyID").text(),$(this).children(".replyDepth").text(),$(this).children(".replyUserID").text(),$(this).children(".replyDate").text(),$(this).children(".replyParentID").text(),$(this).children(".replyBody").text());
		});
		$("#replyLinear")
	});
	
	
	function InsertTwoDepthReply(obj){
		var clone = $(".replyForm").clone();
		clone.attr("class","activeClone");
		clone.children("input[name=parentID]").val($(obj).parent().parent().children(".replyID").text());
		clone.children("input[name=depth]").val(Number($(obj).parent().parent().children(".replyDepth").text())+Number(1));
		$(obj).parent().parent().append(clone);
	}
	
	function makeReplyTree(replyid,depth,userID,date,parentID,replyBody){
		var clone = $(".replyClone").clone();
		clone.attr("class","");
		
		clone.attr("id","reply"+replyid);
		clone.children("div[class=replyID]").text(replyid);
		clone.children("div[class=replyDepth]").text(depth);
		clone.children("div[class=replyUserID]").text(userID);
		clone.children("div[class=replyDate]").text(date);
		clone.children("div[class=replyBody]").text(replyBody);
		clone.css("display", "block");
		console.log(clone);
		if(depth == 1){
			$("#replyBox").append(clone);
		}else{
			$("#reply"+parentID).append(clone);
		}
	}
	
	function modifyReply(obj){
		if(confirmAuthority($(obj).parent().parent().children(".replyUserID").text())){
			var clone = $(".replyForm").clone();
			var originalReply = $(obj).parent().parent();
			console.log();
			clone.attr("class","");
			clone.attr("action","<c:url value='/board/modifyReply'/>");
			clone.attr("id","");
			clone.children("input[name=replyID]").val($(originalReply).children(".replyID").text());
			clone.children("textarea[name=replyBody]").val($(originalReply).children(".replyBody").text());
			clone.css("display", "block");
			console.log(clone);
			$(obj).parent().parent().html(clone);
		}else{
			alert("게시자 또는 관리자만 수정할 수 있습니다");	
		}
	}
	
	function deleteReply(obj){
		if(confirmAuthority($(obj).parent().parent().children(".replyUserID").text())){
			$.ajax({
				type:"POST",
				url : "<c:url value="/board/deleteReply"/>",
				async:false,
				data: {"replyID" : $(obj).parent().parent().children(".replyID").text()},
				dataType : "JSON",
				success: function(json){
					if(json.result){
						location.reload();
					}else{
						alert("삭제할 수 없습니다");
					}
				},
				error:function(e){
					alert("통신상태가 원할하지 않습니다");
					console.log(e)
				}	
			});
		}else{
			alert("게시자 또는 관리자만 삭제할 수 있습니다");	
		}
	}
	
	function modifyBoard(){
		if(confirmAuthority('<c:out value="${boardDataType.userID}"></c:out>')){
			$("#modifyBoardForm").children("#modifyBoardInput").val(<c:out value='${boardDataType.boardID}'/>);
			$("#modifyBoardForm").submit();
		}else{
			alert("게시자 또는 관리자만 수정할 수 있습니다");	
		}
	}
	
	function deleteBoard(){
		if(confirmAuthority('<c:out value="${boardDataType.userID}"></c:out>')){
			$("#deleteBoardForm").children("#deleteBoardInput").val(<c:out value='${boardDataType.boardID}'/>);
			$("#deleteBoardForm").submit();
		}else{
			alert("게시자 또는 관리자만 삭제할 수 있습니다");	
		}
	}
	
	function confirmAuthority(userID){
		var bool = false;
		$.ajax({
			type:"POST",
			url : "<c:url value="/board/confirmAuthority"/>",
			async:false,
			data: {"userID" : userID},
			dataType : "JSON",
			success: function(json){
				if(json.result == "1"){
					bool = true;
				}else{
					return false;
				}
			},
			error:function(e){
				alert("통신상태가 원할하지 않습니다");
				console.log(e)
			}	
		});
		console.log(bool);
		return bool
	}

</script>
</head>
<body>
	<div id="wrapper">
		<div id="headerWrap">
			<c:import url="../common/prescription/header.jsp"/>
		</div>
		<!-- header :e -->


		<!-- contentsWrap :s -->

			<!-- container :s -->
			
		
		<div id="hiddenbody" style="display:none">
			<c:out value="${boardDataType.boardBody}"></c:out>
		</div>
	
		<div id="contentsWrap">
			<!-- container :s -->
				<div id="lnbWrap">
					<c:import url="../common/prescription/submenu.jsp"/>
				</div>
				<div id="container">
				<div id="txt">
				<table  class="tbl_basic center" style="table-layout:fixed;">
					<tr id="titleBox">
						<td>제목 : </td>
						<td id="title"><c:out value="${boardDataType.boardTitle}"></c:out></td>
					</tr>
					<tr>
						<td id="writer">작성자 : <c:out value="${boardDataType.userID}"></c:out></td>
						<td id="date">날짜 : <c:out value="${boardDataType.date}"></c:out></td>
			
					</tr>
					<tr>
						<td id="modifyBoardBodyButton"><button onclick="modifyBoard()">수정하기</button></td>
						<td id="deleteBoardBodyButton"><button onclick="deleteBoard()">삭제하기</button></td>
					</tr>
				</table>
				<div id="body"></div>
				<div style="margin-left:auto;margin-right:auto">
					<form action="<c:url value='/board/insertReply'/>" method="POST" class="replyForm">
						<textarea style="float:left;width:80%;height:30px;" rows="5" name="replyBody" class="replyBody"></textarea>
						<input type="text" name="depth" value="" style="display:none"/>
						<input type="text" name="parentID" value="" style="display:none"/>
						<input type="text" name="replyID" value="" style="display:none" class="replyID">
						<input type="text" name="boardID"  value="<c:out value='${boardDataType.boardID}'/>" style="display:none" >
						<input class="subbtn" style="width:10%"type="submit" value="submit">
					
					</form>
				</div>
				<div id="replyLinear">
					<c:choose>
						<c:when test="${!empty boardDataType.replyList}">
							<c:set var="cnt" value="${0}"/>
							<c:forEach var="reply" items="${boardDataType.replyList}">
								<div class="replyDisplayNone" style="display:none">
									<c:set var="cnt" value="${cnt+1}"></c:set>
									<div class="replyID" style="display:none"><c:out value="${reply.replyid}"/></div>
									<div class="replyDepth" style="display:none"><c:out value="${reply.depth}"/></div>
									<div class="replyUserID"><c:out value="${reply.userID}"></c:out></div>
									<div class="replyDate"><c:out value="${reply.date}"></c:out></div>
									<div class="replyParentID"><c:out value="${reply.parentID}"></c:out></div>
									<div class="replyBody"><c:out value="${reply.replyBody}"></c:out></div>
								</div>	
							</c:forEach>
						</c:when>
					</c:choose>
				</div>
	
				<div id="replyBox"></div>
			</div>
			</div>
		</div>
	</div>
	
	<div id="footerWrap">
			<c:import url="../common/footer.jsp"/>
	</div>
	
	<div class="replyClone" style="display:none">
						<div class=replyID style="display:none"></div>
						<div class=replyDepth style="display:none"></div>
						<div class=replyUserID style="float:left;width:50%"></div>
						<div class=replyDate style="float:left;width:50%"></div>
						<div class=replyBody></div>
						<div><button onclick="InsertTwoDepthReply(this)">답글달기</button></div>
						<div><button onclick="modifyReply(this)">수정하기</button></div>
						<div><button onclick="deleteReply(this)">샂제하기</button></div>
					</div>
				
	
	<div id="replyTemplate" style="display:none">
		<div class="twoDepthReplyHeader">
			<div class="twoDepthReplyWriter"></div>
			<div class="twoDepthReplyDate"></div>
			
		</div>
		<div class="twoDepthReplyID" style="display:none"></div>
		<div class="twoDepthReplyBody"></div>
		<div class="moreDepthReplyBody"></div>
	</div>
	<div>
		<form id="modifyBoardForm" action="<c:url value='/board/modifyBoard'/>">
			<input id="modifyBoardInput" type="text" style="display:none" name="boardID">
		</form>
		<form id="deleteBoardForm" action="<c:url value='/board/deleteBoard'/>">
			<input id="deleteBoardInput" type="text" style="display:none" name="boardID">
		</form>
	</div>
</div>
</body>
</html>