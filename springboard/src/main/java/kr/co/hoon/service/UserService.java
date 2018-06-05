package kr.co.hoon.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.hoon.domain.User;

public interface UserService {
	// 파라미터는 3가지 방법
	// 파라미터 각가을 파라미터로 만드는 경우 @RequestParam
	// 파라미터를 전부 모아서 만드는 경우 Command 객체
	// 모든 경우에 동일한 파라미터를 사용 HttpServletRequest
	// 단, 파일이 있는 경우는 MultipartHttpServletRequest
	public String emailCheck(HttpServletRequest request);
	public void register(MultipartHttpServletRequest request);
	public User login(HttpServletRequest request);
	public String address(String loc);
	public void sendmail(HttpServletRequest request);
}
