package net.sf.memoranda;

import java.util.Hashtable;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;

public class DefectListImpl implements DefectList{
	
    private Project _project = null;
    private Document _doc = null;
    private Element _root = null;
    private int numberOfDefects = 0;
    
   // private Hashtable<Integer, Element> elements = new Hashtable<Integer, Element>();
    
    public DefectListImpl(Document doc, Project prj){
    	_doc = doc;
    	_root = _doc.getRootElement();
    	_project = prj;
    }
    
    public DefectListImpl(Project prj){
    	_root = new Element("defectlist");
    	_doc = new Document(_root);
        _project = prj;
    }
    
    @Override
    public Project getProject(){
    	return _project;
    }

	@Override
	public Defect getDefect(int defectNumber) {
		return new DefectImpl(getDefectElement(defectNumber));
	}

	private Element getDefectElement(int defectNumber) {
		Element result = null;
		Element header = (Element)_doc.getChild(0); 
		if(header.getChildCount() > defectNumber - 1){
		    result = (Element)header.getChild(defectNumber - 1); 
		}
		return result;
	}

	@Override
	public void addDefect(CalendarDate dateFound, CalendarDate dateRemoved, int phaseOfInjection,
			int removalPhase, int typeOfDefect, String description) {
		numberOfDefects++;
		Element defect = new Element("defect");
		defect.addAttribute(new Attribute("defectNumber", String.valueOf(numberOfDefects)));
		defect.addAttribute(new Attribute("dateFound", dateFound.toString()));
		defect.addAttribute(new Attribute("dateRemoved", dateRemoved.toString()));
		defect.addAttribute(new Attribute("phaseOfInjection", String.valueOf(phaseOfInjection)));
		defect.addAttribute(new Attribute("removalPhase", String.valueOf(removalPhase)));
		defect.addAttribute(new Attribute("typeOfDefect", String.valueOf(typeOfDefect)));
		defect.addAttribute(new Attribute("description", description));
		_root.appendChild(defect);
	}

	@Override
	public void removeDefect(int defectNumber) {
		try{
			_doc.getChild(0).getChild(defectNumber - 1);
			
			for(int i = defectNumber - 1; i < _doc.getChild(0).getChildCount(); i++){
				Attribute attr = ((Element)_doc.getChild(0).getChild(i)).getAttribute("defectNumber");
				attr.setValue(String.valueOf((i)));
			}
			defectNumber--;
			
			_doc.getChild(0).getChild(defectNumber - 1).detach();
			
		}
		catch(Exception e){
			
		}
		
	}

	@Override
	public Document getXMLContent() {
		return _doc;
	}
}
