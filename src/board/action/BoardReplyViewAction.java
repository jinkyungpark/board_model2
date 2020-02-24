package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.domain.BoardVO;
import board.domain.SearchVO;
import board.persistence.BoardDAO;
import board.service.BoardViewService;
public class BoardReplyViewAction implements Action {
	private String path;	
	
	public BoardReplyViewAction(String path) {
		super();
		this.path = path;
	}

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//qna_board_view.jsp에서 넘긴 값 가져오기=>bno
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
		
		//bno에 해당하는 원본글의 정보 가져오기
		//re_ref,re_lev,re_seq 정보가 필요함
		BoardViewService view=new BoardViewService();
		BoardVO vo=view.getArticle(bno);
		
		if(vo!=null) { //원본글의 정보를 담고 qna_board_reply.jsp로 이동 
			req.setAttribute("vo", vo);
			req.setAttribute("page", page);
			req.setAttribute("search", search);
		}		
		return new ActionForward(path,false);
	}
}







