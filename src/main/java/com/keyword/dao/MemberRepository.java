package com.keyword.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
	default Member findOne(String account) { 
        return (Member) findById(account).orElse(null); 
    }



}
