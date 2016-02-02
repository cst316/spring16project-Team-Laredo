package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Element;

public interface Defect {
	
	//PSP Phases
    public static final int PLANNING = 0;
    public static final int DESIGN = 1;
    public static final int CODE = 2;
    public static final int REVIEW = 3;
    public static final int COMPILE = 4;
    public static final int TESTING = 5;
    public static final int NO_PHASE = 6;
    
    //Defect Types
    public static final int DOCUMENTATION = 0;
    public static final int SYNTAX = 1;
    public static final int BUILD = 2;
    public static final int ASSIGNMENT = 3;
    public static final int INTERFACE = 4;
    public static final int CHECKING = 5;
    public static final int DATA = 6;
    public static final int FUNCTION = 7;
    public static final int SYSTEM = 8;
    public static final int ENVIROMENT = 9;
    public static final int NON_TYPE = 10;
		 
	int getDefectNumber();
    
    CalendarDate getDateFound();
    void setDateFound(CalendarDate dateFound);
    
    CalendarDate getDateRemoved();
    void setDateRemoved(CalendarDate dateRemoved);
    
    int getPhaseOfInjection();
    void setPhaseOfInjection(int phaseOfInjection);
    
    int getPhaseOfRemoval();
    void setPhaseOfRemoval(int phaseOfRemoval);
    
    boolean isRemoved();
    
    int getTypeOfDefect();
    void setTypeOfDefect(int typeOfDefect);
    
    String getDescription();
    void setDescription(String description);
    
    String toString();
    Element getElement();
}
