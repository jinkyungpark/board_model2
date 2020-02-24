package board.persistence;

import static board.persistence.JDBCUtil.*;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import board.domain.BoardVO;

public class BoardDAO {
	
	private Connection con;	
	
	public BoardDAO(Connection con) {
		super();
		this.con = con;
	}

	//게시판 전체 목록 가져오기
	public ArrayList<BoardVO> selectAll(){
		//번호, 제목, 작성자, 날짜, 조회수가 보여질 것임
		ArrayList<BoardVO> list=new ArrayList<>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			String sql="select bno,title,name,regdate,readcount ";
			//sql+="from board order by bno desc";
			sql+="from board order by re_ref desc, re_seq asc";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				BoardVO vo=new BoardVO();
				vo.setBno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setRegdate(rs.getDate(4));
				vo.setReadcount(rs.getInt(5));
				list.add(vo);
			}			
		}catch(Exception e) {			
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}			
		return list;
	}
	
	//게시글 하나 삽입하기
	public int insertArticle(BoardVO vo) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
				
		try {
			String sql="insert into board(bno,name,password,title,content,";
			sql+="attach,re_ref,re_lev,re_seq) values(board_seq.nextVal,?,?,?,?,?,board_seq.currval,?,?)";
			pstmt=con.prepareStatement(sql);			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getTitle());
			pstmt.setString(4, vo.getContent());
			pstmt.setString(5, vo.getFile());			
			pstmt.setInt(6, 0);
			pstmt.setInt(7, 0);
			
			result=pstmt.executeUpdate();
				
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rs);
		}		
		return result;
	}
	
	//bno에 해당하는 글 하나 가져오기
	public BoardVO getRow(int bno){
		BoardVO vo=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			String sql="select bno,title,name,content,attach,re_ref,re_lev,re_seq ";
			sql+="from board where bno=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				vo=new BoardVO();
				vo.setBno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setFile(rs.getString(5));
				//reply에서 필요한 정보들
				vo.setRe_ref(rs.getInt(6));
				vo.setRe_lev(rs.getInt(7));
				vo.setRe_seq(rs.getInt(8));
				//===여기까지
			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}		
		return vo;
	}
	
	//조회수 올리기
	public int setReadCountUpdate(int bno) {
		int result=0;
		PreparedStatement pstmt=null;
	
		try {
				String sql="update board set readcount=readcount+1 ";
				sql+="where bno=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, bno);
				result=pstmt.executeUpdate();				
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}		
		return result;
	}
	
	//비밀번호 확인
	public boolean pwdCheck(int bno,String password) {
		boolean flag=false;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			String sql="select bno from board where bno=? and password=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			pstmt.setString(2, password);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				flag=true;
			}			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}		
		return flag;
	}
	//게시글 삭제
	public int deleteRow(int bno) {
		int result=0;
		PreparedStatement pstmt=null;
		try {					
			String sql="delete from board where bno=?";			
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			result=pstmt.executeUpdate();				
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}		
		return result;
	}
	
	//게시글 수정
	public int updateRow(BoardVO vo) {
		int result=0;
		PreparedStatement pstmt=null;
		try {
			String sql="";
			if(vo.getFile()!=null) {
				sql="update board set content=?, title=?, attach=?"
						+ " where bno=?";			
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, vo.getContent());
				pstmt.setString(2, vo.getTitle());
				pstmt.setString(3, vo.getFile());
				pstmt.setInt(4, vo.getBno());			
			}else {			
				sql="update board set content=?, title=? where bno=?";			
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, vo.getContent());
				pstmt.setString(2, vo.getTitle());				
				pstmt.setInt(3, vo.getBno());				
			}			
			result=pstmt.executeUpdate();			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}				
		return result;
	}
	
	//답변글 처리
	public int board_reply(BoardVO vo) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;		
		
		try {			
		//원본글의 re_ref,re_lev,re_seq가져온 후
		//update 실행하기
		int re_ref=vo.getRe_ref();
		int re_lev=vo.getRe_lev();
		int re_seq=vo.getRe_seq();
		//현재 원본글에 대한 기존 댓글들의 re_seq 값 변화
		String sql="update board set re_seq=re_seq+1 ";
		sql+="where re_ref=? and re_seq>?";
		pstmt=con.prepareStatement(sql);
		pstmt.setInt(1, re_ref);
		pstmt.setInt(2, re_seq);
		int updateCount=pstmt.executeUpdate();
		
		if(updateCount>0) {
			commit(con);			
		}
		close(pstmt);
		
		//원본글의 re_seq와 re_lev값에 +1을 한 후
		re_seq=re_seq+1;
		re_lev=re_lev+1;
		//댓글의 insert 작업 시작하기
		sql="insert into board(bno,name,password,title,content,";
		sql+="attach,re_ref,re_lev,re_seq) values(board_seq.nextVal,?,?,?,?,?,?,?,?)";
		pstmt=con.prepareStatement(sql);		
		pstmt.setString(1, vo.getName());
		pstmt.setString(2, vo.getPassword());
		pstmt.setString(3, vo.getTitle());
		pstmt.setString(4, vo.getContent());
		pstmt.setString(5, vo.getFile());
		pstmt.setInt(6, re_ref);
		pstmt.setInt(7, re_lev);
		pstmt.setInt(8, re_seq);
		
		result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}		
		return result;
	}
	
	//페이징 처리 후 게시물 가져오기
	public ArrayList<BoardVO> getList(int page, int amount){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		//페이지 번호에 따른 rownum 시작값
		int start=page*amount;
		//데이터 수
		int limit=(page-1)*amount;
				
		//번호, 제목, 작성자, 날짜, 조회수가 보여질 것임
		ArrayList<BoardVO> list=new ArrayList<>();

		try {
		//select bno,name,title,regdate,readcount from (select /*+INDEX_DESC(board SYS_C0012619)*/
		//rownum rn,bno,name,title,content,regdate,readcount from board where rownum <= 1*10) where rn>(1-1)*10
			
			StringBuffer sql=new StringBuffer();
			sql.append("select bno,title,name,regdate,readcount,re_lev ");
			sql.append("from (select rownum rn,A.* from (select bno,title,name,regdate,readcount,re_lev ");
			sql.append(" from board order by re_ref desc, re_seq asc) A");
			sql.append(" where rownum <= ? )");
			sql.append(" where rn>? ");
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setInt(1, start);
			pstmt.setInt(2, limit);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				BoardVO vo=new BoardVO();
				vo.setBno(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setTitle(rs.getString(3));
				vo.setRegdate(rs.getDate(4));
				vo.setReadcount(rs.getInt(5));
				vo.setRe_lev(rs.getInt(6));
				list.add(vo);
			}		
		}catch(Exception e) {		
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}			
        return list;
	}
	
	//전체 목록 갯수 구하기
	public int getRows() {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int total_rows=0;
		
		try {			
			String sql="select count(*) from board";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next())
				total_rows=rs.getInt(1);			
		}catch(Exception e) {			
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return total_rows;
	}
	//검색기준과 검색어에 따른 전체 행수
	public int getSearchRows(String criteria, String keyword) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int total_rows=0;
		
		try {
			
			String sql="select count(*) from board where ";
			sql+=criteria+" like ?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "%"+keyword+"%");
			rs=pstmt.executeQuery();
			if(rs.next())
				total_rows=rs.getInt(1);			
		}catch(Exception e) {			
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return total_rows;
	}
	
	//검색결과리스트
	public ArrayList<BoardVO> getSearchList(int page, int amount
			,String criteria,String keyword){
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		//사용자가 선택한 페이지 번호를 가지고 몇번째 레코드부터 가져올 것인지 결정
		//int start=(page-1)*10;
		int start=page*amount;
		int limit=(page-1)*amount;
		//번호, 제목, 작성자, 날짜, 조회수가 보여질 것임
		ArrayList<BoardVO> list=new ArrayList<>();

		try {	
			StringBuffer sql=new StringBuffer();
			sql.append("select bno,title,name,regdate,readcount,re_lev ");
			sql.append("from (select rownum rn,A.* from (select bno,title,name,regdate,readcount,re_lev ");
			sql.append(" from board where "+criteria+" like ? order by re_ref desc, re_seq asc) A");
			sql.append(" where rownum <= ? )");
			sql.append(" where rn>? ");		
		
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, "%"+keyword+"%");
			pstmt.setInt(2, start);
			pstmt.setInt(3, limit);
			rs=pstmt.executeQuery();			
			while(rs.next()) {
				BoardVO vo=new BoardVO();
				vo.setBno(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setTitle(rs.getString(3));
				vo.setRegdate(rs.getDate(4));
				vo.setReadcount(rs.getInt(5));
				vo.setRe_lev(rs.getInt(6));
				list.add(vo);
			}			
		}catch(Exception e) {			
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}			
		return list;
	}
}













