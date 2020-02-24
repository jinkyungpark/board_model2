package board.action;

import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.domain.BoardVO;
import board.domain.SearchVO;
import board.persistence.BoardDAO;
import board.service.BoardReplyService;
public class BoardReplyAction implements Action {
	private String path;	
	
	public BoardReplyAction(String path) {
		super();
		this.path = path;
	}

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//파일 처리를 위해 UploadFileUtils 호출
		UploadFileUtils utils=new UploadFileUtils();
		HashMap<String, String> dataMap=utils.getItem(req);
		
		//qna_board_reply.jsp에서 넘긴 값 가져오기		
		//작성자,제목,내용,비밀번호
		String name=dataMap.get("name");
		String title=dataMap.get("title");
		String content=dataMap.get("content");
		String password=dataMap.get("password");
		String file=null;
		if(dataMap.get("file")!=null)
			file=dataMap.get("file");
		
		//원본글의 bno,re_ref,re_lev,re_seq => hidden 태그 값
		int bno=Integer.parseInt(dataMap.get("bno"));
		int re_ref=Integer.parseInt(dataMap.get("re_ref"));
		int re_lev=Integer.parseInt(dataMap.get("re_lev"));
		int re_seq=Integer.parseInt(dataMap.get("re_seq"));
		
		
		
		//페이지 나누기 후 사용자가 현재 보던 페이지 가져오기
		String page=dataMap.get("page");
			
		/********검색어 처리 부분때문에 추가**********/		
		SearchVO search=null;
		String criteria=null,keyword=null;
		if(dataMap.get("criteria")!=null) {
			search=new SearchVO();
			criteria=dataMap.get("criteria");
			search.setCriteria(criteria); 
			keyword=URLEncoder.encode(dataMap.get("keyword"), "utf-8");
			search.setKeyword(keyword);
		}
		/*********************/
		
		
		BoardVO vo=new BoardVO();
		vo.setBno(bno);
		vo.setName(name);
		vo.setTitle(title);
		vo.setContent(content);
		vo.setPassword(password);
		vo.setFile(file);
		vo.setRe_ref(re_ref);
		vo.setRe_lev(re_lev);
		vo.setRe_seq(re_seq);
		
		//입력작업 수행
		BoardReplyService service=new BoardReplyService();
		boolean isReplySuccesse=service.replyArticle(vo);
		
		if(!isReplySuccesse) { //실패 : qna_board_reply.jsp
		  path="";
		}else { 			
			if(criteria.isEmpty()) {//목록보기로 이동
				path+="?page="+page;
			}else {//검색목록보기
				path="qSearch.do?page="+page+"&criteria="
							+criteria+"&keyword="+keyword;
			}
		}		
		return new ActionForward(path, true);
	}
}





