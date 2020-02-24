<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp"%>
<%
	//qna_board_view.jsp에서 넘긴 값
	String bno=request.getParameter("bno");
	String cur_page=request.getParameter("page");
%>
<!-- Main content -->
<section class="content">
	<div class="box box-primary">
		<div class="box-header">
			<h3 class="box-title">Password Check</h3>
		</div>
		<div style="height:20px"></div>
		<form name="pwdCheck" method="post" action="../qPwdCheck.do">
			<div class="box-body">
				<div class="form-group">
					<input type="password" name="password" class="form-control" 
					size="10" maxlength='10' autofocus>
				</div>
				<div class="form-group">
					<button type="submit" class="btn btn-primary">확인</button>
				</div>
			</div>
			<input type="hidden" name="bno" value="<%=bno%>">
			<input type="hidden" name="page" value="<%=cur_page%>">
		</form>
	</div>
</section>
<%@include file="../include/footer.jsp"%>





