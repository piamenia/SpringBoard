package kr.co.hoon;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.hoon.domain.Board;
import kr.co.hoon.domain.Criteria;
import kr.co.hoon.domain.SearchCriteria;
import kr.co.hoon.service.BoardService;

@Controller
public class BoardController {
	// github에서 수정한 
	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "board/write", method = RequestMethod.GET)
	public String write() {
		return "board/write";
	}

	@RequestMapping(value = "board/write", method = RequestMethod.POST)
	public String write(HttpServletRequest request, RedirectAttributes attr, Model model) {
		boardService.write(request);
		attr.addFlashAttribute("msg", "게시글이 작성되었습니다.");
		// DB에 저장했기 때문에 반복하지 않도록 redirect
		return "redirect:list";
	}

	@RequestMapping(value = "board/list", method = RequestMethod.GET)
	public String list(Model model, SearchCriteria criteria) {
		model.addAttribute("map", boardService.list(criteria));
		return "board/list";
	}

	@RequestMapping(value = "board/detail", method = RequestMethod.GET)
	// 현재 페이지 번호와 페이지당 출력개수를 criteria에 저장하고 다음페이지에 자동으로 전달
	public String detail(SearchCriteria criteria, HttpServletRequest request, Model model) {
		Board board = boardService.detail(request);
		model.addAttribute("board", board);
		model.addAttribute("criteria",criteria);
		return "board/detail";
	}
	
	@RequestMapping(value = "board/update", method = RequestMethod.GET)
	public String update(HttpServletRequest request, Model model) {
		Board board = boardService.detail(request);
		model.addAttribute("board", board);
		return "board/update";
	}
	
	@RequestMapping(value = "board/update", method = RequestMethod.POST)
	public String update(SearchCriteria criteria, HttpServletRequest request, Model model, RedirectAttributes attr) {
		//System.out.println("컨트롤러");
		boardService.update(request);
		attr.addFlashAttribute("msg", "게시글이 수정되었습니다.");
		return "redirect:list?page="+criteria.getPage()+"&perPageNum="+criteria.getPerPageNum();
	}
	
	@RequestMapping(value = "board/delete", method = RequestMethod.GET)
	public String delete(SearchCriteria criteria, HttpServletRequest request) {
		boardService.delete(request);
		return "redirect:list?page="+criteria.getPage()+"&perPageNum="+criteria.getPerPageNum();
	}
}
