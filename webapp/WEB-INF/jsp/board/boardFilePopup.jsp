<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.jsp" %>
<!doctype html>
<html>
<head>
<title>Daum에디터 - 이미지 첨부</title> 
<script src="<c:url value="/js/DaumEditor-master/daumeditor/js/popup.js"/>" type="text/javascript" charset="utf-8"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="<c:url value="/js/jquery-ui-1.10.3.custom.min.js"/>"></script>
<script src="<c:url value="/js/submenu.js"/>"></script>
<script type=text/javascript src="http://malsup.github.com/jquery.form.js"></script>
<link rel="stylesheet" href="<c:url value="/js/DaumEditor-master/daumeditor/css/popup.css"/>" type="text/css" />
<script type="text/javascript">
// <![CDATA[
	
	function done(jsonObject) {
		if (typeof(execAttach) == 'undefined') { //Virtual Function
	        return;
	    }
		var fileList = jsonObject.fileList;
		var _mockdata = {
			'attachurl': fileList[0].uploadFolder,
			'filename': fileList[0].saveName,
			'filesize': fileList[0].length,
		};
		execAttach(_mockdata);
		//closeWindow();
	}
	
	function validation(filename){
		return false;
	}

	function initUploader(){
	    var _opener = PopupUtil.getOpener();
	    if (!_opener) {
	        alert('잘못된 경로로 접근하셨습니다.');
	        return;
	    }
	    
	    var _attacher = getAttacher('file', _opener);
	    registerAction(_attacher);
	}
	
	$(document).ready(function (){ // <input type=file> 태그 기능 구현
		$('.file input[type=file]').change(function (){ 
			var inputObj = $(this).prev().prev(); // 두번째 앞 형제(text) 객체
			var fileLocation = $(this).val(); // 파일경로 가져오기 
			inputObj.val(fileLocation.replace('C:\\fakepath\\','')); // 몇몇 브라우저는 보안을 이유로 경로가 변경되서 나오므로 대체 후 text에 경로 넣기 
		});
		$('.submit a').on('click', function () { 
			var form = $('#daumOpenEditorForm'); // form id값 
			var fileName = $('.file input[type=text]').val(); // 파일명(절대경로명 또는 단일명) 
			form.ajaxSubmit({ 
				type: 'POST', 
				url: '<c:url value="/board/saveFileRowData"/>', 
				dataType: 'json', // 리턴되는 데이타 타입 
				beforeSubmit: function() { 
					if(validation(fileName)) { // 확장자 체크 (jpg, gif, png, bmp) 
						return false; 
					} 
				}, 
				success: function(jsonObject) { // fileInfo는 이미지 정보를 리턴하는 객체
					console.log("json success");
					if(jsonObject.result===-1) { // 서버단에서 체크 후 수행됨 
						alert('jpg, gif, png, bmp 확장자만 업로드 가능합니다.'); 
						return false; 
					} else if(jsonObject.result===-2) { // 서버단에서 체크 후 수행됨 
						alert('파일이 1MB를 초과하였습니다.'); 
						return false; 
					} else { 
						done(jsonObject); // 첨부한 이미지를 에디터에 적용시키고 팝업창을 종료 
						} 
				} , 
				error: function(e){
					console.log("error")
					console.log(e);
				}
				}); 
			});

	});

		
// ]]>
</script>
</head>
<body onload="initUploader();">
<div class="wrapper">
	<div class="header">
		<h1>파일 첨부</h1>
	</div>	
	<div class="body">
		<dl class=alert>  
		<dd> 
			<form id=daumOpenEditorForm encType=multipart/form-data method=post action=""> <!-- 파일첨부 --> 
				<div class=file> 
					<input disabled class=file-text> 
					<label class=file-btn for=uploadInputBox>사진첨부</label> 
					<input id=uploadInputBox style="display: none" type=file name=Filedata><!-- 버튼대체용(안보임) --> 
				</div> <!-- //파일첨부 --> 
			</form> 
		</dd> 
	</dl>
	</div>
	<div class="footer">
		<p><a href="#" onclick="closeWindow();" title="닫기" class="close">닫기</a></p>
		<ul>
			<li class="submit"><a href="#" title="등록" class="btnlink">등록</a></li>
			<li class="cancel"><a href="#" onclick="closeWindow();" title="취소" class="btnlink">취소</a></li>
		</ul>
	</div>
</div>
</body>
</html>