package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Attribute;
import nu.xom.Element;

public class DefectImpl implements Defect {
	
	private Element _element = null;

	public DefectImpl(Element element){
	  _element = element;	
	}
	
	@Override
	public int getDefectNumber() {
		String defectNumber = _element.getAttributeValue("defectNumber");
		int number = Integer.parseInt(defectNumber);
		return number;
	}

	@Override
	public CalendarDate getDateFound() {
		CalendarDate dateFound = new CalendarDate(_element.getAttributeValue("dateFound"));
		return dateFound;
	}

	@Override
	public void setDateFound(CalendarDate dateFound) {
		setAttr("dateFound", dateFound.toString());
	}

	@Override
	public CalendarDate getDateRemoved() {
		CalendarDate dateRemoved = new CalendarDate(_element.getAttributeValue("dateRemoved"));
		return dateRemoved;
	}

	@Override
	public void setDateRemoved(CalendarDate dateRemoved) {
		setAttr("dateRemoved", dateRemoved.toString());
	}

	@Override
	public int getPhaseOfInjection() {
		int phase = Defect.NO_PHASE;
		Attribute attr = _element.getAttribute("phaseOfInjection");
		if(attr != null){
			phase = Integer.parseInt(attr.getValue());
		}
		return phase;
	}

	@Override
	public void setPhaseOfInjection(int phaseOfInjection) {
		if(phaseOfInjection >= 0 && phaseOfInjection <= 6){
			setAttr("phaseOfInjection", String.valueOf(phaseOfInjection));
		}	
	}

	@Override
	public int getPhaseOfRemoval() {
		int phase = Defect.NO_PHASE;
		Attribute attr = _element.getAttribute("removalPhase");
		if(attr != null){
			phase = Integer.parseInt(attr.getValue());
		}
		return phase;
	}

	@Override
	public void setPhaseOfRemoval(int phaseOfRemoval) {
		if(phaseOfRemoval >= 0 && phaseOfRemoval <= 6){
			setAttr("removalPhase", String.valueOf(phaseOfRemoval));
		}	
	}

	@Override
	public boolean isRemoved() {
		return !(this.getPhaseOfRemoval() == Defect.NO_PHASE);
	}

    @Override
	public int getTypeOfDefect() {
    	int typeOfDefect = Defect.NON_TYPE;
		Attribute attr = _element.getAttribute("typeOfDefect");
		if(attr != null){
			typeOfDefect = Integer.parseInt(attr.getValue());
		}
		return typeOfDefect;
	}

	@Override
	public void setTypeOfDefect(int typeOfDefect) {
		if(typeOfDefect >= 0 && typeOfDefect <= 10){
			setAttr("typeOfDefect", String.valueOf(typeOfDefect));
		}	
	}
	
	@Override
	public String getDescription() {
		String description = _element.getAttributeValue("description");
		return description;
	}

	@Override
	public void setDescription(String description) {
		setAttr("description", description);
	}
	
	@Override
	public String toString(){
		String result = "Defect number: " + getDefectNumber() + "\n";
		result = result + "Defect type: "+ getTypeOfDefect() + "\n";
		result = result + "Defect description: "+ getDescription() + "\n";
		return result;
	}

	@Override
	public Element getElement() {
		return _element;
	}
	
    private void setAttr(String a, String value) {
        Attribute attr = _element.getAttribute(a);
        if (attr == null)
           _element.addAttribute(new Attribute(a, value));
        else
            attr.setValue(value);
    }
}
