<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp"%>
<!-- Main content -->
<section class="content">
	<div class="box box-primary">
		<div class="box-header">
			<h3 class="box-title">Board Modify</h3>
		</div>
		<div style="height:20px"></div>
		<form action="qUpdate.do" method="post" role="form" enctype="multipart/form-data">
			<div class="box-body">
				<div class="form-group row">
					<label for="name" class="col-sm-2 col-form-label">글쓴이</label>
					<div class="col-sm-10" >
					<input type="text" name="name" size="10" class="form-control" maxlength='10' value="${vo.name}" readonly>
					</div>
				</div>
				<div class="form-group row">
					<label for="title" class="col-sm-2 col-form-label">제목</label>
					<div class="col-sm-10">
						<input type="text" name="title" size="50" class="form-control"	maxlength='100' value="${vo.title}" required>
					</div>
				</div>
				<div class="form-group row">
					<label for="content" class="col-sm-2 col-form-label">내용</label>
					<div class="col-sm-10">
						<textarea name='content' cols='60' class="form-control" rows='15' required>${vo.content}</textarea>
					</div>
				</div>
				<div class="form-group row">
					<label for="name" class="col-sm-2 col-form-label">비밀번호</label>
					<div class="col-sm-10">
						<input type="password" name="password" class="form-control" size="10" maxlength='10' required>
					</div>
        		</div>
				<div class="form-group row">
					<label for="filename" class="col-sm-2 col-form-label">파일첨부</label>
					<div class="col-sm-10">
						<c:if test="${empty vo.file}">
							<input type="file" name="file">
						</c:if>
						<c:if test="${!empty vo.file}">	
							<%--jstl 변수 선언 --%>
							<c:set value="${vo.file}" var="file"/>
							<%
								String file=(String)pageContext.getAttribute("file");
								//_기준으로  파일명 잘라내기
								int start=file.lastIndexOf("_");
								//file=file.substring(start+1);	
								pageContext.setAttribute("file", file.substring(start+1));
							%>							
							<a href="view/download.jsp?fileName=${vo.file}">${file}</a>
						</c:if>		
					</div>
				</div>
				<div style="height:20px"></div>
				<div class="box-footer text-center">
					<button type="submit" class="btn btn-primary">수정</button>
					<button type="button" class="btn btn-danger" onclick="history.back();">취소</button>
				</div>
				<div style="height:20px"></div>
			</div>
			<input type="hidden" value="${vo.bno}" name="bno">
			<input type="hidden" value="${page}" name="page">
			<%--검색어 처리 후 추가되는 부분 --%>
			<input type="hidden" value="${search.criteria}" name="criteria">
			<input type="hidden" value="${search.keyword}" name="keyword">			
		</form>
	</div>
</section>
<%@include file="../include/footer.jsp"%>







