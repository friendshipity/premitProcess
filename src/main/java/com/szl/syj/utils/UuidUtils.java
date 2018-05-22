package com.szl.syj.utils;



import java.util.UUID;

/**
 * Title: 通用工具
 * <p>Description: 生成唯一主键的工具
 * 该类采用了Java的UUID方式生成
 * <p>Copyright: 2016-08-23
 * <p>Company: 数之联
 * @author lijl
 */
public class UuidUtils {
	/**
	 * 得到数据类型为long的唯一标识值
	 * @return 唯一long型的的主键ID
	 */
	public static Long getUUIDLong() {
		UUID id = UUID.randomUUID();
		return id.getMostSignificantBits();
	}
	
	/**
	 * 得到数据类型为string的唯一标识值
	 * @return 唯一string型的的主键ID
	 */
	public static String getUUIDStr() {
		return getUUIDLong().toString();
	}
	
	/**
	 * 得到数据类型为long的唯一标识值
	 * @return 唯一long型的的主键ID
	 */
	public static String getUUIDStr128() {
		UUID id = UUID.randomUUID();
		return id.toString();
	}
	
	/**
	 * 得到数据类型为string的唯一标识值
	 * @return 唯一string型的的主键ID
	 */
	public static String getID16() {
		String s=String.format("%1$016x",getUUIDLong());
		return s;
	}
	
	/**
	 * 得到数据类型为Long的正整数类型
	 * @return
	 */
	public static Long getAbsUUIDLong() {
		Long r = getUUIDLong();
		if (r < 0) {
			r=Math.abs(r);
		}
		return r;
	}
	
	/**
	 * 得到数据类型为String的正整数类型
	 * @return
	 */
	public static String getAbsUUIDStr() {
		Long r = getAbsUUIDLong();
		return r.toString();
	}
}