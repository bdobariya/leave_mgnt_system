package com.leave.cache;

public class CacheManagement extends DataCache
{
	private CacheManagement()
	{
	}

	private static CacheManagement cMang = null;
	public static synchronized CacheManagement getInstance()
	{
		if (cMang == null)
		{
			cMang = new CacheManagement();
		}
		return cMang;
	}

	public void removeCache(int cacheType)
	{
		clearCache(cacheType);
	}
	
	public void removeAllCache() {
		clearAllCache();
	}
	
}
