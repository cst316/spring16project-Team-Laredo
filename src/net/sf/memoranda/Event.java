/**
 * Event.java
 * Created on 08.03.2003, 12:21:40 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 *-----------------------------------------------------
 */
package net.sf.memoranda;
import net.sf.memoranda.date.CalendarDate;

import java.util.Date;

/**
 *
 */
/*$Id: Event.java,v 1.4 2004/07/21 17:51:25 ivanrise Exp $*/
public interface Event extends Comparable<Event> {

    String getId();

    int getStartHour();
    int getStartMinute();

    int getEndHour();
    int getEndMinute();

    /**
     * Format the event duration as a String with format "HH:MM".
     *
     * @return Duration as a "HH:MM" String.
     */
    String getDurationString();

    /**
     * Calculate the number of minutes in an Event's duration.
     *
     * @return Number of minutes in the duration.
     */
    int getDurationMinutes();

    String getText();

    nu.xom.Element getContent();

    int getRepeat();

    CalendarDate getStartDate();
    CalendarDate getEndDate();
    int getPeriod();
    boolean isRepeatable();

    Date getTime();
    String getTimeString();

	boolean getWorkingDays();

}
