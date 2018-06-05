package kr.co.hoon;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.hoon.service.UserService;

@RestController
public class JSONController {
	@Autowired
	private UserService userService;
	
	// 이메일 중복체크 요청을 처리
	@RequestMapping(value="user/emailcheck", method=RequestMethod.GET)
	public Map<String, Object> emailChech(HttpServletRequest request, Model modell){
		String email = userService.emailCheck(request);
		// 리턴할 Map 생성 
		Map<String, Object> map = new HashMap<String, Object>();
		// result라는 키에 email이 null 인지 저장
		// false 면 존재하는 email, true면 존재하지 않는 email
		map.put("result", email==null);
		return map;
	}
	
	@RequestMapping(value="address", method=RequestMethod.GET)
	public Map<String,Object> address(String loc){
		Map<String,Object> map = new HashMap<String, Object>();
		// Service의 주소 가져오는 메소드 호출
		String address = userService.address(loc);
		map.put("address", address);
		return map;
	}
}
