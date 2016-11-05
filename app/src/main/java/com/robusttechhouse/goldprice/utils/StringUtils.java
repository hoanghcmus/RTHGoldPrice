package com.robusttechhouse.goldprice.utils;

/**
 * App's string utilities
 *
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public class StringUtils {

    /**
     * @param year value got from {@link java.util.Calendar#YEAR}
     * @return Year string
     */
    public static String getYearString(int year) {
        return String.valueOf(year);
    }

    /**
     * @param month value got from {@link java.util.Calendar#MONTH}
     * @return Month string
     */
    public static String getMonthString(int month) {
        switch (month) {
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November";
            case 11:
                return "December";
            default:
                return "December";
        }
    }

    /**
     * @param dayOfMonth value got from {@link java.util.Calendar#DATE}
     * @return Day of month string
     */
    public static String getDayOfMonthString(int dayOfMonth) {
        switch (dayOfMonth) {
            case 1:
                return "01";
            case 2:
                return "02";
            case 3:
                return "03";
            case 4:
                return "04";
            case 5:
                return "05";
            case 6:
                return "06";
            case 7:
                return "07";
            case 8:
                return "08";
            case 9:
                return "09";
            default:
                return String.valueOf(dayOfMonth);
        }
    }

    /**
     * @param dayOfWeek value got from {@link java.util.Calendar#DAY_OF_WEEK}
     * @return Day of week string
     */
    public static String getDayOfWeekString(int dayOfWeek) {
        switch (dayOfWeek) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
            default:
                return String.valueOf(dayOfWeek);
        }
    }
}
