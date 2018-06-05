package kr.co.hoon.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.co.hoon.domain.Board;
import kr.co.hoon.domain.Criteria;
import kr.co.hoon.domain.SearchCriteria;

public interface BoardService {
	public void write(HttpServletRequest request);
	// 게시물 목록과 현재 페이지 번호,
	// 출력할 페이지 번호 관련 데이터를 같이 넘겨줘야함
	// 게시물 상세보기를 하거나 수정 및 삭제를 했을 때 현재 페이지번호를 같이 넘겨서 목록보기를 하거나
	// 작업이 완료되면 현재 페이지로 이동하는게 UI가 좋기 때문
	public Map<String, Object> list(SearchCriteria criteria);
	public Board detail(HttpServletRequest request);
	public Board updateView(HttpServletRequest request);
	public void update(HttpServletRequest request);
	public void delete(HttpServletRequest request);
}
