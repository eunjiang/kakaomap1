package com.keyword.dao;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RankRepository extends JpaRepository<Rank, Integer> {
    Long countByRank(Rank rank);
}