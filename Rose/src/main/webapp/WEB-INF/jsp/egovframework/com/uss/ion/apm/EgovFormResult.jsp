<%@ page language="java" pageEncoding="utf-8" contentType="application/json; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
{
	<c:if test="${not empty result}">"result": "<c:out value="${result}"/>",</c:if>
	<c:if test="${not empty error_msg}">"error_msg": "<c:out value="${error_msg}"/>",</c:if>
	<c:if test="${not empty error_code}">"error_code": "<c:out value="${error_code}"/>",</c:if>
	<c:if test="${not empty form}">"form":{
	"formID" : "<c:out value="${form.formId}"/>",
	"deptID" : "<c:out value="${form.orgId}"/>",
	"deptNm" : "<c:out value="${form.orgNm}"/>",
	"formName" : "<c:out value="${form.formName}"/>",
	"formVersion" : "<c:out value="${form.formVersion}"/>",
	"formRemark" : "<c:out value="${form.formRemark}"/>",
	"formUseF" : "<c:if test="${form.formUseF}">2</c:if><c:if test="${not form.formUseF}">1</c:if>"
	},</c:if>
	"test":"test"
}