package board.service;

import board.persistence.BoardDAO;

import static board.persistence.JDBCUtil.*;

import java.sql.Connection;

public class BoardDeleteService {
	public 	boolean removeArticle(int bno) throws Exception{	
		boolean isRemoveSuccess=false;
		//db삽입
		Connection con=getConnection();
		BoardDAO dao=new BoardDAO(con);
		
		int removeCount=dao.deleteRow(bno);
		
		if(removeCount>0) {
			isRemoveSuccess=true;
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
		
		return isRemoveSuccess;
	}
}
