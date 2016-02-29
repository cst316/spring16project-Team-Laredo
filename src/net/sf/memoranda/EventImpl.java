/**
 * EventImpl.java
 * Created on 08.03.2003, 13:20:13 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;
import nu.xom.Attribute;
import nu.xom.Element;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*$Id: EventImpl.java,v 1.9 2004/10/06 16:00:11 ivanrise Exp $*/
public class EventImpl implements Event, Comparable<Event> {

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
        Attribute start_hour_attr = _elem.getAttribute("start_hour");
        if (start_hour_attr != null) {
            return Integer.parseInt(start_hour_attr.getValue());
        } else {
            return 0;
        }
    }

    /**
     * @see net.sf.memoranda.Event#getStartMinute()
     */
    public int getStartMinute() {
        Attribute start_min_attr = _elem.getAttribute("start_min");
        if (start_min_attr != null) {
            return Integer.parseInt(start_min_attr.getValue());
        } else {
            return 0;
        }
    }

    public int getEndHour() {
        try {
            return Integer.parseInt(_elem.getAttribute("end_hour").getValue());
        } catch (NumberFormatException e) {
            return 0;
        } catch (NullPointerException npe) {
            return 0;
        }
    }

    public int getEndMinute() {
        try {
            return Integer.parseInt(_elem.getAttribute("end_minute").getValue());
        } catch (NumberFormatException e) {
            return 0;
        } catch (NullPointerException npe) {
            return 0;
        }
    }

    /**
     * Format the event duration as a String with format "HH:MM".
     *
     * @return Duration as a "HH:MM" String.
     */
    public String getDurationString() {
        int minutes = this.getDurationMinutes();
        int hours = minutes / 60;
        minutes = minutes % 60;

        if (minutes <= 0 && hours <= 0) {
            return "";
        }

        StringBuilder build = new StringBuilder();
        build.append(hours);
        build.append(':');
        if (minutes < 10) build.append('0');
        build.append(minutes);

        return build.toString();
    }

    /**
     * Calculate the number of minutes in an EventImpl's duration.
     *
     * @return Number of minutes in the duration.
     */
    public int getDurationMinutes() {
        int startHour = this.getStartHour();
        int startMinute = this.getStartMinute();

        int endHour = this.getEndHour();
        int endMinute = this.getEndMinute();

        // if they're both 0, then this event does not have a duration
        if (endHour == 0 && endMinute == 0) {
            return 0;
        }

        // account for carrying an hour into the minutes
        if (startMinute > endMinute && endHour > 0) {
            endHour -= 1;
            endMinute += 60;
        } else if (startMinute > endMinute) { // shouldn't happen
            return 0;
        }

        int hours = endHour - startHour;
        int minutes = endMinute - startMinute;

        // good catch Ben! need to handle negative hours
        if (hours < 0 || minutes < 0) {
            return 0;
        }

        return (hours * 60) + minutes;
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
        if (a != null) return Integer.parseInt(a.getValue());
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
        if (a != null) return Integer.parseInt(a.getValue());
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
