package com.leave.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import com.leave.cache.CacheManagement;
import com.leave.cache.DataCache;
import com.leave.entities.Leave;
import com.leave.entities.LeaveDetail;

public class LeaveServiceImpl implements LeaveService {

	private static final String CSV_SEPARATOR = ",";

	@Override
	public boolean monitorLeave() {
		try {
			Map<String, LeaveDetail> leaveBalanceMap = CacheManagement.getInstance().getEmployeeLeaveBalanceData();
			Map<String, Leave> appliedLeaveMap = CacheManagement.getInstance().getEmployeeAppliedLeaveData();

			boolean isLeaveBalanceUpdate = false;
			if (!appliedLeaveMap.isEmpty()) {
				for (Map.Entry<String, Leave> entry : appliedLeaveMap.entrySet()) {
					String empId = entry.getKey();
					int appliedLeave = Integer.parseInt(entry.getValue().getAppliedLeaves());

					LeaveDetail leaveBalance = leaveBalanceMap.get(empId);
					if (leaveBalance != null) {
						int availableLeaves = Integer.parseInt(leaveBalance.getAvailableLeaves());

						if (appliedLeave <= availableLeaves) {
							System.out.println(leaveBalance.getName() + " is eligible for the leave.");

							int newLeavesTaken = Integer.parseInt(leaveBalance.getLeavesTaken()) + appliedLeave;
							int newAvailableLeaves = availableLeaves - appliedLeave;

							LeaveDetail newLeaveDetail = new LeaveDetail();
							newLeaveDetail.setEmpid(empId);
							newLeaveDetail.setName(leaveBalance.getName());
							newLeaveDetail.setLeavesTaken(String.valueOf(newLeavesTaken));
							newLeaveDetail.setAvailableLeaves(String.valueOf(newAvailableLeaves));

							leaveBalanceMap.remove(empId);
							leaveBalanceMap.put(empId, newLeaveDetail);
							isLeaveBalanceUpdate = true;
						} else {
							System.out.println(leaveBalance.getName() + " is not eligible for the leave.");
						}
					} else {
						System.out.println("Employee ID " + empId + " not found.");
					}
				}

				updateLeaveBalance(isLeaveBalanceUpdate, leaveBalanceMap);
				updateAppliedLeaves(false, "", "");
			} else {
				System.out.println("There is no any leave applied by an any employee.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void updateLeaveBalance(boolean isLeaveBalanceUpdate, Map<String, LeaveDetail> leaveBalanceMap) {

		if (isLeaveBalanceUpdate && !leaveBalanceMap.isEmpty()) 
		{
			File file = new File(CacheManagement.getInstance().getEmployeeFilePath().get(DataCache.CACHE_EMPLOYEE_BALANCE_LEAVE_FILE_NAME));
			try (
					FileWriter fstream = new FileWriter(file);
					BufferedWriter bw = new BufferedWriter(fstream);
				)
			{
				StringBuffer headerLine = new StringBuffer();
				headerLine.append("empId");
				headerLine.append(CSV_SEPARATOR);
				headerLine.append("name");
				headerLine.append(CSV_SEPARATOR);
				headerLine.append("leavesTaken");
				headerLine.append(CSV_SEPARATOR);
				headerLine.append("availableLeaves");
				headerLine.append(CSV_SEPARATOR);
				bw.write(headerLine.toString());
				bw.newLine();

				for (Map.Entry<String, LeaveDetail> entry : leaveBalanceMap.entrySet()) {
					StringBuffer line = new StringBuffer();
					line.append(entry.getKey());
					line.append(CSV_SEPARATOR);
					line.append(entry.getValue().getName());
					line.append(CSV_SEPARATOR);
					line.append(entry.getValue().getLeavesTaken());
					line.append(CSV_SEPARATOR);
					line.append(entry.getValue().getAvailableLeaves());
					line.append(CSV_SEPARATOR);
					bw.write(line.toString());
					bw.newLine();
				}
				CacheManagement.getInstance().removeCache(DataCache.CACHE_EMPLOYEE_LEAVE_BALANCE_DATA);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateAppliedLeaves(boolean isAppend, String empId, String appliedLeave) {

		File file = new File(CacheManagement.getInstance().getEmployeeFilePath()
				.get(DataCache.CACHE_EMPLOYEE_APPLIED_LEAVE_FILE_NAME));
		try (FileWriter fstream = new FileWriter(file, isAppend); BufferedWriter bw = new BufferedWriter(fstream);) {
			if (!isAppend) {
				StringBuffer headerLine = new StringBuffer();
				headerLine.append("empId");
				headerLine.append(CSV_SEPARATOR);
				headerLine.append("appliedLeaves");
				headerLine.append(CSV_SEPARATOR);
				bw.write(headerLine.toString());
				bw.newLine();
			} else {
				StringBuffer line = new StringBuffer();
				line.append(empId);
				line.append(CSV_SEPARATOR);
				line.append(appliedLeave);
				line.append(CSV_SEPARATOR);
				bw.write(line.toString());
				bw.newLine();
			}
			CacheManagement.getInstance().removeCache(DataCache.CACHE_EMPLOYEE_LEAVE_APPLIED_DATA);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
