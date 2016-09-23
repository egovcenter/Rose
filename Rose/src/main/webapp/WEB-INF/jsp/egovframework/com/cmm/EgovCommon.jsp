<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set var="CONTEXT" value="${pageContext.request.contextPath}" />
<c:choose>
    <c:when test="${not empty param.FRAMEWORK_DIRECTORY_LOCALE}">
        <c:set var="LOCALE" value="${param.FRAMEWORK_DIRECTORY_LOCALE}" />
    </c:when>
    <c:when test="${not empty sessionScope.FRAMEWORK_DIRECTORY_LOCALE}">
        <c:set var="LOCALE" value="${sessionScope.FRAMEWORK_DIRECTORY_LOCALE}" />
    </c:when>
    <c:when test="${not empty cookie.GWLANG.value}">
        <c:set var="LOCALE" value="${cookie.GWLANG.value}" />
    </c:when>
    <c:when test="${not empty pageContext.request.locale}">
        <c:set var="LOCALE" value="${pageContext.request.locale}" />
    </c:when>
    <c:otherwise>
        <c:set var="LOCALE" value="en_US" />
    </c:otherwise>
</c:choose>
<fmt:setLocale value="${LOCALE}" />
<fmt:setBundle basename="com.tbs.framework.edirectory.resources.messages_directory" var="messages_directory" />

<c:set var="IS_ENGLISH" value="false" />
<c:if test="${LOCALE == 'en' || LOCALE == 'en_US'}">
    <c:set var="IS_ENGLISH" value="true" />
</c:if>
