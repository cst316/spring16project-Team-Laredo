package net.sf.memoranda;

import java.util.Hashtable;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

/**
 * Stores defects 
 * 
 * @author Benjamin Paothatat
 * @since 2/9/2016
 */

public class DefectListImpl implements DefectList{
	private int m_numberOfDefects = 0;
    private Project m_project = null;
    private Document m_document = null;
    private Element m_root = null;
    private Hashtable<String, Element> defectList = new Hashtable<String, Element>();
    
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
    	m_numberOfDefects = m_root.getChildCount();
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
		Element element = (Element)defectList.get(String.valueOf(defectNumber));
		return new DefectImpl(element, this);
	}
    
	public int getNumberOfDefects() {
		return m_numberOfDefects;
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
		m_numberOfDefects++;
        Element defect = new Element("defect");
        defect.addAttribute(new Attribute("dateFound", dateFound.toString()));
        defect.addAttribute(new Attribute("dateRemoved", dateRemoved != null? dateRemoved.toString():""));
        defect.addAttribute(new Attribute("defectNumber", String.valueOf(m_numberOfDefects)));
        defect.addAttribute(new Attribute("phaseOfInjection", String.valueOf(phaseOfInjection)));
        defect.addAttribute(new Attribute("removalPhase", String.valueOf(removalPhase)));
        defect.addAttribute(new Attribute("typeOfDefect", String.valueOf(typeOfDefect)));
		defect.addAttribute(new Attribute("description", description));
		
		
		m_root.appendChild(defect);
		defectList.put(String.valueOf(m_numberOfDefects), defect);
	}
	
	/**
	 * See interface for defect list
	 */
	public boolean removeDefect(int defectNumber) {
		boolean removed = false;
		if(defectList.containsKey(String.valueOf(defectNumber))){
			m_numberOfDefects--;
            for(int i = defectNumber; i < defectList.size(); i++){
				Element defect = (Element) m_root.getChild(i);
				Attribute defectNum = defect.getAttribute("defectNumber");
				defectNum.setValue(String.valueOf(i));
				defectList.put(String.valueOf(i), defect);
			}
            m_root.removeChild(defectNumber - 1);
            defectList.remove(String.valueOf(defectList.size()));
            removed = true;
		}
	    return removed;
	}  
	
	private void buildDefectList(Element parent){
		Elements defects = parent.getChildElements("defect");
		for(int i = 0; i < defects.size(); i++){
			Element defect = defects.get(i);
			defectList.put(defect.getAttributeValue("defectNumber"), defect);
			buildDefectList(defect);
		}
	}
}
