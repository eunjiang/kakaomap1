package com.keyword;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.keyword.dao.Member;
import com.keyword.dao.MemberRepository;

public class App implements CommandLineRunner {

	@Autowired
	MemberRepository memberRepository;

	@Override
	public void run(String... arg0) throws Exception {
		//
		Member member = memberRepository.save(new Member("aaa", "ddd", Timestamp.valueOf(LocalDateTime.now())));

		System.out.println("result=" + member);

	}

}