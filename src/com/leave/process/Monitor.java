package com.leave.process;

import com.leave.services.LeaveService;
import com.leave.services.LeaveServiceImpl;

public class Monitor extends Thread {

	private LeaveService leaveService = new LeaveServiceImpl();
	
	public Monitor(String threadName) {
		super(threadName);
	}
	
	@Override
	public void run() {
		
		boolean flag = true;
		while(flag) {
			flag = leaveService.monitorLeave();
			try {
				Thread.sleep(60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				flag = false;
			}
		}
	}
}
