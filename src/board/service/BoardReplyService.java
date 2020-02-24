package board.service;

import board.domain.BoardVO;
import board.persistence.BoardDAO;

import static board.persistence.JDBCUtil.*;

import java.sql.Connection;

public class BoardReplyService {
	public boolean replyArticle(BoardVO vo) throws Exception{	
		
		boolean isReplySuccesse=false;
		
		//db삽입
		Connection con=getConnection();
		BoardDAO dao=new BoardDAO(con);
		
		int replyCount=dao.board_reply(vo);
			
		if(replyCount>0) {
			isReplySuccesse=true;
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		
		return isReplySuccesse;
	}
}
