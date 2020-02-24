package board.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadFileUtils {
	//파일 업로드 처리 담당
	public HashMap<String, String> uploadFile(List<FileItem> items){
		HashMap<String, String> dataMap=new HashMap<>();
		
		//items에 들어있는 데이터를 폼 필드와 file 형태로 구별하기
		Iterator<FileItem> iter=items.iterator();
		String fieldName=null;
		while(iter.hasNext()) {
			FileItem item=iter.next();
			if(item.isFormField()) {
				fieldName=item.getFieldName();
				
				try {
					String value=item.getString("utf-8");
					dataMap.put(fieldName, value);
				}catch(UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}else {
				fieldName=item.getFieldName();
				String fileName=item.getName();
				
				if(!fileName.isEmpty() && item.getSize()>0) {
					String saveDir = "d:/upload";
					//모든 파일에 고유의 값을 생성 후 붙이기
					UUID id=UUID.randomUUID();
					File uploadFile=new File(saveDir+
							File.separator+id+"_"+fileName);
					//새롭게 바뀐 파일명을 HashMap에 저장
					dataMap.put(fieldName,uploadFile.getName());
					
					//디스크에 파일 저장
					try {
						item.write(uploadFile);
					} catch (Exception e) {						
						e.printStackTrace();
					}
				}
			}
		}		
		return dataMap;
	}
	public HashMap<String, String> getItem(HttpServletRequest req){
		//사용자로부터 넘어온 요청에 multipart 부분이 포함되어 있는지 확인하기
		boolean isMultipart=ServletFileUpload.isMultipartContent(req);
		ServletFileUpload upload=null;
		
		if(isMultipart) {
			//factory생성
			DiskFileItemFactory factory=new DiskFileItemFactory();
			
			//파일 업로드 핸들러
			upload=new ServletFileUpload(factory);
			//업로드 가능한 사이즈 지정 : 2MB
			upload.setSizeMax(2000*1024);
		}		
		//사용자의 업로드 요청 구문 해석 후 일반 요소로 온 것과 file로 온 것 분리
		List<FileItem> items=null;
		try {
			items=upload.parseRequest(req);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		//사용자의 요청에 대한 items 넘김		
		HashMap<String, String> dataMap=uploadFile(items);
	   
		return dataMap;
	}
}
