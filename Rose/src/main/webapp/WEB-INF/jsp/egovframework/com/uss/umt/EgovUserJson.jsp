<%@ page language="java" pageEncoding="utf-8" contentType="application/json; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
{"ID": "<c:out value="${user.uniqId}"/>", "name": "<c:out value="${user.emplyrNm}"/>", "deptName": "<c:out value="${user.orgnztNm}"/>", "deptID": "<c:out value="${user.orgnztId}"/>", "positionName" : "<c:out value="${user.positionNm}"/>", "dutyName": "<c:out value="${user.dutyNm}"/>", "type": "user"}