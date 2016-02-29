/**
 * CalendarDate.java
 * Created on 11.02.2003, 18:02:02 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.date;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */
/*$Id: CalendarDate.java,v 1.3 2004/01/30 12:17:41 alexeya Exp $*/
public class CalendarDate {

    private final int _year;
    private final int _month;
    private final int _day;

    public CalendarDate() {
        this(Calendar.getInstance());
    }

    public CalendarDate(int day, int month, int year) {
        _year = year;
        _month = month;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, _year);
        cal.set(Calendar.MONTH, _month);
        cal.getTime();
        int dmax = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (day <= dmax)
            _day = day;
        else
            _day = dmax;

    }

    public CalendarDate(Calendar cal) {
        _year = cal.get(Calendar.YEAR);
        _day = cal.get(Calendar.DAY_OF_MONTH);
        _month = cal.get(Calendar.MONTH);
    }

    public CalendarDate(Date date) {
        this(dateToCalendar(date));
    }

    public CalendarDate(String date) {
        int[] d = Util.parseDateStamp(date);
        _day = d[0];
        _month = d[1];
        _year = d[2];

    }

    private static Calendar dateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static CalendarDate today() {
        return new CalendarDate();
    }

    public static CalendarDate tomorrow() {
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.DATE, true);
        return new CalendarDate(cal);
    }

    private static Calendar toCalendar(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.getTime();
        return cal;
    }

    private static Date toDate(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    public Calendar getCalendar() {
        return toCalendar(_day, _month, _year);
    }

    public Date getDate() {
        return toDate(_day, _month, _year);
    }

    public int getDay() {
        return _day;
    }

    public int getMonth() {
        return _month;
    }

    public int getYear() {
        return _year;
    }

    // not sure why findbugs thinks this is so awful
    @SuppressFBWarnings("EQ_CHECK_FOR_OPERAND_NOT_COMPATIBLE_WITH_THIS")
    public boolean equals(Object object) {
        if (object instanceof CalendarDate) {
            CalendarDate d2 = (CalendarDate) object;
            return hashCode() == d2.hashCode();
        } else if (object instanceof Calendar) {
            Calendar cal = (Calendar) object;
            return this.equals(new CalendarDate(cal));
        } else if (object instanceof Date) {
            Date d = (Date) object;
            return this.equals(new CalendarDate(d));
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = _year;
        result = 31 * result + _month;
        result = 31 * result + _day;
        return result;
    }

    public boolean equals(CalendarDate date) {
        return date != null && ((date.getDay() == getDay()) && (date.getMonth() == getMonth()) && (date.getYear() == getYear()));
    }

    public boolean before(CalendarDate date) {
        return date == null || this.getCalendar().before(date.getCalendar());
    }

    public boolean after(CalendarDate date) {
        return date == null || this.getCalendar().after(date.getCalendar());
    }

    public boolean inPeriod(CalendarDate startDate, CalendarDate endDate) {
        return (after(startDate) && before(endDate)) || equals(startDate) || equals(endDate);
    }

    public String toString() {
        return Util.getDateStamp(this);
    }

    public String getFullDateString() {
        return Local.getDateString(this, DateFormat.FULL);
    }

    public String getMediumDateString() {
        return Local.getDateString(this, DateFormat.MEDIUM);
    }

    public String getLongDateString() {
        return Local.getDateString(this, DateFormat.LONG);
    }

    public String getShortDateString() {
        return Local.getDateString(this, DateFormat.SHORT);
    }
}
