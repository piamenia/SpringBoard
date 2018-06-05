package kr.co.hoon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.hoon.domain.Reply;
import kr.co.hoon.service.ReplyService;

// 결과를 html화면이 아니라 text나 json으로 만들어주는 Controller
@RestController
public class ReplyController {
	@Autowired
	private ReplyService replyService;
	
	@RequestMapping(value = "reply/write", method = RequestMethod.GET)
	public Map<String, Object> write(HttpServletRequest request) {
		boolean result = replyService.write(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}
	
	@RequestMapping(value = "reply/list", method = RequestMethod.GET)
	public List<Reply> list(HttpServletRequest request){
		return replyService.list(request);
	}
	
	@RequestMapping(value = "reply/delete", method = RequestMethod.GET)
	public Map<String, Object> delete(HttpServletRequest request) {
		boolean result = replyService.delete(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}
	
	@RequestMapping(value = "reply/update", method = RequestMethod.GET)
	public Map<String, Object> update(HttpServletRequest request){
		boolean result = replyService.update(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}
}
