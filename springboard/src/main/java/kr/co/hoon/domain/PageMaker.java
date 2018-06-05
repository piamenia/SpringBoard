package kr.co.hoon.domain;

// 목록보기 하단에 출력되는 페이지번호와 관련된 VO클래스
public class PageMaker {
	// 전체 데이터 개수, 시작페이지번호, 마지막페이지번호
	private int totalCount, startPage, endPage;
	// 이전과 다음 링크 여부
	private boolean prev, next;
	// 페이지번호 출력개수
	private int displayPageNum = 5;
	// 이전에 설정된 옵션 값을 저장하기 위한 변수
	private SearchCriteria criteria;
	
	@Override
	public String toString() {
		return "PageMaker [totalCount=" + totalCount + ", startPage=" + startPage + ", endPage=" + endPage + ", prev="
				+ prev + ", next=" + next + ", displayPageNum=" + displayPageNum + ", criteria=" + criteria + "]";
	}
	public int getTotalCount() {
		return totalCount;
	}
	// 전체 데이터 개수를 알고 현재 페이지번호와 출력할 페이지 개수를 알면 나머지 전부 계산 가능
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		// 마지막페이지번호
		// Math.ceil 올림 함수
		endPage = (int)(Math.ceil(criteria.getPage()/(double)displayPageNum)) * displayPageNum;
//		System.out.println("끝페이지"+endPage);
		// 시작페이지번호
//		System.out.println("시작페이지"+startPage);
		startPage = endPage - displayPageNum + 1;
		// 이전페이지 링크여부
		prev = startPage==1 ? false : true;
		// 끝페이지 번호는 전체데이터의 페이지 개수보다 크면 전체데이터의 페이지 개수로 설정
		int pagesu = (int)(Math.ceil(totalCount/(double)criteria.getPerPageNum()));
		if(endPage > pagesu) {
			endPage = pagesu;
//			System.out.println("끝페이지"+endPage);
		}
		// 마지막 페이지번호와 페이지 개수가 같으면 다음은 나올필요 없음
		next = endPage == pagesu ? false : true;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public int getDisplayPageNum() {
		return displayPageNum;
	}
	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}
	public SearchCriteria getCriteria() {
		return criteria;
	}
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = criteria;
	}
}
