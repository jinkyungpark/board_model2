package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.persistence.BoardDAO;
import board.service.BoardDeleteService;

public class BoardDeleteAction implements Action {
	private String path;	
	
	public BoardDeleteAction(String path) {
		super();
		this.path = path;
	}
	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// PwdCheck.do에서 넘어오는 값 가져오기
		int bno=Integer.parseInt(req.getParameter("bno"));
		//페이지 나누기 후 사용자가 현재 보던 페이지 가져오기
		String page=req.getParameter("page");
		
		// 삭제한 후 리스트로 경로 이동	
		BoardDeleteService service=new BoardDeleteService();
		boolean isRemoveSuccess=service.removeArticle(bno);
		
		if(isRemoveSuccess) {
			path+="?page="+page;
		}else { //삭제실패
			
		}
		
		return new ActionForward(path, true);
	}
}
