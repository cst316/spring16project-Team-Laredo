package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Document;

public interface DefectList {
    Project getProject();
    
    Defect getDefect(int defectNumber);
    
    void addDefect(CalendarDate dateFound, CalendarDate dateRemoved,
    		int phaseOfInjection, int removalPhase, int typeOfDefect,
    		String description);
    
    void removeDefect(int defectNumber);

    Document getXMLContent();
}
