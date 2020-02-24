package board.service;

import board.domain.BoardVO;
import board.persistence.BoardDAO;

import static board.persistence.JDBCUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

public class BoardListService {
	
	public int getListCount() throws Exception{
				
		Connection con=getConnection();
		BoardDAO dao=new BoardDAO(con);
		int totalRows=dao.getRows();
		close(con);
		
		return totalRows;
	}
	
	
	public ArrayList<BoardVO> getArticleList(int start,int end) throws Exception{
		
		Connection con=getConnection();
		BoardDAO dao=new BoardDAO(con);
		
		ArrayList<BoardVO> list=dao.getList(start,end);
				
		close(con);
		
		return list;
	}
}
