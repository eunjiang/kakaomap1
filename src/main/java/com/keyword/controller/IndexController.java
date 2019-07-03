package com.keyword.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.keyword.dao.Member;
import com.keyword.dao.Rank;
import com.keyword.dao.SearchHistory;
import com.keyword.exceptions.IllegalLoginException;
import com.keyword.service.MemberService;
import com.keyword.service.RankService;
import com.keyword.service.SearchHistoryService;
import com.keyword.utils.CookieBox;

@Controller
@EnableAutoConfiguration
public class IndexController {
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	MemberService memberService;

	@Autowired
	SearchHistoryService searchHistoryService;
	
	@Autowired
	RankService rankService;

	private Member getMemberObj(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String account = CookieBox.getAccount(req);
		Member member = memberService.getMember(account);
		if (member == null) {
			throw new IllegalLoginException("로그인 되지 않았습니다.");
		}
		return member;
	}

	@RequestMapping("/")
	public String index(HttpServletRequest req, HttpServletResponse res, Model model) {
		try {
			Member member = getMemberObj(req, res);
			return "/index";
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "redirect:/loginForm";
		}

	}

	@RequestMapping("/searchHistory")
	public String searchHistory(HttpServletRequest req, HttpServletResponse res, Model model,
			@PageableDefault(size = 10, page = 0, sort = "regdate", direction = Direction.DESC) Pageable pageable) {
		try {
			Member member = getMemberObj(req, res);

			Page<SearchHistory> searchHistoryPage = searchHistoryService.findByMember(member, pageable);
			model.addAttribute("searchHistoryPage", searchHistoryPage);
			return "/searchHistory";
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "redirect:/loginForm";
		}
	
	}
	
	@RequestMapping("/rank")
	public String searchHistory(HttpServletRequest req, HttpServletResponse res, Model model, Rank rank) {
		try {
			Member member = getMemberObj(req, res);

			Long countByRank = rankService.countByRank(rank);
			model.addAttribute("countByRank", countByRank);
			return "/rank";
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "redirect:/loginForm";
		}
	}
	

}