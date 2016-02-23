package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Stores defects 
 * 
 * @author Benjamin Paothatat
 * @since 2/9/2016
 */

public class DefectListImpl implements DefectList {
    private Project m_project = null;
    private Document m_document = null;
    private Element m_root = null;
    private ArrayList<Defect> defectList = new ArrayList<>();
    
    /**
     * Constructor that initializes a defect list for a respective project.
     * @param document The document that will be used to initialize the defect list.
     * @param project Project that the defect list is associated with.
     */
    public DefectListImpl(Document document, Project project){
    	m_document = document;
    	m_root = m_document.getRootElement();
    	m_project = project;
    	buildDefectList(m_root);
    }
    
    /**
     * Constructor that initializes a defect list for a respective project that does not exist yet.
     * @param project Project that the defect list is associated with.
     */
    public DefectListImpl(Project project){
    	m_root = new Element("defectlist");
    	m_document = new Document(m_root);
    	m_project = project;
    	buildDefectList(m_root);
    }
    
	public Defect getDefect(int defectNumber) {
		return defectList.get(defectNumber);
	}

	public Collection<Defect> getAllDefects() {
		return defectList;
	}
    
	public int getNumberOfDefects() {
		return defectList.size();
	}
    
	public Project getProject() {
		return m_project;
	}
	
	public Document getXMLContent() {
		return m_document;
	}
	
	/**
	 * See interface for defect list
	 */
	public void addDefect(CalendarDate dateFound, CalendarDate dateRemoved, int phaseOfInjection, int removalPhase,
			int typeOfDefect, String description) {
        Element defect = new Element("defect");
        defect.addAttribute(new Attribute("dateFound", dateFound.toString()));
        defect.addAttribute(new Attribute("dateRemoved", dateRemoved != null? dateRemoved.toString():""));
        defect.addAttribute(new Attribute("defectNumber", String.valueOf(defectList.size())));

        defect.addAttribute(new Attribute("phaseOfInjection", String.valueOf(phaseOfInjection)));
        defect.addAttribute(new Attribute("removalPhase", String.valueOf(removalPhase)));
        defect.addAttribute(new Attribute("typeOfDefect", String.valueOf(typeOfDefect)));
		defect.addAttribute(new Attribute("description", description));
		
		
		m_root.appendChild(defect);
		
		defectList.add(new DefectImpl(defect, this));
	}
	
	/**
	 * See interface for defect list
	 */
	public boolean removeDefect(int defectNumber) {
		boolean removed = false;
		if (defectNumber >= 0 && defectNumber < defectList.size()){
			m_root.removeChild(defectNumber);
            defectList.remove(defectNumber);
            updateDefectNumber(defectNumber);
            removed = true;
		}
	    return removed;
	}  
	
	private void buildDefectList(Element parent){
		Elements defects = parent.getChildElements("defect");
		for(int i = 0; i < defects.size(); i++){
			Element defect = defects.get(i);
			defectList.add(new DefectImpl(defect, this));
		}
	}
	
	private void updateDefectNumber(int defectNumber){
		for(int i = defectNumber; i < defectList.size(); i++){
			Defect defect = (Defect) defectList.toArray()[i];
			defect.setDefectNumber(i);
		}
	}
}
