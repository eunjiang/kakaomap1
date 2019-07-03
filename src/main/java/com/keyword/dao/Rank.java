package com.keyword.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rank")
public class Rank {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String search_word;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "rank_search_word")
	private Rank rank;

	
	public Rank() {
		super();
	}

	public Rank(String search_word) {
		super();
		this.search_word = search_word;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSearch_word() {
		return search_word;
	}

	public void setSearch_word(String search_word) {
		this.search_word = search_word;
	}
}
