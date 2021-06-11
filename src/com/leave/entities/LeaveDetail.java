package com.leave.entities;

public class LeaveDetail
{
	private String empid;

	private String name;

	private String leavesTaken;

	private String availableLeaves;

	public String getEmpid()
	{
		return empid;
	}

	public void setEmpid(String empid)
	{
		this.empid = empid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getLeavesTaken()
	{
		return leavesTaken;
	}

	public void setLeavesTaken(String leavesTaken)
	{
		this.leavesTaken = leavesTaken;
	}

	public String getAvailableLeaves()
	{
		return availableLeaves;
	}

	public void setAvailableLeaves(String availableLeaves)
	{
		this.availableLeaves = availableLeaves;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((availableLeaves == null) ? 0 : availableLeaves.hashCode());
		result = prime * result + ((empid == null) ? 0 : empid.hashCode());
		result = prime * result + ((leavesTaken == null) ? 0 : leavesTaken.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LeaveDetail other = (LeaveDetail) obj;
		if (availableLeaves == null)
		{
			if (other.availableLeaves != null)
				return false;
		}
		else if (!availableLeaves.equals(other.availableLeaves))
			return false;
		if (empid == null)
		{
			if (other.empid != null)
				return false;
		}
		else if (!empid.equals(other.empid))
			return false;
		if (leavesTaken == null)
		{
			if (other.leavesTaken != null)
				return false;
		}
		else if (!leavesTaken.equals(other.leavesTaken))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "LeaveDetail [empid=" + empid + ", name=" + name + ", leavesTaken=" + leavesTaken + ", availableLeaves=" + availableLeaves + "]";
	}

	
}
