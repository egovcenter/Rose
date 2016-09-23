
/*
 * @param targetURL 	: file upload 처리 url
 * @param installPath	: 설치 위치
 * @param ext 			: 허용 확장자
 * @param fileLimit 	: 첨부 허용 갯수
 * @return object 		: 첨부 control 내부 변수에 접근 할 수 있는 object
 * 						  object.getOrgFileNames : 첨부 당시의 파일명을 배열로 Return
 * 						  object.getSysFileNames :  첨부파일의 시스템 파일명을 배열로 Return
 * 						  object.getTotalFileSize : 첨부된 파일의 전체 크기를 Return;
 * 						  object.getFileID : 첨부된 파일의 FileID를 Return;
 * */ 
(function($){
	$.fn.fileUpload = function(initial)
	{
		var fileUploadSettings = $.extend(
						{
							fileIdPrefix : "frmwk_attach_",
							event : null,
							fileAttachSize : null,
							totalFileSize : 0,
							attachList : {},
							status : false,
							fileUploadCount : 0,
							event : null,
							extension : null,
							notsupportedextension : ['js', 'jsp'],
							files : [],
							browser : $.browser,
							fileLimit : 10,
							curruntLength : null, 
							fileLengthLimit : 1024 * 1024 * 100,
							supportHTML5 : true,
							dropboxId : "filelistlayer",
							usePopup : false,
							fileNameLength : 9999,
							fileUploadForm : "fileUploadForm",
							filelist : "filelist",
							filelistlayer : "filelistlayer",
							fileUploadTargetDiv : "fileUpload",
							suppportMove: false
						}, 
						initial||{}
					);
		
		if(!$("#" + fileUploadSettings.fileUploadTargetDiv).attr("class"))
			$("#" + fileUploadSettings.fileUploadTargetDiv).attr("class", "fileUpload");
		
		fileUploadSettings.debug = function(obj){
			if(window.console && window.console.log){
				console.log(obj);
			}
		}
		fileUploadSettings.debug("fileUploadSettings.on:"+fileUploadSettings.on);
		fileUploadSettings.debug("fileUploadSettings.fileUploadForm:"+fileUploadSettings.fileUploadForm);
		if(typeof(fileUploadSettings.on) != 'undefined'){
			for(var eventName in fileUploadSettings.on){
				fileUploadSettings.debug("fileUploadSettings.on["+eventName+"]:"+fileUploadSettings.on[eventName]);
				this.bind(eventName, fileUploadSettings.on[eventName]);
				fileUploadSettings.debug("bind ["+eventName+"]:"+fileUploadSettings.on[eventName]);
			}
		}
		if(fileUploadSettings.targetURL.indexOf('/FileUploadServlet') == 0){
			fileUploadSettings.targetURL = fileUploadSettings.targetURL.replace('/FileUploadServlet','/servlet/FileUploadServlet');
		}
		$.localize({
			path : fileUploadSettings.installPath+"/jquery.fileUpload/localization", 
			package : "fileUploadLang"
		});
		// check the functions of drag and drop, XMLHttpRequest2(progress, send(blob)), multiple files, File API
	    fileUploadSettings.tests = {
	      //filereader: typeof FileReader != 'undefined',//ignore this api for safari
	      dnd: 'draggable' in document.createElement('span'),
	      progress: "upload" in new XMLHttpRequest,
	      //multiple: typeof window.FileList != 'undefined'
	      multiple: typeof FileReader != 'undefined'
	    };
	    
	    fileUploadSettings.html5 = true;
	    $.each(fileUploadSettings.tests, function(key, value){
	    	fileUploadSettings.html5 = fileUploadSettings.html5 && value;
	    	fileUploadSettings.debug("html5 " + key +"="+value);
	    });
	    fileUploadSettings.html5 = fileUploadSettings.supportHTML5 && fileUploadSettings.html5;
	    fileUploadSettings.debug("fileUploadSettings.html5 ="+fileUploadSettings.html5);
	    
		fileUploadSettings.uploadURL = fileUploadSettings.targetURL + (/\?.*=/.test(fileUploadSettings.targetURL) ? "&" : "?") + "acton=upload";
		fileUploadSettings.progressURL = fileUploadSettings.targetURL;
		fileUploadSettings.downloadURL = fileUploadSettings.targetURL + (/\?.*=/.test(fileUploadSettings.targetURL) ? "&" : "?") + "acton=download";
		
fileUploadSettings.debug("fileUploadSettings.ext ="+fileUploadSettings.ext);
		fileUploadSettings.extension = (fileUploadSettings.ext == null || fileUploadSettings.ext == '') ? new Array() : ((typeof fileUploadSettings.ext == 'object' && fileUploadSettings.ext["splice"] !== null) ? fileUploadSettings.ext : fileUploadSettings.ext.split(","));
fileUploadSettings.debug("fileUploadSettings.extension ="+fileUploadSettings.extension);
		if(!fileUploadSettings.notsupportedextension || fileUploadSettings.notsupportedextension.length == 0) fileUploadSettings.notsupportedextension = ['js', 'jsp'];
		/*
		 * 허용되는 확장자인지 확인한다.  
		 * */
		fileUploadSettings.checkExtension = function(fileName)
		{
fileUploadSettings.debug("fileUploadSettings.extension ="+fileUploadSettings.extension.join(","));
			if(fileUploadSettings.extension.length === 1 && fileUploadSettings.extension[0] === '*') return true;
			var val = fileName;
			var chk = false;
fileUploadSettings.debug("val ="+val);
			for(var i = 0 ; i < fileUploadSettings.extension.length ; i++)
			{
				if(eval("/\\."+fileUploadSettings.extension[i]+"$/i").test(val)) return true;
			}
fileUploadSettings.debug("fileUploadSettings.notsupportedextension ="+fileUploadSettings.notsupportedextension);
			for(var i = 0 ; i < fileUploadSettings.notsupportedextension.length ; i++)
			{	
				if(eval("/\\."+fileUploadSettings.notsupportedextension[i]+"$/i").test(val)) {
					return false;
				}
			}
			// ext 가 설정된경우 return false, 설정되지 않은 경우 return true 
			return fileUploadSettings.extension.length < 1 ? true : false;
		}
		/*
		 * 파일을 서버로 전송한다. (under HTML5)
		 * */
		fileUploadSettings.submit = function()
		{
			var ajaxSubmit = $.fn.ajaxSubmit;
			var uploadProcess = {
				dataType: 'json',
				success : fileUploadSettings.showResponse,
				error : fileUploadSettings.errorResponse
			};
			var that = this;
			that.unbind("submit");
			that.submit(function(){
				fileUploadSettings.status = true; // during uploading.. fileUploadSettings.status is true.. or false....
				if($.fck && $.fck.intercepted && $.fck.intercepted.ajaxSubmit){
			     	$.fck.intercepted.ajaxSubmit.apply($(that), [uploadProcess]);
			    }
				else{
     				$(that).ajaxSubmit(uploadProcess);
    			}
				fileUploadSettings.startUpload();
				return false;
			});
			that.submit();
		}
		fileUploadSettings.getFileName = function(path){
			if(path.indexOf('/') > -1){
				return path.substring(path.lastIndexOf('/') + 1);
			}else if(path.indexOf('\\') > -1){
				return path.substring(path.lastIndexOf('\\') + 1);
			}else{
				return path;
			}
		}
		fileUploadSettings.showFileTotalMaxSizeMessage = function(maxUploadSize){
			var fileLengthLimit = typeof(maxUploadSize) != 'undefined' ? maxUploadSize : fileUploadSettings.fileLengthLimit;
			if(fileLengthLimit < 1000){
				alert(fileUploadLanguage.error.too_much_total_file_length + " [" +  fileUploadLanguage.error.limit_size + ":" + fileLengthLimit + " B]");
			}else if(fileLengthLimit/1024 < 1000){
				alert(fileUploadLanguage.error.too_much_total_file_length + " [" +  fileUploadLanguage.error.limit_size + ":" + parseInt(fileLengthLimit/1024) + " KB]");
			}else if(fileLengthLimit/1048576 < 1000){
				alert(fileUploadLanguage.error.too_much_total_file_length + " [" +  fileUploadLanguage.error.limit_size + ":" + parseInt(fileLengthLimit/1048576) + " MB]");
			}else{
				alert(fileUploadLanguage.error.too_much_total_file_length + " [" +  fileUploadLanguage.error.limit_size + ":" + parseInt(fileLengthLimit/1073741824) + " GB]");
			}
		}
		fileUploadSettings.showFileMaxSizeMessage = function(maxUploadSize){
			var fileLengthLimit = typeof(maxUploadSize) != 'undefined' ? maxUploadSize : fileUploadSettings.fileLengthLimit;
			if(fileLengthLimit < 1000){
				alert(fileUploadLanguage.error.too_large_file_size + " [" +  fileUploadLanguage.error.limit_size + ":" + fileLengthLimit + " B]");
			}else if(fileLengthLimit/1024 < 1000){
				alert(fileUploadLanguage.error.too_large_file_size + " [" +  fileUploadLanguage.error.limit_size + ":" + parseInt(fileLengthLimit/1024) + " KB]");
			}else if(fileLengthLimit/1048576 < 1000){
				alert(fileUploadLanguage.error.too_large_file_size + " [" +  fileUploadLanguage.error.limit_size + ":" + parseInt(fileLengthLimit/1048576) + " MB]");
			}else{
				alert(fileUploadLanguage.error.too_large_file_size + " [" +  fileUploadLanguage.error.limit_size + ":" + parseInt(fileLengthLimit/1073741824) + " GB]");
			}
		}
		/*
		 * 전송전 파일에 대한 검사(중복 파일이름, 파일 크기) (over HTML5)
		 */
		fileUploadSettings.validateLocalFiles = fileUploadSettings.html5 ? function(files){
			if(!files || files.length < 1) return false;
			if(fileUploadSettings.interruptStatus) return false;
			//if(fileUploadSettings.status) {alert('try later!!');return false};
			if(fileUploadSettings.status) {alert(fileUploadLanguage.error.during_uploading);return false};
			var sizeExceed = false;
			if((fileUploadSettings.files.length + files.length) > fileUploadSettings.fileLimit){
				alert(fileUploadLanguage.error.too_many_file_count + " [" +  fileUploadLanguage.error.limit_count.replace("%COUNT%", fileUploadSettings.fileLimit) + "]");
				return false;
			};
			var uploadFilesSize = 0;
			for(var i = 0; i< files.length; i++){
				if(files[i].size <= 0){
					alert(fileUploadLanguage.error.zero_size_not_allowed);
					return false;
				}
				uploadFilesSize += files[i].size;
			}
			if(! sizeExceed && (outerModule.getTotalFileSize() + uploadFilesSize) >= fileUploadSettings.fileLengthLimit) {
				fileUploadSettings.showFileTotalMaxSizeMessage();
				return false;
			}
			
			for(var i = 0; i< files.length; i++){
				if(!fileUploadSettings.checkExtension(files[i].name)) {
					alert(fileUploadLanguage.error.extension_not_supported);
					return false;
				}
				
				if (fileUploadSettings.countUTF8(files[i].name) > fileUploadSettings.fileNameLength){
					alert(fileUploadLanguage.error.too_long_file_name.replace("%LENGTH_ENG%", fileUploadSettings.fileNameLength).replace("%LENGTH_HAN%", parseInt(fileUploadSettings.fileNameLength/3)));
					return false;
				}
				var filename = files[i].name; 
				//같은 파일 업로드시 check
				for (var j = 0; j < fileUploadSettings.files.length; j++){
					if (fileUploadSettings.files[j].orgFileName == filename){
						alert(fileUploadLanguage.error.is_same_file_name);
						return false;
					}
				}
			}
			return true;
		} : function (fileName){
			if(!fileName || fileName == "") return false; 
			if(fileUploadSettings.interruptStatus) return false;
			//if(fileUploadSettings.status) {alert('try later!!');return false};
			if(fileUploadSettings.status) {alert(fileUploadLanguage.error.during_uploading);return false};
			var sizeExceed = false;
			if(fileUploadSettings.files.length >= fileUploadSettings.fileLimit){
				alert(fileUploadLanguage.error.too_many_file_count + " [" +  fileUploadLanguage.error.limit_count.replace("%COUNT%", fileUploadSettings.fileLimit) + "]");
				return false;
			};
			if(! sizeExceed && outerModule.getTotalFileSize() >= fileUploadSettings.fileLengthLimit) {
				fileUploadSettings.showFileTotalMaxSizeMessage();
				return false;
			}
			
			if(!fileName) return false;
			if(!fileUploadSettings.checkExtension(fileName)) {
				alert(fileUploadLanguage.error.extension_not_supported);
				return false;
			}

			if (fileUploadSettings.countUTF8(fileName) > fileUploadSettings.fileNameLength){
				alert(fileUploadLanguage.error.too_long_file_name.replace("%LENGTH_ENG%", fileUploadSettings.fileNameLength).replace("%LENGTH_HAN%", parseInt(fileUploadSettings.fileNameLength/3)));
				return false;
			}

			var filename = fileUploadSettings.getFileName(fileName);
			//같은 파일 업로드시 check
			for (var i = 0; i < fileUploadSettings.files.length; i++){
				if (fileUploadSettings.files[i].orgFileName == filename){
					alert(fileUploadLanguage.error.is_same_file_name);
					return false;
				}
			}
			return true;
		}
		
		fileUploadSettings.xhrs = [];
		/*
		 * xhr 2를 이용한 upload files (over HTML5)
		 */
		fileUploadSettings.uploadFiles = fileUploadSettings.html5 ? function(files){
			//alert("UploadFile file:"+file);
				if(fileUploadSettings.onStart) {
					if(!fileUploadSettings.onStart(files)){
						return;
					}
				}
				
				for(var i=0; i<files.length; i++){
					var fileInfo = {
						itemId : fileUploadSettings.fileIdPrefix+fileUploadSettings.fileUploadCount++,
						sysFileName		: null,
						orgFileName		: files[i].name,
						fileSize		: files[i].size,
						progressStatus	: false
					}
					fileUploadSettings.xhrs[fileInfo.itemId] = new XMLHttpRequest();
					var li = $("<li id=\"li_"+fileInfo.itemId+"\"/>")
					$("#" + fileUploadSettings.filelist).append(li);
					li.append("<div>" +
							"<span class=\"filename\"><input type=\"checkbox\" id=\"chk_"+fileInfo.itemId+"\" value=\""+fileInfo.itemId+"\" class=\"chk_file\"><img src=\""+fileUploadSettings.installPath+"/jquery.fileUpload/images/pubcon.gif\"><a id=\"a_"+fileInfo.itemId+"\">"+fileInfo.orgFileName+"</a>" +
							"<span class=\"fileprogress\" id=\"fileprogress_"+fileInfo.itemId+"\">" +
							"<span class=\"progressHolder\"><span class=\"progress\" id=\"progress_"+fileInfo.itemId+"\"></span></span>" +
							"</span>" +
							"</span>" +
							"<span class=\"filesize\" id=\"filesize_"+fileInfo.itemId+"\">"+(Math.floor(fileInfo.fileSize/1024)+"").numberFormat()+" KB</span>" +
							"<img class=\"btn_x\" id=\"img_"+fileInfo.itemId+"\" src=\""+fileUploadSettings.installPath+"/jquery.fileUpload/images/btn_del.gif\" width=\"16\" height=\"16\" alt=\"delete file\"/>" +
							"</div>");
					$("#img_"+fileInfo.itemId).bind("click", fileUploadSettings.interrupt);

					$("#progressfilesinfo").val(files[i].name + "("+(i+1)+"/"+files.length+")");
					fileUploadSettings.xhrs[fileInfo.itemId].upload.fileInfo = fileInfo;
					// progress bar
					fileUploadSettings.xhrs[fileInfo.itemId].upload.addEventListener("loadstart", function(e) {
						//$("#progressfilesinfo").val(files[i].name + "("+(i+1)+"/"+files.length+")");
					}, false);
					fileUploadSettings.xhrs[fileInfo.itemId].upload.addEventListener("progress", function(e) {
fileUploadSettings.debug("uploadFiles progress fileInfo.itemId="+this.fileInfo.itemId+",fileInfo.orgFileName="+this.fileInfo.orgFileName+",e.loaded="+e.loaded+",e.total="+e.total + ", this.id=" + this.id);
						fileUploadSettings.updateProgressStatus(this.fileInfo, e.loaded, e.total, i, files.length);
					}, false);
					fileUploadSettings.xhrs[fileInfo.itemId].upload.addEventListener("abort", function(e) {
						alert(fileUploadLanguage.message.interrupted);
					}, false);
					fileUploadSettings.xhrs[fileInfo.itemId].upload.addEventListener("error", function(e) {
						fileUploadSettings.errorResponse();
					}, false);
					fileUploadSettings.xhrs[fileInfo.itemId].upload.addEventListener("load", function(e) {
					}, false);
					fileUploadSettings.xhrs[fileInfo.itemId].upload.addEventListener("timeout", function(e) {
					}, false);
					fileUploadSettings.xhrs[fileInfo.itemId].upload.addEventListener("loadend", function(e) {
						fileUploadSettings.makeFinish(this.fileInfo);
						jQuery("#amountText").remove();
					}, false);

fileUploadSettings.debug("start file:"+files[i].name);
					// file received/failed
					fileUploadSettings.xhrs[fileInfo.itemId].onreadystatechange = function(e) {
						if (this.readyState == 4) {
							try{
								var result = eval('(' + this.responseText + ')');
								if(result.response.files && result.response.files.length > 0){
									var _fileInfo = fileUploadSettings.getFileInfo(result.response.files[0].itemID);
fileUploadSettings.debug("onreadystatechange result.response.files[0].itemID="+result.response.files[0].itemID+", file:"+_fileInfo.orgFileName+",_fileInfo.fileSize="+_fileInfo.fileSize);
									fileUploadSettings.updateProgressStatus(_fileInfo, _fileInfo.fileSize, _fileInfo.fileSize, i, files.length);
								}
								fileUploadSettings.showResponse(result, this.readyState);
							}catch(error){
							}
						}
					};
					
					fileUploadSettings.files.push(fileInfo);
	
					// start upload
					fileUploadSettings.xhrs[fileInfo.itemId].open("POST", fileUploadSettings.uploadURL, true);
					fileUploadSettings.xhrs[fileInfo.itemId].setRequestHeader("X_FILENAME", encodeURI(files[i].name));
					fileUploadSettings.xhrs[fileInfo.itemId].setRequestHeader("X_ITEMID", fileInfo.itemId);
					fileUploadSettings.xhrs[fileInfo.itemId].setRequestHeader("Content-Type", files[i].type);
fileUploadSettings.debug("onreadystatechange files[i].type="+files[i].type);
					fileUploadSettings.xhrs[fileInfo.itemId].send(files[i]);
					
					//fileUploadSettings.xhrs[fileInfo.itemId] = xhr;
				}
		} : function (fileName){
			if(fileUploadSettings.onStart) {
				if(!fileUploadSettings.onStart(fileName)){
					return;
				}
			}
			var filename = fileUploadSettings.getFileName(fileName);
			fileUploadSettings.files.push({
				itemId : fileUploadSettings.fileIdPrefix+fileUploadSettings.fileUploadCount,
				sysFileName		: null,
				orgFileName		: filename,
				fileSize		: 0,
				progressStatus	: false
			});
			fileUploadSettings.submit.call($("#"+fileUploadSettings.fileUploadForm));
		}
		/**
		 * 파일을 선택했을때 실행되는 event handler
		 */
		fileUploadSettings.onchangeSingle = fileUploadSettings.html5 ? function(event)
		{
//alert("fileUploadSettings.onchangeSingle called");
			if(this == event.target){
				fileUploadSettings.status = !fileUploadSettings.doneUploadProcess();
//alert("fileUploadSettings.onchangeSingle $(this).val():"+$(this).val());
				if(!$(this).val() || $(this).val() === "") return false; 
				
				if(!fileUploadSettings.validateLocalFiles(event.target.files)) {
					fileUploadSettings.clearFileTag();
					return false;
				}
			
				fileUploadSettings.uploadFiles(event.target.files);
				
			}
			return false;
		} : function(event)
		{
			if(this == event.target){
				fileUploadSettings.status = !fileUploadSettings.doneUploadProcess();
				
				if(!$(this).val() || $(this).val() === "") return false;
				
				if(!fileUploadSettings.validateLocalFiles($(this).val())) {
					fileUploadSettings.clearFileTag();
					return false;
				}

				fileUploadSettings.uploadFiles($(this).val());
			}
			return false;
		}
		
		/**
		 * 파일을 선택했을때 실행되는 event handler
		 */
		fileUploadSettings.uploadFileURL = function()
		{
//			var url = that.find("#fileurl").val();
//			var filename = that.find("#filename").val();
//			if(url || filename){
//				alert(fileUploadLanguage.error.no_url_and_filename);
//				return false;
//			}
//			if(fileUploadSettings.interruptStatus) return false;
//			//if(fileUploadSettings.status) {alert('try later!!');return false};
//			if(fileUploadSettings.status) {alert(fileUploadLanguage.error.during_uploading);return false};
//			var sizeExceed = false;
//			if(fileUploadSettings.files.length >= fileUploadSettings.fileLimit){
//				alert(fileUploadLanguage.error.too_many_file_count);
//				return false;
//			};
//			if(! sizeExceed && outerModule.getTotalFileSize() >= fileUploadSettings.fileLengthLimit) {
//				alert(fileUploadLanguage.error.too_much_total_file_length);
//				return false;
//			}
//			if(!fileUploadSettings.checkExtension($(target).val())) {
//				alert(fileUploadLanguage.error.extension_not_supported);
//				return false;
//			}
//			var filename = that.find("#filename").val().substring($(target).val().lastIndexOf('\\') + 1);
//			//같은 파일 업로드시 check
//			for (var i = 0; i < fileUploadSettings.files.length; i++){
//				if (fileUploadSettings.files[i].orgFileName == filename){
//					alert(fileUploadLanguage.error.is_same_file_name);
//					return false;
//				}
//			}
//			fileUploadSettings.files.push({
//				itemId : fileUploadSettings.fileIdPrefix+fileUploadSettings.fileUploadCount,
//				sysFileName		: null,
//				orgFileName		: filename,
//				fileSize		: 0,
//				progressStatus	: false
//			});
//			fileUploadSettings.submit.call($("#fileUrlUploadForm"));
		}
		
		
		
		/**
		 * json data를 출력하기 위한 toString function
		 */
		fileUploadSettings.testObject = function(obj)
		{
			var a = '';
			for(var i in obj)
			{
				a += 'obj['+i+'] : '+obj[i] +'\n';
			}
			return a;
		}
		
		/**
		 * 첨부파일이 삭제에 성공하면 실행되는 호출 되는 callback function
		 */
		fileUploadSettings.deleteNode = function(result){
			var targetId = '';
fileUploadSettings.debug("fileUploadSettings.deleteNode fileUploadSettings.deletetarget="+fileUploadSettings.deletetarget);
			if(fileUploadSettings.deletetarget){
				var spanId = $(fileUploadSettings.deletetarget).attr("id");
fileUploadSettings.debug("fileUploadSettings.deleteNode spanId="+spanId);
				// 전체 파일 용량이 초과됨을 감지한 후 이 함수 호출시 return
				if (!spanId){
					return;
				}
				targetId = spanId.substring(spanId.indexOf('img_'+fileUploadSettings.fileIdPrefix) + 4);
			}else{
fileUploadSettings.debug("fileUploadSettings.deleteNode result.response.itemId="+result.response.itemId);
				if(result.response.itemId){
					targetId = result.response.itemId;
				}else{
fileUploadSettings.debug("fileUploadSettings.deleteNode fileUploadSettings.fileIdPrefix+fileUploadSettings.fileUploadCount="+(fileUploadSettings.fileIdPrefix+fileUploadSettings.fileUploadCount));
					targetId = fileUploadSettings.fileIdPrefix+fileUploadSettings.fileUploadCount;
				}
			}			
fileUploadSettings.debug("fileUploadSettings.deleteNode targetId="+targetId);
fileUploadSettings.debug("fileUploadSettings.deleteNode targetId="+targetId+",result.response.success="+result.response.success);
			if(result.response.success === 'Y'){
				$("#img_"+targetId).parent().parent().remove();
				fileUploadSettings.deleteNodeInFiles(targetId);
			}else
			{
				alert(fileUploadLanguage.error.trans_failure);
			}
			fileUploadSettings.deletetarget = null;
			if(fileUploadSettings.onDelete) fileUploadSettings.onDelete();
		}
		
		/**
		 * fileId로 파일의 정보(fileUploadSettings.files)를 구하는 function
		 */
		fileUploadSettings.getFileInfo = function(fileId)
		{
fileUploadSettings.debug("getFileInfo itemId="+fileId+",fileUploadSettings.files.length="+fileUploadSettings.files.length);
			var ret = null;
			for(var i = 0 ; i < fileUploadSettings.files.length ; i++)
			{
//fileUploadSettings.debug("getFileInfo fileUploadSettings.files[i].itemId="+fileUploadSettings.files[i].itemId+",fileId="+fileId);
				if(fileUploadSettings.files[i].itemId == fileId)
				{
					ret = fileUploadSettings.files[i];
					break;
				}
			}
			return ret;
		}
		
		/**
		 * 파일 삭제 image가 클릭되면 실행되는 event handler
		 */
		fileUploadSettings.deleteFile = function(event)
		{
			var target = event.target;
			fileUploadSettings.deletetarget = target;
			var spanId = $(fileUploadSettings.deletetarget).attr("id");
			var targetId = spanId.substring(spanId.indexOf('img_'+fileUploadSettings.fileIdPrefix) + 4);
			var deletefilename = fileUploadSettings.getFileInfo(targetId).sysFileName;
fileUploadSettings.debug("fileUploadSettings.deleteFile spanId="+spanId+",targetId="+targetId+",deletefilename="+deletefilename);
			try
			{
				$.getJSON(fileUploadSettings.progressURL, 
						{"acton" : "delete", itemId : targetId,"delFileName" : deletefilename},
						fileUploadSettings.deleteNode
					);
			}
			catch(e)
			{
				alert('1' + e);
			}
		}
		
		/**
		 * 전체 파일을 삭제한 후 페이지의 파일 목록을 지우는 function
		 */
		fileUploadSettings.deleteAllFile = function(event)
		{
			$("#" + fileUploadSettings.filelistlayer + " > ul").html("");
			fileUploadSettings.files.splice(0, fileUploadSettings.file.length);
		}

		/**
		 * 파일 전송 진행상태 정보를 조회하는 function(under HTML5)
		 */
		fileUploadSettings.getProgressStatus = function(flag)
		{
			if(!fileUploadSettings.interruptStatus)
			$.getJSON(fileUploadSettings.progressURL, {"acton" : flag, "itemId" : fileUploadSettings.fileIdPrefix+fileUploadSettings.fileUploadCount}, fileUploadSettings.showProgressStatus);
		}
		
		/**
		 * 파일 전송이 모두 종료된후 서버로 파일 전송이 완전히 종료되었음을 알리는 function으로 이 function이 실행되지 않으면
		 * 이후 파일 전송의 진행상태 정보를 제대로 조회할 수 없다. 
		 * 즉 파일 전송이 후 본 function 은 무조건 절대로 실행되어야 한다.
		 */
		fileUploadSettings.makeFinish = fileUploadSettings.html5 ? function(fileInfo)
		{
			if(fileUploadSettings.debug){
				if(arguments[0]) fileUploadSettings.debug(arguments[0])
			}
			fileUploadSettings.clearFileTag();
			$.getJSON(fileUploadSettings.progressURL, {"acton" : "finish", "itemId" : fileInfo.itemId}, fileUploadSettings.finishUpload);
		} : function()
		{
			if(fileUploadSettings.debug){
				if(arguments[0]) fileUploadSettings.debug(arguments[0]);
			}
			fileUploadSettings.clearFileTag();
			$.getJSON(fileUploadSettings.progressURL, {"acton" : "finish", "itemId" : fileUploadSettings.fileIdPrefix+fileUploadSettings.fileUploadCount}, fileUploadSettings.finishUpload);
		}
		/**
		 * 파일 전송 중단 function
		 */
		fileUploadSettings.interrupt = fileUploadSettings.html5 ? function(event){
//alert("fileUploadSettings.interrupt called itemId="+itemId);
			if(fileUploadSettings.interruptStatus) {
				alert(fileUploadLanguage.message.interrupting);
				return;
			}
			var target = event.target;
			var imgId = target.id;
			var itemId = imgId.substring(imgId.indexOf('img_'+fileUploadSettings.fileIdPrefix) + 4);
//alert("fileUploadSettings.interrupt called imgId="+imgId+",itemId="+itemId);
			fileUploadSettings.interruptStatus = true;
			if(fileUploadSettings.xhrs && fileUploadSettings.xhrs[itemId]){
				$("#img_"+itemId).parent().parent().remove();
				fileUploadSettings.deleteNodeInFiles(itemId);
				fileUploadSettings.hideProgressLayer();
				fileUploadSettings.xhrs[itemId].abort();
				fileUploadSettings.interruptStatus = false;
			}
		} : function(event)
		{
//alert("fileUploadSettings.interrupt called itemId="+itemId);
			if(fileUploadSettings.interruptStatus) {
				alert(fileUploadLanguage.message.interrupting);
				return;
			}
			var target = event.target;
			var imgId = target.id;
			var itemId = imgId.substring(imgId.indexOf('img_'+fileUploadSettings.fileIdPrefix) + 4);
			//var itemId = fileUploadSettings.fileIdPrefix+fileUploadSettings.fileUploadCount;
//alert("fileUploadSettings.interrupt called imgId="+imgId+",itemId="+itemId);
			fileUploadSettings.interruptStatus = true;
			var callback = function(result)
			{
				fileUploadSettings.deleteNodeInFiles(itemId);
				$("#img_"+itemId).parent().parent().remove();
				fileUploadSettings.hideProgressLayer();
				fileUploadSettings.interruptStatus = false;
				
				alert(fileUploadLanguage.message.interrupted);
			}
			//$.getJSON(fileUploadSettings.progressURL, {"acton" : "interrupt", "itemId" : fileUploadSettings.fileIdPrefix+fileUploadSettings.fileUploadCount}, callback);
			$.getJSON(fileUploadSettings.progressURL, {"acton" : "interrupt", "itemId" : itemId}, callback);
		}

		/**
		 * fileUploadSettings.getProgressStatus 의 callback function으로 fileUploadSettings.getProgressStatus로부터 구한 파일 전송 상태를 화면에 표시한다.
		 */
		fileUploadSettings.showProgressStatus = function(result)
		{
			var loaded = result.response.bytesRead;
			var total = result.response.contentLength;
			
			fileUploadSettings.updateProgressStatus(loaded, total, result.response);
		}
		
		fileUploadSettings.updateProgressStatus = fileUploadSettings.html5 ? function(fileInfo, loaded, total, i, length)
		{
			//$("#progressfilesinfo").val(fileInfo.orgFileName + "("+(i+1)+"/"+length+")");
			/*var perc =  (loaded * 100) / total;
			var width = $("#showprogressimg_"+fileInfo.itemId).parent().width() - 20;
			$("#showprogressimg_"+fileInfo.itemId).height(15);
			try{
				$("#showprogressimg_"+fileInfo.itemId).width(perc * (width / 100));
			}catch(e){
			}*/
			var percentage = Math.round((loaded * 100) / total);
fileUploadSettings.debug("updateProgressStatus fileInfo.itemId="+fileInfo.itemId+",loaded="+loaded+",total="+total+",percentage="+percentage);
fileUploadSettings.debug("updateProgressStatus before #progress_"+fileInfo.itemId+").width()="+$("#progress_"+fileInfo.itemId).width());
			$("#progress_"+fileInfo.itemId).width(percentage);
fileUploadSettings.debug("updateProgressStatus after #progress_"+fileInfo.itemId+").width()="+$("#progress_"+fileInfo.itemId).width());
fileUploadSettings.debug("updateProgressStatus before #progress_"+fileInfo.itemId+").val()="+$("#progress_"+fileInfo.itemId).val());
			$("#progress_"+fileInfo.itemId).val(loaded+"/"+total);
fileUploadSettings.debug("updateProgressStatus after #progress_"+fileInfo.itemId+").val()="+$("#progress_"+fileInfo.itemId).val());
			var fileSize = total < 1024 ? total + " B" : ((Math.floor(total/1024)+"").numberFormat()) + " KB";
fileUploadSettings.debug("updateProgressStatus before filesize "+$("#filesize_"+fileInfo.itemId).text()+",fileSize="+fileSize);
			$("#filesize_"+fileInfo.itemId).text(fileSize);
fileUploadSettings.debug("updateProgressStatus after filesize "+$("#filesize_"+fileInfo.itemId).text());
			/*var filegage = $("#fileUpload #filegage");
			if(!filegage)
			{
				filegage = $("<div class=\"filegage\"/>").text("");
				$("#" + fileUploadSettings.filelistlayer).insertBefore("filegage");
			}*/
			//fileUploadSettings.test(file, loaded, total);
		} : function(loaded, total, response)
		{
			var itemId = fileUploadSettings.fileIdPrefix+fileUploadSettings.fileUploadCount;
			
			var percentage = Math.round((loaded * 100) / total);
			fileUploadSettings.debug("updateProgressStatus loaded="+loaded+",total="+total+",percentage="+percentage);
			fileUploadSettings.debug("updateProgressStatus before #progress_"+itemId+").width()="+$("#progress_"+itemId).width());
			$("#progress_"+itemId).width(percentage);
			fileUploadSettings.debug("updateProgressStatus after #progress_"+itemId+").width()="+$("#progress_"+itemId).width());
			fileUploadSettings.debug("updateProgressStatus before #progress_"+itemId+").val()="+$("#progress_"+itemId).val());
			$("#progress_"+itemId).val(loaded+"/"+total);
			fileUploadSettings.debug("updateProgressStatus after #progress_"+itemId+").val()="+$("#progress_"+itemId).val());
			
			//var fileSize = (Math.floor(total/1024)+"").numberFormat();
			var fileSize = total < 1024 ? total + " B" : ((Math.floor(total/1024)+"").numberFormat()) + " KB";
			$("#filesize_"+itemId).text(fileSize);

			try{
				if(!response.finishstatus)
				{
					window.setTimeout(function(){fileUploadSettings.getProgressStatus('uploadprogress')}, 1000);
				}
			}catch(e){}

			/*
			var bytesRead = result.response.bytesRead;
			var contentLength = result.response.contentLength;
			
			var perc =  (result.response.bytesRead * 100) / result.response.contentLength
			var width = $("#showprogressimg").parent().width() - 20;
			$("#showprogressimg").height(15);
			try{
				$("#showprogressimg").width(perc * (width / 100));
			}catch(e){
			}
			var filegage = $("#fileUpload #filegage");
			if(!filegage)
			{
				filegage = $("<div class=\"filegage\"/>").text("");
				$("#" + fileUploadSettings.filelistlayer).insertBefore("filegage");
			}
			fileUploadSettings.test(result);
			if(!result.response.finishstatus)
			{
				window.setTimeout("fileUploadSettings.getProgressStatus('uploadprogress')", 50);
				jQuery("#amountText").remove();
				jQuery("#fileUpload").append("<div id='amountText' style='height:20px'><div style='height:5px'></div>Transferring... " + (Math.floor(bytesRead/1024)+"").numberFormat() + " / " + (Math.floor(contentLength/1024)+"").numberFormat() + " KB</div>");
			}
			else
			{
				fileUploadSettings.makeFinish("fileUploadSettings.showProgressStatus");
				jQuery("#amountText").remove();
			}
			*/
			
		}
		
		fileUploadSettings.isExceed = function(contentLength){
			var ret = false;
			alert(fileUploadSettings.fileLengthLimit +"   "+ (parseInt(outerModule.getTotalFileSize()) + parseInt(contentLength)));
			if(fileUploadSettings.fileLengthLimit < (parseInt(outerModule.getTotalFileSize()) + parseInt(contentLength))) {
				ret = true;
			}
			return ret;
		}
		
		
		/**
		 * 파일전송이 시작되었을때 화면에 progress bar 영역을 표시하는 function(under HTML5)
		 */
		fileUploadSettings.startUpload = function()
		{
			var itemId = fileUploadSettings.fileIdPrefix + fileUploadSettings.fileUploadCount;
			var fileInfo = fileUploadSettings.getFileInfo(itemId);
			
			var li = $("<li id=\"li_"+itemId+"\"/>")
			$("#" + fileUploadSettings.filelist).append(li);
			
			li.append("<div>" +
					"<span class=\"filename\"><input type=\"checkbox\" id=\"chk_"+itemId+"\" value=\""+itemId+"\" class=\"chk_file\"><img src=\""+fileUploadSettings.installPath+"/jquery.fileUpload/images/pubcon.gif\"><a id=\"a_"+itemId+"\">"+fileInfo.orgFileName+"</a>" +
					"<span class=\"fileprogress\" id=\"fileprogress_"+itemId+"\"><span class=\"progressHolder\"><span class=\"progress\" id=\"progress_"+itemId+"\"></span></span></span>" +
					"</span>" +
					"<span class=\"filesize\" id=\"filesize_"+itemId+"\">0 KB</span>" +
					"<img class=\"btn_x\" id=\"img_"+itemId+"\" src=\""+fileUploadSettings.installPath+"/jquery.fileUpload/images/btn_del.gif\" width=\"16\" height=\"16\" alt=\"delete file\"/>" +
					"</div>")
			$("#img_"+itemId).bind("click", fileUploadSettings.interrupt);

			window.setTimeout(function(){fileUploadSettings.getProgressStatus('uploadprogress')}, 1000);
		}
		/**
		 * 진행상태 로그를 progress layer 영역에 표시한다.
		 */
		fileUploadSettings.test = fileUploadSettings.html5 ? function(file, loaded, total)
		{
			if(!$("#progress-test")) return false;
			fileUploadSettings.index = fileUploadSettings.index || 1;
			text = fileUploadSettings.index 
					+ "  loaded : [" + loaded +"]"
					+ "  total : [" + total +"]"
					+ "  file : [" + file.name +"]";

			$("#progress-test").html( text+ "</br>");
			fileUploadSettings.index++;
		} : function(result)
		{
			if(!$("#progress-test")) return false;
			fileUploadSettings.index = fileUploadSettings.index || 1;
			text = fileUploadSettings.index 
					+ "  result.response.finishstatus : [" + result.response.acton +"]"
					+ "  result.response.finishstatus : [" + result.response.finishstatus +"]"
					+ "  result.response.bytesRead : [" + result.response.bytesRead +"]"
					+ "  result.response.contentLength : [" + result.response.contentLength +"]"

			$("#progress-test").html( text+ "</br>");
			fileUploadSettings.index++;
		}
		fileUploadSettings.getFileInfoFromFileName = function(fileName){
			for(var i =0; i<fileUploadSettings.files.length; i++){
				if(fileUploadSettings.files[i].orgFileName == fileName){
					return fileUploadSettings.files[i]
				}
			}
			return false;
		}
		/**
		 * 파일 전송이 모두 완료 되면 전송완료된 파일의 정보를 화면에 표시한다. 오류가 발생했을때 오류 메시지를 표시한다.
		 */
		fileUploadSettings.showResponse = fileUploadSettings.html5 ? function(result, status) {
//alert("fileUploadSettings.showResponse result.response.success="+result.response.success);
			if(result.response.success === 'true'){
				//file size가 0인 경우 처리 
				if(!result.response.files[0]){
					alert(fileUploadLanguage.error.zero_size_not_allowed);
					$("#img_"+result.response.itemId).parent().parent().remove();
					fileUploadSettings.deleteNodeInFiles(result.response.itemId);	//file size가 0인 경우 삭제
					fileUploadSettings.status = false;
					fileUploadSettings.onFinish(result, status);
					return false;
				}
				// In case of HTML5, the total size of files is included with uploading files and then exclude the current files  
				if(parseInt(outerModule.getTotalFileSize()) > fileUploadSettings.fileLengthLimit) {
					fileUploadSettings.showFileTotalMaxSizeMessage();
					try
					{
						var fileInfo = fileUploadSettings.getFileInfoFromFileName(result.response.files[0].orgFileName);
						$.getJSON(fileUploadSettings.progressURL, 
								{"acton" : "delete", itemId : fileInfo.itemId,"delFileName" : result.response.files[0].sysFileName},
								fileUploadSettings.deleteNode
							);
					}
					catch(e)
					{
						alert('1' + e);
					}
					fileUploadSettings.deleteNodeInFiles(result.response.itemId);
					fileUploadSettings.status = false;
					fileUploadSettings.onFinish(result, status);
					return false;
				}
				var file = result.response.files[0];
				$("#a_"+file.itemID).attr("href", fileUploadSettings.downloadURL+"&filename="+file.sysFileName);
				$("#img_"+file.itemID).unbind("click");
				$("#img_"+file.itemID).bind("click", fileUploadSettings.deleteFile);
				$("#fileprogress_"+file.itemID).hide();
//alert("fileUploadSettings.showResponse file.itemID="+file.itemID);
				fileUploadSettings.onFinish(result, status);
				
			}else{
				var deletedFiles = fileUploadSettings.deleteNodeInFiles(result.response.itemId);
				
				for(var i=0; i<deletedFiles.length; i++){
					$("#img_"+deletedFiles[i].itemId).parent().parent().remove();
				}
				
				if(result.response.error == 'SIZE')
				{
					fileUploadSettings.showFileMaxSizeMessage(result.response.maxUploadSize);
//					alert(result.response.maxUploadSize/1024 + " KB");
				}
				else if(result.response.error == 'INTERRUPT')
				{
					//alert(fileUploadLanguage.message.interrupted);
				}
				else if(result.response.error == 'EXT')
				{
					alert(fileUploadLanguage.error.extension_not_supported);
					//alert(fileUploadLanguage.message.interrupted);
				}
				else
				{
					alert(fileUploadLanguage.error.trans_failure);
				}
				fileUploadSettings.onFinish(result, status);
			}
//			alert(fileUploadSettings.files.length +",  "+ outerModule.getTotalFileSize());
			fileUploadSettings.status = false;
			return false;
		} : function(result, status) {
			var itemId = fileUploadSettings.fileIdPrefix+fileUploadSettings.fileUploadCount;
			if(result.response.success === 'true'){
				//file size가 0인 경우 처리 
				if(!result.response.files[0]){
					alert(fileUploadLanguage.error.zero_size_not_allowed);
					$("#img_"+itemId).parent().parent().remove();
					fileUploadSettings.deleteNodeInFiles(itemId);	//file size가 0인 경우 삭제
					fileUploadSettings.status = false;
					fileUploadSettings.onFinish(result, status);
					return false;
				}
				if(parseInt(outerModule.getTotalFileSize()) + parseInt(result.response.files[0].attachFileSize) > fileUploadSettings.fileLengthLimit) {
					fileUploadSettings.showFileTotalMaxSizeMessage();
					try
					{
						$.getJSON(fileUploadSettings.progressURL, 
								{"acton" : "delete", itemId : itemId,"delFileName" : result.response.files[0].sysFileName},
								fileUploadSettings.deleteNode
							);
					}
					catch(e)
					{
						alert('1' + e);
					}
					fileUploadSettings.deleteNodeInFiles(itemId);
					fileUploadSettings.status = false;
					fileUploadSettings.onFinish(result, status);					
					return false;
				}
				
				fileUploadSettings.updateProgressStatus(result.response.files[0].attachFileSize, result.response.files[0].attachFileSize, result.response);
				$("#a_"+itemId).attr("href", fileUploadSettings.downloadURL+"&filename="+result.response.files[0].sysFileName);
				$("#img_"+itemId).unbind("click");
				$("#img_"+itemId).bind("click", fileUploadSettings.deleteFile);
				$("#fileprogress_"+itemId).hide();
	
				fileUploadSettings.onFinish(result, status);
				fileUploadSettings.fileUploadCount++;
				
			}else{
				fileUploadSettings.deleteNodeInFiles(itemId);
				$("#img_"+itemId).parent().parent().remove();
				
				if(result.response.error == 'SIZE')
				{
					fileUploadSettings.showFileMaxSizeMessage(result.response.maxUploadSize);
//					alert(result.response.maxUploadSize/1024 + " KB");
				}
				else if(result.response.error == 'INTERRUPT')
				{
					//alert(fileUploadLanguage.message.interrupted);
				}
				else if(result.response.error == 'EXT')
				{
					alert(fileUploadLanguage.error.extension_not_supported);
					//alert(fileUploadLanguage.message.interrupted);
				}
				else
				{
					alert(fileUploadLanguage.error.trans_failure);
				}
				try{
					fileUploadSettings.onFinish(result, status);
					$("#img_"+itemId).parent().parent().remove();
				}catch(e){}
				fileUploadSettings.fileUploadCount++;
			}
//			alert(fileUploadSettings.files.length +",  "+ outerModule.getTotalFileSize());
			fileUploadSettings.status = false;
			//fileUploadSettings.onFinish(result, status);			
			return false;
		}
		
		fileUploadSettings.countUTF8 = function (str){
			if (typeof str == 'undefined')
				return 0;
			var hanLength = (escape(str)+"%u").match(/%u/g).length-1;
			return str.length + (hanLength*2);
		}
		
		
		/**
		 * 모두 완료되었을때 모든 진행상태 값을 초기화 한다.
		 */
		fileUploadSettings.destroyed = function()
		{
			fileUploadSettings.status = false;
			fileUploadSettings.interruptStatus = false;
			fileUploadSettings.currentLength = null;
			//$("#temporaryFile").val("");
		}
		fileUploadSettings.doneUploadProcess = function(){
			for(var i = 0 ; i < fileUploadSettings.files.length ; i++)
			{
				if(!fileUploadSettings.files[i].progressStatus){
					return false;
				}
			}
			return true;
		}
		/**
		 * 파일전송이 정상적으로 종료되었을때 fileUploadSettings.files에 파일 정보를 추가한다. 
		 */
		fileUploadSettings.onFinish = fileUploadSettings.onFinish || fileUploadSettings.html5 ? function(result, status)
		{
			var toString = function(){
				return this.orgFileName + ", " + this.sysFileName + ", " + this.fileSize
			}
			for(var i = 0 ; i < fileUploadSettings.files.length ; i++)
			{
				if(result.response.files[0].itemID === fileUploadSettings.files[i].itemId)
				{
					fileUploadSettings.files[i].sysFileName = result.response.files[0].sysFileName;
					fileUploadSettings.files[i].orgFileName = result.response.files[0].orgFileName;
					fileUploadSettings.files[i].progressStatus = true;
					fileUploadSettings.files[i].fileSize = result.response.files[0].attachFileSize;
					fileUploadSettings.files[i].fileID = result.response.files[0].fileID;
					fileUploadSettings.files[i].toString = toString;
				}
			}
			fileUploadSettings.clearFileTag();
			if(fileUploadSettings.onComplete && fileUploadSettings.doneUploadProcess()) fileUploadSettings.onComplete();
		} : function(result, status)
		{
			var toString = function(){
				return this.orgFileName + ", " + this.sysFileName + ", " + this.fileSize
			}
			for(var i = 0 ; i < fileUploadSettings.files.length ; i++)
			{
				if(fileUploadSettings.fileIdPrefix + fileUploadSettings.fileUploadCount === fileUploadSettings.files[i].itemId)
				{
					fileUploadSettings.files[i].sysFileName = result.response.files[0].sysFileName;
					fileUploadSettings.files[i].orgFileName = result.response.files[0].orgFileName;
					fileUploadSettings.files[i].progressStatus = true;
					fileUploadSettings.files[i].fileSize = result.response.files[0].attachFileSize;
					fileUploadSettings.files[i].fileID = result.response.files[0].fileID;
					fileUploadSettings.files[i].toString = toString;
					break;
				}
			}
			fileUploadSettings.clearFileTag();
			if(fileUploadSettings.onComplete && fileUploadSettings.doneUploadProcess()) fileUploadSettings.onComplete();
		}
		
		/**
		 * 파일 전송이 완료되거나 중단되었을때 화면의 progress bar를 사라지게 하는 function
		 */
		fileUploadSettings.finishUpload = function()
		{
			jQuery("#fileuploading").fadeOut(700);
			try{
				jQuery("#showprogressimg").width(0);
			}catch(e){alert('3' + e);}
			jQuery("#hideDiv").hide();
			jQuery("#fileuploading").remove();
			fileUploadSettings.destroyed();
			return false;
		}

		/**
		 * 파일 전송 오류가 발생했을 때 실행되는 callback function 
		 */
		fileUploadSettings.errorResponse = function(result)
		{
			fileUploadSettings.makeFinish("fileUploadSettings.errorResponse");
			alert(fileUploadLanguage.error.trans_failure);
			var b = '';

			//fileUploadSettings.deleteNodeInFiles(fileUploadSettings.fileIdPrefix + fileUploadSettings.fileUploadCount);
			fileUploadSettings.files.splice(fileUploadSettings.files.length - 1, 1);
			fileUploadSettings.hideProgressLayer();
			//fileUploadSettings.makeFinish();
			return false;
		}
		
		/**
		 * 파일 전송 완료, 오류 후 progress bar 영역을 삭제하는 function
		 * TODO 중복되는 function이 있으므로 삭제 여부 고려해야 함.
		 */
		fileUploadSettings.hideProgressLayer = function()
		{
			jQuery("#fileuploading").fadeOut(700);
			jQuery("#hideDiv").hide();
			try{
				jQuery("#showprogressimg").width(0);
			}catch(e){alert('4' + e);}
			fileUploadSettings.destroyed();
		}
		
		/**
		 * 파일 삭제 이벤트 후 삭제가 정상적으로 처리 되었을때 fileUploadSettings.files에서 해당 file의 정보를 삭제한다.
		 */
		fileUploadSettings.deleteNodeInFiles = function(targetId, callback)
		{
			var deletedFiles = new Array();
			if(!targetId) {
				deletedFiles = deletedFiles.concat(fileUploadSettings.files.splice(fileUploadSettings.files.length - 1, 1));
			}else {
				for(var i = 0 ; i < fileUploadSettings.files.length ; i++)
				{
					if(fileUploadSettings.files[i].itemId == targetId)
					{
						deletedFiles = deletedFiles.concat(fileUploadSettings.files.splice(i, 1));
						break;
					}
				}
			}
			return deletedFiles;
		}
		
		fileUploadSettings.deleteFileByName = function(filename){
fileUploadSettings.debug("deleteFileByName filename:"+filename+",fileUploadSettings.files.length="+fileUploadSettings.files.length);
			for (var i = 0; i < fileUploadSettings.files.length; i++){
fileUploadSettings.debug("deleteFileByName fileUploadSettings.files["+i+"].orgFileName:"+fileUploadSettings.files[i].orgFileName);
				if (fileUploadSettings.files[i].orgFileName == filename){
fileUploadSettings.debug("deleteFileByName fileUploadSettings.files["+i+"].itemId:"+fileUploadSettings.files[i].itemId);
					$("#img_"+fileUploadSettings.files[i].itemId).parent().parent().remove();
					fileUploadSettings.deleteNodeInFiles(fileUploadSettings.files[i].itemId);
				}
			}
		}
		
		fileUploadSettings.clear = function(){
			if(fileUploadSettings.files) fileUploadSettings.files.clear();
			$("#"+fileUploadSettings.fileUploadForm+" #temporaryFile").remove();
			$("#"+fileUploadSettings.fileUploadForm+"").prepend("<input type=\"file\" id=\"temporaryFile\" name=\""+fileUploadSettings.inputTagName+"\" class=\"filetag\" value=\"file\" size=\"1\" "+(fileUploadSettings.html5? "multiple" : "")+"/>")
				.find("input:file")
				.unbind("change")
				.bind("change", fileUploadSettings.onchangeSingle);
			$("#" + fileUploadSettings.filelistlayer + " #" + fileUploadSettings.filelist + " li").remove();
		}
		
		fileUploadSettings.dragEnter = fileUploadSettings.html5 ? function(e){
			e.stopPropagation();
			e.preventDefault();
		} : false;
		
		fileUploadSettings.dragExit = fileUploadSettings.html5 ? function(e){
			e.stopPropagation();
			e.preventDefault();
		} : false;
		
		fileUploadSettings.dragHover = fileUploadSettings.html5 ? function(e){
			e.stopPropagation();
			e.preventDefault();
		} : false;
		
		fileUploadSettings.drop = fileUploadSettings.html5 ? function(e){
			e.stopPropagation();
			e.preventDefault();
			
			var files = e.dataTransfer.files;
			var count = files.length;

			// Only call the handler if 1 or more files was dropped.
			if (count > 0){
				if(!fileUploadSettings.validateLocalFiles(files)) {
					fileUploadSettings.clearFileTag();
					return false;
				}
				fileUploadSettings.uploadFiles(files);
			}
		} : false;
		
		fileUploadSettings.clearFileTag = function(){
			fileUploadSettings.debug("fileUploadSettings.clearFileTag val="+$("#"+fileUploadSettings.fileUploadForm+" #temporaryFile").val());
			if($("#"+fileUploadSettings.fileUploadForm+" #temporaryFile").val() != ''){
				fileUploadSettings.debug("fileUploadSettings.clearFileTag call remove");
				$("#"+fileUploadSettings.fileUploadForm+" #temporaryFile").remove();
				$("#"+fileUploadSettings.fileUploadForm).prepend("<input type=\"file\" id=\"temporaryFile\" name=\""+fileUploadSettings.inputTagName+"\" class=\"filetag\" value=\"file\" size=\"1\" "+(fileUploadSettings.html5? "multiple" : "")+"/>")
					.find("input:file")
					.unbind("change")
					.bind("change", fileUploadSettings.onchangeSingle);
			}
		}

		String.prototype.numberFormat=function() { 
			return this.replace(/(\d)(?=(?:\d{3})+(?!\d))/g,'$1,'); 
		}
		
		fileUploadSettings.moveToUp = function(){
			fileUploadSettings.moveFile(1);
		}
		
		fileUploadSettings.moveToDown = function(){
			fileUploadSettings.moveFile(-1);
		}
		
		fileUploadSettings.moveFile = function(offset){
			var checkedFile = offset > 0 ? $($(".chk_file:checked").get().reverse()) : $(".chk_file:checked"); 
			checkedFile.each(function(index){
				var itemID = $(this).val()//itemid
fileUploadSettings.debug("moveFile itemID["+itemID+"]");				
				for(var i = 0 ; i < fileUploadSettings.files.length ; i++)
				{
fileUploadSettings.debug("moveFile itemID["+itemID+"], fileUploadSettings.files[i].itemId["+fileUploadSettings.files[i].itemId+"]");
					if(fileUploadSettings.files[i].itemId ==itemID){
						fileUploadSettings.swapFile(i, offset);
						break;
					}
				}
			});
		}
		fileUploadSettings.swapFile = function(srcIdx, offset){
			var tgtIdx = srcIdx + offset;
fileUploadSettings.debug("swapFile srcIdx["+srcIdx+"], tgtIdx["+tgtIdx+"], fileUploadSettings.files.length["+fileUploadSettings.files.length+"]");			
			if(tgtIdx < 0 || tgtIdx >= fileUploadSettings.files.length){
				return;
			}
			var srcFileInfo = fileUploadSettings.files[srcIdx];
			var tgtFileInfo = fileUploadSettings.files[tgtIdx];
fileUploadSettings.debug("swapFile srcFileInfo["+srcFileInfo+"]");			
			fileUploadSettings.files[srcIdx] = tgtFileInfo;
			fileUploadSettings.files[tgtIdx] = srcFileInfo;
fileUploadSettings.debug("swapFile srcFileInfo.itemId["+srcFileInfo.itemId+"], tgtFileInfo.itemId["+tgtFileInfo.itemId+"]");			
			// TODO swap the li_<itemid> li_frmwk_attach_0
			//$("#li_"+fileUploadSettings.files[tgtIdx].itemId).insertAfter($("#li_"+fileUploadSettings.files[srcIdx].itemId));
			//
			if(offset > 0){
				$("#li_"+srcFileInfo.itemId).next().after($("#li_"+srcFileInfo.itemId));
			}else{
				$("#li_"+srcFileInfo.itemId).prev().before($("#li_"+srcFileInfo.itemId));
			}
		}

		
		this.append("<div class=\"add_filebg\">" +
			"<div id=\"wfw_file_box\" class=\"wfw_file_box\">" +
			"<form name='"+fileUploadSettings.fileUploadForm+"' id='"+fileUploadSettings.fileUploadForm+"' action='"+fileUploadSettings.uploadURL+"' METHOD='POST' enctype='multipart/form-data'>" +
			"<input type=\"file\" id=\"temporaryFile\" name=\""+fileUploadSettings.inputTagName+"\" class=\"filetag\" value=\"file\" size=\"1\" "+(fileUploadSettings.html5? "multiple" : "")+"/>" +
			"</form>"+
			"<ul>"+
            "<li><a href=\"#\" class=\"attach_add\">"+fileUploadLanguage.label.addfile+"</a></li>"+
            "</ul>" +
            "</div>" +
			"</div>")
			.find("#temporaryFile")
			.unbind("change")
			.bind("change", fileUploadSettings.onchangeSingle);
		
		this.append("<div><ul >"+
                "<li class=\"theader_h1\">"+(fileUploadSettings.suppportMove ? ("<span class=\"theader_btn\"><a class=\"attach_up_btn\"/><a class=\"attach_down_btn\"/></span>") : "")+fileUploadLanguage.label.filename+"</li>"+
                "<li class=\"theader_h2\">"+fileUploadLanguage.label.filesize+"</li>"+
                "</ul></div>");
		if(fileUploadSettings.suppportMove){
			this.find(".attach_up_btn").unbind("click").bind("click", fileUploadSettings.moveToUp);
			this.find(".attach_down_btn").unbind("click").bind("click", fileUploadSettings.moveToDown);
		}
		
		this.find("#wfw_file_box")
			.append("<form name='fileUrlUploadForm' id='fileUrlUploadForm' action='"+fileUploadSettings.uploadURL+"' METHOD='POST'/>")
			.find("#fileUrlUploadForm")
			.append("<input type=\"hidden\" id=\"fileurl\" name=\"fileurl\" value=\"\"/>")
			.find("#fileUrlUploadForm")
			.append("<input type=\"hidden\" id=\"filename\" name=\"filename\" value=\"\"/>");

		this.append("<div id=\"" + fileUploadSettings.filelistlayer + "\" class=\""+(fileUploadSettings.usePopup ? "filelistlayer_popup" : "filelistlayer")+"\"/>")
			.find("#" + fileUploadSettings.filelistlayer)
			.append("<ul id=\"" + fileUploadSettings.filelist + "\" class=\""+(fileUploadSettings.usePopup ? "filelist_popup" : "filelist")+"\"/>");
		
		if(fileUploadSettings.html5){
			// can't bind the event handler to the drop event in jquery 1.3 
			var filedrag = document.getElementById(fileUploadSettings.dropboxId);;
			
			if(filedrag){
				if(filedrag.attachEvent){
					filedrag.attachEvent("dragover", fileUploadSettings.dragHover);
					filedrag.attachEvent("dragleave", fileUploadSettings.dragHover);
					filedrag.attachEvent("drop", fileUploadSettings.drop);
				}else{
					filedrag.addEventListener("dragover", fileUploadSettings.dragHover, false);
					filedrag.addEventListener("dragleave", fileUploadSettings.dragHover, false);
					filedrag.addEventListener("drop", fileUploadSettings.drop, false);
				}
				filedrag.style.display = "block";
			}
		}
		
		/**
		 * preventing the drag & drop on the body(window)
		 */
		try{
			$(document).bind('drop dragover', function (e) {
				 e.preventDefault(true);
			});
		}catch(e){}
		 
		 /**
		 * 본 plugin이 실행되면 리턴되는 객체로 내부의 fileUploadSettings closure 객체 내부의 정보에 접근하기 위한 기능 객체
		 * 파일명 목록, 서버 저장 파일명 목록, 전체 파일 크기 등의 정보를 리턴하는 method를 갖고 있다
		 */
		var outerModule = {
			setFileUploadSetting : function(setting, value)
			{
				eval("fileUploadSettings." + setting + "=" + value);
			},
			getFileUploadSetting : function(setting)
			{
				var value = eval("fileUploadSettings." + setting);
				return value;
			},
			removeFile : function(event)
			{
				fileUploadSettings.deleteFile(event);
			},
			putFile : function(sysFileName, fileName, size, itemID)
			{
				fileUploadSettings.files.push({
					itemId : itemID,
					sysFileName : sysFileName,
					orgFileName : fileName,
					fileSize : size,
					progressStatus : true
				});
			},
			getOrgFileNames : function(gubun)
			{
				var result = new Array();
				for(var i = 0 ; i < fileUploadSettings.files.length ; i++)
				{
					result.push(fileUploadSettings.files[i].orgFileName);
				}
				return (gubun && gubun != '') ? result.join(gubun) : result;
			},
			getSysFileNames : function(gubun)
			{
				var result = new Array();
				for(var i = 0 ; i < fileUploadSettings.files.length ; i++)
				{
					result.push(fileUploadSettings.files[i].sysFileName);
				}
				return (gubun && gubun != '') ? result.join(gubun) : result;
			},
			getTotalFileSize : function()
			{
				var result = 0;
				for(var i = 0 ; i < fileUploadSettings.files.length ; i++)
				{
					
					result += parseInt(fileUploadSettings.files[i].fileSize);
				}
				return result;
			},
			getFileIDs : function(gubun)
			{
				var result = new Array();
				for(var i = 0 ; i < fileUploadSettings.files.length ; i++)
				{
					result.push(fileUploadSettings.files[i].fileID);
				}
				return (gubun && gubun != '') ? result.join(gubun) : result;
			},
			getFiles:function() {
				var result = 0;
				var str = '';
				var filesObj = []; 
				var arrFiles = new Array();
				for(var i = 0 ; i < fileUploadSettings.files.length ; i++)
				{
					var fileObj = [];
					var thing = {fileName: fileUploadSettings.files[i].orgFileName, TRID: fileUploadSettings.files[i].sysFileName, size:parseInt(fileUploadSettings.files[i].fileSize) };

					fileObj['fileName'] = fileUploadSettings.files[i].orgFileName;
					fileObj['sysFileName'] = fileUploadSettings.files[i].sysFileName;
					fileObj['size'] = fileUploadSettings.files[i].fileSize;
					fileObj['TRID'] = fileUploadSettings.files[i].fileID;
					fileObj['itemID'] = fileUploadSettings.files[i].itemId;

					arrFiles.push(fileObj);
				}
				/*{"files":
					[
					 {"fileName":"down.jpg","TRID":"2f32303132303133312f31392f33392f31303033303162666165386364313862356665663432636562353962663831333734363138","size":1016},
					 {"fileName":"blank.xml","TRID":"2f32303132303133312f31392f33392f31303033346164646463313364333064393033323134653033316164653165363734636464","size":40}
					 ],
				 "ok":true
				}*/
				filesObj['files'] = arrFiles;
				filesObj['ok'] = true;
				
				return filesObj;
			},
			clear:function(){
				fileUploadSettings.clear();
			},
			increaseFileLimit:function(inc){
				if((fileUploadSettings.fileLimit + inc) < 1){
					alert(fileUploadLanguage.error.error_limit_count);
					return;
				}
				fileUploadSettings.fileLimit = fileUploadSettings.fileLimit + inc; 
			}
		}
		
		this.trigger("instanceReady");
		
		return outerModule;
	}
})(jQuery)




/*

		fileUploadSettings.createUploadForm = function()
		{
			var form = jQuery('#"+fileUploadSettings.fileUploadForm+":form');

			if(form.size() == 0){
				form = that.append("<form name='"+fileUploadSettings.fileUploadForm+"' id='"+fileUploadSettings.fileUploadForm+"' action='' METHOD='POST' enctype='multipart/form-data'/>").children(':form');
			}
			return form;
		}

*/

