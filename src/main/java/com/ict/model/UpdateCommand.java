package com.ict.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ict.db.BVO;
import com.ict.db.DAO;

public class UpdateCommand implements Command{
	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		String cPage = request.getParameter("cPage");
		String b_idx = request.getParameter("b_idx");
		BVO bvo = DAO.getOneList(b_idx);
		
		request.setAttribute("cPage", cPage);
		request.setAttribute("bvo", bvo);
		return "view/update.jsp";
	}
}
