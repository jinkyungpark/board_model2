<%@page import="java.net.URLEncoder"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.*"%>

<%
	String fileName = request.getParameter("fileName");

	String savePath = "c:/upload";
	//ServletContext context = getServletContext();
	//String sDownloadPath = application.getRealPath(savePath);
	String sFilePath = savePath + "/" + fileName;
	byte b[] = new byte[4096];
	FileInputStream in = new FileInputStream(sFilePath);
	String sMimeType = getServletContext().getMimeType(sFilePath);
	System.out.println("sMimeType>>>" + sMimeType);

	if (sMimeType == null)
		sMimeType = "application/octet-stream";

	response.setContentType(sMimeType);
	String agent = request.getHeader("User-Agent");
	
	boolean ieBrowser = (agent.indexOf("MSIE") > -1) || (agent.indexOf("Trident") > -1);

	if (ieBrowser) {
		fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
	} else {
		fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
	}

	//fileName이 현재 uuid 값이 붙어 있기 때문에 너무 길고 사용자가 본인이 올린 파일인지 헤깔릴 수 있기 때문에
	//fileName 변경해주기
	int start=fileName.lastIndexOf("_");	
	String oriName=fileName.substring(start+1);	
		
	response.setHeader("Content-Disposition", "attachment; filename= " + oriName);

	//IllegalStateException
	out.clear();
	out=pageContext.pushBody();	
	
	
	ServletOutputStream out2 = response.getOutputStream();
	int numRead;

	while ((numRead = in.read(b, 0, b.length)) != -1) {
		out2.write(b, 0, numRead);
	}
	out2.flush();
	out2.close();
	in.close();
%>
