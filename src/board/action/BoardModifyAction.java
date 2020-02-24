package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.domain.BoardVO;
import board.domain.SearchVO;
import board.persistence.BoardDAO;
import board.service.BoardViewService;
public class BoardModifyAction implements Action {
	private String path;	
	
	public BoardModifyAction(String path) {
		super();
		this.path = path;
	}

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//qna_board_view.jsp에서 넘어온 bno가져오기
		int bno=Integer.parseInt(req.getParameter("bno"));
		
		//페이지 나누기 후 사용자가 현재 보던 페이지 가져오기
		String page=req.getParameter("page");
		
		/********검색어 처리 부분때문에 추가**********/		
		SearchVO search=null;
		if(req.getParameter("criteria")!=null) {
			search=new SearchVO();
			search.setCriteria(req.getParameter("criteria")); 
			search.setKeyword(req.getParameter("keyword"));
		}
		/*********************/	
		
		
		//bno에 해당하는 행 하나 검색한 후 값을 가지고
		//qna_board_modify.jsp로 돌아가기
		BoardViewService views=new BoardViewService();
		BoardVO vo=views.getArticle(bno);
		
		if(vo==null)  
			path="";   //qna_board_view.jsp로 돌려보내기
		else {//
			req.setAttribute("vo", vo);
			req.setAttribute("page", page);
			req.setAttribute("search", search);
		}		
		return new ActionForward(path,false);
	}

}






