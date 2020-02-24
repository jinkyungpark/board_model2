package board.service;

import board.domain.BoardVO;
import board.persistence.BoardDAO;

import static board.persistence.JDBCUtil.*;

import java.sql.Connection;

public class BoardUpdateService {
	public boolean isArticleWriter(int bno, String password) throws Exception{	
		
		//db삽입
		Connection con=getConnection();
		BoardDAO dao=new BoardDAO(con);
		
		boolean isArticleWriter=dao.pwdCheck(bno, password);
			
		close(con);
		
		return isArticleWriter;
	}
	
	public 	boolean modifyArticle(BoardVO modifyVo) throws Exception{	
		boolean isModifySuccess=false;
		//db삽입
		Connection con=getConnection();
		BoardDAO dao=new BoardDAO(con);
		
		int updateCount=dao.updateRow(modifyVo);
		
		if(updateCount>0) {
			isModifySuccess=true;
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
		
		return isModifySuccess;
	}
}
