package com.zbank.cache;

import com.zbank.exceptions.BankingException;

public class RedisCache implements Cache {

	@Override
	public Object getValue(Object key) throws BankingException {
		
		return null;
	}

	@Override
	public Object fetchData(Object key) throws BankingException {
		return null;
	}

	@Override
	public void clear() {
		
	}

	@Override
	public void update(Object key, Object vale) throws BankingException {
		
	}

}
