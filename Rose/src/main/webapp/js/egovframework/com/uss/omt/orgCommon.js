/**
 * 객체 표현식 {key: value, ...} 문자열을 객체화한다.
 * 
 * @param objStr
 */
function toInstance(objStr) {
	return eval(["_o", objStr].join("="));
}

/**
 * # 주의사항
 * 
 * 1. 특정 페이지에 종속되는 함수를 두지 않는다.
 * 
 */
function _warning_() {}
