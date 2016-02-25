package net.sf.memoranda;

public interface Profile {
	//Phase
    public static final int PLANNING = 1;
    public static final int DESIGN = 2;
    public static final int CODE = 3;
    public static final int REVIEW = 4;
    public static final int COMPILE = 5;
    public static final int TESTING = 6;
    public static final int NO_PHASE = 0;
    
    static final String[] PHASE = {"PL", "DS", "CD", "RV", "CM", "TS", "PM"};

	void addProject(String id, long[] times, int[] defects, int line) throws IllegalArgumentException;

	void removeProject(String id);

	void refreshProfile();
	
	long getLifetimeTiming();
	int getLifetimeDefects();
	int getLifetimeLines();

	long[] getTimes(String id);
	int[] getDefects(String id);

	int getProjectLines(String id);
	
	long getPhaseTimes(String id, String phase);
	public long getPhaseDefects(String id, String phase);

}
