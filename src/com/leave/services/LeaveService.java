package com.leave.services;

import java.util.Map;

import com.leave.entities.LeaveDetail;

public interface LeaveService
{
	public boolean monitorLeave();
	
	public void updateLeaveBalance(boolean isLeaveBalanceUpdate, Map<String, LeaveDetail> leaveBalanceMap);
	
	public void updateAppliedLeaves(boolean isAppend, String empId, String appliedLeave);
}
