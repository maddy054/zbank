package com.zbank.cache;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;

import com.zbank.exceptions.BankingException;
import com.zbank.models.CacheType;
import com.zbank.persistence.Connector;
import com.zbank.persistence.DbConnector;

public class LruCache<K,V> implements  Cache<K, V>{
	private HashMap<K, V> dataMap;
	private LinkedList< K>  keyList;
	private Connector dbConnector  = new DbConnector();
	private CacheType type;
	private int limit = 10;
	
	public LruCache(CacheType type) {
		this.dataMap = new HashMap<K, V>();
		this.keyList = new LinkedList<K>();
		this.type =  type;
	}

	@Override
	public V getValue(K key) throws BankingException{
		V value;
		if(keyList.contains(key)) {
			keyList.remove(key);
			keyList.addFirst(key);
			value = (V) dataMap.get(key);
			
		}else {
			value = fetchData(key);
			putValue(key,value);
			
		}
		System.out.println("cache"+dataMap);
		System.out.println("list"+keyList);
		return value;
	}

	private void putValue(K key,V value) {
		if(keyList.size()>=limit) {
			dataMap.remove(keyList.removeLast());		
		}
		keyList.addFirst(key);
		dataMap.put(key, value);
		
			
	}

	@SuppressWarnings("unchecked")
	@Override
	public V fetchData(K key) throws BankingException {
		try {
		return	(V) dbConnector.getClass().getDeclaredMethod("get"+type.get(), Integer.class).invoke( dbConnector, key);
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			throw new BankingException(e.getMessage());
		}
		
	}
	public void clear() {
		dataMap.clear();
		keyList.clear();
		
	}

}
