package com.zbank.servlet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;

import com.zbank.exceptions.BankingException;
import com.zbank.logics.ZBank;
import com.zbank.models.OperationLog;

public class LogExecutor {
						
	private static ExecutorService executor = Executors.newFixedThreadPool(10);
	private OperationLog log;
	
	public void updateLog(OperationLog log) throws BankingException {
		this.log = log;
		executor.execute(this::auditLog);
	}
	
	private void auditLog( ) {

		ZBank zbank = new ZBank();	
			try {
			
				zbank.updateLog(log);
			} catch (BankingException e) {
				
				e.printStackTrace();
			}
			
		}
	
}
