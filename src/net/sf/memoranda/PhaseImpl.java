package net.sf.memoranda;

public class PhaseImpl implements Phase{
    private int phase; 
	
	//PSP Phases
	public static final int PLANNING = 0;
	public static final int DESIGN = 1;
	public static final int CODE = 2;
	public static final int REVIEW = 3;
	public static final int COMPILE = 4;
	public static final int TESTING = 5;
	public static final int POSTMORTEM = 6;
	public static final int NO_PHASE = 7;
	
	public PhaseImpl(int phase){
		this.phase = phase;
	}
	
	@Override
	public String getPhase() {
		return convertToString(phase);
	}

	@Override
	public void setPhase(String phase) {
		this.phase = convertToInt(phase);
		
	}

	@Override
	public void setPhase(int phase) {
		this.phase = phase;	
	}
	
	public String toString(){
		return convertToString(phase);
	}
	
	private String convertToString(int phase){
		String strPhase = "No Phase";
		if(phase == 0){
			strPhase = "Planning";
		}
		else if(phase == 1){
			strPhase = "Design";
		}
		else if(phase == 2){
			strPhase = "Code";
		}
		else if(phase == 3){
			strPhase = "Review";
		}
		else if(phase == 4){
			strPhase = "Compile";
		}
		else if(phase == 5){
			strPhase = "Testing";
		}
		else if(phase == 6){
			strPhase = "Postmortem";
		}
		else if(phase == 7){
			strPhase = "No Phase";
		}
		return strPhase;
	}

	private int convertToInt(String phase){
		int intPhase = 7; 
		if(phase.equalsIgnoreCase("Planning")){
			intPhase = 0;
		}
		else if(phase.equalsIgnoreCase("Design")){
			intPhase = 1;
		}
		else if(phase.equalsIgnoreCase("Code")){
			intPhase = 2;
		}
		else if(phase.equalsIgnoreCase("Review")){
			intPhase = 3;
		}
		else if(phase.equalsIgnoreCase("Compile")){
			intPhase = 4;
		}
		else if(phase.equalsIgnoreCase("Testing")){
			intPhase = 5;
		}
		else if(phase.equalsIgnoreCase("Postmortem")){
			intPhase = 6;
		}
		else if(phase.equalsIgnoreCase("No Phase")){
			intPhase = 7;
		}
		return intPhase;
	}
}
