package kr.co.hoon.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.hoon.dao.BoardDao;
import kr.co.hoon.domain.Board;
import kr.co.hoon.domain.Criteria;
import kr.co.hoon.domain.PageMaker;
import kr.co.hoon.domain.SearchCriteria;
import kr.co.hoon.domain.User;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDao boardDao;

	@Override
	public void write(HttpServletRequest request) {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String ip = request.getRemoteAddr();
		// 로그인한 유저의 정보는 session의 user 속성에 저장돼있음
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String email = user.getEmail();
		String nickname = user.getNickname();
		
		Board board = new Board();
		board.setTitle(title);
		board.setContent(content);
		board.setIp(ip);
		board.setEmail(email);
		board.setNickname(nickname);
		
		System.out.println(board);
		
		boardDao.write(board);
	}

	@Override
	public Map<String, Object> list(SearchCriteria criteria) {
		Map<String, Object> map = new HashMap<String, Object>();
		// return boardDao.list();
		// 오늘 작성된 글은 시간, 이전에 작성된 글은 날짜를 출력
		List<Board> list = boardDao.list(criteria);
		// 마지막 페이지에 있는 데이터가 1개밖에 없을 때
		// 그 데이터를 삭제하면 그 페이지의 데이터가 없음
		if(list.size()==0) {
			// 현재 페이지 번호를 1 감소시켜서 데이터를 다시 가져오기
			criteria.setPage(criteria.getPage()-1);
			list = boardDao.list(criteria);
		}
		
		// 오늘 날짜
		Calendar cal = Calendar.getInstance();
		java.sql.Date today = new java.sql.Date(cal.getTimeInMillis());
		
		// list의 데이터를 확인해서 날짜와 시간 저장
		for(Board board : list) {
			// 날짜
			String regdate = board.getRegdate().split(" ")[0];
			// 글쓴 날짜와 오늘날짜를 비교해서
			if(regdate.equals(today.toString())) {
				// 같을 때(오늘 작성한 글)
				board.setDispdate(board.getRegdate().split(" ")[1].substring(0, 5));
			}else {
				// 다를 떄(이전 글)
				board.setDispdate(regdate);
			}
			board.setReplycnt(boardDao.replyCount(board.getBno()));
		}
		map.put("list", list);
		
		// 페이지번호 목록
		PageMaker pageMaker = new PageMaker();
		// 현재 페이지와 페이지 당 출력 개수는 저장
		pageMaker.setCriteria(criteria);
		// 전체 데이터 개수저장
		pageMaker.setTotalCount(boardDao.totalCount(criteria));
//		System.out.println(pageMaker);
		// 맵에 저장
		map.put("pageMaker", pageMaker);
		return map;
	}

	@Override
	public Board detail(HttpServletRequest request) {
		// Integer.parseInt() 를 호출했을 때 예외가 발생하는 경우
		// 숫자 뒤에 공백이 포함된 경우
		// NumberFormatException
		int bno = Integer.parseInt(request.getParameter("bno"));
		// 조회수 1증가
		boardDao.readcnt(bno);
		Board board = boardDao.detail(bno);
		// 댓글 개수 가져와서 설정
		board.setReplycnt(boardDao.replyCount(bno));
		return board;
	}

	@Override
	public Board updateView(HttpServletRequest request) {
		int bno = Integer.parseInt(request.getParameter("bno"));
		return boardDao.detail(bno);
	}

	@Override
	public void update(HttpServletRequest request) {
		//System.out.println("서비스");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String ip = request.getRemoteAddr();
		int bno = Integer.parseInt(request.getParameter("bno"));
		Board board = new Board();
		board.setTitle(title);
		board.setContent(content);
		board.setIp(ip);
		board.setBno(bno);
		
		boardDao.update(board);
	}

	@Override
	public void delete(HttpServletRequest request) {
		int bno = Integer.parseInt(request.getParameter("bno"));
		boardDao.delete(bno);
	}
}
