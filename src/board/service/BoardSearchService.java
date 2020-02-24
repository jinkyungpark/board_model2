package board.service;

import board.domain.BoardVO;
import board.persistence.BoardDAO;

import static board.persistence.JDBCUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

public class BoardSearchService {
	
	public int getSearchListCount(String criteria,String keyword) throws Exception{
				
		Connection con=getConnection();
		BoardDAO dao=new BoardDAO(con);
		int searchTotalRows=dao.getSearchRows(criteria,keyword);
		close(con);
		
		return searchTotalRows;
	}
	
	
	public ArrayList<BoardVO> getSearchList(int page,int limit,String criteria,String keyword) throws Exception{
		
		Connection con=getConnection();
		BoardDAO dao=new BoardDAO(con);
		
		ArrayList<BoardVO> list=dao.getSearchList(page,limit,criteria,keyword);
				
		close(con);
		
		return list;
	}
}
