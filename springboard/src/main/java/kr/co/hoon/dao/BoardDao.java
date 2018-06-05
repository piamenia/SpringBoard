package kr.co.hoon.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.hoon.domain.Board;
import kr.co.hoon.domain.Criteria;
import kr.co.hoon.domain.SearchCriteria;

@Repository
public class BoardDao {
	@Autowired
	private SqlSession sqlSession;
	
	public void write(Board board) {
		sqlSession.insert("board.write", board);
	}
	
	public int totalCount(SearchCriteria criteria) {
		return sqlSession.selectOne("board.totalCount", criteria);
	}
	
	public List<Board> list(SearchCriteria criteria){
		return sqlSession.selectList("board.list", criteria);
	}
	
	public void readcnt(int bno) {
		sqlSession.update("board.readcnt", bno);
	}
	public Board detail(int bno) {
		return sqlSession.selectOne("board.detail", bno);
	}
	
	public void update(Board board) {
		//System.out.println("DAO");
		sqlSession.update("board.update", board);
	}
	
	public void delete(int bno) {
		sqlSession.delete("board.delete", bno);
	}
	
	public int replyCount(int bno) {
		return sqlSession.selectOne("board.replyCount", bno);
	}
}
