jQuery.validator.addMethod("isPatient", function(value, element, param) {
	if($(param).is(":checked")) {
		if(value != "") {
			return true;
		} else {
			return false;
		}
	}
	return true;
}, "환자일 경우 병원코드를 입력해야 합니다.");