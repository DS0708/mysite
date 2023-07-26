package com.bitacademy.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.mysite.exception.UserRepositoryException;
import com.bitacademy.mysite.vo.UserVo;

@Repository
public class UserRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public Boolean insert(UserVo vo) {
		return 1 == sqlSession.insert("user.insert", vo);
	}
	
	public UserVo findByEmailAndPassword(UserVo vo) throws UserRepositoryException {
		return findByEmailAndPassword(vo.getEmail(), vo.getPassword());
	}
	
	public UserVo findByEmailAndPassword(String email, String password) throws UserRepositoryException {
		Map<String, Object> map = new HashMap<>();
		map.put("e", email);
		map.put("p", password);

		return sqlSession.selectOne("user.findByEmailAndPassword", map);
	}

	public UserVo findByNo(Long no) {
		return sqlSession.selectOne("user.findByNo", no);
	}

	public void update(UserVo vo) {
		sqlSession.update("user.update", vo);
	}
}