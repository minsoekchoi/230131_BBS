package com.ict.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ict.db.BVO;
import com.ict.db.DAO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class WriteOKCommand implements Command{
	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		try {
			String path = request.getServletContext().getRealPath("upload");
			MultipartRequest mr = 
				new MultipartRequest(request, path,
						100*1024*1024,"utf-8", new DefaultFileRenamePolicy() );
			BVO bvo = new BVO();
			
			bvo.setSubject(mr.getParameter("subject"));
			bvo.setWriter(mr.getParameter("writer"));
			bvo.setContent(mr.getParameter("content"));
			bvo.setPwd(mr.getParameter("pwd"));
			
			// 파일처리
			if(mr.getFile("f_name") != null) {
				bvo.setF_name(mr.getFilesystemName("f_name"));
			}else {
				bvo.setF_name("");
			}
			int result = DAO.getInsert(bvo);	
			
		    if(result>0) {
		    	return "MyController?cmd=list";
		    }else {
		    	return "MyController?cmd=write";
		    }
		} catch (Exception e) {
		}
		return null;
	}
}
