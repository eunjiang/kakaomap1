package com.keyword.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyword.utils.Utils;

@Service
public class ApiService {

	private static final Logger logger = LoggerFactory.getLogger(ApiService.class);

	private static final String API_REST_API_KEY = "9389b7d9631436f7eb4cf588127be5f1";
	private static final String API_BOOK_URL = "https://dapi.kakao.com/v2/local/search/keyword.json";

//	public Map<String, Object> searchAddress(String searchWord, String target, String category, int page) {
	public Map<String, Object> searchAddress(String searchWord, int page) {
		//
		final String URL = API_BOOK_URL + "?page=" + page;
//				+ "?target=" + target + "&target=" + target + "&category=" + category + "&page="
//				+ page;
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "KakaoAK " + API_REST_API_KEY);
		Map<String, String> params = new HashMap<>();
		params.put("query", searchWord);
		String jsonString = null;
		Map<String, Object> resultData = null;
		try {
			jsonString = Utils.getHttpPOST2String(URL, headers, params, false);
			ObjectMapper mapper = new ObjectMapper();
			resultData = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
			});

			logger.debug(URL + " - get API Info : " + jsonString);
			// resultData = JsonUtils.readJsonToStringObjectUnparse(jsonString);

		} catch (Exception e) {
			logger.info(URL + " - get API Exception : " + jsonString);
			e.printStackTrace();
		}
		return resultData;
	}
}
