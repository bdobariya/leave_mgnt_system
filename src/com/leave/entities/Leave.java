package com.leave.entities;

public class Leave
{
	private String empid;

	private String appliedLeaves;

	public String getEmpid()
	{
		return empid;
	}

	public void setEmpid(String empid)
	{
		this.empid = empid;
	}

	public String getAppliedLeaves()
	{
		return appliedLeaves;
	}

	public void setAppliedLeaves(String appliedLeaves)
	{
		this.appliedLeaves = appliedLeaves;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appliedLeaves == null) ? 0 : appliedLeaves.hashCode());
		result = prime * result + ((empid == null) ? 0 : empid.hashCode());
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
		Leave other = (Leave) obj;
		if (appliedLeaves == null)
		{
			if (other.appliedLeaves != null)
				return false;
		}
		else if (!appliedLeaves.equals(other.appliedLeaves))
			return false;
		if (empid == null)
		{
			if (other.empid != null)
				return false;
		}
		else if (!empid.equals(other.empid))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Leave [empid=" + empid + ", appliedLeaves=" + appliedLeaves + "]";
	}

}
