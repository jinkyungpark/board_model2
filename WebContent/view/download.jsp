<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedOutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
  		//클라이언트 브라우저 확인하기
  		String agent=request.getHeader("User-Agent");
  		//Trident : explorer 11
  		boolean ieBrowser=(agent.indexOf("Trident")>-1) 
  							|| (agent.indexOf("Edge")>-1);
    
    	//qna_board_view.jsp에서 넘긴 fileName 가져오기
    	String fileName=request.getParameter("fileName");    	    	
    
    	String fDownloadPath="d:/upload";
    	String filePath=fDownloadPath+"/"+fileName;
    	
    	FileInputStream in=new FileInputStream(filePath);
    	
    	//response 헤더 설정
    	response.setContentType("application/octet-stream");
    	
    	//넘어온 fileName 인코딩 변경
    	if(ieBrowser){ //edge 
    		fileName=URLEncoder.encode(fileName,"UTF-8")
    										.replaceAll("\\+", "%20");
    	}else{//크롬
    		fileName=new String(fileName.getBytes("UTF-8"),"iso-8859-1");
    	}   	
    	//fileName _ 기준으로 자르기
    	int start=fileName.lastIndexOf("_");
    	String oriName=fileName.substring(start+1);  	
    	    	
    	response.setHeader("Content-Disposition"
    			,"attachment;filename="+oriName);    	  	
    	
    	out.clear();
    	out=pageContext.pushBody();
    	
    	BufferedOutputStream buf=
    			new BufferedOutputStream(response.getOutputStream());
    	int numRead;
    	byte b[]=new byte[4096];
    	while((numRead=in.read(b,0,b.length))!=-1){
    		buf.write(b, 0, numRead);
    	}
    	buf.flush();
    	buf.close();
    	in.close();
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>