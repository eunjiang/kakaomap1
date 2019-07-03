package com.keyword.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.keyword.dao.Member;
import com.keyword.dao.SearchHistory;
import com.keyword.dao.SearchHistoryRepository;

@Service
public class SearchHistoryService {
	@Autowired
	SearchHistoryRepository searchHistoryRepository;

	@Transactional
	public void save(SearchHistory entity) {
		searchHistoryRepository.save(entity);
	}

	public Page<SearchHistory> findByMember(Member member, Pageable pageable) {
		//
		return searchHistoryRepository.findByMember(member, pageable);
	}

}