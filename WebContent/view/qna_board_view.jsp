<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp"%>
<!-- Main content -->
<section class="content">
	<div class="box box-primary">
		<div class="box-header">
			<h3 class="box-title">Read Board</h3>
		</div>
		<div style="height:20px"></div>
		<form action="" method="post">
			<div class="box-body">
				<div class="form-group row">
					<label for="name" class="col-sm-2 col-form-label">글쓴이</label>
					<div class="col-sm-10">
						<input type="text" name="name" size="10" class="form-control" maxlength='10' value="${vo.name}" readonly>
					</div>
				</div>
				<div class="form-group  row">
					<label for="title" class="col-sm-2 col-form-label">제목</label>
					<div class="col-sm-10">
						<input type="text" name="title" size="50" class="form-control"	maxlength='100' value="${vo.title}" readonly>
					</div>
				</div>
				<div class="form-group  row">
					<label for="content" class="col-sm-2 col-form-label">내용</label>
					<div class="col-sm-10">
						<textarea name='board_content' cols='60' class="form-control" rows='15' readonly>${vo.content}</textarea>
					</div>
				</div>
				<div class="form-group  row">
					<label for="filename" class="col-sm-2 col-form-label">파일첨부</label>
					<div class="col-sm-10"><!-- 첨부파일이 없으면 첨부 파일 없음 메세지를 띄우고 있으면 파일명 보여주기 -->
						<%
							/* BoardVO vo=(BoardVO)request.getAttribute("vo");
							String filename=vo.getFile(); */						
						%>
						<c:if test="${empty vo.file}">
							첨부파일 없음
						</c:if>
						<c:if test="${!empty vo.file}">	
							<%--jstl 변수 선언 --%>
							<c:set value="${vo.file}" var="file"/>
							<%
								String file=(String)pageContext.getAttribute("file");
								//_기준으로  파일명 잘라내기
								//int start=file.lastIndexOf("_");
								//System.out.println(file.indexOf("_"));
								int start=file.indexOf("_");
								//file=file.substring(start+1);	
								pageContext.setAttribute("file", file.substring(start+1));
							%>							
							<a href="view/download.jsp?fileName=${vo.file}">${file}</a>
						</c:if>						
					</div>
				</div>
				<div style="height:10px"></div>
				<div class="box-footer text-center">
					<button type="button" class="btn btn-success" id="reply">답변</button>
					<button type="button" class="btn btn-warning" id="modify">수정</button>
					<button type="button" class="btn btn-danger" id="delete">삭제</button>
					<button type="button" class="btn btn-primary" id="list">목록보기</button>
				</div>
				<div style="height:20px"></div>
			</div>
		</form>
	</div>
	<form method="post" role="form">
		<input type="hidden" value="${vo.bno}" name="bno">	
		<%--페이지 나누기 후 추가되는 부분 --%>	
		<input type="hidden" value="${page}" name="page">
		<%--검색어 처리 후 추가되는 부분 --%>
		<input type="hidden" value="${search.criteria}" name="criteria">
		<input type="hidden" value="${search.keyword}" name="keyword">
	</form>
</section>
<script>
	$(function(){
		var formObj=$("form[role='form']");
		
		$("#list").click(function(){
			//location.href="qList.do";
			var criteria=$("input[name='criteria']").val();
			if(criteria===''){
				formObj.attr("action","qList.do");
			}else{
				formObj.attr("action","qSearch.do");
			}
			formObj.submit();			
		});
		$("#modify").click(function(){
			//location.href="qModify.do?bno=${vo.bno}";
			formObj.attr("action","qModify.do");
			formObj.submit();
		});
		$("#reply").click(function(){
			//location.href="qReplyView.do?bno=${vo.bno}";
			formObj.attr("action","qReplyView.do");
			formObj.submit();
		});
		$("#delete").click(function(){
			//location.href="view/qna_board_pwdCheck.jsp?bno=${vo.bno}";
			formObj.attr("action","view/qna_board_pwdCheck.jsp");
			formObj.submit();
		});		
	});
</script>
<%@include file="../include/footer.jsp"%>








