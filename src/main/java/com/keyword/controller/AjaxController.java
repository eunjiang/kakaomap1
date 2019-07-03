package com.keyword.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.keyword.dao.Member;
import com.keyword.dao.Rank;
import com.keyword.dao.SearchHistory;
import com.keyword.service.ApiService;
import com.keyword.service.MemberService;
import com.keyword.service.RankService;
import com.keyword.service.SearchHistoryService;
import com.keyword.utils.CookieBox;

@RestController
@RequestMapping("ajax/")
public class AjaxController {
	private static final Logger logger = LoggerFactory.getLogger(AjaxController.class);

	@Autowired
	ApiService apiService;

	@Autowired
	MemberService memberService;

	@Autowired
	SearchHistoryService searchHistoryService;
	
	@Autowired
	RankService rankService;

	/**
	 * 키워드 검색 restAPI
	 * 
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/searchAddress")
	public Map<String, Object> searchBooks(HttpServletRequest req, HttpServletResponse res,
			@RequestParam("searchWord") String searchWord,
			@RequestParam(name = "page", defaultValue = "1") int page) {

		String account = CookieBox.getAccount(req);
		Member member = memberService.getMember(account);
		Map<String, Object> result = apiService.searchAddress(searchWord, page);
		searchHistoryService
				.save(new SearchHistory(searchWord, Timestamp.valueOf(LocalDateTime.now()), member));
		rankService
		.save(new Rank(searchWord));

		//
		return result;
	}


}