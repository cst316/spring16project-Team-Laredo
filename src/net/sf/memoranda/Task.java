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
    public static final int SCHEDULED = 0;
    public static final int ACTIVE = 1;
    public static final int COMPLETED = 2;
    
    public static final int FROZEN = 4;
    public static final int FAILED = 5;
    public static final int LOCKED = 6;
    public static final int DEADLINE = 7;
    
    //Priority
    public static final int PRIORITY_LOWEST = 0;
    public static final int PRIORITY_LOW = 1;
    public static final int PRIORITY_NORMAL = 2;  
    public static final int PRIORITY_HIGH = 3;  
    public static final int PRIORITY_HIGHEST = 4;
    
    //Phase
    public static final int PLANNING = 0;
    public static final int DESIGN = 1;
    public static final int CODE = 2;
    public static final int REVIEW = 3;
    public static final int COMPILE = 4;
    public static final int TESTING = 5;
    public static final int POSTMORTEM = 6;
    public static final int NO_PHASE = 7;
    
    
    
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
    
    /*Collection getDependsFrom();
    
    void addDependsFrom(Task task);
    
    void removeDependsFrom(Task task);*/
            
    Collection getSubTasks();    
    Task getSubTask(String id);
    
    boolean hasSubTasks(String id);
    
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
}
