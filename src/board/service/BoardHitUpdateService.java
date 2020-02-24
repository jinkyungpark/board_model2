package board.service;

import board.persistence.BoardDAO;

import static board.persistence.JDBCUtil.*;

import java.sql.Connection;

public class BoardHitUpdateService {
	public 	boolean hitUpdate(int bno) throws Exception{	
		boolean hitUpdate=false;
		//db삽입
		Connection con=getConnection();
		BoardDAO dao=new BoardDAO(con);
		
		int hitUpdateCount=dao.setReadCountUpdate(bno);
		
		if(hitUpdateCount>0) {
			hitUpdate=true;
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
		
		return hitUpdate;
	}
}
