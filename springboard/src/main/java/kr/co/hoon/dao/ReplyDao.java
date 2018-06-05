package kr.co.hoon.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.hoon.domain.Reply;

@Repository
public class ReplyDao {
	@Autowired
	private SqlSession sqlSession;
	
	public int write(Reply reply) {
		return sqlSession.insert("reply.write",reply);
	}
	
	public List<Reply> list(int bno) {
		return sqlSession.selectList("reply.list", bno);
	}
	
	public int delete(int rno) {
		return sqlSession.delete("reply.delete", rno);
	}
	
	public int update(Reply reply) {
		return sqlSession.update("reply.update", reply);
	}
}
