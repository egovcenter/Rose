<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
{
	<c:if test="${not empty result}">"result": "<c:out value="${result}"/>",</c:if>
	<c:if test="${not empty error_msg}">"error_msg": "<c:out value="${error_msg}"/>",</c:if>
	<c:if test="${not empty label}">"label":{
	"labelID" : "<c:out value="${label.labelId}"/>",
	"deptID" : "<c:out value="${label.deptId}"/>",
	"labelParentID" : "<c:out value="${label.labelParentId}"/>",
	"labelLevel" : "<c:out value="${label.labelLevel}"/>",
	"labelSeq" : <c:out value="${label.labelSeq}"/>,
	"labelNm" : "<c:out value="${label.labelNm}"/>"
	},</c:if>
	"test":"test"
}