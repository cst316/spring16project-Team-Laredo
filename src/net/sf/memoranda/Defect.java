package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Element;

public interface Defect {
	
	//PSP Phases
    public static final int PLANNING = 1;
    public static final int DESIGN = 2;
    public static final int CODE = 3;
    public static final int REVIEW = 4;
    public static final int COMPILE = 5;
    public static final int TESTING = 6;
    public static final int NO_PHASE = 0;
    
    //Defect Types
    public static final int DOCUMENTATION = 1;
    public static final int SYNTAX = 2;
    public static final int BUILD = 3;
    public static final int ASSIGNMENT = 4;
    public static final int INTERFACE = 5;
    public static final int CHECKING = 6;
    public static final int DATA = 7;
    public static final int FUNCTION = 8;
    public static final int SYSTEM = 9;
    public static final int ENVIROMENT = 10;
    public static final int NON_TYPE = 0;
		 
	int getDefectNumber();
	void setDefectNumber(int i);
    
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
