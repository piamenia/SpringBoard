package kr.co.hoon.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.hoon.dao.ReplyDao;
import kr.co.hoon.domain.Reply;

@Service
public class ReplyServiceImpl implements ReplyService {
	@Autowired
	private ReplyDao replyDao;

	@Override
	public boolean write(HttpServletRequest request) {
		boolean result = false;
		// 파라미터 읽기
		int bno = Integer.parseInt(request.getParameter("bno"));
		String email = request.getParameter("email");
		String nickname = request.getParameter("nickname");
		String replytext = request.getParameter("replytext");
		
		// Dao의 파라미터 만들기
		Reply reply = new Reply();
		reply.setBno(bno);
		reply.setEmail(email);
		reply.setNickname(nickname);
		reply.setReplytext(replytext);
		
		// Dao의 메소드 호출
		int r = replyDao.write(reply);
		if(r>0) result = true;
		
		return result;
	}

	@Override
	public List<Reply> list(HttpServletRequest request) {
		List<Reply> list = new ArrayList<Reply>();
		// 파라미터 읽기
		int bno = Integer.parseInt(request.getParameter("bno"));
		// Dao의 메소드 호출해서 리턴
		list = replyDao.list(bno);
		return list;
	}

	@Override
	public boolean delete(HttpServletRequest request) {
		boolean result = false;
		int rno = Integer.parseInt(request.getParameter("rno"));
		int r = replyDao.delete(rno);
		if(r>0) result = true;
		return result;
	}

	@Override
	public boolean update(HttpServletRequest request) {
		boolean result = false;
		int rno = Integer.parseInt(request.getParameter("rno"));
		String replytext = request.getParameter("replytext");
		Reply reply = new Reply();
		reply.setRno(rno);
		reply.setReplytext(replytext);
		int r = replyDao.update(reply);
		if(r>0) result = true;
		return result;
	}
}
