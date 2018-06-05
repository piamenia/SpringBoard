package kr.co.hoon.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kr.co.hoon.domain.Reply;

public interface ReplyService {
	public boolean write(HttpServletRequest request);
	public List<Reply> list(HttpServletRequest request);
	public boolean delete(HttpServletRequest request);
	public boolean update(HttpServletRequest request);
}
