package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Document;

public interface DefectList {
    Project getProject();
    
    Defect getDefect(int defectNumber);
    
    void addDefect(CalendarDate dateFound, CalendarDate dateRemoved,
    		String phaseOfInjection, String removalPhase, String typeOfDefect,
    		String description);
    
    void removeDefect(Defect defect);

    Document getXMLContent();
}
