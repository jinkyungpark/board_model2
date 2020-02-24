package board.control;

import board.action.Action;
import board.action.BoardWriteAction;
import board.action.BoardDeleteAction;
import board.action.BoardHitUpdateAction;
import board.action.BoardListAction;
import board.action.BoardModifyAction;
import board.action.BoardPwdCheckAction;
import board.action.BoardReplyAction;
import board.action.BoardReplyViewAction;
import board.action.BoardSearchAction;
import board.action.BoardUpdateAction;
import board.action.BoardViewAction;

public class BoardActionFactory {
	
	private static BoardActionFactory baf=null;
	
	private BoardActionFactory() {}
	public static BoardActionFactory getInstance() {
		if(baf==null)
			baf=new BoardActionFactory();
		return baf;
	}
	
	Action action=null;
	public Action action(String cmd) {
		
		if(cmd.equals("/qList.do")) {
			action=new BoardListAction("view/qna_board_list.jsp");
		}else if(cmd.equals("/qInsert.do")) {
			action=new BoardWriteAction("qList.do");
		}else if(cmd.equals("/qView.do")) {
			action=new BoardViewAction("view/qna_board_view.jsp");
		}else if(cmd.equals("/qHitUpdate.do")) {
			action=new BoardHitUpdateAction("qView.do");
		}else if(cmd.equals("/qPwdCheck.do")) {
			action=new BoardPwdCheckAction("qDelete.do");
		}else if(cmd.equals("/qDelete.do")) {
			action=new BoardDeleteAction("qList.do");
		}else if(cmd.equals("/qModify.do")){
			action=new BoardModifyAction("view/qna_board_modify.jsp");		
		}else if(cmd.equals("/qUpdate.do")) {
			action=new BoardUpdateAction("qView.do");
		}else if(cmd.equals("/qReplyView.do")) {
			action=new BoardReplyViewAction("view/qna_board_reply.jsp");
		}else if(cmd.equals("/qReply.do")) {
			action=new BoardReplyAction("qList.do");
		}else if(cmd.equals("/qSearch.do")) {			
			action=new BoardSearchAction("view/qna_board_list.jsp");
		}
		return action;
	}
}