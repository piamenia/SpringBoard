package kr.co.hoon.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

	// Controller가 처리하기 전에 호출되는 메소드
	// true를 리턴하면 Controller로 이동
	// false를 리턴하면 Controller로 이동하지 않음
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 로그인 확인을 위해 session 가져오기
		HttpSession session = request.getSession();
		// 로그인 정보는 session 에 user 속성에 있음
		if(session.getAttribute("user")==null) {
			// 사용자의 요청을 session에 dest라는 속성에 저장
			// 로그인이 되면 원래 요청을 처리하기 위해
			// 클라이언트의 전체 요청주소
			String requestURI = request.getRequestURI();
			// 서버의 주소
			String contextPath = request.getContextPath();
			// 주소
			String uri = requestURI.substring(contextPath.length()+1);
			// 주소 뒤의 파라미터
			String query = request.getQueryString();
			// 실제 요청주소 만들기
			if(query == null || query.equals("null")) {
				query = "";
			}else {
				query = "?" + query;
			}
			// 세션에 주소 저장
			session.setAttribute("dest", uri+query);
			// 세션에 메시지 저장
			session.setAttribute("msg", "로그인해야 이용할 수 있는 서비스입니다.");
			
			// 로그인 페이지로 redirect
			response.sendRedirect(contextPath + "/user/login");
			return false;
		}
		return true;
	}

	// Contoller가 정상적으로 처리한 후에 호출되는 메소드
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	// Controller 처리여부에 관계 없이 호출되는 메소드
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}