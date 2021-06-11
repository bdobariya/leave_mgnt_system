package com.leave.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.leave.cache.CacheManagement;
import com.leave.entities.LeaveDetail;
import com.leave.services.LeaveService;
import com.leave.services.LeaveServiceImpl;

@WebServlet(name = "LeaveMgntController", urlPatterns = {"/LeaveMgntController"})
public class LeaveMgntController extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private LeaveService leaveService = new LeaveServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String, LeaveDetail> leaveBalanceMap = CacheManagement.getInstance().getEmployeeLeaveBalanceData();
		response.setContentType("application/json");
		Gson json = new Gson();
		String operation = request.getParameter("operation");
		if(operation.equals("all"))
		{
			response.getWriter().write(json.toJson(leaveBalanceMap));
		}
		else
		{
			String empId = request.getParameter("id");
			response.getWriter().write(json.toJson(leaveBalanceMap.get(empId)));
		}
		response.getWriter().flush();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String empId = request.getParameter("empId");
		String appliedLeave = request.getParameter("noOfDays");
		
		leaveService.updateAppliedLeaves(true, empId, appliedLeave);
		
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		out.println("<font style=\"font-weight: bold;\">Successfully applied leave.</font><br><br>");
		rd.include(request, response);
	}

}
