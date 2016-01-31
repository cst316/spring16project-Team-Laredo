/**
 * EventImpl.java
 * Created on 08.03.2003, 13:20:13 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;
import nu.xom.Attribute;
import nu.xom.Element;

/**
 * 
 */
/*$Id: EventImpl.java,v 1.9 2004/10/06 16:00:11 ivanrise Exp $*/
public class EventImpl implements Event {
    
    private Element _elem = null;

    /**
     * Constructor for EventImpl.
     */
    public EventImpl(Element elem) {
        _elem = elem;
    }

   
    /**
     * @see net.sf.memoranda.Event#getStartHour()
     */
    public int getStartHour() {
        return new Integer(_elem.getAttribute("start_hour").getValue());
    }

    /**
     * @see net.sf.memoranda.Event#getStartMinute()
     */
    public int getStartMinute() {
        return new Integer(_elem.getAttribute("start_min").getValue());
    }
    
    public int getEndHour() {
    	return new Integer(_elem.getAttribute("end_hour").getValue());
    }
    
    public int getEndMinute() {
    	return new Integer(_elem.getAttribute("end_minute").getValue());
    }
    
    public int getInteruptTime(){
        	return new Integer(_elem.getAttribute("interupt_time").getValue());
    }
    
    public String getTimeString() {
        return Local.getTimeString(getStartHour(), getStartMinute());
    }
        
  
    /**
     * @see net.sf.memoranda.Event#getText()
     */
    public String getText() {
        return _elem.getValue();
    }

    /**
     * @see net.sf.memoranda.Event#getContent()
     */
    public Element getContent() {
        return _elem;
    }
    /**
     * @see net.sf.memoranda.Event#isRepeatable()
     */
    public boolean isRepeatable() {
        return getStartDate() != null;
    }
    /**
     * @see net.sf.memoranda.Event#getStartDate()
     */
    public CalendarDate getStartDate() {
        Attribute a = _elem.getAttribute("startDate");
        if (a != null) return new CalendarDate(a.getValue());
        return null;
    }
    /**
     * @see net.sf.memoranda.Event#getEndDate()
     */
    public CalendarDate getEndDate() {
        Attribute a = _elem.getAttribute("endDate");
        if (a != null) return new CalendarDate(a.getValue());
        return null;
    }
    /**
     * @see net.sf.memoranda.Event#getPeriod()
     */
    public int getPeriod() {
        Attribute a = _elem.getAttribute("period");
        if (a != null) return new Integer(a.getValue());
        return 0;
    }
    /**
     * @see net.sf.memoranda.Event#getId()
     */
    public String getId() {
        Attribute a = _elem.getAttribute("id");
        if (a != null) return a.getValue();
        return null;
    }
    /**
     * @see net.sf.memoranda.Event#getRepeat()
     */
    public int getRepeat() {
        Attribute a = _elem.getAttribute("repeat-type");
        if (a != null) return new Integer(a.getValue());
        return 0;
    }
    /**
     * @see net.sf.memoranda.Event#getTime()
     */
    public Date getTime() {

		Date d = new Date(); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		Calendar calendar = new GregorianCalendar(Local.getCurrentLocale()); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		calendar.setTime(d); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		calendar.set(Calendar.HOUR_OF_DAY, getStartHour()); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		calendar.set(Calendar.MINUTE, getStartMinute()); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		calendar.set(Calendar.SECOND, 0); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		d = calendar.getTime(); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
        return d;
    }
	
	/**
     * @see net.sf.memoranda.Event#getWorkingDays()
     */
	public boolean getWorkingDays() {
        Attribute a = _elem.getAttribute("workingDays");
        return a != null && a.getValue().equals("true");
    }
	
	public int compareTo(Event o) {
        return (getStartHour() * 60 + getStartMinute()) - (o.getStartHour() * 60 + o.getStartMinute());
	}

}
