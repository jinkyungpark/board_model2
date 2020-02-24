package board.service;

import board.domain.BoardVO;
import board.persistence.BoardDAO;

import static board.persistence.JDBCUtil.*;

import java.sql.Connection;

public class BoardWriteService {
	public boolean registArticle(BoardVO regist) throws Exception{
		boolean isWriteSuccess=false;
		
		//db삽입
		Connection con=getConnection();
		BoardDAO dao=new BoardDAO(con);
		
		int result=dao.insertArticle(regist);
		if(result>0) {
			commit(con);
			isWriteSuccess=true;
		}else {
			rollback(con);
		}
		close(con);
		
		return isWriteSuccess;
	}
}
