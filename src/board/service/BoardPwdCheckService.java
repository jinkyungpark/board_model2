package board.service;

import board.persistence.BoardDAO;

import static board.persistence.JDBCUtil.*;

import java.sql.Connection;

public class BoardPwdCheckService {
	public boolean passCheck(int bno, String password) throws Exception{
		boolean isCorrectPass=false;
		
		//db삽입
		Connection con=getConnection();
		BoardDAO dao=new BoardDAO(con);
		
		isCorrectPass=dao.pwdCheck(bno, password);
		
		close(con);
		
		return isCorrectPass;
	}
}
