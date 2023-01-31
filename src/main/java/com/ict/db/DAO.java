package com.ict.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class DAO {
	// 실제 사용하는 클래스  : SqlSession
	private static SqlSession ss;
	
	// 싱글턴 패턴으로 만들기 (동기화 처리) : 프로그램이 종료 될때 까지 
	// 한번 만들어진 객체를 재사용하게 하는 방식
	
	private synchronized static SqlSession getSession() {
		if(ss == null) {
			ss = DBService.getFactory().openSession();
		}
		return ss;
	}
	
	// DB 처리하는 메서드들
	
	// list
	// public static List<BVO> getList(){
	//	List<BVO> list = getSession().selectList("bbs.list");
	//	return list;
	//  }
	
	/////////////////// 페이징 처리 //////////////////
	
	// 1.전체 게시물의 수
	public static int getCount() {
		int result = getSession().selectOne("bbs.count");
		return result;
	}
	
	// 2. 시작번호와 끝번호를 받아서 리스트 구하기
	public static List<BVO> getList(int begin,int end){
		// 파라미터가 두개 이상이면 VO 또는 Map를 사용
		Map<String, Integer> map = new HashMap<>();
		map.put("begin", begin);
		map.put("end", end);
		List<BVO> list = getSession().selectList("bbs.list",map);
		return list;
	}
	
	// insert
	public static int getInsert(BVO bvo) {
		int result = getSession().insert("bbs.insert",bvo);
		ss.commit();
		return result;
	}
	
	// 조회수 업데이트
	public static int getHit(String b_idx) {
		int result = getSession().update("bbs.hitup", b_idx);
		ss.commit();
		return result;
	}
	
	// 상세정보 가져오기
	public static BVO getOneList(String b_idx) {
		BVO bvo = getSession().selectOne("bbs.onelist",b_idx);
		return bvo;
	}
	
	// 해당 원글에 대한 댓글도 가져와야 된다.
	public static List<CVO> getCList(String b_idx){
		List<CVO> c_list = getSession().selectList("bbs.c_list",b_idx);
		return c_list ;
	}
	
	// 댓글 삽입
	public static int getC_Insert(CVO cvo) {
		int result = getSession().insert("bbs.c_insert", cvo);
		ss.commit();
		return result;
	}
	
	// 댓글 삭제
	public static int getC_Delete(String c_idx) {
		int result = getSession().delete("bbs.c_delete", c_idx);
		ss.commit();
		return result;
	}
	
	// 원글 pwd 얻어내기
	public static String getPwd(String b_idx) {
		String result = getSession().selectOne("bbs.getpwd",b_idx);
		return result;
	}
	
	
	// 댓글 삭제 
	public static int getCommentDelete(String b_idx) {
		int result = getSession().delete("bbs.comment_delete", b_idx);
		ss.commit();
		return result;
	}
	
	// 원글 삭제 
	public static int getDelete(String b_idx) throws Exception{
		int result = getSession().delete("bbs.delete", b_idx);
		ss.commit();
		return result;
	}
	
	// 원글 수정
	public static int getUpdate(BVO bvo) {
		int result = getSession().update("bbs.update", bvo);
		ss.commit();
		return result;
	}
}










