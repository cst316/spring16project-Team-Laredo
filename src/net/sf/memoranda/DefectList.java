package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Document;

import java.util.Collection;

/**
 * Stores defects 
 * 
 * @author Benjamin Paothatat
 * @since 2/9/2016
 */
public interface DefectList {
    Project getProject();
    
    Defect getDefect(int defectNumber);
    
    Document getXMLContent();
    
    int getNumberOfDefects();
    Collection<Defect> getAllDefects();
    
    /**
     * Adds a defect to the defect list.
     * @param dateFound Date that the defect was found.
     * @param dateRemoved Date that the defect was removed.
     * @param phaseOfInjection Phase that defect was injected.
     * @param removalPhase Phase that defect was removed.
     * @param typeOfDefect Type of defect.
     * @param description Description of defect.
     */
    void addDefect(CalendarDate dateFound, CalendarDate dateRemoved,
    		int phaseOfInjection, int removalPhase, int typeOfDefect,
    		String description);
    
    /**
     * Removes defect from the defect list.
     * @param defectNumber Defect number that will be removed.
     * @return True, unless defect is not found, then false is returned.
     */
    boolean removeDefect(int defectNumber);
}
