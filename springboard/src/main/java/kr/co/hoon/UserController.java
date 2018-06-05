package kr.co.hoon;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.hoon.domain.User;
import kr.co.hoon.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="user/register", method=RequestMethod.GET)
	public String register(Model model) {
		return "user/register";
	}
	
	@RequestMapping(value="user/register", method=RequestMethod.POST)
	public String register(MultipartHttpServletRequest request, RedirectAttributes attr) {
		userService.register(request);
		// RedirectAttributes는 redirect로 이동할 때 한번만 전달되는 데이터를 보관하는 Spring의 클래스
		attr.addFlashAttribute("msg","회원가입에 성공했습니다.");
		attr.addFlashAttribute("command","register");
		// 삽입, 삭제, 갱신 후에는 redirect 해야함
		// redirect는 Controller로 다시 돌아온 후 다시 forwarding
		return "redirect:/";
	}
	
	@RequestMapping(value="user/login", method=RequestMethod.GET)
	public String login(Model model) {
		return "user/login";
	}
	
	@RequestMapping(value="user/login", method=RequestMethod.POST)
	public String login(HttpServletRequest request, Model model, RedirectAttributes attr, HttpSession session) {
		User user = userService.login(request);
		// 로그인 실패한 경우
		if(user == null) {
			attr.addFlashAttribute("msg","없는 이메일이거나 잘못된 비밀번호입니다.");
			return "redirect:login";
		}else {
			// 로그인에 성공했다면
			session.setAttribute("user", user);
			// 이전 요청이 있는지 확인
			Object dest = session.getAttribute("dest");
			if(dest==null) {
				// 없으면 메인페이지로
				return "redirect:/";
			} else {
				// 있으면 그 페이지로
				return "redirect:/" + dest.toString();
			}
		}
	}
	
	@RequestMapping(value="user/logout", method=RequestMethod.GET)
	public String logout(RedirectAttributes attr, HttpSession session) {
		attr.addFlashAttribute("msg", "로그아웃 되었습니다.");
		attr.addFlashAttribute("command","logout");
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value = "user/sendmail", method = RequestMethod.GET)
	public String sendmail(@RequestParam("email") String email, Model model) {
		model.addAttribute("email", email);
		return "user/sendmail";
	}
	
	@RequestMapping(value = "user/sendmail", method = RequestMethod.POST)
	public String sendmail(HttpServletRequest request, RedirectAttributes attr) {
		userService.sendmail(request);
		attr.addFlashAttribute("msg", "메일을 보내셨습니다.");
		return "redirect:/board/list";
	}
	
}
