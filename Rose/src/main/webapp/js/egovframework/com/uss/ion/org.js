function org_debug(msg){
	if(window.console) window.console.log(msg);
}

function getUser(userID, deptId){
	var userInfo = false;
	$.ajax({                        
		type: "POST",
		url: APPROVAL_CONTEXT +"/user.do",
		dataType: 'json',
		cache: false,
		data:{"userId": userID, "deptId": deptId},
		async: false,
		success: function(data) {
			userInfo = data;
		},
		error : function(jqXHR, textStatus, errorThrown){
			alert("getUser error. textStatus["+textStatus+"], errorThrown["+errorThrown+"]");
			org_debug("getUser error. textStatus["+textStatus+"], errorThrown["+errorThrown+"]");
		}
    });
	return userInfo;
}
function getDept(deptId){
	var deptInfo = false;
	var action = "getDeptJson";
	$.ajax({                        
		type: "POST",
		url: APPROVAL_CONTEXT +"/deptView.do",
		dataType: 'json',
		cache: false,
		data:{"deptId": deptId, "action": action},
		async: false,
		success: function(data) {
			deptInfo = data;
		},
		error : function(jqXHR, textStatus, errorThrown){
			alert("getDept error. textStatus["+textStatus+"], errorThrown["+errorThrown+"]");
			org_debug("getDept error. textStatus["+textStatus+"], errorThrown["+errorThrown+"]");
		}
    });
	return deptInfo;
}