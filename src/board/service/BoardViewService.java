package board.service;

import board.domain.BoardVO;
import board.persistence.BoardDAO;

import static board.persistence.JDBCUtil.*;

import java.sql.Connection;

public class BoardViewService {
	public BoardVO getArticle(int bno) throws Exception{	
		
		//db삽입
		Connection con=getConnection();
		BoardDAO dao=new BoardDAO(con);
		
		BoardVO vo=dao.getRow(bno);
			
		close(con);
		
		return vo;
	}
}
