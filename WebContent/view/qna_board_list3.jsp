<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%@include file="../include/header.jsp"%>
<!-- Main content -->
<section class="content">
	<div class="box box-primary">
		<div class="box-header">
			<h3 class="box-title">List Board</h3>
		</div>
		<div class="row">
			<div class="col-md-4">
				<input type="button" value="글쓰기" class="btn btn-success" onclick="location.href='view/qna_board_write.jsp'">
			</div>			
				<div class="col-md-4 offset-md-4">
					<form action="qSearch.do" method="post" name='search'>								
						<select name="criteria">
							<option value="n" <c:out value="${search.criteria==null?'selected':''}"/>>-------</option>
							<option value="title" <c:out value="${search.criteria eq 'title'?'selected':''}"/>>title</option>
							<option value="content" <c:out value="${search.criteria eq 'content'?'selected':''}"/>>content</option>
							<option value="name" <c:out value="${search.criteria eq 'name'?'selected':''}"/>>name</option>						
						</select>
						<input type="text" name="keyword" value="${search.keyword}">									
						<input type="hidden" name="page" value="${info.page}">									
						<input type="button" value="검색"  class="btn btn-primary">
					</form>			
				</div>			
		</div>
		<br>	
		<table class="table table-bordered">	
			<tr>
				<th class='text-center' style='width:100px'>번호</th>
				<th class='text-center'>제목</th>
				<th class='text-center'>작성자</th>
				<th class='text-center'>날짜</th>
				<th class='text-center' style='width:100px'>조회수</th>
			</tr>
			<c:forEach var="vo" items="${list}">
			<tr><!-- 리스트 목록 보여주기 -->
				<td class='text-center'>${vo.bno}</td>		
				<td><a href="qHitUpdate.do?bno=${vo.bno}&page=${info.page}&criteria=${search.criteria}&searchword=${search.searchword}">
				<c:if test="${vo.re_lev != 0}">
					<c:forEach begin="0" step="1" end="${vo.re_lev*1}">
						&nbsp;
					</c:forEach>
				</c:if>
					${vo.title}</a></td>
				<%-- 
				① 방식 : 조회수+내용보기 하나로 구현되어 있어 새로고침시 조회수 계속 올라감
				<a href="qView.do?bno=${vo.bno}">${vo.title}</a> 
				
				② 방식 : 조회수만 먼저 실행 후 내용보기 실행
				<a href="qHitUpdate.do?bno=${vo.bno}">${vo.title}</a>--%>				 
				<td class='text-center'>${vo.name}</td>
				<td class='text-center'>${vo.regdate}</td>
				<td class='text-center'><span class="badge badge-pill badge-primary">${vo.readcount}</span></td>
			</tr>	
			</c:forEach>			
		</table>		
		<div class="container">			
		<div class="row  justify-content-md-center">			
			<div class="col col-lg-2">	
				<nav aria-label="Page navigation">
				  <ul class="pagination">
				    <li class="page-item">
				    	<c:if test="${info.page<=1}">
				    		<a class="page-link">Previous</a>
				    	</c:if>
				    	<c:if test="${info.page>1}">
				    		<c:choose>
				    			<c:when test="${search.criteria==null}">
				    				<a class="page-link" href="qList.do?page=${info.page-1}">Previous</a>
				    			</c:when>
				    			<c:otherwise>
				    				<li class="page-item"><a class="page-link" href="qSearch.do?page=${info.page-1}&criteria=${search.criteria}&searchword=${search.searchword}">Previous</a></li>
				    			</c:otherwise>
					    	</c:choose>				    		
				    	</c:if>
				    </li>
				   	 <c:forEach begin="${info.startPage}" end="${info.endPage}" var="idx">
			    	  	<c:choose>
				    	  	<c:when test="${idx==info.page}">
					    		 <li class="page-item"><a class="page-link">${idx}</a></li>				    		 
					    	</c:when>
					    	<c:otherwise>
					    		<c:choose>
					    			<c:when test="${search.criteria==null}">
					    				<li class="page-item"><a class="page-link" href="qList.do?page=${idx}">${idx}</a></li>
					    			</c:when>
					    			<c:otherwise>
					    				<li class="page-item"><a class="page-link" href="qSearch.do?page=${idx}&criteria=${search.criteria}&searchword=${search.searchword}">${idx}</a></li>
					    			</c:otherwise>
					    		</c:choose>					    		 				    		 
					    	</c:otherwise>
				    	</c:choose>
				    </c:forEach>	
			    	<c:if test="${info.page>=info.totalPage}">
			    		<li class="page-item"><a class="page-link">Next</a></li>
			    	</c:if>
			    	<c:if test="${info.page<info.totalPage}">
			    		<c:choose>
				    		<c:when test="${search.criteria==null}">
			    				<li class="page-item"><a class="page-link" href="qList.do?page=${info.page+1}">Next</a></li>
			    			</c:when>
			    			<c:otherwise>
			    				<li class="page-item"><a class="page-link" href="qSearch.do?page=${info.page+1}&criteria=${search.criteria}&searchword=${search.searchword}">Next</a></li>
			    			</c:otherwise>
			    		</c:choose>
			    	</c:if>
				  </ul>
				</nav>
			</div>	
			<div class="col col-md-auto offset-md-1"></div>			
			<div class="col col-lg-2 ">				
							
			</div>	
		</div>
		</div>
		<div style="height:20px"></div>	
	</div>	
</section>
<script>
	$(function(){
		$(".btn-primary").on("click",function(){
			if($("select[name='criteria']").val()=='n'){
				alert('검색 조건을 선택하세요');
				$("select[name='criteria']").focus();
				return false;
			}else if($("input[name='searchword']").val()==""){
				alert('검색어를 선택하세요');
				$("input[name='searchword']").focus();
				return false;
			}
			//버튼이기 때문에 submit 필요
			$("form[name='search']").submit();
		});
	});
</script>
<%@include file="../include/footer.jsp"%>

