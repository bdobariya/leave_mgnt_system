package com.leave.cache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.leave.entities.Leave;
import com.leave.entities.LeaveDetail;

public class DataCache {

	public static final int CACHE_EMPLOYEE_LEAVE_BALANCE_DATA = 1;
	public static final int CACHE_EMPLOYEE_LEAVE_APPLIED_DATA = 2;
	public static final int CACHE_EMPLOYEE_FILE_PATH = 3;
	public static final String CACHE_EMPLOYEE_BALANCE_LEAVE_FILE_NAME = "EmployeeData";
	public static final String CACHE_EMPLOYEE_APPLIED_LEAVE_FILE_NAME = "Leaves";

	private Map<Integer, Object> cacheMap = new HashMap<>();

	protected DataCache() {
		// for removing visibility.
	}

	private boolean ifCacheExists(int i) {
		return cacheMap.get(i) != null;
	}

	protected void clearCache(int i) {
		cacheMap.remove(i);
		cacheMap.put(i, null);
	}

	protected void clearAllCache() {
		cacheMap.clear();
	}

	public Map<String, String> getEmployeeFilePath() {
		if (!ifCacheExists(CACHE_EMPLOYEE_FILE_PATH)) {
			cacheMap.put(CACHE_EMPLOYEE_FILE_PATH, loadFilePath());
		}
		return (Map<String, String>) cacheMap.get(CACHE_EMPLOYEE_FILE_PATH);
	}

	private Map<String, String> loadFilePath() {
		Map<String, String> map = new HashMap<>();
		FileReader reader = null;
		try {
			reader = new FileReader(DataCache.class.getProtectionDomain().getCodeSource().getLocation().getPath()+ "application.properties");
			Properties p = new Properties();
			p.load(reader);
			map.put(CACHE_EMPLOYEE_BALANCE_LEAVE_FILE_NAME, p.getProperty("balance.leave.file.path"));
			map.put(CACHE_EMPLOYEE_APPLIED_LEAVE_FILE_NAME, p.getProperty("applied.leave.file.path"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (reader != null)
					reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public Map<String, LeaveDetail> getEmployeeLeaveBalanceData() {
		if (!ifCacheExists(CACHE_EMPLOYEE_LEAVE_BALANCE_DATA)) {
			cacheMap.put(CACHE_EMPLOYEE_LEAVE_BALANCE_DATA, getEmployeeLeaveData());
		}
		return (Map<String, LeaveDetail>) cacheMap.get(CACHE_EMPLOYEE_LEAVE_BALANCE_DATA);
	}

	private Map<String, LeaveDetail> getEmployeeLeaveData() {
		Map<String, LeaveDetail> leaveBalance = new HashMap<>();
		File file = new File(getEmployeeFilePath().get(CACHE_EMPLOYEE_BALANCE_LEAVE_FILE_NAME));
		if (file.exists()) {
			try (BufferedReader csvReader = new BufferedReader(new FileReader(file));) {
				String row = csvReader.readLine(); // Skip the CSV Header
				while ((row = csvReader.readLine()) != null) {
					String[] data = row.split(",");
					LeaveDetail leaveDetail = new LeaveDetail();
					leaveDetail.setEmpid(data[0]);
					leaveDetail.setName(data[1]);
					leaveDetail.setLeavesTaken(data[2]);
					leaveDetail.setAvailableLeaves(data[3]);
					leaveBalance.put(data[0], leaveDetail);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return leaveBalance;
	}

	public Map<String, Leave> getEmployeeAppliedLeaveData() {
		if (!ifCacheExists(CACHE_EMPLOYEE_LEAVE_APPLIED_DATA)) {
			cacheMap.put(CACHE_EMPLOYEE_LEAVE_APPLIED_DATA, getEmployeeAppliedLeaves());
		}
		return (Map<String, Leave>) cacheMap.get(CACHE_EMPLOYEE_LEAVE_APPLIED_DATA);
	}

	private Map<String, Leave> getEmployeeAppliedLeaves() {
		Map<String, Leave> appliedLeavesMap = new HashMap<>();
		File file = new File(getEmployeeFilePath().get(CACHE_EMPLOYEE_APPLIED_LEAVE_FILE_NAME));
		if (file.exists()) {
			try (BufferedReader csvReader = new BufferedReader(new FileReader(file));) 
			{
				String row = csvReader.readLine(); // Skip the CSV Header
				while ((row = csvReader.readLine()) != null) {
					String[] data = row.split(",");
					Leave leave = new Leave();
					leave.setEmpid(data[0]);
					leave.setAppliedLeaves(data[1]);
					appliedLeavesMap.put(data[0], leave);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return appliedLeavesMap;
	}
}
