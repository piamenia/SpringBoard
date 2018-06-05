package kr.co.hoon.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.co.hoon.domain.User;

@Repository
public class UserDao {
	@Autowired
	private SqlSession sqlSession;
	
	// email 중복검사
	public String emailCheck(String email) {
		// 데이터 1개 받아오는 SQL
		return sqlSession.selectOne("user.emailcheck",email);	
	}
	
	// 회원가입
	public void register(User user) {
		sqlSession.insert("user.register", user);
	}
	
	// 로그인
	public User login(String email) {
		return sqlSession.selectOne("user.login", email);
	}
}
