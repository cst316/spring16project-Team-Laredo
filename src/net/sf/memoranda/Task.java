/**
 * Task.java
 * Created on 11.02.2003, 16:39:13 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.util.Collection;

import net.sf.memoranda.date.CalendarDate;

/**
 *
 */
/*$Id: Task.java,v 1.9 2005/06/16 04:21:32 alexeya Exp $*/
public interface Task {

    //Status
    int SCHEDULED = 0;
    int ACTIVE = 1;
    int COMPLETED = 2;

    int FROZEN = 4;
    int FAILED = 5;
    int LOCKED = 6;
    int DEADLINE = 7;

    //Priority
    int PRIORITY_LOWEST = 0;
    int PRIORITY_LOW = 1;
    int PRIORITY_NORMAL = 2;
    int PRIORITY_HIGH = 3;
    int PRIORITY_HIGHEST = 4;

    //Phase
    int PLANNING = 0;
    int DESIGN = 1;
    int CODE = 2;
    int REVIEW = 3;
    int COMPILE = 4;
    int TESTING = 5;
    int POSTMORTEM = 6;
    int NO_PHASE = 7;

    CalendarDate getStartDate();

    void setStartDate(CalendarDate date);

    CalendarDate getEndDate();

    void setEndDate(CalendarDate date);

    int getStatus(CalendarDate date);

    int getProgress();

    void setProgress(int p);

    int getPriority();

    void setPriority(int p);

    int getPhase();

    void setPhase(int p);

    String getID();

    String getText();

    void setText(String s);

    Collection getSubTasks();

    void setEstEffort(long effort);

    long getEstEffort();

    void setActEffort(long effort);

    long getActEffort();

    void setDescription(String description);

    String getDescription();

    Task getParentTask();

    String getParentId();

    void freeze();

    void unfreeze();

    long getRate();

    nu.xom.Element getContent();

    int getLocBase();

    int getLocAdded();

    int getLocModified();

    int getLocDeleted();

    int getLocNewAndChanged();

    int getLocReused();

    int getLocNewReuse();

    int getLocTotal();

    void setLocBase(int loc);

    void setLocAdded(int loc);

    void setLocModified(int loc);

    void setLocDeleted(int loc);

    void setLocNewAndChanged(int loc);

    void setLocReused(int loc);

    void setLocNewReuse(int loc);

    void setLocTotal(int loc);
}
