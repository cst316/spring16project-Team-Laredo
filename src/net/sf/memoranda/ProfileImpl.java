package net.sf.memoranda;

import java.util.Map;

import nu.xom.Element;

public class ProfileImpl implements Profile{
	private Map<String, Long> m_Times;
	private Map<String, Integer> m_Defects;
	private Map<String, Integer> m_Lines;
	private long[] m_lifeTimes = {0,0,0,0,0,0,0,0};
	private int[] m_lifeDefects = {0,0,0,0,0,0,0,0};
	private int m_lifeLines = 0;
    
    /*
     * Keys will have form PH# where # is the project 
     * id and PH is the shorthand for a PSP phase.
     * For example, to access the time spent coding 
     * in project "stuff", the key would be "CDstuff"
     */
    
    /**
     * Empty Constructor.
     */
    ProfileImpl() {
    }
    
	ProfileImpl(String[] ids, long[] time, int[] defect, int[] lines) {
		//Ensures the correct amount of data is passed.
		if(time.length == defect.length 
				&& lines.length == ids.length
				&& time.length == ids.length * 7) {
			int x = 0;
			for(String id : ids) {
				int p = 0;
				id = id.toLowerCase();
				for(int i = x*6; i < (x+1)*6; i++) {
					m_Times.put(PHASE[p] + id, time[i]);
					m_Defects.put(PHASE[p] + id, defect[i]);
					if(p == 2) {
						m_Lines.put(PHASE[p], lines[x]);
					}
					p++;
				}
				x++;
			}
			refreshProfile();
		}
	}
	
	
	public void addProject(String id, long[] times, int[] defects, int line) throws IllegalArgumentException {
		if(times.length == defects.length && times.length == 7) {
			for(int i = 0; i < 7; i++) {
				m_Times.put(id.toLowerCase().concat(PHASE[i]), times[i]);
				m_Defects.put(id.toLowerCase().concat(PHASE[i]), defects[i]);
				if(i == 2) {
					m_Lines.put(id.toLowerCase().concat(PHASE[i]), line);
				}
			}
			refreshProfile(times, defects, line);
		}
		else {
			throw new IllegalArgumentException("Array does not contain enough data for project " + id);
		}	
	}
	
	
	public void removeProject(String id) {
		int p = 0;
		while (p < 7) {
			for (String key : m_Times.keySet()) {
				if(key.endsWith(id.toLowerCase()) && key.length() == id.length() + 2) {
					m_Times.remove(key);
					m_Defects.remove(key);
					if(key.startsWith(PHASE[2])){
						m_Lines.remove(key);
					}
					p++;
				}
			}
			if(p == 0) {
				System.out.println("Project " + id + " does not exist.");
				break;
			}
		}
		refreshProfile();
	}
	
	
	public void refreshProfile() {
		generateTimes();
		generateDefects();
		generateLines();
	}
	
	
	private void refreshProfile(long[] times, int[] defects, int line) {
		generateTimes(times);
		generateDefects(defects);
		generateLines(line);
	}
	
	
	private void generateTimes() {
		m_lifeTimes = new long[] {0,0,0,0,0,0,0,0};
		for (String key : m_Times.keySet()) {
			//Check first two chars in key for phase.
			for(int i = 0; i < 7; i++) {
				if(key.startsWith(PHASE[i])) {
					m_lifeTimes[i] = m_lifeTimes[i] + m_Times.get(key);
					break;
				}
			}
			m_lifeTimes[7] = m_lifeTimes[7] + m_Times.get(key);
		}
	}
	
	
	private void generateTimes(long[] times) throws IllegalArgumentException {
		if(times.length == 7) {
			for(int i = 0; i < 7; i++) {
				m_lifeTimes[i] = m_lifeTimes[i] + times[i];
				m_lifeTimes[7] = m_lifeTimes[7] + times[i];
			}
		}
		else {
			throw new IllegalArgumentException("Array does not contain enough data for a project.");
		}
	}
	
	
	private void generateDefects() {
		m_lifeDefects = new int[] {0,0,0,0,0,0,0,0};
		for (String key : m_Defects.keySet()) {
			//Check first two chars in key for phase.
			for(int i = 0; i < 7; i++) {
				if(key.startsWith(PHASE[i])) {
					m_lifeDefects[i] = m_lifeDefects[i] + m_Defects.get(key);
					break;
				}
			}
			m_lifeDefects[7] = m_lifeDefects[7] + m_Defects.get(key);
		}
	}
	
	
	private void generateDefects(int[] defects) throws IllegalArgumentException {
		if(defects.length == 7) {
			for(int i = 0; i < 7; i++) {
				m_lifeDefects[i] = m_lifeDefects[i] + defects[i];
				m_lifeDefects[7] = m_lifeDefects[7] + defects[i];
			}
		}
		else {
			throw new IllegalArgumentException("Array does not contain enough data for a project.");
		}
	}
	
	
	private void generateLines() {
		m_lifeLines = 0;
		for(int line : m_Lines.values()) {
			m_lifeLines = m_lifeLines + line;
		}
	}
	
	
	private void generateLines(int line) {
		m_lifeLines = m_lifeLines + line;
	}
	
	
	public long getLifetimeTiming() {
		return m_lifeTimes[7];
	}
	
	
	public int getLifetimeDefects() {
		return m_lifeDefects[7];
	}
	
	
	public int getLifetimeLines() {
		return m_lifeLines;
	}
	
	
	public long[] getTimes(String id) {
		int p = 0;
		long[] times = new long[7];
		while (p < 7) {
			for (String key : m_Times.keySet()) {
				if(key.endsWith(id.toLowerCase())&&key.length() == id.length() + 2) {
					times[p] = m_Times.get(key);
					p++;
				}
			}
			if(p == 0) {
				System.out.println("Project " + id + " does not exist.");
				break;
			}
		}
		return times;
	}
	
	
	public int[] getDefects(String id) {
		int p = 0;
		int[] defects = new int[7];
		while (p < 7) {
			for (String key : m_Defects.keySet()) {
				if(key.endsWith(id.toLowerCase())&&key.length() == id.length() + 2) {
					defects[p] = m_Defects.get(key);
					p++;
				}
			}
			if(p == 0) {
				System.out.println("Project " + id + " does not exist.");
				break;
			}
		}
		return defects;
	}
	
	
	public int getProjectLines(String id) {
		return m_Lines.get(id.toLowerCase().concat(PHASE[2]));
	}
	
	
	public long getPhaseTimes(String id, String phase){
		int x = 42;
		for(int i = 0; i < 8; i++) {
			if(phase.equals(PHASE[i])) {
				x = i;
			}
		}
		if(x != 42 && m_Times.containsKey(id.toLowerCase().concat(PHASE[x]))) {
			return m_Times.get(id.toLowerCase().concat(PHASE[x]));
		}
		else {
			System.out.println("Project " + id + " does not exist.");
			return 0;
		}
	}
	
	
	public long getPhaseDefects(String id, String phase){
		int x = 42;
		for(int i = 0; i < 8; i++) {
			if(phase.equals(PHASE[i])) {
				x = i;
			}
		}
		if(x != 42 && m_Defects.containsKey(id.toLowerCase().concat(PHASE[x]))) {
			return m_Defects.get(id.toLowerCase().concat(PHASE[x]));
		}
		else {
			System.out.println("Project " + id + " does not exist.");
			return 0;
		}
	}
	
}
