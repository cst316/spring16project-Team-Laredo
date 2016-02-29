/**
 * HistoryItem.java
 * Created on 07.03.2003, 18:31:39 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;

/*$Id: HistoryItem.java,v 1.4 2004/10/06 19:15:43 ivanrise Exp $*/
public class HistoryItem {

    private final CalendarDate _date;
    private final Project _project;

    /**
     * Constructor for HistoryItem.
     */
    public HistoryItem(CalendarDate date, Project project) {
        _date = date;
        _project = project;
    }

    public CalendarDate getDate() {
        return _date;
    }

    public Project getProject() {
        return _project;
    }

    public boolean equals(Object i) {
        if (i instanceof HistoryItem) {
            HistoryItem ihi = (HistoryItem) i;
            return ihi.getDate().equals(_date) && ihi.getProject().getID().equals(_project.getID());
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = _date != null ? _date.hashCode() : 0;
        result = 31 * result + (_project != null ? _project.hashCode() : 0);
        return result;
    }
}
