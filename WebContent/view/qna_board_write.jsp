<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp"%>
<style>
.text-info { color: red; font-size: 0.8rem}
span.error { color: red; font-size: 0.8rem}
</style>
<!-- Main content -->
<section class="content">
	<div class="box box-primary">
		<div class="box-header">
			<h3 class="box-title">Board Write</h3>
		</div>
		<div style="height:20px"></div>
		<form action="../qInsert.do" method="post" id="writeForm" role="form" enctype="multipart/form-data">
			<div class="box-body">
				<div class="form-group row">
					<label for="name" class="col-sm-2 col-form-label">작성자</label>
					<div class="col-sm-10">
					<input type="text" name="name" id="name" size="10" class="form-control" maxlength='10'  placeholder="이름 입력">										
					</div>
				</div>
				<div class="form-group row">
					<label for="title"  class="col-sm-2 col-form-label">제목</label>
					<div class="col-sm-10">
					<input type="text" name="title" id="title" size="50" class="form-control"
					maxlength='100'>					
					</div>
				</div>
				<div class="form-group row">
					<label for="content" class="col-sm-2 col-form-label">내용</label>
					<div class="col-sm-10">
					<textarea name='content' cols='60' id="content" class="form-control" rows='15'></textarea>					
					</div>
				</div>
				<div class="form-group row">
					<label for="name" class="col-sm-2 col-form-label">비밀번호</label>
					<div class="col-sm-10">
						<input type="password" name="password" id="password" class="form-control" size="10" maxlength='10'>						
					</div>
				</div>
				<div class="form-group row">
					<label for="file" class="col-sm-2 col-form-label">파일첨부</label>
					<div class="col-sm-10">
						<input type="file" name="file" id="file">
						<small class="text-muted">(파일크기 : 2MB / 이미지 파일만 가능)</small>
						<small id="file" class="text-info"></small>
					</div>
				</div>
				<div style="height:20px"></div>
				<div class="form-group text-center">
					<button type="submit" class="btn btn-primary">등록</button>
					<button type="reset" class="btn btn-danger">다시작성</button>
					<button type="button" class="btn btn-warning" id="list">목록보기</button>
				</div>
				<div style="height:20px"></div>
			</div>
		</form>
	</div><!-- /.box -->
</section>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/jquery.validate.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/additional-methods.js"></script>
<script src="../js/writeform_validate.js"></script>
<%@include file="../include/footer.jsp"%>
<%-- 이 페이지는 validation 까지 한 페이지임
	file 확장자와 파일 사이즈를 걸기 위해 사용
	additional-method.js 사용해야 쓸 수 있음
	에러메시지는 일반 폼 필드는 라벨안쪽에 넣고
	파일 필드만 small 안에 넣은 것
 --%>

