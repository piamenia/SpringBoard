package kr.co.hoon.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

// kr.co.hoon 패키지에 있는 Controller에서 예외가 발생하면 호출되는 클래스로 설정
@ControllerAdvice("kr.co.hoon")
public class CommonExceptionAdvice {
	// 예외가 발생하면 호출되는 메소드
	public ModelAndView error(Exception e) {
		ModelAndView mav = new ModelAndView();
		// 뷰 이름 설정
		mav.setViewName("/error/error");
		// 전달할 데이터
		mav.addObject("exception", e);
		
		return mav;
	}
}
