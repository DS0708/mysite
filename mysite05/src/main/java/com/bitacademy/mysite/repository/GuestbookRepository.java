package com.bitacademy.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public Boolean deleteByNoAndPassword(Long no, String password) {
		boolean result = false;
		
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("password", password);
		sqlSession.delete("guestbook.deleteByNoAndPassword", map);
		return result;		
	}
	
	public Boolean insert(GuestbookVo vo) {
		return 1 == sqlSession.insert("guestbook.insert", vo);
	}
	
	public List<GuestbookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");
	}
}