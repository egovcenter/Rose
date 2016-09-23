/*
browser�� language �Ӽ��̳� ������ language�� �� �����ϴ� utility �Լ� 
 
option = {
	language : "ko", -- ������ ���
	path : "/javascript/jquery.fileUpload/localization", -- ��������� ��ġ�� ���
	package : "fileUploadLang --��������� prefix
}
 */

(function($){
	$.localize = function(option){
		var settings = $.extend({
				language : $.locale.defaultLanguage
			}, option || {});
		var locale = function(path, package, lang){
			var ret = '';
			if(! lang)
				ret =  path + "/" + package + ".js";
			else if(lang.length >= 2)
				ret = path + "/" + package + "-" +lang.substring(0,2) + ".js";
			else if(lang.length >=5)
				ret = path + "/" + package + "-" +lang.substring(0,5) + ".js";
			
			return ret;
		}	

		if($("script[src*=" + settings.package + "]").size() > 0){
			$("script[src*=" + settings.package + "]").remove();
		}
		$("head:first").append("<script type=\"text/javascript\" src=\""+locale(settings.path, settings.package, settings.language)+"\"></script>");
		
	}
	$.locale = {};
	$.locale.defaultLanguage = (getCookie("GWLANG") == "") ? normalizeLang(navigator.language /* Mozilla */ || navigator.userLanguage/* IE */) : getCookie("GWLANG") ;
	
	function normalizeLang(lang) {
		lang = lang.replace(/_/, '-').toLowerCase();
		if (lang.length > 3) {
			lang = lang.substring(0, 3) + lang.substring(3).toUpperCase();
		}
		return lang;
	}
	
	function getCookie(cName) {
        cName = cName + '=';
        var cookieData = document.cookie;
        var start = cookieData.indexOf(cName);//-1
        var cValue = '';
        if(start != -1){
             start += cName.length;
             var end = cookieData.indexOf(';', start);
             if(end == -1)end = cookieData.length;
             cValue = cookieData.substring(start, end);
        }
        return unescape(cValue);
   } 
})(jQuery)