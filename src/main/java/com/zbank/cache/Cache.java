package com.zbank.cache;

import com.zbank.exceptions.BankingException;

 public interface Cache<K,V> {
	
	public V getValue(K key) throws BankingException;
	
	public V fetchData(K key) throws BankingException ;

	public void clear();


	void update(K key, V vale) throws BankingException;
}
