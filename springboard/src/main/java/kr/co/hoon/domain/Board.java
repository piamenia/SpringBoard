package kr.co.hoon.domain;

import java.util.Date;

public class Board {
	private String title, content, ip, email, nickname;
	private int bno, readcnt, replycnt;
	private String regdate;
	// 날짜 및 시간을 출력할 변수
	// 오늘 작성한 글은 시간, 이전에 작성한 글은 날자를 출력
	private String dispdate;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public int getReadcnt() {
		return readcnt;
	}
	public void setReadcnt(int readcnt) {
		this.readcnt = readcnt;
	}
	public int getReplycnt() {
		return replycnt;
	}
	public void setReplycnt(int replycnt) {
		this.replycnt = replycnt;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getDispdate() {
		return dispdate;
	}
	public void setDispdate(String dispdate) {
		this.dispdate = dispdate;
	}
	@Override
	public String toString() {
		return "Board [title=" + title + ", content=" + content + ", ip=" + ip + ", email=" + email + ", nickname="
				+ nickname + ", bno=" + bno + ", readcnt=" + readcnt + ", replycnt=" + replycnt + ", regdate=" + regdate
				+ ", dispdate=" + dispdate + "]";
	}
	
	
}
