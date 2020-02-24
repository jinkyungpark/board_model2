package board.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.action.Action;
import board.action.ActionForward;

/**
 * Servlet implementation class BoardControlServlet
 */
@WebServlet("*.do")
public class BoardControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//한글처리
		req.setCharacterEncoding("utf-8");
		
		//명령어 분리
		String requestURI=req.getRequestURI();
		String contextPath=req.getContextPath();
		String cmd=requestURI.substring(contextPath.length());

		//명령어에 따라 액션 생성
		BoardActionFactory baf=BoardActionFactory.getInstance();
		Action action=baf.action(cmd);
		
		//페이지 이동 경로
		ActionForward af=null;
		
		try {
			af=action.execute(req,res);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		//이동방식
		if(af.isRedirect()) {//path 가 true이면 sendRedirect
			res.sendRedirect(af.getPath());
		}else {
			RequestDispatcher rd=req.getRequestDispatcher(af.getPath());
			rd.forward(req, res);
		}	
	}		

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
