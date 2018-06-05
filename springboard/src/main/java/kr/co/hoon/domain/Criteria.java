package kr.co.hoon.domain;

public class Criteria {
	// 페이지번호, 페이지당 개수, 페이지에 출력될 데이터 시작번호
	private int page, perPageNum, pageStart;

	// 생성자
	// 페이지번호 기본값은 1
	// 한페이지당 10개씩 출력
	public Criteria() {
		this.page = 1;
		this.perPageNum = 1;
	}
	
	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + ", pageStart=" + pageStart + "]";
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}

	// 페이지 번호를 가지고 시작하는 데이터 번호를 계산해서 리턴
	public int getPageStart() {
		pageStart = (page-1) * perPageNum + 1;
		return pageStart;
	}
	
	/*// 현재페이지의 데이터 시작번호는 입력받지 않으므로 삭제
	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}*/
}
