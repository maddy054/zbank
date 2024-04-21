package com.zbank.servlet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zbank.exceptions.BankingException;
import com.zbank.logics.ZBank;
import com.zbank.models.OperationLog;

public class LogExecutor {
	
	private static ExecutorService executor = Executors.newFixedThreadPool(10);
	private OperationLog log;
	public void updateLog(OperationLog log) throws BankingException {
		this.log = log;
		Thread.dumpStack();
		System.out.println("main34555555555555555555 thread "+Thread.currentThread().getName());
	//	executor.execute(()-> auditLog());
		executor.execute(this::auditLog);
	}
	
	private void auditLog( ) {
		Thread.dumpStack();
		ZBank zbank = new ZBank();	
		System.out.println("thissuhnrfuerynak thread "+Thread.currentThread().getName());
			try {
				
				zbank.updateLog(log);
			} catch (BankingException e) {
				e.printStackTrace();
			}
			
		}
	
}
