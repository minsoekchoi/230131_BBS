package com.ict.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ict.db.BVO;
import com.ict.db.DAO;

public class ListCommand implements Command{
	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		// 전체 보기
		// List<BVO> list = DAO.getList();
		// request.setAttribute("list", list);
		
		// 나중에 페이징 기법을 처리
		Paging paging = new Paging();
		
		// 1.전체 게시물의 수
		int count = DAO.getCount();
		paging.setTotalRecord(count);
		
		// 2.전체 페이지의 수
		// 전체 게시글의 수가 한 페이지안에 존재하는 원글의 수 보다 작거나 같으면 전체 페이지수는 1페이지
		if(paging.getTotalRecord()<= paging.getNumPerPage()) {
			paging.setTotalPage(1);
		}else {
			paging.setTotalPage(paging.getTotalRecord()/paging.getNumPerPage());
			
			if(paging.getTotalRecord()%paging.getNumPerPage() != 0) {
				paging.setTotalPage(paging.getTotalPage()+1);
			}
		}
		
		// 3. 현재 페이지 구하기 
		// cmd가 list이면 무조건 cPage라는 현재 페이지값을 가지고 가야 한다.
		// cPage를 nowPage로 변경 시킨다.
		 String cPage = request.getParameter("cPage");
		 
		// ListCommand를 맨처음 오면 cPage가 없으므로  무조건 cPage는 null이다.
		 if(cPage == null) {
			 paging.setNowPage(1);
		 }else {
			 paging.setNowPage(Integer.parseInt(cPage));
		 }
		 
		 
		// ** 4-1. 현재 페이지의 시작번호와 끝번호 구하기 
		paging.setBegin((paging.getNowPage()-1)*paging.getNumPerPage()+1);
		paging.setEnd((paging.getBegin()-1)+paging.getNumPerPage());
		
		// ** 4-2. 시작번호와 끝번호를 가지고 DB에서 원하는 만큼은 게시물 가져오기
		List<BVO> list = DAO.getList(paging.getBegin(), paging.getEnd());
		request.setAttribute("list", list);
		
		
		// **5. 현재 페이지그이 시작 블록과 끝 블록를 구하자 
		paging.setBeginBlock((int)((paging.getNowPage()-1)/paging.getPagePerBlock())*paging.getPagePerBlock()+1 );
		paging.setEndBlock(paging.getBeginBlock()+paging.getPagePerBlock()-1);
		// 1=> 1-2,  2=> 1-2,   //  1=> 1-5, 2=> 1-5, 3=> 1-5, 
		// 3=> 3-4,  4=> 3-4    //  6=> 6-10 
		// 5=> 5-6,  6=> 5-6  
		// 주의 사항 : endBlock이 totalPage 보다 클수가 있다.
		//           이때는 쓸데없는 endBlock 생성된다.
		//           그래서 endBlock이 totalPage보다 크면 
		//           endBlock을 totalPage로 변경하자.
		if(paging.getEndBlock()>paging.getTotalPage()) {
			paging.setEndBlock(paging.getTotalPage());
		}
		request.setAttribute("paging", paging);
		
		
		return "view/list.jsp";
	}
}







