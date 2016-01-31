package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.Phase;
import nu.xom.Attribute;
import nu.xom.Element;

public class DefectImpl implements Defect {
	
	private Element _element = null;
	private Phase injectionPhase = new PhaseImpl(7);
	private Phase removalPhase = new PhaseImpl(7);
	private TypeOfDefect defectType = null;

	
	public DefectImpl(Element element){
	  _element = element;	
	}
	
	@Override
	public int getDefectNumber() {
		// TODO Auto-generated method stub
		return 0;
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
	public String getPhaseOfInjection() {
		return injectionPhase.toString();
	}

	@Override
	public void setPhaseOfInjection(String phaseOfInjection) {
		injectionPhase.setPhase(phaseOfInjection);
	}

	@Override
	public String getPhaseOfRemoval() {
		return removalPhase.toString();
	}

	@Override
	public void setPhaseOfRemoval(String phaseOfRemoval) {
		removalPhase.setPhase(phaseOfRemoval);
	}

	@Override
	public boolean isRemoved() {
		return !(this.getPhaseOfRemoval().equals("No phase"));
	}

    @Override
	public String getTypeOfDefect() {
		return _element.getAttributeValue("typeOfDefect");
	}

	@Override
	public void setTypeOfDefect(String typeOfDefect) {
		setAttr("typeOfDefect", defectType.toString());
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

	private enum TypeOfDefect{
		DOCUMENTATION(0){
		    public String toString() {return "Documentation";}
		}, SYNTAX(1){
			public String toString() {return "Syntax";}
		}, BUILD(2){
			public String toString() {return "Build";}
		}, ASSIGNMENT(3){
			public String toString() {return "Assignment";}
		}, INTERFACE(4){
			public String toString() {return "Interface";}
		},CHECKING(5){
			public String toString() {return "Checking";}
		}, DATA(6){
			public String toString() {return "Data";}
		}, FUNCTION(7){
			public String toString() {return "Function";}
		}, SYSTEM(8){
			public String toString() {return "System";}
		}, ENVIROMENT(9){
			public String toString() {return "Enviroment";}
		};
		
		private int defectType;
		
		TypeOfDefect(int defectType){
			this.defectType = defectType;
		}
		
		public String getDefectType(){
			return toString();
		}
		
		public void setDefectType(int type){
			defectType = type;
		}
		
		public void setDefectType(String type){
			if(type.equalsIgnoreCase("Documentation")){
				defectType = 0;
			}
			else if(type.equalsIgnoreCase("Syntax")){
				defectType = 1;
			}
			else if(type.equalsIgnoreCase("Build")){
				defectType = 2;
			}
			if(type.equalsIgnoreCase("Assignment")){
				defectType = 3;
			}
			if(type.equalsIgnoreCase("Interface")){
				defectType = 4;
			}
			if(type.equalsIgnoreCase("Checking")){
				defectType = 5;
			}
			if(type.equalsIgnoreCase("Data")){
				defectType = 6;
			}
			if(type.equalsIgnoreCase("Function")){
				defectType = 7;
			}
			if(type.equalsIgnoreCase("System")){
				defectType = 8;
			}
			if(type.equalsIgnoreCase("Enviorment")){
				defectType = 9;
			}
		}
		
	}
}
