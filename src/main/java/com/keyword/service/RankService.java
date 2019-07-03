package com.keyword.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keyword.dao.Rank;
import com.keyword.dao.RankRepository;

@Service
public class RankService {
	@Autowired
	RankRepository rankRepository;

	@Transactional
	public void save(Rank entity) {
		rankRepository.save(entity);
	}

	public Long countByRank(Rank rank) {
		return rankRepository.countByRank(rank);
	}

}
