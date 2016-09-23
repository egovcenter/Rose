<%@ page language="java" pageEncoding="utf-8" contentType="application/json; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
{"ID": "<c:out value="${dept.deptId}"/>", "name": "<c:out value="${dept.deptNm}"/>", "type": "dept"}