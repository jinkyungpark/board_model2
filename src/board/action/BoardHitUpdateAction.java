package board.action;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.persistence.BoardDAO;
import board.service.BoardHitUpdateService;

public class BoardHitUpdateAction implements Action {
	private String path;	
	
	public BoardHitUpdateAction(String path) {
		super();
		this.path = path;
	}

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//조회수 업데이트 후 업데이트가 성공하면
		//게시글 읽어오기 액션으로 경로 지정하기
		
		//bno 가져오기
		int bno=Integer.parseInt(req.getParameter("bno"));
		//페이지 나누기 후 사용자가 현재 보던 페이지 가져오기
		String page=req.getParameter("page");
		
		/********검색어 처리 부분때문에 추가**********/
		String criteria=req.getParameter("criteria"); 
		String keyword=URLEncoder.encode
				(req.getParameter("keyword"),"utf-8");			  
		/*********************/
		
		//db
		BoardHitUpdateService service=new BoardHitUpdateService();
		//조회수 올리기
		boolean hitUpdate=service.hitUpdate(bno);
		
		if(hitUpdate) { //path : qView.do
			path+="?bno="+bno+"&page="+page+"&criteria="+criteria+"&keyword="+keyword;
		}
		
		return new ActionForward(path,true);
	}
}







