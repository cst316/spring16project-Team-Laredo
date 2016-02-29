/**
 * Task.java
 * Created on 11.02.2003, 16:39:13 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;

import java.util.Collection;
import java.util.Observer;

/**
 *
 */
/*$Id: Task.java,v 1.9 2005/06/16 04:21:32 alexeya Exp $*/
public interface Task extends Observer {

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

    long getEstEffort();

    void setEstEffort(long effort);

    long getActEffort();

    void setActEffort(long effort);

    String getDescription();

    void setDescription(String description);

    Task getParentTask();

    String getParentId();

    void freeze();

    void unfreeze();

    long getRate();

    nu.xom.Element getContent();

    int getLocBase();

    void setLocBase(int loc);

    void addLocBase(int loc);

    int getLocAdded();

    void setLocAdded(int loc);

    void addLocAdded(int loc);

    int getLocModified();

    void setLocModified(int loc);

    void addLocModified(int loc);

    int getLocDeleted();

    void setLocDeleted(int loc);

    void addLocDeleted(int loc);

    int getLocNewAndChanged();

    void setLocNewAndChanged(int loc);

    int getLocReused();

    void setLocReused(int loc);

    void addLocReused(int loc);

    int getLocNewReuse();

    void setLocNewReuse(int loc);

    void addLocNewReuse(int loc);

    int getLocTotal();

    void setLocTotal(int loc);

    void registerObservers();
}
