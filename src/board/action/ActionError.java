package board.action;

public class ActionError extends Exception {
	//��� exception �� ���� ó�����ִ� Exception Ŭ����
	public ActionError() {}
	public ActionError(String msg) {
		super(msg);
	}
}

