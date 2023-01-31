package com.ict.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ict.db.DAO;

public class DeleteCommand implements Command{
	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		String cPage = request.getParameter("cPage");
		String b_idx = request.getParameter("b_idx");
		// b_idx에 해당하는 비밀번호 가져오기 
		String pwd = DAO.getPwd(b_idx);
		request.setAttribute("cPage", cPage);
		request.setAttribute("b_idx", b_idx);
		request.setAttribute("pwd", pwd);
		return "view/delete.jsp";
	}
}
