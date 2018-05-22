package com.szl.syj.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 2016/9/8.
 * 处理时间
 */
public class TimeUtils {
    /**
     * @return 当前时间
     */
    public static String now() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return formatter.format(now);
    }

    private TimeUtils() {
    }

    /**
     * 字符串转Date
     *
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date parse(String strDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.parse(strDate.replaceAll("[^0-9]", "").substring(0, 8));
    }
    public static Date parse2(String strDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(strDate.replaceAll("[^0-9]", "").substring(0, 8));
    }

    public static Date stampToDate(String s) throws Exception {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        res = simpleDateFormat.format(lt * 1000L);
        Date date = simpleDateFormat.parse(res);



        return date;
    }
    public static Date parseMonth(String strDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");

        //return formatter.parse(strDate.replaceAll("[^0-9]", "").substring(0, 8));
        Date date = formatter.parse(strDate.replaceAll("[^0-9]", ""));

        return date;
    }
    public static Date parseMonth(String strDate,String structure) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(structure);

        //return formatter.parse(strDate.replaceAll("[^0-9]", "").substring(0, 8));
        Date date = formatter.parse(strDate.replaceAll("[^0-9]", ""));

        return date;
    }

    /**
     * 获取几天前(后)的日期
     *
     * @param d   当前时间
     * @param day 几天前(后)
     * @return 几天前(后)的时间
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
      //  now.get(2);
        now.setTime(d);
        now.set(2,now.get(2)-1);
    //    now.set(Calendar.DATE, now.get(Calendar.DATE).);
        return now.getTime();
    }
}
