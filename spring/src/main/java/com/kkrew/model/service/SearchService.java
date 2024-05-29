package com.kkrew.model.service;

import java.util.List;

public interface SearchService {
	List searchData(String key, String word);
	List searchMyData(String id, String table);
}
