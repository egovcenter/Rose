<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="egovframework.com.uss.ion.apu.PathUtil"%>
<%@page import="egovframework.com.uss.omt.OrgConstant"%>
<%@ page import="java.io.File" %>
<%@ page import="java.io.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>file-download</title>
</head>
<body>
<%
	String fileName = request.getParameter("savedFileName");
	String subDir = request.getParameter("subDir");
	
	System.out.println(String.format("fileName=%s", fileName));
	System.out.println(String.format("fileType=%s", subDir));	
	
	ServletContext context = getServletContext();
	PathUtil pathUtil = new PathUtil();
	String realFolder = pathUtil.getCkeditorImagePath();
	realFolder = realFolder+subDir;
	System.out.println(String.format("realFolder=%s", realFolder));

	try {
		out.clear();
		out = pageContext.pushBody();
		
		File file = null;
		if (StringUtils.isNotBlank(fileName)) {
			file = new File(realFolder, fileName);
			System.out.println(String.format("path=%s", file.getCanonicalPath()));
		} 
		
		System.out.println(String.format("path=%s", file.getCanonicalPath()));
		byte b[] = new byte[4096];
		response.reset();
		response.setContentType("application/octet-stream");
		String Encoding = new String(fileName.getBytes("UTF-8"), "8859_1");
		response.setHeader("Content-Disposition","attatchment; filename = " + Encoding);
		FileInputStream is = new FileInputStream(file.getCanonicalPath());
		ServletOutputStream sos = response.getOutputStream();
		int numRead;
		while ((numRead = is.read(b, 0, b.length)) != -1) {
			sos.write(b, 0, numRead);
		}
		sos.flush();
		sos.close();
		is.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
</body>
</html>