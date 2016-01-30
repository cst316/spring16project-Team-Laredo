package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;

public interface Defect {
	int getDefectNumber();
    
    CalendarDate getDateFound();
    void setDateFound(CalendarDate dateFound);
    
    CalendarDate getDateRemoved();
    void setDateRemoved(CalendarDate dateRemoved);
    
    String getPhaseOfInjection();
    void setPhaseOfInjection(String phaseOfInjection);
    
    String getPhaseOfRemoval();
    void setPhaseOfRemoval(String phaseOfRemoval);
    
    boolean isRemoved();
    
    String typeOfDefect();
    void setTypeOfDefect(String typeOfDefect);
    
    String getDescription();
    void setDescription(String description);
    
    String toString();
}
